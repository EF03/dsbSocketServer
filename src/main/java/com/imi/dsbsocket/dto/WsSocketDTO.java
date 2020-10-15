package com.imi.dsbsocket.dto;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;

/**
 * Created by zonvan on 2019/11/11.
 */
public class WsSocketDTO {

    private DecodedJWT jwt;
    private SocketIOClient client;
    private SendCmdDto data;
    private String sessionId;
    private AckRequest ackRequest;

    public DecodedJWT getJwt() {
        return jwt;
    }

    public void setJwt(DecodedJWT jwt) {
        this.jwt = jwt;
    }

    public SocketIOClient getClient() {
        return client;
    }

    public void setClient(SocketIOClient client) {
        this.client = client;
    }

    public SendCmdDto getData() {
        return data;
    }

    public void setData(SendCmdDto data) {
        this.data = data;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public AckRequest getAckRequest() {
        return ackRequest;
    }

    public void setAckRequest(AckRequest ackRequest) {
        this.ackRequest = ackRequest;
    }
}
