package utils;

import managers.KingManager;
import managers.PawnManager;

public class Main {
    public static void main(String[] args) {

        KingManager kingManager = new KingManager();
        PawnManager pawnManager = new PawnManager();

        Board board = new Board();
        board.setupDefault();
        Game game = new Game(kingManager, pawnManager);

        game.gameLoop(board);

    }

}