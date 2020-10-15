package com.imi.dsbsocket.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "DSB_SOCKET_DOMAIN")
public class DsbSocketDomain {

    @Id
    @Column(name = "DOMAIN", length = 50)
    private String domain;
    @Column(name = "CONN_COUNT")
    private Integer connCount;

}
