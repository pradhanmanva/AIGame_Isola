import java.util.Scanner;

public class AIGame_Isola {
    private int N = 7;
    private int[][] Board = new int[N][N];

    /**
     * Constructor
     */
    private AIGame_Isola() {
        System.out.println("");
        Board[0][3] = 1;
        Board[6][3] = 2;
    }

    /**
     * TO get the inout from the user
     */
    private void input() {
        try {
            Scanner scanner = new Scanner(System.in);
            int x, y;
            System.out.println("Would you like to state the initial position for the players? (Y or N)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("Yes")) {
                System.out.println("Player 1 : \n\tX-coordinate : ");
                x = scanner.nextInt();
                System.out.println("\tY-coordinate : ");
                y = scanner.nextInt();
                Board[x][y] = 1;

                System.out.println("Player 2 : \n\tX-coordinate : ");
                x = scanner.nextInt();
                System.out.println("\tY-coordinate : ");
                y = scanner.nextInt();
                Board[x][y] = 2;
            } else {
                Board[0][3] = 1;
                Board[6][3] = 2;
            }
        } catch (Exception e) {
            Board[0][3] = 1;
            Board[6][3] = 2;
        }
    }

    public static void main(String[] args) {
        AIGame_Isola isola = new AIGame_Isola();
        isola.input();
        int turn = 1;
        while (isola.myMove(turn)) {
            isola.blockMove(turn);
            turn = turn == 1 ? 2 : 1;
        }
    }

    /**
     * To find the player position and returns in the Point Object
     *
     * @param id - player ID
     * @return Point object for the position
     */
    private Point findPlayer(int id) {
        Point position = new Point(0, 0);
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

    /**
     * To find the number of blocked states around that point (-1 states on the board)
     *
     * @param position - gets the position of the blocked states
     * @return Number of BlockedStates around it
     */
    private int findBlocked(Point position) {
        int blocked = 0;
        int x = position.getX();
        int y = position.getY();
        try {
            for (int i = x - 1; i <= x + 1; i++) {
                if (i < 0 || i > 6)
                    continue;

                for (int j = y - 1; j <= y + 1; j++) {
                    if (j < 0 || j > 6)
                        continue;

                    if (i != x && j != y && Board[i][j] == -1) {
                        blocked++;
                    }
                }
            }
        } catch (Exception e) {
            blocked = -1;
        }
        return blocked;
    }

    /**
     * Function checks if any of the 8 moves is doable or not.
     *
     * @param point     the coordinate of player
     * @param initPoint this will save the coordinate which is empty
     * @return true if any move is possible and false otherwise
     */
    private boolean moveable(Point point, Point initPoint) {
        int x = point.getX(), y = point.getY();

        for (int i = x + 1; i >= x - 1; i--) {
            if (i < 0 || i > 6)
                continue;
            for (int j = y + 1; j >= y - 1; j--) {
                if (j < 0 || j > 6)
                    continue;

                if (i == x && j == y)
                    continue;

                if (Board[i][j] == 0) {
                    initPoint.setX(i);
                    initPoint.setY(j);
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Moves the current player to best position
	 * Param:
	 * 		playerId: The calling playerId
	 * Return:
	 * 		None. Moves the Player to Best Position 
	 */
    private boolean myMove(int playerId) {
        Point myCoordinates = findPlayer(playerId);
        Point initPoint = new Point(0, 0);
        if (moveable(myCoordinates, initPoint)) {
            findBestMove(playerId, myCoordinates, initPoint);
            return true;
        } else {
            int won = playerId == 1 ? 2 : 1;
            System.out.println("Player ID: " + won + " Won");
            return false;
        }
    }

    /*
     * Find Best Move for player.
     * Find no of blocked node around the player and selects the minimum blocked one.
     * Param:
     * 		playerId: The calling playerId
     * 		myCoordinates: Coordinates of Calling Player
     * Return:
     * 		None
     */
    private void findBestMove(int playerId, Point myCoordinates, Point initPoint) {
        int x = myCoordinates.getX();
        int y = myCoordinates.getY();

        Point minPoint = new Point(initPoint.getX(), initPoint.getY());
        int minWeakness = findBlocked(new Point(initPoint.getX(), initPoint.getY()));

        for (int i = x + 1; i >= x - 1; i--) {
            if (i < 0 || i > 6)
                continue;
            for (int j = y + 1; j >= y - 1; j--) {
                if (j < 0 || j > 6)
                    continue;
                if (Board[i][j] != 0)
                    continue;
                int weakness = findBlocked(new Point(i, j));
                if (weakness < minWeakness) {
                    minPoint.setX(i);
                    minPoint.setY(j);
                    minWeakness = weakness;
                }
            }
        }

        Board[x][y] = 0;
        Board[minPoint.getX()][minPoint.getY()] = playerId;
        System.out.println("Player ID: " + playerId + " moved to: " + minPoint.getX() + "," + minPoint.getY());
    }

    /*
     * Block the best Square
     * Param:
     * 		playerId: The calling playerId. Our aim is to block the other player i.e. the opponent.
     * Return:
     * 		None.
     */
    private void blockMove(int playerId) {
        Point myCoordinates = findPlayer(playerId == 1 ? 2 : 1);
        Point initPoint = new Point(0, 0);
        //Just calling to get initPoints
        moveable(myCoordinates, initPoint);
        findBestBlock(myCoordinates, initPoint);
    }

    /*
     * Find Best Block.
     * Find no of blocked node around the player and selects the maximum blocked one.
     * Param:
     * 		myCoordinates: Coordinates of opponent
     * Return:
     * 		None
     */
    private void findBestBlock(Point myCoordinates, Point initPoint) {
        int x = myCoordinates.getX();
        int y = myCoordinates.getY();

        Point maxPoint = new Point(initPoint.getX(), initPoint.getY());
        int maxWeakness = findBlocked(new Point(initPoint.getX(), initPoint.getY()));

        for (int i = x + 1; i >= x - 1; i--) {
            if (i < 0 || i > 6)
                continue;
            for (int j = y + 1; j >= y - 1; j--) {
                if (j < 0 || j > 6)
                    continue;
                if (Board[i][j] != 0)
                    continue;
                int weakness = findBlocked(new Point(i, j));
                if (weakness > maxWeakness) {
                    maxPoint.setX(i);
                    maxPoint.setY(j);
                    maxWeakness = weakness;
                }
            }
        }
        Board[maxPoint.getX()][maxPoint.getY()] = -1;
        System.out.println("Blocked: " + maxPoint.getX() + "," + maxPoint.getY());
    }
}

