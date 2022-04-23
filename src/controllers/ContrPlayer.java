package controllers;

import java.util.ArrayList;

import models.Card;
import models.Model;
import models.Player;
import views.View;

/**
 * ContrPlayer
 */
public class ContrPlayer extends Controller {
    public Player selectedPlayer;
    public ArrayList<Player> playersHeli;
    public Card selectedCard;
    public ContrEndTurn contrEndTurn;

    public ContrPlayer(Model model, View view) {
        super(model, view);
        this.playersHeli = new ArrayList<Player>();
    }

    public void playerClick(Player player) {
        Player lastPlayer = this.selectedPlayer;
        this.selectedPlayer = player;

        if (this.selectedPlayer != null && this.selectedCard != null
                && this.model.getActPlayer().possibleExchange(this.selectedPlayer, this.selectedCard)) {
            if (this.model.getActPlayer().getCards(this.selectedCard) >= 1) {
                this.model.getActPlayer().useCard(this.selectedCard);
                this.selectedPlayer.addcard(this.selectedCard);

                this.model.getActPlayer().setState(Player.State.MOVING);
                this.selectedCard = null;
                this.selectedPlayer = lastPlayer;
                view.repaint();
                return;
            }
        }
        this.selectedCard = null;
        view.repaint();
    }

    private void selectedPlayerHeli(Player player) {
        if (this.playersHeli.contains(player)) {
            this.playersHeli.remove(player);
        } else {
            for (Player p : this.playersHeli) {
                if (p.getPosition() != player.getPosition()) {
                    view.repaint();
                    return;
                }
            }
            this.playersHeli.add(player);
        }
        view.repaint();
    }

    private void throwClick(Card card) {
        this.selectedCard = null;
        if (model.getActPlayer().getCards(card) >= 1) {
            model.getActPlayer().useCard(card);
            if (model.getActPlayer().getNbCards() <= contrEndTurn.maxCard) {
                contrEndTurn.nexTurn();
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
        this.selectedCard = card;
        if (model.getActPlayer().getState() == Player.State.THROW) {
            throwClick(card);
        }
        view.repaint();
    }
}
