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

    // Constructeur
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

    // Setter
    public void setPosition(Case C) {
        this.position = C;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void resetAction() {
        this.nbActions = 3;
    }

    public void setAction(int n) {
        this.nbActions = n;
    }

    public void setState(Action a) {
        this.action = a;
    }

    public Action getState() {
        return this.action;
    }

    public void addcard(Card c) {
        this.hand.put(c, this.hand.get(c) + 1);
    }

    // Getter
    public String getImage(){ return this.image; }

    public Case getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<Card, Integer> getAllCards() {
        return this.hand;
    }

    public int getNbActions() {
        return this.nbActions;
    }

    public Integer getCards(Card c) {
        return this.hand.get(c);
    }

    public int getNbCards() {
        int total = 0;
        for (Integer nbCard : this.hand.values()) {
            total += nbCard;
        }
        return total;
    }

    // méthode
    public boolean possibleExchange(Player player, Card card) {
        return getState() == Action.Exchange
                && player.getPosition() == getPosition()
                && player != this
                && getCards(card) > 0;
    }

    public void changePosition(Case C) {
        this.position = C;
    }

    public void dryUp() {
        this.nbActions -= 1;
    }

    public void useCard(Card c) {
        this.hand.put(c, this.hand.get(c) - 1);
    }

    public Boolean isNeight(Case move, Case base) {
        return move.getFlood() < move.getMaxFlood() && Math.abs(move.getX() - base.getX()) +
                Math.abs(move.getY() - base.getY()) < 2;
    }

    public int getWeightNeight(int weightNeight, int lastWeight, Case C) {
        if (weightNeight + 1 < lastWeight) {
            return weightNeight + 1;
        } else {
            return lastWeight;
        }
    }

    public ArrayList<Point> neigboursMove(Model model) {
        ArrayList<Point> neigbours = new ArrayList<Point>();
        neigbours.add(new Point(getPosition().getX() - 1, getPosition().getY()));
        neigbours.add(new Point(getPosition().getX() + 1, getPosition().getY()));
        neigbours.add(new Point(getPosition().getX(), getPosition().getY() - 1));
        neigbours.add(new Point(getPosition().getX(), getPosition().getY() + 1));
        return neigbours;
    }

    public ArrayList<Point> neigboursDry(Model model) {
        return neigboursMove(model);
    }
}

