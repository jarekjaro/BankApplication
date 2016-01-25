package tests;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.ClassDeclarationSpy;
import com.luxoft.bankapp.tests.TestService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestServiceTest {
    Bank bank1, bank2;

    @Before
    public void initBanks() {
        bank1 = new Bank();
        bank1.setId(1);
        bank1.setBankName("My Bank");
        Client client = new Client();
        client.setName("Ivan Ivanov");
        client.setCity("Kiev");
        client.addAccount(new CheckingAccount(10000, 300));
        // Add some fields from Client
        // Marked as @ NoDB, with different values
        // For client and client2    Set<Account> accounts;
//        private String name;
//        private String surname;
//        private Gender gender;
//        private String phone;
//        private String email;
//        private Account activeAccount;
//        private float initialOverdraft;
//        private String city;
//        private int id;
        client.setEmail("krzemol@ring.com");
        client.setGender(Gender.FEMALE);
        bank1.addClient(client);
        bank2 = new Bank();
        bank2.setId(1);
        bank2.setBankName("My Bank");
        Client client2 = new Client();
        client2.setName("Ivan Ivanov");
        client2.setCity("Kiev");
        client2.addAccount(new CheckingAccount(10000, 300));
        // annotated @nonDB
        client2.setEmail("brzeczyszczykiewicz@liga.zf");
        client.setGender(Gender.MALE);
        bank2.addClient(client2);
    }

    @Test
    public void testEquals() {
        ClassDeclarationSpy.main(bank1.getClass().getCanonicalName(),
                bank2.getClass().getCanonicalName());
        assertTrue(TestService.isEquals(bank1, bank2));
    }
}