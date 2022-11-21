import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.net.*;

public class server {
    ServerSocket serverSocket;
    List<Socket> clientList;
    HashMap<String, player> playerList;
//    HashMap<Socket, String> getOnline;
    String archive;

    public void readFile() throws IOException{
        String filePath = archive;
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis, "gb2312");
             BufferedReader bReader = new BufferedReader(isr);){

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
             BufferedWriter bWriter = new BufferedWriter(osw);){
            for (player player : playerList.values()) {
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

    public server() throws IOException {
        //initial
        serverSocket = new ServerSocket(9999);
        playerList = new HashMap<>();
        clientList = new ArrayList<>();
        archive = "D:\\code\\java\\Java2Assign2\\src\\archives.txt";


        while (true) {
            Socket client = serverSocket.accept();
            clientList.add(client);
        }
    }


    public static void main(String[] args) {

    }

}

class player{
    String name;
    String password;
    int num_win;
    int num_games;

    public player( String name,
            String password,
            int num_win,
            int num_games){
        this.name = name;
        this.password = password;
        this.num_win = num_win;
        this.num_games = num_games;
    }
}
