package com.imi.dsbsocket.socket;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.imi.dsbsocket.dto.SendCmdDto;
import com.imi.dsbsocket.dto.SendMsgDto;
import com.imi.dsbsocket.dto.WsSocketDTO;

import com.imi.dsbsocket.entity.model.CardTable;
import com.imi.dsbsocket.enums.DsbErrorCodeMsg;
import com.imi.dsbsocket.service.*;

import com.imi.dsbsocket.util.ExceptionUtil;
import com.imi.dsbsocket.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.LongAdder;

@Component
public class SocketHandler<session> {

    /**
     * log
     */
    private Logger log = LoggerFactory.getLogger(SocketHandler.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。 房间在线人数
     * <p>
     * socketIOServer.getAllClients().size()
     */
    private static LongAdder onlineCount;

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private SocketIOClient thisClient;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 在外部可以获取此连接的所有websocket对象，并能对其触发消息发送功能，我们的定时发送核心功能的实现在与此变量
     */
//    private static CopyOnWriteArraySet<SocketHandler> webSocketSet = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<SocketIOClient> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * socketIOServer
     */
    private final SocketIOServer socketIOServer;
    private final DsbWsService dsbWsService;
    private final AccountManagementService accountManagementService;
    private final SocketEventService socketEventService;
    private final OrderManagementService orderManagementService;
    private final ObjectMapper objectMapper;
    private final DsbSocketMsgService dsbSocketMsgService;
    private final DsbSocketDomainService dsbSocketDomainService;

    @Autowired
    public SocketHandler(SocketIOServer socketIOServer, DsbWsService dsbWsService, AccountManagementService accountManagementService, SocketEventService onDataService, OrderManagementService orderManagementService, ObjectMapper objectMapper, DsbSocketMsgService dsbSocketMsgService, DsbSocketDomainService dsbSocketDomainService) {
        this.socketIOServer = socketIOServer;
        this.dsbWsService = dsbWsService;
        this.accountManagementService = accountManagementService;
        this.socketEventService = onDataService;
        this.orderManagementService = orderManagementService;
        this.objectMapper = objectMapper;
        this.dsbSocketMsgService = dsbSocketMsgService;
        this.dsbSocketDomainService = dsbSocketDomainService;
    }

    private void updateConnCount() {
        int onLineConnCount = socketIOServer.getAllClients().size();
        log.info("online count(包含後台):" + onLineConnCount);
        dsbSocketDomainService.updateOneBySelf(onLineConnCount);
    }

    //当客户端发起连接时调用
    @OnConnect
    @Transactional
    public void onConnect(SocketIOClient client) {
        try {
            log.info("onConnect :sessionId:" + client.getSessionId() + "  clientAddr:" + client.getRemoteAddress());
            HandshakeData handshakeData = client.getHandshakeData();
            String token = handshakeData.getSingleUrlParam("token");
            String where = handshakeData.getSingleUrlParam("where");
            String roomId = handshakeData.getSingleUrlParam("roomId");

            thisClient = client;
//            onlineCount.increment();    //在线数加1
            webSocketSet.add(client);     //加入set中

            String realIp = client.getRemoteAddress().toString();
            int one = client.getRemoteAddress().toString().lastIndexOf(":");
            realIp = realIp.substring(1, one);
            log.info("onConnect 真實ip:" + realIp);
//            DecodedJWT jwt = JwtUtils.verifyToken(token);

//            if (jwt != null) {
//                Map<String, Object> jwtMap = JwtUtils.getPayLoadDataRtMap(jwt);
            updateConnCount();
            //連接成功socket server會自動傳connect事件到dsbOrder、dsbFront、dsbAdmin的webSocketUtil.js的connectHandler
            log.info("sessionId:" + client.getSessionId().toString() + " where:" + where + " 連線成功 !!");
            if ("dsbFront".equals(where)) {
                accountManagementService.updateOnlineSessionId(token, client.getSessionId().toString());
            }
            if (null != roomId && !"".equals(roomId)) {//不為空值才送
                client.joinRoom(roomId);
            }
//            } else {
//                //sendErrorMsg(client, DsbErrorCodeMsg.GET_TOKEN_FAILE.getErrorMsg(), DsbErrorCodeMsg.GET_TOKEN_FAILE.getErrorCode());
//                log.error("JWT is null .");
//            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }

    //客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    @Transactional
    public void onDisConnect(SocketIOClient client) {
        updateConnCount();
        log.info("onDisconnect:" + client.getSessionId());
        HandshakeData handshakeData = client.getHandshakeData();
        String where = handshakeData.getSingleUrlParam("where");
        String token = handshakeData.getSingleUrlParam("token");
        if ("dsbFront".equals(where)) {
            // 清空当前wsClient SessionId
            accountManagementService.updateOnlineSessionId(token, "");
        }
    }


    @OnEvent("message")
    public void message(SocketIOClient client, SendCmdDto data, AckRequest ackRequest) {
        try {
            log.info("onEvent message SendCmdDto: " + data.toString());
            DecodedJWT jwt = JwtUtils.verifyToken(data.getToken());
            if (jwt != null) {
                log.info("onData : sessionId" + client.getSessionId() + " SendCmdDto:" + data);
                String sessionId = "";
                WsSocketDTO socketParams = new WsSocketDTO();
                if (data.getWhere() != null && "dsbFront".equalsIgnoreCase(data.getWhere())) {
                    sessionId = accountManagementService.findSessionIdByToken(data.getToken());
                } else {
                    sessionId = client.getSessionId().toString();
                }
                socketParams.setData(data);
                socketParams.setClient(client);
                socketParams.setJwt(jwt);
                socketParams.setAckRequest(ackRequest);
                socketParams.setSessionId(sessionId);
                getStrategy(data.getCmd(), socketParams);
            } else {
                dsbWsService.sendErrorMsg(client, DsbErrorCodeMsg.GET_TOKEN_FAILE.getErrorMsg(), DsbErrorCodeMsg.GET_TOKEN_FAILE.getErrorCode());
                log.error("JWT is null .");
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }

    @OnEvent("sendMsg")
    public void sendMsg(SocketIOClient client, SendMsgDto sendMsgDto, AckRequest ackRequest) {
        try {
            log.info("onEvent sendMsg sendMsgDto: " + sendMsgDto);
            DecodedJWT jwt = JwtUtils.verifyToken(sendMsgDto.getToken());
            if (jwt != null) {
                Map<String, Object> jwtTokenMap = JwtUtils.getPayLoadDataRtMap(jwt);
                String name = jwtTokenMap.get("name").toString(); //取出token的名字
                String roomId = sendMsgDto.getRoomId();
                long timeStamp = System.currentTimeMillis(); //取得當前時間時間戳
                sendMsgDto.setDate(timeStamp);
                sendMsgDto.setToken(""); //送出聊天訊息前清空token
                sendMsgDto.setName(name);
                BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomId);
                roomOperations.getClients().forEach(socketIOClient -> {
                    socketIOClient.sendEvent("receiveMsg", sendMsgDto); //對應dsbFront webSocketUtil的receiveMsg註冊事件
                });

                //redis推播事件
//                Packet packet = new Packet(PacketType.MESSAGE);
//                ObjectNode packetJson = objectMapper.createObjectNode();
//                packetJson.put("cmd", "sendMsg");
//                packetJson.put("message", sendMsgDto.getMessage());
//                packetJson.put("roomId", sendMsgDto.getRoomId());
//                packetJson.put("token", sendMsgDto.getToken());
//                packetJson.put("isImageMessage", sendMsgDto.getIsImageMessage());
//                packetJson.put("date", sendMsgDto.getDate());
//                packetJson.put("role", sendMsgDto.getRole());
//                packetJson.put("name", sendMsgDto.getName());
//                packet.setData(packetJson);
//                socketIOServer.getConfiguration().getStoreFactory().pubSubStore().publish(PubSubType.DISPATCH,
//                        new DispatchMessage(roomId, packet, ""));
                dsbSocketMsgService.insertDsbSocketMsgData(sendMsgDto, roomId); //儲存聊天訊息
            } else {
                dsbWsService.sendErrorMsg(client, DsbErrorCodeMsg.GET_TOKEN_FAILE.getErrorMsg(), DsbErrorCodeMsg.GET_TOKEN_FAILE.getErrorCode());
                log.error("JWT is null .");
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }

    @OnEvent("sendRoom")
    public void sendRoom(SocketIOClient client, CardTable cardTable) throws IOException {
        if (cardTable == null) {
            return;
        }
//        Packet packet = new Packet(PacketType.MESSAGE);
//        ObjectNode packetJson = objectMapper.createObjectNode();
//        SendCmdDto sendCmdDto = wsSocketDTO.getData();

        HandshakeData handshakeData = client.getHandshakeData();
//        String token = handshakeData.getSingleUrlParam("token");
//        String where = handshakeData.getSingleUrlParam("where");
        String roomId = handshakeData.getSingleUrlParam("roomId");

//        packetJson.put("cmd", "sendRoom");
//        packetJson.put("message", message);
//        packetJson.put("roomId", roomId);
//        packetJson.put("token", token);
//        packet.setData(packetJson);
//        thisClient.send(packet);

        BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomId);
        roomOperations.getClients().forEach(socketIOClient -> {
            socketIOClient.sendEvent("sendRoom", cardTable.toString()); //對應dsbFront webSocketUtil的receiveMsg註冊事件
        });
    }


    private void getStrategy(String cmdName, WsSocketDTO cmdParams) throws Exception {
        getCmdExecute(DsbWsService.cmd.valueOf(cmdName), cmdParams);
    }

    private void getCmdExecute(DsbWsService.cmd cmdName, WsSocketDTO cmdParams) throws Exception {
        log.debug("server cmd -> " + cmdName.toString());
        switch (cmdName) {
            case newOrderInfo:
                socketEventService.newOrderInfo(cmdParams);
                break;
            case matchOrderInfo:
                socketEventService.matchOrderInfo(cmdParams);
                break;
            case kickOutApi:
                socketEventService.kickOutApi(cmdParams);
                break;
            case updateAccountBalance:
                socketEventService.updateAccountBalance(cmdParams);
                break;
            case updateAmount:
                socketEventService.updateAmount(cmdParams);
                break;
            case showSystemNews:
                socketEventService.showSystemNews(cmdParams);
                break;
            case forcedKickingOut:
                socketEventService.forcedKickingOut(cmdParams);
                break;
            case reviewOrderDelete:
                socketEventService.reviewOrderDelete(cmdParams);
                break;
            case rushOrders:
                socketEventService.rushOrders(cmdParams);
                break;
            case joinRoom:
                socketEventService.joinRoom(cmdParams);
                break;
            case buyerCreateAppealChat:
                socketEventService.buyerCreateAppealChat(cmdParams);
                break;
            case inviteCustomerService:
                socketEventService.inviteCustomerService(cmdParams);
                break;
            case createConfirmationBox:
                socketEventService.createConfirmationBox(cmdParams);
                break;
            case buyerCancel:
                socketEventService.buyerCancel(cmdParams);
                break;
            case buyerHadPaid:
                socketEventService.buyerHadPaid(cmdParams);
                break;
            case buyerAppeal:
                socketEventService.buyerAppeal(cmdParams);
                break;
            case showTheCloseCard:
                socketEventService.showTheCloseCard(cmdParams);
                break;
            default:
                throw new IllegalStateException("No strategy known with cmd " + cmdName);
        }
    }

    //    public static CopyOnWriteArraySet<SocketHandler> getWebSocketSet() {
//        return webSocketSet;
//    }
    public static CopyOnWriteArraySet<SocketIOClient> getWebSocketSet() {
        return webSocketSet;
    }

    public SocketIOClient getThisClient() {
        return thisClient;
    }
}