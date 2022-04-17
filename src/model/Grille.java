package model;
//import du package view
import view.GrilleVue;
import java.awt.Color;

//autres imports
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;


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
    public Grille(Coord heliPos,Coord[] artifactPos ){
        super(6,6);
        // Basic constructor for grille, given the position of heliport and the artifacts.
        if (!(isValidCoord(heliPos))){
            throw new IllegalArgumentException("Coordonnées d'heliport invalides !");
        }
        for (Coord c : artifactPos){
            if (!(isValidCoord(c))){
                throw new IllegalArgumentException("Coordonnées d'artefact invalides !");
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
                    this.ajtElem(cases[i][j]);
                    cases[i][j].setBackground(Color.DARK_GRAY);
                }
            }
        }

        for(int i=0; i<4; i++){//les joueurs commencent de l'héliport.
            this.joueurs[i] = new Joueur(getHeliport().getCoord(),4);
            heliport.setBackground(Color.RED);
        }
    }
    public ArrayList<Case> neighbours(Case c, int dist) {
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
            throw new IllegalArgumentException("Erreur : getCase -> Coordonnées invalides");
        }else{
            return cases[i][j];
        }
    }

    public Case getCaseByCoord(Coord C){return getCase(C.get_x(),C.get_y());}

    public ArrayList<Case> getRandomCases(int n, boolean inundate){
        //get a random ArrayList of Cases from Grille
        Random rand = new Random();
        ArrayList<Case> tab = new ArrayList<Case>();
        int x = 0;
        int y = 0;
        int count = 0;
        while (count !=n){
            if (!(inundate)) {
                while (!isValidCoord(new Coord(x, y)) || tab.contains(cases[x][y])) {
                    x = rand.nextInt(6);
                    y = rand.nextInt(6);
                }
            }else{
                while (!isValidCoord(new Coord(x, y)) || cases[x][y].getInnondation()==2 ||tab.contains(cases[x][y])) {
                    x = rand.nextInt(6);
                    y = rand.nextInt(6);
                }
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
    public int countDryCases(){
        int cpt = 0;
        for(int i = 0;i<6;i++){
            for(int j = 0;j<6;j++){
                if (isValidCoord(new Coord(i,j)) && cases[i][j].getInnondation()<2){
                    cpt ++;
                }
            }
        }
        return cpt;
    }
    public void innondation(int amount){
        //Innonde aléatoirement un nombre donné de cases
        int n = min(amount,countDryCases());
        ArrayList<Case> tab = getRandomCases(min(amount,countDryCases()),true);
        for (Case aCase : tab) {
            aCase.innonde();
        }
        while (n<amount && countDryCases()!=0){ // pour remplir 3 fois meme s'il ne reste que 2 cases dont une totallement seche et l'autre partiellement innondée.
            n += min(amount-n,countDryCases());
            tab = getRandomCases(min(amount-n,countDryCases()),true);
            for (Case aCase : tab) {
                aCase.innonde();
            }
        }
    }
    public void claimArtifact(Joueur J,int keyAmount){
        Coord JCoord = J.getCoord();
        for (Artifact A: tresors){
            if (!A.is_claimed() && JCoord.equals(A.get_pos()) && J.hasNkeys(A.get_element(),keyAmount)){
                J.delNKey(A.get_element(),keyAmount);
                J.addArtifact(A);
                A.claim(J);
                getCaseByCoord(JCoord).removeArtifact();
                return;
            }
        }
        throw new IllegalCallerException("erreur: claimArtifact -> pas d'Artifact récupérables à la position du joueur.");

    }

}
