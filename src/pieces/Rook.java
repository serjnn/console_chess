package pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Rook extends Piece {
    public Rook(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    // // any_0 0_any
//    //D8 - D1
//    // == 3_8 - 3_1
    @Override
    protected boolean pieceTypeAbilityToMove(Piece piece, Coordinates to) {

        return piece.coordinates.file == to.file || piece.coordinates.rank == to.rank;
    }

    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to) throws RuntimeException {
        if (!pieceTypeAbilityToMove(piece,to)) throw new RuntimeException(
                "rook does not move like that");

        int fileFrom = piece.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = piece.coordinates.rank;
        int rankTo = to.rank;
        if (fileFrom == fileTo) {
            List<Integer> rankChanges = new ArrayList<>(IntStream.rangeClosed
                            (Math.min(rankFrom, rankTo), Math.max(rankTo, rankFrom))
                    .boxed().toList());
            if (rankFrom > rankTo) {
                Collections.reverse(rankChanges);

            }
            return rankChanges.stream()
                    .map(rank -> new Coordinates(File.values()[fileFrom], rank)

                    ).toList();
        }
        else {
            {
                List<Integer> fileChanges = new ArrayList<>(IntStream.rangeClosed
                                (Math.min(fileFrom, fileTo), Math.max(fileTo, fileFrom) )//
                        .boxed().toList());
                if (fileFrom > fileTo) {
                    Collections.reverse(fileChanges);

                }
                return fileChanges.stream()
                        .map(file -> new Coordinates(File.values()[file], rankFrom)

                        ).toList().subList(1, fileChanges.size());
            }
        }
    }


}
