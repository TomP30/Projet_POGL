package controllers;

import models.Model;
import models.Case;
import models.Player;
import views.View;

import java.awt.*;
import java.util.ArrayList;

public class ContrFlooding extends Controller {
    public int nbInondation;
    private Player escape;

    public ContrFlooding(Model model, View view) {
        super(model, view);
        nbInondation = model.getFloodLevel().innondationRate();
        this.escape = null;
    }

    public Player getEscape() {
        return this.escape;
    }

    public void setEscape() {
        Player escape = null;
        for (Player player : model.getPlayers()) {
            if (player.getState() == Player.State.ESCAPE) {
                escape = player;
            }
        }
        this.escape = escape;
    }

    private Boolean escape(Case zone, Player player) {
        ArrayList<Point> action = player.neigboursMove(this.model);

        return !action.isEmpty();
    }

    private Boolean gameOverCase(Case C) {
        return model.getTemple().contains(C) ||
                model.getHeliZone() == C;
    }

    public void flooding() {
        for (; nbInondation > 0; nbInondation--) {
            Case drownC = model.getPiocheWater().pick();
            drownC.drown();
            Boolean escape = false;
            if (drownC.getWaterLvl() == drownC.getMaxWaterLvl()) {
                if (gameOverCase(drownC)) {
                    model.setState(Model.State.LOSE);
                    view.gameOver();
                    break;
                }
                for (Player p : model.getPlayers()) {
                    if (drownC == p.getPosition()) {
                        p.setState(Player.State.ESCAPE);
                        if (escape(drownC, p)) {
                            escape = true;
                            p.setState(Player.State.ESCAPE);
                        } else {
                            model.setState(Model.State.LOSE);
                            view.gameOver();
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
        nbInondation = model.getFloodLevel().innondationRate();
    }
}
