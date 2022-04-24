package models;

import java.util.ArrayList;
import java.io.IOException;
import java.util.logging.*;

import java.awt.Point;

/**
 * Models
 */
public class Model {
    public static enum Condition {
        START,
        PROGRESS,
        ENDLOST,
        ENDWON
    }

    private String map;
    private Condition cond;
    private Board board;
    private Flood flood;
    private ArrayList<Case> treasure;
    private ArrayList<Boolean> treasureClaimed;
    private ArrayList<Player> players;
    private DrawFlood drawFlood;
    private int ActivePlayer;
    private Case heliport;
    private Draw draw;

    public Model(String map) {
        try {
            this.board = new Board(map);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(
                    Model.class.getName());
            logger.setLevel(Level.WARNING);
            logger.warning(map + " not found default map used");
            this.board = new Board();
        }

        this.flood = new Flood(0);
        this.map = map;
        this.cond = Condition.START;
        this.treasure = new ArrayList<Case>();
        this.players = new ArrayList<Player>();
        this.drawFlood = new DrawFlood(this.CardPile());
        this.draw = new Draw();
        this.treasureClaimed = new ArrayList<Boolean>();
        for (int i = 0; i < 4; i++) {
            treasureClaimed.add(false);
        }

        this.ActivePlayer = 0;

        for (int i = 0; i < 4; i++) {
            this.treasure.add(this.getRandomValideCase());
        }
        this.heliport = this.getRandomValideCase();
    }

    // Getter
    public Flood getFloodLvl() {
        return this.flood;
    }

    public Boolean getTreasureState(int i) {
        return this.treasureClaimed.get(i);
    }

    public ArrayList<Boolean> getTreasureState() {
        return this.treasureClaimed;
    }

    public Condition getCond() {
        return this.cond;
    }

    public Board getBoard() {
        return this.board;
    }

    public ArrayList<Case> getTreasure() {
        return this.treasure;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public Case getHeliport() {
        return this.heliport;
    }

    public int getActivePlayerId() {
        return this.ActivePlayer;
    }

    public Player getActivePlayer() {
        return this.players.get(ActivePlayer);
    }

    public DrawFlood getDrawFlood() {
        return this.drawFlood;
    }

    public Draw getDraw() {
        return this.draw;
    }

    // Setter
    public void setCond(Condition condition) {
        this.cond = condition;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void nextPlayer() {
        this.ActivePlayer = (this.ActivePlayer + 1) % this.players.size();
        getActivePlayer().resetAction();
    }

    public Case getRandomValideCase() {
        Case C;
        do {
            C = this.board.getRandomCase();
        } while (this.treasure.contains(C));
        return C;
    }

    private Boolean moved(Boolean[][] visited) {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                if (!visited[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private Point getMinCase(Boolean[][] visited, int[][] act) {
        Point p = new Point(0, 0);
        int min = 999;
        for (int j = 0; j < act.length; j++) {
            for (int i = 0; i < act[j].length; i++) {
                if (!visited[j][i] && act[j][i] <= min) {
                    min = act[j][i];
                    p = new Point(i, j);
                }
            }
        }
        return p;
    }


    public int[][] actionAmount(Player player) {
        Boolean[][] visited = new Boolean[board.getHeight()][board.getWidth()];
        int[][] action = new int[board.getHeight()][board.getWidth()];
        for (int j = 0; j < action.length; j++) {
            for (int i = 0; i < action[j].length; i++) {
                action[j][i] = 999;
                if (board.getCase(i, j) == null) {
                    visited[j][i] = true;
                } else {
                    visited[j][i] = false;
                }
            }
        }
        Player playerForZ = this.getActivePlayer();
        for (Player p : this.players) {
            if (p.getAction() == Player.Action.Escape) {
                playerForZ = p;
            }
        }
        action[playerForZ.getPosition().getY()][playerForZ.getPosition().getX()] = 0;
        while (!moved(visited)) {
            Point p = getMinCase(visited, action);
            visited[p.y][p.x] = true;
            for (int j = -1; j <= 1; j++) {
                for (int i = -1; i <= 1; i++) {
                    if (board.getCase(p.x + i, p.y + j) != null) {
                        if (player.isNeigh(board.getCase(p.x + i, p.y + j),
                                board.getCase(p.x, p.y))) {
                            action[p.y + j][p.x + i] = player.getWeightNeigh(action[p.y][p.x],
                                    action[p.y + j][p.x + i], board.getCase(p.x + i, p.y + j));
                        }
                    }
                }

            }
        }
        return action;
    }

    public ArrayList<Case> CardPile() {
        ArrayList<Case> cards = new ArrayList<Case>();
        for (int y = 0; y < this.getBoard().getHeight(); y++) {
            for (int x : this.getBoard().getLine(y)) {
                cards.add(this.getBoard().getCase(x, y));
            }
        }
        return cards;
    }

    public void reset() {
        this.cond = Condition.START;
        try {
            this.board = new Board(map);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(
                    Model.class.getName());
            logger.setLevel(Level.WARNING);
            logger.warning(map + " not found default map used");
            this.board = new Board();
        }
        this.treasure.clear();
        this.players.clear();
        this.drawFlood = new DrawFlood(this.CardPile());
        this.draw = new Draw();
        this.treasureClaimed = new ArrayList<Boolean>();
        for (int i = 0; i < 4; i++) {
            treasureClaimed.add(false);
        }

        this.ActivePlayer = 0;

        for (int i = 0; i < 4; i++) {
            this.treasure.add(this.getRandomValideCase());
        }
        this.heliport = this.getRandomValideCase();
    }
}
