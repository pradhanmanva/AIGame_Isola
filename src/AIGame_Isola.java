
public class AIGame_Isola {
    int N = 7;
    int[][] Board = new int[N][N];

    /**
     * Constructor
     */
    AIGame_Isola() {
        System.out.println("");
        Board[0][3] = 1;
        Board[6][3] = 2;
    }

    /**
     * to collect the input from the user
     */
    void input() {

    }
    
    public static void main(String[] args) {
		Isola isola = new Isola();
		
		int turn=1;
		while(isola.myMove(turn)){
				isola.blockMove(turn);
				turn = turn==1?2:1;
		}
	}

    /**
     * FindPlayer() - finds the player position and returns in the Point Object
     *
     * @param id - player ID
     * @return Point object for the position
     */
    Point findPlayer(int id) {
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
     * @return
     */
    int findBlocked(Point position) {
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
	 * Function checks if any of the 8 moves is do able of not.
	 * @param point the coordinate of player
	 * @return true if any move is possible and false otherwise
	 */
	public boolean moveable(Point point){
		int x = point.getX(), y = point.getY();
		
		for(int i= x+1;i>=x-1;i--){
			if( i<0 || i>6)
				continue;
			for(int j=y+1;j>=y-1;j--){
				if (j<0 || j>6)
					continue;
				
				if (i == x && j == y)
					continue;
				
				if(Board[i][j] == 0)
					return true;
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
    public boolean myMove(int playerId) {
        Point myCoordinates = findPlayer(playerId);
        if(moveable(myCoordinates)){
			findBestMove(playerId,myCoordinates);
			return true;
        }
		else{
			int won = playerId==1?2:1;
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
    private void findBestMove(int playerId, Point myCoordinates) {
        int x = myCoordinates.getX();
        int y = myCoordinates.getY();
        
        int init_x = x+1>6?x-1:x+1;
        int init_y = y+1>6?y-1:y+1;

        Point minPoint = new Point(init_x,init_y);
        int minWeakness = findBlocked(new Point(init_x, init_y));

        for (int i = x + 1; i >= x - 1; i--) {
            if (i < 0 || i > 6)
                continue;
            for (int j = y + 1; j >= y - 1; j--) {
                if (j < 0 || j > 6)
                    continue;
                if(Board[i][j] != 0)
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
    public void blockMove(int playerId) {
        Point myCoordinates = findPlayer(playerId == 1 ? 2 : 1);
        findBestBlock(myCoordinates);
    }

    /*
     * Find Best Block.
     * Find no of blocked node around the player and selects the maximum blocked one.
     * Param:
     * 		myCoordinates: Coordinates of opponent
     * Return:
     * 		None
     */
    private void findBestBlock(Point myCoordinates) {
        int x = myCoordinates.getX();
        int y = myCoordinates.getY();

        int init_x = x+1>6?x-1:x+1;
        int init_y = y;
        
        Point maxPoint = new Point(init_x, init_y);
        int maxWeakness = findBlocked(new Point(init_x, init_y));;

        for (int i = x + 1; i >= x - 1; i--) {
            if (i < 0 || i > 6)
                continue;
            for (int j = y + 1; j >= y - 1; j--) {
                if (j < 0 || j > 6)
                    continue;
                if(Board[i][j] != 0)
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

