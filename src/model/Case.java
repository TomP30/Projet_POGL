package model;

import java.awt.*;

import controller.ClickableSlot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.nio.charset.CoderResult;

public class Case extends ClickableSlot {
    private final Grille grille;
    private final Coord coord; //coordinates of the cell.
    private int innondation; //flowing rate of the cell.
    private Artifact tresor;//
    private boolean occupied;
    private boolean clickableL;
    private boolean clickableR;
    protected Image buffer;

    public Case(Coord c, Grille g){
        super(80,80);
        //Constructor if the cell doesn't contain an artifact.
        grille = g;
        tresor = null;
        innondation = 0;
        coord = c;
        occupied = false;
        clickableL = false;
        clickableR = false;
    }
    public Case(Coord c, String txt, Grille g){
        super(txt,80,80);
        grille = g;
        tresor = null;
        innondation = 0;
        coord = c;
        occupied = false;
        clickableL = false;
        clickableR = false;
    }


    public boolean get_occupied(){ return this.occupied; }

    public void add_Artifact(Artifact a){
        //puts the artificat in the case
        if (a.get_pos().get_x() != coord.get_x() && a.get_pos().get_y() != coord.get_y()){
            System.out.print("Erreur : add_Artifact -> les positions ne correspondent pas");
        }else {
            tresor = a;
        }
    }

    public void innonde(){
        // grows innondation
        if (innondation<2){
            innondation ++;
            if(innondation == 1){
                Color c = new Color(0,75,175);
                setBackground(c);
            } else if(innondation == 2){
                Color c = new Color(0,25,100);
                setBackground(c);
            }
        }else{
            System.out.print("Erreur : innonde -> la case est deja innondée");
        }
    }
    public void asseche(){
        //reduce innondation
        if (innondation>0){
            innondation --;
            if(innondation == 1){
                Color c = new Color(0,75,175);
                setBackground(c);
            } else if(innondation == 0){
                Color c = new Color(28, 164, 41, 255);
                setBackground(c);
            }
        }else{
            System.out.print("Erreur : asseche -> {0} ne peut pas être assechée" .formatted(this.getCoord()));
        }
    }
    public Coord getCoord(){
        //get the coords of the case
        return coord;
    }

    public int getInnondation(){
        //get the innondation rate.
        return innondation;
    }

    public boolean hasArtifact(){return tresor != null;}

    public Artifact getTresor(){ return this.tresor; }

    public void removeArtifact(){ tresor = null;}

    public void occupy(){
        this.occupied = true;
    }

    public void empty(){
        this.occupied = false;
    }

    public boolean getClickL(){ return this.clickableL; }
    public boolean getClickR(){ return this.clickableR; }

    public void switchclickL(){
        this.clickableL = !this.clickableL;
    }
    public void switchClickR() { this.clickableR = !this.clickableR;}

    @Override
    public void clicDroit() {
        //when a player righclicks on a slot, it means
        //the player who clicked is the ActivePlayer
        //the slot where he clicked is a slot with another player
        //maybe the player will just give a key to the other one not exchange ?
        System.out.println("Case cliquée -> (" + coord.get_x() + "," + coord.get_y() + ")");

        if(clickableR) {
            Joueur J1 = this.grille.getActivePlayer();
            Coord coord = this.coord;
            for(Joueur J2 : this.grille.getJoueurs()){
                if(J2.getCoord().get_x() == coord.get_x() && J2.getCoord().get_y() == coord.get_y()){
                    //ici on veut mtn donner le choix au joueur de donner la clef qu'il veut dans sa main
                    //d'abord il faut donner le choix au joueur actif de la clef a donner
                    System.out.println("Choisissez une carte à donner (indice dans votre main)");
                    J1.showHand();
                    Scanner keyboard = new Scanner(System.in);
                    int index = keyboard.nextInt();
                    Type Kj = J1.getKeys()[index];
                    J1.giveKey(J2, Kj);
                    J1.setActions(J1.getActions()-1);
                    System.out.println("done");
                }
            }
        }
    }

    @Override
    public void clicGauche(){
        System.out.println("LeftClick" + clickableL);
        if(clickableL){
            this.asseche();
            Joueur J = this.grille.getActivePlayer();
            J.setActions(J.getActions()-1);
            if(J.nextPlayer()){
                this.grille.nextPlayer();
            }
        }
    }
}
