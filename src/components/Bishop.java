package components;

/**
 * This class represents the bishop piece.
 * @author Jane Chang
 * @author Vlad Tsoy
 */
public class Bishop extends Piece {
	
	public String getInitial() { 
		return "B"; 
	}
	
	public boolean isLegal(Tile dest) {
		
		int currentX = getLocation().getX();
		int destinationX = dest.getX();
		int currentY = getLocation().getY();
		int destinationY = dest.getY();
		
		int rank = Math.abs(destinationX- currentX);
		int file= Math.abs( destinationY - currentY);
		if(rank != file) { 
			return false;
		}
		return isClear(dest);
	}

}