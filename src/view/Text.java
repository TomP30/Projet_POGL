package view;

import javax.swing.JLabel;
import javax.swing.JComponent;

public class Text extends JLabel{

    public Text(String txt){
        super(txt);
    }

    public void changeText(String txt){
        this.setText(txt);
        this.repaint();
    }
}
