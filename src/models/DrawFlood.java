package models;

import java.util.ArrayList;
import java.util.Collections;

public class DrawFlood {
    private ArrayList<Case> deck;
    private ArrayList<Case> cimetery;
    private int deckAmount;

    public DrawFlood(ArrayList<Case> p) {
        this.deck = p;
        Collections.shuffle(this.deck);
        this.cimetery = new ArrayList<Case>();
        this.deckAmount = this.deck.size();
    }

    // Getter
    public ArrayList<Case> getDeck() {
        return this.deck;
    }

    public int getDeckAmount() {
        return this.deckAmount;
    }

    public void cimeteryAdd(Case C) {
        this.cimetery.add(C);
    }

    public void setDeck(ArrayList<Case> Cs) {
        this.deck = Cs;
    }

    // Méthode
    public Case pick() {
        if (this.deck.size() == 0) {
            resetDeck();
        }
        Case C = this.getDeck().get(0);
        if (C.getFlood() != C.getMaxFlood()) {
            this.cimeteryAdd(C);
        }
        this.deck.remove(0);
        this.deckAmount--;
        return C;
    }

    public void addCimetery() {
        Collections.shuffle(this.cimetery);
        for (int i = 0; i < this.cimetery.size(); i++) {
            this.deck.add(0, this.cimetery.get(i));
        }
        this.cimetery.clear();
    }

    public void resetDeck() {
        this.setDeck(new ArrayList<Case>(cimetery));
        Collections.shuffle(this.deck);
        this.deckAmount = this.getDeckAmount();
        this.cimetery.removeAll(this.cimetery);
    }
}
