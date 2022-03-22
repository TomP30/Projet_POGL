package model;

import static java.lang.Math.abs;

import javax.management.RuntimeErrorException;
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
}
