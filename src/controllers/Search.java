package controllers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import models.Card;
import models.Model;
import views.View;

/**
 * ContrSearch
 */
public class Search extends Controler implements ActionListener {

    public Search(Model model, View view) {
        super(model, view);
    }

    public Boolean claimable() {
        int index = model.getTreasure().indexOf(model.getActivePlayer().getPosition());
        if (index != -1) {
            return true;
        }
        return false;

    }

    public void actionPerformed(ActionEvent e) {
        if (model.getActivePlayer().getAmount() > 0 && claimable()) {
            model.getActivePlayer().setAmount(
                    model.getActivePlayer().getAmount() - 1);
            int index = model.getTreasure().indexOf(model.getActivePlayer().getPosition());
            if(model.getActivePlayer().getCards(Card.getTreasureCard(index)) >= 4){
                model.getTreasure().set(index, null);
                model.getTreasureState().set(index, true);
                view.repaint();
                model.getActivePlayer().getHand().replace(Card.getTreasureCard(index), model.getActivePlayer().getCards(Card.getTreasureCard(index)) - 4);
            }
        }
    }
}
