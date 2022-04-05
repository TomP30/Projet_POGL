package model;

import view.ClickableSlot;

import java.awt.*;

public class FinDeTour extends ClickableSlot {
    private Grille grille;

    //Constructor
    public FinDeTour(Grille g){
        super("Fin De Tour", 80, 25);
        this.grille = g;
    }
    @Override
    public void clicDroit() {
    }

    @Override
    public void clicGauche() {
        setBackground(Color.RED);
        Case c = this.grille.getRandomCases(1)[0];
        c.innonde();
    }
}
