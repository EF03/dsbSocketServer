package com.imi.dsbsocket.enums;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ron
 * @date 2020/10/22 上午 11:11
 */
public enum PokerCard {

    SPADE_2(1, 2),
    SPADE_3(2, 3),
    SPADE_4(3, 4),
    SPADE_5(4, 5),
    SPADE_6(5, 6),
    SPADE_7(6, 7),
    SPADE_8(7, 8),
    SPADE_9(8, 9),
    SPADE_10(9, 10),
    SPADE_J(10, 11),
    SPADE_Q(11, 12),
    SPADE_K(12, 13),
    SPADE_A(13, 1),
    HEART_2(14, 2),
    HEART_3(15, 3),
    HEART_4(16, 4),
    HEART_5(17, 5),
    HEART_6(18, 6),
    HEART_7(19, 7),
    HEART_8(20, 8),
    HEART_9(21, 9),
    HEART_10(22, 10),
    HEART_J(23, 11),
    HEART_Q(24, 12),
    HEART_K(25, 13),
    HEART_A(26, 1),
    CLUB_2(27, 2),
    CLUB_3(28, 3),
    CLUB_4(29, 4),
    CLUB_5(30, 5),
    CLUB_6(31, 6),
    CLUB_7(32, 7),
    CLUB_8(33, 8),
    CLUB_9(34, 9),
    CLUB_10(35, 10),
    CLUB_J(36, 11),
    CLUB_Q(37, 12),
    CLUB_K(38, 13),
    CLUB_A(39, 1),
    DIAMOND_2(40, 2),
    DIAMOND_3(41, 3),
    DIAMOND_4(42, 4),
    DIAMOND_5(43, 5),
    DIAMOND_6(44, 6),
    DIAMOND_7(45, 7),
    DIAMOND_8(46, 8),
    DIAMOND_9(47, 9),
    DIAMOND_10(48, 10),
    DIAMOND_J(49, 11),
    DIAMOND_Q(50, 12),
    DIAMOND_K(51, 13),
    DIAMOND_A(52, 1),
    RED_JOKER(53, 0),
    BLACK_JOKER(54, 0);

    private int id;
    private int value;

    PokerCard(int id, int value) {
        this.id = id;
        this.value = value;
    }

    /**
     * 取單一牌
     *
     * @param id
     * @return
     */
    public static PokerCard getInstanceOf(int id) {
        for (PokerCard oo : PokerCard.values()) {
            if (oo.id == id) {
                return oo;
            }
        }
        return null;
    }

    /**
     * 取得撲克牌list
     *
     * @param deckNumber 幾副牌
     * @param isJoker    是否含鬼牌
     * @return
     */
    public static List<PokerCard> getPokerCardsList(int deckNumber, boolean isJoker) {
        int cnt = isJoker ? 54 : 52;
        ArrayList<PokerCard> poker = new ArrayList<>(cnt * deckNumber);
        PokerCard[] pokerCards;
        PokerCard[] allCards = PokerCard.values();
        if (isJoker) {
            pokerCards = allCards;
        } else {
            pokerCards = new PokerCard[allCards.length - 2];
            System.arraycopy(allCards, 0, pokerCards, 0, pokerCards.length);
        }
        for (int i = 0; i < deckNumber; i++) {
            poker.addAll(Arrays.asList(pokerCards));
        }
        return poker;
    }

    /**
     * 得到 切牌,洗牌後的 牌組
     */
    public static Stack<PokerCard> shufflingCards(int deckNum, boolean hasJoker, boolean hasCut, boolean hasShuffle) {
        // 初始牌
        List<PokerCard> cards = getPokerCardsList(deckNum, hasJoker);
        // 切牌
        if (hasCut) {
            cutTheDeck(cards);
        }
        //洗牌
        if (hasShuffle) {
            Collections.shuffle(cards);
        }
        Stack<PokerCard> cardStack = new Stack<>();
        cardStack.addAll(cards);
        return cardStack;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    /**
     * 切牌
     */
    private static void cutTheDeck(List<PokerCard> cards) {
        Random random = new Random();
        // 只切一半以內
        int cutNum = random.nextInt(cards.size() / 2);
        cards.subList(0, cutNum).clear();
    }

    public static List<Integer> switchPokerCardToId(List<PokerCard> cards){
        return cards.stream().map(PokerCard::getId)
                .collect(Collectors.toList());

    }


//    public static void main(String[] args) {
//        List<PokerCard> pokerCards = getPokerCardsList(2, false);
//        System.out.println(pokerCards);
//        for(){
//
//        }
//        for (PokerCards pc : PokerCards.values()) {
//            System.out.println(排序 :  + pc.id + 數值 :  + pc.value);
//        }
//    }
}
