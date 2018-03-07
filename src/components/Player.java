package components;

import java.util.ArrayList;

/**
 * This class represents the Players in the game.
 * Contains getters and setters for players, colors, opponents, and all pieces.
 * Lists all of a player's pieces currently on the board. 
 * @author Jane Chang
 * @author Vlad Tsoy
 *
 */
public class Player {
	
	private King king;
	private Queen queen;
	private Bishop bishop;
	private Knight knight;
	private Rook rook;
	private Pawn pawn;
	private ArrayList<Piece> pieces;
	private Player opponent;

	private Color color;
	public Player(Color color) {
		this.color = color;	
		pieces = new ArrayList<Piece>(16);
	}
	
	public ArrayList<Piece> getPieces() { 
		return pieces; 
	}

	public Color getColor() { 
		return color; 
	}

	public Player getOpponent() { 
		return opponent; 
	}

	public void addPiece(Piece piece) { 
		pieces.add(piece); 
	} 

	public void setOpponent(Player opponent) { 
		this.opponent = opponent;
	}
	public void setKing(King king) { 
		this.king = king; 
	}
	public void setQueen(Queen queen){
		this.queen = queen;
	}
	public void setBishop(Bishop bishop){
		this.bishop = bishop;
	}
	public void setKnight(Knight knight){
		this.knight = knight;
	}
	public void setRook(Rook rook){
		this.rook = rook;
	}
	public void setPawn(Pawn pawn){
		this.pawn = pawn;
	}
	public King getKing() { 
		return king; 
	}
	public Queen getQueen() { 
		return queen; 
	}
	public Rook getRook() { 
		return rook; 
	}
	public Knight getKnight() { 
		return knight; 
	}
	public Bishop getBishop() { 
		return bishop; 
	}
	public Pawn getPawn(){
		return pawn;
	}
}