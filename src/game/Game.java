package game;

import gui.GameWindow;
import gui.Window;

public abstract class Game {
    public static final int WIDTH = 600, HEIGHT = 600;
    public static final int FIELD_WIDTH = WIDTH / 3, FIELD_HEIGHT = HEIGHT / 3;
    public static final int FREE = 0, PLAYER_ONE = 1, PLAYER_TWO = 2;
    protected static final int PORT = 4444;
    private Window window;
    protected GameWindow gameWindow;
    protected int[][] fields;
    protected int currentPlayer;
    protected int thisPlayer;

    public Game(int thisPlayer) {
        this.thisPlayer = thisPlayer;
        fields = new int[3][3];
        window = new Window(this, "Tic tac toe", WIDTH, HEIGHT);
        gameWindow = new GameWindow(this);
        window.add(gameWindow);
        window.setVisible(true);
        currentPlayer = Game.PLAYER_ONE;
    }

    public abstract void inputReceived(int x, int y);

    public abstract void close();

    public abstract void packetReceived(Object o);

    public int[][] getFields() {
        return this.fields;
    }

    protected boolean isMyTurn(){
        return this.thisPlayer == this.currentPlayer;
    }
}
