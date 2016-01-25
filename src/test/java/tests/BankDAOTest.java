package tests;

import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.tests.TestService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankDAOTest {
    Bank bank;
    BankDAOImpl bankDAO;

    @Before
    public void initBank() {
        bankDAO = new BankDAOImpl();
        bank = new Bank ();
        bank.setBankName("Deutche");
        bank.setId(1);
        Client client = new Client ();
        client.setName("Ivan Ivanov");
        client.setCity("Kiev");
        client.addAccount(new CheckingAccount(1000,300));
        bank.addClient(client);
    }

    @Test
    public void testInsert() throws DAOException {
        bankDAO.save(bank);
        Bank bank2 = bankDAO.getBankByName("Deutche");
        assertTrue(TestService.isEquals(bank, bank2));
    }

    @Test
    public void testUpdate() throws DAOException {
        bankDAO.save(bank);
        Client client2 = new Client ();
        client2.setName("Ivan Petrov");
        client2.setCity("New York");
        client2.addAccount(new SavingAccount(2000f));
        bank.addClient(client2);
        bankDAO.save(bank);
        Bank bank2 = bankDAO.getBankByName("Deutche");
        assertFalse(TestService.isEquals(bank, bank2));
    }
}
