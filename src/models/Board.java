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
public class Board {

    private ArrayList<ArrayList<Case>> board;

    private final int width;
    private final int height;

    public Board(String map) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(map));

        this.width = Integer.parseInt(reader.readLine());
        this.height = Integer.parseInt(reader.readLine());
        board = new ArrayList<ArrayList<Case>>();
        for (int j = 0; j < height; j++) {
            ArrayList<Case> line = new ArrayList<Case>();
            String lineMap = reader.readLine();
            for (int i = 0; i < width; i++) {
                if (lineMap.charAt(i) == '#') {
                    line.add(new Case(i, j));
                } else {
                    line.add(null);
                }
            }
            board.add(line);
        }
        reader.close();
    }

    public Board() {
        this.width = 6;
        this.height = 6;
        board = new ArrayList<ArrayList<Case>>();
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
            board.add(line);
        }
        Random rand = new Random();
    }

    public Case getCase(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return null;
        }
        return board.get(y).get(x);
    }

    public Point getSize() {
        return new Point(this.width, this.height);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public ArrayList<Integer> getLine(int y) {
        ArrayList<Integer> s = new ArrayList<Integer>();
        for (int index = 0; index < board.get(y).size(); index++) {
            if (board.get(y).get(index) != null) {
                s.add(index);
            }
        }
        return s;
    }

    public Case getRandomCase() {
        Random rand = new Random();
        int y = rand.nextInt(this.getSize().y);
        int x = this.getLine(y).get(rand.nextInt(this.getLine(y).size()));
        return this.getCase(x, y);
    }

    public boolean isValid(Point pos) {
        return pos.y >= 0 && pos.y < getSize().y && pos.x >= 0 &&
                pos.x < getSize().x &&
                this.board.get(pos.y).get(pos.x) != null;
    }
}
