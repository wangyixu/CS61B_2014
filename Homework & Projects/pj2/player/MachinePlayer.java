/* MachinePlayer.java */

package player; 

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
	
  protected SimpleBoard board = SimpleBoard();
  protected int color;
  protected int searchDepth = Integer.MAX_VALUE;
	
  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
	  this.color = color;
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
	  this.color = color;
	  this.searchDepth = searchDepth;
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
	int alpha = evaluateFunction(color);   //TODO
	int beta = evaluateFunction(opponent(color));    //TODO
	Best best = chooseMove(color, alpha, beta, 0);
	return best.move;
  } 
  
	/**
	 *  Returns a best move and its evaluation score.
	 *  
	 */
    private Best chooseMove(int side, int alpha, int beta, int depth) {
		Best myBest = new Best();
		Best reply;
		
		if (depth >= searchDepth || hasValidNetwork(side) || hasValidNetwork(opponent(side)) { 
			//达到搜索深度、一方或另一方达成Network，停止Move、评估此时score，返回最好结果
			myBest.score = this.evaluateFunction(color);
			return myBest;
		}
		
		if (side == color) {
			myBest.socre = alpha;
		} else {
			myBest.score = beta;
		}
		
		Move[] moves = this.validMoves(side);
		for (int i = 0; i < moves.length(); i++) {
			Move move = moves[i];
			performMove(side, move);
			reply = chooseMove(opponent(side), alpha, beta, depth + 1);
			undoMove(side, move);
			
			if (side == color && reply.score > myBest.score) {
				myBest.move = move;
				myBest.score = reply.score;
				alpha = reply.score;
			} else if (side == opponent(color) && reply.score < myBest.score) {
				myBest.move = move;
				myBest.score = reply.score;
				beta = reply.score;
			}
			if (alpha >= beta) {
				return myBest;
			}
		}
		return myBest;
    }
	
	
	/**
	* hasValidNetwork() determines whether "this" GameBoard has a valid network
	* for player "side". (Does not check whether the opponent has a network.)
	* A full description of what constitutes a valid network appears in the
	* project "readme" file.
	*
	* Unusual conditions:
	* If side is neither MachinePlayer.COMPUTER nor MachinePlayer.OPPONENT,
	* returns false.
	* If GameBoard squares contain illegal values, the behavior of this
	* method is undefined (i.e., don’t expect any reasonable behavior).
	*
	* @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
	* @return true if player "side" has a winning network in "this" GameBoard;
	* false otherwise.
	**/
	protected boolean hasValidNetwork(int side) {
		int[][] chips = currentChips(side);
		int chipsNum = chips.length;
		if (chipsNum < 6) return false;
		
		//use connected slopes to construct chips' connections graph.
		int[][] chipsConnections = new int[chipsNum][chipsNum];
		for (int i = 0; i < chipsNum; i++) {
			for (int j = 0; j < chipsNum; j++) {
				chipsConnections[i][j] = connectedSlope(side, chips[i], chips[j]);
			}
		}
		//probably do BFS to find Network.
		//a network can only have 2 chips in the goal areas(first and last), any chip can't use more than twice, can't pass through without corner(slope change).
		
		//user gaol areas' chips' chips to do 4 or more chips(at most chipsNum - 2) connections.any pair successed, networksed!
		boolean result = false;
		for (int i = 0; i < chipsNum; i++) {
			if ((side == 0 && chip[i][1] == 0) ||
				(side == 1 && chips[i][0] == 0)) {
				//从黑方上 goal area，或 白方下 goal area的chip开始
				int[] pathchips = new int[chipsNum];
				result = result || findNetwork(chips, chipsConnections, i, pathchips, 0);
			}
		}
		return result;
	}
	private boolean findNetwork(int[][] chips, int[][] chipsConnections, int chipIndex, int[] pathchips, int count) {
		boolean result = false;
		for (int i = 0; i < chips.length && i != chipIndex; i++) {
			if (chipsConnections[chipIndex][i] != 0 &&
				chipsConnections[chipIndex][i] != chipsConnections[chipIndex][pathchips[count - 2]]) {
				//has connection && slopes are not eaual && no repeation
				for (int j = 0; j < count; j++) {
					if (i == pathchips[j]) {
						continue;
					}
				}				
				//do search
				if ((side == 0 && chip[i][1] == 0) ||
					(side == 1 && chips[i][0] == 0)) {
					if (count >= 6) {
						result = true;
					}
				} else {
					result = result || findNetwork(chips, chipsConnections, i, pathchips, count + 1);
				}
			}
		}
		return result;
	}
	//calculate 2 chips' slope, +1 -1  +2(x1 == x2) -2(y1 == y2)  0(not connected).
	private int connectedSlope(int side, int[] chip1, int[] chip2) {
		int x1 = chip1[0],
			y1 = chip1[1],
			x2 = chip2[0],
			y2 = chip2[1];		
		
		if (x1 == x2) return 2;
		if (y1 == y2) return -2;
		
		for (int i = -6; i <= 6 && i != 0; i++) {
			if (isValidPlace(side, x1 + i, y1 + i) &&
				x1 + i == x2 && y1 + i == y2) {
					return 1;
			}
		}
		for (int i = -6; i <= 6 && i != 0; i++) {
			if (isValidPlace(side, x1 + i, y1 - i) &&
				x1 + i == x2 && y1 - i == y2) {
					return -1;
			}
		}
		return 0;
	}
	
	//return the opponentside's color number.
	private int opponent(int side) {
		return (side + 1 ) % 2;
	}
	
	/**
	 *  Computing an evaluation function for this side's chips.
	 *  Some evaluations: in center places、make more chips'connections、make a network.
	 */
	protected int evaluateFunction(int side) {
		int score = 0;
		if (hasValidNetwork(color)) {
			score = 100;
		if (hasValidNetwork(opponent(color)) {
			score = -100;
		}
		return score;
		
	}
	
	/**
	 *  Generate and return a list of all valid moves.
	 *  
	 */
	protected Move[] validMoves(int side) {
		//collect side's chips' count, x and y.
		int[][] chips = currentChips(side);
		
		//construct possible moves.
		Move[] validMoves = new Move[48];   //at most 48 valid places
		int moveCount = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isValidPlace(side, i, j)) {
					if (chips.length == 10) {
						//step moves
						for (int k = 0; k < 10; k++) {
							Move stepMove = new Move(i, j, chips[k][0], chips[k][1]);
							if (isValidMove(side, stepMove)) {
								validMoves[moveCount ++] = stepMove;
							}
						}
					} else {
						//add moves
						Move addMove = new Move(i, j);
						if (isValidMove(side, addMove)) {
							validMoves[moveCount ++] = addMove;
						}
					}
				}
			}
		}
		return ((Move[]) resizeArray(validMoves, moveCount));
	}
	
	private int[][] currentChips(int side) {
		int[][] chips = new int[10][10];
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isValidPlace(side, i, j) && board.elementAt(i, j) == side + 1) {
					//count chips on side's 6 x 8 grids.
					chips[count][0] = i;
					chips[count][1] = j;
					count++;
				}
			}
		}
		return ((int[][]) resizeArray(chips, count));
	}
	
	/**
	* Reallocates an array with a new size, and copies the contents
	* of the old array to the new array.
	* @param oldArray  the old array, to be reallocated.
	* @param newSize   the new array size.
	* @return          A new array with the same contents.
	*/
	private Object resizeArray (Object oldArray, int newSize) {
	    int oldSize = java.lang.reflect.Array.getLength(oldArray);
	    Class elementType = oldArray.getClass().getComponentType();
	    Object newArray = java.lang.reflect.Array.newInstance(
			 elementType, newSize);
		System.arraycopy (oldArray, 0, newArray, 0, newSize);
	    return newArray;
	}

	private boolean isValidMove(int side, Move move) {
		int x1 = move.x1,
			y1 = move.y1;
		
		if (!isValidPlace(side, x1, y1)) return false;
		
		if (board.elementAt(x1, y1) != 0)	return false;
		
		//check clusters, use a 2 depth surround chips check,if any chip has >= 2 surround chips,return false.
		boolean isValid = true;
		performMove(move);
		int[][] chips = surroundChips(side, x1, y1);
		int count = chips.length;
		if (count >= 2) {
			isValid =  false;
		} else if (count == 1) {
			int[][] surrChips = surroundChips(side, chips[0][0], chips[0][1]);
			if (surrChips.length >= 2) {
				isValid =  false;
			}
		}
		undoMove(move);
		return isValid;			
	}
	
	//return if this place for side's chip could place in
	private boolean isValidPlace(int side, int x, int y) {
		if (x < 0 || x > 7 || y < 0 || y > 7) return false;
		
		if (side == 0) {
			//0(black) can't place chips on these places
			if (x == 0 || x == 7) {
				return false;
			}
		} else {
			if (y == 0 || y == 7) {
				return false;
			}
		}
	}
	
	//check valid place in 8 surround grid and return surround chips
	private int[][] surroundChips(int side, int x, int y) {
		int[][] chips = new int[8][2];
		int value = side + 1;
		int count = 0;
		
		int x1 = (x - 1 >= 0) ? x - 1 : 0,
			y1 = (y - 1 >= 0) ? y - 1 : 0,
			x2 = (x + 1 <= 7) ? x + 1 : 7,
			y2 = (y + 1 <= 7) ? y + 1 : 7;
		
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (isValidPlace(side, i, j) &&
					(i != x || j != y) &&
					board.elementAt(i, j) == value) {
						chips[count][0] = i;
						chips[count][1] = j;
						count++;
					}
			}
		}
		return ((int[][]) resizeArray(chips, count));
	}
	
	//perform move on this board
	//0 为空，数字为 side + 1。1 (black) or 2 (white)
	private void performMove(int side, Move move) {
		if (move.moveKind != 0) {
			//not QUIT
			//addMove or stepMove's new location
			board.setElementAt(move.x1, move.y1, side + 1);
			//remove stepMove's old location or set (0, 0) 0,which have no influence
			board.setElementAt(move.x2, move.y2, 0);     
		}
    }
	
	//undo the move.
	private void undoMove(int side, Move move) {
		if (move.moveKind != 0) {
			board.setElementAt(move.x1, move.y1, 0);
			if (move.moveKind == 2) {
				board.setElementAt(move.x2, move.y2, side + 1);
			}
		}
	}

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move move) {
	if (isValidMove(color, move)) {
		performMove(move);
		return true;
	} else {
		return false;
	}
  }
	
  
  
  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move move) {
	int thisColor = (color + 1) / 2;
	if (isValidMove(thisColor, move)) {
		performMove(move);
		return true;
	} else {
		return false;
	}
  }

}
