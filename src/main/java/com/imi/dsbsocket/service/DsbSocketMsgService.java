package com.imi.dsbsocket.service;

import com.imi.dsbsocket.dto.SendMsgDto;
import com.imi.dsbsocket.entity.DsbSocketMsg;
import com.imi.dsbsocket.entity.DsbSocketRoom;
import com.imi.dsbsocket.repository.DsbSocketMsgRepository;
import com.imi.dsbsocket.socket.SocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class DsbSocketMsgService {

    private final DsbSocketMsgRepository dsbSocketMsgRepository;
    private final DsbSocketRoomService dsbSocketRoomService;
    private Logger log = LoggerFactory.getLogger(SocketHandler.class);

    public DsbSocketMsgService(DsbSocketMsgRepository dsbSocketMsgRepository, DsbSocketRoomService dsbSocketRoomService) {
        this.dsbSocketMsgRepository = dsbSocketMsgRepository;
        this.dsbSocketRoomService = dsbSocketRoomService;
    }

    @Transactional
    public DsbSocketMsg insertDsbSocketMsgData(SendMsgDto sendMsgDto, String roomId) {
        log.info("新增歷史訊息 roomId = " + roomId);
        DsbSocketRoom dsbSocketRoom = dsbSocketRoomService.findByRoomId(roomId); //依照roomId取出該筆訂單相關資訊
        DsbSocketMsg dsbSocketMsg = new DsbSocketMsg();
        dsbSocketMsg.setMessage(sendMsgDto.getMessage());
        dsbSocketMsg.setIsImage(sendMsgDto.getIsImageMessage());
        dsbSocketMsg.setName(sendMsgDto.getName());
        dsbSocketMsg.setRole(sendMsgDto.getRole());
        Date createDate = new Date(sendMsgDto.getDate()); //將时间戳轉為Date
        dsbSocketMsg.setCreateDate(createDate);
        dsbSocketMsg.setDsbSocketRoom(dsbSocketRoom);
        return dsbSocketMsgRepository.save(dsbSocketMsg);
    }
}
