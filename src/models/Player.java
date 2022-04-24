package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Players
 */
public class Player {
    public static enum Action {
        Move,
        Drain,
        Exchange,
        Escape,
        Discard
    }
    public static enum colour {
        green,
        red,
        blue,
        yellow,
    }

    protected Case position;
    protected int nbActions;
    protected Action action;
    protected String name;
    protected HashMap<Card, Integer> hand;
    private String image;

    //Constructors
    public Player(String name, Case zone, int i) {
        this.name = name;
        this.hand = new HashMap<Card, Integer>();
        this.hand.put(Card.Wind, 0);
        this.hand.put(Card.Wave, 0);
        this.hand.put(Card.Fire, 0);
        this.hand.put(Card.Stone, 0);
        this.nbActions = 3;
        this.position = zone;
        this.action = Action.Move;
        this.image = colour.values()[i].toString()+".png";
    }

    //Setters
    public void resetAction() {
        this.nbActions = 3;
    }

    public void setAmount(int n) {
        this.nbActions = n;
    }

    public void setAction(Action a) {
        this.action = a;
    }

    public Action getAction() {
        return this.action;
    }

    public void addcard(Card c) {
        this.hand.put(c, this.hand.get(c) + 1);
    }

    //Getters
    public String getImage(){ return this.image; }

    public Case getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<Card, Integer> getHand() {
        return this.hand;
    }

    public int getAmount() {
        return this.nbActions;
    }

    public Integer getCards(Card c) {
        return this.hand.get(c);
    }

    public int getCardsAmount() {
        int tot = 0;
        for (Integer nbCard : this.hand.values()) {
            tot += nbCard;
        }
        return tot;
    }

    //methods
    public boolean Exchangeable(Player player, Card card) {
        return getAction() == Action.Exchange && player.getPosition() == getPosition() && player != this && getCards(card) > 0;
    }

    public void newPos(Case C) {
        this.position = C;
    }

    public void drained() {
        this.nbActions -= 1;
    }

    public void useCard(Card c) {
        this.hand.put(c, this.hand.get(c) - 1);
    }

    public Boolean isNeigh(Case to, Case from) {
        return to.getFlood() < to.getMaxFlood() && Math.abs(to.getX() - from.getX()) + Math.abs(to.getY() - from.getY()) < 2;
    }

    public int getWeight(int actual, int last, Case C) {
        if (actual + 1 < last) {
            return actual + 1;
        } else {
            return last;
        }
    }

    public ArrayList<Point> moves(Model M) {
        ArrayList<Point> neigbours = new ArrayList<Point>();
        neigbours.add(new Point(getPosition().getX() - 1, getPosition().getY()));
        neigbours.add(new Point(getPosition().getX() + 1, getPosition().getY()));
        neigbours.add(new Point(getPosition().getX(), getPosition().getY() - 1));
        neigbours.add(new Point(getPosition().getX(), getPosition().getY() + 1));
        return neigbours;
    }

    public ArrayList<Point> drains(Model M) {
        return moves(M);
    }
}

