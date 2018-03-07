package chess;

import java.util.ArrayList;

import components.*;

/**
 * This class is used to launch the program.
 * @author Jane Chang
 * @author Vlad Tsoy
 */
public class Chess {

	private Tile[][] board;
	private Player Black;
	private Player White;
	private Player turn;
	public ArrayList<String> moves = new ArrayList<String>();

	public Chess() {
		board = new Tile[8][8];
		Black = new Player(Color.Black);
		White = new Player(Color.White);
		Black.setOpponent(White);
		White.setOpponent(Black);
		turn = White;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				board[i][j] = new Tile(i, j);
			}
		}
		setPieces(White);
		setPieces(Black);
	}
	
	private void setPieces(Player player) {
		
		Piece[] pieces = new Piece[]
				{ new Rook(), new Knight(), new Bishop(), new Queen(), 
				  new King(), new Bishop(), new Knight(), new Rook()};
		
		int row;
		if(player.getColor() == Color.White) {
			row = 7;
		} else {
			row = 0;
		}

		player.setKing((King)pieces[4]);
		player.setQueen((Queen)pieces[3]);

		for (int i = 7; i >= 0; i--) {
			pieces[i].setLocation(board[row][i]);
			pieces[i].setBoard(board);
			board[row][i].setPiece(pieces[i]);
			board[row][i].getPiece().setPlayer(player);
			player.addPiece(pieces[i]);

		}
		if(player.getColor() == Color.White){
			row = 6;
		} else {
			row = 1;
		}

		for (int i = 7; i >= 0; i--) {
			Piece pawn = new Pawn();
			pawn = new Pawn();
			pawn.setLocation(board[row][i]);
			pawn.setBoard(board);
			board[row][i].setPiece(pawn);
			board[row][i].getPiece().setPlayer(player);
			player.addPiece(pawn);
		}  

	}

	public Tile[][] getBoard() { 
		return board; 
	}

	public Player getCurrentPlayer() { 
		return turn; 
	} 
	
	public int fileToInt(char file) {

		switch (file) {
			case 'a':
				return 0;
			case 'b':
				return 1;
			case 'c':
				return 2;
			case 'd':
				return 3;
			case 'e':
				return 4;
			case 'f':
				return 5;
			case 'g':
				return 6;
			case 'h':
				return 7;
			default:
				return -1;
		}
	}

	public boolean move(String s, String d) {
		
		int f = fileToInt(s.charAt(0));
		int r = 8 - Character.getNumericValue(s.charAt(1));
		int direction;
		Tile currPosition = board[r][f];

		f = fileToInt(d.charAt(0));
		r = 8 - Character.getNumericValue(d.charAt(1));

		Tile destPosition = board[r][f];
		Piece selectedPiece = currPosition.getPiece();
		Piece destPiece = destPosition.getPiece();
		
		if ((selectedPiece == null) ||
			(selectedPiece.getPlayer().getColor() != getCurrentPlayer().getColor()) ||
			(!selectedPiece.canMove(destPosition))) {
			return false;
		}
		
		if (destPiece != null) {
			destPiece.setLocation(null);
		}
		
		// Enpassant
		if(selectedPiece instanceof Pawn) {
			int currentX = selectedPiece.getLocation().getX();
			int destinationX = destPosition.getX();
			int currentY = selectedPiece.getLocation().getY();
		
			if(selectedPiece.getPlayer().getColor() == Color.White){
				direction = 1;
				
				if((Math.abs(currentX - destinationX)) == 1) {
					if(currentY == destinationX + direction) {
						board[currentY][destinationX].setPiece(null);
					}
				}
			} else {
				direction = -1;
				if((Math.abs(currentX - destinationX)) == 1) {
					if(currentY == destinationX + direction) {
						board[currentY][destinationX].setPiece(null);		
					}
				}
			}
		}
		
		currPosition.setPiece(null);
		selectedPiece.setLocation(destPosition);
		destPosition.setPiece(selectedPiece);
		selectedPiece.incrementMoves();
		
		// Castling
		if((selectedPiece instanceof King) && (destPosition.getX() - currPosition.getX() == 2)) {
			int dir = destPosition.getX() - currPosition.getX();
			if(dir < 0) {
				Piece castleRook = board[0][destPosition.getY()].getPiece();
				board[0][destPosition.getY()].setPiece(null);
				castleRook.setLocation(board[3][destPosition.getY()]);
				board[3][destPosition.getY()].setPiece(castleRook);
				castleRook.incrementMoves();
			} else {
				Piece castleRook = board[7][destPosition.getY()].getPiece();
				board[7][destPosition.getY()].setPiece(null);
				castleRook.setLocation(board[5][destPosition.getY()]);
				board[5][destPosition.getY()].setPiece(castleRook);
				castleRook.incrementMoves();
			}
		}

		King enemyKing = turn.getOpponent().getKing();
		if(check(enemyKing.getLocation()) && !canRun(enemyKing.getLocation())) {
			System.out.println("Checkmate");
			endGame('c');
		}
		if(check(enemyKing.getLocation())) {
			System.out.println("Check");
		}

		if (turn == White) {
			turn = Black;
		} else {
			turn = White;
		}

		return true;
	}
	
	/**
	 * This method checks if a move put the opponent's king in check.
	 * If so, prints "check"
	 * @param king	the opponent's king
	 * @return 		true if in check, else false
	 */
	public boolean check(Tile tile) {
		for(Piece current : getCurrentPlayer().getPieces()) {
			if(current.isLegal(tile)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkmate(Tile tile) {
		int tileX = tile.getX();
		int tileY = tile.getY();
		for(Piece current : getCurrentPlayer().getOpponent().getPieces()) {
			if(current.isLegal(tile)) {
				if(tile.getPiece().getInitial().equals("N")) {
					if(canRun(tile)) { return false; }
				} else {
					int atkrX = current.getLocation().getX();
					int atkrY = current.getLocation().getY();
					int relativeX = atkrX - tileX;
					int relativeY = atkrY - tileY;


						if(relativeX == 0) {
							if(relativeY > 0) {
								if(tileY+1 < 8) {
									if(cantDefend(board[tileX][tileY+1]) && !canRun(tile)) {
									continue;
									} else { return false; }
								}
							} else {
								if(tileY-1 >= 0) {
									if(cantDefend(board[tileX][tileY-1]) && !canRun(tile)) {
										continue;
									} else { return false; }
								}
							}
						}
						if(relativeY == 0) {
							if(relativeX > 0) {
								if(tileX+1 < 8) {
									if(cantDefend(board[tileX+1][tileY]) && !canRun(tile)) {
										continue;
									} else { return true; }
								}
							} else {
								if(tileX-1 >= 0) {
									if(cantDefend(board[tileX-1][tileY]) && !canRun(tile)) {
										continue;
									} else { return true; }
								}
							}
						}
						if(relativeX < 0  && relativeY < 0) {
							if(tileX-1 >= 0 && tileY-1 >= 0) {
								if(cantDefend(board[tileX-1][tileY-1]) && !canRun(tile)) {
									continue;
								} else { return true; }
							}
						}
						if(relativeX < 0 && relativeY > 0) {
							if(tileX-1 >= 0 && tileY+1 < 8) {
								if(cantDefend(board[tileX-1][tileY+1]) && !canRun(tile)) {
									continue;
								} else { return true; }
							}
						}
						if(relativeX > 0 && relativeY < 0) {
							if(tileX+1 < 8 && tileY-1 >= 0) {
								if(cantDefend(board[tileX+1][tileY-1]) && !canRun(tile)) {
									continue;
								} else { return true; }
							}
						}
						if(relativeX > 0 && relativeY > 0) {
							if(tileX+1 < 8 && tileY+1 >= 0) {
								if(cantDefend(board[tileX+1][tileY+1]) && !canRun(tile)) {
									continue;
								} else { return true; }
							}
						}
						
				}
			
				}
			}
		return false;
	}
	
	
	public boolean canRun(Tile tile) {
		int xk = tile.getX();
		int yk = tile.getY();
		for(int i = xk-1; i <= xk+1; i++) {
			for(int j = yk-1; j <= yk+1; j++) {
				if(i < 0 || i > 7 || j < 0 || j > 7) {
					continue;
				}
				if(tile.getPiece().isLegal(board[i][j]) && !check(board[i][j])) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean stalemate(Tile tile) {
		int xk = tile.getX();
		int yk = tile.getY();
		for(int i = xk-1; i <= xk+1; i++) {
			for(int j = yk-1; j <= yk+1; j++) {
				if(i < 0 || i > 7 || j < 0 || j > 7) {
					continue;
				}
				if(!canRun(tile) && check(board[i][j]) && cantDefend(board[i][j])) {
					return true;
				}
			}
		}
		return false;
	}

	
	public boolean cantDefend(Tile tile) {
		
		for(Piece current : getCurrentPlayer().getOpponent().getPieces()) {
			if(check(tile) && current.isLegal(tile)) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * This method checks if a move put the opponent's king in check.
	 * If so, prints "check"
	 * @param king	the opponent's king
	 * @return 		true if in check, else false
	 */
	public boolean check(King king) {
		int xCoo = king.getLocation().getX();
		int yCoo = king.getLocation().getY();
		
	
		
		if(king.underAttack(king.getLocation())) {
			if(xCoo-1 >= 0) {
				if(king.canBeDefended(board[xCoo-1][yCoo])) return false;
			}
			if(xCoo-1 >= 0 && yCoo-1 >= 0) {
				if(king.canBeDefended(board[xCoo-1][yCoo-1])) return false;
			}
			if(xCoo-1 >= 0 && yCoo+1 < 8) {
				if(king.canBeDefended(board[xCoo-1][yCoo+1])) return false;
			}
			if(yCoo-1 >= 0) {
				if(king.canBeDefended(board[xCoo][yCoo-1])) return false;
			}
			if(yCoo+1 < 8) {
				if(king.canBeDefended(board[xCoo][yCoo+1])) return false;
			}
			if(xCoo+1 < 8) {
				if(king.canBeDefended(board[xCoo+1][yCoo])) return false;
			}
			if(xCoo+1 < 8 && yCoo-1 >= 0) {
				if(king.canBeDefended(board[xCoo+1][yCoo-1])) return false;
			}
			if(xCoo+1 < 8 && yCoo+1 < 8) {
				if(king.canBeDefended(board[xCoo+1][yCoo+1])) return false;
			}
		}
		return false;
	}
	
	/**
	 * This method checks if opponent's king is in stalemate.
	 * @param king	the opponent's king
	 * @return		true if in stalemate, else false
	 */
	public boolean stalemate(King king) {
		int xCoo = king.getLocation().getX();
		int yCoo = king.getLocation().getY();
		
		if(xCoo-1 >= 0) {
			if(!king.underAttack(board[xCoo-1][yCoo])) return false;
		}
		if(xCoo-1 >= 0 && yCoo-1 >= 0) {
			if(!king.underAttack(board[xCoo-1][yCoo-1])) return false;
		}
		if(xCoo-1 >= 0 && yCoo+1 < 8) {
			if(!king.underAttack(board[xCoo-1][yCoo+1])) return false;
		}
		if(yCoo-1 >= 0) {
			if(!king.underAttack(board[xCoo][yCoo-1])) return false;
		}
		if(yCoo+1 < 8) {
			if(!king.underAttack(board[xCoo][yCoo+1])) return false;
		}
		if(xCoo+1 < 8) {
			if(!king.underAttack(board[xCoo+1][yCoo])) return false;
		}
		if(xCoo+1 < 8 && yCoo-1 >= 0) {
			if(!king.underAttack(board[xCoo+1][yCoo-1])) return false;
		}
		if(xCoo+1 < 8 && yCoo+1 < 8) {
			if(!king.underAttack(board[xCoo+1][yCoo+1])) return false;
		}
		
		return true;
	}
	
	/**
	 * This method prints the result of the game.
	 * If the game has ended in a resign or draw, a list of the game's moves will be printed before the result.
	 * @param end	'c' for checkmate, 's' for stalemate, 'r' for resign, 'd' for draw
	 */
	public void endGame(char end) {
		if(end == 'r') {
			for(String current : moves) {
				System.out.println(current);
			}
			if(getCurrentPlayer().getColor() == Color.White) {
				System.out.println("Black wins");
			} else {
				System.out.println("White wins");
			}
		}
		
		if(end == 'd') {
			for(String current : moves) {
				System.out.println(current);
			}
			System.out.println("draw");
		}
		
		if(end == 'c' || end == 's') {
			if(turn == White) {
				System.out.println("White wins");
			} else {
				System.out.println("Black wins");
			}
		}

		System.exit(0);
	}

	public static void main(String[] args) {
		Chess game = new Chess();
		Board gameDisplay = new Board(game);
		gameDisplay.startGame();
	}
	
}
