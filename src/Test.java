import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class Test{

    public static void main (String [] args) throws IOException {
        System.out.println("Test");

        JFrame f = new JFrame("Tic Tac Toe");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setSize(1000,1000);


        ImageIcon titleCard = new ImageIcon("Title_Card.png");
        Image titleCardImage = titleCard.getImage();
        titleCardImage = titleCardImage.getScaledInstance(800,400, Image.SCALE_SMOOTH);
        titleCard = new ImageIcon(titleCardImage);
        JLabel title = new JLabel(titleCard);
        title.setBounds(100,100, 800, 400);
        title.setVisible(true);
        f.add(title);


        ImageIcon againstAI = new ImageIcon("Against_AI.png");
        Image againstAIImage = againstAI.getImage();
        againstAIImage = againstAIImage.getScaledInstance(300,65, java.awt.Image.SCALE_SMOOTH);
        againstAI = new ImageIcon(againstAIImage);
        JLabel playAI = new JLabel(againstAI);
        playAI.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                f.dispose();
                new Game(true);
            }
        });
        playAI.setVisible(true);
        playAI.setBounds(100,700,300,65);
        f.add(playAI);

        ImageIcon againstPlayer = new ImageIcon("Against_Player.png");
        Image againstPlayerImage = againstPlayer.getImage();
        againstPlayerImage = againstPlayerImage.getScaledInstance(350, 65, Image.SCALE_SMOOTH);
        againstPlayer = new ImageIcon(againstPlayerImage);
        JLabel playVS = new JLabel(againstPlayer);
        playVS.addMouseListener(new MouseAdapter()
        {
           @Override
           public void mouseClicked(MouseEvent e)
           {
               f.dispose();
               new Game(false);
           }
        });
        playVS.setVisible(true);
        playVS.setBounds(550, 700, 350, 65);
        f.add(playVS);

        f.setVisible(true);
    }



}