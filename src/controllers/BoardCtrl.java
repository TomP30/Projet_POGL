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

    public BoardCtrl(Model model, View view, FloodingCtrl flooding) {
        super(model, view);
        this.flooding = flooding;
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

    public void setPlayerCtrl(PlayerCtrl player) {
        this.player = player;
    }

    private void Move(int x, int y) {
        int[][] act = model.actionAmount(model.getActivePlayer());
        Case C = model.getBoard().getCase(x, y);

        if (act[y][x] <= model.getActivePlayer().getAmount() && C.movable()) {
            model.getActivePlayer().newPos(C);
            model.getActivePlayer().setAmount(model.getActivePlayer().getAmount() - act[y][x]);
        }
    }

    private void Dry(int x, int y) {
        if (model.getActivePlayer().getAmount() > 0) {
            Case C = model.getBoard().getCase(x, y);
            ArrayList<Case> Cs = new ArrayList<Case>();
            ArrayList<Point> act = model.getActivePlayer().drains(this.model);
            act.add(new Point(model.getActivePlayer().getPosition().getX(),
                    model.getActivePlayer().getPosition().getY()));
                for (Point point : act) {
                Cs.add(model.getBoard().getCase(point.x, point.y));
            }

            if (C.getFlood() == 1 && Cs.contains(C)) {
                C.dry();
                model.getActivePlayer().drained();
            }
        }
    }

    private void Escape(int x, int y) {
        ArrayList<Point> neigh = flooding.getEscape().moves(this.model);
        for (Point pts : neigh) {
            Case C = model.getBoard().getCase(pts.x, pts.y);
            if (C != null && C.getFlood() != C.getMaxFlood() && x == C.getX() && y == C.getY()) {
                flooding.getEscape().setAction(Player.Action.Move);
                flooding.getEscape().newPos(C);
                flooding.setEscape();
                if (flooding.getEscape() == null) {
                    flooding.flooding();
                }
                return;
            }
        }
    }
}
