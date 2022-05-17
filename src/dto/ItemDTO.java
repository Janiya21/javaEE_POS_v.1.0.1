package dto;

public class ItemDTO {
    private String itemCode;
    private String itemName;
    private double unitPrice;
    private int qty;

    public ItemDTO(String itemCode, String itemName, double unitPrice, int qty) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                '}';
    }
}
