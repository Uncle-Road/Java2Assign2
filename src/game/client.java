package game;

import java.io.*;
import java.util.*;
import java.net.*;

public class client extends Thread{
    Socket client;
    String state;
    String name;
    String password;
    int num_win;
    int num_games;
    int[][] match;
    List<String> playerList;
    PrintWriter printWriter;
    BufferedReader bufferedReader;



    @Override
    public void run(){
        try{
            InetAddress localAddress = InetAddress.getLocalHost();
            client = new Socket(localAddress,1234);
            state = "ready";
            match = new int[3][3];
            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            printWriter = new PrintWriter(client.getOutputStream(),true);


        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
