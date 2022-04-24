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

    public void click(Player P) {
        Player lastPlayer = this.selectP;
        this.selectP = P;

        if (this.selectP != null && this.selectC != null && this.model.getActivePlayer().Exchangeable(this.selectP, this.selectC) && this.model.getActivePlayer().getCards(this.selectC) >= 1) {
            this.model.getActivePlayer().useCard(this.selectC);
            this.selectP.addcard(this.selectC);
            this.model.getActivePlayer().setAction(Player.Action.Move);
            this.selectC = null;
            this.selectP = lastPlayer;
            view.repaint();
            return;
        }
        this.selectC = null;
        view.repaint();
    }

    private void discard(Card C) {
        this.selectC = null;
        if (model.getActivePlayer().getCards(C) >= 1) {
            model.getActivePlayer().useCard(C);
            if (model.getActivePlayer().getCardsAmount() <= endTurn.hand) {
                endTurn.nextTurn();
            }
        }
    }

    public void cardClick(Card C) {
        this.selectC = C;
        if (model.getActivePlayer().getAction() == Player.Action.Discard) {
            discard(C);
        }
        view.repaint();
    }
}
