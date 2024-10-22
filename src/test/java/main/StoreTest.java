package main;

import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import storage.DatabaseManager;

import static org.mockito.ArgumentMatchers.any;

public class StoreTest {

    @Test
    @DisplayName("Main should call start and initialize application")
    void testStart() throws Exception {
        MockedStatic<DatabaseManager> mockedDatabaseManager = Mockito.mockStatic(DatabaseManager.class);
        MockedStatic<View> mockedView = Mockito.mockStatic(View.class);

        Store store = new Store();
        Stage mockStage = Mockito.mock(Stage.class);

        store.start(mockStage);

        mockedView.verify(() -> View.initializeView(any(Stage.class)), Mockito.times(1));
        mockedDatabaseManager.verify(DatabaseManager::initializeDatabase, Mockito.times(1));
    }
}
