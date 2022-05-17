package dto;

import java.util.Date;

public class OrderDTO {
    private String orderID;
    private String customerID;
    private Date date;
    private double discount;
    private double total;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, String customerID, Date date, double discount, double total) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.date = date;
        this.discount = discount;
        this.total = total;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderID='" + orderID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", date=" + date +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
