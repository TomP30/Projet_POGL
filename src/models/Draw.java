package models;

import java.util.ArrayList;
import java.util.Collections;

public class Draw {
    private ArrayList<Card> pioche;
    private ArrayList<Card> defausse;

    public Draw() {
        this.pioche = new ArrayList<Card>();
        this.defausse = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            this.pioche.add(Card.Wind);
            this.pioche.add(Card.Fire);
            this.pioche.add(Card.Wave);
            this.pioche.add(Card.Stone);
            if (i > 2) {
                this.pioche.add(Card.Flood);
            }
        }
        Collections.shuffle(this.pioche);
    }

    public ArrayList<Card> getPioche() {
        return this.pioche;
    }

    public ArrayList<Card> getDefausse() {
        return this.defausse;
    }

    public void setPioche(ArrayList<Card> cards) {
        this.pioche = cards;
    }

    public void sendToDefausse(Card c){
        this.defausse.add(c);
        this.pioche.remove(c);
    }

    public Card pick() {
        if (this.pioche.size() == 0) {
            resetPioche();
        }
        Card c = this.getPioche().get(0);
        this.defausse.add(c);
        this.pioche.remove(this.pioche.get(0));
        return c;
    }

    public void resetPioche() {
        this.setPioche(new ArrayList<Card>(defausse));
        Collections.shuffle(this.pioche);
        this.defausse.removeAll(this.defausse);
    }
}
