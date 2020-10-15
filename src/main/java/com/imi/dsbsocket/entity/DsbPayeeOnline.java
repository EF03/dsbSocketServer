package com.imi.dsbsocket.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Data
@Table(name = "DSB_PAYEE_ONLINE")
public class DsbPayeeOnline {

    @Id
    @Column(name = "PAYEE_ID")
    private Integer payeeId;

    @Column(name = "LOGIN_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date loginDate;

    @Column(name = "LOGIN_IP")
    private String loginIp;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CREATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date updateDate;


}
