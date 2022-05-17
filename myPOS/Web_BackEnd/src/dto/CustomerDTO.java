package dto;

public class CustomerDTO {
    private String customerId;
    private String customerName;
    private String email;
    private int telNo;

    public CustomerDTO(String customerId, String customerName, String email, int telNo) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.telNo = telNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelNo() {
        return telNo;
    }

    public void setTelNo(int telNo) {
        this.telNo = telNo;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", telNo=" + telNo +
                '}';
    }
}
