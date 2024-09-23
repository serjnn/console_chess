package utils;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        board.setupDefault();
        Game game = new Game();
        game.gameLoop(board);

    }

}