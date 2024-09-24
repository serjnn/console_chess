package managers;


import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;
import utils.Board;

import static utils.Game.moveColor;

public class KingManager implements Manager {


    public static Coordinates whiteKingCoords;
    public static Coordinates blackKingCoords;

    static {
        whiteKingCoords = new Coordinates(File.C, 3);
        blackKingCoords = new Coordinates(File.C, 6);
    }

    private static Coordinates tempBlackKingCoords;
    private static Coordinates tempWhiteKingCoords;

    @Override
    public void processMove(Piece piece, Coordinates from, Coordinates to, Board board) {
        if (piece.getClass().getSimpleName().equals("King")) {
            if (moveColor == Color.WHITE) {
                whiteKingCoords = to;
            } else {
                blackKingCoords = to;

            }

        }


    }

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


}
