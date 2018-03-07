package components;

/**
 * This class is the superclass of all pieces in the game.
 * @author Jane Chang
 * @author Vlad Tsoy
 *
 */
public abstract class Piece {

	private Tile[][] board;
	private Tile location;
	private Player player;
	private int moves;
	public abstract String getInitial();
	
	public abstract boolean isLegal(Tile dest);
	
	/**
	 * This method checks whether a piece's move is legal.
	 * @param dest	the tile on which the piece is to land
	 * @return		true if the move is valid, else false
	 */
	public boolean canMove(Tile dest) {
		
		if (dest.getPiece() != null) {
			if (player.getColor() == dest.getPiece().getPlayer().getColor()) {
				return false;
			}
		}
		
		if (location.equals(dest) || !isLegal(dest)) {
			return false;
		}
		
		Tile kingPos;
		if (this instanceof King) {
			kingPos = dest;
		} else {
			kingPos = getPlayer().getKing().getLocation();
		}
		return true;
	}

	/**
	 * This method checks if the path to the destination is clear.
	 * @param dest	the tile on which the piece is to land
	 * @return		true if the path is clear, else false
	 */
	public boolean isClear(Tile dest) {
		int currentX = location.getX();
		int destinationX = dest.getX();
		int currentY = location.getY();
		int destinationY = dest.getY();
		
		if (currentX == destinationX && currentY != destinationY) {
			int start = Math.min(currentY, destinationY) + 1;
			int destination = Math.max(currentY, destinationY) - 1;

			while(start <= destination) {
				if (board[start++][currentX].getPiece() != null) {
					return false;
				}
			}
			return true;
		} else if (currentX != destinationX && currentY == destinationY) {
			int start = Math.min(currentX, destinationX) + 1;
			int destination = Math.max(currentX, destinationX) - 1;

			while (start <= destination) {
				if (board[currentY][start++].getPiece() != null) {
					return false;
				}
			}
			return true;
		} else if (Math.abs(currentX - destinationX) == Math.abs(currentY - destinationY)) {
			int destRank;
			int destFile;
			if(currentX < destinationX) {
				destRank = 1;
			} else {
				destRank = -1;
			}
			if(currentY < destinationY) {
				destFile = 1;
			} else {
				destFile = -1;
			}
			int startRank  = currentX + destRank;
			int startFile = currentY + destFile;

			while ((destRank == 1 && startRank < destinationX) || 
				   (destRank == -1 && startRank > destinationX)) {
				
				if (board[startFile][startRank].getPiece() != null) {
					return false;
				}
				startRank += destRank;
				startFile += destFile;
			}
			return true;
		}

		return false;
	}
	
	public Player getPlayer() { 
		return player; 
	}

	public void setPlayer(Player player) { 
		this.player = player; 
	}


	public Tile getLocation() { 
		return location; 
	}

	public void setLocation(Tile location) { 
		this.location = location; 
	}
	
	public void setBoard(Tile[][] board) { 
		this.board = board; 
	}
	
	public Tile[][] getBoard() { 
		return board; 
	}
	
	public int numberOfMoves() { 
		return moves; 
	}
	
	public void setNumberOfMoves(int moves) { 
		this.moves = moves; 
	}

	public void incrementMoves() { 
		moves++; 
	}

}
