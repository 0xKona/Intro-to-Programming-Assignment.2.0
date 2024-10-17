package models;

import storage.ItemStorage;

/**
 * Represents an item in stock with its unique ID, name, unit price, quantity in stock,
 * and the total price based on the current quantity and unit price.
 *
 * <p>The class provides methods to access and update the item's details, while automatically
 * calculating the total price when either the unit price or quantity in stock is updated.</p>
 *
 * <p>The total price is calculated as the product of the unit price and the quantity in stock,
 * and is automatically updated when either the unit price or the quantity changes.
 * If the unit price or quantity is set to a non-positive value, the total price is set to 0.0.</p>
 */
public class Item {
    private int id;
    private String name;

    // Initialize these variables as doubles as the price can include a decimal.
    private double unitPrice;
    private double qtyInStock;
    private double totalValue;

    private double previousQuantityInStock;
    private double previousTotalValue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        // Total price is always updated whenever unitPrice or qtyInStock is updated.
        calculateTotalPrice();
    }

    public double getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(double qtyInStock) {
        // Save copy of previous values when updating to calculate change when generating a transaction.
        this.previousQuantityInStock = this.qtyInStock;
        this.previousTotalValue = this.totalValue;
        this.qtyInStock = qtyInStock;
        // Total price is always updated whenever unitPrice or qtyInStock is updated.
        calculateTotalPrice();
    }

    public double getTotalValue() {
        return totalValue;
    }

    public double getPreviousQuantityInStock() {
        return previousQuantityInStock;
    }

    public double getPreviousTotalValue() {
        return previousTotalValue;
    }

    // Validate unitPrice and qtyInStock before updating totalValue
    private void calculateTotalPrice() {
        if (this.unitPrice > 0 && this.qtyInStock > 0) {
            this.totalValue = this.unitPrice * this.qtyInStock;
        } else {
            this.totalValue = 0.0;
        }
    }

    public void submitNewItem() {
        Item thisItem = this;
        ItemStorage.writeNewItemToStorage(thisItem);
        Transaction transaction = new Transaction();
        transaction.generate(thisItem, TransactionType.ADDED);
    }

    public void submitUpdatedItem() {
        Item thisItem = this;
        ItemStorage.updateItem(thisItem);
        Transaction transaction = new Transaction();
        transaction.generate(thisItem, TransactionType.UPDATED);
    }

    public void submitDeleteItem() {
        Item thisItem = this;
        ItemStorage.deleteItem(thisItem);
        Transaction transaction = new Transaction();
        transaction.generate(thisItem, TransactionType.REMOVED);
    }


}
