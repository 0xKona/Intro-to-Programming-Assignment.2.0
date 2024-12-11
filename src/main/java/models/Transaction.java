package models;

import storage.TransactionStorage;
import utils.IDGenerator;
import utils.TableType;
import utils.TimestampGenerator;

import java.sql.SQLException;

/**
 * Represents a transaction that records changes to the quantity and value of an item in stock.
 * <p>Uses getters and setters to alter fields</p>
 * <p>Methods:</p>
 * <li>{@link #getId()}</li>
 * <li>{@link #getDescription()}</li>
 * <li>{@link #setDescription(String)}</li>
 * <li>{@link #getQuantityChange()}</li>
 * <li>{@link #setQuantityChange(double)}</li>
 * <li>{@link #getValueChange()}</li>
 * <li>{@link #setValueChange(double)}</li>
 * <li>{@link #getQuantityRemaining()}</li>
 * <li>{@link #setQuantityRemaining(double)}</li>
 * <li>{@link #getTransactionType()}</li>
 * <li>{@link #setTransactionType(TransactionType)}</li>
 * <li>{@link #getTimestamp()}</li>
 * <li>{@link #setTimestamp(String)}</li>
 * <li>{@link #generate(Item, TransactionType)}</li>
 */
public class Transaction extends ModelID {
    private String description;
    private double quantityChange;
    private double valueChange;
    private double quantityRemaining;
    private String transactionType;
    private String timestamp;

    /**
     * Gets the transaction objects description and returns it
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the Transactions description.
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the transaction objects quantity change value and returns it
     * @return double (can be plus or minus)
     */
    public double getQuantityChange() {
        return quantityChange;
    }

    /**
     * Manually sets the transaction objects quantity change value
     * @param quantityChange double (can be plus or minus)
     */
    public void setQuantityChange(double quantityChange) {
        this.quantityChange = quantityChange;
    }

    /**
     * Gets the transaction objects value change value
     * @return double (can be plus or minus)
     */
    public double getValueChange() {
        return valueChange;
    }

    /**
     * Manually sets the value change value
     * @param valueChange double
     */
    public void setValueChange(double valueChange) {
        this.valueChange = valueChange;
    }

    /**
     * Gets the quantity remaining value from the transaction object
     * @return double (plus or minus)
     */
    public double getQuantityRemaining() {
        return quantityRemaining;
    }

    /**
     * Sets the quantity remaining value for the transaction object
     * @param quantityRemaining double (plus or minus)
     */
    public void setQuantityRemaining(double quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    /**
     * Gets the transaction objects Transaction Type and returns is as a TransactionType enum
     * @return {@link TransactionType}
     */
    public TransactionType getTransactionType() {
        return TransactionType.valueOf(this.transactionType);
    }

    /**
     * Sets the transaction type for the transaction object.
     * @param transactionType {@link TransactionType} enum
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType.toString();
    }

    /**
     * gets the transaction objects timestamp
     * @return String
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the transaction timestamp for the transaction object
     * @param timestamp String
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Generates the transaction object based on the provided Item object and TransactionType enum. 
     * @param item {@link Item} object
     * @param transactionType {@link TransactionType} object
     */
    public void generate(Item item, TransactionType transactionType) {
        try {
            this.setId(IDGenerator.generateNewID(TableType.Transactions));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.description = String.format("%s has been %s", item.getName(), transactionType.toString());
        this.quantityChange = transactionType == TransactionType.ADDED ? item.getQtyInStock() : item.getQtyInStock() - item.getPreviousQuantityInStock();
        this.valueChange = item.getTotalValue() - item.getPreviousTotalValue();
        this.quantityRemaining = item.getQtyInStock();
        this.transactionType = transactionType.toString();
        this.timestamp = TimestampGenerator.getCurrentTime();

        Transaction thisTransaction = this;
        TransactionStorage.writeNewTransaction(thisTransaction);
    }
}
