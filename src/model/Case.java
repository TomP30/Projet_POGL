package model;

import java.awt.Color;
import controller.ClickableSlot;

public class Case extends ClickableSlot {
    private final Coord coord; //coordinates of the cell.
    private int innondation; //flowing rate of the cell.
    private Artifact tresor;//

    public Case(Coord c, Artifact a ) {
        //Initialization of the slot in the graphic window
        super(40,40);
        //Constructor if the cell contains an artifact.
        if (tresor.get_pos().get_x() != c.get_x() && tresor.get_pos().get_y() != c.get_y() ){
            throw new IllegalArgumentException("Erreur : Constructor Case -> les positions des artefacts ne correspondent pas");
        }
        tresor = a;
        innondation = 0;
        coord = c;

    }
    public Case(Coord c){
        super(40,40);
        //Constructor if the cell doesn't contain an artifact.
        tresor = null;
        innondation = 0;
        coord = c;
    }

    public void add_Artifact(Artifact a){
        //puts the artificat in the case
        if (a.get_pos().get_x() != coord.get_x() && a.get_pos().get_y() != coord.get_y()){
            throw new IllegalArgumentException("Erreur : add_Artifact -> les positions ne correspondent pas");
        }else {
            tresor = a;
        }
    }
    public void innonde(){
        // grows innondation
        if (innondation<2){
            innondation ++;
            if(innondation == 1){
                setBackground(Color.CYAN);
            } else if(innondation == 2){
                setBackground(Color.BLUE);
            }
        }else{
            throw new IllegalCallerException("Erreur : innonde -> la case est deja innondée");
        }
    }
    public void asseche(){
        //reduce innondation
        if (innondation==1){
            innondation --;
            if(innondation == 1){
                setBackground(Color.CYAN);
            } else if(innondation == 0){
                setBackground(Color.GREEN);
            }
        }else{
            throw new IllegalCallerException("Erreur : asseche -> {0} ne peut pas être assechée" .formatted(this.getCoord()));
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

    public void removeArtifact(){ tresor = null;}

    @Override
    public void clicDroit() {

    }

    @Override
    public void clicGauche(){

    }
}
