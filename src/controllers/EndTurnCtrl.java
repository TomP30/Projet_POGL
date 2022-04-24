package controllers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import models.Model;
import models.Player;
import models.Card;
import views.View;

/**
 * ContrEndTurn
 */
public class EndTurnCtrl extends Controler implements ActionListener {
    public FloodingCtrl escape;
    public PlayerCtrl player;
    public int hand;

    public EndTurnCtrl(Model model, View view, FloodingCtrl escape) {
        super(model, view);
        this.escape = escape;
        this.hand = 5;
    }

    public void nextTurn() {
        model.nextPlayer();
        model.getActivePlayer().setAction(Player.Action.Move);
        model.getActivePlayer().setAmount(3);
        escape.flooding();
    }

    public void actionPerformed(ActionEvent e) {
        if (escape.getEscape() == null) {
            if (model.getActivePlayer().getAction() != Player.Action.Discard) {
                for (int i = 0; i < 2; i++) {
                    Card actual = model.getDraw().pick();
                    if (actual.equals(Card.Flood)) {
                        model.getFloodLvl().lvlIncr();
                        if(model.getFloodLvl().getLvl() == 9) {
                            model.setCond(Model.Condition.ENDLOST);
                            view.Lose();
                        }
                        model.getDrawFlood().addCimetery();
                        model.getDraw().discard(actual);
                    } else {
                        model.getActivePlayer().addcard(actual);
                    }
                }
            }
            if (model.getActivePlayer().getCardsAmount() > hand) {
                model.getActivePlayer().setAction(Player.Action.Discard);
                player.selectP = model.getActivePlayer();
            } else {
                nextTurn();
            }
            view.repaint();
        }
    }
}
