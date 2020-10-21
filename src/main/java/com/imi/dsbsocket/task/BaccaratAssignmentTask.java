package com.imi.dsbsocket.task;


import com.corundumstudio.socketio.SocketIOClient;
import com.imi.dsbsocket.entity.base.Card;
import com.imi.dsbsocket.entity.CardTable;
import com.imi.dsbsocket.enums.BaccaratStatus;
import com.imi.dsbsocket.service.BaccaratAsyncService;
import com.imi.dsbsocket.socket.SocketHandler;
import com.imi.dsbsocket.util.BaccaratUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.*;


/**
 * @author fm035
 * <p>
 * 2. 在定時任務方法上新增 @ Async 註解
 * <p>
 * # 定時指派任務
 */
@Slf4j
@Component
@QuartzDataSource
@EnableAsync
public class BaccaratAssignmentTask {

    /**
     * 1-下注中/2-开牌中/3-派奖中/4-准备中/5-洗牌中
     */
    private static ConcurrentLinkedQueue<CardTable> rooms = new ConcurrentLinkedQueue<>();
    private static Map<String, CardTable> roomsMap = new ConcurrentHashMap<>(16);

    /**
     * 參數時間
     */
    public static final int BET_SECONDS = 2;
    public static final int OPEN_SECONDS = 2;
    public static final int AWARD_SECONDS = 2;
    public static final int PREPARE_SECONDS = 2;
    public static final int SHUFFLE_SECONDS = 2;
    public static int[] baccaratTimes;

    /**
     * 桌子數
     */
    public static final int SENIOR_TABLE_NUM = 5;
    public static final int BASIC_TABLE_NUM = 5;
    public static final int TOTAL_TABLE_NUM = 10;
    public static int[] tableNums;

    /**
     * thread number
     */
    public static final int THREAD_NUMBER = 5;

    @Autowired
    BaccaratAsyncService baccaratAsyncService;

    /**
     * fixedRate：設定定時任務執行的固定間隔，該值為當前任務啟動時間與下次任務啟動時間之差；
     * fixedDelay：設定定時任務執行的固定間隔，該值為當前任務結束時間與下次任務啟動時間之差；
     */

    @Async
    @Scheduled(fixedRate = 1000)
    public void perSecondJob() throws ExecutionException, InterruptedException {
//        log.info(Thread.currentThread().getId() + " ----- job1 ----- " + System.currentTimeMillis());
        for (int i = 0; i < BaccaratAssignmentTask.TOTAL_TABLE_NUM; i++) {
            CardTable cardTable = BaccaratAssignmentTask.pollBaccaratFromQueue();
            if (cardTable == null) {
                Future<String> initTask = init();
                // 等待 牌桌完成
                while (true) {
                    if (initTask.isDone()) {
                        break;
                    }
                }
                cardTable = BaccaratAssignmentTask.pollBaccaratFromQueue();
            }
            CompletableFuture<CardTable> tableCompletableFuture = baccaratAsyncService.countdown(cardTable);
            // 等待 倒數結果
            while (true) {
                if (tableCompletableFuture.isDone()) {
                    break;
                }
            }
            CardTable result = tableCompletableFuture.get();
            int roomId = result.getRoomId();
            int tableId = result.getTableId();
            String key = roomId + "_" + tableId;
            roomsMap.put(key, result);
        }
    }

    //    @Async
//    @Scheduled(fixedRate = 1000)
//    public void socketSentTable() {
//        roomsMap.keySet().forEach(e -> {
//            log.info(roomsMap.get(e).toString());
//        });
//    }

    @Autowired
    SocketHandler socketHandler;

    @Async
    @Scheduled(fixedRate = 1000)
    public void socketSentTable() {
//        CopyOnWriteArraySet<MyWebSocket> webSocketSet = MyWebSocket.getWebSocketSet();
        CopyOnWriteArraySet<SocketIOClient> webSocketSet = SocketHandler.getWebSocketSet();
//        SocketHandler.message()
        int i = 0;
//        log.info("  定时发送  " + new Date().toLocaleString());
        webSocketSet.forEach(c -> {
            try {
                socketHandler.sendRoom(c, "  定时发送  " + new Date().toLocaleString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 定時任務 ↑↑↑
     */

    public static void offerBaccaratInQueue(CardTable cardTable) {
        rooms.offer(cardTable);
    }

    public static CardTable pollBaccaratFromQueue() {
        return rooms.poll();
    }

    /**
     * get set
     */
    public static ConcurrentLinkedQueue<CardTable> getRooms() {
        return rooms;
    }

    public static Map<String, CardTable> getRoomsMap() {
        return roomsMap;
    }

    /**
     * 初始化 所有房間所有牌桌 todo 取得個別桌子參數( 時間 賠率 金額 )
     */
    public Future<String> init() {
        // 初始化房間 桌子數
        tableNums = new int[]{SENIOR_TABLE_NUM, BASIC_TABLE_NUM};
        // 初始化桌子 秒數
        baccaratTimes = new int[]{BET_SECONDS, OPEN_SECONDS, AWARD_SECONDS, PREPARE_SECONDS, SHUFFLE_SECONDS};

        for (int i = 0; i < tableNums.length; i++) {
            for (int j = 0; j < tableNums[i]; j++) {
                CardTable cardTable = new CardTable();
                Stack<Card> cards = BaccaratUtil.shufflingCard();
                cardTable.setSendNum(BaccaratUtil.PACK_NUM * BaccaratUtil.CARDS_NUM - cards.size());
                cardTable.setCardStack(cards);
                cardTable.setCountSeconds(baccaratTimes[4]);
                cardTable.setStatus(BaccaratStatus.SHUFFLING_TIME);
                cardTable.setRoomId(i + 1);
                cardTable.setTableId(j + 1);

                cardTable.setBettingTime(baccaratTimes[0]);
                cardTable.setOpeningTime(baccaratTimes[1]);
                cardTable.setAwardingTime(baccaratTimes[2]);
                cardTable.setPreparingTime(baccaratTimes[3]);
                cardTable.setShufflingTime(baccaratTimes[4]);

                BaccaratAssignmentTask.offerBaccaratInQueue(cardTable);
            }
        }
        /** 確保 init 完成 */
        return new AsyncResult<>("done");
    }


}
