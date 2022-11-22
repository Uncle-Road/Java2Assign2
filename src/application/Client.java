package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private static String enemyMove = null;
    private final Socket socket;
    private Scanner in;
    private PrintWriter out;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public void sendMove(int x, int y, int side) throws IOException {
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String myMove = x + "," + y + "," + side;
        if(!myMove.equals("")){
            out.println(myMove);
            out.flush();
        }else {
            System.out.println("no move");
        }

    }

    public void playGame() {
        while (in.hasNext()) {
            enemyMove = in.nextLine();
            if (enemyMove.equals("Your Enemy is leaving")) {
                System.out.println(enemyMove);
                break;
            }
        }
    }

    @Override
    public void run() {
            try {
                try {
                    in = new Scanner(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out = new PrintWriter(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                playGame();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    public String getEnemyMove() {
        return enemyMove;
    }
}
