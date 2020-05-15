import java.util.Comparator;

public class StringLengthComparator implements Comparator<String> {
    private int _referenceLength;

    public StringLengthComparator(String reference) {
        super();
        _referenceLength = reference.length();
    }

    public int compare(String s1, String s2) {
        int dist1 = Math.abs(s1.length() - _referenceLength);
        int dist2 = Math.abs(s2.length() - _referenceLength);

        return dist2 - dist1;
    }
}
