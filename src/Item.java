public class Item {
    private int itemCode;
    private String name;
    private int units;
    private double price;
    private double discount;
    private String brand;
    private String category;
    private double taxes;
    private double discountAmount;
    private double totalAmount;

    public Item(int itemCode_, String name_, String brand_, int units_, double price_, double discount_, String category_){
        itemCode = itemCode_;
        name = name_;
        brand = brand_;
        units = units_;
        price = price_;
        discount = discount_;
        category = category_;
    }

    public int getItemCode(){
        return itemCode;
    }

    public String getName(){
        return name;
    }

    public int getUnits(){
        return units;
    }

    public String getBrand(){
        return brand;
    }

    public double getPrice(){
        return price;
    }

    public double getDiscount(){
        return discount;
    }

    public String getCategory(){
        return category;
    }

    public double getTaxes(){
        return taxes;
    }

    public double getDiscountAmount(){
        return discountAmount;
    }

    public double getTotalAmount(){
        return totalAmount;
    }

    public void setDiscountAmount(double discountAmount_) {
        discountAmount = discountAmount_;
    }

    public void setTotalAmount(double totalAmount_) {
        totalAmount = totalAmount_;
    }

    public void setTaxes(double taxes_) {
        taxes = taxes_;
    }

    public String getReceiptItemText() { //formats the output on to the window: 20 means padding (like moving the character spaces to the right) and so on
        return String.format("%20s%20d%20s%20d%20.2f%25.2f\r\n", category, itemCode, name, units, discountAmount, totalAmount);
    }
}
