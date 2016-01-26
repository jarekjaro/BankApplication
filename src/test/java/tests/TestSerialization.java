package tests;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankFeedService;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSerialization {

    Client[] clientInit;
    BankService bankServiceTest;

    @Before
    public void initialize() {
        bankServiceTest = new BankServiceImpl();
        clientInit = new Client[]{
                new Client("Janusz", Gender.MALE),
                new Client("Zosia", Gender.FEMALE),
                new Client("Maryla", Gender.FEMALE),
                new Client("Pawel", Gender.MALE),
                new Client("Stefan", Gender.MALE),
                new Client("Filip", Gender.MALE)};
    }

    @Test
    public void testSaveClient() {
        for (int i = 0; i < clientInit.length; i++) {
            bankServiceTest.saveClient(clientInit[i]);
            assertTrue(new File(clientInit[i].getName() + ".object").exists());
        }
    }

    @Test
    public void testLoadClient() throws IOException, ClassNotFoundException {
        for (int i = 0; i < clientInit.length; i++) {
            String nameFromFile = bankServiceTest.loadClient(clientInit[i].getName() + ".object").getName();
            Gender genderFromFile = bankServiceTest.loadClient(clientInit[i].getName() + ".object").getGender();
            assertEquals(clientInit[i].getName(), nameFromFile);
            assertEquals(clientInit[i].getGender(), genderFromFile);
        }
    }

    @Test
    public void testLoadFeed() throws FileNotFoundException {
        String testFilePath = "C:\\Users\\JARO\\IdeaProjects\\BankApplication\\testFile";
        BankFeedService.loadFeed(testFilePath);
    }
}
