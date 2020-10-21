package com.imi.dsbsocket.util;

import com.imi.dsbsocket.entity.base.Card;
import com.imi.dsbsocket.entity.CardTable;
import com.imi.dsbsocket.enums.BaccaratStatus;
import lombok.extern.slf4j.Slf4j;


import java.util.*;


/**
 * @author Ron
 * @date 2020/10/20 下午 12:42
 */
@Slf4j
public class BaccaratUtil {
    public final static int PACK_NUM = 8;
    public final static int CARDS_NUM = 52;
    public final static int LIMIT_CARDS = 7;


    /**
     * 更新狀態 當 0 秒時 更新狀態
     * 状态 1-下注中/2-开牌中/3-派奖中/4-准备中/5-洗牌中
     */
    public static void switchStatusAndDo(CardTable cardTable) {
        switch (cardTable.getStatus().getBaccaratCode()) {
            case 1:
                // todo 交易邏輯
                cardTable.setStatus(BaccaratStatus.OPENING_TIME);
                cardTable.setCountSeconds(cardTable.getOpeningTime());
                break;
            case 2:
                // todo 傳給 各個使用者 結果
                cardTable.setStatus(BaccaratStatus.AWARDING_TIME);
                cardTable.setCountSeconds(cardTable.getAwardingTime());
                break;
            case 3:
                // todo 垃圾時間
                cardTable.setStatus(BaccaratStatus.PREPARING_TIME);
                cardTable.setCountSeconds(cardTable.getPreparingTime());
                break;
            case 4:
                if (cardTable.getSendNum() < BaccaratUtil.LIMIT_CARDS) {
                    // todo 洗牌

                    Stack<Card> newCards = shufflingCard();
                    cardTable.setCardStack(newCards);
                    cardTable.setSendNum((PACK_NUM * CARDS_NUM) - newCards.size());
                    cardTable.setStatus(BaccaratStatus.SHUFFLING_TIME);
                    cardTable.setCountSeconds(cardTable.getShufflingTime());
                } else {
                    // todo 傳出發牌結果
                    playBaccarat(cardTable);
                    cardTable.setStatus(BaccaratStatus.BETTING_TIME);
                    cardTable.setCountSeconds(cardTable.getBettingTime());
                }
                break;
            case 5:
                // todo 傳出發牌結果
                playBaccarat(cardTable);
                cardTable.setStatus(BaccaratStatus.BETTING_TIME);
                cardTable.setCountSeconds(cardTable.getBettingTime());
                break;
            default:
                break;
        }
    }


    /**
     * 初始 洗牌
     */
    public static Stack<Card> shufflingCard() {
        // 初始牌
        List<Card> cards = buildPackOfCards(CARDS_NUM, PACK_NUM);
        // 切牌
        cutTheDeck(cards);
        //洗牌
        shuffle(cards);
        Stack<Card> cardStack = new Stack<>();
        cardStack.addAll(cards);
        return cardStack;
    }

    private static List<Card> buildPackOfCards(int cardNum, int packNum) {
        List<Card> cards = new LinkedList<>();
        for (int i = 0; i < cardNum * packNum; i++) {
            cards.add(new Card(suit(i + 1), symbol(i + 1)));
        }
        return cards;
    }

    /**
     * 切牌
     */
    private static void cutTheDeck(List<Card> cards) {
        Random random = new Random();
        // 只切一半以內
        int cutNum = random.nextInt(cards.size() / 2);
        cards.subList(0, cutNum).clear();
    }

    /**
     * 洗牌
     */
    private static void shuffle(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Collections.swap(cards, i,
                    (int) (Math.random() * cards.size() - 1));
        }
    }

    private static int symbol(int number) {
//        int remain = number % 13;
//        return (char) (remain + 65);
        return ((number % 13) + 1);
    }

    private static char suit(int number) {
        switch (((number - 1) / 13) % 4) {
            // ♥
            case 0:
                return '\u2665';
            //♦
            case 1:
                return '\u2666';
            // ♣
            case 2:
                return '\u2663';
            //♠
            default:
                return '\u2660';
        }
    }


    /**
     * 傳出發牌結果
     *
     * @param cardTable
     */
    private static void playBaccarat(CardTable cardTable) {
        Stack<Card> cardStack = cardTable.getCardStack();
        List<Integer> result = new ArrayList<>(11);

        if (cardStack.size() < 7) {
            cardStack.clear();
            // 初始牌
            cardStack = shufflingCard();
        }

        // 初始发排
        Map<String, List<Card>> firstDistributeMap = firstDistribute(cardStack);
        List<Card> player = firstDistributeMap.get("player");
        List<Card> banker = firstDistributeMap.get("banker");

        baccaratDraw(player, banker, cardStack);

        // 分勝負
        int sumPlayer = getCardsSum(player);
        int sumBanker = getCardsSum(banker);
        int differencePlayer = differenceNine(sumPlayer);
        int differenceBanker = differenceNine(sumBanker);

//        1-閒贏 ,2-莊贏 ,3-和局 ,4-閒對 ,5-莊對 ,6-閒雙 ,7-閒單 ,8-莊雙 ,9-莊單 ,10-大 ,11-小

        // 判斷結果
        if (differencePlayer < differenceBanker) {
            result.add(1);
//            System.out.println("== 閒贏 ==");
        } else if (differencePlayer > differenceBanker) {
            result.add(2);
//            System.out.println("== 莊贏 ==");
        } else {
            result.add(3);
//            System.out.println("== 和局 ==");
        }

        // 判斷前兩張是否是對子
        if (isPair(player)) {
            result.add(4);
//            System.out.println("== 閒對 ==");
        }
        if (isPair(banker)) {
            result.add(5);
//            System.out.println("== 莊對 ==");
        }

        // 改變 桌子的發牌結果
        cardTable.setPlayerCards(player);
        cardTable.setBankerCards(banker);
        cardTable.setResult(result);
    }

    private static Map<String, List<Card>> firstDistribute(Stack<Card> cardStack) {
        Map<String, List<Card>> result = new HashMap<>(4);
        List<Card> odd = new ArrayList<>();
        List<Card> even = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                odd.add(cardStack.pop());
            } else {
                even.add(cardStack.pop());
            }
        }
        result.put("player", odd);
        result.put("banker", even);
        return result;
    }


    private static void baccaratDraw(List<Card> odd, List<Card> even, Stack<Card> cardStack) {
        // 莊閒 累計
        int tempOddNum = 0;
        int sumOdd = getCardsSum(odd);
        int sumEven = getCardsSum(even);
        Card oddThird;
//        System.out.println("sumOdd = " + sumOdd);
//        System.out.println("sumEven = " + sumEven);
        // 天生贏家
        if (sumOdd == 8 || sumOdd == 9 || sumEven == 8 || sumEven == 9) {
            return;
        }
        // 閒家有補牌 莊家才補
        if (sumOdd <= 5) {
            oddThird = cardStack.pop();
            odd.add(oddThird);

//            tempOddNum = getSymbolNum(oddThird.getSymbol());
            tempOddNum = (oddThird.getSymbol());
            switch (sumEven) {
                case 0:
                case 1:
                case 2:
                    even.add(cardStack.pop());
                    break;
                case 3:
                    if (tempOddNum == 8) {
                        return;
                    } else {
                        even.add(cardStack.pop());
                    }
                    break;
                case 4:
                    if (tempOddNum == 0 || tempOddNum == 1 || tempOddNum == 8 || tempOddNum == 9) {
                        return;
                    } else {
                        even.add(cardStack.pop());
                    }
                    break;
                case 5:
                    if (tempOddNum == 0 || tempOddNum == 1 || tempOddNum == 2 || tempOddNum == 3 || tempOddNum == 8 || tempOddNum == 9) {
                        return;
                    } else {
                        even.add(cardStack.pop());
                    }
                    break;
                case 6:
                    if (tempOddNum == 6 || tempOddNum == 7) {
                        even.add(cardStack.pop());
                    } else {
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
        // 閒家 沒補牌 判斷莊家補牌
        else {
            if (sumEven <= 5) {
                even.add(cardStack.pop());
            }
        }
    }


    private static int getCardsSum(List<Card> cards) {
        return cards.stream()
                .mapToInt(Card::getSymbol)
//                .map(e -> e - 64)
                .filter(e -> e < 10)
                .sum() % 10;
    }


    private static int differenceNine(int sum) {
        return (9 - sum);
    }

    private static boolean isPair(List<Card> cards) {
        return cards.get(0).getSymbol() == cards.get(1).getSymbol();
    }

    private static boolean isBig(List<Card> odd, List<Card> even) {
        return odd.size() + even.size() > 4;
    }

//    private static void printCards(List<Card> cards) {
//        int symbol;
//        List<Card> printCard = new ArrayList<>();
//        for (Card card : cards) {
//            symbol = card.getSymbol() - 64;
//            switch (symbol) {
//                case 13:
//                    symbol = 'K';
//                    break;
//                case 1:
//                    symbol = 'A';
//                    break;
//                case 10:
//                    symbol = 'T';
//                    break;
//                case 11:
//                    symbol = 'J';
//                    break;
//                case 12:
//                    symbol = 'Q';
//                    break;
//                default:
//                    symbol = (symbol + 48);
//                    break;
//            }
//            Card cloneCard = new Card(card.getSuit(), (char) symbol);
//            printCard.add(cloneCard);
//        }
//        System.out.println(printCard);
//    }


}
