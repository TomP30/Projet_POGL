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
    public FloodingCtrl flooding;
    public PlayerCtrl player;

    private Model model;

    public ArrayList<Image> players;
    public ArrayList<Image> treasure;
    public Image heliport;
    public Image gameOver;
    public Image victory;

    final public int widthPanel;
    final public int heightPanel;
    final public int CaseSize = 80;
    final public int BorderSize = 10;

    public BoardView(Model M, View V, FloodingCtrl flooding) {
        this.model = M;
        int width = M.getBoard().getSize().x;
        int height = M.getBoard().getSize().y;
        this.widthPanel = width * CaseSize + (width + 1) * BorderSize;
        this.heightPanel = height * CaseSize + (height + 1) * BorderSize;

        setPreferredSize(new java.awt.Dimension(widthPanel, heightPanel));
        setBackground(new Color(109, 157, 231));
        addMouseListener(this);
        this.control = new BoardCtrl(M, V, flooding);
        this.flooding = flooding;
        players = new ArrayList<Image>();
        String path = "images/keys/";
        String paths[] = new String[] { path + "wind.png", path + "stone.png", path + "fire.png", path + "wave.png" };
        treasure = new ArrayList<Image>();
        for (int i = 0; i < 4; i++) {
            Image img = new ImageIcon(paths[i]).getImage();
            img = img.getScaledInstance(CaseSize - 10, CaseSize - 10, Image.SCALE_DEFAULT);
            img.getHeight(null);
            treasure.add(img);
        }
        this.heliport = new ImageIcon("images/heliport.png").getImage();
        this.heliport = heliport.getScaledInstance(CaseSize + 5, CaseSize + 5, Image.SCALE_DEFAULT);
        this.heliport.getHeight(null);
        this.gameOver = new ImageIcon("images/gameOver.png").getImage();
        this.victory = new ImageIcon("images/victory.png").getImage();
    }

    public void initPlayers() {
        players.clear();
        String path = "images/pawns/";
        for (int i = 0; i < model.getPlayers().size(); i++) {
            Image img = new ImageIcon(path + model.getPlayers().get(i).getImage()).getImage();
            players.add(img);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (model.getCond() == Model.Condition.PROGRESS) {
            if (flooding.getEscape() != null) {
                drawEscape(g);
            } else if (model.getActivePlayer().getAction() == Player.Action.Drain && model.getActivePlayer().getAmount() > 0) {
                drawDrain(g);
            } else if (model.getActivePlayer().getAction() == Player.Action.Move) {
                drawMove(g);
            }
        }
        drawImages(g);
        drawPlayers(g);
        if (model.getCond() == Model.Condition.ENDLOST) {
            drawLose(g);
        } else if (model.getCond() == Model.Condition.ENDWON) {
            drawWin(g);
        }
    }

    private void drawBoard(Graphics g) {
        Board board = this.model.getBoard();
        for (int y = 0; y < board.getSize().y; y++) {
            for (int x = 0; x < board.getSize().x; x++) {
                if (board.isValid(new Point(x, y))) {
                    g.setColor(new Color(200, 200, 200, alpha(board.getCase(x, y))));
                    int x_case = x * (CaseSize + BorderSize) + BorderSize;
                    int y_case = y * (CaseSize + BorderSize) + BorderSize;
                    g.fillRect(x_case, y_case, CaseSize, CaseSize);
                }
            }
        }
    }

    private void drawMove(Graphics g) {
        int[][] actM = model.actionAmount(model.getActivePlayer());
        Board board = this.model.getBoard();
        for (int y = 0; y < actM.length; y++) {
            for (int x = 0; x < actM[y].length; x++) {
                Case C = board.getCase(x, y);
                int x_case = x * (CaseSize + BorderSize) + BorderSize;
                int y_case = y * (CaseSize + BorderSize) + BorderSize;
                if (actM[y][x] <= model.getActivePlayer().getAmount() && actM[y][x] != 0 && C.movable()) {
                    drawBorder(g, x_case, y_case, new Color(61, 58, 58));
                }
            }
        }
    }

    private void drawDrain(Graphics g) {
        ArrayList<Point> neigh = model.getActivePlayer().drains(this.model);
        neigh.add(new Point(model.getActivePlayer().getPosition().getX(), model.getActivePlayer().getPosition().getY()));
        for (Point p : neigh) {
            Case C = model.getBoard().getCase(p.x, p.y);
            if (C != null && C.getFlood() == 1) {
                int x = (int) p.getX() * (CaseSize + BorderSize) + BorderSize;
                int y = (int) p.getY() * (CaseSize + BorderSize) + BorderSize;
                drawBorder(g, x, y, new Color(145, 6, 6));
            }
        }
    }

    private void drawBorder(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        for (int i = 0; i < 3; i++) {
            g.drawRect(x + i, y + i, this.CaseSize - i * 2, this.CaseSize - i * 2);
        }
    }

    private void drawEscape(Graphics g) {
        ArrayList<Point> neigh = flooding.getEscape().moves(this.model);
        for (Point point : neigh) {
            Case C = model.getBoard().getCase(point.x, point.y);
            if (C != null && C.getFlood() != C.getMaxFlood()) {
                int x = point.x * (CaseSize + BorderSize) + BorderSize;
                int y = point.y * (CaseSize + BorderSize) + BorderSize;
                drawBorder(g, x, y, new Color(124, 29, 20));
            }
        }
    }

    private void drawImages(Graphics g) {
        int i = 0;
        for (Case Ct : model.getTreasure()) {
            if (Ct != null) {
                int x = Ct.getCoord().x * (CaseSize + BorderSize) + BorderSize;
                int y = Ct.getCoord().y * (CaseSize + BorderSize) + BorderSize;
                g.drawImage(treasure.get(i), x + 5, y + 5, null);
            }
            i++;
        }
        Case heliport = model.getHeliport();
        int x = heliport.getCoord().x * (CaseSize + BorderSize) + BorderSize;
        int y = heliport.getCoord().y * (CaseSize + BorderSize) + BorderSize;
        g.drawImage(this.heliport, x, y, null);
    }

    private void drawPlayers(Graphics g) {
        int i = 0;
        for (Player player : model.getPlayers()) {
            Point pos = player.getPosition().getCoord();
            drawPlayer(g, (int) pos.getX(), (int) pos.getY(), i, model.getPlayers().indexOf(player));
            i++;
        }
    }

    private void drawPlayer(Graphics g, int x, int y, int i, int player) {
        x = x * (CaseSize + BorderSize) + BorderSize;
        y = y * (CaseSize + BorderSize) + BorderSize;
        switch (i) {
            case 0:
                x += BorderSize;
                y += BorderSize;
                break;
            case 1:
                x += CaseSize - BorderSize - players.get(player).getWidth(null);
                y += BorderSize;
                break;
            case 2:
                x += BorderSize;
                y += CaseSize - BorderSize - players.get(player).getHeight(null);
                break;
            case 3:
                x += CaseSize - BorderSize - players.get(player).getWidth(null);
                y += CaseSize - BorderSize - players.get(player).getHeight(null);
                break;
            default:
                break;
        }
        g.drawImage(players.get(player), x, y, null);
    }

    private void drawLose(Graphics g) {
        g.drawImage(this.gameOver, this.widthPanel / 2 - this.gameOver.getWidth(null) / 2, this.heightPanel / 2 - this.gameOver.getHeight(null) / 2, null);
    }

    private void drawWin(Graphics g) {
        g.drawImage(this.victory, this.widthPanel / 2 - this.victory.getWidth(null) / 2, this.heightPanel / 2 - this.victory.getHeight(null) / 2, null);
    }

    private int alpha(Case C) {
        int m = C.getMaxFlood();
        int act = C.getFlood();
        int alpha = (int) (255 * (double) (m - act) / m);
        return alpha;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int x_case = x / (CaseSize + BorderSize);
        int y_case = y / (CaseSize + BorderSize);
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
