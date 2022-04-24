package views;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controllers.FloodingCtrl;
import controllers.BoardCtrl;
import controllers.PlayerCtrl;
import models.Board;
import models.Model;
import models.Case;
import models.Player;

/**
 * Grid
 */
public class BoardView extends JPanel implements MouseListener {
    public BoardCtrl control;
    public FloodingCtrl floodingCtrl;
    public PlayerCtrl playerCtrl;

    private Model model;

    public ArrayList<Image> pawns;
    public ArrayList<Image> temples;
    public Image heliport;
    public Image gameOver;
    public Image victory;

    final public int widthJpanel;
    final public int heightJpanel;
    final public int sizeCase = 80;
    final public int sizeBorder = 5;

    public BoardView(Model m, View view, FloodingCtrl floodingCtrl) {
        this.model = m;
        int width = m.getIsland().getGridSize().x;
        int height = m.getIsland().getGridSize().y;
        this.widthJpanel = width * sizeCase + (width + 1) * sizeBorder;
        this.heightJpanel = height * sizeCase + (height + 1) * sizeBorder;

        setPreferredSize(new java.awt.Dimension(
                widthJpanel, heightJpanel));

        setBackground(new Color(1, 59, 204));
        addMouseListener(this);

        this.control = new BoardCtrl(m, view, floodingCtrl);
        this.floodingCtrl = floodingCtrl;

        pawns = new ArrayList<Image>();
        String path = "images/keys/";
        String pawnsPath[] = new String[] { path + "wind.png", path + "stone.png", path + "fire.png",
                path + "wave.png" };
        temples = new ArrayList<Image>();
        for (int i = 0; i < 4; i++) {
            Image img = new ImageIcon(pawnsPath[i]).getImage();
            img = img.getScaledInstance(sizeCase - 10, sizeCase - 10, Image.SCALE_DEFAULT);
            img.getHeight(null);
            temples.add(img);
        }

        this.heliport = new ImageIcon("images/heliport.png").getImage();
        this.heliport = heliport.getScaledInstance(sizeCase + 5, sizeCase + 5, Image.SCALE_DEFAULT);
        this.heliport.getHeight(null);

        this.gameOver = new ImageIcon("images/gameOver.png").getImage();
        this.victory = new ImageIcon("images/victory.png").getImage();
    }

    public void initPawn() {
        pawns.clear();
        String path = "images/pawns/";
        for (int i = 0; i < model.getPlayers().size(); i++) {
            Image img = new ImageIcon(path + model.getPlayers().get(i).getImage()).getImage();
            pawns.add(img);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawIsland(g);

        if (model.getState() == Model.Condition.PROGRESS) {
            if (floodingCtrl.getEscape() != null) {
                drawEscape(g);
            } else if (model.getActPlayer().getState() == Player.Action.Drain && model.getActPlayer().getNbActions() > 0) {
                drawDry(g);
            } else if (model.getActPlayer().getState() == Player.Action.Move) {
                drawMove(g);

            }
        }
        drawImages(g);
        drawPlayers(g);

        if (model.getState() == Model.Condition.ENDLOST) {
            drawGameOver(g);
        } else if (model.getState() == Model.Condition.ENDWON) {
            drawVictory(g);
        }
    }

    private void drawIsland(Graphics g) {
        Board board = this.model.getIsland();
        for (int y = 0; y < board.getGridSize().y; y++) {
            for (int x = 0; x < board.getGridSize().x; x++) {
                if (board.inMap(new Point(x, y))) {
                    g.setColor(new Color(200, 200, 200, getAlpha(board.getCase(x, y))));
                    int x_case = x * (sizeCase + sizeBorder) + sizeBorder;
                    int y_case = y * (sizeCase + sizeBorder) + sizeBorder;
                    g.fillRect(x_case, y_case, sizeCase, sizeCase);
                }
            }
        }
    }

    private void drawMove(Graphics g) {
        int[][] actionMove = model.nbAction(model.getActPlayer());
        Board board = this.model.getIsland();
        for (int y = 0; y < actionMove.length; y++) {
            for (int x = 0; x < actionMove[y].length; x++) {
                Case C = board.getCase(x, y);
                int x_case = x * (sizeCase + sizeBorder) + sizeBorder;
                int y_case = y * (sizeCase + sizeBorder) + sizeBorder;
                if (actionMove[y][x] <= model.getActPlayer().getNbActions() && actionMove[y][x] != 0
                        && C.movable()) {
                    drawOutline(g, x_case, y_case, new Color(241, 176, 13));
                }
            }
        }
    }

    private void drawDry(Graphics g) {
        ArrayList<Point> neigbours = model.getActPlayer().neigboursDry(this.model);
        neigbours.add(new Point(model.getActPlayer().getPosition().getX(), model.getActPlayer().getPosition().getY()));
        for (Point p : neigbours) {
            Case slot = model.getIsland().getCase(p.x, p.y);
            if (slot != null && slot.getFlood() == 1) {
                int x_case = (int) p.getX() * (sizeCase + sizeBorder) + sizeBorder;
                int y_case = (int) p.getY() * (sizeCase + sizeBorder) + sizeBorder;
                drawOutline(g, x_case, y_case, new Color(175, 24, 24));
            }
        }
    }

    private void drawOutline(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        for (int i = 0; i < 3; i++) {
            g.drawRect(x + i, y + i, this.sizeCase - i * 2, this.sizeCase - i * 2);
        }
    }

    private void drawEscape(Graphics g) {
        ArrayList<Point> neigbours = floodingCtrl.getEscape().neigboursMove(this.model);
        for (Point point : neigbours) {
            Case slot = model.getIsland().getCase(point.x, point.y);
            if (slot != null && slot.getFlood() != slot.getMaxFlood()) {
                int x_case = point.x * (sizeCase + sizeBorder) + sizeBorder;
                int y_case = point.y * (sizeCase + sizeBorder) + sizeBorder;
                drawOutline(g, x_case, y_case, new Color(124, 29, 20));
            }
        }
    }

    private void drawImages(Graphics g) {
        int i = 0;
        for (Case temple : model.getTemple()) {
            if (temple != null) {
                int x = temple.getCoord().x * (sizeCase + sizeBorder) + sizeBorder;
                int y = temple.getCoord().y * (sizeCase + sizeBorder) + sizeBorder;
                g.drawImage(temples.get(i), x + 5, y + 5, null);
            }
            i++;
        }
        Case heliport = model.getHeliZone();
        int x = heliport.getCoord().x * (sizeCase + sizeBorder) + sizeBorder;
        int y = heliport.getCoord().y * (sizeCase + sizeBorder) + sizeBorder;
        g.drawImage(this.heliport, x, y, null);
    }

    private void drawPlayers(Graphics g) {
        int playerIter = 0;
        for (Player player : model.getPlayers()) {
            Point pos = player.getPosition().getCoord();
            draw_pawn(g, (int) pos.getX(), (int) pos.getY(), playerIter, model.getPlayers().indexOf(player));
            playerIter++;
        }
    }

    private void draw_pawn(Graphics g, int x, int y, int i, int player) {
        x = x * (sizeCase + sizeBorder) + sizeBorder;
        y = y * (sizeCase + sizeBorder) + sizeBorder;
        switch (i) {
            case 0:
                x += sizeBorder;
                y += sizeBorder;
                break;
            case 1:
                x += sizeCase - sizeBorder - pawns.get(player).getWidth(null);
                y += sizeBorder;
                break;

            case 2:
                x += sizeBorder;
                y += sizeCase - sizeBorder - pawns.get(player).getHeight(null);
                break;

            case 3:
                x += sizeCase - sizeBorder - pawns.get(player).getWidth(null);
                y += sizeCase - sizeBorder - pawns.get(player).getHeight(null);
                break;

            default:
                break;
        }

        g.drawImage(pawns.get(player), x, y, null);
    }

    private void drawGameOver(Graphics g) {
        g.drawImage(this.gameOver, this.widthJpanel / 2 - this.gameOver.getWidth(null) / 2,
                this.heightJpanel / 2 - this.gameOver.getHeight(null) / 2, null);
    }

    private void drawVictory(Graphics g) {
        g.drawImage(this.victory, this.widthJpanel / 2 - this.victory.getWidth(null) / 2,
                this.heightJpanel / 2 - this.victory.getHeight(null) / 2, null);
    }

    private int getAlpha(Case C) {
        int max = C.getMaxFlood();
        int act = C.getFlood();
        int alpha = (int) (255 * (double) (max - act) / max);
        return alpha;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int x_case = x / (sizeCase + sizeBorder);
        int y_case = y / (sizeCase + sizeBorder);
        this.control.click(x_case, y_case);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}
