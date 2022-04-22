package controller;

import model.*;
import java.awt.*;

public class Right extends ClickableSlot{
    private Grille grille;

    public Right(Grille g){
        super("RIGHT",50,25);
        this.grille = g;
        setBackground(Color.LIGHT_GRAY);
    }
    @Override
    public void clicDroit(){}

    @Override
    public void clicGauche(){
        System.out.println("Move Right");
        Joueur J = this.grille.getActivePlayer();
        Coord coord = new Coord(J.getCoord().get_x(),J.getCoord().get_y()+1);
        Coord oldCoord = J.getCoord();
        if(coord.isValidCoord() && this.grille.getCase(coord.get_x(),coord.get_y()).getInnondation()!=2) {
            Color col = new Color(28, 164, 41, 255);
            this.grille.getCase(J.getCoord().get_x(), J.getCoord().get_y()).setBackground(col);
            this.grille.getCase(J.getCoord().get_x(), J.getCoord().get_y()).empty();

            J.deplace(coord);
            this.grille.getCase(J.getCoord().get_x(),J.getCoord().get_y()).occupy();
            Color c = new Color(150, 25, 0);
            this.grille.getCase(J.getCoord().get_x(), J.getCoord().get_y()).setBackground(c);
            J.setActions(J.getActions()-1);
            Case C = this.grille.getCase(oldCoord.get_x(),oldCoord.get_y());
            if(C.getInnondation()==1) {
                Color c0 = new Color(0,75,175);
                this.grille.getCase(oldCoord.get_x(), oldCoord.get_y()).setBackground(c0);
            } else if(C.getCoord() == this.grille.getHeliport().getCoord()){
                this.grille.getCase(oldCoord.get_x(), oldCoord.get_y()).setBackground(Color.LIGHT_GRAY);
            }
            for(Joueur J1 : this.grille.getJoueurs()){
                if(J1.getCoord().get_x()==oldCoord.get_x() && J1.getCoord().get_y()==oldCoord.get_y()){
                    Color c1 = new Color(150, 25, 0);
                    this.grille.getCase(J1.getCoord().get_x(),J1.getCoord().get_y()).setBackground(c1);
                }
            }
        }
        if(J.nextPlayer()){
            this.grille.nextPlayer();
        }
        this.grille.ClickabilityL();
        this.grille.ClickabilityR();
    }
}
