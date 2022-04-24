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
    public FloodingCtrl contrEscape;
    public PlayerCtrl playerCtrl;
    public int maxCard;

    public EndTurnCtrl(Model model, View view, FloodingCtrl contrEscape) {
        super(model, view);
        this.contrEscape = contrEscape;
        this.maxCard = 5;
    }

    public void nexTurn() {
        model.nextPlayer();
        model.getActPlayer().setState(Player.Action.Move);
        model.getActPlayer().setAction(3);
        contrEscape.flooding();
    }

    public void actionPerformed(ActionEvent e) {
        if (contrEscape.getEscape() == null) {
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
            if (model.getActPlayer().getNbCards() > maxCard) {
                model.getActPlayer().setState(Player.Action.Discard);
                playerCtrl.selectedPlayer = model.getActPlayer();
            } else {
                nexTurn();
            }
            view.repaint();
        }
    }
}
