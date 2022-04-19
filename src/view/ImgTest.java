package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImgTest {
    private JFrame frame;
    private ImageIcon Icon;
    private JLabel myLabel;
    private JButton startButton;

    public ImgTest() {

         Icon = new ImageIcon(this.getClass().getResource("../images/b1.png"));
         myLabel = new JLabel(Icon);
         myLabel.setSize(600,400);

         startButton = new JButton("Start");
         startButton.setBounds(50,50,100,50);
         startButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent actionEvent) {
                 JOptionPane.showMessageDialog(null,"Clicked Start");
             }
         });

         myLabel.add(startButton);

        frame = new JFrame("Test");
        frame.add(myLabel);
        frame.setSize(600,400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
