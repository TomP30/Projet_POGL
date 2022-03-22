package model;
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
}
