package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import packets.ClientPlayPacket;
import packets.UpdatePacket;


public class ServerGame extends Game {
    private ServerSocket serverSocket;
    private Socket socket;
    private Connection connection;

    public ServerGame() {
        super(Game.PLAYER_ONE);
        try {
            serverSocket = new ServerSocket(Game.PORT);
            socket = serverSocket.accept();
            connection = new Connection(this, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputReceived(int x, int y) {
        System.out.println(x + " " + y);
        if (isMyTurn()) {
            updateField(x, y);
        }
        gameWindow.repaint();
    }

    private void updateField(int x, int y) {
        if (fields[x][y] == Game.FREE) {
            fields[x][y] = currentPlayer;
            if (currentPlayer == Game.PLAYER_ONE) {
                currentPlayer = Game.PLAYER_TWO;
            } else if (currentPlayer == Game.PLAYER_TWO) {
                currentPlayer = Game.PLAYER_ONE;
            }
            connection.sendPacket(new UpdatePacket(fields, currentPlayer));
//            if (isFinish()){
//                close();
//            }
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
        return judge(fields);
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


    @Override
    public void close() {
        System.out.println("Closing");
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void packetReceived(Object o) {
        if (o instanceof ClientPlayPacket) {
            ClientPlayPacket c = (ClientPlayPacket) o;
            int x = c.getX();
            int y = c.getY();
            updateField(x, y);
        }
        gameWindow.repaint();
    }
}
