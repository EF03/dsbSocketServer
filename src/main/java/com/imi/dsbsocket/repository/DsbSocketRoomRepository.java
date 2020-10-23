package com.imi.dsbsocket.repository;

import com.imi.dsbsocket.entity.dsb.DsbSocketRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DsbSocketRoomRepository extends JpaRepository<DsbSocketRoom, Integer> {
    DsbSocketRoom findByRoomId(String roomId);
}
