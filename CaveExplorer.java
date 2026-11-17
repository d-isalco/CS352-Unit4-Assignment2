import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

//CS352 Unit 4 - Assingment 2 
//Sir, I had issues login into myPark Account. I was not able to complete the assignement on time
//I sent you a message in the assignment portal and cc'd you on an emial to IT on Sunday. 
//I hope you are able to let me have some flecibility. I was TDY all week last week and had to use my Starklink to get some of it done.
//I thinkg the Starklink messed wiht my location settings to login. I'm still having issues, but I'll try to fix it for this week. 
//I'm home in Florida, but I'm working with StarkLink to figure out why this is happening. 
//test 3
public class CaveExplorer {
	// Step 1: Zero parameter constructor to create the specified cave
	private char [][] cave;

	private String path = ""; // This will hold the path taken to reach the end position

	private static class CaveStep{
		int row;
		int col;
		String path;

		CaveStep(int row, int col, String path){
			this.row = row;
			this.col = col;
			this.path = path;
		}
	}
	// The cave layout is represented as a 2D array of characters, where:
	// 'R' represents a rock (an impassable cell)
	// 'S' represents the start position
	// 'M' represents the end position
	public CaveExplorer() {
		cave = new char [][] {
			"RRRRRR".toCharArray(),
			"R..S.R".toCharArray(),
			"R.R.RR".toCharArray(),
			"R.MRRR".toCharArray(),
			"RRRRRR".toCharArray()
		};
	}
	// Step 2: toString method
	// This method should return a string representation of the cave layout.
	@Override 
	public String toString() {
		StringBuilder layout = new StringBuilder();
		// Loop through each row of the cave and append it to the layout string
		for (char[] row : cave) {
			layout.append(row);
			layout.append("\n");
		}
		// Remove the last newline character for cleaner output
		return layout.toString();
	}
	// Step 3: solve method
	public boolean canMoveto(int row, int col) {
		if (row < 0 || row >= cave.length) {
			return false; // Out of bounds
		}
		if (col < 0 || col >= cave[row].length) {
			return false; // Out of bounds
		}

		char square = cave[row][col];
		return square == '.' || square == 'M';
	}
	
	// This method should return true if there is a path from the start to the end, 
	// and false otherwise. It will also print out the possible moves from the start position.
	private boolean walkFrom(int row, int col) {

		Stack<CaveStep> moves = new Stack<>();

		moves.push(new CaveStep(row, col, ""));
		path = ""; // Reset path

		while (!moves.isEmpty()){

			CaveStep current = moves.pop();

			int currentRow = current.row;
			int currentCol = current.col;

			// Skip locations already covered
			if (cave[currentRow][currentCol] == '*'){
				continue;
			}

			// Check if we found the mirror pool
			if (cave[currentRow][currentCol] == 'M'){
				path = current.path; // Store the path taken
				return true; // Found the mirror pool
			}

			// Mark the path
			cave[currentRow][currentCol] = '*'; 

			// Check East
			if (canMoveto(currentRow, currentCol + 1)){
				moves.push(new CaveStep(
					currentRow, currentCol + 1,
				current.path + "e"));
			}

			//Check West
			if (canMoveto(currentRow, currentCol - 1)){
				moves.push(new CaveStep(
					currentRow, currentCol - 1,
				current.path + "w"));
			}

			//Check South
			if (canMoveto(currentRow + 1, currentCol)){
				moves.push(new CaveStep(
					currentRow + 1, currentCol,
				current.path + "s"));
			}

			//Check North
			if (canMoveto(currentRow - 1, currentCol)){
				moves.push(new CaveStep(
					currentRow - 1, currentCol,
				current.path + "n"));
			}
		}

		return false; // No valid moves available
		/*StringBuilder moves = new StringBuilder();

		while (cave[row][col] != 'M') {
			int nextRow = row;
			int nextCol = col;

			if(canMoveto(row - 1, col)) { // North
				nextRow--; 
				moves.append('n'); // Append 'n' for north move
			} else if(canMoveto(row + 1, col)) { // South
				nextRow++;
				moves.append('s');// Append 's' for south move
			} else if(canMoveto(row, col -1)){ // West
				nextCol--;
				moves.append('w');// Append 'w' for west move
			} else if(canMoveto(row, col + 1)){ // East
				nextCol++;
				moves.append('e');// Append 'e' for east move
			} else {
				return false; // No valid moves available
			}
			
			cave[row][col] = '*'; // Mark the path
			row = nextRow;
			col = nextCol;
		}
		path = moves.toString(); // Store the path taken
		return true; // Reached the end
		*/
	}

	// This method should return true if there is a path from the start to the end, 
	// and false otherwise.
	// it will just print out the possible moves from the start position.
	public boolean solve() {
		for (int row = 0; row < cave.length; row++) {
			for (int col = 0; col < cave[row].length; col++) {
				if (cave[row][col] == 'S') {
					//System.out.println("Found S at row " + row + ", col" + col);
					//System.out.println("North: " + canMoveto(row - 1, col));
					//System.out.println("South: " + canMoveto(row + 1, col));
					//System.out.println("West: " + canMoveto(row, col - 1));
					//System.out.println("East: " + canMoveto(row, col + 1));
					return walkFrom(row, col); // Start walking from the start position
				}
			}
		}
		return false;
	}
	
	// Step 4: getPath method
	public String getPath(){
		return path;
	}
	
	// Step 5: One parameter constructor to read from a file
	
	public CaveExplorer (String fileName) throws FileNotFoundException {
	    
		try (Scanner input = new Scanner(new File(fileName))){
			int rows = input.nextInt();
			int cols = input.nextInt();

			cave = new char[rows][cols];
			
			for (int row = 0; row < rows; row++) {
				String line = input.next();

				if (line.length() != cols) {
					throw new IllegalArgumentException(
						"Cave row " + row + "must contain " + cols + "characters.");
				}
				
				line.getChars(0, cols, cave[row], 0);
			}  	
    	} // Construct cave and populate with rest of data in the file
	}	

	

	
	public static void main(String[] args) {
		System.out.println("Starting CaveExplorer");
		
		// Create a CaveExplorer object and print the starting layout
		CaveExplorer explorer = new CaveExplorer();
		String startingLayout = explorer.toString();
		System.out.println(startingLayout);

		String expected = "RRRRRR\\nR..S.R\\nR.R.RR\\nR.MRRR\\nRRRRRR\\n";
		System.out.println("Layout matches expected: " + expected.equals(startingLayout));

		// Call solve
		//explorer.solve();
		boolean foundPool = explorer.solve();

		// Print the final layout, whether there is a path, and if so, what it is.
		System.out.println("Final layout: ");
		System.out.println(explorer);
		System.out.println("Reached mirror pool: " + foundPool);

		System.out.println("Path taken; " + explorer.getPath());
		(System.out).println("Path matches expected: " + "wwsse".equals(explorer.getPath()));


		
		// Step 5/6: Repeat for a different CaveExplorer object read from a file
		// Uncomment code below to start testing your 1-parameter constructor
		String fileName = "testdat.txt"; 
		try { 
			CaveExplorer filExplorer = new CaveExplorer(fileName); 
			String fileStartingLayout = filExplorer.toString(); 
			boolean fileFoundPool = filExplorer.solve(); 
			
			System.out.println("\nStarting layout from " + fileName + ":");
			System.out.println(fileStartingLayout);
			
			System.out.println("Final layout: ");
			System.out.println(filExplorer);

			System.out.println("Reached mirror pool: " + fileFoundPool);
				if (fileFoundPool) {
					System.out.println("Path taken: " + filExplorer.getPath());
				}
			System.out.println("Path matches expected: " + "eeeennnn".equals(filExplorer.getPath()));
			
			//adding blocked path test
			CaveExplorer blockedExplorer = new CaveExplorer("blocked.txt");
			String blockedStart = blockedExplorer.toString();
			boolean blockedFoundPool = blockedExplorer.solve();

			System.out.println("\nStarting layout from blocked.txt:");
			System.out.println(blockedStart);
			System.out.println("Final layout: ");
			System.out.println(blockedExplorer);
			System.out.println("Reached mirror pool: " + blockedFoundPool);
			System.out.println("Path is empty: " + blockedExplorer.getPath().isEmpty());
		} 
		catch (FileNotFoundException e ) {
			System.out.println("Can't find file " + fileName); 
		}
		catch (Exception e) {
			System.out.println("Other error: " + e.getMessage());
		}
		
		
		System.out.println("Finished CaveExplorer");
	}
}
//FINAL
