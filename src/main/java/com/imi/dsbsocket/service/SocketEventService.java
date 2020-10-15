package com.imi.dsbsocket.service;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.imi.dsbsocket.dto.SendCmdDto;
import com.imi.dsbsocket.dto.WsSocketDTO;
import com.imi.dsbsocket.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SocketEventService {

    private final ObjectMapper objectMapper;
    private final SocketIOServer socketIOServer;
    private final OrderManagementService orderManagementService;
    private final AccountManagementService accountManagementService;
    private final DsbWsService dsbWsService;
    private Logger log = LoggerFactory.getLogger(SocketEventService.class);

    public SocketEventService(ObjectMapper objectMapper, SocketIOServer socketIOServer, OrderManagementService orderManagementService, AccountManagementService accountManagementService, DsbWsService dsbWsService) {
        this.objectMapper = objectMapper;
        this.socketIOServer = socketIOServer;
        this.orderManagementService = orderManagementService;
        this.accountManagementService = accountManagementService;
        this.dsbWsService = dsbWsService;
    }

    public void newOrderInfo(WsSocketDTO cmdParams) {
        String orderNo = cmdParams.getData().getJsonResult().get("orderNo").asText();
        String cmd = cmdParams.getData().getCmd();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("orderNo", orderNo);
        SendCmdDto data = new SendCmdDto();
        data.setCmd(cmd);
        data.setMessage("success");
        data.setCode(0);
        data.setJsonResult(json);
        socketIOServer.getRoomOperations("dsbOrder").sendEvent("message", data);
        ObjectNode json2 = objectMapper.createObjectNode();
        json2.put("orderNo", orderNo);
        SendCmdDto data2 = new SendCmdDto();
        data2.setCmd("pushNewOrderInfo");
        data2.setMessage("success");
        data2.setCode(0);
        data2.setExecuteCmd(cmd);
        data2.setJsonResult(json2);
        List<String> sessionIdList = orderManagementService.noticeOrder(orderNo);
        dsbWsService.sendEventToAllOnlinePayee(sessionIdList, data2);
    }

    public void matchOrderInfo(WsSocketDTO cmdParams) throws Exception {
        String orderNo = cmdParams.getData().getJsonResult().get("orderNo").asText();
        String cmd = cmdParams.getData().getCmd();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("orderNo", orderNo);
        SendCmdDto data = new SendCmdDto();
        data.setCmd(cmd);
        data.setMessage("success");
        data.setCode(0);
        data.setJsonResult(json);
        socketIOServer.getRoomOperations("dsbOrder").sendEvent("message", data);
        int payeeId = orderManagementService.findOrderByNo(orderNo);
        String sessionId = accountManagementService.findPayeeOnlineById(payeeId).getSessionId();
        dsbWsService.newReviewOrder(sessionId, orderNo);
    }

    public void kickOutApi(WsSocketDTO cmdParams) throws Exception {
        String message = cmdParams.getData().getJsonResult().get("message").asText();
        String cmd = cmdParams.getData().getCmd();
        String sessionId = cmdParams.getData().getSessionId();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("message", message);
        SendCmdDto data = new SendCmdDto();
        data.setCmd(cmd);
        data.setMessage("success");
        data.setCode(0);
        data.setJsonResult(json);
        socketIOServer.getRoomOperations("dsbFront").sendEvent("message", data);
        dsbWsService.kickOutApi(sessionId, message);
    }

    public void updateAccountBalance(WsSocketDTO cmdParams) throws Exception {
        String accountBalance = cmdParams.getData().getJsonResult().get("accountBalance").asText();
        String cmd = cmdParams.getData().getCmd();
        String sessionId = cmdParams.getData().getSessionId();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("accountBalance", accountBalance);
        SendCmdDto data = new SendCmdDto();
        data.setCmd(cmd);
        data.setMessage("success");
        data.setCode(0);
        data.setJsonResult(json);
        socketIOServer.getRoomOperations("dsbFront").sendEvent("message", data);
        dsbWsService.updateAccountBalance(sessionId, accountBalance);
    }

    public void updateAmount(WsSocketDTO cmdParams) throws Exception {
        String accountBalance = cmdParams.getData().getJsonResult().get("accountBalance").asText();
        String frozenAmount = cmdParams.getData().getJsonResult().get("frozenAmount").asText();
        String cmd = cmdParams.getData().getCmd();
        String sessionId = cmdParams.getData().getSessionId();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("accountBalance", accountBalance);
        json.put("frozenAmount", frozenAmount);
        SendCmdDto data = new SendCmdDto();
        data.setCmd(cmd);
        data.setMessage("success");
        data.setCode(0);
        data.setJsonResult(json);
        socketIOServer.getRoomOperations("dsbFront").sendEvent("message", data);
        dsbWsService.updateAmount(sessionId, accountBalance, frozenAmount);
    }

    public void showSystemNews(WsSocketDTO cmdParams) throws Exception {
        String subject = cmdParams.getData().getJsonResult().get("subject").asText();
        String msg = cmdParams.getData().getJsonResult().get("msg").asText();
        String cmd = cmdParams.getData().getCmd();
        String sessionId = cmdParams.getData().getSessionId();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("subject", subject);
        json.put("msg", msg);
        SendCmdDto data = new SendCmdDto();
        data.setCmd(cmd);
        data.setMessage("success");
        data.setCode(0);
        data.setJsonResult(json);
        socketIOServer.getRoomOperations("dsbFront").sendEvent("message", data);
        dsbWsService.showSystemNews(sessionId, subject, msg);
    }

    public void forcedKickingOut(WsSocketDTO cmdParams) throws Exception {
        String message = cmdParams.getData().getJsonResult().get("message").asText();
        String sessionId = cmdParams.getData().getSessionId();
        dsbWsService.forcedKickingOut(sessionId, message);
    }


    public void reviewOrderDelete(WsSocketDTO cmdParams) throws Exception {
        String orderNo = cmdParams.getData().getJsonResult().get("orderNo").asText();
        String cmd = cmdParams.getData().getCmd();
        String sessionId = cmdParams.getData().getSessionId();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("orderNo", orderNo);
        SendCmdDto data = new SendCmdDto();
        data.setCmd(cmd);
        data.setMessage("success");
        data.setCode(0);
        data.setJsonResult(json);
        socketIOServer.getRoomOperations("dsbFront").sendEvent("message", data);
        dsbWsService.reviewOrderDelete(sessionId, orderNo);
    }

    public void rushOrders(WsSocketDTO cmdParams) throws Exception {
        String orderNo = cmdParams.getData().getJsonResult().get("orderNo").asText();
        String cmd = cmdParams.getData().getCmd();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("orderNo", orderNo);
        SendCmdDto data = new SendCmdDto();
        data.setCmd(cmd);
        data.setMessage("success");
        data.setCode(0);
        data.setJsonResult(json);
        socketIOServer.getRoomOperations("dsbFront").sendEvent("message", data);
        List<String> sessionIdList = orderManagementService.noticeOrder(orderNo);
        dsbWsService.sendEventToAllOnlinePayee(sessionIdList, data);
    }

    public void joinRoom(WsSocketDTO cmdParams) throws Exception {
        String roomId = cmdParams.getData().getRoomId();
        String sessionId = cmdParams.getSessionId();
        dsbWsService.joinRoom(roomId, sessionId);
    }

    public void buyerCreateAppealChat(WsSocketDTO cmdParams) throws Exception {
        String roomId = cmdParams.getData().getRoomId();
        int payeeId = cmdParams.getData().getPayeeId();
        String sessionId = accountManagementService.findPayeeOnlineById(payeeId).getSessionId();
        dsbWsService.buyerCreateAppealChat(roomId, sessionId);
    }

    public void inviteCustomerService(WsSocketDTO cmdParams) throws Exception {
        String roomId = cmdParams.getData().getRoomId();
        dsbWsService.inviteCustomerService(roomId);
    }

    public void createConfirmationBox(WsSocketDTO cmdParams) throws Exception {
        String roomId = cmdParams.getData().getRoomId();
        DecodedJWT jwt =cmdParams.getJwt();
        Map<String, Object> jwtTokenMap = JwtUtils.getPayLoadDataRtMap(jwt);
        String name=jwtTokenMap.get("name").toString(); //取出token的名字
        dsbWsService.createConfirmationBox(roomId,name);
    }

    public void buyerCancel(WsSocketDTO cmdParams) throws Exception {
        String orderNo = cmdParams.getData().getRoomId();
        int payeeId = cmdParams.getData().getPayeeId();
        String sessionId = accountManagementService.findPayeeOnlineById(payeeId).getSessionId();
        dsbWsService.buyerCancel(orderNo, sessionId);
    }

    public void buyerHadPaid(WsSocketDTO cmdParams) throws Exception {
        String orderNo = cmdParams.getData().getJsonResult().get("orderNo").asText();
        int payeeId = Integer.parseInt(cmdParams.getData().getJsonResult().get("payeeId").asText());
        String sessionId = accountManagementService.findPayeeOnlineById(payeeId).getSessionId();
        dsbWsService.buyerHadPaid(orderNo, sessionId);
    }

    public void buyerAppeal(WsSocketDTO cmdParams) throws Exception {
        String orderNo = cmdParams.getData().getJsonResult().get("orderNo").asText();
        int payeeId = Integer.parseInt(cmdParams.getData().getJsonResult().get("payeeId").asText());
        String sessionId = accountManagementService.findPayeeOnlineById(payeeId).getSessionId();
        String appealObj = cmdParams.getData().getJsonResult().get("appealObj").asText();
        String appealName = cmdParams.getData().getJsonResult().get("appealName").asText();
        String appealDesc = cmdParams.getData().getJsonResult().get("appealDesc").asText();
        dsbWsService.buyerAppeal(orderNo, sessionId, appealObj, appealName, appealDesc);
    }

    public void showTheCloseCard(WsSocketDTO cmdParams) throws Exception {
        log.debug("showTheCloseCard params -> " + cmdParams.toString());
        String orderNo = cmdParams.getData().getJsonResult().get("orderNo").asText();
        int payeeId = Integer.parseInt(cmdParams.getData().getJsonResult().get("payeeId").asText());
        String sessionId = accountManagementService.findPayeeOnlineById(payeeId).getSessionId();
        dsbWsService.showTheCloseCard(orderNo, sessionId);
    }
}
