package controllers;

import java.awt.Point;

import java.util.ArrayList;

import models.Card;
import models.Model;
import models.Case;
import models.Player;
import views.View;

/**
 * ContrGrid
 */
public class ContrGrid extends Controller {
    private ContrFlooding contrFlooding;
    private ContrPlayer contrPlayer;

    public ContrGrid(Model model, View view, ContrFlooding contrFlooding) {
        super(model, view);
        this.contrFlooding = contrFlooding;
    }

    public void click(int x, int y) {
        if (model.getIsland().inMap(new Point(x, y)) && model.getState() != Model.State.LOSE) {
            if (contrFlooding.getEscape() != null) {
                clickEscape(x, y);
            } else if (model.getActPlayer().getState() == Player.State.MOVING) {
                clickMove(x,y);
            } else if (model.getActPlayer().getState() == Player.State.DRY) {
                clickDry(x, y);
            }
        }
        this.view.repaint();
    }

    public void setContrPlayer(ContrPlayer contrPlayer) {
        this.contrPlayer = contrPlayer;
    }

    private void clickMove(int x, int y) {
        int[][] action = model.nbAction(model.getActPlayer());
        Case Cmove = model.getIsland().getCase(x, y);

        if (action[y][x] <= model.getActPlayer().getNbActions()
                && Cmove.moove()) {
            model.getActPlayer().changePosition(Cmove);
            model.getActPlayer().setAction(model.getActPlayer().getNbActions() - action[y][x]);
        }
    }

    private void clickNavigator(int x, int y) {
        if (contrPlayer.selectedPlayer == null || contrPlayer.selectedPlayer == null
                || contrPlayer.selectedPlayer == model.getActPlayer()) {
            clickMove(x, y);
        } else {
            int[][] action = model.nbActionNormal(contrPlayer.selectedPlayer.getPosition().getX(),
                    contrPlayer.selectedPlayer.getPosition().getY());
            if (action[y][x] <= 2 && model.getActPlayer().getNbActions() > 0) {
                model.getActPlayer().setAction(model.getActPlayer().getNbActions() - 1);
                contrPlayer.selectedPlayer.changePosition(model.getIsland().getCase(x, y));
            }
        }
    }

    private void clickDry(int x, int y) {
        if (model.getActPlayer().getNbActions() > 0) {
            Case digZ = model.getIsland().getCase(x, y);
            ArrayList<Case> digZones = new ArrayList<Case>();
            ArrayList<Point> action = model.getActPlayer().neigboursDry(this.model);
            action.add(new Point(model.getActPlayer().getPosition().getX(),
                    model.getActPlayer().getPosition().getY()));
            for (Point point : action) {
                digZones.add(model.getIsland().getCase(point.x, point.y));
            }

            if (digZ.getWaterLvl() == 1 && digZones.contains(digZ)) {
                digZ.dry();
                model.getActPlayer().dryUp();
            }
        }
    }

    private void clickEscape(int x, int y) {
        ArrayList<Point> neigbours = contrFlooding.getEscape().neigboursMove(this.model);
        for (Point point : neigbours) {
            Case zone = model.getIsland().getCase(point.x, point.y);
            if (zone != null && zone.getWaterLvl() != zone.getMaxWaterLvl() && x == zone.getX() && y == zone.getY()) {
                contrFlooding.getEscape().setState(Player.State.MOVING);
                contrFlooding.getEscape().changePosition(zone);
                contrFlooding.setEscape();
                if (contrFlooding.getEscape() == null) {
                    contrFlooding.flooding();
                }
                return;
            }
        }
    }
}
