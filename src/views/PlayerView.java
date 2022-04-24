package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controllers.PlayerCtrl;
import models.Card;
import models.Model;
import models.Player;

/**
 * ViewPlayer
 */
public class PlayerView extends JPanel implements MouseListener {
    private Model model;
    public PlayerCtrl playerCtrl;
    public int width;
    public int height;
    public int CaseSize;
    public int spacing;
    public ArrayList<Image> players;
    public ArrayList<Image> treasure;

    public PlayerView(Model M, View V) {
        this.model = M;
        this.width = 250;
        this.height = V.board.heightPanel;
        this.CaseSize = V.board.CaseSize;
        this.players = V.board.players;
        this.playerCtrl = new PlayerCtrl(model, V);
        V.board.control.setPlayerCtrl(playerCtrl);
        V.board.player = playerCtrl;
        this.playerCtrl.selectP = null;
        playerCtrl.endTurn = V.endTurn;
        this.spacing = (this.width - 60) / 4;

        String path = "images/keys/";
        String pawnsPath[] = new String[] { path + "wind.png", path + "stone.png", path + "fire.png", path + "wave.png" };
        treasure = new ArrayList<Image>();
        for (int i = 0; i < 4; i++) {
            Image img = new ImageIcon(pawnsPath[i]).getImage();
            img = img.getScaledInstance(CaseSize / 2, CaseSize / 2, Image.SCALE_DEFAULT);
            img.getHeight(null);
            treasure.add(img);
        }
        setPreferredSize(new java.awt.Dimension(width, height));
        setBackground(V.background);
        addMouseListener(this);
    }

    private void drawBorder(Graphics g, int x, int y, int w, int h, Color col) {
        g.setColor(col);
        for (int i = 0; i < 3; i++) {
            g.drawRect(x + i, y + i, w - i * 2, h - i * 2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.players != null) {
            drawPlayer(g);
        }
        if (model.getActivePlayer().getAction() == Player.Action.Exchange && playerCtrl.selectC != null) {
            drawExchange(g);
        }
        drawActive(g);
        drawChoice(g);
    }

    private void drawPlayerBorder(Graphics g, int p, Color col) {
        int midX = 30 + (spacing + this.players.get(p).getWidth(null) / 2) * p + this.players.get(p).getWidth(null) / 2;
        int midY = 15 + this.players.get(p).getHeight(null) / 2;
        int size = this.players.get(p).getHeight(null) + 10;
        drawBorder(g, midX - size / 2, midY - size / 2, size, size, col);
    }

    private void drawPlayer(Graphics g) {
        for (int player = 0; player < this.players.size(); player++) {
            g.drawImage(this.players.get(player), 30 + (spacing + this.players.get(player).getWidth(null) / 2) * player, 15, null);

            if ((model.getPlayers().get(player) == playerCtrl.selectP)) {
                int midX = 30 + (spacing + this.players.get(player).getWidth(null) / 2) * player + this.players.get(player).getWidth(null) / 2;
                int midY = 15 + this.players.get(player).getHeight(null) / 2;
                g.setColor(new Color(255, 255, 255));
                g.fillOval(midX - 5, midY * 2, 10, 10);
            }
        }
        drawPlayerBorder(g, model.getActivePlayerId(), new Color(255, 255, 255));
    }

    private void drawActive(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        Font currentFont = g.getFont();
        g.setFont(new Font("Arial", Font.BOLD, 65));
        g.drawString("" + model.getActivePlayer().getAmount(), this.width - 50, 130);
        g.setFont(new Font("Times Roman",Font.PLAIN,25));
        String act = "Use Card";
        if (model.getCond() == Model.Condition.PROGRESS) {
            act = model.getActivePlayer().getAction().toString();
            act = act.substring(0, 1) + act.substring(1).toLowerCase();
        }
        g.drawString(model.getActivePlayer().getName() + "        " + act, 20, 130 - g.getFontMetrics().getHeight());
        g.setFont(currentFont);
    }

    private void drawExchange(Graphics g) {
        for (int i = 0; i < this.model.getPlayers().size(); i++) {
            if ((model.getActivePlayer().getPosition() == model.getPlayers().get(i).getPosition() &&
                    model.getActivePlayerId() != i)) {
                drawPlayerBorder(g, i, new Color(181, 225, 165));
            }
        }
    }

    private void drawChoice(Graphics g) {
        Color colorT = new Color(200, 200, 200);
        g.setColor(colorT);
        Player player = playerCtrl.selectP;
        int minY = 145;
        if (player != null && model.getPlayers().indexOf(player) != -1) {
            int index = model.getPlayers().indexOf(player);
            g.drawImage(this.players.get(index), 20, minY + 20, null);
            g.drawString("" + player.getName(), 30 + players.get(index).getWidth(null),
                    minY + 20 + players.get(index).getHeight(null) - g.getFontMetrics().getHeight());

            int space = (this.width - minY - 60) / 4;
            for (int i = 0; i < treasure.size(); i++) {
                int y = minY + 100 + (space + treasure.get(i).getHeight(null)) * i;
                g.drawImage(treasure.get(i), 20, y, null);
                Card card = Card.getTreasureCard(i);
                g.drawString("x " + playerCtrl.selectP.getCards(card), 30 + treasure.get(i).getWidth(null),
                        y + treasure.get(i).getHeight(null) / 2 + g.getFontMetrics().getAscent() / 2);
                if (playerCtrl.selectC == card) {
                    g.setColor(new Color(255, 255, 255));
                    g.fillOval(5, y + treasure.get(i).getWidth(null) / 2 - 5, 10, 10);
                    g.setColor(colorT);
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if ((e.getY() >= 15 && e.getY() <= 15 + this.players.get(0).getHeight(null))
                && (model.getActivePlayer().getAction() != Player.Action.Discard)) {
            for (int player = 0; player < model.getPlayers().size(); player++) {
                int size = this.players.get(player).getHeight(null) + 10;
                if (e.getX() >= 30 + (spacing + this.players.get(player).getWidth(null) / 2) * player - size / 2 && e.getX() <= 30 + (spacing + this.players.get(player).getWidth(null) / 2) * (player + 1) - size / 2) {
                    playerCtrl.click(model.getPlayers().get(player));
                    return;
                }
            }
            playerCtrl.click(null);
        } else if (e.getY() >= 145) {
            playerCtrl.selectC = null;
            int minY = 145;
            int space = (this.width - minY - 60) / 4;
            for (int i = 0; i < 4; i++) {
                int y = minY + 100 + (space + treasure.get(i).getHeight(null)) * i;
                if (y <= e.getY() && y + treasure.get(i).getHeight(null) >= e.getY() && e.getX() >= 20 && e.getX() <= 20 + treasure.get(i).getWidth(null)) {
                    playerCtrl.cardClick(Card.getTreasureCard(i));
                    return;
                }
            }
        }
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
