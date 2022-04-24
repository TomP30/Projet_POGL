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

    public int width, height;
    public int sizeCase;

    public int pawnsSapcing;

    public ArrayList<Image> pawns;
    public ArrayList<Image> temples;

    public PlayerView(Model model, View view) {
        this.model = model;
        this.width = 300;
        this.height = view.board.heightJpanel;
        this.sizeCase = view.board.sizeCase;
        this.pawns = view.board.pawns;

        this.playerCtrl = new PlayerCtrl(model, view);
        view.board.control.setContrPlayer(playerCtrl);
        view.board.playerCtrl = playerCtrl;
        this.playerCtrl.selectedPlayer = null;
        playerCtrl.endTurnCtrl = view.endTurnCtrl;

        this.pawnsSapcing = (this.width - 60) / 4;

        String path = "images/keys/";
        String pawnsPath[] = new String[] { path + "wind.png", path + "stone.png", path + "fire.png",
                path + "wave.png" };
        temples = new ArrayList<Image>();
        for (int i = 0; i < 4; i++) {
            Image img = new ImageIcon(pawnsPath[i]).getImage();
            img = img.getScaledInstance(sizeCase / 2, sizeCase / 2, Image.SCALE_DEFAULT);
            img.getHeight(null);
            temples.add(img);
        }

        setPreferredSize(new java.awt.Dimension(width, height));
        setBackground(view.background);
        addMouseListener(this);
    }

    private void drawOutline(Graphics g, int x, int y, int sizeW, int sizeH, Color color) {
        g.setColor(color);
        for (int i = 0; i < 3; i++) {
            g.drawRect(x + i, y + i, sizeW - i * 2, sizeH - i * 2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawOutline(g, 0, 0, this.width, 140, new Color(51, 49, 48));
        drawOutline(g, 0, 145, this.width, this.height - 145, new Color(51, 49, 48));
        if (this.pawns != null) {
            drawPlayer(g);
        }
        if (model.getActPlayer().getState() == Player.Action.Exchange && playerCtrl.selectedCard != null) {
            drawExchange(g);
        }
        drawActPlayer(g);
        drawSelectedPlayer(g);
    }

    private void drawPawnOutline(Graphics g, int player, Color color) {
        int midX = 30 + (pawnsSapcing + this.pawns.get(player).getWidth(null) / 2) * player
                + this.pawns.get(player).getWidth(null) / 2;
        int midY = 15 + this.pawns.get(player).getHeight(null) / 2;
        int size = this.pawns.get(player).getHeight(null) + 10;
        drawOutline(g, midX - size / 2, midY - size / 2, size, size, color);
    }

    private void drawPlayer(Graphics g) {
        for (int player = 0; player < this.pawns.size(); player++) {
            g.drawImage(this.pawns.get(player),
                    30 + (pawnsSapcing + this.pawns.get(player).getWidth(null) / 2) * player, 15, null);

            if ((model.getPlayers().get(player) == playerCtrl.selectedPlayer)) {
                int midX = 30 + (pawnsSapcing + this.pawns.get(player).getWidth(null) / 2) * player
                        + this.pawns.get(player).getWidth(null) / 2;
                int midY = 15 + this.pawns.get(player).getHeight(null) / 2;
                g.setColor(new Color(255, 255, 255));
                g.fillOval(midX - 5, midY * 2, 10, 10);
            }
        }
        drawPawnOutline(g, model.getActPlayerId(), new Color(255, 255, 255));
    }

    private void drawActPlayer(Graphics g) {
        g.setColor(new Color(200, 200, 200));
        Font currentFont = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, 50));
        g.drawString("" + model.getActPlayer().getNbActions(), this.width - 50, 130);
        g.setFont(currentFont);
        String action = "Use Card";
        if (model.getState() == Model.Condition.PROGRESS) {
            action = model.getActPlayer().getState().toString();
            action = action.substring(0, 1) + action.substring(1).toLowerCase();
        }
        g.drawString(model.getActPlayer().getName() + "        " + action, 20,
                130 - g.getFontMetrics().getHeight());
    }

    private void drawExchange(Graphics g) {
        for (int i = 0; i < this.model.getPlayers().size(); i++) {
            if ((model.getActPlayer().getPosition() == model.getPlayers().get(i).getPosition() &&
                    model.getActPlayerId() != i)) {
                drawPawnOutline(g, i, new Color(63, 171, 22));
            }
        }
    }

    private void drawSelectedPlayer(Graphics g) {
        Color colorT = new Color(200, 200, 200);
        g.setColor(colorT);
        Player player = playerCtrl.selectedPlayer;
        int minY = 145;
        if (player != null && model.getPlayers().indexOf(player) != -1) {
            int index = model.getPlayers().indexOf(player);
            g.drawImage(this.pawns.get(index), 20, minY + 20, null);
            g.drawString("" + player.getName(), 30 + pawns.get(index).getWidth(null),
                    minY + 20 + pawns.get(index).getHeight(null) - g.getFontMetrics().getHeight());

            int space = (this.width - minY - 60) / 4;
            for (int i = 0; i < temples.size(); i++) {
                int y = minY + 100 + (space + temples.get(i).getHeight(null)) * i;
                g.drawImage(temples.get(i), 20, y, null);
                Card card = Card.getCardTemple(i);
                g.drawString("x " + playerCtrl.selectedPlayer.getCards(card), 30 + temples.get(i).getWidth(null),
                        y + temples.get(i).getHeight(null) / 2 + g.getFontMetrics().getAscent() / 2);
                if (playerCtrl.selectedCard == card) {
                    g.setColor(new Color(255, 255, 255));
                    g.fillOval(5, y + temples.get(i).getWidth(null) / 2 - 5, 10, 10);
                    g.setColor(colorT);
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if ((e.getY() >= 15 && e.getY() <= 15 + this.pawns.get(0).getHeight(null))
                && (model.getActPlayer().getState() != Player.Action.Discard)) {
            for (int player = 0; player < model.getPlayers().size(); player++) {
                int size = this.pawns.get(player).getHeight(null) + 10;
                if (e.getX() >= 30 + (pawnsSapcing + this.pawns.get(player).getWidth(null) / 2) * player - size / 2
                        && e.getX() <= 30 + (pawnsSapcing + this.pawns.get(player).getWidth(null) / 2) * (player + 1)
                                - size / 2) {
                    playerCtrl.playerClick(model.getPlayers().get(player));
                    return;
                }
            }
            playerCtrl.playerClick(null);
        } else if (e.getY() >= 145) {
            playerCtrl.selectedCard = null;
            int minY = 145;
            int space = (this.width - minY - 60) / 4;
            for (int i = 0; i < 4; i++) {
                int y = minY + 100 + (space + temples.get(i).getHeight(null)) * i;
                if (y <= e.getY() && y + temples.get(i).getHeight(null) >= e.getY() && e.getX() >= 20
                        && e.getX() <= 20 + temples.get(i).getWidth(null)) {
                    playerCtrl.cardClick(Card.getCardTemple(i));
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