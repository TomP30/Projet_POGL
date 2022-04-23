import model.*;
import view.*;

public class Main {
    public static void main(String[] args){
        Model model;
        model = new Model();

        View view = new View(model);
        view.launch();
    }
}
