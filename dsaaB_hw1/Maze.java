//package homework1;

import java.util.Stack;

public class Maze {
    int[][] mazeMat;
    private int width;
    private int height;
    boolean flag = true;
    static String path = "";
    //    Stack<Node> path=new Stack<>();
//    public static int dx[] = {0, 1, 0, -1};
//    public static int dy[] = {1, 0, -1, 0};
    static int[][] visit;

    public Maze(int[][] mazeMatrix, int width, int height) {
        this.mazeMat = mazeMatrix;
        this.width = width;
        this.height = height;
        path = "";
    }

    class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void DFSearchPath(int startX, int startY, int endX, int endY) {
        if (flag) {
            mazeMat[startX][startY] = 0;
            mazeMat[endX][endY] = 0;  // set start and end as 0
            flag = false;  // only run once
        }
        int x = startX;
        int y = startY;
        if (x < 0 || y < 0 || x > height - 1 || y > width - 1 || mazeMat[x][y] != 0) // judge if in the area and pass
            return;
        if (x == endX && y == endY) { // judge the end of DFS
            mazeMat[x][y] = 2;
            path = path + "{" + x + ", " + y + "}";
            System.out.println();
            System.out.println(path);
            System.out.println();
            throw new Error();  // exit the recursion
        }
        String temp = path;
        path = path + "{" + x + ", " + y + "}" + "->";
        mazeMat[x][y] = 2;
        DFSearchPath(x, y + 1, endX, endY);  //rightward
        DFSearchPath(x + 1, y, endX, endY);  //downward
        DFSearchPath(x, y - 1, endX, endY);  //leftward
        DFSearchPath(x - 1, y, endX, endY);  //upward
        mazeMat[x][y] = 0;
        path = temp;
    }

    @Override
    public String toString() {
        String ResultString = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                ResultString += mazeMat[i][j] + " ";
            }
            ResultString += "\n";
        }
        return ResultString;
    }
}