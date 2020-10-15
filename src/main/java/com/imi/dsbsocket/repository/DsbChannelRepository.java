package com.imi.dsbsocket.repository;

import com.imi.dsbsocket.entity.DsbChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DsbChannelRepository extends JpaRepository<DsbChannel, Integer> {

}
