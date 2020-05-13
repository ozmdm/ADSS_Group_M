package ServiceLayer.ServiceObjects;

import java.time.DayOfWeek;
import java.util.List;

public class ContractDTO {

	private List<DayOfWeek> constDayDelivery;
	private int supplierId;

	public ContractDTO(int supplierId, List<DayOfWeek> constDayDeliviery) {
		this.supplierId = supplierId;
		this.constDayDelivery = constDayDeliviery;
	}

	public List<DayOfWeek> getConstDayDelivery() {
		return constDayDelivery;
	}

	public int getSupplierId() {
		return supplierId;
	}

	@Override
	public String toString() {
		return "" + constDayDelivery;
	}
    
}