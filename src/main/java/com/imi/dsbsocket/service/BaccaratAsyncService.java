package com.imi.dsbsocket.service;

import com.imi.dsbsocket.entity.model.CardTable;
import com.imi.dsbsocket.task.BaccaratAssignmentTask;
import com.imi.dsbsocket.util.BaccaratUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Ron
 */
@Slf4j
@Service
@Async("baccaratExecutor")
public class BaccaratAsyncService {

    /**
     * @Async("baccaratExecutor") public CompletableFuture<String> doSomething2(String message) throws InterruptedException {
     * log.info("do something2: {}", message);
     * Thread.sleep(1000);
     * return CompletableFuture.completedFuture("; do something2: " + message);
     * }
     */

//    @Async("baccaratExecutor")
//    public Future<String> initTable() {
//        for (int i = 1; i < BaccaratAssignmentTask.TABLE_NUM + 1; i++) {
//            CardTable cardTable = new CardTable();
//            Stack<Card> cards = BaccaratUtil.shufflingCard();
//            cardTable.setSendNum(BaccaratUtil.pack * BaccaratUtil.cardsNum - cards.size());
//            cardTable.setCountSeconds(BaccaratAssignmentTask.SHUFFLE_SECONDS);
//            cardTable.setStatus(BaccaratStatus.SHUFFLING_TIME);
//            cardTable.setTableId(i);
////            BaccaratAsyncService.offerBaccaratInQueue(roomIdTest, cardTable);
//            BaccaratAssignmentTask.offerBaccaratInQueue(cardTable);
//        }
//        return new AsyncResult<>("done");
//    }

    /**
     * 倒數 轉換狀態
     *
     * @return
     */
    @Async("baccaratExecutor")
    public void countdown(CardTable cardTable) {
        if (cardTable != null) {
            int countSeconds = cardTable.getCountSeconds();
            if (countSeconds == 0) {
                BaccaratUtil.switchStatusAndDo(cardTable);
            } else {
                cardTable.setCountSeconds(countSeconds - 1);
            }
            BaccaratAssignmentTask.offerBaccaratInQueue(cardTable);

            int roomId = cardTable.getRoomId();
            int tableId = cardTable.getTableId();
            String key = roomId + "_" + tableId;
            BaccaratAssignmentTask.putToRoomsMap(key, cardTable);
        }

//        BaccaratAssignmentTask.roomsMap.put(key, cardTable);
//        log.info("<< countdown BaccaratAsyncService threadName : {} BaccaratAsyncService cardTable : {}", Thread.currentThread().getName(), BaccaratAssignmentTask.getRooms());
//        return CompletableFuture.completedFuture(cardTable);
    }
}
