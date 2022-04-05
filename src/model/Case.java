package model;

import java.awt.Color;
import view.ClickableSlot;
import java.nio.charset.CoderResult;

public class Case extends ClickableSlot {
    private final Coord coord; //coordinates of the cell.
    private int innondation; //flowing rate of the cell.
    private Artifact tresor;//

    public Case(Coord c, Artifact a ) throws Exception {
        //Initialization of the slot in the graphic window
        super(40,40);
        //Constructor if the cell contains an artifact.
        if (tresor.get_pos().get_x() != c.get_x() && tresor.get_pos().get_y() != c.get_y() ){
            throw new Exception("No match between the artifact position and the given coordinates !");
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

    public void add_Artifact(Artifact a) throws Exception {
        //puts the artificat in the case
        if (a.get_pos().get_x() != coord.get_x() && a.get_pos().get_y() != coord.get_y()){
            throw new Exception("No match between the artifact position and the given coordinates !");
        }else {
            tresor = a;
        }
    }
    public void innonde() throws Exception{
        // grows innondation
        if (innondation<2){
            innondation ++;
            if(innondation == 1){
                setBackground(Color.CYAN);
            } else if(innondation == 2){
                setBackground(Color.BLUE);
            }
        }else{
            throw new Exception("La case est déjà innondée");
        }
    }
    public void asseche() throws Exception{
        //reduce innondation
        if (innondation==1){
            innondation --;
            if(innondation == 1){
                setBackground(Color.CYAN);
            } else if(innondation == 0){
                setBackground(Color.GREEN);
            }
        }else{
            throw new Exception("La case ne peut être asséchée");
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
