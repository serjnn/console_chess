package utils;

import managers.KingManager;
import managers.Manager;
import managers.PawnManager;

public class Main {
    public static void main(String[] args) {

        Manager kingManager = new KingManager();
        Manager pawnManager = new PawnManager();

        Board board = new Board();
        board.setupDefault();
        Game game = new Game();
        game.registerManager(kingManager);
        game.registerManager(pawnManager);
        game.gameLoop(board);

    }

}