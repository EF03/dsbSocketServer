package com.imi.dsbsocket.entity.dsb;

import com.imi.dsbsocket.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DSB_SOCKET_MSG")
public class DsbSocketMsg extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Lob
    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "IS_IMAGE", length = 1)
    private String isImage;

    @Column(name = "ROLE", length = 1)
    private String role;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private DsbSocketRoom dsbSocketRoom;
};
