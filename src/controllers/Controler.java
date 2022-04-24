package controllers;

import models.Model;
import views.View;

/**
 * Controllers
 */
public abstract class Controler {
    protected Model model;
    protected View view;

    public Controler(Model model, View view) {
        this.model = model;
        this.view = view;
    }
}
