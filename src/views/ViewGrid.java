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

import controllers.ContrFlooding;
import controllers.ContrGrid;
import controllers.ContrPlayer;
import models.Card;
import models.Island;
import models.Model;
import models.Zone;
import models.Player;

/**
 * Grid
 */
public class ViewGrid extends JPanel implements MouseListener {
    public ContrGrid control;
    public ContrFlooding contrFlooding;
    public ContrPlayer contrPlayer;

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

    public ViewGrid(Model m, View view, ContrFlooding contrFlooding) {
        this.model = m;
        int width = m.getIsland().getGridSize().x;
        int height = m.getIsland().getGridSize().y;
        this.widthJpanel = width * sizeCase + (width + 1) * sizeBorder;
        this.heightJpanel = height * sizeCase + (height + 1) * sizeBorder;

        setPreferredSize(new java.awt.Dimension(
                widthJpanel, heightJpanel));

        setBackground(new Color(1, 138, 204));
        addMouseListener(this);

        this.control = new ContrGrid(m, view, contrFlooding);
        this.contrFlooding = contrFlooding;

        pawns = new ArrayList<Image>();
        String path = "images/elements/";
        String pawnsPath[] = new String[] { path + "air.png", path + "earth.png", path + "fire.png",
                path + "water.png" };
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

        if (model.getState() == Model.State.RUNNING) {
            if (contrFlooding.getEscape() != null) {
                drawEscape(g);
            } else if (model.getActPlayer().getState() == Player.State.DRY && model.getActPlayer().getNbActions() > 0) {
                drawDry(g);
            } else if (model.getActPlayer().getState() == Player.State.MOVING) {
                drawMove(g);

            }
        }
        drawImages(g);
        drawPlayers(g);

        if (model.getState() == Model.State.LOSE) {
            drawGameOver(g);
        } else if (model.getState() == Model.State.VICTORY) {
            drawVictory(g);
        }
    }

    private void drawIsland(Graphics g) {
        Island island = this.model.getIsland();
        for (int y = 0; y < island.getGridSize().y; y++) {
            for (int x = 0; x < island.getGridSize().x; x++) {
                if (island.inMap(new Point(x, y))) {
                    g.setColor(new Color(200, 200, 200, getAlpha(island.getZone(x, y))));
                    int x_case = x * (sizeCase + sizeBorder) + sizeBorder;
                    int y_case = y * (sizeCase + sizeBorder) + sizeBorder;
                    g.fillRect(x_case, y_case, sizeCase, sizeCase);
                }
            }
        }
    }

    private void drawMove(Graphics g) {
        int[][] actionMove = model.nbAction(model.getActPlayer());
        Island island = this.model.getIsland();
        for (int y = 0; y < actionMove.length; y++) {
            for (int x = 0; x < actionMove[y].length; x++) {
                Zone z = island.getZone(x, y);
                int x_case = x * (sizeCase + sizeBorder) + sizeBorder;
                int y_case = y * (sizeCase + sizeBorder) + sizeBorder;
                if (actionMove[y][x] <= model.getActPlayer().getNbActions() && actionMove[y][x] != 0
                        && z.moove()) {
                    drawOutline(g, x_case, y_case, new Color(176, 242, 182));
                }
            }
        }
    }

    private void drawHeli(Graphics g) {
        Island island = this.model.getIsland();
        for (int y = 0; y < this.model.getIsland().getHeight(); y++) {
            for (int x = 0; x < model.getIsland().getWidth(); x++) {
                if (island.inMap(new Point(x, y))
                        && this.contrPlayer.playersHeli.get(0).getPosition() != island.getZone(x, y)) {
                    Zone z = island.getZone(x, y);
                    int x_case = x * (sizeCase + sizeBorder) + sizeBorder;
                    int y_case = y * (sizeCase + sizeBorder) + sizeBorder;

                    if (z.moove()) {
                        drawOutline(g, x_case, y_case, new Color(176, 242, 182));
                    }
                }
            }
        }
    }

    private void drawSandBag(Graphics g) {
        Island island = this.model.getIsland();
        for (int y = 0; y < this.model.getIsland().getHeight(); y++) {
            for (int x = 0; x < model.getIsland().getWidth(); x++) {
                if (island.inMap(new Point(x, y))) {
                    Zone z = island.getZone(x, y);
                    int x_case = x * (sizeCase + sizeBorder) + sizeBorder;
                    int y_case = y * (sizeCase + sizeBorder) + sizeBorder;

                    if (z.dryable()) {
                        drawOutline(g, x_case, y_case, new Color(124, 78, 40));
                    }
                }
            }
        }
    }

    private void drawNavigatorMove(Graphics g) {
        if (contrPlayer.selectedPlayer == null || contrPlayer.selectedPlayer == model.getActPlayer()
                || model.getActPlayer().getNbActions() <= 0) {
            drawMove(g);
        } else {
            int[][] action = model.nbActionNormal(contrPlayer.selectedPlayer.getPosition().getX(),
                    contrPlayer.selectedPlayer.getPosition().getY());
            Island island = this.model.getIsland();
            for (int y = 0; y < action.length; y++) {
                for (int x = 0; x < action[y].length; x++) {
                    Zone z = island.getZone(x, y);
                    int x_case = x * (sizeCase + sizeBorder) + sizeBorder;
                    int y_case = y * (sizeCase + sizeBorder) + sizeBorder;
                    if (action[y][x] <= 2 && action[y][x] != 0
                            && z.getWaterLvl() < z.getMaxWaterLvl()
                            && contrFlooding.getEscape() == null) {
                        drawOutline(g, x_case, y_case, new Color(176, 242, 182));
                    }
                }
            }
        }
    }

    private void drawDry(Graphics g) {
        ArrayList<Point> neigbours = model.getActPlayer().neigboursDry(this.model);
        neigbours.add(new Point(model.getActPlayer().getPosition().getX(), model.getActPlayer().getPosition().getY()));
        for (Point p : neigbours) {
            Zone zone = model.getIsland().getZone(p.x, p.y);
            if (zone != null && zone.getWaterLvl() == 1) {
                int x_case = (int) p.getX() * (sizeCase + sizeBorder) + sizeBorder;
                int y_case = (int) p.getY() * (sizeCase + sizeBorder) + sizeBorder;
                drawOutline(g, x_case, y_case, new Color(124, 78, 40));
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
        ArrayList<Point> neigbours = contrFlooding.getEscape().neigboursMove(this.model);
        for (Point point : neigbours) {
            Zone zone = model.getIsland().getZone(point.x, point.y);
            if (zone != null && zone.getWaterLvl() != zone.getMaxWaterLvl()) {
                int x_case = point.x * (sizeCase + sizeBorder) + sizeBorder;
                int y_case = point.y * (sizeCase + sizeBorder) + sizeBorder;
                drawOutline(g, x_case, y_case, new Color(124, 29, 20));
            }
        }
    }

    private void drawImages(Graphics g) {
        int i = 0;
        for (Zone temple : model.getTemple()) {
            if (temple != null) {
                int x = temple.getCoord().x * (sizeCase + sizeBorder) + sizeBorder;
                int y = temple.getCoord().y * (sizeCase + sizeBorder) + sizeBorder;
                g.drawImage(temples.get(i), x + 5, y + 5, null);
            }
            i++;
        }
        Zone heliport = model.getHeliZone();
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

    private int getAlpha(Zone zone) {
        int max = zone.getMaxWaterLvl();
        int act = zone.getWaterLvl();
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