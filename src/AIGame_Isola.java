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
    
    /*
	 * Moves the current player to best position
	 * Param:
	 * 		playerId: The calling playerId
	 * Return:
	 * 		None. Moves the Player to Best Position 
	 */
	public void myMove(int playerId){
		Point myCoordinates = findPlayer(playerId);
		findBestMove(playerId,myCoordinates);
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
	private void findBestMove(int playerId,Point myCoordinates) {
		int x = myCoordinates.getX();
		int y = myCoordinates.getY();
		
		Point minPoint = new Point(0,0);
		int minWeakness = 0;
		
		for(int i= x+1;i<=x-1;i--){
			if( i<0 || i>6)
				continue;
			for(int j=y+1;j<=y-1;i--){
				if (j<0 || j>6)
					continue;
				int weakness = findBlocked(new Point(i,j));
				if(weakness<minWeakness){
					minPoint.setX(i);
					minPoint.setY(j);
					minWeakness = weakness;
				}
			}
		}
		
		Board[x][y]=0;
		Board[minPoint.getX()][minPoint.getY()] = playerId;
	}
	
	/*
	 * Block the best Square 
	 * Param:
	 * 		playerId: The calling playerId. Our aim is to block the other player i.e. the opponent.
	 * Return:
	 * 		None. 
	 */
	public void blockMove(int playerId){
		Point myCoordinates = findPlayer(playerId==1?2:1);
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
		
		Point maxPoint = new Point(0,0);
		int maxWeakness = 0;
		
		for(int i= x+1;i<=x-1;i--){
			if( i<0 || i>6)
				continue;
			for(int j=y+1;j<=y-1;i--){
				if (j<0 || j>6)
					continue;
				int weakness = findBlocked(new Point(i,j));
				if(weakness>maxWeakness){
					maxPoint.setX(i);
					maxPoint.setY(j);
					maxWeakness = weakness;
				}
			}
		}
		Board[maxPoint.getX()][maxPoint.getY()] = -1;
	}
	
}

