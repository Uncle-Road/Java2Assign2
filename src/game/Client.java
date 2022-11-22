package game;

import javafx.application.Application;

import java.io.*;
import java.util.*;
import java.net.*;

public class Client extends Thread {
    Socket client;
    String state;
    String name;
    String password;
    int num_win;
    int num_games;
    int[][] match;
    List<String> playerList;
    int[][] board;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    boolean updat;



    @Override
    public void run(){
        try{
            InetAddress localAddress = InetAddress.getLocalHost();
            client = new Socket(localAddress,9999);
            state = "ready";
            match = new int[3][3];
            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            printWriter = new PrintWriter(client.getOutputStream(),true);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
