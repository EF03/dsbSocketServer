package com.imi.dsbsocket.repository;

import com.imi.dsbsocket.entity.DsbOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DsbOrderRepository extends JpaRepository<DsbOrder, Integer> {


    @Query(value = "SELECT a.RECEIVED_PAYEE_ID FROM DSB_ORDER a, DSB_CHANNEL b WHERE  a.CHANNEL_ID = b.CHANNEL_ID AND  a.RECEIVED_ORDER_NO=:receivedOrderNo", nativeQuery = true)
    Integer findOrderByNo(@Param("receivedOrderNo") String receivedOrderNo);

    @Query(value = "SELECT DISTINCT a.PAYEE_ID, a.CHANNEL_ID,(SELECT COUNT(*) FROM DSB_ORDER Z WHERE Z.CHANNEL_ID = A.CHANNEL_ID AND  Z.RECEIVED_STATUS = '0') COUNT " +
            " FROM DSB_PAYEE_QRCODE_CONFIG a, DSB_PAYEE_ONLINE b " +
            " WHERE a.PAYEE_ID = b.PAYEE_ID " +
            " AND a.PAYEE_STATUS = '3' " +
            " AND b.SESSION_ID IS NOT NULL " +
            " AND a.PAYEE_ID =:payeeId", nativeQuery = true)
    List<Map<String,String>> getOrderPendingCounts(@Param("payeeId") String payeeId);

    @Query(value = "SELECT COUNT(*) FROM DSB_ORDER WHERE RECEIVED_PAYEE_ID =:payeeId AND VERIFY_STATUS = '0' AND (APPEAL_OBJ ='2' OR APPEAL_STATUS ='0') ", nativeQuery = true)
    int getReviewOrderPendingCounts(@Param("payeeId") int payeeId);

}
