package models;

import java.util.ArrayList;

public class Flood {
    private ArrayList<Boolean> level;

    public Flood(int niv) {
        this.level = new ArrayList<Boolean>(10);
        for (int i = 0; i < 10; i++) {
            this.level.add(false);
        }
        this.level.set(niv, true);
    }

    public int getLvl() {
        return level.indexOf(true);
    }

    public void incrementLvl() {
        setLvl(getLvl() + 1);
    }

    public void setLvl(int i) {
        this.level.set(this.getLvl(), false);
        this.level.set(i, true);
    }

    public int innondationRate() {
        if (this.getLvl() < 2)
            return 2;
        else if (this.getLvl() < 5)
            return 3;
        else if (this.getLvl() < 7)
            return 4;
        return 5;
    }
}
