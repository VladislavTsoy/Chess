package components;

/**
 * This class represents the Pawns in the game.
 * @author Jane Chang
 * @author Vlad Tsoy
 *
 */
public class Pawn extends Piece {

	public String getInitial() { 
		return "p";
	}

	public boolean isLegal(Tile dest) {
		int currentX = getLocation().getX();
		int destinationX = dest.getX();
		int currentY = getLocation().getY();
		int destinationY = dest.getY();
		int file;
		
		if(getPlayer().getColor() == Color.White) {
			file = 1;
			if (currentX == destinationX) {
				return (dest.getPiece() == null 
						&& currentY == destinationY + file)
						||
						(numberOfMoves() == 0 
						&& dest.getPiece() == null 
						&& currentY == destinationY + (file + 1));
			}
		} else {
			file = -1;
			if (currentX == destinationX) {
				return (dest.getPiece() == null 
						&& currentY == destinationY + file)
						||
						(numberOfMoves() == 0 
						&& dest.getPiece() == null 
						&& currentY == destinationY + (file - 1));
			}
		}
		if (Math.abs(currentX - destinationX) == 1 && currentY == destinationY + file) {
			if (dest.getPiece() != null) {
				return true;
			}
			
			Piece piece = getBoard()[getLocation().getY()][destinationX].getPiece();
			if (piece != null && piece instanceof Pawn && piece.numberOfMoves() == 1) {
				if (piece.getPlayer().getColor() == getPlayer().getColor()) {
					return false;
				} else {
					return ((getPlayer().getColor() == Color.White && dest.getY() == 2 || 
							getPlayer().getColor() == Color.Black && dest.getY() == 5));
				}
			}
		}
		return false;
	}
}