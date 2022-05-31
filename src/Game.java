import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Game{
    public static int[][] grid; // 0 not filled in, 1 = X, 2 = O
    static int filled;
    public static JLabel[][] uiGrid;
    public static boolean p1Turn;
    public static boolean gameEnd;
    public static boolean isAI;

    public static JLabel nextAI;
    public static JLabel nextVS;

    public static JLabel label = new JLabel();

    public static ImageIcon O;
    public static ImageIcon X;

    public static JFrame f;

    public static int cellW = 90;
    public static int cellH = 90;

    public static int titleW = 500;
    public static int titleH = 100;

    public Game(boolean isAI)
    {
        f = new JFrame("Tic Tac Toe");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setSize(1000,1000);

        uiGrid = new JLabel[3][3];
        grid = new int[3][3];

        filled = 0;

        label.setBounds( 500 - titleW/2, titleH/2 + 50, titleW, titleH);
        label.setVisible(false);
        f.add(label);
        this.isAI = isAI;
        p1Turn = true;
        gameEnd = false;

        O = new ImageIcon("O.png");
        Image oImage = O.getImage();
        oImage = oImage.getScaledInstance(cellW, cellH, Image.SCALE_SMOOTH);
        O = new ImageIcon(oImage);

        X = new ImageIcon("X.png");
        Image xImage = X.getImage();
        xImage = xImage.getScaledInstance(cellW,cellH, Image.SCALE_SMOOTH);
        X = new ImageIcon(xImage);

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                JLabel b = new JLabel();
                b.setBounds(325 + (cellH + 10)*i, 300 + (cellW + 10)*j, cellW,cellH);
                b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                uiGrid[i][j] = b;
                b.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        for(int r = 0; r < 3; r++)
                        {
                            for(int c = 0; c < 3; c++)
                            {
                                if(e.getSource() == uiGrid[r][c])
                                {
                                    updateButton(r,c);
                                }
                            }
                        }
                    }
                });

                f.add(uiGrid[i][j]);
            }
        }

        ImageIcon playAINext = new ImageIcon("Play_Again_AI.png");
        Image playAINextImage= playAINext.getImage();
        playAINextImage = playAINextImage.getScaledInstance(350, 65, Image.SCALE_SMOOTH);
        playAINext = new ImageIcon(playAINextImage);
        nextAI = new JLabel(playAINext);
        nextAI.setBounds(75, 700, 350, 65);
        nextAI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                f.dispose();
                new Game(true);
            }
        });
        nextAI.setVisible(false);

        f.add(nextAI);


        ImageIcon playPlayerNext = new ImageIcon("Play_Again_Player.png");
        Image playPlayerNextImage = playPlayerNext.getImage();
        playPlayerNextImage = playPlayerNextImage.getScaledInstance(400, 65, Image.SCALE_SMOOTH);
        playPlayerNext = new ImageIcon(playPlayerNextImage);
        nextVS = new JLabel(playPlayerNext);
        nextVS.setBounds(525, 700, 400, 65);
        nextVS.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.dispose();
                new Game(false);
            }
        });
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

    public static void updateButton(int i, int j)
    {
        if(grid[i][j] != 0)
        {
            System.out.println("already filled");
            return;
        }

        if(p1Turn)
        {
            uiGrid[i][j].setIcon(X);
            grid[i][j] = 1;
        }
        else
        {
            uiGrid[i][j].setIcon(O);
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
            ImageIcon p1Win = new ImageIcon("One_Wins.png");
            Image p1WinImage = p1Win.getImage();
            p1WinImage = p1WinImage.getScaledInstance(titleW, titleH, Image.SCALE_SMOOTH);
            p1Win = new ImageIcon(p1WinImage);
            label.setIcon(p1Win);
            label.setVisible(true);
            needEnd = true;
        }
        else if(checkPlayerWin(2))
        {
            ImageIcon p2Win = new ImageIcon("Two_Wins.png");
            Image p2WinImage = p2Win.getImage();
            p2WinImage = p2WinImage.getScaledInstance(titleW, titleH, Image.SCALE_SMOOTH);
            p2Win = new ImageIcon(p2WinImage);
            label.setIcon(p2Win);
            label.setVisible(true);
            needEnd = true;
        }
        else if(filled == 9)
        {
            ImageIcon draw = new ImageIcon("Draw.png");
            Image drawImage = draw.getImage();
            drawImage = drawImage.getScaledInstance(titleW,titleH, Image.SCALE_SMOOTH);
            draw = new ImageIcon(drawImage);
            label.setIcon(draw);
            label.setVisible(true);
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
                uiGrid[r][c].setIcon(O);
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
