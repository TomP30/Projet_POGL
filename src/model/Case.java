package model;

import java.nio.charset.CoderResult;

public class Case {
    private final Coord coord; //coordinates of the cell.
    private int innondation; //flowing rate of the cell.
    private Artifact tresor;//

    public Case(Coord c, Artifact a ) throws Exception {
        //Constructor if the cell contains an artifact.
        if (tresor.get_pos() != c){
            throw new Exception("No match between the artifact position and the given coordinates !");
        }
        tresor = a;
        innondation = 0;
        coord = c;

    }
    public Case(Coord c){
        //Constructor if the cell doesn't contain an artifact.
        tresor = null;
        innondation = 0;
        coord = c;
    }

    public void add_Artifact(Artifact a) throws Exception {
        //puts the artificat in the case
        if (a.get_pos() != coord){
            throw new Exception("No match between the artifact position and the given coordinates !");
        }else {
            tresor = a;
        }
    }
    public void innonde() throws Exception{
        // grows innondation
        if (innondation<2){
            innondation ++;
        }else{
            throw new Exception("La case est déjà innondée");
        }
    }
    public void asseche() throws Exception{
        //reduce innondation
        if (innondation==1){
            innondation --;
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

}
