package controllers;

import models.Model;
import models.Case;
import models.Player;
import views.View;

import java.awt.*;
import java.util.ArrayList;

public class FloodingCtrl extends Controler {
    public int flood;
    private Player escape;

    public FloodingCtrl(Model model, View view) {
        super(model, view);
        flood = model.getFloodLvl().floodRate();
        this.escape = null;
    }

    public Player getEscape() {
        return this.escape;
    }

    public void setEscape() {
        Player escape = null;
        for (Player player : model.getPlayers()) {
            if (player.getAction() == Player.Action.Escape) {
                escape = player;
            }
        }
        this.escape = escape;
    }

    private Boolean escape(Player p) {
        ArrayList<Point> act = p.moves(this.model);

        return !act.isEmpty();
    }

    private Boolean gameOverCase(Case C) {
        return model.getTreasure().contains(C) ||
                model.getHeliport() == C;
    }

    public void flooding() {
        for (; flood > 0; flood--) {
            Case drown = model.getDrawFlood().pick();
            drown.drown();
            Boolean escape = false;
            if (drown.getFlood() == drown.getMaxFlood()) {
                if (gameOverCase(drown)) {
                    model.setCond(Model.Condition.ENDLOST);
                    view.Lose();
                    break;
                }
                for (Player p : model.getPlayers()) {
                    if (drown == p.getPosition()) {
                        p.setAction(Player.Action.Escape);
                        if (escape(p)) {
                            escape = true;
                            p.setAction(Player.Action.Escape);
                        } else {
                            model.setCond(Model.Condition.ENDLOST);
                            view.Lose();
                            break;
                        }
                    }
                }
            }
            if (escape) {
                setEscape();
                return;
            }
        }
        flood = model.getFloodLvl().floodRate();
    }
}
