package controllers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collections;

import models.Model;
import models.Case;
import models.*;
import views.View;

/**
 * ContrSetup
 */
public class ContrSetup extends Controller implements ActionListener {

    public ContrSetup(Model model, View view) {
        super(model, view);
    }

    private Player createPlayer(String name, Case zone, int i) {
        Player player = new Player(name,zone,i);

        return player;
    }

    private void setPlayer(ArrayList<String> names) {
        int i=0;
        for (String name: names){
            Player player = createPlayer(name, model.getIsland().getRandomCase(),i);
            model.addPlayer(player);
            i++;
        }
    }

    public void actionPerformed(ActionEvent e) {
        ArrayList<String> names = view.getViewSetup().getNamePlayer();
        setPlayer(names);
        model.getFloodLevel().setLvl(this.view.setup.levelSlider.getValue() - 1);
        view.start();
    }
}
