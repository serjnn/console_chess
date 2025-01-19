import managers.KingManager;
import managers.PawnManager;
import utils.Board;
import utils.Game;

public class Main {
    public static void main(String[] args) {

        KingManager kingManager = new KingManager();
        PawnManager pawnManager = new PawnManager();

        Board board = new Board();
        board.setupDefault();
        Game game = new Game(kingManager, pawnManager);

        game.gameLoop(board);

    }
    //TODO add everyone to active if king steps over middle of desc
    //TODO remove redundant piece and from in methods' params

}