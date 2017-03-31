/**
 * Created by pradh on 3/31/2017.
 */
public class AIGame_Isola {
    int N = 7;
    int[][] Board = new int[N][N];

    //Constructor
    AIGame_Isola() {
        System.out.println("");
        Board[0][3] = 1;
        Board[6][3] = 2;
    }

    //FindPlayer() - finds the player position and returns in the Point Object
    Point findPlayer(int id) {
        Point position = new Point();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (id == Board[i][j]) {
                    position.setX(i);
                    position.setY(j);
                }
            }
        }
        return position;
    }

    //FindBlocked - find the number of blocked states around that point (-1 states on the board)
    int findBlocked(Point position) {
        int blocked = 0;
        try {
            for (int i = position.getX() - 1; i <= position.getX() + 1; i++) {
                for (int j = position.getY() - 1; j <= position.getY() + 1; j++) {
                    if (i != position.getX() && j != position.getY() && Board[i][j] == -1) {
                        blocked++;
                    }
                }
            }
        } catch (Exception e) {
            blocked = -1;
        }
        return blocked;
    }
}

