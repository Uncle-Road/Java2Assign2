package game;

public class CheckWin {
    public static int Check(int[][] board) {
        //0 continues 1 play1 win 2 play2 win 3 peace
        if (win(board, 1)) return 1;
        if (win(board, -1)) return 2;
        for(int i = 0;i< 3;i++){
            for (int j = 0;j<3;j++){
                if (board[i][j]== 0){
                    return 0;
                }
            }
        }
        return 3;
    }

    public static boolean win(int[][] board, int player) {
        if(board[0][0]==player&&board[1][1]==player&&board[2][2]==player) return true;
        if(board[0][2]==player&&board[1][1]==player&&board[2][0]==player) return true;
        for(int i =0; i < 3;i++){
            int row = 0;
            int column = 0;
            for(int j = 0; j < 3; i++){
                if (board[i][j]==player){
                    row ++;
                }
                if (board[j][i]==player){
                    column++;
                }
            }
            if(row == 3 || column== 3){
                return true;
            }
        }
        return false;
    }


}
