import java.awt.Dimension;

import javax.swing.JFrame;

public class Breakout {
  public static void main(String[] args) {
    JFrame obj = new JFrame();
    Gameplay gameplay = new Gameplay();

    obj.setBounds(10, 10, 710, 600);
    obj.setMinimumSize(new Dimension(710, 600));
    obj.setTitle("Bye thoth ");
    obj.setResizable(false);
    obj.setVisible(true);
    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    obj.add(gameplay);
    obj.pack();
  }
}