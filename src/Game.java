import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Game implements ActionListener{
    public static int[][] grid; // 0 not filled in, 1 = X, 2 = O
    static int filled;
    public static JButton[][] uiGrid;
    public static boolean p1Turn;
    public static boolean gameEnd;
    public static boolean isAI;

    public static JButton nextAI = new JButton();
    public static JButton nextVS = new JButton();

    public static JLabel label = new JLabel();

    public static JFrame f;

    public Game(boolean isAI)
    {
        f = new JFrame("Tic Tac Toe");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setSize(1000,1000);

        uiGrid = new JButton[3][3];
        grid = new int[3][3];

        filled = 0;

        label.setBounds(20,20, 500, 50);
        label.setVisible(false);
        f.add(label);
        this.isAI = isAI;
        p1Turn = true;
        gameEnd = false;

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                JButton b = new JButton();
                b.setBounds(250 + 100*i, 250 + 100*j, 90,90);
                b.addActionListener(this);

                uiGrid[i][j] = b;

                f.add(uiGrid[i][j]);
            }
        }

        nextAI.setBounds(100, 650, 350, 40);
        nextAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clearBoard();
                f.dispose();
                new Game(true);
            }
        });
        nextAI.setText("Play Again VS AI?");
        nextAI.setVisible(false);

        f.add(nextAI);

        nextVS.setBounds(550, 650, 350, 40);
        nextVS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clearBoard();
                f.dispose();
                new Game(false);
            }
        });
        nextVS.setText("Play Again VS Another Player?");
        nextVS.setVisible(false);

        f.add(nextVS);

        f.setVisible(true);
        p1Turn = true;
    }

    public static void disableBoard()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                uiGrid[i][j].setEnabled(false);
            }
        }
    }


    public static void clearBoard()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                uiGrid[i][j].setText("");
                grid[i][j] = 0;
            }
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(e.getSource() == uiGrid[i][j])
                {
                    updateButton(i,j);
                }
            }
        }
    }

    public static void updateButton(int i, int j)
    {
        if(grid[i][j] != 0)
        {
            System.out.println("already filled");
            return;
        }

        if(p1Turn)
        {
            uiGrid[i][j].setText("X");
            grid[i][j] = 1;
        }
        else
        {
            uiGrid[i][j].setText("O");
            grid[i][j] = 2;
        }

        filled++;

        if(checkEnd())
        {
            return;
        }

        if(isAI)
        {
            randFill();
            checkEnd();
        }
        else
        {
            p1Turn = !p1Turn;
        }

    }

    public static boolean checkEnd()
    {
        boolean needEnd = false;

        if(checkPlayerWin(1))
        {
            label.setText("Player 1 Wins!");
            label.setVisible(true);
            System.out.println("Congrats for Winning!");
            needEnd = true;
        }
        else if(checkPlayerWin(2))
        {
            label.setText("Player 2 Wins!");
            label.setVisible(true);
            System.out.println("Try again next time");
            needEnd = true;
        }
        else if(filled == 9)
        {
            label.setText("Draw");
            label.setVisible(true);
            System.out.println("Game over");
            needEnd = true;
        }

        if(needEnd)
        {
            disableBoard();
            nextAI.setVisible(true);
            nextVS.setVisible(true);
        }
        return needEnd;
    }

    public static boolean checkPlayerWin(int player)
    {
        return checkRows(player) || checkCols(player) || checkDiags(player);
    }


    public static boolean checkRows(int num)
    {
        for(int i = 0; i < 3; i++)
        {
            boolean rowSame = true;
            for(int j = 0; j < 3; j++)
            {
                if(grid[i][j] != num)
                {
                    rowSame = false;
                }
            }
            if(rowSame)
            {
                return true;
            }
        }

        return false;
    }

    public static boolean checkCols(int num)
    {
        for(int i = 0; i < 3; i++)
        {
            boolean colSame = true;
            for(int j = 0; j < 3; j++)
            {
                if(grid[j][i] != num)
                {
                    colSame = false;
                }
            }

            if(colSame)
            {
                return true;
            }
        }

        return false;
    }

    public static boolean checkDiags(int num)
    {
        boolean diaSame = true;
        boolean offDiaSame = true;

        for(int i = 0; i < 3; i++)
        {
            if(grid[i][i] != num)
            {
                diaSame = false;
            }
            if(grid[i][2-i] != num)
            {
                offDiaSame = false;
            }
        }

        return diaSame || offDiaSame;
    }

    public static void randFill()
    {
        boolean placed = false;

        if(filled >= 9)
        {
            return;
        }
        while(!placed)
        {
            int rand = (int)(Math.random() * 9);
            int r = rand/3;
            int c = rand%3;
            if(grid[r][c] == 0)
            {
                grid[r][c] = 2;
                uiGrid[r][c].setText("O");
                grid[r][c] = 2;
                placed = true;
            }
        }
        filled++;
    }

    public static void printBoard()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(grid[i][j] == 0)
                {
                    System.out.print("  ");
                }
                else if(grid[i][j] == 1)
                {
                    System.out.print("X ");
                }
                else
                {
                    System.out.print("O ");
                }

                if(j == 2)
                {
                    System.out.println();
                    System.out.println("-------------");
                }
                else
                {
                    System.out.print(" | ");
                }
            }
        }
    }


}
