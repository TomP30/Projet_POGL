package model;
//import du package view
import view.GrilleVue;
import java.awt.Color;

//autres imports
import java.util.ArrayList;
import java.util.Random;

public class Grille extends GrilleVue {
    private Case[][] cases;
    private Case heliport;
    private Artifact[] tresors;
    private Joueur[] joueurs = new Joueur[4];

    public boolean isValidCoord(Coord c){
        return c.get_y()>=0 && c.get_y()<6
                && c.get_x()>=0 && c.get_x()<6
                && c.dist(new Coord(0,0)) >=2
                && c.dist(new Coord(0,5)) >=2
                && c.dist(new Coord(5,0)) >=2
                && c.dist(new Coord(5,5)) >=2;
    }
    public Grille(Coord heliPos,Coord[] artifactPos ) throws Exception{
        super(6,6);
        // Basic constructor for grille, given the position of heliport and the artifacts.
        if (!(isValidCoord(heliPos))){
            throw new Exception("Coordonnées d'heliport invalides !");
        }
        for (Coord c : artifactPos){
            if (!(isValidCoord(c))){
                throw new Exception("Coordonnées d'artefact invalides !");
            }
        }

        cases = new Case[6][];
        tresors = new Artifact[4];
        for (int i = 0; i<6;i++){
            cases[i] = new Case[6];
            for (int j =0;j<6;j++){
                Coord casCoord = new Coord(i,j);
                 if (isValidCoord(casCoord)){

                     if (i == heliPos.get_x() && j == heliPos.get_y()) {
                         cases[i][j] = new Case(casCoord);
                         heliport = cases[i][j];
                         this.ajtElem(heliport);
                         cases[i][j].setBackground(Color.LIGHT_GRAY);
                     }else {
                         cases[i][j] = new Case(casCoord);
                         for (int k = 0; k<4;k++){
                                if (i == artifactPos[k].get_x() && j == artifactPos[k].get_y()){
                                    tresors[k] = new Artifact(artifactPos[k],Type.from(k));
                                    cases[i][j].add_Artifact(tresors[k]);
                                }
                         }
                         this.ajtElem(cases[i][j]);
                     }
                }else{
                    cases[i][j] = new Case(casCoord);
                    setBackground(Color.DARK_GRAY);
                    this.ajtElem(cases[i][j]);
                }
            }
        }
        Coord[] coord = new Coord[4];
        coord[0] = new Coord(2,0);
        coord[1] = new Coord(5,2);
        coord[2] = new Coord(3,5);
        coord[3] = new Coord(0,3);
        for(int i=0; i<4; i++){
            this.joueurs[i] = new Joueur(coord[i],4);
        }
    }
    public ArrayList<Case> neighbours(Case c, int dist) throws Exception {
        // Gets all the neighbours of c with a maximum distance of dist.
        ArrayList<Case> tab = new ArrayList<Case>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (isValidCoord(new Coord(i,j))) {
                    int distance = c.getCoord().dist(cases[i][j].getCoord());
                    if (distance > 0 && distance <= dist) {
                        tab.add(cases[i][j]);
                    }
                }
            }
        }
        return tab;
    }

    public Joueur[] getJoueurs(){
        return this.joueurs;
    }

    public Case getCase(int i,int j){
        if (!isValidCoord(new Coord(i,j))){
            System.out.print("Erreur : getCase -> Coordonnées invalides");
            return cases[i][j];
        }else{
            return cases[i][j];
        }
    }
    public ArrayList<Case> getRandomCases(int n){
        //get a random ArrayList of Cases from Grille
        Random rand = new Random();
        ArrayList<Case> tab = new ArrayList<Case>();
        int x = 0;
        int y = 0;
        int count = 0;
        while (count !=n){
            while (!isValidCoord(new Coord(x,y)) || tab.contains(cases[x][y])){
                 x = rand.nextInt(6);
                 y = rand.nextInt(6);
            }
            tab.add(cases[x][y]);
            count ++;
            x = 0;
            y = 0;
        }
        return tab;
    }
    public Case[][] getCases(){ return this.cases; }
    public Case getHeliport(){ return heliport;}
    public void innondation(int amount){
        //Innonde aléatoirement un nombre donné de cases
        ArrayList<Case> tab = getRandomCases(amount);
        for (Case aCase : tab) {
            aCase.innonde();
        }
    }

}
