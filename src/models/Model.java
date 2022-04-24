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
        this.drawFlood = new DrawFlood(this.pileOfZone());
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
    public Flood getFloodLevel() {
        return this.flood;
    }

    public Boolean getTreasureState(int i) {
        return this.treasureClaimed.get(i);
    }

    public ArrayList<Boolean> getTreasureState() {
        return this.treasureClaimed;
    }

    public Condition getState() {
        return this.cond;
    }

    public Board getIsland() {
        return this.board;
    }

    public ArrayList<Case> getTemple() {
        return this.treasure;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public Case getHeliZone() {
        return this.heliport;
    }

    public int getActPlayerId() {
        return this.ActivePlayer;
    }

    public Player getActPlayer() {
        return this.players.get(ActivePlayer);
    }

    public DrawFlood getPiocheWater() {
        return this.drawFlood;
    }

    public Draw getPiocheCard() {
        return this.draw;
    }

    // Setter
    public void setState(Condition condition) {
        this.cond = condition;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void nextPlayer() {
        this.ActivePlayer = (this.ActivePlayer + 1) % this.players.size();
        getActPlayer().resetAction();
    }

    public Case getRandomValideCase() {
        Case C;
        do {
            C = this.board.getRandomCase();
        } while (this.treasure.contains(C));
        return C;
    }

    private Boolean allTraveled(Boolean[][] visitedCase) {
        for (int i = 0; i < visitedCase.length; i++) {
            for (int j = 0; j < visitedCase[i].length; j++) {
                if (!visitedCase[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private Point getMinCase(Boolean[][] visitedCase, int[][] action) {
        Point p = new Point(0, 0);
        int min = 999;
        for (int j = 0; j < action.length; j++) {
            for (int i = 0; i < action[j].length; i++) {
                if (!visitedCase[j][i] && action[j][i] <= min) {
                    min = action[j][i];
                    p = new Point(i, j);
                }
            }
        }
        return p;
    }


    public int[][] nbAction(Player player) {
        Boolean[][] visitedCase = new Boolean[board.getHeight()][board.getWidth()];
        int[][] action = new int[board.getHeight()][board.getWidth()];
        for (int j = 0; j < action.length; j++) {
            for (int i = 0; i < action[j].length; i++) {
                action[j][i] = 999;
                if (board.getCase(i, j) == null) {
                    visitedCase[j][i] = true;
                } else {
                    visitedCase[j][i] = false;
                }
            }
        }
        Player playerForZ = this.getActPlayer();
        for (Player p : this.players) {
            if (p.getState() == Player.Action.Escape) {
                playerForZ = p;
            }
        }
        action[playerForZ.getPosition().getY()][playerForZ.getPosition().getX()] = 0;
        while (!allTraveled(visitedCase)) {
            Point p = getMinCase(visitedCase, action);
            visitedCase[p.y][p.x] = true;
            for (int j = -1; j <= 1; j++) {
                for (int i = -1; i <= 1; i++) {
                    if (board.getCase(p.x + i, p.y + j) != null) {
                        if (player.isNeight(board.getCase(p.x + i, p.y + j),
                                board.getCase(p.x, p.y))) {
                            action[p.y + j][p.x + i] = player.getWeightNeight(action[p.y][p.x],
                                    action[p.y + j][p.x + i], board.getCase(p.x + i, p.y + j));
                        }
                    }
                }

            }
        }
        return action;
    }

    public int[][] nbActionNormal(int x, int y) {
        Boolean[][] visitedCase = new Boolean[board.getHeight()][board.getWidth()];
        int[][] action = new int[board.getHeight()][board.getWidth()];
        for (int j = 0; j < action.length; j++) {
            for (int i = 0; i < action[j].length; i++) {
                action[j][i] = 999;
                if (board.getCase(i, j) == null) {
                    visitedCase[j][i] = true;
                } else {
                    visitedCase[j][i] = false;
                }
            }
        }
        action[y][x] = 0;
        while (!allTraveled(visitedCase)) {
            Point p = getMinCase(visitedCase, action);
            visitedCase[p.y][p.x] = true;
            for (int j = -1; j <= 1; j++) {
                for (int i = -1; i <= 1; i++) {
                    if (board.getCase(p.x + i, p.y + j) != null) {
                        if (Math.abs(i) + Math.abs(j) == 1) {
                            if (action[p.y + j][p.x + i] > action[p.y][p.x] + 1) {
                                action[p.y + j][p.x + i] = action[p.y][p.x] + 1;
                            }
                        }
                    }
                }
            }
        }
        return action;
    }

    public ArrayList<Case> pileOfZone() {
        ArrayList<Case> cards = new ArrayList<Case>();
        for (int y = 0; y < this.getIsland().getHeight(); y++) {
            for (int x : this.getIsland().getCoordLine(y)) {
                cards.add(this.getIsland().getCase(x, y));
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
        this.drawFlood = new DrawFlood(this.pileOfZone());
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
