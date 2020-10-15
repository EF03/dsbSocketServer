package com.imi.dsbsocket.service;

import com.imi.dsbsocket.entity.DsbPayeeOnline;
import com.imi.dsbsocket.repository.DsbPayeeOnlineRepository;
import org.springframework.stereotype.Service;


@Service
public class AccountManagementService {

    private final DsbPayeeOnlineRepository dsbPayeeOnlineRepository;

    public AccountManagementService(DsbPayeeOnlineRepository dsbPayeeOnlineRepository) {
        this.dsbPayeeOnlineRepository = dsbPayeeOnlineRepository;
    }

    public String findSessionIdByToken(String token) {
        DsbPayeeOnline payeeOnline = dsbPayeeOnlineRepository.findByToken(token);
        if (payeeOnline != null) {
            return payeeOnline.getSessionId();
        } else {
            return "";
        }
    }

    public DsbPayeeOnline findPayeeOnlineById(int payeeId) {
        return dsbPayeeOnlineRepository.findByPayeeId(payeeId);
    }

    public void updateOnlineSessionId(String token, String sessionId) {
        dsbPayeeOnlineRepository.updateOnlineSessionId(sessionId, token);
    }
}
