package com.imi.dsbsocket.entity.model;

import com.imi.dsbsocket.enums.PokerCard;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Stack;

/**
 * @author Ron
 * @date 2020/10/20 上午 11:46
 */
@Getter@Setter
public class CardTable {


    private int tableId;
    private int roomId;
    private String gameNum;
    /**
     * 剩馀牌堆
     */
    private Stack<PokerCard> cardStack;

    /**
     * 已发牌数
     */
    private int sendNum;

    /**
     * 庄家得牌
     */
    List<Integer> bankerCards;

    /**
     * 闲家得牌
     */
    List<Integer> playerCards;

    /**
     * 1-閒贏 ,2-莊贏 ,3-和局 ,4-閒對 ,5-莊對 ,6-閒雙 ,7-閒單 ,8-莊雙 ,9-莊單 ,10-大 ,11-小
     */
    List<Integer> result;

    /**
     * 状态 1-下注中/2-开牌中/3-派奖中/4-准备中/5-洗牌中
     */
    private int status;

    /**
     * 倒数秒数
     */
    private int countSeconds;

    /**
     * 玩家人数
     */
    private int accessMember;

    /**
     * 時間
     */
    private int bettingTime;
    private int openingTime;
    private int awardingTime;
    private int preparingTime;
    private int shufflingTime;
    private String roomName;
    private String tablesName;
    private int minBet;
    private int maxBet;
    private String chips;

    /**
     * 賠率以及下注上限
     */
    private Double bankerOdds;
    private Double bankerPairOdds;
    private Double playerOdds;
    private Double playerPairOdds;
    private Double tieOdds;
    private int bankerMaxBet;
    private int bankerPairMaxBet;
    private int playerMaxBet;
    private int playerPairMaxBet;
    private int tieMaxBet;


    public CardTable() {
    }


    @Override
    public String toString() {
        return "CardTable{" +
                "tableId=" + tableId +
                ", roomId=" + roomId +
                ", status=" + status +
                ", countSeconds=" + countSeconds +
                ", sendNum=" + sendNum +
                ", bankerCards=" + bankerCards +
                ", playerCards=" + playerCards +
                ", result=" + result +
                '}';
    }
}
