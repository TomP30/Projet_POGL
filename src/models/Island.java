package models;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Ile
 */
public class Island {

    private ArrayList<ArrayList<Case>> grid;
    private Random rand;

    private final int width;
    private final int height;

    public Island(String map) throws IOException {
        BufferedReader lecteur = new BufferedReader(new FileReader(map));

        this.width = Integer.parseInt(lecteur.readLine());
        this.height = Integer.parseInt(lecteur.readLine());
        grid = new ArrayList<ArrayList<Case>>();
        for (int j = 0; j < height; j++) {
            ArrayList<Case> line = new ArrayList<Case>();
            String lineMap = lecteur.readLine();
            for (int i = 0; i < width; i++) {
                if (lineMap.charAt(i) == '#') {
                    line.add(new Case(i, j));
                } else {
                    line.add(null);
                }
            }
            grid.add(line);
        }

        lecteur.close();

        rand = new Random();
    }

    public Island() {
        this.width = 6;
        this.height = 6;
        grid = new ArrayList<ArrayList<Case>>();
        for (int j = 0; j < height; j++) {
            ArrayList<Case> line = new ArrayList<Case>();
            for (int i = 0; i < width; i++) {
                if (Math.abs(i - (width - 1) / 2.) +
                        Math.abs(j - (height - 1) / 2.) <= height / 2.) {
                    line.add(new Case(i, j));
                } else {
                    line.add(null);
                }
            }
            grid.add(line);
        }
        rand = new Random();
    }

    public Case getCase(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return null;
        }
        return grid.get(y).get(x);
    }

    public Point getGridSize() {
        return new Point(this.width, this.height);
    }

    public ArrayList<ArrayList<Case>> getGrid() {
        return this.grid;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public ArrayList<Integer> getCoordLine(int y) {
        ArrayList<Integer> s = new ArrayList<Integer>();
        for (int index = 0; index < grid.get(y).size(); index++) {
            if (grid.get(y).get(index) != null) {
                s.add(index);
            }
        }
        return s;
    }

    public Case getRandomCase() {
        int y = rand.nextInt(this.getGridSize().y);
        int x = this.getCoordLine(y).get(rand.nextInt(this.getCoordLine(y).size()));
        return this.getCase(x, y);
    }

    public ArrayList<Case> neighbours(Case C) {
        ArrayList<Case> neighbours = new ArrayList<Case>();
        neighbours.add(this.getCase(C.getCoord().x, C.getCoord().y + 1));
        neighbours.add(this.getCase(C.getCoord().x, C.getCoord().y - 1));
        neighbours.add(this.getCase(C.getCoord().x + 1, C.getCoord().y));
        neighbours.add(this.getCase(C.getCoord().x - 1, C.getCoord().y));
        return neighbours;
    }

    public boolean inMap(Point pos) {
        return pos.y >= 0 && pos.y < getGridSize().y && pos.x >= 0 &&
                pos.x < getGridSize().x &&
                this.grid.get(pos.y).get(pos.x) != null;
    }
}
