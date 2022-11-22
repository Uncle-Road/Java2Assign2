package game;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server implements Runnable{
    ServerSocket serverSocket;
    List<Socket> clientList;
    HashMap<String, Player> playerList;
//    HashMap<Socket, String> getOnline;
    String archive;
    BufferedReader bReader;
    BufferedWriter bWriter;

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
        archive = "D:\\code\\java\\Java2Assign2\\src\\archives.txt";

    }



    public static void main(String[] args) throws IOException {
        new Server();
    }

    @Override
    public void run() {

    }
}
