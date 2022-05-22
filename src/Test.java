import java.io.*;
import java.lang.*;
import java.util.*;

public class Test {

    public static int[][] grid; // 0 not filled in, 1 = X, 2 = O
    static int filled;

    public static void main (String [] args) throws IOException {
        System.out.println("Test");

        playGame();

        System.out.println("Would you like to play again?");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String ans = br.readLine();
        ans.trim();
        ans.toLowerCase(Locale.ROOT);
        if(ans.equals("no"))
        {
            System.exit(0);
        }

    }

    public static void playGame() throws IOException {
        grid = new int[3][3];
        filled = 0;
        boolean done = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        printBoard();

        while(filled < 9 && !done)
        {
            System.out.println("Enter number from 0 to 8 ");
            int number = Integer.parseInt(br.readLine());
            int row = number/3;
            int col = number%3;

            while(grid[row][col] != 0)
            {
                System.out.println("Enter grid number not filled yet");
                number = Integer.parseInt(br.readLine());
                row = number/3;
                col = number%3;

            }

            grid[row][col] = 1;

            if(checkPlayerWin(1))
            {
                done = true;
                System.out.println("Congratulations for win!");
            }
            else
            {
                randFill();
                if(checkPlayerWin(2))
                {
                    done = true;
                    System.out.println("Try again next time!");
                }
            }
            printBoard();
        }
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
                placed = true;
            }
        }
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