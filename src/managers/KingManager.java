package managers;


import pieces.enums.Color;
import pieces.Coordinates;
import pieces.enums.File;
import pieces.Piece;
import utils.Board;

import java.util.stream.Stream;

import static utils.Game.*;

public class KingManager {


    public static Coordinates whiteKingCoords;
    public static Coordinates blackKingCoords;

    static {
        whiteKingCoords = new Coordinates(File.E, 1);
        blackKingCoords = new Coordinates(File.E, 8);
    }

    private static Coordinates tempBlackKingCoords;
    private static Coordinates tempWhiteKingCoords;
    public static boolean castling;


    public static void rollbackKingCoords(Color color) {
        if (color == Color.BLACK) {
            blackKingCoords = tempBlackKingCoords;
        } else {
            whiteKingCoords = tempWhiteKingCoords;
        }


    }

    public static void mockKingCoords(Color color, Coordinates from, Coordinates to) {
        {
            if (color == Color.BLACK) {
                tempBlackKingCoords = from;
                blackKingCoords = to;
            } else {
                tempWhiteKingCoords = from;
                whiteKingCoords = to;

            }

        }

    }


    public void checkForChangeKingCoords(Piece piece, Coordinates to) {
        if (piece.getClass().getSimpleName().equals("King")) {
            if (piece.color == Color.WHITE) {
                whiteKingCoords = to;
            } else {
                blackKingCoords = to;

            }
            haveWhiteKingMoved = true;

        }

    }

    public void checkForCastling(Piece piece,  Coordinates to, Board board) {
        if (!piece.getClass().getSimpleName().equals("King")) {
            return;
        }
        if (moveColor == Color.WHITE) {
            ableToCastleForWhites(piece, to,  board);
        } else {
            ableToCastleForBlacks(piece, to,  board);
        }
    }

    private void ableToCastleForBlacks(Piece piece,
                                       Coordinates to,
                                       Board board) {

        int fileFrom = piece.coordinates.file.ordinal(),
                fileTo = to.file.ordinal();

        boolean correctFileRotate = fileTo == fileFrom - 2 || fileTo == fileFrom + 2;

        if (haveBlackKingMoved || !correctFileRotate) {
            return;
        }
        boolean isThisShiftToRight = fileTo > fileFrom;

        Piece mockingRook = board.getPiece(new Coordinates(isThisShiftToRight ? File.H : File.A,
                8));
        if (mockingRook == null || !mockingRook.getClass().getSimpleName().equals("Rook")) {
            return;
        }


        boolean isThereEmpty =
                Stream.of(
                                new Coordinates(isThisShiftToRight ? File.values()[fileFrom +1] :
                                        File.values()[fileFrom -1]    ,8),
                                new Coordinates(isThisShiftToRight ? File.values()[fileFrom +2] :
                                        File.values()[fileFrom -2]    ,8),
                                new Coordinates(isThisShiftToRight ? File.values()[fileFrom +2] :
                                        File.values()[fileFrom -3]    ,8)

                        )
                        .allMatch(board::isSquareEmpty);
        if (!isThereEmpty){return;}
        File gottenFile = isThisShiftToRight ? File.values()[fileTo - 1] : File.values()[fileTo + 1];
        board.removePieceFromSquare(mockingRook.coordinates);
        Coordinates coordinates = new Coordinates(gottenFile, 8);

        board.setPiece(coordinates, mockingRook); // set rook
        board.setPiece(to, piece); //set king
        castling = true;


    }

    private void ableToCastleForWhites(Piece piece, Coordinates to, Board board) {

        int fileFrom = piece.coordinates.file.ordinal(),
                fileTo = to.file.ordinal();

        boolean correctFileRotate = fileTo == fileFrom - 2 || fileTo == fileFrom + 2;

        if (haveWhiteKingMoved || !correctFileRotate) {
            return;
        }
        boolean isThisShiftToRight = fileTo > fileFrom;

        Piece mockingRook = board.getPiece(new Coordinates(isThisShiftToRight ? File.H : File.A,
                piece.coordinates.rank));
        if (mockingRook == null || !mockingRook.getClass().getSimpleName().equals("Rook")) {
            return;
        }


        boolean isThereEmpty =
                Stream.of(
                        new Coordinates(isThisShiftToRight ? File.values()[fileFrom +1] :
                                File.values()[fileFrom -1]    ,1),
                        new Coordinates(isThisShiftToRight ? File.values()[fileFrom +2] :
                                File.values()[fileFrom -2]    ,1),
                        new Coordinates(isThisShiftToRight ? File.values()[fileFrom +2] :
                                File.values()[fileFrom -3]    ,1)

                )
                        .allMatch(board::isSquareEmpty);

        if (!isThereEmpty){return;}
        File gottenFile = isThisShiftToRight ? File.values()[fileTo - 1] : File.values()[fileTo + 1];
        board.removePieceFromSquare(mockingRook.coordinates);
        Coordinates coordinates = new Coordinates(gottenFile, piece.coordinates.rank);

        board.setPiece(coordinates, mockingRook); // set rook
        board.setPiece(to, piece); //set king
        castling = true;


    }
}
