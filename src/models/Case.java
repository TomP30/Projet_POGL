package models;

import java.awt.*;

/**
 * Zone
 */
public class Case {

    private Point coord;
    private int waterLvl;
    private static int maxWaterLvl = 2;

    public Case(int x, int y) {
        this.coord = new Point(x,y);
        this.waterLvl = 0;
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

    public int getWaterLvl() {
        return this.waterLvl;
    }

    public int getMaxWaterLvl(){
        return maxWaterLvl;
    }

    public boolean moove(){
        return maxWaterLvl > this.waterLvl;
    }

    public boolean dryable(){
        return moove() && this.waterLvl > 0;
    }

    public void dry() {
        if (waterLvl > 0)
            this.waterLvl -= 1;
    }

    public void drown() {
        if (waterLvl < maxWaterLvl)
            this.waterLvl += 1;
    }
}
