package Supplier;

public class Range {
    private int max;
    private int min;

    public Range (int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
