package view;

import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.GridLayout;

public class GrilleVue extends JPanel {
    public GrilleVue (int h, int l){
        setLayout(new GridLayout(h,l));
    }

    public void ajtElem(JComponent elem){
        this.add(elem);
    }
}
