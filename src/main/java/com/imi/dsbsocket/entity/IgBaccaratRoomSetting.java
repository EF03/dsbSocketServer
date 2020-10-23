package com.imi.dsbsocket.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Ron
 * @date 2020/10/23 下午 03:47
 */
@Entity
@Data
@Table(name = "IG_BACCARAT_ROOM_SETTING")
public class IgBaccaratRoomSetting {

    @Id
    @Column(name = "ROOM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;
    // 房間名稱
    @Column(name = "ROOM_NAME")
    private String roomName;

    // 最小投注額
    @Column(name = "MIN_BET")
    private Integer minBet;

    // 最大投注額
    @Column(name = "MAX_BET")
    private Integer maxBet;

    // 籌碼陣列
    @Column(name = "CHIPS")
    private String chips;

    // 投注時間(秒)
//    @Builder.Default
    @Column(name = "BETTING_TIME")
    private Integer bettingTime = 20;

    // 準備時間(秒)
    @Column(name = "PREPARING_TIME")
    private Integer preparingTime;

    // 開牌時間(秒)
    @Column(name = "OPENING_TIME")
    private Integer openingTime;

    // 派獎時間(秒)
    @Column(name = "AWARDING_TIME")
    private Integer awardingTime;

    // 洗牌時間(秒)
    @Column(name = "SHUFFLING_TIME")
    private Integer shufflingTime;

    // 桌數
    @Column(name = "TABLES_NO")
    private Integer tablesNo;

    // 桌名
    @Column(name = "TABLES_NAME")
    private Integer tablesName;

    // 可接受上桌人數
    @Column(name = "TABLE_ACCEPT_PLAYER_NUM")
    private Integer tableAcceptPlayerNum;

    // 莊賠率
    @Column(name = "BANKER_ODDS",precision=10, scale=2)
    private Double bankerOdds;

    // 莊對賠率
    @Column(name = "BANKER_PAIR_ODDS")
    private Double bankerPairOdds;

    // 閑賠率
    @Column(name = "PLAYER_ODDS")
    private Double playerOdds;

    // 閑對賠率
    @Column(name = "PLAYER_PAIR_ODDS")
    private Double playerPairOdds;

    // 和賠率
    @Column(name = "TIE_ODDS")
    private Double tieOdds;

    // 莊最大投注額
    @Column(name = "BANKER_MAX_BET")
    private Integer bankerMaxBet;

    // 莊對最大投注額
    @Column(name = "BANKER_PAIR_MAX_BET")
    private Integer bankerPairMaxBet;

    // 閑最大投注額
    @Column(name = "PLAYER_MAX_BET")
    private Integer playerMaxBet;

    // 閑對最大投注額
    @Column(name = "PLAYER_PAIR_MAX_BET")
    private Integer playerPairMaxBet;

    // 和最大投注額
    @Column(name = "TIE_MAX_BET")
    private Integer tieMaxBet;

    // 最少切牌數
    @Column(name = "CUT_CARDS_MIN")
//    @Builder.Default
    private Integer cutCardsMin = 80;

    // 最大切牌數
    @Column(name = "CUT_CARDS_MAX")
//    @Builder.Default
    private Integer cutCardsMax = 180;

    // 准入最低金額
    @Column(name = "ACCEPT_IN_MIN")
    private Integer acceptInMin;


}
