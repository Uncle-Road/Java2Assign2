package game;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
    ServerSocket serverSocket;
    List<Socket> clientList;
    List<Socket> online;
    HashMap<String, Player> playerList;
    String archive;
    BufferedReader bReader;
    BufferedWriter bWriter;
    int[][] board;

    public void readFile() throws IOException{
        String filePath = archive;
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis, "gb2312");){
            bReader = new BufferedReader(isr);
            char[] cbuf = new char[16];
            int file_len = bReader.read(cbuf);

            System.out.println(file_len);
            System.out.println(cbuf);

        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Character Encoding is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        }
    }

    public void writeFile() throws IOException{
        try (FileOutputStream fos = new FileOutputStream(new File(archive));
             OutputStreamWriter osw = new OutputStreamWriter(fos, "gbk");
             ){
            bWriter = new BufferedWriter(osw);
            for (Player player : playerList.values()) {
                bWriter.write(player.name + "\n");
                bWriter.write(player.password + "\n");
                bWriter.write(player.num_win + "\n");
                bWriter.write(player.num_games + "\n");
            }
            bWriter.flush();//bWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Character Encoding is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        }
    }

    public Server() throws IOException {
        //initial
        serverSocket = new ServerSocket(9999);
        playerList = new HashMap<>();
        clientList = new ArrayList<>();
        archive = "D:\\code\\java\\Java2Assign2\\src\\game\\archives.txt";
        board = new int[3][3];
        bReader = new BufferedReader(new FileReader(archive));
        online = new ArrayList<>();
        while (bReader.ready()){
            String name = bReader.readLine();
            String password = bReader.readLine();
            int num_win = Integer.parseInt(bReader.readLine());
            int num_games = Integer.parseInt(bReader.readLine());
            Player player = new Player(name,password,num_win,num_games);
            playerList.put(name,player);
        }
        readFile();
        while (true){
            Socket client1 = serverSocket.accept();
            clientList.add(client1);
            Socket client2 = serverSocket.accept();
            clientList.add(client2);
            new GameThread(client1,client2).start();
        }
    }



    public static void main(String[] args) throws IOException {
        new Server();
    }


//    @Override
//    public void run() {
//
//    }

//    public void start(){
//        int player = 1;
//        int x;
//        int y;
//        while (true) {
//            if (player == 1) {
//                x = Integer.parseInt(in1.nextLine());
//                y = Integer.parseInt(in1.nextLine());
//                board[x][y] = 1;
//                out2.println(x);
//                out2.flush();
//                out2.println(y);
//                out2.flush();
//                player = player * (-1);
//            } else {
//                x = Integer.parseInt(in2.nextLine());
//                y = Integer.parseInt(in2.nextLine());
//                board[x][y] = -1;
//                out1.println(x);
//                out1.flush();
//                out1.println(y);
//                out1.flush();
//                player = player * (-1);
//            }
//            if (CheckWin.Check(board) == 2) {
//                out1.println("Game over, winner is X-Player");
//                out1.flush();
//                out2.println("Game over, winner is X-Player");
//                out2.flush();
//            }else if (CheckWin.Check(board) == 1){
//                out1.println("Game over, winner is O-Player");
//                out1.flush();
//                out2.println("Game over, winner is O-Player");
//                out2.flush();
//            }else if (CheckWin.Check(board) == -1) {
//                out1.println("Game over, there is no winner");
//                out1.flush();
//                out2.println("Game over, there is no winner");
//                out2.flush();
//            }
//        }
//    }
}
