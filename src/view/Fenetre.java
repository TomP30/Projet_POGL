package view;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Fenetre extends JFrame{
    private JPanel elem;

    public Fenetre(String name){
        super(name);
        this.elem = new JPanel();
        this.add(elem);
    }

    public void ajtElem(JComponent element){
        elem.add(element);
    }

    public void draw(){
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
