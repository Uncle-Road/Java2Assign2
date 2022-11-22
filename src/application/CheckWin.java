package application;

public class CheckWin {

    private static final int[][] board = new int[3][3];

    public void move(String index) {
        board[Integer.parseInt(String.valueOf(index.charAt(0)))][Integer.parseInt(String.valueOf(index.charAt(2)))] = Integer.parseInt(String.valueOf(index.charAt(4)));;
        switch (check()){
            case 1:
                System.out.println("Game over, winner is O-Player");
            case 2:
                System.out.println("Game over, winner is X-Player");
            case 3:
                System.out.println("Game over, there is no winner");
        }
    }

    public int check() {
        return judge(board);
    }

    static int judge(int[][] board) {
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[1][1];
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[1][1];
        for (int i = 0; i < 3; i++) {
            boolean row = false;
            boolean column = false;
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                row = true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                column = true;
            }
            if (row) return board[i][1];
            if (column) return board[1][i];
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

}
