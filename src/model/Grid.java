package model;

import java.awt.*;
import java.util.*;

public class Grid {
    private ArrayList<ArrayList<String>> grid;
    private ArrayList<String> tiles = new ArrayList<>();
    private Random random = new Random();
    private int totalScore = 0;
    private final int NUM_TILES = 6;

    public Grid(int rows, int cols, String pack) {
        String type;
        if (pack.equalsIgnoreCase("fruit"))
            type = ".jpg";
        else
            type = ".png";

        for (int i = 0; i < NUM_TILES; i++) {
            this.tiles.add("in/images/" + pack + "/" + i + type);
        }

        do {
            this.grid  = new ArrayList<>();
            for (int y = 0; y < rows; y++) {
                ArrayList<String> row = new ArrayList<>();
                for (int x = 0; x < cols; x++) {
                    row.add(tiles.get(this.random.nextInt(NUM_TILES)));
                }
                this.grid.add(row);
            }

            /*for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    System.out.print(grid.get(y).get(x) + "  ");
                }
                System.out.println();
            }*/
        } while (checkMatches() && moreCorrectMoves(grid));
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

    public void setIcon(Point p, String str) {
        this.grid.get(p.y).set(p.x, str);
    }

    public LinkedHashSet<Point> allMatches() {
        LinkedHashSet<Point> allMatches = new LinkedHashSet<>();
        allMatches.addAll(horizontalMatches());
        allMatches.addAll(verticalMatches());
        //System.out.println(allMatches);
        return allMatches;
    }

    public LinkedHashSet<Point> horizontalMatches() {
        LinkedHashSet<Point> hMatches = new LinkedHashSet<>();

        for (int y = 0; y < getRows(); y++) {
            //System.out.print("Row: " + y + "  ");
            for (int x = 0; x < getCols() - 2; x++) {
                HashSet<Point> hPoints = new HashSet<>();
                HashSet<String> hIcons = new HashSet<>();

                //System.out.print("\tCol: " + x + "  ");
                for (int i = 0; i < 3; i++) {
                    Point point = new Point(x + i, y);
                    hPoints.add(point);
                    //System.out.print(point + "  ");

                    String str = getIcon(point);
                    hIcons.add(str);
                    //System.out.print(str + "  ");
                }
                if (hIcons.size() == 1)
                    hMatches.addAll(hPoints);
            }
            //System.out.println();
        }

        //System.out.println(hMatches);
        return hMatches;
    }

    public LinkedHashSet<Point> verticalMatches() {
        LinkedHashSet<Point> vMatches = new LinkedHashSet<>();

        for (int x = 0; x < getCols(); x++) {
            //System.out.print("Col: " + x + "  ");
            for (int y = 0; y < getRows() - 2; y++) {
                HashSet<Point> vPoints = new HashSet<>();
                HashSet<String> vIcons = new HashSet<>();

                //System.out.print("\tRow: " + y + "  ");
                for (int i = 0; i < 3; i++) {
                    Point point = new Point(x, y + i);
                    vPoints.add(point);
                    //System.out.print(point + "  ");

                    String str = getIcon(point);
                    vIcons.add(str);
                    //System.out.print(str + "  ");
                }
                if (vIcons.size() == 1)
                    vMatches.addAll(vPoints);
            }
            //System.out.println();
        }

        //System.out.println(vMatches);
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
        if (checkMatches()) {
            removeTiles();
            System.out.println("Total score: " + this.totalScore);
        }
    }

    public void removeTiles() {
        int i = 0;
        while (allMatches().size() > 0) {
            LinkedHashSet<Point> newPoints = new LinkedHashSet<>();

            System.out.println("\tLoop: " + i);
            LinkedHashSet<Point> allMatches = allMatches();
            System.out.println("All: " + allMatches.size() + "  " + allMatches);

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
                System.out.println(set);
                for (Point p : set)
                    this.totalScore += score(getIcon(p));
                System.out.println();
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
                System.out.print(p + "  " + getIcon(p) + " -> ");
                setNewTile(p);
                System.out.println(getIcon(p));
            }

            i++;
        }
    }

    public void setNewTile(Point p) {
        setIcon(p, this.tiles.get(this.random.nextInt(NUM_TILES)));
    }

    public int maxX(LinkedHashSet<Point> set) {
        int maxX = 0;
        for (Point p : set) {
            if (maxX < p.x)
                maxX = p.x;
        }
        return maxX;
    }

    public int maxY(LinkedHashSet<Point> set) {
        int maxY = 0;
        for (Point p : set) {
            if (maxY < p.y)
                maxY = p.y;
        }
        return maxY;
    }

    public int minX(LinkedHashSet<Point> set) {
        int minX = getCols();
        for (Point p : set) {
            if (minX > p.x)
                minX = p.x;
        }
        return minX;
    }

    public int minY(LinkedHashSet<Point> set) {
        int minY = getRows();
        for (Point p : set) {
            if (minY > p.y)
                minY = p.y;
        }
        return minY;
    }

    public int score(String str) {
        int value = Integer.parseInt(str.replaceAll("[^0-9]", ""));
        value++;
        value *= 10;
        //System.out.print(value + "  ");
        return value;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public boolean match(Point a, Point b, ArrayList<ArrayList<String>> grid) {
        return grid.get(a.y).get(a.x).equals(grid.get(b.y).get(b.x));
    }

    public boolean withinGrid(Point p, ArrayList<ArrayList<String>> grid) {
        return (p.x >= 0) && (p.x < grid.get(0).size()) && (p.y >= 0) && (p.y < grid.size());
    }

    public boolean moreCorrectMoves(ArrayList<ArrayList<String>> grid) {
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
                //System.out.println(a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + ", " + h + ", " + i + ", " + j + ", " + k + ", " + l);

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
}
