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
    public ArrayList<Case> getPioche() {
        return this.deck;
    }

    public ArrayList<Case> getDefausse() {
        return this.cimetery;
    }

    public int getNbCarte() {
        return this.deckAmount;
    }

    public void addCardDefausse(Case p) {
        this.cimetery.add(p);
    }

    public void setPioche(ArrayList<Case> cards) {
        this.deck = cards;
    }

    // Méthode
    public Case pick() {
        if (this.deck.size() == 0) {
            resetPioche();
        }
        Case z = this.getPioche().get(0);
        if (z.getFlood() != z.getMaxFlood()) {
            this.addCardDefausse(z);
        }
        this.deck.remove(0);
        this.deckAmount--;
        return z;
    }

    public void addDefausse() {
        Collections.shuffle(this.cimetery);
        for (int i = 0; i < this.cimetery.size(); i++) {
            this.deck.add(0, this.cimetery.get(i));
        }
        this.cimetery.clear();
    }

    public void resetPioche() {
        this.setPioche(new ArrayList<Case>(cimetery));
        Collections.shuffle(this.deck);
        this.deckAmount = this.getNbCarte();
        this.cimetery.removeAll(this.cimetery);
    }
}
