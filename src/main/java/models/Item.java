package models;

import storage.ItemStorage;

/**
 * Represents an item in stock with its unique ID, name, unit price, quantity in stock,
 * and the total price based on the current quantity and unit price.
 *
 * <p>The total price is calculated as the product of the unit price and the quantity in stock,
 * and is automatically updated when either the unit price or the quantity changes.
 * If the unit price or quantity is set to a non-positive value, the total price is set to 0.0.</p>
 *
 * <p>Methods:</p>
 * <li>{@link #getId()}</li>
 * <li>{@link #getName()}</li>
 * <li>{@link #setName(String)}</li>
 * <li>{@link #getUnitPrice()}</li>
 * <li>{@link #setUnitPrice(double)}</li>
 * <li>{@link #getQtyInStock()}</li>
 * <li>{@link #setQtyInStock(double)}</li>
 * <li>{@link #getTotalValue()}</li>
 * <li>{@link #getPreviousQuantityInStock()}</li>
 * <li>{@link #getPreviousTotalValue()}</li>
 * <li>{@link #submitNewItem()}</li>
 * <li>{@link #submitUpdatedItem()}</li>
 * <li>{@link #submitDeleteItem()}</li>
 */
public class Item {
    private String id;
    private String name;

    // Initialize these variables as doubles as the price can include a decimal.
    private double unitPrice;
    private double qtyInStock;
    private double totalValue;

    private double previousQuantityInStock;
    private double previousTotalValue;

    /**
     * Returns the Item objects ID
     * @return int ID
     */
    public String getId() {
        return id;
    }

    /**
     * Manually update the Item objects ID
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the Item objects name as a String
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Manually update the Item objects name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Item objects unit price as a double
     * @return double
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Manually update the Item object's unit price, also updates totalValue.
     * @param unitPrice double
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        calculateTotalValue(); // Total price is always updated whenever unitPrice or qtyInStock is updated.
    }

    /**
     * Returns the Item objects quantity as a double
     * @return double
     */
    public double getQtyInStock() {
        return qtyInStock;
    }

    /**
     * Sets the Item objects quantity, also sets the previous variable for use when updating or deleting the object.
     * The totalValue is also recalculated
     * @param qtyInStock double
     */
    public void setQtyInStock(double qtyInStock) {
        // Save copy of previous values when updating to calculate change when generating a transaction.
        this.previousQuantityInStock = this.qtyInStock;
        this.previousTotalValue = this.totalValue;

        this.qtyInStock = qtyInStock; // update the value
        calculateTotalValue(); // Total price is always updated whenever unitPrice or qtyInStock is updated.
    }

    /**
     * Gets the totalValue and returns it. No public setter for this value as it should only update internally and should
     * never be altered manually.
     * @return double
     */
    public double getTotalValue() {
        return totalValue;
    }

    /**
     * Gets the previous quantity in stock, No public setter for this value as it should only update internally and should
     * never be altered manually.
     * @return double
     */
    public double getPreviousQuantityInStock() {
        return previousQuantityInStock;
    }

    /**
     * Gets the previous total value, No public setter for this value as it should only update internally and should
     * never be altered manually.
     * @return double
     */
    public double getPreviousTotalValue() {
        return previousTotalValue;
    }

    // Validate unitPrice and qtyInStock before updating totalValue
    private void calculateTotalValue() {
        if (this.unitPrice > 0 && this.qtyInStock > 0) {
            this.totalValue = this.unitPrice * this.qtyInStock;
        } else {
            this.totalValue = 0.0;
        }
    }

    /**
     * Submits this Item object as a new item to be written into storage and generate an appropriate transaction.
     */
    public void submitNewItem() {
        Item thisItem = this;
        ItemStorage.addNewItem(thisItem);
        Transaction transaction = new Transaction();
        transaction.generate(thisItem, TransactionType.ADDED);
    }

    /**
     * Submits this Item object to have its data updated in the storage and generate an appropriate transaction.
     */
    public void submitUpdatedItem() {
        Item thisItem = this;
        ItemStorage.updateItem(thisItem);
        Transaction transaction = new Transaction();
        transaction.generate(thisItem, TransactionType.UPDATED);
    }

    /**
     * Submits this Item object to be deleted from storage and generate an appropriate transaction.
     */
    public void submitDeleteItem() {
        Item thisItem = this;
        ItemStorage.deleteItem(thisItem);
        Transaction transaction = new Transaction();
        transaction.generate(thisItem, TransactionType.REMOVED);
    }


}
