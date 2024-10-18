package testModels;

import models.Item;
import models.Transaction;
import models.TransactionType;
import utils.IDGenerator;
import utils.TimestampGenerator;

import java.util.ArrayList;

public class MockModels {

    public static Item getMockItem(int mockID, String mockName) {
        Item item = new Item();
        item.setId(mockID);
        item.setName(mockName);
        item.setQtyInStock(1.0);
        item.setUnitPrice(10.0);
        return item;
    }

    public static ArrayList<Item> getMockItemsList() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(getMockItem(11111, "TestItem1"));
        items.add(getMockItem(22222, "TestItem2"));
        items.add(getMockItem(33333, "TestItem3"));
        items.add(getMockItem(44444, "TestItem4"));
        items.add(getMockItem(55555, "TestItem5"));
        return items;
    }

    public static Transaction getMockTransaction(Item item, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setId(IDGenerator.generateNewID());
        transaction.setDescription(String.format("%s has been %s", item.getName(), transactionType.toString()));
        transaction.setQuantityChange(item.getQtyInStock() - item.getPreviousQuantityInStock());
        transaction.setValueChange(item.getTotalValue() - item.getPreviousTotalValue());
        transaction.setQuantityRemaining(item.getQtyInStock());
        transaction.setTransactionType(transactionType);
        transaction.setTimestamp(TimestampGenerator.getCurrentTime());
        return transaction;
    }

    public static ArrayList<Transaction> getMockTransactionsList() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<Item> items = getMockItemsList();
        for (Item item : items) {
            Transaction transaction = getMockTransaction(item, TransactionType.ADDED);
            transactions.add(transaction);
        }
        return transactions;
    }
}
