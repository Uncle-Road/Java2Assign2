package game;

import java.net.*;
import java.util.*;
import java.io.*;

public class GameThread extends Thread implements Runnable  {
    Socket s1;
    Socket s2;
    Scanner in1;
    PrintWriter out1;
    Scanner in2;
    PrintWriter out2;
    int[][] board = new int[3][3];
    boolean continueToPlay = true;

    public GameThread(Socket socket1, Socket socket2) throws IOException {
        s1 = socket1;
        s2 = socket2;
    }

    @Override
    public void run() {
        try {
            in1 = new Scanner(s1.getInputStream());
            in2 = new Scanner(s2.getInputStream());
            out1 = new PrintWriter(s1.getOutputStream());
            out2 = new PrintWriter(s2.getOutputStream());
            play();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void play() {
        int id = 1;
        int x;
        int y;
        while (true) {
            if (id == 1) {
                x = Integer.parseInt(in1.nextLine());
                y = Integer.parseInt(in1.nextLine());
                board[x][y] = 1;
                out2.println(x);
                out2.flush();
                out2.println(y);
                out2.flush();
                id = id * (-1);
            } else {
                x = Integer.parseInt(in2.nextLine());
                y = Integer.parseInt(in2.nextLine());
                board[x][y] = -1;
                out1.println(x);
                out1.flush();
                out1.println(y);
                out1.flush();
                id = id * (-1);
            }
            if (CheckWin.Check(board) == 2) {
                out1.println("Game over, winner is X-Player");
                out1.flush();
                out2.println("Game over, winner is X-Player");
                out2.flush();
            }else if (CheckWin.Check(board) == 1){
                out1.println("Game over, winner is O-Player");
                out1.flush();
                out2.println("Game over, winner is O-Player");
                out2.flush();
            }else if (CheckWin.Check(board) == 0) {
                out1.println("Game over, there is no winner");
                out1.flush();
                out2.println("Game over, there is no winner");
                out2.flush();
            }
        }
    }
}
