import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class EightPuzzle {
	
	static Node startState;
	static Node finalState;
	static int nodesGenerated = 0;
	static int nodesOpened = 1;
	static int choice;
	static PriorityQueue<Node> choiceOfNodes;
	static ArrayList<Node> nodesCovered;
	
	//A Comparator for ordering the priority queue
	public static Comparator<Node> manhattanDistance = new Comparator<Node>() {

		@Override
		public int compare(Node obj1, Node obj2) {
			if (obj1.function1()> obj2.function1())
				return 1;
			else if (obj1.function1()< obj2.function1())
				return -1;
			else
				return 0;
		}
		
	};
	
	public static Comparator<Node> heuristicOne = new Comparator<Node>() {

		@Override
		public int compare(Node o1, Node o2) {
			if (o1.function2()> o2.function2())
				return 1;
			else if (o1.function2()< o2.function2())
				return -1;
			else
				return 0;
		}
		
	};
	
	//Execution starts
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Select either of the heuristics: \n");
		System.out.println("1. Total number of misplaced tiles(Press 1)");
		System.out.println("2. Manhattan Distance(Press 2)");
		choice = scanner.nextInt();
		if(choice==1)
			choiceOfNodes = new PriorityQueue<>(heuristicOne);
		else
			choiceOfNodes = new PriorityQueue<>(manhattanDistance);
		nodesCovered = new ArrayList<Node>();
		int[][] start = new int[3][3];
		int[][] last = new int[3][3];
		System.out.println("Enter the start state: \n"); //Accept start and goal state 
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				start[i][j] = scanner.nextInt();
			}
		}
		
		System.out.println("Enter the goal state: \n");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				last[i][j] = scanner.nextInt();
			}
		}
		scanner.close();
		finalState = new Node(last);
		startState = new Node(start, finalState, null);
		choiceOfNodes.add(startState); //Add start state/node into choiceOfNodes
		check(); // checking for further expansions
		

	}
	
	//Checks which nodes to be selected for expansion
	public static void check() {
		while (!choiceOfNodes.isEmpty()) {
			Node node = choiceOfNodes.remove();
			if (node.isItGoal()) {
				System.out.println("\nSolution: \n");
				printTheBoards(node);
				System.out.println("No. of nodes generated in total: " + nodesGenerated);
				System.out.println("No. of nodes expanded: " + nodesOpened);
				return;
			} else {
				if (isItVisted(node)) {
					continue;
				} else {
					nodesOpened++;
					nodesCovered.add(node);
					startOperation(node);
				}
			}
		}
	}
	
	//Checks if state is already present in the nodesCovered
	private static boolean isItVisted(Node node) {
		
		boolean areNodesEqual = false;
		for (Node temp : nodesCovered) {
			if(choice==1){
				if (node.count == temp.count) 				{
					if (areTheyEqual(temp.stateSpace, node.stateSpace)) {
						return true;
					}
				
			    }
			}
				else{
					
					if (node.manhattanDist == temp.manhattanDist) {
						if (areTheyEqual(temp.stateSpace, node.stateSpace)) {
					return true;
						}
					}
				}
		}
		return areNodesEqual;
	}
	
	//Compares if they are same or not
	private static boolean areTheyEqual(int[][] tempArray, int[][] array2) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tempArray[i][j] != array2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	//prints the sequence of states 
	private static void printTheBoards(Node node) {
		
		ArrayList<Node> finalList = new ArrayList<>();
		do {
			finalList.add(node);
			node = node.parent;
		} while (node.parent != null);
		node.printBoard();
		Collections.reverse(finalList);
		for (Node temp : finalList) {
			temp.printBoard();
		}
		System.out.println("Path cost: " + finalList.size());
	}

	//determines the next possible states achievable from this node
	public static void startOperation(Node presesntNode) {
		if (presesntNode != null) {
			goUp(presesntNode);
			goDown(presesntNode);
			goLeft(presesntNode);
			goRight(presesntNode);
		}
	}
	
	//Moves the blank tile up
	public static void goUp(Node parent) {
		int[][] tempArray = new int[3][3];
		
		int blankPositionRow = parent.getblankPositionRow();
		int blankPositionCol = parent.getblankPositionCol();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tempArray[i][j] = parent.stateSpace[i][j];
			}
		}
		
		if (blankPositionRow != 0) {
			int temp = tempArray[blankPositionRow - 1][blankPositionCol];
			tempArray[blankPositionRow - 1][blankPositionCol] = 0;
			tempArray[blankPositionRow][blankPositionCol] = temp;
			Node node = new Node(tempArray, finalState, parent);
			choiceOfNodes.add(node);
			nodesGenerated++;
			
		}
	}
	
	//Moves the blank tile down
	public static void goDown(Node parent) {
		int[][] tempArray = new int[3][3];
		
		int blankPositionRow = parent.getblankPositionRow();
		int blankPositionCol = parent.getblankPositionCol();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tempArray[i][j] = parent.stateSpace[i][j];
			}
		}
		if (blankPositionRow != 2) {
			int temp = tempArray[blankPositionRow + 1][blankPositionCol];
			tempArray[blankPositionRow + 1][blankPositionCol] = 0;
			tempArray[blankPositionRow][blankPositionCol] = temp;
			Node node = new Node(tempArray, finalState, parent);
			choiceOfNodes.add(node);
			nodesGenerated++;
		}
	}
	
	//gos the blank tile left
	public static void goLeft(Node parent) {
		int[][] tempArray = new int[3][3];
		
		int blankPositionRow = parent.getblankPositionRow();
		int blankPositionCol = parent.getblankPositionCol();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tempArray[i][j] = parent.stateSpace[i][j];
			}
		}
		if (blankPositionCol != 0) {
			int temp = tempArray[blankPositionRow][blankPositionCol - 1];
			tempArray[blankPositionRow][blankPositionCol - 1] = 0;
			tempArray[blankPositionRow][blankPositionCol] = temp;
			Node node = new Node(tempArray, finalState, parent);
			choiceOfNodes.add(node);
			nodesGenerated++;
		}
	}
	
	//Moves the blank tile right
	public static void goRight(Node parent) {
		int[][] tempArray = new int[3][3];
		
		int blankPositionRow = parent.getblankPositionRow();
		int blankPositionCol = parent.getblankPositionCol();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tempArray[i][j] = parent.stateSpace[i][j];
			}
		}
		if (blankPositionCol != 2) {
			int temp = tempArray[blankPositionRow][blankPositionCol + 1];
			tempArray[blankPositionRow][blankPositionCol + 1] = 0;
			tempArray[blankPositionRow][blankPositionCol] = temp;
			Node node = new Node(tempArray, finalState, parent);
			choiceOfNodes.add(node);
			nodesGenerated++;
		}
	}
}
