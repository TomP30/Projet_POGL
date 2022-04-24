package models;

import java.awt.*;

/**
 * Zone
 */
public class Case {

    private Point coord;
    private int flood;
    private static int floodLvl = 2;

    public Case(int x, int y) {
        this.coord = new Point(x,y);
        this.flood = 0;
    }

    public Point getCoord() {
        return this.coord;
    }

    public int getX() {
        return this.coord.x;
    }

    public int getY() {
        return this.coord.y;
    }

    public int getFlood() {
        return this.flood;
    }

    public int getMaxFlood(){
        return floodLvl;
    }

    public boolean movable(){
        return floodLvl > this.flood;
    }

    public boolean dryable(){
        return movable() && this.flood > 0;
    }

    public boolean drownable() { return flood < floodLvl; }

    public void dry() {
        if (dryable()) {
            this.flood--;
        }
    }

    public void drown() {
        if (drownable()) {
            this.flood++;
        }
    }
}
