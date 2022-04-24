package models;

import java.util.ArrayList;

public class Flood {
    private ArrayList<Boolean> level;

    public Flood(int lvl) {
        this.level = new ArrayList<Boolean>(7);
        for (int i = 0; i < 7; i++) {
            this.level.add(false);
        }
        this.level.set(lvl, true);
    }

    public int getLvl() {
        return level.indexOf(true);
    }

    public void lvlIncr() {
        setLvl(getLvl() + 1);
    }

    public void setLvl(int n) {
        this.level.set(this.getLvl(), false);
        this.level.set(n, true);
    }

    public int floodRate() {
        if (this.getLvl() < 2)
            return 2;
        else if (this.getLvl() < 5)
            return 3;
        return 4;
    }
}
