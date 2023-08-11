import java.util.Objects;

public class Pair<T extends Comparable<T>, V extends Comparable<V>> implements Comparable<Pair<T,V>>{
    T first;
    V second;
    Pair(T fi, V se){
        first = fi;
        second = se;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(first, other.first) && Objects.equals(second, other.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public int compareTo(Pair<T, V> o) {
        int firstComparison = this.first.compareTo(o.first);
        if(firstComparison != 0) return firstComparison;
        else return this.second.compareTo(o.second);
    }
}
