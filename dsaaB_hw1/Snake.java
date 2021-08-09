//package homework1;

import java.util.LinkedList;
import java.util.Queue;
// 0:snake
// 1:enpty
// 2:food
// 3:stone
public class Snake {
    public int[][] grid = null;
    int width = 20;
    int height = 20;
    Queue<pos> queueSnake = new LinkedList<>();
    public int length;
    pos curPos;
    pos foodPos;

    public Snake() {
        if (grid == null) grid = new int[height][width];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 1; //empty
            }
        }
        curPos = new pos(1, 1);
        foodPos = new pos(5, 5);
    }

    public class pos {
        public int x;
        public int y;

        public pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Snake(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new int[height][width];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 1; //empty
            }
        }
    }
    //generate the game map of matrix

    public void StartSnake(int i, int j) {
        curPos = new pos(i, j);
        queueSnake.add(curPos);
        length=queueSnake.size();
        grid[i][j] = 0; //snake
    }
    // start snake at position (i,j)

    public void ShowSnake() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    // print the grid with snake

    public void GenerateFood(int x, int y) {
        this.grid[x][y] = 2; //food
    }
    // generate food at position(x,y)

    public void GenerateFoodRandom() {
        int x = (int) (Math.random() * height);
        int y = (int) (Math.random() * width);
        while (FoodOnSnake(x, y) || grid[x][y] == 3) { // no snake and no stone
            x = (int) (Math.random() * height);
            y = (int) (Math.random() * width);
        }
        GenerateFood(x, y);
    }
    // generate food at a random position

    public void GenerateStone(int x, int y) {
        this.grid[x][y] = 3; //stone
    }
    // generate a stone at position(x,y)

    public void GenerateStoneRandom() {
        int x = (int) (Math.random() * height);
        int y = (int) (Math.random() * width);
        while (FoodOnSnake(x, y) || grid[x][y] == 2) { // no snake and no food
            x = (int) (Math.random() * height);
            y = (int) (Math.random() * width);
        }
        GenerateStone(x, y);
    }
    // generate a stone at a random position

    public boolean FoodOnSnake(int x, int y) {
        for (pos step : queueSnake) {
            if (step.x == x && step.y == y) {
                return true;
            }
        }
        return false;
    }
    // if food is on the snake, return true
// food should disappear and generates new food

    public void Move(int direction) {
        int x = curPos.x;
        int y = curPos.y;
        switch (direction) {
            case 1:
                y++;
                break;
            case 2:
                x++;
                break;
            case 3:
                y--;
                break;
            case 4:
                x--;
                break;
            default:
                System.out.println("invalid direction!");
                return;
        }
        if (x < 0 || x > height - 1 || y < 0 || y > width - 1) {
            System.out.println("The snake goes out of game area!");
            System.out.println("Game Over!");
            System.exit(0);
        } else if (grid[x][y] == 3) {
            System.out.println("The snake eats stone!");
            System.out.println("Game Over!");
            System.exit(0);
        } else {
            for (pos step : queueSnake) {
                if (step.x == x && step.y == y) { // meet itself
                    System.out.println("The snake eats itself!");
                    System.out.println("Game Over!");
                    System.exit(0);
                }
            }
        }
        curPos = new pos(x, y);
        queueSnake.add(curPos);
        if (grid[x][y] != 2) {
            pos rem = queueSnake.remove(); // not eat food
            grid[rem.x][rem.y] = 1;
        } else {
            GenerateFoodRandom(); // eat food
        }
        grid[x][y] = 0;
    }
// move a step according to direction, 0-right, 1-down, 2-left, 3-right
}