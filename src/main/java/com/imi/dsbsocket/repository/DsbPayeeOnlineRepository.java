package com.imi.dsbsocket.repository;

import com.imi.dsbsocket.entity.dsb.DsbPayeeOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DsbPayeeOnlineRepository extends JpaRepository<DsbPayeeOnline, Integer> {
    @Query(value = "SELECT DISTINCT B.SESSION_ID FROM DSB_PAYEE_QRCODE_CONFIG A, DSB_PAYEE_ONLINE B, DSB_ORDER C " +
            " WHERE A.PAYEE_ID = B.PAYEE_ID  AND A.PAYEE_STATUS = '3'   AND B.SESSION_ID IS NOT NULL " +
            " AND A.CHANNEL_ID = C.CHANNEL_ID ", nativeQuery = true)
    List<String> noticeOrder();

    @Query(value = "SELECT  DISTINCT B.SESSION_ID FROM DSB_PAYEE_QRCODE_CONFIG A, DSB_PAYEE_ONLINE B, DSB_ORDER C " +
            " WHERE A.PAYEE_ID = B.PAYEE_ID  AND A.PAYEE_STATUS = '3'   AND B.SESSION_ID IS NOT NULL " +
            " AND A.CHANNEL_ID = C.CHANNEL_ID AND C.RECEIVED_ORDER_NO =:orderNo  ", nativeQuery = true)
    List<String> noticeOrder(@Param("orderNo") String orderNo);

    DsbPayeeOnline findByToken(String token);

    DsbPayeeOnline findByPayeeId(Integer payeeId);

    @Query("update DsbPayeeOnline d set d.sessionId = :sessionId WHERE d.token = :token")
    @Modifying
    void updateOnlineSessionId(@Param("sessionId") String sessionId, @Param("token") String token);
}
