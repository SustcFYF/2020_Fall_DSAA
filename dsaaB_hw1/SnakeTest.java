//package homework1;

import java.util.Scanner;
public class SnakeTest {
    public static void main(String[] args) {
// TODO Auto-generated method stub
        Snake snake = new Snake(20, 10);
        snake.StartSnake(3, 3);
        snake.GenerateFoodRandom();
        snake.ShowSnake();
        int i = 0;
        Scanner sc = new Scanner(System.in);
        while (i < 1000) {
            System.out.println("the snake is going to move to (1:right, 2:down, 3:left, 4:up):");
            int dir = sc.nextInt();
            if (i % (4 * snake.length) == 0) snake.GenerateStoneRandom();
            snake.Move(dir);
            snake.ShowSnake();
            i++;
        }
        sc.close();
    }
}