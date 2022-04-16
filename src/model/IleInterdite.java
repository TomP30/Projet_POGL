package model;

import view.*;

import java.util.Random;

public class IleInterdite {
    public static void main(String[] args) throws Exception{
        //initialization of the main board
        Random rand = new Random();
        Coord heli = new Coord(rand);
        while (!heli.isValidCoord()){
            heli = new Coord(rand);
        }
        Coord[] Art = new Coord[4];
        for (int i =0;i<4;i++){
            boolean flag = false;
            Coord c = new Coord(rand);
            if(i==0){
                while (!flag){
                    c = new Coord(rand);
                    flag = !(c.equals(heli)) && c.isValidCoord();
                }
            }else{
                Coord [] sub_art = new Coord[i];
                for (int j = 0;j<i;j++){
                    sub_art[j] = Art[j];
                }
                while (!flag){
                    c = new Coord(rand);
                    flag = c.isValidCoord() && !(c.equals(heli) || c.is_in_list(sub_art));
                }
            }
            Art[i] = c;
        }

        Grille grille = new Grille(heli,Art);

        //initialization of the graphics content
        Fenetre window = new Fenetre("IleInterdite");
        FinDeTour TurnEnd = new FinDeTour(grille);

        window.ajtElem(grille);
        window.ajtElem(TurnEnd);

        window.draw();
    }
}
