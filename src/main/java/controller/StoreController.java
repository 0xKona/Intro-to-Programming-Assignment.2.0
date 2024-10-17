package controller;

import main.View;

public class StoreController {

    public static void start() {
        View.displayMainMenu();
    }

    /**
     * Routes user to desired program depending on their input
     * @param capturedInput int [between 1 and 5]. Will print a warning that the
     *                      input is invalid if anything else is entered.
     */
    public static void mainMenuRouting(int capturedInput) {
        /* I've moved away from using a while loop here to avoid causing any infinite loop's, however it is possible
        to use one here as shown in the starter code for this assignment. I've decided to use a switch case instead as
        in my opinion it makes the code more readable, and it also makes it easier to add additional options later.
         */
        switch (capturedInput) {
            case 1: {
                ItemController.addNewItem();
                break;
            }
            case 2: {
                ItemController.editItemQuantity();
                break;
            }
            case 3: {
                ItemController.deleteItem();
                break;
            }
            case 4: {
                TransactionController.generateDailyTransactionReport();
                break;
            }
            case 5: {
                System.exit(0);
            }
            default: {
                // If capturedInput does not match any of the defined cases, input is invalid and user should try again.
                System.out.println("This doesn't appear to be a valid option! Please try again...");
                View.handleMainMenuChoice();
                break;
            }
        }
    }
}
