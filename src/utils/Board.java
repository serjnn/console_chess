package utils;

import pieces.*;
import pieces.enums.Color;
import pieces.enums.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Coordinates, Piece> map = new HashMap<>();

    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.coordinates = coordinates;
        map.put(coordinates, piece);
    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        return !map.containsKey(coordinates);
    }

    public Piece getPiece(Coordinates coordinates) {
        return map.get(coordinates);
    }

    public void setupDefault() {
        //set pawns
        for (File file : File.values()) {
            setPiece(new Coordinates(file, 2), new Pawn(Color.WHITE, new Coordinates(file, 2)));
            setPiece(new Coordinates(file, 7), new Pawn(Color.BLACK, new Coordinates(file, 7)));
        }

        //rooks
        setPiece(new Coordinates(File.A, 1), new Rook(Color.WHITE, new Coordinates(File.A, 1)));
        setPiece(new Coordinates(File.H, 1), new Rook(Color.WHITE, new Coordinates(File.H, 1)));
        setPiece(new Coordinates(File.A, 8), new Rook(Color.BLACK, new Coordinates(File.A, 8)));
        setPiece(new Coordinates(File.H, 8), new Rook(Color.BLACK, new Coordinates(File.H, 8)));

        //knights
        setPiece(new Coordinates(File.B, 1), new Knight(Color.WHITE, new Coordinates(File.B, 1)));
        setPiece(new Coordinates(File.G, 1), new Knight(Color.WHITE, new Coordinates(File.G, 1)));
        setPiece(new Coordinates(File.B, 8), new Knight(Color.BLACK, new Coordinates(File.B, 8)));
        setPiece(new Coordinates(File.G, 8), new Knight(Color.BLACK, new Coordinates(File.G, 8)));

        //bishops
        setPiece(new Coordinates(File.C, 1), new Bishop(Color.WHITE, new Coordinates(File.C, 1)));
        setPiece(new Coordinates(File.F, 1), new Bishop(Color.WHITE, new Coordinates(File.F, 1)));
        setPiece(new Coordinates(File.C, 8), new Bishop(Color.BLACK, new Coordinates(File.C, 8)));
        setPiece(new Coordinates(File.F, 8), new Bishop(Color.BLACK, new Coordinates(File.F, 8)));

        //queens
        setPiece(new Coordinates(File.D, 1), new Queen(Color.WHITE, new Coordinates(File.D, 1)));
        setPiece(new Coordinates(File.D, 8), new Queen(Color.BLACK, new Coordinates(File.D, 8)));

        //kings
        setPiece(new Coordinates(File.E, 1), new King(Color.WHITE, new Coordinates(File.E, 1)));
        setPiece(new Coordinates(File.E, 8), new King(Color.BLACK, new Coordinates(File.E, 8)));
    }

    public void removePieceFromSquare(Coordinates coordinates) {
        map.remove(coordinates);
    }

    public boolean isSquareWhite(Coordinates coordinates) {
        return ((coordinates.file.ordinal() + 1) % 2 == 0 && coordinates.rank % 2 == 0) || (
                (coordinates.file.ordinal() + 1) % 2 != 0 && coordinates.rank % 2 != 0
        );
    }

    public boolean isKingUnderCheck(Color kingColor) {
        Coordinates kingCoords = null;
        for (Map.Entry<Coordinates, Piece> entry : map.entrySet()) {
            Piece piece = entry.getValue();
            if (piece instanceof King && piece.color == kingColor) {
                kingCoords = entry.getKey();
                break;
            }
        }
        if (kingCoords == null) {
            return false;
        }

        for (Map.Entry<Coordinates, Piece> entry : map.entrySet()) {
            Piece piece = entry.getValue();
            if (piece.color != kingColor) {
                if (canPieceAttackSquare(piece, kingCoords)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canPieceAttackSquare(Piece piece, Coordinates target) {
        if (piece.coordinates.equals(target)) {
            return false;
        }
        if (target.isOutOfBounds()) {
            return false;
        }
        if (piece instanceof Pawn) {
            int fileFrom = piece.coordinates.file.ordinal();
            int fileTo = target.file.ordinal();
            int rankFrom = piece.coordinates.rank;
            int rankTo = target.rank;
            boolean fileCheck = fileTo == fileFrom - 1 || fileTo == fileFrom + 1;
            boolean rankCheck = piece.color == Color.WHITE ? rankTo == rankFrom + 1 : rankTo == rankFrom - 1;
            return fileCheck && rankCheck;
        }

        if (!piece.isMoveValidForThisType(target)) {
            return false;
        }

        List<Coordinates> steps = piece.everyStepToPoint(target);
        for (int i = 0; i < steps.size() - 1; i++) {
            if (!isSquareEmpty(steps.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean wouldMovePutKingInCheck(Coordinates from, Coordinates to, Color playerColor) {
        Piece movingPiece = getPiece(from);
        Piece originalTargetPiece = getPiece(to);

        // Simulate move
        map.remove(from);
        map.put(to, movingPiece);
        Coordinates originalCoords = movingPiece.coordinates;
        movingPiece.coordinates = to;

        // Castling rook simulation
        boolean isCastling = false;
        Coordinates rookFrom = null;
        Coordinates rookTo = null;
        Piece rook = null;
        if (movingPiece instanceof King && Math.abs(from.file.ordinal() - to.file.ordinal()) == 2) {
            isCastling = true;
            boolean isRight = to.file.ordinal() > from.file.ordinal();
            int rank = from.rank;
            rookFrom = new Coordinates(isRight ? File.H : File.A, rank);
            rookTo = new Coordinates(isRight ? File.F : File.D, rank);
            rook = getPiece(rookFrom);
            if (rook != null) {
                map.remove(rookFrom);
                map.put(rookTo, rook);
                rook.coordinates = rookTo;
            }
        }

        boolean inCheck = isKingUnderCheck(playerColor);

        // Rollback
        movingPiece.coordinates = originalCoords;
        map.put(from, movingPiece);
        if (originalTargetPiece != null) {
            map.put(to, originalTargetPiece);
        } else {
            map.remove(to);
        }

        if (isCastling && rook != null) {
            rook.coordinates = rookFrom;
            map.put(rookFrom, rook);
            map.remove(rookTo);
        }

        return inCheck;
    }

    public void commitMove(Coordinates from, Coordinates to) {
        Piece piece = getPiece(from);
        if (piece == null) return;

        removePieceFromSquare(from);

        // Castling rook movement
        if (piece instanceof King && Math.abs(from.file.ordinal() - to.file.ordinal()) == 2) {
            boolean isRight = to.file.ordinal() > from.file.ordinal();
            int rank = from.rank;
            Coordinates rookFrom = new Coordinates(isRight ? File.H : File.A, rank);
            Coordinates rookTo = new Coordinates(isRight ? File.F : File.D, rank);
            Piece rook = map.remove(rookFrom);
            if (rook != null) {
                rook.coordinates = rookTo;
                rook.hasMoved = true;
                map.put(rookTo, rook);
            }
        }

        piece.coordinates = to;
        piece.hasMoved = true;
        map.put(to, piece);

        // Pawn promotion to Queen
        if (piece instanceof Pawn && (to.rank == 1 || to.rank == 8)) {
            Piece promotedQueen = new Queen(piece.color, to);
            promotedQueen.hasMoved = true;
            map.put(to, promotedQueen);
        }
    }
}
