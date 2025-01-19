package managers;

import pieces.enums.Color;
import pieces.Coordinates;
import pieces.Piece;
import pieces.Queen;
import utils.Board;


public class PawnManager {

    public static boolean pawnPeacefulMove;

    public void checkForUpgrade(Piece piece, Coordinates to, Board board) {
        if (!piece.getClass().getSimpleName().equals("Pawn")) {
            return;
        }
        if (to.rank == 8 || to.rank == 1) {
            board.setPiece(to, new Queen(piece.color, to));

        }


    }


    public void checkForPeacefulMove(Piece piece, Coordinates to, Board board) {
        if (piece.getClass().getSimpleName().equals("Pawn")
                && canPawnPeacefullyMove(piece, to, board)) {
            pawnPeacefulMove = true;


        }

    }

    private boolean canPawnPeacefullyMove(Piece piece, Coordinates to, Board board) {


        int fileFrom = piece.coordinates.file.ordinal(),
                fileTo = to.file.ordinal(),
                rankFrom = piece.coordinates.rank,
                rankTo = to.rank;
        Color color = piece.color;
        int possibleDistance = rankFrom == 2 || rankFrom == 7 ? 2 : 1;


        boolean emptySquareCheck = board.isSquareEmpty(to);
        boolean emptyNextSquareCheck =
                color == Color.WHITE ?
                        board.isSquareEmpty(new Coordinates(piece.coordinates.file,
                                piece.coordinates.rank + 1)) :
                        board.isSquareEmpty(new Coordinates(piece.coordinates.file,
                                piece.coordinates.rank - 1));

        boolean fileCheck = fileTo == fileFrom;
        boolean rankCheck = color == Color.WHITE ?
                rankTo == rankFrom + possibleDistance ||
                        rankTo == rankFrom + 1 :
                rankTo == rankFrom - possibleDistance
                        || rankTo == rankFrom - 1;
        return fileCheck && rankCheck && emptyNextSquareCheck
                && emptySquareCheck;


    }


}
