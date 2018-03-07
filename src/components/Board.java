package components;

import chess.*;

import java.util.Scanner;

/**
 * This class produces the board output and takes in the players' input for the game.
 * @author Jane Chang
 * @author Vlad Tsoy
 *
 */
public class Board {

	private Chess game;
	public boolean requestDraw = false;


	public Board(Chess game) { 
		this.game = game; 
	}
	
	public Chess getGame() {
		return game;
	}

	public void startGame() {

		showBoard(game);
		Scanner sc = new Scanner(System.in);

		while (true) {
			if(game.getCurrentPlayer().getColor() == Color.White){
				System.out.print("White's move: ");
				String input = sc.nextLine().trim();
				Input(input);
			} else {
				System.out.print("Black's move: ");			
				String input = sc.nextLine().trim();
				Input(input);
			}
			System.out.println();
			showBoard(game);
		}
	}
	
	
	public static void showBoard(Chess game) {

		Tile[][] board = game.getBoard();
		int k = 9;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = board[i][j].getPiece();
				if (piece == null) {
					if((i % 2) == (j % 2)) {
						System.out.print("   ");
						continue;					
					} else {
						System.out.print("## ");
						continue;
					}
				}
				if (piece.getPlayer().getColor() == Color.White) {
					String initials = "w";
					initials = initials + piece.getInitial();
					System.out.print(initials + " ");
				} else {
					String initials = "b";
					initials = initials + piece.getInitial();
					System.out.print(initials + " ");
				}
			}
			k--;
			System.out.println(k);
		}
		
		System.out.println(" a  b  c  d  e  f  g  h ");
		System.out.println();
	}

	/**
	 * This method processes what move the player wants to make.
	 * If the string input does not follow rules, prompts player to try again.
	 * @param userInput 	a string detailing start tile, end tile, and possible promotion, draw, or resign
	 */
	public void Input(String userInput) {
		String[] entries = userInput.split("\\s");
		if(entries.length == 1) {
			if(entries[0].toLowerCase().equals("resign")) {
				game.moves.add(userInput);
				game.endGame('r');
				return;
			}
			if(entries[0].toLowerCase().equals("draw")) {
				game.endGame('d');
				return;
			} else {
				System.out.println("Invalid input, try again\n");
				return;
			}
		}
		
		String source = entries[0];
		String dest = entries[1];
		String third = null;
		if(entries.length == 3) {
			third = entries[2];
		}
		
		if (source.length() != 2 || dest.length() != 2) {
			System.out.println("Invalid input, try again\n");
			return;
		}

		char file1 = entries[0].toLowerCase().charAt(0);
		int rank1 = Character.getNumericValue(entries[0].charAt(1));
		char file2 = entries[1].toLowerCase().charAt(0);
		int rank2 = Character.getNumericValue(entries[1].charAt(1));
		
		if ((file1 < 'a' || file1 > 'h') 
				|| (file2 < 'a' || file2 > 'h')
				|| (rank1 < 1 || rank1 > 8)
				|| (rank2 < 1 || rank2 > 8)) {
			
			System.out.println("Invalid Input, try again\n");
			return;
		}
		
		if (!game.move(source, dest)) {
			System.out.println("Illegal move, try again\n");
			return;
		}

		Tile[][] board = game.getBoard();
		Tile endLoc = board[8-rank2][game.fileToInt(file2)];
		
		// Promotion
		if(rank2 == 8 && endLoc.getPiece().getInitial().equals("p") ) {
			if(third.equals(null) || third.toLowerCase().equals("q")) {
				Piece queen = new Queen();
				endLoc.setPiece(queen);
			} else {
				Piece promo = endLoc.getPiece();
				if(third.toLowerCase().equals("r")) promo = new Rook();
				if(third.toLowerCase().equals("n")) promo = new Knight();
				if(third.toLowerCase().equals("b")) promo = new Bishop();
				endLoc.setPiece(promo);
			}
		}
		
		game.moves.add(userInput);
		
	}
}

