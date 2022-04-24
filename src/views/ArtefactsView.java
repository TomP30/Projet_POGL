package views;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import models.Model;

/**
 * ViewTreasure
 */
public class ArtefactsView extends JPanel {
    public Model model;
    public ArrayList<Image> treasure;

    public ArtefactsView(Model model, View view, BoardView boardView) {
        this.model = model;
        this.treasure = boardView.treasure;
        setBackground(view.background);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int pawnsSapcing = (this.getWidth() - 60) / this.model.getTreasure().size();
        for (int i = 0; i < this.model.getTreasure().size(); i++) {
            if (model.getTreasureState(i)) {
                g.drawImage(this.treasure.get(i), 30 + (pawnsSapcing + this.treasure.get(i).getWidth(null) / 2) * i, 15,
                        null);
            }
        }
    }
}
