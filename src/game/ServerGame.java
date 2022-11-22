package game;

import packets.ClientPlayPacket;
import packets.UpdatePacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
        }
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
        if(o instanceof ClientPlayPacket){
            ClientPlayPacket c = (ClientPlayPacket) o;
            int x = c.getX();
            int y = c.getY();
            updateField(x,y);
        }
        gameWindow.repaint();
    }
}
