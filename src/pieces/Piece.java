package pieces;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Piece {
    public Color color;
    public Coordinates coordinates;

    public Piece(Color color, Coordinates coordinates) {
        this.color = color;
        this.coordinates = coordinates;
    }


    public abstract boolean isMoveInvalidForThisType(Coordinates to);

    public abstract List<Coordinates> everyStepToPoint(Coordinates to);

    public List<Coordinates> diagonalMoveSteps(Coordinates to) {
        int fileFrom = this.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = this.coordinates.rank;
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
        return steps.subList(1, steps.size());
    }

    public List<Coordinates> straightMoveSteps(Coordinates to) {
        int fileFrom = this.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = this.coordinates.rank;
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

                    ).toList().subList(1, rankChanges.size());
        } else {
            {
                List<Integer> fileChanges = new ArrayList<>(IntStream.rangeClosed
                                (Math.min(fileFrom, fileTo),
                                        Math.max(fileTo, fileFrom))//
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

    @Override
    public String toString() {
        return "Piece{" +
                "name= " + getClass().getSimpleName() +
                ", color=" + color +
                ", coordinates=" + coordinates +
                '}';
    }

}
