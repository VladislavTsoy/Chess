package components;

/**
 * This class represents the Knight in the game.
 * @author Jane Chang
 * @author Vlad Tsoy
 *
 */
public class Knight extends Piece {

	public String getInitial() { 
		return "N"; 
	}
	
	public boolean isLegal(Tile dest) {
		int currentX = getLocation().getX();
		int destinationX = dest.getX();
		int currentY = getLocation().getY();
		int destinationY = dest.getY();
		
		int rank = Math.abs(destinationX - currentX);
		int files= Math.abs(destinationY - currentY);
		
		if ((rank == 2 && files == 1) || (rank == 1 && files == 2)) {
			return true;
		}
		return false;
	}

}