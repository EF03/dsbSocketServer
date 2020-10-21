package com.imi.dsbsocket.entity;

import com.imi.dsbsocket.entity.base.Card;
import com.imi.dsbsocket.enums.BaccaratStatus;

import java.util.List;
import java.util.Stack;

/**
 * @author Ron
 * @date 2020/10/20 上午 11:46
 */
public class CardTable {

    private int tableId;
    private int roomId;
    /**
     * 剩馀牌堆
     */
    private Stack<Card> cardStack;

    /**
     * 已发牌数
     */
    private int sendNum;

    /**
     * 庄家得牌
     */
    List<Card> bankerCards;

    /**
     * 闲家得牌
     */
    List<Card> playerCards;

    List<Integer> result;

    /**
     * 状态 1-下注中/2-开牌中/3-派奖中/4-准备中/5-洗牌中
     */
    private BaccaratStatus status;

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

    public Stack<Card> getCardStack() {
        return cardStack;
    }

    public void setCardStack(Stack<Card> cardStack) {
        this.cardStack = cardStack;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    public List<Card> getBankerCards() {
        return bankerCards;
    }

    public void setBankerCards(List<Card> bankerCards) {
        this.bankerCards = bankerCards;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public BaccaratStatus getStatus() {
        return status;
    }

    public void setStatus(BaccaratStatus status) {
        this.status = status;
    }

    public int getCountSeconds() {
        return countSeconds;
    }

    public void setCountSeconds(int countSeconds) {
        this.countSeconds = countSeconds;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBettingTime() {
        return bettingTime;
    }

    public void setBettingTime(int bettingTime) {
        this.bettingTime = bettingTime;
    }

    public int getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(int openingTime) {
        this.openingTime = openingTime;
    }

    public int getAwardingTime() {
        return awardingTime;
    }

    public void setAwardingTime(int awardingTime) {
        this.awardingTime = awardingTime;
    }

    public int getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(int preparingTime) {
        this.preparingTime = preparingTime;
    }

    public int getShufflingTime() {
        return shufflingTime;
    }

    public void setShufflingTime(int shufflingTime) {
        this.shufflingTime = shufflingTime;
    }

    public int getAccessMember() {
        return accessMember;
    }

    public void setAccessMember(int accessMember) {
        this.accessMember = accessMember;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getTablesName() {
        return tablesName;
    }

    public void setTablesName(String tablesName) {
        this.tablesName = tablesName;
    }

    public int getMinBet() {
        return minBet;
    }

    public void setMinBet(int minBet) {
        this.minBet = minBet;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int maxBet) {
        this.maxBet = maxBet;
    }

    public String getChips() {
        return chips;
    }

    public void setChips(String chips) {
        this.chips = chips;
    }

    public Double getBankerOdds() {
        return bankerOdds;
    }

    public void setBankerOdds(Double bankerOdds) {
        this.bankerOdds = bankerOdds;
    }

    public Double getBankerPairOdds() {
        return bankerPairOdds;
    }

    public void setBankerPairOdds(Double bankerPairOdds) {
        this.bankerPairOdds = bankerPairOdds;
    }

    public Double getPlayerOdds() {
        return playerOdds;
    }

    public void setPlayerOdds(Double playerOdds) {
        this.playerOdds = playerOdds;
    }

    public Double getPlayerPairOdds() {
        return playerPairOdds;
    }

    public void setPlayerPairOdds(Double playerPairOdds) {
        this.playerPairOdds = playerPairOdds;
    }

    public Double getTieOdds() {
        return tieOdds;
    }

    public void setTieOdds(Double tieOdds) {
        this.tieOdds = tieOdds;
    }

    public int getBankerMaxBet() {
        return bankerMaxBet;
    }

    public void setBankerMaxBet(int bankerMaxBet) {
        this.bankerMaxBet = bankerMaxBet;
    }

    public int getBankerPairMaxBet() {
        return bankerPairMaxBet;
    }

    public void setBankerPairMaxBet(int bankerPairMaxBet) {
        this.bankerPairMaxBet = bankerPairMaxBet;
    }

    public int getPlayerMaxBet() {
        return playerMaxBet;
    }

    public void setPlayerMaxBet(int playerMaxBet) {
        this.playerMaxBet = playerMaxBet;
    }

    public int getPlayerPairMaxBet() {
        return playerPairMaxBet;
    }

    public void setPlayerPairMaxBet(int playerPairMaxBet) {
        this.playerPairMaxBet = playerPairMaxBet;
    }

    public int getTieMaxBet() {
        return tieMaxBet;
    }

    public void setTieMaxBet(int tieMaxBet) {
        this.tieMaxBet = tieMaxBet;
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
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
