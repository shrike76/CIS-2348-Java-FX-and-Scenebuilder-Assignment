public class Grocery extends Item implements Tax, Discount{

    public Grocery(int itemCode_, String Name_, int units_, double price_, String brand_, double discount_, String category_){
        super(itemCode_, Name_, brand_, units_, price_,  discount_, category_);
        super.setTotalAmount(computeTotalPrice());
        super.setDiscountAmount(computeDiscount());
        super.setTaxes(computeTax());
    }

    public double computeTotalPrice(){
        int units = getUnits();
        double discount = getDiscount();
        double price = getPrice();
        return price*units-computeDiscount();
    }

    public double computeTax(){ return 0;}

    public double computeDiscount(){
        int units = getUnits();
        double discount = getDiscount();
        double price = getPrice();

        if (discount==0) {
            return 0;
        }
        discount=units*(price * (discount/100));
        return discount;
    }
}
