package com.imi.dsbsocket.service;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.imi.dsbsocket.dto.SendCmdDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DsbWsService {

    private final SocketIOServer socketIOServer;
    private final ObjectMapper objectMapper;


    public DsbWsService(SocketIOServer socketIOServer, ObjectMapper objectMapper) {
        this.socketIOServer = socketIOServer;
        this.objectMapper = objectMapper;
    }

    public enum cmd {
        newOrderInfo,  // 新的訂單資訊
        matchOrderInfo, // 已匹配訂單資訊
        kickOutApi, //後台管理員踢除前台使用者API
        updateAccountBalance, //更新帳戶餘額
        updateAmount, //更新帳戶餘額與凍結金額
        showSystemNews, //顯示公告訊息
        forcedKickingOut, //強制登出
        reviewOrderDelete, //移出審核超时訂單，下方審核數量-1
        rushOrders, //移除接單頁面的訂單，下方數字-1
        joinRoom, //加入聊天室
        buyerCreateAppealChat, //上線發起申訴聊天
        inviteCustomerService, //通知客服此聊天室需要客服加入處理
        createConfirmationBox, //後台呼叫此方法通知dsbFront建立審核卡片
        buyerCancel, //上線取消訂單
        buyerHadPaid, //上線通知我已支付(加速到帳)
        buyerAppeal, //上線申訴
        showTheCloseCard //通知下線申訴聊天可關閉
    }

    public void sendErrorMsg(SocketIOClient client, String message, int errCode) {
        SendCmdDto data = new SendCmdDto();
        data.setCode(-1);
        data.setMessage(message);
        data.setErrCode(errCode);
        client.sendEvent("message", data);
    }

    public void sendSuccessMsg(SocketIOClient client, String ip, String executeCmd) {
        SendCmdDto data = new SendCmdDto();
        data.setCode(0);
        data.setErrCode(0);
        data.setMessage("success");
        data.setIp(ip);
        data.setExecuteCmd(executeCmd);
        client.sendEvent("message", data);
    }

    public void sendEventToAllOnlinePayee(List<String> sessionList, SendCmdDto data) {
        if (sessionList != null) {
            sessionList.forEach(sessionId -> {
                if (StringUtils.isNotBlank(sessionId)) {
                    UUID uuid = UUID.fromString(sessionId);
                    SocketIOClient client = socketIOServer.getClient(uuid);
                    if (client != null) {
                        client.sendEvent("message", data);
                    }
                }
            });
        }
    }

    public void newReviewOrder(String sessionId, String orderNo) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("orderNo", orderNo);

                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("pushNewReviewOrderInfo");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void kickOutApi(String sessionId, String message) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("message", message);
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("kickOutApi");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void updateAccountBalance(String sessionId, String balance) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("accountBalance", balance);
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("updateAccountBalance");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void updateAmount(String sessionId, String balance, String frozenAmount) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("accountBalance", balance);
                    json.put("frozenAmount", frozenAmount);
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("updateAmount");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void showSystemNews(String sessionId, String subject, String msg) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("subject", subject); // 公告标题
                    json.put("msg", msg); // 公告内容
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("showSystemNews");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void forcedKickingOut(String sessionId, String message) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("message", message);
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("forcedKickingOut");
                    data.setMessage(message);
                    data.setErrCode(2025);
                    data.setCode(-1);
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void reviewOrderDelete(String sessionId, String orderNo) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("orderNo", orderNo);
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("reviewOrderDelete");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void joinRoom(String roomId, String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    client.joinRoom(roomId);
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("message", "success");
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("joinRoomResult");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void buyerCreateAppealChat(String roomId, String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("roomId", roomId);
                    json.put("type", "0");
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("buyerCreateAppealChatNotice");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void inviteCustomerService(String roomId) {
        socketIOServer.getAllClients().forEach(socketIOClient -> {
            HandshakeData handshakeData = socketIOClient.getHandshakeData();
            if ("dsbAdmin".equals(handshakeData.getSingleUrlParam("where"))) {
                ObjectNode json = objectMapper.createObjectNode();
                json.put("roomId", roomId);
                SendCmdDto data = new SendCmdDto();
                data.setCmd("noticeCustomerService");
                data.setJsonResult(json);
                socketIOClient.sendEvent("message", data);

                //redis推播事件
                Packet packet = new Packet(PacketType.MESSAGE);
                ObjectNode packetJson = objectMapper.createObjectNode();
                packetJson.put("cmd", "inviteCustomerService");
                packetJson.put("roomId", roomId);
                packet.setData(packetJson);
//                socketIOServer.getConfiguration().getStoreFactory().pubSubStore().publish(PubSubType.DISPATCH,
//                        new DispatchMessage(roomId, packet, ""));

            }
        });
    }

    public void createConfirmationBox(String roomId,String name) {
        ObjectNode json = objectMapper.createObjectNode();
        json.put("roomId", roomId);
        json.put("name", name);
        SendCmdDto data = new SendCmdDto();
        data.setCmd("createConfirmationBox");
        data.setJsonResult(json);
        BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomId);
        roomOperations.getClients().forEach(socketIOClient -> {
            socketIOClient.sendEvent("message", data);
        });
    }

    public void buyerCancel(String orderNo, String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("orderNo", orderNo);
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("buyerCancelNotice");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void buyerHadPaid(String orderNo, String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("orderNo", orderNo);
                    json.put("accelerateStatus", "Y");
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("buyerHadPaidNotice");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void buyerAppeal(String orderNo, String sessionId, String appealObj, String appealName, String appealDesc) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("orderNo", orderNo);
                    json.put("appealObj", appealObj);
                    json.put("appealName", appealName);
                    json.put("appealDesc", appealDesc);
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("buyerAppealNotice");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }

    public void showTheCloseCard(String orderNo, String sessionId) {
        if (StringUtils.isNotBlank(sessionId)) {
            UUID uuid = UUID.fromString(sessionId);
            if (uuid != null) {
                SocketIOClient client = socketIOServer.getClient(uuid);
                if (client != null) {
                    ObjectNode json = objectMapper.createObjectNode();
                    json.put("orderNo", orderNo);
                    SendCmdDto data = new SendCmdDto();
                    data.setCmd("showTheCloseCardNotice");
                    data.setJsonResult(json);
                    client.sendEvent("message", data);
                }
            }
        }
    }
}
