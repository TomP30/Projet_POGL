package model;

import view.ClickableSlot;

public class Deplacement extends ClickableSlot{
    private Grille grille;

    public Deplacement(Grille g){
        super("Deplacer", 80,25);
        this.grille = g;
    }
}
