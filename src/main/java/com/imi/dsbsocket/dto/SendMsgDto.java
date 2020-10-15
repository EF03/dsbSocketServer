package com.imi.dsbsocket.dto;

import java.io.Serializable;
import java.util.Date;

public class SendMsgDto implements Serializable {

    private String message;
    private String roomId;
    private String token;
    private String isImageMessage;
    private long date;
    private String role;
    private String name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsImageMessage() {
        return isImageMessage;
    }

    public void setIsImageMessage(String isImageMessage) {
        this.isImageMessage = isImageMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SendMsgDto() {

    }

    public SendMsgDto(String message, String roomId, String token, String isImageMessage,
                      String role, String name, long date) {
        this.message = message;
        this.roomId = roomId;
        this.token = token;
        this.isImageMessage = isImageMessage;
        this.role = role;
        this.name = name;
        this.date = date;
    }
}
