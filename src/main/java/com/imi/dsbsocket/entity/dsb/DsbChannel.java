package com.imi.dsbsocket.entity.dsb;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "DSB_CHANNEL")
public class DsbChannel {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CHANNEL_ID")
    private Integer channelId;

    @Column(name = "CHANNEL_NAME")
    private String channelName;

    @Column(name = "PC_ICON_PATH")
    private String pcIconPath;

    @Column(name = "WAP_ICON_PATH")
    private String wapIconPath;

    @Column(name = "PAY_TYPE")
    private String payType;

}
