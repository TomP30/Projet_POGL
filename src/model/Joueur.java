package model;


import java.util.Random;

public class Joueur {
    private Coord coord; //defines where the player is located on the board
    private Artifact[] tresors; //represents all the artifacts the player owns
    private Type[] keys; //represents all the keys possessed by the player
    private int actions;

    //constructors
    public Joueur(Coord c, int lenght){
        //at the beginning a player only has its position, he possess no artifact or key neither
        this.coord = c;
        this.tresors = new Artifact[4]; //a player can only own a maximum of 4 artifacts
        this.keys = new Type[lenght]; //we will set the maximum amount of key a player can own in the constructor
        this.actions = 3; //a player will always have 3 actions per lap

        //when we initiate a player we give him 2 random keys
        Random r = new Random();
        int n;
        for(int i=0; i<2; i++){
            n = r.nextInt(4);
            this.keys[i] = Type.from(n);
        }
    }

    //getters
    public Coord getCoord() {
        return this.coord;
    }

    public Artifact[] getTresors() {
        return this.tresors;
    }

    public Type[] getKeys() {
        return this.keys;
    }

    public int getActions(){ return this.actions; }

    //setters
    public void setCoord(Coord c){
        this.coord = c;
    }

    public void setActions(int n){ this.actions = n; }

    public void addArtifact(Artifact A){//break ?
        if(this.getTresors()[3] != null){
            return;
        }
        for(int i=0; i<4; i++){
            if(this.getTresors()[i] == null){
                this.getTresors()[i] = A;
            }
        }
    }

    public void delArtifact(Artifact A){
        for(int i=0; i<4; i++){
            if(this.getTresors()[i] == A){
                this.getTresors()[i] = null;
            }
        }
    }

    public void addKey(Type K){
        if(this.getKeys()[this.getKeys().length -1] !=null){
            return;
        }
        for(int i=0; i<this.getKeys().length; i++){
            if(this.getKeys()[i]==null){
                this.getKeys()[i] = K;
                return;
            }
        }
    }

    public void delKey(Type K){
        for(int i=0; i<4; i++){
            if(this.getKeys()[i] == K){
                this.getKeys()[i] = null;
                return;
            }
        }
    }

    public void delNKey(Type K, int n){
        int cpt = 0;
        for(int i=0; i<4; i++){
            if(this.getKeys()[i] == K && cpt<n){
                this.getKeys()[i] = null;
                cpt++;
            }
        }
    }

    //methods

    public boolean hasInventoryFull(){
        for (Type T: keys){
            if (T == null){
                return false;
            }
        }
        return true;
    }

    public void deplace(Coord newC){
        this.setCoord(newC);
    }

    public boolean hasNkeys(Type K,int n){
        int cpt = 0;
        for (Type T : keys){
            if (T == K){cpt ++;}
        }
        return cpt == n;
    }
    public void giveKey(Joueur J, Type K){
        //the player gives a key of K type to the J player
        if(J.getKeys()[J.getKeys().length -1] != null){
            return;
        }
        this.delKey(K);
        J.addKey(K);
    }

    public boolean nextPlayer(){
        return this.actions<=0;
    }

    public void showHand(){
        String hand = "Main du Joueur : |";
        for(Type K : this.keys){
            if(K==Type.Stone){
                hand += " Stone |";
            } else if(K==Type.Wind){
                hand += " Wind |";
            } else if(K==Type.Fire){
                hand += " Fire |";
            } else if(K==Type.Wave){
                hand += " Wave |";
            } else {
                hand += " 0 |";
            }
        }
        System.out.println(hand);
    }
}
