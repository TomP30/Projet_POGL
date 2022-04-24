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
        if (model.getBoard().isValid(new Point(x, y)) && model.getCond() != Model.Condition.ENDLOST) {
            if (flooding.getEscape() != null) {
                Escape(x, y);
            } else if (model.getActivePlayer().getAction() == Player.Action.Move) {
                Move(x,y);
            } else if (model.getActivePlayer().getAction() == Player.Action.Drain) {
                Dry(x, y);
            }
        }
        this.view.repaint();
    }

    public void setPlayerCtrl(PlayerCtrl playerCtrl) {
        this.player = playerCtrl;
    }

    private void Move(int x, int y) {
        int[][] action = model.actionAmount(model.getActivePlayer());
        Case Cmove = model.getBoard().getCase(x, y);

        if (action[y][x] <= model.getActivePlayer().getAmount()
                && Cmove.movable()) {
            model.getActivePlayer().newPos(Cmove);
            model.getActivePlayer().setAmount(model.getActivePlayer().getAmount() - action[y][x]);
        }
    }

    private void Dry(int x, int y) {
        if (model.getActivePlayer().getAmount() > 0) {
            Case digZ = model.getBoard().getCase(x, y);
            ArrayList<Case> digZones = new ArrayList<Case>();
            ArrayList<Point> action = model.getActivePlayer().drains(this.model);
            action.add(new Point(model.getActivePlayer().getPosition().getX(),
                    model.getActivePlayer().getPosition().getY()));
            for (Point point : action) {
                digZones.add(model.getBoard().getCase(point.x, point.y));
            }

            if (digZ.getFlood() == 1 && digZones.contains(digZ)) {
                digZ.dry();
                model.getActivePlayer().drained();
            }
        }
    }

    private void Escape(int x, int y) {
        ArrayList<Point> neigbours = flooding.getEscape().moves(this.model);
        for (Point point : neigbours) {
            Case zone = model.getBoard().getCase(point.x, point.y);
            if (zone != null && zone.getFlood() != zone.getMaxFlood() && x == zone.getX() && y == zone.getY()) {
                flooding.getEscape().setAction(Player.Action.Move);
                flooding.getEscape().newPos(zone);
                flooding.setEscape();
                if (flooding.getEscape() == null) {
                    flooding.flooding();
                }
                return;
            }
        }
    }
}
