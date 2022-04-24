package controllers;

import java.awt.Point;

import java.util.ArrayList;

import models.Model;
import models.Case;
import models.Player;
import views.View;

/**
 * ContrGrid
 */
public class BoardCtrl extends Controler {
    private FloodingCtrl flooding;
    private PlayerCtrl player;

    public BoardCtrl(Model model, View view, FloodingCtrl floodingCtrl) {
        super(model, view);
        this.flooding = floodingCtrl;
    }

    public void click(int x, int y) {
        if (model.getIsland().inMap(new Point(x, y)) && model.getState() != Model.Condition.ENDLOST) {
            if (flooding.getEscape() != null) {
                clickEscape(x, y);
            } else if (model.getActPlayer().getState() == Player.Action.Move) {
                clickMove(x,y);
            } else if (model.getActPlayer().getState() == Player.Action.Drain) {
                clickDry(x, y);
            }
        }
        this.view.repaint();
    }

    public void setContrPlayer(PlayerCtrl playerCtrl) {
        this.player = playerCtrl;
    }

    private void clickMove(int x, int y) {
        int[][] action = model.nbAction(model.getActPlayer());
        Case Cmove = model.getIsland().getCase(x, y);

        if (action[y][x] <= model.getActPlayer().getNbActions()
                && Cmove.movable()) {
            model.getActPlayer().changePosition(Cmove);
            model.getActPlayer().setAction(model.getActPlayer().getNbActions() - action[y][x]);
        }
    }

    private void clickNavigator(int x, int y) {
        if (player.selectP == null || player.selectP == null
                || player.selectP == model.getActPlayer()) {
            clickMove(x, y);
        } else {
            int[][] action = model.nbActionNormal(player.selectP.getPosition().getX(),
                    player.selectP.getPosition().getY());
            if (action[y][x] <= 2 && model.getActPlayer().getNbActions() > 0) {
                model.getActPlayer().setAction(model.getActPlayer().getNbActions() - 1);
                player.selectP.changePosition(model.getIsland().getCase(x, y));
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

            if (digZ.getFlood() == 1 && digZones.contains(digZ)) {
                digZ.dry();
                model.getActPlayer().dryUp();
            }
        }
    }

    private void clickEscape(int x, int y) {
        ArrayList<Point> neigbours = flooding.getEscape().neigboursMove(this.model);
        for (Point point : neigbours) {
            Case zone = model.getIsland().getCase(point.x, point.y);
            if (zone != null && zone.getFlood() != zone.getMaxFlood() && x == zone.getX() && y == zone.getY()) {
                flooding.getEscape().setState(Player.Action.Move);
                flooding.getEscape().changePosition(zone);
                flooding.setEscape();
                if (flooding.getEscape() == null) {
                    flooding.flooding();
                }
                return;
            }
        }
    }
}
