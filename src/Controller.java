import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Controller{ //these connect to the ids in the fxml
    public Pane addPane = new Pane();
    public Pane removePane = new Pane();
    public Pane updatePane = new Pane();
    public ComboBox categoryselection = new ComboBox();
    public TextField itemCode = new TextField();
    public TextField removeItemCode = new TextField();
    public TextField updateItemCode = new TextField();
    public TextField itemName = new TextField();
    public TextField brand = new TextField();
    public TextField quantity = new TextField();
    public TextField price = new TextField();
    public TextField discountedAmount = new TextField();
    public Button addItemButton = new Button();
    public Button updateItemButton = new Button();
    public Label receiptLabel = new Label();
    public Label printTotal = new Label();
    public CheckBox saleDay = new CheckBox();
    private ArrayList<Item> cart = new ArrayList<>();


    @FXML
    private void toggleAddPane(ActionEvent event) { //makes add pane visible
        addItemButton.setVisible(true);
        updateItemButton.setVisible(false);
        itemCode.setDisable(false);
        clearItems(); //function to clear text fields
        hideAllPanes(); //function to reset panes
        addPane.setVisible(true); //adds back add pane
    }

    @FXML
    private void toggleRemovePane(ActionEvent event) { //makes remove pane visible
        hideAllPanes();
        removePane.setVisible(true);
    }

    @FXML
    private void toggleUpdatePane(ActionEvent event) { //makes update pane visible
        hideAllPanes();
        updatePane.setVisible(true);
    }



    private void hideAllPanes() { //resets panes
        addPane.setVisible(false);
        removePane.setVisible(false);
        updatePane.setVisible(false);
    }


    @FXML
    private void initialize(){ //initializes data
        categoryselection.getItems().addAll("Clothing", "Groceries", "Accessories", "Electronics", "Utensils", "Furniture"); //adds string to the combo box
        categoryselection.getSelectionModel().select("Clothing"); //selects the string you want otherwise its blank
    }

    @FXML
    private void addItem(){
        String category = categoryselection.getValue().toString(); //turns combo box selection into a property which is called later

        int code=Integer.parseInt(itemCode.getText()); //converts field text String to int
        int quantity2=Integer.parseInt(quantity.getText());
        double price2=Double.parseDouble(price.getText());
        double discount2=Double.parseDouble(discountedAmount.getText());

        switch(category) { //calls the combo box selection made from category property and goes to the category type case by case
            case "Clothing":
                Clothing clothing = new Clothing(code, itemName.getText(), quantity2, price2, brand.getText(), discount2, category); //takes info from text fields and makes an object
                cart.add(clothing); //adds object to array list cart
                break;
            case "Groceries":
                Grocery grocery = new Grocery(code, itemName.getText(), quantity2, price2, brand.getText(), discount2, category);
                cart.add(grocery);
                break;
            case "Accessories":
                Accessories accessories = new Accessories(code, itemName.getText(), quantity2, price2, brand.getText(), discount2, category);
                cart.add(accessories);
                break;
            case "Electronics":
                electronics electronics = new electronics(code, itemName.getText(), quantity2, price2, brand.getText(), discount2, category);
                cart.add(electronics);
                break;
            case "Utensils":
                utensils utensils = new utensils(code, itemName.getText(), quantity2, price2, brand.getText(), discount2, category);
                cart.add(utensils);
                break;
            case "Furniture":
                furniture furniture = new furniture(code, itemName.getText(), quantity2, price2, brand.getText(), discount2, category);
                cart.add(furniture);
                break;
        }
        updateReceipt();
        clearItems();
    }

    private void clearItems(){ //clears text fields
        itemCode.clear();
        itemName.clear();
        brand.clear();
        quantity.clear();
        price.clear();
        discountedAmount.clear();
        removeItemCode.clear();
        updateItemCode.clear();
    }

    @FXML
    private void removeItem(){
        int code = Integer.parseInt(removeItemCode.getText()); //converts to int
        cart.removeIf(item -> item.getItemCode() == code); //lambda expresion to remove object from array if item code equals field text code
        updateReceipt();
        clearItems();
    }

    @FXML
    private void updatePopulateItem(){
        int code = Integer.parseInt(updateItemCode.getText());
        Item existingItem = cart.stream().filter(item -> item.getItemCode() == code).findFirst().orElse(null); //takes array list and searches for an object with item code given then goes to update pane
        if (existingItem != null)
        {
            categoryselection.getSelectionModel().select(existingItem.getCategory()); //puts in info from object into field texts
            itemCode.setText(Integer.toString(existingItem.getItemCode()));
            itemCode.setDisable(true);
            itemName.setText(existingItem.getName());
            brand.setText(existingItem.getBrand());
            quantity.setText(Integer.toString(existingItem.getUnits()));
            price.setText(Double.toString(existingItem.getPrice()));
            discountedAmount.setText(Double.toString(existingItem.getDiscount()));

            addItemButton.setVisible(false);
            updateItemButton.setVisible(true);
            hideAllPanes();
            addPane.setVisible(true);
        }
        else{
            clearItems();
        }
    }

    @FXML
    private void updateItem(){ //removes the existing object and replaces it with updated object
        int code = Integer.parseInt(itemCode.getText());
        Item existingItem = cart.stream().filter(item -> item.getItemCode() == code).findFirst().orElse(null);
        cart.remove(existingItem);
        addItem();
        hideAllPanes();
    }


    private void updateReceipt(){
        String receipt = String.format("%-20s %-20s %-20s %-20s %-20s %-20s \n", "Category", "Code", "Name", "Units", "Discount", "Total"); //header for label

        for(Item item : cart) { //for each statement to set receipt equal to object.getreceiptitemtext
            receipt += item.getReceiptItemText();
        }
        receiptLabel.setText(receipt); //sets label to receipt
    }


    @FXML
    public void printTotal()
    {
        boolean isSaleDay = saleDay.isSelected();
        double subTotal = 0;
        double tax = 0;
        double total = 0;
        double totalDiscount = 0;

        for(Item item : cart)
        {
            double saleDayDiscount = 0;
            subTotal += item.getTotalAmount();
            tax += item.getTaxes();
            if (isSaleDay && !item.getCategory().equals("Electronics")){
                saleDayDiscount = item.getTotalAmount() * .25;
            }
            totalDiscount += item.getDiscount() + saleDayDiscount;
        }
        total = subTotal + tax - totalDiscount;

        String totals = String.format("Subtotal: $%.2f   Today's discount: $%.2f   Tax: $%.2f   Total: $%.2f", subTotal, totalDiscount, tax, total);
        printTotal.setText(totals);
    }
}