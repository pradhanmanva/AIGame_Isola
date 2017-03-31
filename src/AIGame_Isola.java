/**
 * Created by pradh on 3/31/2017.
 */
public class AIGame_Isola {
    int N = 7;
    int[][] Board = new int[N][N];

    //Constructor
    AIGame_Isola() {
        System.out.println("");
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

    //FindBlocked - find the number of blocked states around that point
    int findBlocked(Point position) {
        return
    }
}

