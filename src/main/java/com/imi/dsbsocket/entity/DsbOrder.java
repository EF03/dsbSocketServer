package com.imi.dsbsocket.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@Table(name = "DSB_ORDER")
public class DsbOrder {

    @Id
    @Column(name = "RECEIVED_ORDER_NO")
    private String receivedOrderNo;

    @Column(name = "MERCHANT_NO")
    private String merchantNo;

    @Column(name = "SOURCE_ORDER_NO")
    private String sourceOrderNo;

    @Column(name = "CHANNEL_ID")
    private Integer channelId;

    @Column(name = "ORDER_AMOUNT")
    private BigDecimal orderAmount;

    @Column(name = "REAL_AMOUNT")
    private BigDecimal realAmount;

    @Column(name = "CLIENT_IP")
    private String clientIp;

    @Column(name = "CLIENT_IP_AREA")
    private String clientIpArea;

    @Column(name = "RECEIVED_STATUS")
    private String receivedStatus;

    @Column(name = "RECEIVED_ORDER_TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date receivedOrderTime;

    @Column(name = "VERIFY_ORDER_TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date verifyOrderTime;

    @Column(name = "VERIFY_STATUS")
    private String verifyStatus;

    @Column(name = "CREATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date updateDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "VERIFY_ROLE")
    private String verifyRole;

    @Column(name = "QRCODE_ID")
    private Integer qrcodeId;

    @Column(name = "PAYEE_NAME")
    private String payeeName;

    @Column(name = "PAYEE_NICKNAME")
    private String payeeNickname;

    @Column(name = "RECEIVED_PAYEE_ID")
    private Integer receivedPayeeId;

    @Column(name = "ACCELERATE_SUBMIT_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date accelerateSubmitDate;

    @Column(name = "IS_RECEIVED_ORDER_OUT_OF_DATE")
    private String isReceivedOrderOutOfDate;

    @Column(name = "NOTIFY_STATUS")
    private String notifyStatus;

    @Column(name = "NOTIFY_ORDER_TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date notifyOrderTime;

    @Column(name = "LOCK_USER_ID")
    private String lockUserId;

    @Column(name = "LOCK_USER_NAME")
    private String lockUserName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    @Column(name = "LOCK_TIME")
    private Date lockTime;

    @Column(name = "CLIENT_TERMINAL")
    private String clientTerminal;

    @Column(name = "NOTIFY_COUNT")
    private Integer notifyCount;

    @Column(name = "NOTIFY_URL")
    private String notifyUrl;

    @Column(name = "NOTIFY_CONTENT")
    private String notifyContent;

    @Column(name = "MERCHANT_ID")
    private Integer merchantId;

    @Column(name = "PAYEE_ACCOUNT")
    private String payeeAccount;

    private String channelName;

    private String loginAccount;

    private String isAppeal;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "REWARD_AMOUNT")
    private Double rewardAmount;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "ORDER_TIMEOUT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date orderTimeout;

    @Column(name = "RECHARGE_TIMEOUT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date rechargeTimeout;

    @Column(name = "MER_EXCHANGE_RATE")
    private Double merExchangeRate;

    @Column(name = "ORDER_FEES")
    private Double orderFees;

    @Column(name = "APPEAL_OBJ")
    private String appealObj;

    @Column(name = "APPEAL_STATUS")
    private String appealStatus;

    @Column(name = "QRCODE_URL")
    private String qrcodeUrl;


}
