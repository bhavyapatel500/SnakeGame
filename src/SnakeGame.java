import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        Board board = new Board();
        add(board);
        pack();
        setResizable(false);
        setTitle("Snake Game");
        setVisible(true);
    }
    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
    }
}