package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static application.CheckWin.judge;

public class DrawXO implements Initializable {

    private static final int PLAY_1 = 1;
    private static final int PLAY_2 = 2;
    private static final int EMPTY = 0;
    private static final int BOUND = 90;
    private static final int OFFSET = 15;
    private static boolean TURN = false;
    private static final int[][] board = new int[3][3];
    private static final boolean[][] flag = new boolean[3][3];

    private static int MYTURN;

    @FXML
    private Pane base_square;

    @FXML
    private Rectangle game_panel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client client = null;

        Socket socket = null;
        try {
            socket = new Socket("localhost", 8884);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = new Client(socket);

        Client finalClient = client;
        Thread thread = new Thread(finalClient);
        thread.start();

        new Thread(() -> {

            boolean flag1 = true;
            boolean flag2 = true;

            while (true) {
                if (check() != 0) {
                    if (check() == MYTURN) {
                        System.out.println("Game over, winner is you");
                    } else if (check() == 3 - MYTURN) {
                        System.out.println("Game over, loser is you");
                    } else if (check() == 3) {
                        System.out.println("Game over, there is no winner");
                    }
                    break;
                }


                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                String enemyMove = finalClient.getEnemyMove();

                if (enemyMove != null) {

                    if (enemyMove.equals("Only 1 player now, waiting...") && flag1) {
                        System.out.println(enemyMove);
                        flag1 = false;
                    } else if (enemyMove.contains("2 players now") && flag2) {
                        MYTURN = Integer.parseInt(String.valueOf(enemyMove.charAt(0)));
                        System.out.println("2 players now, you are player" + MYTURN);

                        if (MYTURN == 1) {
                            TURN = true;
                        } else if (MYTURN == 2) {
                            TURN = false;
                        }
                        flag2 = false;
                    } else if (enemyMove.length() == 5) {
                        int enemyX = Integer.parseInt(String.valueOf(enemyMove.charAt(0)));
                        int enemyY = Integer.parseInt(String.valueOf(enemyMove.charAt(2)));
                        Platform.runLater(() -> {
                            if (enemyRefreshBoard(enemyX, enemyY)) {
                                TURN = !TURN;
                            }
                        });
                    }
                }
            }
        }).start();

        game_panel.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / BOUND);
            int y = (int) (event.getY() / BOUND);
            if (meRefreshBoard(x, y)) {
                TURN = !TURN;
                try {
                    finalClient.sendMove(x, y, MYTURN);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public int check() {
        return judge(board);
    }

    private boolean enemyRefreshBoard(int x, int y) {
        if (board[x][y] == EMPTY) {
            if (!TURN) {
                board[x][y] = 3 - MYTURN;
                drawChess();
                System.out.println("Opponent moves at " + x + "," + y);
                return true;
            }
        }
        return false;
    }

    private boolean meRefreshBoard(int x, int y) {
        if (board[x][y] == EMPTY) {
            if (TURN) {
                board[x][y] = MYTURN;
                drawChess();
                System.out.println("You move at (" + x + "," + y+")");
                return true;
            }
        }
        return false;
    }

    private void drawChess() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (flag[i][j]) {
                    // This square has been drawing, ignore.
                    continue;
                }
                switch (board[i][j]) {
                    case PLAY_1:
                        drawCircle(i, j);
                        break;
                    case PLAY_2:
                        drawLine(i, j);
                        break;
                    case EMPTY:
                        // do nothing
                        break;
                    default:
                        System.err.println("Invalid value!");
                }
            }
        }
    }

    private void drawCircle(int i, int j) {
        Circle circle = new Circle();
        base_square.getChildren().add(circle);
        circle.setCenterX(i * BOUND + BOUND / 2.0 + OFFSET);
        circle.setCenterY(j * BOUND + BOUND / 2.0 + OFFSET);
        circle.setRadius(BOUND / 2.0 - OFFSET / 2.0);
        circle.setStroke(Color.RED);
        circle.setFill(Color.TRANSPARENT);
        flag[i][j] = true;
    }

    private void drawLine(int i, int j) {
        Line line_a = new Line();
        Line line_b = new Line();
        base_square.getChildren().add(line_a);
        base_square.getChildren().add(line_b);
        line_a.setStartX(i * BOUND + OFFSET * 1.5);
        line_a.setStartY(j * BOUND + OFFSET * 1.5);
        line_a.setEndX((i + 1) * BOUND + OFFSET * 0.5);
        line_a.setEndY((j + 1) * BOUND + OFFSET * 0.5);
        line_a.setStroke(Color.BLUE);

        line_b.setStartX((i + 1) * BOUND + OFFSET * 0.5);
        line_b.setStartY(j * BOUND + OFFSET * 1.5);
        line_b.setEndX(i * BOUND + OFFSET * 1.5);
        line_b.setEndY((j + 1) * BOUND + OFFSET * 0.5);
        line_b.setStroke(Color.BLUE);
        flag[i][j] = true;
    }
}
