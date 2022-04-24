package views;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.*;
import models.Case;
import models.Model;
import models.Player;

/**
 * View
 */
public class View extends JFrame {
    private Model model;

    public BoardView board;
    public PlayerView player;
    public ArtefactsView treasure;

    private JPanel buttons;
    private JPanel EndButtons;

    public EndTurnCtrl endTurn;
    public FloodingCtrl flooding;
    public Search search;

    private JPanel panel;
    public Color background;

    public int height;
    public int width;

    public View(Model m) {
        super();
        this.model = m;
        setSize(400, 300);
        this.background = new Color(0, 0, 0);

        this.panel = new JPanel();
        this.panel.setBackground(background);

        this.flooding = new FloodingCtrl(this.model, this);
        this.endTurn = new EndTurnCtrl(this.model, this, this.flooding);
        this.search = new Search(model, this);

        this.board = new BoardView(this.model, this, this.flooding);
        this.player = new PlayerView(this.model, this);
        this.treasure = new ArtefactsView(this.model, this, this.board);

        this.endTurn.player = this.player.playerCtrl;

        this.buttons = new JPanel();
        this.buttons.setPreferredSize(new Dimension(this.board.widthPanel + this.player.width + 300, 100));
        this.buttons.setBackground(background);

        JButton search = new JButton("Claim");
        search.setPreferredSize(new Dimension((this.board.widthPanel + 200) / 4, 50));
        search.addActionListener(this.search);

        JButton drain = new JButton("Drain");
        drain.setPreferredSize(new Dimension((this.board.widthPanel + 200) / 4, 50));
        drain.addActionListener(e -> {
            if (model.getActivePlayer().getAction() == Player.Action.Drain) {
                model.getActivePlayer().setAction(Player.Action.Move);
            } else {
                model.getActivePlayer().setAction(Player.Action.Drain);
            }
            this.repaint();
        });

        JButton exchange = new JButton("Exchange");
        exchange.setPreferredSize(new Dimension((this.board.widthPanel + 200) / 4, 50));
        exchange.addActionListener(e -> {
            if (model.getActivePlayer().getAction() == Player.Action.Exchange) {
                model.getActivePlayer().setAction(Player.Action.Move);
            } else {
                model.getActivePlayer().setAction(Player.Action.Exchange);
                player.playerCtrl.selectP = model.getActivePlayer();
            }
            this.repaint();
        });

        JButton next = new JButton("End of turn");
        next.setPreferredSize(new Dimension(this.player.width, 50));
        next.addActionListener(this.endTurn);
        buttons.add(search);
        buttons.add(drain);
        buttons.add(exchange);
        buttons.add(next);

        this.width = this.board.widthPanel + this.player.width + 100;
        this.height = this.board.heightPanel + 100;

        treasure.setPreferredSize(new Dimension(this.board.widthPanel + this.player.width, 150));

        panel.add(this.player);
        panel.add(this.board);
        panel.add(this.buttons);
        panel.add(this.treasure);

        this.EndButtons = new JPanel();
        this.EndButtons.setPreferredSize(new Dimension(this.board.widthPanel + this.player.width + 30, 100));
        this.EndButtons.setBackground(background);

        JButton exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension((this.board.widthPanel + this.player.width) / 2, 50));
        exit.addActionListener(e -> {
            this.dispose();
        });
        JButton restart = new JButton("New Game");
        restart.setPreferredSize(new Dimension((this.board.widthPanel + this.player.width) / 2, 50));
        restart.addActionListener(e -> {
            this.model.reset();
            this.panel.remove(this.EndButtons);
            this.panel.add(this.buttons);
            this.panel.add(this.treasure);
            setup();
        });
        EndButtons.add(restart);
        EndButtons.add(exit);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setup() {
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            names.add("J "+ (i+1));
        }
        setPlayer(names);
        model.getFloodLvl().setLvl(0);
        repaint();
        start();
    }

    public void start() {
        this.model.setCond(Model.Condition.PROGRESS);
        getContentPane().removeAll();
        setBackground(background);

        this.board.initPlayers();
        setSize(this.width, this.height);

        add(this.panel);

        this.repaint();
    }

    public void Lose() {
        panel.remove(this.buttons);
        panel.remove(this.treasure);
        panel.add(this.EndButtons);
        panel.add(this.treasure);

        panel.updateUI();
        this.repaint();
    }

    private void setPlayer(ArrayList<String> names) {
        int i=0;
        for (String name: names){
            Player player = createPlayer(name, model.getBoard().getRandomCase(),i);
            model.addPlayer(player);
            i++;
        }
    }

    private Player createPlayer(String name, Case zone, int i) {
        Player player = new Player(name,zone,i);
        return player;
    }
}
