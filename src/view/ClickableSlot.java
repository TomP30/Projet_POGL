package view;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickableSlot extends JPanel implements MouseListener {
    private Text text;

    //Constructors
    public ClickableSlot(String txt, int x, int y){
        this(x,y);
        this.text = new Text(txt);
        this.add(this.text);
    }

    public ClickableSlot(int x, int y){
        setPreferredSize(new Dimension(x,y));
        addMouseListener(this);
        setBackground(Color.GREEN);
    }

    //Methods
    public void changeText(String txt){
        this.text.changeText(txt);
    }

    public abstract void clicGauche();
    public abstract void clicDroit();

    public void mouseClicked(MouseEvent e){
        if(SwingUtilities.isRightMouseButton(e)){
            this.clicDroit();
        } else {
            this.clicGauche();
        }
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
