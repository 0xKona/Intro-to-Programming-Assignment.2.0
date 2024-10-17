package models;

import storage.TransactionStorage;
import utils.IDGenerator;
import utils.TimestampGenerator;

/**
 * Represents a transaction that records changes to the quantity and value of an item in stock.
 *
 * <p>The class stores essential information about the transaction, including the type of transaction
 * (e.g., sale or purchase), the change in quantity and value, the remaining quantity after the transaction,
 * and the timestamp indicating when the transaction occurred.</p>
 *
 * <p>This class provides getter and setter methods for each field, allowing for the transaction's details to be accessed and modified as needed.</p>
 */
public class Transaction {
    private int id;
    private String description;
    private double quantityChange;
    private double valueChange;
    private double quantityRemaining;
    private String transactionType;
    private String timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(double quantityChange) {
        this.quantityChange = quantityChange;
    }

    public double getValueChange() {
        return valueChange;
    }

    public void setValueChange(double valueChange) {
        this.valueChange = valueChange;
    }

    public double getQuantityRemaining() {
        return quantityRemaining;
    }

    public void setQuantityRemaining(double quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    public TransactionType getTransactionType() {
        return TransactionType.valueOf(this.transactionType);
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType.toString();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void generate(Item item, TransactionType transactionType) {
        this.id = IDGenerator.generateNewID();
        this.description = String.format("%s has been %s", item.getName(), transactionType.toString());
        this.quantityChange = item.getQtyInStock() - item.getPreviousQuantityInStock();
        this.valueChange = item.getTotalValue() - item.getPreviousTotalValue();
        this.quantityRemaining = item.getQtyInStock();
        this.transactionType = transactionType.toString();
        this.timestamp = TimestampGenerator.getCurrentTime();

        Transaction thisTransaction = this;
        TransactionStorage.writeNewTransaction(thisTransaction);
    }
}
