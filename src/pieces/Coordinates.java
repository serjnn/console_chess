package pieces;

import java.util.Objects;

public class Coordinates {
    public final File file;
    public final int rank;


    public Coordinates(File file, int rank) {
        this.file = file;
        this.rank = rank;
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
