package game;

import packets.ClientPlayPacket;
import packets.UpdatePacket;

import java.io.IOException;
import java.net.Socket;

public class ClientGame extends Game {
    private Socket socket;
    private Connection connection;

    public ClientGame() {
        super(Game.PLAYER_TWO);
        try {
            socket = new Socket("localhost", Game.PORT);
            connection = new Connection(this, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputReceived(int x, int y) {
        if (isMyTurn()) {
            connection.sendPacket(new ClientPlayPacket(x, y));
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void packetReceived(Object o) {
        if (o instanceof UpdatePacket) {
            UpdatePacket packet = (UpdatePacket) o;
            fields = packet.getFields();
            currentPlayer = packet.getCurrentPlayer();
        }
        gameWindow.repaint();
    }
}
