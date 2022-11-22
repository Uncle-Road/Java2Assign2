package game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawXO implements Initializable {
    private static final int BOUND = 90;
    private static final int OFFSET = 15;

    private static final int[][] chessBoard = new int[3][3];
    private static final boolean[][] flag = new boolean[3][3];

    private static boolean TURN = false;

    @FXML
    private static Pane base_square;

    @FXML
    private Rectangle game_panel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game_panel.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / BOUND);
            int y = (int) (event.getY() / BOUND);
        });
    }

    public static class CirclePane extends Pane {
        public CirclePane(int i, int j) {
            Circle circle = new Circle();
            circle.setCenterX(i * BOUND + BOUND / 2.0 + OFFSET);
            circle.setCenterY(j * BOUND + BOUND / 2.0 + OFFSET);
            circle.setRadius(BOUND / 2.0 - OFFSET / 2.0);
            circle.setStroke(Color.RED);
            circle.setFill(Color.TRANSPARENT);
            base_square.getChildren().add(circle);
            flag[i][j] = true;
        }
    }

    public static class XPane extends Pane{
        public XPane(int i, int j) {
            Line line_a = new Line();
            Line line_b = new Line();

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

            base_square.getChildren().add(line_a);
            base_square.getChildren().add(line_b);
            flag[i][j] = true;
        }
    }

    public static class ChessBoard extends StackPane {
        int[][] chessBoard = new int[3][3];
        GridPane recGridPane = new GridPane();

        public ChessBoard() {
            recGridPane.setAlignment(Pos.TOP_CENTER);
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    Rectangle r1 = new Rectangle(50, 50, 50, 50);
                    r1.setStroke(Color.BLACK);
                    r1.setFill(Color.WHITE);
                    r1.setStrokeWidth(1);
                    recGridPane.add(r1, i, k);
                }
            }
            getChildren().addAll(recGridPane);
        }
    }
}
