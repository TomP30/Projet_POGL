package model;

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
        for (int i = 0; i<4; i++){
            tresors[i] = null;
        }
        this.keys = new Type[lenght]; //we will set the maximum amount of key a player can own in the constructor
        for (int i = 0; i<lenght; i++){
            keys[i] = null;
        }
        this.actions = 3; //a player will always have 3 actions per lap
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

    public boolean hasInventoryFull(){
        for (Type T: keys){
            if (T == null){
                return false;
            }
        }
        return true;
    }

    //setters
    public void setCoord(Coord c){
        this.coord = c;
    }

    public void setActions(int n){ this.actions = n; }

    public void addArtifact(Artifact A){//break ?
        this.tresors[A.get_element().value] = A;
    }

    public void delArtifact(Artifact A){
        this.tresors[A.get_element().value] = null;
    }

    public void addKey(Type K){//break ?
        for(int i=0; i<this.getKeys().length; i++){
            if(this.getKeys()[i]==null){
                this.keys[i] = K;
                return;
            }
        }
    }

    public void delKey(Type K){
        for(int i=0; i<this.getKeys().length; i++){
            if(this.getKeys()[i] == K){
                this.keys[i] = null;
                return;
            }
        }
    }

    public void delNKey(Type K, int n){
        for (int i = 0;i<n;i++){
            delKey(K);
        }
    }

    //methods
    public void deplace(Coord newC){
        this.setCoord(newC);
    }

    public void donnerClef(Joueur J, Type K){
        //the player gives a key of K type to the J player
        if(J.hasInventoryFull()){
            return;
        }
        this.delKey(K);
        J.addKey(K);
    }
}
