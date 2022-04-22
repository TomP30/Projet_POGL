package model;

import controller.ClickableSlot;
import java.util.Random;

public class Search extends ClickableSlot {
    private Grille grille;

    public Search(Grille g){
        super("Search",50,25);
        this.grille = g;
    }

    @Override
    public void clicGauche(){
        Joueur J = this.grille.getActivePlayer();
        Random r = new Random();
        int n = r.nextInt(4);
        for(int i=0; i<6; i++){
            if(J.getKeys()[i]==null){
                System.out.println("Joueur Pioche");
                System.out.println(n);
                J.getKeys()[i] = Type.from(n);
                J.showHand();
                J.setActions(J.getActions()-1);
                break;
            }
        }

        if(J.nextPlayer()){
            this.grille.nextPlayer();
        }
    }

    @Override
    public void clicDroit(){}
}
