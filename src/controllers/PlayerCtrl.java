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

    public void click(Player player) {
        Player lastPlayer = this.selectP;
        this.selectP = player;

        if (this.selectP != null && this.selectC != null
                && this.model.getActivePlayer().Exchangeable(this.selectP, this.selectC)) {
            if (this.model.getActivePlayer().getCards(this.selectC) >= 1) {
                this.model.getActivePlayer().useCard(this.selectC);
                this.selectP.addcard(this.selectC);

                this.model.getActivePlayer().setAction(Player.Action.Move);
                this.selectC = null;
                this.selectP = lastPlayer;
                view.repaint();
                return;
            }
        }
        this.selectC = null;
        view.repaint();
    }

    private void discard(Card card) {
        this.selectC = null;
        if (model.getActivePlayer().getCards(card) >= 1) {
            model.getActivePlayer().useCard(card);
            if (model.getActivePlayer().getCardsAmount() <= endTurn.hand) {
                endTurn.nextTurn();
            }
        }
    }

    private Boolean victoryCheck() {
        boolean ownH = false;
        for (Player p : model.getPlayers()) {
            if (p.getPosition() != model.getHeliport()) {
                return false;
            }
        }
        return !model.getTreasureState().contains(false);
    }

    public void cardClick(Card card) {
        this.selectC = card;
        if (model.getActivePlayer().getAction() == Player.Action.Discard) {
            discard(card);
        }
        view.repaint();
    }
}
