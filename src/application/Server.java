package application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static List<Socket> players;
    List<Socket> clients;
    HashMap<String, Socket> online;
    HashMap<String, Player> playerList;
    String archive;
    BufferedReader bReader;
    BufferedWriter bWriter;
    int[][] board;
    ServerSocket serverSocket;
    ServerSocket heartBeatSocket;
    ServerSocket receiverSocket;

    public void readFile() throws IOException {
        String filePath = archive;
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis, "gb2312");) {
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

    public void writeFile() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File(archive));
             OutputStreamWriter osw = new OutputStreamWriter(fos, "gbk");
        ) {
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
        serverSocket = new ServerSocket(8884);
        heartBeatSocket = new ServerSocket(8885);
        receiverSocket = new ServerSocket(8886);
        archive = "D:\\code\\java\\Java2Assign2\\src\\application\\archives.txt";
        bReader = new BufferedReader(new FileReader(archive));
        playerList = new HashMap<>();
        clients = new ArrayList<>();
        board = new int[3][3];
        online = new HashMap<>();
        while (bReader.ready()) {
            String name = bReader.readLine();
            String password = bReader.readLine();
            int num_win = Integer.parseInt(bReader.readLine());
            int num_games = Integer.parseInt(bReader.readLine());
            Player player = new Player(name, password, num_win, num_games);
            playerList.put(name, player);
        }
        readFile();

        while (true) {
            players = new ArrayList<>();
            CheckWin checkWin = new CheckWin();
            while (players.size() < 2) {
                Socket socket = serverSocket.accept();
                Socket clientSocket = receiverSocket.accept();
                new Thread(() -> {
                    Scanner in = null;
                    PrintWriter out = null;
                    try {
                        in = new Scanner(clientSocket.getInputStream());
                        out = new PrintWriter(clientSocket.getOutputStream());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    out.println("waiting");
                    out.flush();

                    while (true) {
                        try {
                            assert in != null;
                            if (!in.hasNext()) {
                                return;
                            }
                            String msg = in.nextLine();
//                                System.out.println("Msg from client: " + msg);
                            Thread.sleep(100);
                            out.println(msg);
                            out.flush();
//                                System.out.println("msg sent");
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }).start();
                players.add(socket);
                if (players.size() < 2) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("Only 1 player now, waiting...");
                    out.flush();
                } else if (players.size() == 2) {
                    PrintWriter out1 = new PrintWriter(players.get(0).getOutputStream());
                    PrintWriter out2 = new PrintWriter(players.get(1).getOutputStream());
                    out1.println("1, 2 players now");
                    out2.println("2, 2 players now");
                    out1.flush();
                    out2.flush();
                }
                System.out.println("player" + players.size() + " connected");
            }

            ChessService chessService1 = new ChessService(players.get(0), players.get(1), checkWin);
            ChessService chessService2 = new ChessService(players.get(1), players.get(0), checkWin);
            Thread thread1 = new Thread(chessService1);
            Thread thread2 = new Thread(chessService2);
            thread1.start();
            thread2.start();

            new Thread(() -> {
                while (true) {
                    try {
                        Socket closeSocket = heartBeatSocket.accept();
                        Scanner in = new Scanner(closeSocket.getInputStream());
                        if (!in.hasNext()) {
                            return;
                        }
                        String msg = in.nextLine();
                        Thread.sleep(300);
                        if (msg.equals("Close")) {
                            System.out.println("Player is leaving");
                            chessService1.verify();
                            chessService2.verify();
                        }
                        in.close();
                    } catch (IOException | InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }).start();
        }
    }


    public static void main(String[] args) throws IOException {
        new Server();
    }
}

