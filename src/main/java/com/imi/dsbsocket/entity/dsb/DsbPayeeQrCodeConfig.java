package com.imi.dsbsocket.entity.dsb;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Data
@Table(name = "DSB_PAYEE_QRCODE_CONFIG")
public class DsbPayeeQrCodeConfig {

    @Id
    @Column(name = "QRCODE_ID")
    private Integer qrcodeId;

    @Column(name = "PAYEE_ID")
    private Integer payeeId;

    @Column(name = "CHANNEL_ID")
    private Integer channelId;

    @Column(name = "PAYEE_NAME")
    private String payeeName;

    @Column(name = "PAYEE_NICKNAME")
    private String payeeNickname;

    @Column(name = "PAYEE_ACCOUNT")
    private String payeeAccount;

    @Column(name = "PAYEE_QRCODE_URL")
    private String payeeQrcodeUrl;

    @Column(name = "CITY_ID")
    private String cityId;

    @Column(name = "PAYEE_STATUS")
    private String payeeStatus;

    @Column(name = "TODAY_PAYEE_AMOUNT")
    private Integer todayPayeeAmount;

    @Column(name = "TODAY_PAYEE_COUNT")
    private Integer todayPayeeCount;

    @Column(name = "TOTAL_PAYEE_AMOUNT")
    private Integer totalPayeeAmount;

    @Column(name = "TOTAL_PAYEE_COUNT")
    private Integer totalPayeeCount;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "CREATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    private Date updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "IS_DEFAULT_QR")
    private String isDefaultQr;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "PROVINCE_ID")
    private Integer provinceId;

}
