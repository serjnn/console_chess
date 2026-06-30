package pieces;

import pieces.enums.File;
import java.util.Objects;

public final class Coordinates {
    public final File file;
    public final int rank;

    public Coordinates(File file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public boolean isOutOfBounds() {
        return rank < 1 || rank > 8;
    }

    public int getFileDistance(Coordinates other) {
        return Math.abs(this.file.ordinal() - other.file.ordinal());
    }

    public int getRankDistance(Coordinates other) {
        return Math.abs(this.rank - other.rank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return rank == that.rank && file == that.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "file=" + file +
                ", rank=" + rank +
                '}';
    }
}
