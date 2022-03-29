package model;

import static java.lang.Math.abs;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.Random;

public class Coord {
    //simple int Coordinates system with d1
    private int x,y;

    public Coord(int abs, int ord) throws Exception {
        //Constructor of Coord class, with given abscissa and ordinate.
        if (abs <0 || ord<0){
            throw new Exception("x et y doivent être positifs !");
        }
        x = abs;
        y = ord;
    }

    public int get_x(){
        //getter of x Coord
        return x;
    }

    public int get_y(){
        //getter of y Coord
        return y;
    }

    public void set_x(int abs) throws Exception {
        //setter of x Coord
        if (abs < 0) {
            throw new Exception("x doit être positif !");
        }else{
            x = abs;
        }
    }

    public void set_y(int ord) throws Exception {
        //setter of y Coord
        if (ord < 0) {
            throw new Exception("y doit être positif !");
        }else{
            y = ord;
        }
    }

    public int dist(Coord c2){
        //Computes the simple distance (d1) between this point and another with the given coordinates.
        return abs(x-c2.get_x()) + abs(y - c2.get_y());
    }

    public boolean equals(Coord c2){ return x == c2.get_x() && y == c2.get_y();}

    public boolean isValidCoord() throws Exception {
        return get_y()>=0 && get_y()<6
            && get_x()>=0 && get_x()<6
            && dist(new Coord(0,0)) >=2
            && dist(new Coord(0,5)) >=2
            && dist(new Coord(5,0)) >=2
            && dist(new Coord(5,5)) >=2;
    }
}

