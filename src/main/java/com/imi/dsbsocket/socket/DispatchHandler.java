package com.imi.dsbsocket.socket;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.pubsub.DispatchMessage;
import com.corundumstudio.socketio.store.pubsub.PubSubListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.imi.dsbsocket.dto.SendCmdDto;
import com.imi.dsbsocket.dto.SendMsgDto;
import com.imi.dsbsocket.service.DsbSocketMsgService;
import org.springframework.stereotype.Component;

@Component
public class DispatchHandler implements PubSubListener<DispatchMessage> {

    private final SocketIOServer socketIOServer;
    private final DsbSocketMsgService dsbSocketMsgService;
    private final ObjectMapper objectMapper;

    public DispatchHandler(SocketIOServer socketIOServer, DsbSocketMsgService dsbSocketMsgService, ObjectMapper objectMapper) {
        this.socketIOServer = socketIOServer;
        this.dsbSocketMsgService = dsbSocketMsgService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(DispatchMessage dispatchMessage) {
        ObjectNode packetJson = objectMapper.valueToTree(dispatchMessage.getPacket().getData());
        String cmd = packetJson.get("cmd").asText();
        if ("sendMsg".equalsIgnoreCase(cmd)) {
            String message = packetJson.get("message").asText();
            String roomId = packetJson.get("roomId").asText();
            String token = packetJson.get("token").asText();
            String isImageMessage = packetJson.get("isImageMessage").asText();
            String role = packetJson.get("role").asText();
            String name = packetJson.get("name").asText();
            long date = packetJson.get("date").asLong();
            SendMsgDto sendMsgDto=new SendMsgDto(message,roomId, token,isImageMessage,role, name,date);
            //redis推播
            BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomId);
            roomOperations.getClients().forEach(socketIOClient -> {
                socketIOClient.sendEvent("receiveMsg", sendMsgDto);
            });
        } else if("inviteCustomerService".equalsIgnoreCase(cmd)){
            String roomId = packetJson.get("roomId").asText();
            ObjectNode json = objectMapper.createObjectNode();
            json.put("roomId", roomId);
            SendCmdDto data = new SendCmdDto();
            data.setCmd("noticeCustomerService");
            data.setJsonResult(json);
            //redis推播
            BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomId);
            roomOperations.getClients().forEach(socketIOClient -> {
                socketIOClient.sendEvent("message", data);
            });

        }

    }
}
