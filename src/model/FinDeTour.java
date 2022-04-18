package model;

import controller.ClickableSlot;

import java.awt.*;

public class FinDeTour extends ClickableSlot {
    private Grille grille;

    //Constructor
    public FinDeTour(Grille g){
        super("Fin De Tour", 110, 25);
        this.grille = g;
        Color c = new Color(136, 32, 32);
        setBackground(c);
    }
    @Override
    public void clicDroit() {
    }

    @Override
    public void clicGauche() {
        this.grille.nextPlayer();
    }
}
