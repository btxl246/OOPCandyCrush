package code.model;

import java.awt.Point;
import java.util.*;

public class Grid {
	private ArrayList<ArrayList<String>> grid;	// The grid of tiles.
	private ArrayList<String> tileFileNames;	// The names of available tile images.
	private Random rand;						// The randomizer.
	private int total = 0;						// The total score.
	private final int MAX_TILES = 6; 			// The maximum number of types of tiles.

	/**
	 * The constructor to construct the grid.
	 * @param rows The number of rows in the grid.
	 * @param cols The number of columns in the grid.
	 */

	public Grid(int rows, int cols) {
		do {
			// Initialize the fields.
			this.grid = new ArrayList<>();
			this.rand = new Random();
			this.tileFileNames = new ArrayList<>();

			// Load the tile images.
			for (int i = 0; i < MAX_TILES; i++) {
				tileFileNames.add("images/" + i + ".jpg");	// Add the file names for the tiles.
			}
			for (int y = 0; y < rows; y++) {
				ArrayList<String> thisRow = new ArrayList<>();	// Initialize a new ArrayList of names.
				for (int x = 0; x < cols; x++) {
					thisRow.add(tileFileNames.get(rand.nextInt(tileFileNames.size())));	// Randomize and add the images to the tiles.
				}
				grid.add(thisRow);	// Add this y to the grid.
			}
		} while (this.matchCheck() && this.moreCorrectMoves(grid));
	}

	/**
	 * The rows() method returns the number of rows.
	 * @return The number of rows.
	 */

	public int rows() {
		return this.grid.size();	// Size of the ArrayList.
	}

	/**
	 * The cols() method returns the number of columns.
	 * @return The number of columns.
	 */

	public int cols() {
		return this.grid.get(0).size();		// Size of the inner ArrayList at index [0] of the outer ArrayList.
	}

	/**
	 * The get() method returns a Point's icon's name.
	 * @param p The Point.
	 * @return The icon's name.
	 */

	public String get(Point p) {
		//System.out.println("\t\tx = " + p.y + ", y = " + p.x + "\t" + this.grid.get(p.x).get(p.y));
		return this.grid.get(p.x).get(p.y);	// Name of icon of Point at col x and row y in grid.
	}

	/**
	 * The set() method pairs a Point with a new icon.
	 * @param p The current Point.
	 * @param s The new icon's name.
	 */

	private void set(Point p, String s) {
		this.grid.get(p.x).set(p.y, s);	// Replace the icon.
	}

	/**
	 * The exchange() method switches the types of the Points.
	 * @param a The first Point.
	 * @param b The second Point.
	 */

	public void exchange(Point a, Point b) {
		//System.out.println("===Grid exchange(Point a, Point b) a[" + a.y + ", " + a.x + "]  b[" + b.y + ", " + b.x + "]");
		String temp = this.get(a);
		this.set(a, get(b));	// Switch icons.
		set(b, temp);
	}

	/**
	 * The exchangeExtension() method calculates the total score.
	 * @return The total score.
	 */

	public int exchangeExtension() {
		if (this.matchCheck()) {	// If there is a match,
			while (this.matchCheck()) {
				this.removeTiles();			// remove the tiles.
				this.total += score();
			}
		}

		return total;
	}

	/**
	 * The score() method calculates the score of a move.
	 * @return The score.
	 */

	public int score() {
		return 3 + ((partition(grid).size() - 1) - 3) * ((partition(grid).size() - 1) - 3);
	}

	/**
	 * The matchCheck() method checks if there is a match.
	 * @return True if there is a match.
	 */

	public boolean matchCheck() {
		return this.match().size() > 0;
	}

	/**
	 * The removeTiles() method removes tiles in a match and randomize new icons.
	 */

	public void removeTiles() {
		for (Point p : match()) {
			Point a = new Point(p.x, p.y);
			Point b = new Point();
			this.set(p, null);	// Remove its icon.

			//int i = 1;
			while (a.x != 0) {	// If the tile is not on column 0.
				//System.out.println("===Grid removeTiles() i = " + i);
				b.setLocation(a.x - 1, a.y);	// Set Point on top of the tile.
				String temp = this.get(a);
				set(a, get(b));	// Switch the Points.
				set(b, temp);
				a = new Point(a.x - 1, a.y);
				//i++;
			}

			this.dropDown(a);
		}
	}

	/**
	 * The dropDown() method randomizes a new icon for an old tile.
	 * @param p The old tile.
	 */

	public void dropDown(Point p) {
		this.set(p, this.tileFileNames.get(this.rand.nextInt(tileFileNames.size())));
	}

	/**
	 * The match() method returns the set of matched tiles, but does not actually check for matches.
	 * @return The matched tiles.
	 */

	public LinkedHashSet<Point> match() {
		LinkedHashSet<Point> matches = this.verticalMatch();
		//System.out.println("Grid verticalMatch(int runLength)===");
		matches.addAll(this.horizontalMatch());
		//System.out.println("Grid horizontalMatch(int runLength)===");
		return matches;
	}

	/**
	 * The morecorrectMoves() method checks if there is a possible correct move after a click.
	 * @param grid The grid.
	 * @return True if possible.
	 */

	private boolean moreCorrectMoves(ArrayList<ArrayList<String>> grid) { //note: 5 here is the ideal size for the game, suggest changes for a dynamic grid
		for (int x = 0; x < 5; x++) { 
			for (int y = 0; y < 5; y++) { //After a click, check every tile for matches that consist that tile
				Point a = new Point(x - 1, y); //UP
				Point b = new Point(x + 1, y); //DOWN
				Point c = new Point(x, y - 1); //LEFT
				Point d = new Point(x, y + 1); //RIGHT
				Point e = new Point(x + 1, y + 1); //DOWN RIGHT
				Point f = new Point(x - 1, y + 1); //UP RIGHT
				Point g = new Point(x + 1, y - 1); //DOWN LEFT
				Point h = new Point(x - 1, y - 1); //UP LEFT
				Point i = new Point(x + 2, y); //DOWN * 2
				Point j = new Point(x, y + 2); //RIGHT *2
				Point k = new Point(x - 2, y); //UP * 2
				Point l = new Point(x, y - 2); //LEFT * 2
				//System.out.println(a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + ", " + h + ", " + i + ", " + j + ", " + k + ", " + l);

				if (inBounds(a, grid) && inBounds (b, grid) && inBounds(c, grid) && matches(a, b, grid) && matches(a, c, grid)) { return true; } //Check if the combinations consist
				if (inBounds(a, grid) && inBounds (b, grid) && inBounds(d, grid) && matches(a, b, grid) && matches(a, d, grid)) { return true; } //of tiles that are in bounds and 
				if (inBounds(c, grid) && inBounds (b, grid) && inBounds(d, grid) && matches(c, b, grid) && matches(c, d, grid)) { return true; } //of they match, return true
				if (inBounds(c, grid) && inBounds (a, grid) && inBounds(d, grid) && matches(c, a, grid) && matches(c, d, grid)) { return true; }
				if (inBounds(a, grid) && inBounds (c, grid) && inBounds(g, grid) && matches(a, c, grid) && matches(a, g, grid)) { return true; }
				if (inBounds(a, grid) && inBounds (d, grid) && inBounds(e, grid) && matches(a, d, grid) && matches(a, e, grid)) { return true; }
				if (inBounds(c, grid) && inBounds (a, grid) && inBounds(f, grid) && matches(c, a, grid) && matches(c, f, grid)) { return true; }
				if (inBounds(c, grid) && inBounds (b, grid) && inBounds(e, grid) && matches(c, b, grid) && matches(c, e, grid)) { return true; }
				if (inBounds(b, grid) && inBounds (d, grid) && inBounds(f, grid) && matches(b, d, grid) && matches(b, f, grid)) { return true; }
				if (inBounds(b, grid) && inBounds (c, grid) && inBounds(h, grid) && matches(b, c, grid) && matches(b, h, grid)) { return true; }
				if (inBounds(d, grid) && inBounds (a, grid) && inBounds(h, grid) && matches(d, a, grid) && matches(d, h, grid)) { return true; }
				if (inBounds(d, grid) && inBounds (b, grid) && inBounds(g, grid) && matches(d, b, grid) && matches(d, g, grid)) { return true; }
				if (inBounds(a, grid) && inBounds (b, grid) && inBounds(i, grid) && matches(a, b, grid) && matches(a, i, grid)) { return true; }
				if (inBounds(a, grid) && inBounds (k, grid) && inBounds(b, grid) && matches(a, k, grid) && matches(a, b, grid)) { return true; }
				if (inBounds(j, grid) && inBounds (d, grid) && inBounds(c, grid) && matches(j, d, grid) && matches(j, c, grid)) { return true; }
				if (inBounds(d, grid) && inBounds (c, grid) && inBounds(l, grid) && matches(d, c, grid) && matches(l, d, grid)) { return true; }
			}
		}

		return false;
	}

	public boolean moreCorrectMoves(){
		return this.moreCorrectMoves(this.grid);
	}

	/**
	 * The horizontalMatch() method checks all horizontal set of 3 tiles, and record the set of 3 that match.
	 * @return The set of matched tiles.
	 */

	private LinkedHashSet<Point> horizontalMatch() {
		LinkedHashSet<Point> matches = new LinkedHashSet<>();
		int minCol = 0; //a match might start from the left-most side of the grid
		int maxCol = this.cols() - 3; // the last possible horizontal match would start at the right-most border minus 3

		for (int row = 0; row < rows(); row++) { //check every row
			for (int col = minCol; col <= maxCol; col++) {	// Check the columns that are possible to have horizontal matches
				HashSet<String> values = new HashSet<>(); //set that contains the type of that tile, does not allow dulicates; new entry that has already existed will be overwritten
				HashSet<Point> points = new HashSet<>(); //set that contains the coordinates of that tile, does not allow dulicates; new entry that has already existed will be overwritten
				for (int offset = 0; offset < 3; offset++) {
					//System.out.println("\tGrid horizontalMatch(int runLength) col = " + col + " offset = " + offset);
					Point p = new Point(row, col + offset);
					points.add(p); // add the tiles to form a set of 3
					String s = get(p);
					values.add(s); // add the type of the tile
				}
				if (values.size() == 1) { // if the type of the set of 3 tiles is 1 (meaning they're the same icons), then it's a legitimate match
					matches.addAll(points); // put the set of match-3 in matches, a set of Points
				}
			}
		}

		return matches;
	}

	/**
	 * The verticalMatch() method checks all vertical set of 3 tiles, and record the set of 3 that match.
	 * @return The matched tiles.
	 */

	private LinkedHashSet<Point> verticalMatch() {
		LinkedHashSet<Point> matches = new LinkedHashSet<>();
		int minRow = 0; // a match might start at the top-most row of tiles
		int maxRow = rows() - 3; // the last possible vertical matches occurs at the bottom-most row of tiles minus 3

		for (int col = 0; col < cols(); col++) {
			for (int row = minRow; row <= maxRow; row++) {	// The rows we can start checking.
				HashSet<String> values = new HashSet<>();
				HashSet<Point> points = new HashSet<>();
				for (int offset = 0; offset < 3; offset++) {
					//System.out.println("\tGrid verticalMatch(int runLength) row = " + row + " offset = " + offset);
					Point p = new Point(row + offset, col);
					points.add(p);
					String s = get(p);
					values.add(s);
				}
				if (values.size() == 1) { // again, 1 here means the set of 3 tiles is of 01 single type
					matches.addAll(points);
				}
			}
		}

		return matches;
	}

	/**
	 * Finding the valid tiles
	 */

	private HashSet<Point> trimmedRegion(HashSet<Point> in) {
		HashSet<Point> out = new HashSet<>();
		for (Point p : in) {
			if (this.inRow(p, in) || this.inCol(p, in)) {
				out.add(p);
			}
		}

		return out;
	}

	private boolean inCol(Point p, HashSet<Point> in) {
		return (in.contains(new Point(p.x, p.y - 2)) && in.contains(new Point(p.x, p.y - 1)))
				|| (in.contains(new Point(p.x, p.y - 1)) && in.contains(new Point(p.x, p.y + 1)))
				|| (in.contains(new Point(p.x, p.y + 1)) && in.contains(new Point(p.x, p.y + 2)));
	}

	private boolean inRow(Point p, HashSet<Point> in) {
		return (in.contains(new Point(p.x - 2, p.y)) && in.contains(new Point(p.x - 1, p.y)))
				|| (in.contains(new Point(p.x - 1, p.y)) && in.contains(new Point(p.x + 1, p.y)))
				|| (in.contains(new Point(p.x + 1, p.y)) && in.contains(new Point(p.x + 2, p.y)));
	}

	/**
	 * Finding the maximum adjacent tiles in the whole grid
	 */

	public HashSet<HashSet<Point>> partition(ArrayList<ArrayList<String>> grid) {
		HashSet<HashSet<Point>> partition = new HashSet<>();
		if (grid != null) {
			ArrayList<Point> pointsToCheck = new ArrayList<>();
			for (int row = 0; row < grid.size(); row++) {
				for (int col = 0; col < grid.get(0).size(); col++) {
					pointsToCheck.add(new Point(row, col));
				}
			}

			while (!pointsToCheck.isEmpty()) {
				Point p = pointsToCheck.get(0);
				HashSet<Point> region = this.maximalAdjacentRegion(p, grid);
				pointsToCheck.removeAll(region);
				partition.add(this.trimmedRegion(region));
			}
		}

		return partition;
	}

	/**
	 * Finding the maximum adjacent tiles by checking the surrounding plus
	 */

	private HashSet<Point> maximalAdjacentRegion(Point start, ArrayList<ArrayList<String>> grid) {
		HashSet<Point> matchedRegion = new HashSet<>();
		HashSet<Point> candidatesForInclusion = new HashSet<>();
		HashSet<Point> adjacentMatches = new HashSet<>();

		candidatesForInclusion.add(start);

		while (!candidatesForInclusion.isEmpty()) {
			for (Point p : candidatesForInclusion) {
				for (Point q : this.adjacentAndMatching(p, grid)) {
					if (!matchedRegion.contains(q) && !candidatesForInclusion.contains(q)) {
						adjacentMatches.add(q);
					}
				}
			}

			matchedRegion.addAll(candidatesForInclusion);
			HashSet<Point> temp = candidatesForInclusion;
			candidatesForInclusion = adjacentMatches;
			adjacentMatches = temp;
			adjacentMatches.clear();
		}

		return matchedRegion;
	}
	
	/** adjacentAndMatching() method
	 * checks a tile p's surroundings in a plus shape to see if: 
	 * a) if the surrounding tiles are in bounds
	 * b) if they're the same type as p
	 * @param p : the point inspecting (Point)
	 * @param grid : The grid (ArrayList)
	 */

	private HashSet<Point> adjacentAndMatching(Point p, ArrayList<ArrayList<String>> grid) {
		HashSet<Point> result = new HashSet<>(); // result is a set of Points of surrounding tiles that match with the tile being inspected (in this case, it's p)
		if (p != null) {
			Point check = new Point(p.x - 1, p.y); // check the point above p
			if (this.inBounds(check, grid) && this.matches(p, check, grid)) { result.add(check); }

			check = new Point(p.x + 1, p.y); // check the point below p
			if (inBounds(check, grid) && matches(p, check, grid)) { result.add(check); }

			check = new Point(p.x, p.y - 1); // check the point to the left of p
			if (inBounds(check, grid) && matches(p, check, grid)) { result.add(check); }

			check = new Point(p.x, p.y + 1); // check the point to the right of p
			if (inBounds(check, grid) && matches(p, check, grid)) { result.add(check); }
		}

		return result;
	}
	
	
	/** matches() method
	 * Check if 2 tiles are the same type by comparing the Strings that represent their types
	 */

	private boolean matches(Point a, Point b, ArrayList<ArrayList<String>> grid) { 
		return grid.get(a.x).get(a.y).equals(grid.get(b.x).get(b.y));
	}

	
	/** inBounds()
	 * Check if 2 tiles are the same type by comparing the Strings that represent their types 
	 */
	private boolean inBounds(Point p, ArrayList<ArrayList<String>> grid) { // Check to see if a tile is within the bounds of the grid
		return p.x >= 0 && p.x < grid.size() && p.y >= 0 && p.y < grid.get(0).size();
	}
}
