package com.imi.dsbsocket.repository;

import com.imi.dsbsocket.entity.dsb.DsbSocketMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DsbSocketMsgRepository extends JpaRepository<DsbSocketMsg, Integer> {

}
