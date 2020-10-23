package com.imi.dsbsocket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

/**
 * @author Ron
 * @date 2020/10/23 下午 04:25
 */
//@Entity
@Data
@Table(name = "IG_BACCARAT_DECK_RECORD")
public class IgBaccaratDeckRecord {


    @Column(name = "ROOM_ID")
    private Integer roomId;
    @Column(name = "TABLE_ID")
    private Integer tableId;
    @Column(name = "GAME_NO")
    private String gameNo;
    @Column(name = "WINNER")
    private Integer winner;
    @Column(name = "IS_BANKER_PAIR")
    private Character isBankerPair;
    @Column(name = "IS_PLAYER_PAIR")
    private Character isPlayerPair;
    @Column(name = "PLAYER_FIRST_CARD")
    private Integer playerFirstCard;
    @Column(name = "PLAYER_SECOND_CARD")
    private Integer playerSecondCard;
    @Column(name = "PLAYER_THIRD_CARD")
    private Integer playerThirdCard;
    @Column(name = "BANKER_FIRST_CARD")
    private Integer bankerFirstCard;
    @Column(name = "BANKER_SECOND_CARD")
    private Integer bankerSecondCard;
    @Column(name = "BANKER_THIRD_CARD")
    private Integer bankerThirdCard;


}
