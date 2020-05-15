package ServiceLayer.ServiceObjects;

import java.time.DayOfWeek;
import java.util.List;

public class ContractDTO {

	private List<DayOfWeek> constDayDelivery;
	private int supplierId;
	private boolean isDeliver;

	public ContractDTO(int supplierId, List<DayOfWeek> constDayDeliviery, boolean isDeliver) {
		this.supplierId = supplierId;
		this.constDayDelivery = constDayDeliviery;
		this.isDeliver = isDeliver;
	}

	public List<DayOfWeek> getConstDayDelivery() {
		return constDayDelivery;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public boolean getIsDeliver(){
		return this.isDeliver;
	}

	@Override
	public String toString() {
		return "" + constDayDelivery;
	}
    
}