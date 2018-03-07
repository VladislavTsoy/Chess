package components;

/**
 * This class defines the moves a King is allowed to make, and whether it is under attack.
 * @author Jane Chang
 * @author Vlad Tsoy
 * 
 */
public class King extends Piece {


	public String getInitial() { 
		return "K"; 
	}
	
	/**
	 * This method checks to see if a given tile is under attack.
	 * @param tile 	the tile in question
	 * @return		true if under attack, else false (i.e. false if path is clear!)
	 */
	public boolean underAttack(Tile tile) {
		for(Piece current : getPlayer().getOpponent().getPieces()) {
			if(current.isLegal(tile)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canBeDefended(Tile tile) {
		for(Piece current : getPlayer().getPieces()) {
			if(current.isLegal(tile)) {
				return true;
			}
		}
		return false;
	}

	public boolean isLegal(Tile dest) {
		
		int currentX = getLocation().getX();
		int destinationX = dest.getX();
		int currentY = getLocation().getY();
		int destinationY = dest.getY();
		
		int rank = Math.abs(destinationX - currentX);
		int file = Math.abs(destinationY - currentY);

		if (((rank) <=1 && (file) <=1)) {
			return true;
		}

		// Castling
		if (rank == 2 && numberOfMoves() == 0 && destinationY == currentY) {
			Tile[][] board = getBoard();
			Piece castleRook;
			int direction = destinationX = currentX;
			if(direction < 0) {
				castleRook = board[0][destinationY].getPiece();
				if(castleRook == null 
						|| castleRook.numberOfMoves() > 0
						|| !isClear(dest)
						|| underAttack(board[3][destinationY])
						|| underAttack(board[2][destinationY])) {
					return false;
				}
			} else {
				castleRook = board[7][destinationY].getPiece();
				if(castleRook == null
						|| castleRook.numberOfMoves() > 0
						|| !isClear(dest)
						|| underAttack(board[5][destinationY])
						|| underAttack(board[6][destinationY])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}