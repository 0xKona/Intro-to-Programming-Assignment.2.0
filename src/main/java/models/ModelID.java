package models;

public class ModelID {
    private String id;

    /**
     * Gets the transaction objects ID and returns it.
     * @return int;
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the Transactions ID, should only be used when reading data from storage and creating objects, Manually
     * updating a TransactionID is NOT recommended.
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }
}
