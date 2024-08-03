package pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Bishop extends Piece {
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    protected boolean pieceTypeAbilityToMove(Piece piece, Coordinates to) {
        return Math.abs(piece.coordinates.file.ordinal() - to.file.ordinal())
                == Math.abs(piece.coordinates.rank - to.rank);
    }


    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to)
            throws RuntimeException {
        if (!pieceTypeAbilityToMove(piece, to)) throw new RuntimeException(
                "bishop does not move like that");
        int fileFrom = piece.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = piece.coordinates.rank;
        int rankTo = to.rank;

        List<Integer> rankChanges = new ArrayList<>(IntStream.rangeClosed
                        (Math.min(rankFrom, rankTo), Math.max(rankTo, rankFrom))
                .boxed().toList());

        List<Integer> fileChanges = new ArrayList<>(IntStream.rangeClosed
                        (Math.min(fileFrom, fileTo), Math.max(fileTo, fileFrom))//
                .boxed().toList());

        if (rankFrom > rankTo) {
            Collections.reverse(rankChanges);
            Collections.reverse(fileChanges);

        }
        List<Coordinates> steps = new ArrayList<>();
        for (int i = 0; i < fileChanges.size(); i++) {
            steps.add(
                    new Coordinates(File.values()[fileChanges.get(i)],
                            rankChanges.get(i))
            );

        }
        return steps.subList(1,steps.size());
    }

}
