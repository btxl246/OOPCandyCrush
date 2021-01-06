package model;

import ui.Sound;

import java.awt.*;
import java.util.*;

public class Grid {
    private ArrayList<ArrayList<String>> grid;
    private final ArrayList<String> tiles = new ArrayList<>();
    private final int NUM_TILES = 6;
    private final Random random = new Random();
    private int totalScore = 0;
    private int totalMatches = 0;

    public Grid(int rows, int cols, String pack) {
        String type;
        if (pack.equalsIgnoreCase("fruit"))
            type = ".jpg";
        else
            type = ".png";

        for (int i = 0; i < NUM_TILES; i++)
            this.tiles.add("in/images/" + pack + "/" + i + type);

        do {
            this.grid  = new ArrayList<>();
            for (int y = 0; y < rows; y++) {
                ArrayList<String> row = new ArrayList<>();
                for (int x = 0; x < cols; x++)
                    row.add(tiles.get(this.random.nextInt(NUM_TILES)));
                grid.add(row);
            }
        } while ((checkMatches() && moreCorrectMoves(grid)) || (checkMatches() && !moreCorrectMoves(grid)) || (!checkMatches() && !moreCorrectMoves(grid)));
    }

    private LinkedHashSet<Point> allMatches() {
        LinkedHashSet<Point> allMatches = new LinkedHashSet<>();
        allMatches.addAll(horizontalMatches());
        allMatches.addAll(verticalMatches());

        return allMatches;
    }

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

    public boolean checkMatches() {
        return allMatches().size() > 0;
    }

    public void switchTilesDemo(Point a, Point b) {
        String temp = getIcon(a);
        setIcon(a, getIcon(b));
        setIcon(b, temp);
    }

    public void switchTiles() {
        if (checkMatches())
            removeTiles();
    }

    private void removeTiles() {
        while (allMatches().size() > 0) {
            new Sound();
            this.totalMatches++;
            LinkedHashSet<Point> newPoints = new LinkedHashSet<>();
            LinkedHashSet<Point> allMatches = allMatches();

            ArrayList<LinkedHashSet<Point>> sets = new ArrayList<>();
            for (int x = minX(allMatches); x <= maxX(allMatches); x++) {
                LinkedHashSet<Point> thisSet = new LinkedHashSet<>();
                for (int y = minY(allMatches); y <= maxY(allMatches); y++) {
                    if (allMatches.contains(new Point(x, y))) {
                        Point a = new Point(x, y);
                        thisSet.add(a);
                    }
                }
                if (thisSet.size() != 0)
                    sets.add(thisSet);
            }

            for (LinkedHashSet<Point> set : sets) {
                for (Point p : set)
                    this.totalScore += score(getIcon(p));
            }

            for (LinkedHashSet<Point> set : sets) {
                int setSize = set.size();
                for (Point p : set) {
                    Point a = new Point();
                    a.setLocation(p.x, p.y);
                    Point b = new Point();

                    while ((a.y - setSize) >= 0) {
                        b.setLocation(a.x, a.y - setSize);
                        switchTilesDemo(a, b);
                        a.setLocation(a.x, a.y - setSize);
                    }

                    newPoints.add(a);
                }
            }

            for (Point p : newPoints) {
                setNewTile(p);
            }
        }
    }

    private void setNewTile(Point p) {
        setIcon(p, this.tiles.get(this.random.nextInt(NUM_TILES)));
    }

    private int maxX(LinkedHashSet<Point> set) {
        int maxX = 0;
        for (Point p : set) {
            if (maxX < p.x)
                maxX = p.x;
        }

        return maxX;
    }

    private int maxY(LinkedHashSet<Point> set) {
        int maxY = 0;
        for (Point p : set) {
            if (maxY < p.y)
                maxY = p.y;
        }

        return maxY;
    }

    private int minX(LinkedHashSet<Point> set) {
        int minX = getCols();
        for (Point p : set) {
            if (minX > p.x)
                minX = p.x;
        }

        return minX;
    }

    private int minY(LinkedHashSet<Point> set) {
        int minY = getRows();
        for (Point p : set) {
            if (minY > p.y)
                minY = p.y;
        }

        return minY;
    }

    private int score(String str) {
        int value = Integer.parseInt(str.replaceAll("[^0-9]", ""));
        value++;
        value *= 10;

        return value;
    }

    private boolean match(Point a, Point b, ArrayList<ArrayList<String>> grid) {
        return grid.get(a.y).get(a.x).equals(grid.get(b.y).get(b.x));
    }

    private boolean withinGrid(Point p, ArrayList<ArrayList<String>> grid) {
        return (p.x >= 0) && (p.x < grid.get(0).size()) && (p.y >= 0) && (p.y < grid.size());
    }

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

    public boolean moreCorrectMoves(){
        return this.moreCorrectMoves(this.grid);
    }

    public int getRows() {
        return this.grid.size();
    }

    public int getCols() {
        return this.grid.get(0).size();
    }

    public String getIcon(Point p) {
        return this.grid.get(p.y).get(p.x);
    }

    private void setIcon(Point p, String str) {
        this.grid.get(p.y).set(p.x, str);
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public int getTotalMatches() {
        return this.totalMatches;
    }
}
