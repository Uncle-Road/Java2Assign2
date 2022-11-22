package game;

public class player{
    String name;
    String password;
    int num_win;
    int num_games;

    public player( String name,
                   String password,
                   int num_win,
                   int num_games){
        this.name = name;
        this.password = password;
        this.num_win = num_win;
        this.num_games = num_games;
    }
}