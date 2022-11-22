package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Service implements Runnable {

    public CheckWin checkWin;
    private final Socket mySocket;
    private final Socket enemySocket;
    private Scanner in;
    private PrintWriter out;

    public Service(Socket mySocket, Socket enemySocket, CheckWin checkWin) {
        this.mySocket = mySocket;
        this.enemySocket = enemySocket;
        this.checkWin = checkWin;
    }

    public void play() throws IOException {
        while (true) {
            if (!in.hasNext()) {
                return;
            } else {
                System.out.println("end service");
            }
            String receiveMsg = in.next();
            if (!receiveMsg.equals("")) {
                checkWin.move(receiveMsg);
                System.out.println("Usert" + receiveMsg.charAt(4) + " moves at " + receiveMsg.substring(0, 3));
            } else {
                System.out.println("null message");
            }
            String sendMsg = receiveMsg;
            if (!sendMsg.equals("")) {
                out.println(sendMsg);
                out.flush();
            } else {
                System.out.println("null recive message");
            }


        }
    }

    public void LeavingJudge() throws IOException {
        PrintWriter toMyClient = new PrintWriter(mySocket.getOutputStream());
        toMyClient.println("Your Enemy is leaving");
        toMyClient.flush();
        toMyClient.close();
    }

    @Override
    public void run() {
        try {
            try {
                in = new Scanner(mySocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out = new PrintWriter(enemySocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                mySocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                enemySocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
