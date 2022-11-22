package game;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Connection implements Runnable {
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean running;
    private Game game;

    public Connection(Game game, Socket socket) {
        this.game = game;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    public void sendPacket(Object object) {
        try {
            outputStream.reset();
            outputStream.writeObject(object);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Object ojb = inputStream.readObject();
                game.packetReceived(ojb);
            } catch (EOFException | SocketException e) {
                running = false;
                System.out.println(e);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            if (isFinish()) {
                close();
            }
        }
    }

    public boolean isFinish() {
        if (check() != 0) {
            if (check() == 1) {
                System.out.println("Game over, winner is X-Player");
            }
            if (check() == 2) {
                System.out.println("Game over, winner is O-Player");
            }
            if (check() == 3) {
                System.out.println("Game over, there is no winner");
            }
            return true;
        }
        return false;
    }

    public int check() {
        return judge(game.getFields());
    }

    static int judge(int[][] board) {
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[1][1];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[1][1];
        }
        for (int i = 0; i < 3; i++) {
            boolean row = false;
            boolean column = false;
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                row = true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                column = true;
            }
            if (row) {
                return board[i][1];
            }
            if (column) {
                return board[1][i];
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return 0;
                }
            }
        }
        return 3;
    }


    public void close() {
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
