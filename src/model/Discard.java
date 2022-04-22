package model;

import controller.ClickableSlot;

import javax.swing.*;

public class Discard extends ClickableSlot {
    private Grille grille;

    public Discard(Grille g){
        super("Discard",50,25);
        this.grille = g;
    }

    @Override
    public void clicGauche(){
        //Whenever a player choose to discard a key it's like when he gives one
            //but the choosen key is removed from hand
        this.grille.CheckUp();
        Joueur J = grille.getActivePlayer();

        Object[] possibilities = {"Stone","Wind","Fire","Wave","None"};
        String s = (String) JOptionPane.showInputDialog(null,"You decided to throw a Key :"+J.showHandStr(),"Customized Dialog",JOptionPane.PLAIN_MESSAGE,null,possibilities,"None");
        if(s=="None"){
            return;
        } else {
            for(Type K : J.getKeys()){
                if(K==Type.valueOf(s)){
                    J.delKey(K);
                    J.setActions(J.getActions()-1);
                    return;
                }
            }
        }

    }
    public void clicDroit(){
    }
}
