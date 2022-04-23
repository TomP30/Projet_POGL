import models.Model;
import views.View;

public class Game {
    public static void main(String[] args) {
        Model model;
        if (args.length == 0) {
            model = new Model("map/default.map");
        }else{
            model = new Model(args[0]);
        }
        View view = new View(model);
        view.setup();
    }
}