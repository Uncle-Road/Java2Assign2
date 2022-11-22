package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChessService implements Runnable {

    public CheckWin checkWin;
    private final Socket mySocket;
    private final Socket enemySocket;
    private Scanner in;
    private PrintWriter out;

    public ChessService(Socket mySocket, Socket enemySocket, CheckWin checkWin) {
        this.mySocket = mySocket;
        this.enemySocket = enemySocket;
        this.checkWin = checkWin;
    }

    public void doService() throws IOException {

        while (true) {
            if (!in.hasNext()) {
                return;
            }
            String receiveMsg = in.next();
            checkWin.move(receiveMsg);

            System.out
                    .println("Player" + receiveMsg.charAt(4) + " moves at " + receiveMsg.substring(0, 3));
            String sendMsg = receiveMsg;
            out.println(sendMsg);
            out.flush();

        }
    }

    public void verify() {
        try {
            PrintWriter toMyClient = new PrintWriter(mySocket.getOutputStream());
            toMyClient.println("Your Enemy is leaving");
            toMyClient.flush();
            toMyClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            try {
                in = new Scanner(mySocket.getInputStream());
                out = new PrintWriter(enemySocket.getOutputStream());
                doService();
            } finally {
                mySocket.close();
                enemySocket.close();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
