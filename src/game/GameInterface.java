//package game;
//
//import javafx.geometry.Pos;
//import javafx.scene.layout.BorderPane;
//
//import javafx.scene.layout.StackPane;
//
//import javafx.scene.paint.Paint;
//import javafx.scene.shape.Circle;
//
//import javafx.scene.text.Font;
//import javafx.scene.text.FontPosture;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.scene.paint.Color;
//
//
//import java.io.FileNotFoundException;
//
//public class GameInterface extends BorderPane {
//    int player = 1;
//    boolean gameOver = false;
//    ChessBoard chessBoard = new ChessBoard();
//    StackPane topStackPane = new StackPane();
//    Text playerO = new Text(10, 10, "It's O-Player's turn.");
//    Text playerX = new Text(10, 10, "It's X-Player's turn.");
//    Text XWin = new Text(10, 10, "Game over, winner is X-Player");
//    Text OWin = new Text(10, 10, "Game over, winner is O-Player");
//    Text Draw = new Text(10, 10, "Game over, there is no winner");
//    public GameInterface() {
//        setAlignment(XWin, Pos.TOP_CENTER);
//        setAlignment(OWin, Pos.TOP_CENTER);
//        setAlignment(Draw, Pos.TOP_CENTER);
//        setAlignment(chessBoard, Pos.CENTER);
//        setAlignment(topStackPane, Pos.TOP_CENTER);
//        playerO.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
//        playerX.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
//        XWin.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
//        OWin.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
//        topStackPane.getChildren().add(playerO);
//        setCenter(chessBoard);
//        setTop(topStackPane);
//    }
//}
