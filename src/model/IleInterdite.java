package model;

import view.*;
public class IleInterdite {
    public static void main(String[] args) throws Exception{
        //initialization of the main board
        Coord heli = new Coord(3,4);
        Coord[] Art = new Coord[4];
        Art[0] = new Coord(2,3);
        Art[1] = new Coord(3,2);
        Art[2] = new Coord(3,4);
        Art[3] = new Coord(4,3);
        Grille grille = new Grille(heli,Art);

        //initialization of the graphics content
        Fenetre window = new Fenetre("IleInterdite");
        FinDeTour TurnEnd = new FinDeTour(grille);

        window.ajtElem(grille);
        window.ajtElem(TurnEnd);
    }
}
