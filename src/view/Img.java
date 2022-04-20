package view;

import javax.swing.*;

public class Img {
    private JFrame frame;
    private ImageIcon Icon;
    private JLabel myLabel;

    public Img() {

         Icon = new ImageIcon(this.getClass().getResource("../images/b1.png"));
         myLabel = new JLabel(Icon);
         myLabel.setSize(600,400);


        frame = new JFrame("Test");
        frame.add(myLabel);
        frame.setSize(600,400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
