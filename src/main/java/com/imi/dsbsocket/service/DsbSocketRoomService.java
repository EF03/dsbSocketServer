package com.imi.dsbsocket.service;

import com.imi.dsbsocket.entity.DsbSocketRoom;
import com.imi.dsbsocket.repository.DsbSocketRoomRepository;
import org.springframework.stereotype.Service;


@Service
public class DsbSocketRoomService {

    private final DsbSocketRoomRepository dsbSocketRoomRepository;

    public DsbSocketRoomService(DsbSocketRoomRepository dsbSocketRoomRepository) {
        this.dsbSocketRoomRepository = dsbSocketRoomRepository;
    }

    public DsbSocketRoom findByRoomId(String roomId) {
        return dsbSocketRoomRepository.findByRoomId(roomId);
    }
}
