package models;

import java.util.ArrayList;
import java.util.Collections;

public class PiocheInnondation {
    private ArrayList<Case> pioche;
    private ArrayList<Case> defausse;
    private int nbCartePioche;

    public PiocheInnondation(ArrayList<Case> p) {
        this.pioche = p;
        Collections.shuffle(this.pioche);
        this.defausse = new ArrayList<Case>();
        this.nbCartePioche = this.pioche.size();
    }

    // Getter
    public ArrayList<Case> getPioche() {
        return this.pioche;
    }

    public ArrayList<Case> getDefausse() {
        return this.defausse;
    }

    public int getNbCarte() {
        return this.nbCartePioche;
    }

    public void addCardDefausse(Case p) {
        this.defausse.add(p);
    }

    public void setPioche(ArrayList<Case> cards) {
        this.pioche = cards;
    }

    // Méthode
    public Case pick() {
        if (this.pioche.size() == 0) {
            resetPioche();
        }
        Case z = this.getPioche().get(0);
        if (z.getWaterLvl() != z.getMaxWaterLvl()) {
            this.addCardDefausse(z);
        }
        this.pioche.remove(0);
        this.nbCartePioche--;
        return z;
    }

    public void addDefausse() {
        Collections.shuffle(this.defausse);
        for (int i = 0; i < this.defausse.size(); i++) {
            this.pioche.add(0, this.defausse.get(i));
        }
        this.defausse.clear();
    }

    public void resetPioche() {
        this.setPioche(new ArrayList<Case>(defausse));
        Collections.shuffle(this.pioche);
        this.nbCartePioche = this.getNbCarte();
        this.defausse.removeAll(this.defausse);
    }
}
