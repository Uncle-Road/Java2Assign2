//package game;
//
//import javafx.application.*;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.*;
//import java.util.*;
//import java.net.*;
//
//import game.DrawXO.ChessBoard;
//import game.DrawXO.CirclePane;
//import game.DrawXO.XPane;
//
//public class Client extends Application {
//    Socket client;
//    String state;
//    String name;
//    String password;
//    int num_win;
//    int num_games;
//    int[][] match;
//    List<String> playerList;
//    int[][] board;
//    PrintWriter printWriter;
//    BufferedReader bufferedReader;
//    boolean update;
//
//
//    public static void main(String[] args) throws IOException {
//        launch(args);
//    }
//
//    Scanner in;
//    PrintWriter out;
//    int player;
//    int selectX;
//    int selectY;
//    int[] currentPlayer = {1};
//    private boolean waiting = true;
//    GameInterface gameInterface;
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        final int GAME_PORT = 8898;
//        gameInterface = new GameInterface();
//        stage.setTitle("Game");
//        Scene scene = new Scene(gameInterface, 250, 230);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        gameInterface.chessBoard.setOnMouseClicked(e -> {
//            if(e.getX() > 48 && e.getX() < 198 && e.getY() > 2 && e.getY() < 152 && currentPlayer[0] == player) {
//                selectX = (int)((e.getX() - 48) / 51);
//                selectY = (int)((e.getY() - 2) / 51);
//                setChess(selectX, selectY, currentPlayer);
//                waiting = false;
//            }
//        });
//        stage.show();
//        Socket s = new Socket("localhost",  GAME_PORT);
//        in = new Scanner(s.getInputStream());
//        out = new PrintWriter(s.getOutputStream());
//        new Thread(() -> {
//            player = Integer.parseInt(in.nextLine());
//            if (player == 1) {
//                System.out.println(in.nextLine());
//            }
//            System.out.println(in.nextLine());
//            while (!gameInterface.gameOver) {
//                if (player == 1) {
//                    try {
//                        waitForPlayer();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    sendMove();
//                    receiveMove();
//                } else if (player == -1) {
//                    receiveMove();
//                    try {
//                        waitForPlayer();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    sendMove();
//                }
//            }
//        }).start();
//    }
//    public void sendMove() {
//        out.println(selectX);
//        out.flush();
//        out.println(selectY);
//        out.flush();
//    }
//    public void waitForPlayer() throws InterruptedException {
//        while (waiting) {
//            Thread.sleep(100);
//        }
//        waiting = true;
//    }
//    public void receiveMove() {
//        int x = in.nextInt();
//        System.out.println(x);
//        int y = in.nextInt();
//        System.out.println(y);
//        Platform.runLater(() ->setChess(x, y, currentPlayer));
//    }
//    public void setChess(int x, int y, int[] currentPlayer) {
//        double x1 = 48 + x * 51;
//        double y1 = 2 + y * 51;
//        double x2 = 48 + (x + 1) * 51;
//        double y2 = 2 + (y + 1) * 51;
//        if (currentPlayer[0] == 1 && !gameInterface.gameOver && gameInterface.chessBoard.chessBoard[x][y] == 0) {
//            gameInterface.chessBoard.chessBoard[x][y] = currentPlayer[0];
//            gameInterface.chessBoard.getChildren().add(new CirclePane(x,y));
//            gameInterface.topStackPane.getChildren().clear();
//            gameInterface.topStackPane.getChildren().add(gameInterface.playerX);
//        } else if (!gameInterface.gameOver && gameInterface.chessBoard.chessBoard[x][y] == 0) {
//            gameInterface.chessBoard.chessBoard[x][y] = currentPlayer[0];
//            gameInterface.chessBoard.getChildren().add(new XPane(x,y));
//            gameInterface.topStackPane.getChildren().clear();
//            gameInterface.topStackPane.getChildren().add(gameInterface.playerO);
//        }
//        if (CheckWin.Check(gameInterface.chessBoard.chessBoard) == 2) {
//            gameInterface.gameOver = true;
//            gameInterface.setBottom(gameInterface.XWin);
//        }else if (CheckWin.Check(gameInterface.chessBoard.chessBoard) == 1){
//            gameInterface.gameOver = true;
//            gameInterface.setBottom(gameInterface.OWin);
//        }else if (CheckWin.Check(gameInterface.chessBoard.chessBoard) == 0) {
//            gameInterface.gameOver = true;
//            gameInterface.setBottom(gameInterface.Draw);
//        }
//        currentPlayer[0] = -currentPlayer[0];
//    }
//
//}
