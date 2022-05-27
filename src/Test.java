import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Test{

    public static void main (String [] args) throws IOException {
        System.out.println("Test");

        JFrame f = new JFrame("Tic Tac Toe");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setSize(1000,1000);

        JLabel title = new JLabel("Squarme");
        title.setBounds(20,20, 300, 50);
        title.setVisible(true);
        f.add(title);

        JButton playAI = new JButton();
        playAI.setText("Play Against AI?");
        playAI.setBounds(100,700,350,40);
        playAI.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new Game(true);
            }
        });
        f.add(playAI);

        JButton playVS = new JButton();
        playVS.setText("Play Against Another Player?");
        playVS.setBounds(550, 700, 350, 40);
        playVS.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                f.dispose();
                new Game(false);
            }
        });
        f.add(playVS);

        f.setVisible(true);
    }



}