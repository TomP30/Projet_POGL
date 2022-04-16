package model;

import controller.ClickableSlot;

public class Deplacement extends ClickableSlot{
    private Grille grille;

    public Deplacement(Grille g){
        super("Deplacer", 80,25);
        this.grille = g;
    }

    @Override
    public void clicDroit() {

    }

    @Override
    public void clicGauche(){

    }
}
