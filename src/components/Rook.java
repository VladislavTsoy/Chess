package components;

/**
 * This class represents the Rook pieces in the game.
 * @author Jane Chang
 * @author Vlad Tsoy
 *
 */
public class Rook extends Piece {

	public String getInitial() { 
		return "R"; 
	}

	public boolean isLegal(Tile dest) {
		int currentX = getLocation().getX();
		int destinationX = dest.getX();
		int currentY = getLocation().getY();
		int destinationY = dest.getY();

		if (currentX != destinationX && currentY != destinationY) {
			return false;
		}
		return isClear(dest);
	}
}
