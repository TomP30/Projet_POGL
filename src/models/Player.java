package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Players
 */
public class Player {
    public static enum State {
        MOVING,
        DRY,
        EXCHANGE,
        ESCAPE,
        THROW
    }
    public static enum colour {
        greenPawn,
        redPawn,
        bluePawn,
        yellowPawn,
    }

    protected Case position;
    protected int nbActions;
    protected State state;
    protected String name;
    protected HashMap<Card, Integer> cards;
    protected boolean power;
    private String image;

    // Constructeur
    public Player(String name, Case zone, int i) {
        this.name = name;
        this.cards = new HashMap<Card, Integer>();
        this.cards.put(Card.AIR, 0);
        this.cards.put(Card.EAU, 0);
        this.cards.put(Card.FEU, 0);
        this.cards.put(Card.TERRE, 0);
        this.nbActions = 3;
        this.position = zone;
        this.state = State.MOVING;
        this.image = colour.values()[i].toString()+".png";
    }

    // Setter
    public void powerDown() {
        power = false;
    }

    public void powerUp() {
        power = true;
    }

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

    public void setState(State s) {
        this.state = s;
    }

    public State getState() {
        return this.state;
    }

    public void addcard(Card c) {
        this.cards.put(c, this.cards.get(c) + 1);
    }

    // Getter
    public String getImage(){ return this.image; }

    public boolean getPower() {
        return this.power;
    }

    public Case getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<Card, Integer> getAllCards() {
        return this.cards;
    }

    public int getNbActions() {
        return this.nbActions;
    }

    public Integer getCards(Card c) {
        return this.cards.get(c);
    }

    public int getNbCards() {
        int total = 0;
        for (Integer nbCard : this.cards.values()) {
            total += nbCard;
        }
        return total;
    }

    // méthode
    public boolean possibleExchange(Player player, Card card) {
        return getState() == State.EXCHANGE
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
        this.cards.put(c, this.cards.get(c) - 1);
    }

    public Boolean isNeight(Case move, Case base) {
        return move.getWaterLvl() < move.getMaxWaterLvl() && Math.abs(move.getX() - base.getX()) +
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

