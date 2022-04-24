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

    public EndTurnCtrl(Model model, View view, FloodingCtrl contrEscape) {
        super(model, view);
        this.escape = contrEscape;
        this.hand = 5;
    }

    public void nexTurn() {
        model.nextPlayer();
        model.getActPlayer().setState(Player.Action.Move);
        model.getActPlayer().setAction(3);
        escape.flooding();
    }

    public void actionPerformed(ActionEvent e) {
        if (escape.getEscape() == null) {
            if (model.getActPlayer().getState() != Player.Action.Discard) {
                for (int i = 0; i < 2; i++) {
                    Card actualCard = model.getPiocheCard().pick();
                    if (actualCard.equals(Card.Flood)) {
                        model.getFloodLevel().incrementLvl();
                        if(model.getFloodLevel().getLvl() == 9) {
                            model.setState(Model.Condition.ENDLOST);
                            view.gameOver();
                        }
                        model.getPiocheWater().addDefausse();
                        model.getPiocheCard().sendToDefausse(actualCard);
                    } else {
                        model.getActPlayer().addcard(actualCard);
                    }
                }
            }
            if (model.getActPlayer().getNbCards() > hand) {
                model.getActPlayer().setState(Player.Action.Discard);
                player.selectP = model.getActPlayer();
            } else {
                nexTurn();
            }
            view.repaint();
        }
    }
}
