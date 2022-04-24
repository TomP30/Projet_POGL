package controllers;

import java.util.ArrayList;

import models.Card;
import models.Model;
import models.Player;
import views.View;

/**
 * ContrPlayer
 */
public class PlayerCtrl extends Controler {
    public Player selectP;
    public ArrayList<Player> reachedHeli;
    public Card selectC;
    public EndTurnCtrl endTurn;

    public PlayerCtrl(Model model, View view) {
        super(model, view);
        this.reachedHeli = new ArrayList<Player>();
    }

    public void playerClick(Player player) {
        Player lastPlayer = this.selectP;
        this.selectP = player;

        if (this.selectP != null && this.selectC != null
                && this.model.getActPlayer().possibleExchange(this.selectP, this.selectC)) {
            if (this.model.getActPlayer().getCards(this.selectC) >= 1) {
                this.model.getActPlayer().useCard(this.selectC);
                this.selectP.addcard(this.selectC);

                this.model.getActPlayer().setState(Player.Action.Move);
                this.selectC = null;
                this.selectP = lastPlayer;
                view.repaint();
                return;
            }
        }
        this.selectC = null;
        view.repaint();
    }

    private void selectedPlayerHeli(Player player) {
        if (this.reachedHeli.contains(player)) {
            this.reachedHeli.remove(player);
        } else {
            for (Player p : this.reachedHeli) {
                if (p.getPosition() != player.getPosition()) {
                    view.repaint();
                    return;
                }
            }
            this.reachedHeli.add(player);
        }
        view.repaint();
    }

    private void throwClick(Card card) {
        this.selectC = null;
        if (model.getActPlayer().getCards(card) >= 1) {
            model.getActPlayer().useCard(card);
            if (model.getActPlayer().getNbCards() <= endTurn.hand) {
                endTurn.nexTurn();
            }
        }
    }

    private Boolean victoryCheck() {
        boolean ownH = false;
        for (Player p : model.getPlayers()) {
            if (p.getPosition() != model.getHeliZone()) {
                return false;
            }
        }
        return !model.getTreasureState().contains(false);
    }

    public void cardClick(Card card) {
        this.selectC = card;
        if (model.getActPlayer().getState() == Player.Action.Discard) {
            throwClick(card);
        }
        view.repaint();
    }
}
