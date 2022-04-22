package model;
//import du package view
import view.GrilleVue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

//autres imports
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;


public class Grille extends GrilleVue {
    private Case[][] cases;
    private Case heliport;
    private Artifact[] tresors;
    private Joueur[] joueurs = new Joueur[3];
    private Joueur ActivePlayer;

    public boolean isValidCoord(Coord c){
        return c.get_y()>=0 && c.get_y()<6
                && c.get_x()>=0 && c.get_x()<6
                && c.dist(new Coord(0,0)) >=2
                && c.dist(new Coord(0,5)) >=2
                && c.dist(new Coord(5,0)) >=2
                && c.dist(new Coord(5,5)) >=2;
    }
    public Grille(Coord heliPos,Coord[] artifactPos, int hand) throws Exception{
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

        Coord[] coord = new Coord[4];
        coord[0] = new Coord(3,0);
        coord[1] = new Coord(5,3);
        coord[2] = new Coord(2,5);
        coord[3] = new Coord(0,2);

        cases = new Case[6][];
        tresors = new Artifact[4];
        for (int i = 0; i<6;i++){
            cases[i] = new Case[6];
            for (int j =0;j<6;j++){
                Coord casCoord = new Coord(i,j);
                if (isValidCoord(casCoord)) {

                    if (i == heliPos.get_x() && j == heliPos.get_y()) {
                        cases[i][j] = new Case(casCoord, this);
                        heliport = cases[i][j];
                        this.ajtElem(heliport);
                        cases[i][j].setBackground(Color.LIGHT_GRAY);
                    } else {
                        cases[i][j] = new Case(casCoord,"", this);
                        for (int k = 0; k < 4; k++) {
                            if (i == artifactPos[k].get_x() && j == artifactPos[k].get_y()) {
                                tresors[k] = new Artifact(artifactPos[k], Type.from(k));
                                cases[i][j].add_Artifact(tresors[k]);
                                cases[i][j].changeText(Type.from(k).toString());
                            }
                        }
                        this.ajtElem(cases[i][j]);
                    }
                }else{
                    cases[i][j] = new Case(casCoord, this);
                    this.ajtElem(cases[i][j]);
                    Color c = new Color(0,25,100);
                    cases[i][j].setBackground(c);
                }
            }
        }

        for(int i=0; i<3; i++){
            this.joueurs[i] = new Joueur(coord[i],hand);
            this.cases[coord[i].get_x()][coord[i].get_y()].occupy();
            Color c = new Color(150, 25, 0);
            this.cases[coord[i].get_x()][coord[i].get_y()].setBackground(c);
        }
        this.ActivePlayer = this.joueurs[0];
        CheckUp();
    }

    public Joueur getActivePlayer(){ return this.ActivePlayer; }
    public int getActivePlayerIndex(){
        for(int i=0; i<3; i++){
            if(this.joueurs[i]==this.ActivePlayer){
                return i;
            }
        }
        return -1;
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

    public void nextPlayer(){
        CheckUp();
        for(int i=0; i<2; i++){
            if(this.ActivePlayer == this.joueurs[i]){
                this.ActivePlayer = this.joueurs[i+1];
                JOptionPane.showMessageDialog(null,"Voici votre main : "+ActivePlayer.showHandStr());
                return;
            }
        }
        ArrayList<Case> C = getRandomCases(1,false);
        innondation(3);
        this.ActivePlayer = this.joueurs[0];
        for(int i=0; i<3; i++){
            this.joueurs[i].setActions(3);
        }
        JOptionPane.showMessageDialog(null,"Voici votre main : "+ActivePlayer.showHandStr());

    }

    public Artifact[] getTresors(){ return this.tresors; }

    public boolean ClickableL(Case C){
        //une case est cliquable si on veut l'assecher
        //pour se faire le joueur actif doit être dans une des cases adjacentes
        Joueur J = this.ActivePlayer;
        Case Cj = this.cases[J.getCoord().get_x()][J.getCoord().get_y()];
        for(Case cas : this.neighbours(C,1)){
            if(cas == Cj){
                return true;
            }
        }
        return false;
    }

    public void ClickabilityL(){
        //test la Lclickabilité des toutes les cases de la grille
        //une case est Lcliquable si elle se trouve a coté du joueur actif
        Joueur J = this.getActivePlayer();
        for(int j=0; j<6; j++){
            for(int i=0; i<6; i++){
                if(j==J.getCoord().get_y() && i==J.getCoord().get_x()){
                    for(Case C : neighbours(this.cases[i][j],1)) {
                        C.switchclickL();
                        System.out.println("Case Lcliquable -> (" + C.getCoord().get_x() + "," + C.getCoord().get_y() + ")");
                    }
                    //switchclick turns clickable to 'TRUE' -> now we must be sure that when the player moves
                    //the clickability evolves in order to makes it realisticly playable
                } else {
                    if(this.cases[i][j].getClickL() && !(ClickableL(this.cases[i][j]))){
                        System.out.println("Cases Lannulées -> (" + i + "," + j + ")");
                        this.cases[i][j].switchclickL();
                    }
                }
            }
        }
    }
    public void ClickabilityR(){
        //test la Rcliquabilité de toutes les cases de la grille
        //une case est Rcliquable si elle se trouve a coté du joueur actif ET contient un joueur
        Joueur J = this.ActivePlayer;
        for(int j=0; j<6; j++){
            for(int i=0; i<6; i++){
                if(j==J.getCoord().get_y() && i==J.getCoord().get_x()){
                    for(Case C : neighbours(this.cases[i][j],1)){
                        if(C.get_occupied()){
                            C.switchClickR();
                            System.out.println("Case Rcliquable -> (" + C.getCoord().get_x() + "," + C.getCoord().get_y() + ")");
                        }
                    }
                } else {
                    if(this.cases[i][j].getClickR()){
                        System.out.println("Cases Rannulées -> (" + i + "," + j + ")");
                        this.cases[i][j].switchClickR();
                    }
                }
            }
        }
    }
    public  boolean GameOver(){
        boolean end = false;
        for(Artifact A : this.tresors){
            if(!(A.is_claimed()) && this.getCase(A.get_pos().get_x(),A.get_pos().get_y()).getInnondation()==2){
                end = true;
            }
        }
        if(heliport.getInnondation()==2){
            end = true;
        }
        return end;
    }

    public boolean Victory(){
        boolean victory = true;
        for(Joueur J : this.joueurs){
            if(J.getCoord().get_x()!=heliport.getX() && J.getCoord().get_y()!=heliport.getY()){
                victory = false;
            }
        }
        for(Artifact A : this.tresors){
            if(!(A.is_claimed())){
                victory = false;
            }
        }
        return victory;
    }

    public void CheckUp(){
        ClickabilityL();
        ClickabilityR();
        if(Victory()) {
            JOptionPane.showMessageDialog(null,"- YOU WIN !!! -");
            return;
        } else if(GameOver()){
            JOptionPane.showMessageDialog(null,"- GAME OVER -");
            return;
        }

    }
}
