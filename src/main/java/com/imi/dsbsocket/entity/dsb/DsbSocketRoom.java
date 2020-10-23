package com.imi.dsbsocket.entity.dsb;

import com.imi.dsbsocket.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "DSB_SOCKET_ROOM")
public class DsbSocketRoom extends BaseEntity {
    @Id
    @Column(name = "ROOM_ID", length = 50)
    private String roomId;
    @Column(name = "TYPE", length = 1)
    private String type;
    @Column(name = "STATUS", length = 1)
    private String status;
    @OneToMany(mappedBy = "dsbSocketRoom")
    private List<DsbSocketMsg> dsbSocketMsgList;
}
