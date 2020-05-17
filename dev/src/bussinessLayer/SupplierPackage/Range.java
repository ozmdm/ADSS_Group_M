package bussinessLayer.SupplierPackage;

import ServiceLayer.ServiceObjects.RangeDTO;

public class Range {
    private int max;
    private int min;

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public Range(RangeDTO RangeDTO) {
        this.max= RangeDTO.getMax();
        this.min = RangeDTO.getMin();
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
