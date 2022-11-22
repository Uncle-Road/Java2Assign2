package game;

public class Player {
    int id = 0;
    String name;
    String password;
    int num_win;
    int num_games;

    public Player(
                    String name,
                  String password,
                  int num_win,
                  int num_games){
        this.name = name;
        this.password = password;
        this.num_win = num_win;
        this.num_games = num_games;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}