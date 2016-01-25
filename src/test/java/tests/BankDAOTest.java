package tests;

import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.tests.TestService;
import org.junit.Before;
import org.junit.Test;

public class BankDAOTest {
        Bank bank;

        @Before
        public void initBank () {
            bank = new Bank ();
            bank.setName("My Bank");
            Client client = new Client ();
            client.setName ("Ivan Ivanov");
            client.setCity ("Kiev");
            client.addAccount (new CheckingAccount());
            bank.addClient (client);
        }

        @Test
        public void testInsert () {
            BankDAOImpl.save(bank);
            Bank bank2 = BankDaoImpl.loadBank ();
            assertTrue (TestService.compare (bank, bank2));
        }


        @Test
        public void testUpdate () {
            BankDaoImpl.save (bank);

            // Make changes to Bank
            Client client2 = new Client ();
            client2.setName ("Ivan Petrov");
            client2.setCity ("New York");
            client2.addAccount (new SavingAccount());
            bank.addClient (new Client());
            BankDaoImpl.save (bank);

            Bank bank2 = BankDaoImpl.loadBank ();
            assertTrue(TestService.compare(bank, bank2));
        }
}
