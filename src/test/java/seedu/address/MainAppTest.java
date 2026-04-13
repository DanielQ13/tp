package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class MainAppTest {

    @TempDir
    public Path testFolder;

    @Test
    public void initModelManager_missingAddressBookFile_populatesAndSavesSampleData() throws Exception {
        Path addressBookPath = testFolder.resolve("addressbook.json");
        Path prefsPath = testFolder.resolve("preferences.json");
        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(prefsPath)
        );
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookPath);

        MainApp mainApp = new MainApp();
        Method initModelManager = MainApp.class.getDeclaredMethod(
                "initModelManager", Storage.class, ReadOnlyUserPrefs.class);
        initModelManager.setAccessible(true);

        Model model = (Model) initModelManager.invoke(mainApp, storage, userPrefs);

        assertEquals(new AddressBook(SampleDataUtil.getSampleAddressBook()), new AddressBook(model.getAddressBook()));
        assertTrue(Files.exists(addressBookPath));

        AddressBook savedAddressBook = new AddressBook(storage.readAddressBook().orElseThrow());
        assertEquals(new AddressBook(SampleDataUtil.getSampleAddressBook()), savedAddressBook);
    }
}
