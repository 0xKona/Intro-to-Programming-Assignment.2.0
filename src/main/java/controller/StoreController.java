package controller;

import main.View;

/**
 * Handles the main menu display and routing to the various programs.
 * <p>Methods:</p>
 * <li>{@link #start()}</li>
 * <li>{@link #mainMenuRouting(int)}</li>
 */
public class StoreController {

    /**
     * Starts the application by displaying the main menu to the user.
     */
    public static void start() {
        /* View.displayMainMenu displays the user interface and then calls a handler function that captures the user
        input which calls mainMenuRouting below, I have done it this way so that other programs can call the UI and the
        Logic part separately when needed. */
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
                break;
            }
            default: {
                // If capturedInput does not match any of the defined cases, input is invalid and user should try again.
                System.out.println("This doesn't appear to be a valid option! Please try again...");
                /* This is an example of why I seperated displaying the UI and handling the user input, here I can just
                call the handler since the UI will already be on the screen. */
                View.handleMainMenuChoice();
                break;
            }
        }
    }
}
