public class Node {
	
	public int[][] goalState; //holds the goal state that has to be achieved
	
	
	int level; //Assume that the level works as g(n) for the heuristics
	int count;
	int blankPositionRow; //row position of the blank tile
	int blankPositionCol; //column position of the blank tile
	Node parent; //points to the parent of this node
	
	int[][] stateSpace; //state space
	int manhattanDist;
	//a constructor
	public Node(int[][] startState, Node goalState, Node parent) {
		
		this.stateSpace = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (startState[i][j] == 0) {
					this.blankPositionRow = i;
					this.blankPositionCol = j;
				}
				stateSpace[i][j] = startState[i][j];
			}
		}
		this.goalState = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.goalState[i][j] = goalState.stateSpace[i][j];
			}
		}
		if (parent != null) {
			setParent(parent);
			this.level = parent.level + 1;
		} else {
			this.parent = null;
			this.level = 0;
		}
		this.manhattanDist = calculateHeuristicManhattan();
		this.count=calculateHeuristicMisplaced();
	}
	
	//checks if goal state h
	public boolean isItGoal() {
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (stateSpace[i][j] != goalState[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	//another constructor
	public Node(int[][] startState) {
		this.stateSpace = new int[startState.length][startState.length];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				stateSpace[i][j] = startState[i][j];
			}
		}
	}
	
	//prints the state of the board
	public void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(stateSpace[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.println("\n");
	}
	
	//calculates the Manhattan Dist - h(n)
	public int calculateHeuristicManhattan() {
		this.manhattanDist = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int num = stateSpace[i][j];
				if (num != 0) {
					manhattanDist += searchForTheTile(num, i, j);
				}
			}
		}
		return manhattanDist;
	}
	
public int calculateHeuristicMisplaced() {
		
		this.count=0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				
				if ((stateSpace[i][j] != goalState[i][j])) {
					count++;
				}
			}
		}
		return count;
	}
	
	
	
	//the function f(n) = g(n) + h(n)
	public int function1() {
		return (level + manhattanDist); 
	}
	public int function2() {
		return (level + count); 
	}
	
	//calculates the Dist of each tile from its goal
	private int searchForTheTile(int num, int row, int col) {
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (goalState[i][j] == num) {
					return Math.abs(i - row) + Math.abs(j - col);
				}
			}
		}
		return 0;
	}
	
	//sets the parent of this node
	public void setParent(Node parent) {
        //parent.addChild(this);
        this.parent = parent;
    }
    
	//returns the row number of the blank tile
    public int getblankPositionRow() {
    	return blankPositionRow;
    }
    
    //returns the column number of the blank tile
    public int getblankPositionCol() {
    	return blankPositionCol;
    }
	
	//returns Manhattan value for this state
	public int getManhattan() {
		return this.manhattanDist;
	}
	
	}
