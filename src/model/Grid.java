/**
 * Object-Oriented Programming project.
 * @author Bui Thi Xuan Lan - ITDSIU19007
 * @author Nguyen Duc Minh - ITITIU19030
 */

package model;

import ui.Sound;

import java.awt.*;
import java.util.*;

/**
 * Handles functionalities in the play grid.
 */

public class Grid {
    private ArrayList<ArrayList<String>> grid;                      // Play grid.
    private final ArrayList<String> TILES = new ArrayList<>();      // Image icons for tiles.
    private final int NUM_TILES = 6;                                // Number of tile variations.
    private final Random RANDOM = new Random();                     // Randomizer.
    private int totalScore = 0;                                     // Total score.
    private int totalMatches = 0;                                   // Total matches.

    /**
     * Grid constructor.
     * @param rows Height of the grid.
     * @param cols Width of the grid.
     * @param pack Tile theme.
     */

    public Grid(int rows, int cols, String pack) {
        // Processes paths to images.
        String type;
        if (pack.equalsIgnoreCase("fruit"))
            type = ".jpg";
        else
            type = ".png";

        // Adds image paths.
        for (int i = 0; i < NUM_TILES; i++)
            this.TILES.add("in/images/" + pack + "/" + i + type);

        // Randomizes a grid with no match and more correct moves.
        do {
            this.grid  = new ArrayList<>();
            for (int y = 0; y < rows; y++) {
                ArrayList<String> row = new ArrayList<>();
                for (int x = 0; x < cols; x++)
                    row.add(TILES.get(this.RANDOM.nextInt(NUM_TILES)));
                grid.add(row);
            }
        } while ((checkMatches() && moreCorrectMoves(grid)) || (checkMatches() && !moreCorrectMoves(grid)) || (!checkMatches() && !moreCorrectMoves(grid)));
    }

    /**
     * All matches.
     * @return Set of all matches.
     */

    private LinkedHashSet<Point> allMatches() {
        LinkedHashSet<Point> allMatches = new LinkedHashSet<>();
        allMatches.addAll(horizontalMatches());
        allMatches.addAll(verticalMatches());

        return allMatches;
    }

    /**
     * Horizontal matches.
     * @return Set of horizontal matches.
     */

    private LinkedHashSet<Point> horizontalMatches() {
        LinkedHashSet<Point> hMatches = new LinkedHashSet<>();

        for (int y = 0; y < getRows(); y++) {
            for (int x = 0; x < getCols() - 2; x++) {
                HashSet<Point> hPoints = new HashSet<>();
                HashSet<String> hIcons = new HashSet<>();

                for (int i = 0; i < 3; i++) {
                    Point point = new Point(x + i, y);
                    hPoints.add(point);

                    String str = getIcon(point);
                    hIcons.add(str);
                }

                if (hIcons.size() == 1)
                    hMatches.addAll(hPoints);
            }
        }

        return hMatches;
    }

    /**
     * Vertical matches.
     * @return Set of vertical matches.
     */

    private LinkedHashSet<Point> verticalMatches() {
        LinkedHashSet<Point> vMatches = new LinkedHashSet<>();

        for (int x = 0; x < getCols(); x++) {
            for (int y = 0; y < getRows() - 2; y++) {
                HashSet<Point> vPoints = new HashSet<>();
                HashSet<String> vIcons = new HashSet<>();

                for (int i = 0; i < 3; i++) {
                    Point point = new Point(x, y + i);
                    vPoints.add(point);

                    String str = getIcon(point);
                    vIcons.add(str);
                }

                if (vIcons.size() == 1)
                    vMatches.addAll(vPoints);
            }
        }

        return vMatches;
    }

    /**
     * Checks if there are matches.
     * @return True if there is at least a match.
     */

    public boolean checkMatches() {
        return allMatches().size() > 0;
    }

    /**
     * Switches icons of two tiles.
     * @param a First tile.
     * @param b Second tile.
     */

    public void switchTilesTest(Point a, Point b) {
        String temp = getIcon(a);
        setIcon(a, getIcon(b));
        setIcon(b, temp);
    }

    /**
     * Processes switched tiles.
     */

    public void switchTiles() {
        if (checkMatches())
            removeTiles();
    }

    /**
     * Removes switched tiles and creates new ones.
     */

    private void removeTiles() {
        while (allMatches().size() > 0) {
            // Plays a sound for a match.
            Sound sound = Sound.getInstance();
            sound.play();
            sound.remove();

            this.totalMatches++;
            LinkedHashSet<Point> newPoints = new LinkedHashSet<>();
            LinkedHashSet<Point> allMatches = allMatches();

            // Turns all matches into vertical sets of tiles.
            ArrayList<LinkedHashSet<Point>> sets = new ArrayList<>();
            for (int x = minX(allMatches); x <= maxX(allMatches); x++) {
                LinkedHashSet<Point> thisSet = new LinkedHashSet<>();
                for (int y = minY(allMatches); y <= maxY(allMatches); y++)
                    if (allMatches.contains(new Point(x, y))) {
                        Point a = new Point(x, y);
                        thisSet.add(a);
                    }
                if (thisSet.size() != 0)
                    sets.add(thisSet);
            }

            // Calculates the total score.
            for (LinkedHashSet<Point> set : sets)
                for (Point p : set)
                    this.totalScore += score(getIcon(p));

            // Removes tiles.
            for (LinkedHashSet<Point> set : sets) {
                int setSize = set.size();
                for (Point p : set) {
                    Point a = new Point();
                    a.setLocation(p.x, p.y);
                    Point b = new Point();

                    while ((a.y - setSize) >= 0) {
                        b.setLocation(a.x, a.y - setSize);
                        switchTilesTest(a, b);
                        a.setLocation(a.x, a.y - setSize);
                    }

                    newPoints.add(a);
                }
            }

            // Creates new tiles.
            for (Point p : newPoints)
                setNewTile(p);
        }
    }

    /**
     * Randomizes a new tile.
     * @param p Old tile.
     */

    private void setNewTile(Point p) {
        setIcon(p, this.TILES.get(this.RANDOM.nextInt(NUM_TILES)));
    }

    /**
     * Finds the max X coordinate in a set of tiles.
     * @param set Set of tiles.
     * @return X coordinate.
     */

    private int maxX(LinkedHashSet<Point> set) {
        int maxX = 0;
        for (Point p : set) {
            if (maxX < p.x)
                maxX = p.x;
        }

        return maxX;
    }

    /**
     * Finds the min Y coordinate in a set of tiles.
     * @param set Set of tiles.
     * @return Y coordinate.
     */

    private int maxY(LinkedHashSet<Point> set) {
        int maxY = 0;
        for (Point p : set) {
            if (maxY < p.y)
                maxY = p.y;
        }

        return maxY;
    }

    /**
     * Finds the min X coordinate in a set of tiles.
     * @param set Set of tiles.
     * @return X coordinate.
     */

    private int minX(LinkedHashSet<Point> set) {
        int minX = getCols();
        for (Point p : set) {
            if (minX > p.x)
                minX = p.x;
        }

        return minX;
    }

    /**
     * Finds the min Y coordinate in a set of tiles.
     * @param set Set of tiles.
     * @return Y coordinate.
     */

    private int minY(LinkedHashSet<Point> set) {
        int minY = getRows();
        for (Point p : set) {
            if (minY > p.y)
                minY = p.y;
        }

        return minY;
    }

    /**
     * Calculates the score for a tile based on its path.
     * @param tile Tile.
     * @return Score.
     */

    private int score(String tile) {
        int value = Integer.parseInt(tile.replaceAll("[^0-9]", ""));
        value++;
        value *= 10;

        return value;
    }

    /**
     * Checks if two tiles are of the same variation.
     * @param a First tile.
     * @param b Second tile.
     * @param grid Play grid.
     * @return True if the same variation.
     */

    private boolean match(Point a, Point b, ArrayList<ArrayList<String>> grid) {
        return grid.get(a.y).get(a.x).equals(grid.get(b.y).get(b.x));
    }

    /**
     * Checks if a tile is within the play grid.
     * @param p Tile.
     * @param grid Play grid.
     * @return True if within.
     */

    private boolean withinGrid(Point p, ArrayList<ArrayList<String>> grid) {
        return (p.x >= 0) && (p.x < grid.get(0).size()) && (p.y >= 0) && (p.y < grid.size());
    }

    /**
     * Checks if there is at least one match after a move.
     * @param grid Play grid.
     * @return True if there is a match.
     */

    private boolean moreCorrectMoves(ArrayList<ArrayList<String>> grid) {
        for (int x = 0; x < grid.get(0).size(); x++) {
            for (int y = 0; y < grid.size(); y++) {
                Point a = new Point(x - 1, y);
                Point b = new Point(x + 1, y);
                Point c = new Point(x, y - 1);
                Point d = new Point(x, y + 1);
                Point e = new Point(x + 1, y + 1);
                Point f = new Point(x - 1, y + 1);
                Point g = new Point(x + 1, y - 1);
                Point h = new Point(x - 1, y - 1);
                Point i = new Point(x + 2, y);
                Point j = new Point(x, y + 2);
                Point k = new Point(x - 2, y);
                Point l = new Point(x, y - 2);

                // Checks if the tiles are within the play grid and if they are of the same variation.
                if (withinGrid(a, grid) && withinGrid(b, grid) && withinGrid(c, grid) && match(a, b, grid) && match(a, c, grid)) { return true; }
                if (withinGrid(a, grid) && withinGrid(b, grid) && withinGrid(d, grid) && match(a, b, grid) && match(a, d, grid)) { return true; }
                if (withinGrid(c, grid) && withinGrid(b, grid) && withinGrid(d, grid) && match(c, b, grid) && match(c, d, grid)) { return true; }
                if (withinGrid(c, grid) && withinGrid(a, grid) && withinGrid(d, grid) && match(c, a, grid) && match(c, d, grid)) { return true; }
                if (withinGrid(a, grid) && withinGrid(c, grid) && withinGrid(g, grid) && match(a, c, grid) && match(a, g, grid)) { return true; }
                if (withinGrid(a, grid) && withinGrid(d, grid) && withinGrid(e, grid) && match(a, d, grid) && match(a, e, grid)) { return true; }
                if (withinGrid(c, grid) && withinGrid(a, grid) && withinGrid(f, grid) && match(c, a, grid) && match(c, f, grid)) { return true; }
                if (withinGrid(c, grid) && withinGrid(b, grid) && withinGrid(e, grid) && match(c, b, grid) && match(c, e, grid)) { return true; }
                if (withinGrid(b, grid) && withinGrid(d, grid) && withinGrid(f, grid) && match(b, d, grid) && match(b, f, grid)) { return true; }
                if (withinGrid(b, grid) && withinGrid(c, grid) && withinGrid(h, grid) && match(b, c, grid) && match(b, h, grid)) { return true; }
                if (withinGrid(d, grid) && withinGrid(a, grid) && withinGrid(h, grid) && match(d, a, grid) && match(d, h, grid)) { return true; }
                if (withinGrid(d, grid) && withinGrid(b, grid) && withinGrid(g, grid) && match(d, b, grid) && match(d, g, grid)) { return true; }
                if (withinGrid(a, grid) && withinGrid(b, grid) && withinGrid(i, grid) && match(a, b, grid) && match(a, i, grid)) { return true; }
                if (withinGrid(a, grid) && withinGrid(k, grid) && withinGrid(b, grid) && match(a, k, grid) && match(a, b, grid)) { return true; }
                if (withinGrid(j, grid) && withinGrid(d, grid) && withinGrid(c, grid) && match(j, d, grid) && match(j, c, grid)) { return true; }
                if (withinGrid(d, grid) && withinGrid(c, grid) && withinGrid(l, grid) && match(d, c, grid) && match(l, d, grid)) { return true; }
            }
        }

        return false;
    }

    /**
     * Returns results of moreCorrectMoves(ArrayList<ArrayList<String>> grid).
     * @return True if there is a match.
     */

    public boolean moreCorrectMoves(){
        return this.moreCorrectMoves(this.grid);
    }

    /**
     * Returns the height of the play grid.
     * @return Height.
     */

    public int getRows() {
        return this.grid.size();
    }

    /**
     * Returns the width of the play grid.
     * @return Width.
     */

    public int getCols() {
        return this.grid.get(0).size();
    }

    /**
     * Returns the path to a tile's icon.
     * @param p Tile.
     * @return Path.
     */

    public String getIcon(Point p) {
        return this.grid.get(p.y).get(p.x);
    }

    /**
     * Gives a tile a new icon.
     * @param p Tile.
     * @param tile Path to icon.
     */

    private void setIcon(Point p, String tile) {
        this.grid.get(p.y).set(p.x, tile);
    }

    /**
     * Returns the total score.
     * @return Total score.
     */

    public int getTotalScore() {
        return this.totalScore;
    }

    /**
     * Returns the total matches.
     * @return Total matches.
     */

    public int getTotalMatches() {
        return this.totalMatches;
    }
}
