package model;

public class Grille {
    private Case[][] Plateau; //the board is represented by a 2D tab of slots
    private Artifact[] Artefacts; //represent the 4 artefacts of the Game
    private Coord heliport; //represent the position of the heliport (final slot)

    //constructors
    public Grille(int length, Coord h, Coord[] A) throws Exception {
        if(A.length != 4){
            throw new Exception("il faut donner 4 coordonnées uniquement");
        }
        this.heliport = h;

        this.Artefacts = new Artifact[4];
        this.Artefacts[0] = new Artifact(A[0], Type.Stone);
        this.Artefacts[1] = new Artifact(A[1], Type.Wind);
        this.Artefacts[2] = new Artifact(A[2], Type.Fire);
        this.Artefacts[3] = new Artifact(A[3], Type.Wave);

        this.Plateau = new Case[length][length];
        for(int i=0; i<length; i++){
            for(int j=0; j<length; j++){
                this.Plateau[i][j] = new Case(new Coord(i,j) );
            }
        }
    }
}
