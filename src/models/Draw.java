package models;

import java.util.ArrayList;
import java.util.Collections;

public class Draw {
    private ArrayList<Card> deck;
    private ArrayList<Card> cimetery;

    public Draw() {
        this.deck = new ArrayList<Card>();
        this.cimetery = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            this.deck.add(Card.Wind);
            this.deck.add(Card.Fire);
            this.deck.add(Card.Wave);
            this.deck.add(Card.Stone);
            if (i > 2) {
                this.deck.add(Card.Flood);
            }
        }
        Collections.shuffle(this.deck);
    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    public void setDeck(ArrayList<Card> Cs) {
        this.deck = Cs;
    }

    public void discard(Card c){
        this.cimetery.add(c);
        this.deck.remove(c);
    }

    public Card pick() {
        if (this.deck.size() == 0) {
            resetDeck();
        }
        Card c = this.getDeck().get(0);
        this.cimetery.add(c);
        this.deck.remove(this.deck.get(0));
        return c;
    }

    public void resetDeck() {
        this.setDeck(new ArrayList<Card>(cimetery));
        Collections.shuffle(this.deck);
        this.cimetery.removeAll(this.cimetery);
    }
}
