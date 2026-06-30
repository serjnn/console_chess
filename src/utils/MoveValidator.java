package utils;

import pieces.*;
import pieces.enums.Color;
import pieces.enums.File;
import exceptions.ChessException;
import java.util.List;

public class MoveValidator {

    public static void validateMove(Board board, Color currentTurnColor, Coordinates from, Coordinates to) throws ChessException {
        if (from.equals(to)) {
            throw new ChessException("Move destination must be different from origin");
        }

        if (from.isOutOfBounds() || to.isOutOfBounds()) {
            throw new ChessException("You can't go outside the map");
        }

        Piece piece = board.getPiece(from);
        if (piece == null) {
            throw new ChessException("There is empty from where you wanna move");
        }

        if (piece.color != currentTurnColor) {
            throw new ChessException("That piece isn't yours");
        }

        Piece target = board.getPiece(to);
        if (target != null && target.color == currentTurnColor) {
            throw new ChessException("You can't beat your own pieces");
        }

        if (piece instanceof Knight) {
            if (!piece.isMoveValidForThisType(to)) {
                throw new ChessException("Knight can't move like that");
            }
        } else if (piece instanceof Bishop) {
            if (!piece.isMoveValidForThisType(to)) {
                throw new ChessException("Bishop can't move like that");
            }
            if (!isPathClear(board, from, to)) {
                throw new ChessException("You cant go through piece");
            }
        } else if (piece instanceof Rook) {
            if (!piece.isMoveValidForThisType(to)) {
                throw new ChessException("Rook can't move like that");
            }
            if (!isPathClear(board, from, to)) {
                throw new ChessException("You cant go through piece");
            }
        } else if (piece instanceof Queen) {
            if (!piece.isMoveValidForThisType(to)) {
                throw new ChessException("Queen can't move like that");
            }
            if (!isPathClear(board, from, to)) {
                throw new ChessException("You cant go through piece");
            }
        } else if (piece instanceof King) {
            boolean isCastling = isCastlingAttempt(piece, to);
            if (isCastling) {
                validateCastling(board, piece, to);
            } else {
                if (!piece.isMoveValidForThisType(to)) {
                    throw new ChessException("King can't move like that");
                }
            }
        } else if (piece instanceof Pawn) {
            validatePawnMove(board, (Pawn) piece, to);
        }
    }

    private static boolean isPathClear(Board board, Coordinates from, Coordinates to) {
        Piece piece = board.getPiece(from);
        List<Coordinates> steps = piece.everyStepToPoint(to);
        for (int i = 0; i < steps.size() - 1; i++) {
            if (!board.isSquareEmpty(steps.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCastlingAttempt(Piece king, Coordinates to) {
        return king.coordinates.getFileDistance(to) == 2 && king.coordinates.getRankDistance(to) == 0;
    }

    private static void validateCastling(Board board, Piece king, Coordinates to) throws ChessException {
        if (king.hasMoved) {
            throw new ChessException("King has already moved");
        }

        int rank = king.coordinates.rank;
        boolean isRight = to.file.ordinal() > king.coordinates.file.ordinal();
        Coordinates rookCoords = new Coordinates(isRight ? File.H : File.A, rank);
        Piece rook = board.getPiece(rookCoords);

        if (rook == null || !(rook instanceof Rook)) {
            throw new ChessException("Rook is missing or not a Rook");
        }
        if (rook.hasMoved) {
            throw new ChessException("Rook has already moved");
        }

        int fileFrom = king.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int startFile = Math.min(fileFrom, rookCoords.file.ordinal()) + 1;
        int endFile = Math.max(fileFrom, rookCoords.file.ordinal()) - 1;

        for (int f = startFile; f <= endFile; f++) {
            Coordinates intermediate = new Coordinates(File.values()[f], rank);
            if (!board.isSquareEmpty(intermediate)) {
                throw new ChessException("You cant go through piece");
            }
        }
    }

    private static void validatePawnMove(Board board, Pawn pawn, Coordinates to) throws ChessException {
        int fileFrom = pawn.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = pawn.coordinates.rank;
        int rankTo = to.rank;
        Color color = pawn.color;

        boolean isStraight = (fileFrom == fileTo);
        boolean isDiagonal = (fileTo == fileFrom - 1 || fileTo == fileFrom + 1);

        if (isStraight) {
            int direction = (color == Color.WHITE) ? 1 : -1;
            boolean canDoubleMove = !pawn.hasMoved;

            if (rankTo == rankFrom + direction) {
                if (!board.isSquareEmpty(to)) {
                    throw new ChessException("This pawn can't move that way");
                }
            } else if (canDoubleMove && rankTo == rankFrom + 2 * direction) {
                Coordinates intermediate = new Coordinates(pawn.coordinates.file, rankFrom + direction);
                if (!board.isSquareEmpty(intermediate) || !board.isSquareEmpty(to)) {
                    throw new ChessException("This pawn can't move that way");
                }
            } else {
                throw new ChessException("This pawn can't move that way");
            }
        } else if (isDiagonal) {
            int direction = (color == Color.WHITE) ? 1 : -1;
            if (rankTo == rankFrom + direction) {
                Piece target = board.getPiece(to);
                if (target == null) {
                    throw new ChessException("You can't move like that because pawn is not attacking");
                }
                if (target.color == color) {
                    throw new ChessException("You can't beat your own pieces");
                }
            } else {
                throw new ChessException("This pawn can't move that way");
            }
        } else {
            throw new ChessException("This pawn can't move that way");
        }
    }
}
