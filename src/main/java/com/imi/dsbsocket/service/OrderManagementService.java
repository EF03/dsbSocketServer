package com.imi.dsbsocket.service;

import com.imi.dsbsocket.repository.DsbOrderRepository;
import com.imi.dsbsocket.repository.DsbPayeeOnlineRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OrderManagementService {

    private final DsbOrderRepository dsbOrderRepository;
    private final DsbPayeeOnlineRepository dsbPayeeOnlineRepository;

    public OrderManagementService(DsbOrderRepository dsbOrderRepository, DsbPayeeOnlineRepository dsbPayeeOnlineRepository) {
        this.dsbOrderRepository = dsbOrderRepository;
        this.dsbPayeeOnlineRepository = dsbPayeeOnlineRepository;
    }

    public List<String> noticeOrder(String orderNo) {
        if (StringUtils.isNotBlank(orderNo)) {
            return dsbPayeeOnlineRepository.noticeOrder(orderNo);
        } else {
            return dsbPayeeOnlineRepository.noticeOrder();
        }
    }

    public Integer findOrderByNo(String receivedOrderNo) {
        return dsbOrderRepository.findOrderByNo(receivedOrderNo);
    }

    public List<Map<String,String>> getOrderPendingCounts(String payeeId) {
        return dsbOrderRepository.getOrderPendingCounts(payeeId);
    }

    public int getReviewOrderPendingCounts(int payeeId) {
        return dsbOrderRepository.getReviewOrderPendingCounts(payeeId);
    }
}
