package model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Artifact {
    //Design the fourth artifacts that need to be claimed to win.
    private Joueur owner = null; //owner of the artifact, null while it has not been.
    private final Type element; //element of the artifact. There's one artifact of each element.
    private Coord position; //initial position of the artifact, null if it's claimed.

    public Artifact(Coord pos, Type elem){
        //constructor of artifact class, given the initial position and element.
        element = elem;
        position = pos;
    }

    private void set_owner(Joueur j){
        //set owner of the artifact, given the player.
        owner = j;
    }
    private void null_pos(){
        //set position of the artifact to null.
        position = null;
    }

    public void claim(Joueur j){
        //changes the artifact status from "not claimed" to "claimed by j". Throws exception if already claimed.
        if (is_claimed()){
            throw new IllegalCallerException("erreur : claim -> Artifact is already claimed !");
        }else{
            set_owner(j);
            null_pos();
        }
    }

    public boolean is_claimed(){
        //gets if the artifact has been claimed by someone or if it's still not.
        return position == null;
    }

    public Type get_element(){
        //gets the element of the artifact.
        return element;
    }

    public Joueur get_owner(){
        //gets the owner of the artifact (null if not owned).
        return owner;
    }

    public Coord get_pos(){
        //gets initial position of the artifact (null if claimed).
        if (is_claimed()){
            throw new IllegalCallerException("Erreur : get_pos -> l'artefact est deja recupere");
        }else {
            return position;
        }
    }
}
