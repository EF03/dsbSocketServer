package com.imi.dsbsocket.entity.base;

/**
 * @author Ron
 * @date 2020/10/20 上午 11:43
 */
public class Card {
    /*
      A-B-C-D-E-F-G-H-I- J-K-L-M
      A-2-3-4-5-6-7-8-9-10-J-Q-K
      K-A-2-3-4-5-6-7-8-9-10-J-Q
  */
    private char suit;
    private int symbol;

    public Card(char suit, int symbol) {
        this.suit = suit;
        this.symbol = symbol;
    }


    public char getSuit() {
        return suit;
    }

    public void setSuit(char suit) {
        this.suit = suit;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return suit + "" + symbol;
    }
}
