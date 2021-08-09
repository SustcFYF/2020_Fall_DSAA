//package homework1;

public class MazeTest {
    public static void main(String[] args) {
// TODO Auto-generated method stub
        int width = 10;
        int height = 6;
        int[][] mazeMatrix_1 = {
                {1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int[][] mazeMatrix_2 = {
                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 1, 0, 0, 1, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 1, 1, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        Maze maze_1 = new Maze(mazeMatrix_1, 10, 6);
        System.out.print(maze_1);
        try {
            maze_1.DFSearchPath(0, 0, 4, 4);
        } catch (Error e) {
            System.out.println("the path is: ");
            System.out.print(maze_1);
        }
        System.out.println();
        Maze maze_2 = new Maze(mazeMatrix_2, 10, 7);
        System.out.print(maze_2);
        try {
            maze_2.DFSearchPath(0, 9, 4, 8);
        } catch (Error e) {
            System.out.println("the path is: ");
            System.out.print(maze_2);
        }
    }
}
