package model;

import java.util.ArrayList;
import java.util.Random;

public class Grille {
    private Case[][] cases;
    private Case heliport;
    private Artifact[] tresors;

    public boolean isValidCoord(Coord c) throws Exception {
        return c.get_y()>=0 && c.get_y()<=6
                && c.get_x()>=0 && c.get_x()<=6
                && c.dist(new Coord(0,0)) >=2
                && c.dist(new Coord(0,6)) >=2
                && c.dist(new Coord(6,0)) >=2
                && c.dist(new Coord(6,6)) >=2;
    }
    public Grille(Coord heliPos,Coord[] artifactPos ) throws Exception{
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
        for (int i = 0; i<6;i++){
            cases[i] = new Case[6];
            for (int j =0;j<6;j++){
                Coord casCoord = new Coord(i,j);
                 if (isValidCoord(casCoord)){

                     if (i == heliPos.get_x() && j == heliPos.get_y()) {
                         cases[i][j] = new Case(casCoord);
                         heliport = cases[i][j];

                     }else {
                         cases[i][j] = new Case(casCoord);
                         for (int k = 0; k<4;k++){
                                if (i == artifactPos[k].get_x() && j == artifactPos[k].get_y()){
                                    cases[i][j].add_Artifact(new Artifact(artifactPos[k],Type.from(k)));
                                }
                         }
                     }
                }else{
                    cases[i][j] = null;
                }
            }
        }
    }
    public ArrayList<Case> neighbours(Case c, int dist) throws Exception {
        // Gets all the neighbours from c with a maximum distance of dist.
        ArrayList<Case> tab = new ArrayList<Case>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (isValidCoord(new Coord(i,j))) {
                    int distance = c.getCoord().dist(cases[i][j].getCoord());
                    if (distance > 0 && distance < dist) {
                        tab.add(cases[i][j]);
                    }
                }
            }
        }
        return tab;
    }
    public Case getCase(int i,int j) throws Exception {
        if (!isValidCoord(new Coord(i,j))){
            throw new Exception("Coordonnées invalides");
        }else{
            return cases[i][j];
        }
    }
    public ArrayList<Case> getRandomCases(int n) throws Exception {
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
    public void innondation(int amount) throws Exception {
        //Innonde aléatoirement un nombre donné de cases
        ArrayList<Case> tab = getRandomCases(amount);
        for (Case aCase : tab) {
            aCase.innonde();
        }
    }
}
