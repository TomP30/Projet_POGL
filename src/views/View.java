package views;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.*;
import models.Model;
import models.Player;

/**
 * View
 */
public class View extends JFrame {
    private Model model;

    public SetupView setup;
    public BoardView grid;
    public PlayerView player;
    public ArtefactsView treasure;

    private JPanel buttons;
    private JPanel gameOverButtons;

    public EndTurnCtrl endTurnCtrl;
    public FloodingCtrl floodingCtrl;
    public Search search;

    private JPanel elements;
    public Color background;

    public int height;
    public int width;

    public View(Model m) {
        super();
        this.model = m;
        setSize(500, 400);
        this.background = new Color(138, 136, 136);

        this.elements = new JPanel();
        this.elements.setBackground(background);

        this.floodingCtrl = new FloodingCtrl(this.model, this);
        this.endTurnCtrl = new EndTurnCtrl(this.model, this, this.floodingCtrl);
        this.search = new Search(model, this);

        this.setup = new SetupView(this.model, this);
        this.grid = new BoardView(this.model, this, this.floodingCtrl);
        this.player = new PlayerView(this.model, this);
        this.treasure = new ArtefactsView(this.model, this, this.grid);

        this.endTurnCtrl.playerCtrl = this.player.playerCtrl;

        this.buttons = new JPanel();
        this.buttons.setPreferredSize(new Dimension(this.grid.widthJpanel + this.player.width + 300, 100));
        this.buttons.setBackground(background);

        JButton search = new JButton("Search");
        search.setPreferredSize(new Dimension((this.grid.widthJpanel + 200) / 4, 50));
        search.addActionListener(this.search);

        JButton dig = new JButton("Dry up");
        dig.setPreferredSize(new Dimension((this.grid.widthJpanel + 200) / 4, 50));
        dig.addActionListener(e -> {
            if (model.getActPlayer().getState() == Player.Action.Drain) {
                model.getActPlayer().setState(Player.Action.Move);
            } else {
                model.getActPlayer().setState(Player.Action.Drain);
            }
            this.repaint();
        });

        JButton exchange = new JButton("Exchange");
        exchange.setPreferredSize(new Dimension((this.grid.widthJpanel + 200) / 4, 50));
        exchange.addActionListener(e -> {
            if (model.getActPlayer().getState() == Player.Action.Exchange) {
                model.getActPlayer().setState(Player.Action.Move);
            } else {
                model.getActPlayer().setState(Player.Action.Exchange);
                player.playerCtrl.selectedPlayer = model.getActPlayer();
            }
            this.repaint();
        });

        JButton use = new JButton("Use Card");
        use.setPreferredSize(new Dimension((this.grid.widthJpanel + 200) / 4, 50));
        use.addActionListener(e -> {
            model.setState(Model.Condition.PROGRESS);
            this.player.playerCtrl.playersHeli.clear();
            this.player.playerCtrl.selectedCard = null;
            this.repaint();
        });

        JButton next = new JButton("End of turn");
        next.setPreferredSize(new Dimension(this.player.width, 50));
        next.addActionListener(this.endTurnCtrl);
        buttons.add(search);
        buttons.add(dig);
        buttons.add(exchange);
        buttons.add(use);
        buttons.add(next);

        this.width = this.grid.widthJpanel + this.player.width + 300;
        this.height = this.grid.heightJpanel + 300;

        treasure.setPreferredSize(new Dimension(this.grid.widthJpanel + this.player.width, 150));

        elements.add(this.grid);
        elements.add(this.player);
        elements.add(this.buttons);
        elements.add(this.treasure);

        this.gameOverButtons = new JPanel();
        this.gameOverButtons.setPreferredSize(new Dimension(this.grid.widthJpanel + this.player.width + 30, 100));
        this.gameOverButtons.setBackground(background);

        JButton exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension((this.grid.widthJpanel + this.player.width) / 2, 50));
        exit.addActionListener(e -> {
            this.dispose();
        });
        JButton restart = new JButton("New Game");
        restart.setPreferredSize(new Dimension((this.grid.widthJpanel + this.player.width) / 2, 50));
        restart.addActionListener(e -> {
            this.model.reset();
            this.elements.remove(this.gameOverButtons);
            this.elements.add(this.buttons);
            this.elements.add(this.treasure);
            setup();
        });
        gameOverButtons.add(restart);
        gameOverButtons.add(exit);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setup() {
        setSize(500, 400);
        getContentPane().removeAll();
        add(this.setup);
        repaint();
    }

    public void start() {
        this.model.setState(Model.Condition.PROGRESS);
        getContentPane().removeAll();
        setBackground(background);

        this.grid.initPawn();
        setSize(this.width, this.height);

        add(this.elements);

        this.repaint();
    }

    public void gameOver() {
        elements.remove(this.buttons);
        elements.remove(this.treasure);
        elements.add(this.gameOverButtons);
        elements.add(this.treasure);

        elements.updateUI();
        this.repaint();
    }

    public SetupView getViewSetup() {
        return this.setup;
    }
}
