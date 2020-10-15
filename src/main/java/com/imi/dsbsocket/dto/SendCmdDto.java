package com.imi.dsbsocket.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SendCmdDto implements Serializable {

    private String cmd;
    private String token;
    private String message;
    private String ip;
    private int code;
    private int errCode;
    private String executeCmd;
    private JsonNode jsonResult;
    private String sessionId;
    private String roomId;
    private String where;
    private Integer payeeId;
    private String orderNo;

}
