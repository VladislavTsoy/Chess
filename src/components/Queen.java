package components;

/**
 * This class represents the Queen pieces in the game.
 * @author Jane Chang
 * @author Vlad Tsoy
 *
 */
public class Queen extends Piece {

	public String getInitial() { 
		return "Q"; 
	}

	public boolean isLegal(Tile dest) {
		int currentX = getLocation().getX();
		int destinationX = dest.getX();
		int currentY = getLocation().getY();
		int destinationY = dest.getY();
		

		if ((currentX == destinationX) ||
			 currentY == destinationY ||
			 Math.abs(currentX - destinationX) == Math.abs(currentY - destinationY)) {
			return isClear(dest);
		}
		return false;
	}
}