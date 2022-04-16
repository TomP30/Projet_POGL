package model;

import controller.ClickableSlot;

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
        this.grille.innondation(3);
        for(int i=0; i<4; i++){
            Joueur J = this.grille.getJoueurs()[i];
            J.setActions(3);
            Coord coord = J.getCoord();
            Case c = this.grille.getCase(coord.get_x(),coord.get_y());
            c.setBackground(Color.RED);
        }
    }
}