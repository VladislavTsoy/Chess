package components;

/**
 * This class used for a tile's location and the piece that occupies it.
 * @author Jane Chang
 * @author Vlad Tsoy
 */
public class Tile {
	
	private Tile[][] board;
	private Piece piece;
	private int x;
	private int y;
	
	/**
	 * @return x	row, i.e. the current piece's rank
	 */
	public int getX() { 
		return x; 
	}
	
	/**
	 * @return y	column, i.e. the current piece's file
	 */
	public int getY() { 
		return y; 
	}
	
	/**
	 * Tile constructor
	 * @param x 	int representing column
	 * @param y 	int representing row
	 */
	public Tile(int y, int x) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for current piece
	 * @return	the piece on the current tile
	 */
	public Piece getPiece() { 
		return piece; 
	}
	
	/**
	 * Setter for current piece
	 * @param piece 	the piece to be placed on the current tile
	 */
	public void setPiece(Piece piece) { 
		this.piece = piece; 
	}
	
}