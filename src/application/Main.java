package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource("mainUI.fxml"));
            Pane root = fxmlLoader.load();
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

            new Thread(() -> {
                try {
                    Socket testSocket = new Socket("localhost", 8886);
                    Scanner in = new Scanner(testSocket.getInputStream());
                    PrintWriter out = new PrintWriter(testSocket.getOutputStream());
                    while (true) {
                        if (!in.hasNext()) {
                            Thread.sleep(300);
                            if (!in.hasNext()) {
                                System.err.println("Server down!");
                                break;
                            }
                        }
                        String msg = in.next();
                        out.println(msg);
                        out.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            primaryStage.setOnCloseRequest(event -> {

                Socket closeSocket = null;
                try {
                    closeSocket = new Socket("localhost", 8885);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PrintWriter out = null;
                try {
                    out = new PrintWriter(closeSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.println("Close");
                    out.flush();
                    System.out.println("Close");
                    System.exit(0);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
