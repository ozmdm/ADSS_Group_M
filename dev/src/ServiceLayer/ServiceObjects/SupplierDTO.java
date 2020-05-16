package ServiceLayer.ServiceObjects;



import java.util.List;

public class SupplierDTO {
    public enum billingOption {EOM30, EOM60, CASH, BANKTRANSFER, CHECK}

    private billingOption billingOption;
    private String name;
    private int supplierId;
    private int bankAccountNumber;
    private ContractDTO contractDTO;
    private List<ContactDTO> contactDTOS;

    public SupplierDTO(int supplierId, String name, billingOption billingOption, int bankAccountNumber, ContractDTO contractDTO, List<ContactDTO> contactDTOS) {
        this.supplierId = supplierId;
        this.name = name;
        this.billingOption = billingOption;
        this.bankAccountNumber = bankAccountNumber;
        this.contactDTOS = contactDTOS;
        this.contractDTO = contractDTO;
    }

    public billingOption getBillingOption() {
        return billingOption;
    }

    public String getName() {
        return name;
    }

    public int getSupplierId() {
        return supplierId;
    }

    @Override
    public String toString() {
        return "\n" + supplierId + "\t" + name + "\t" + billingOption;
    }

    public int getBankAccountNumber() {
        return this.bankAccountNumber;
    }

    /**
     * @return the contactDTOS
     */
    public List<ContactDTO> getContactDTOS() {
        return contactDTOS;
    }

    /**
     * @return the contractDTO
     */
    public ContractDTO getContractDTO() {
        return contractDTO;
    }

}