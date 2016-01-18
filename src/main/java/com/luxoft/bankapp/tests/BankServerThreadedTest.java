package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankApplication;
import com.luxoft.bankapp.service.BankClientMock;
import com.luxoft.bankapp.service.BankServerThreaded;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class BankServerThreadedTest {
    Thread threadedServerThread = new Thread();

    @Before
    public void runServer() throws IOException {
        Bank bank1 = new Bank();
        BankApplication.initialize(bank1);
        BankServerThreaded serverThreaded = new BankServerThreaded(bank1);
        serverThreaded.run();
    }

    @Test
    public void BankClientMockTest() throws InterruptedException {
        BankClientMock clientMock = new BankClientMock();
        for (int i = 0; i < 1000; i++) {
            Thread newThread = new Thread(clientMock);
            newThread.start();
            try {
                newThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long amountAfterWithdraw = (long) clientMock.getAmountOnTheAccount();
            long amountBeforeWithdraw = amountAfterWithdraw + 1000;
            if (i > 0) {
                assertEquals(amountBeforeWithdraw - 1000, amountAfterWithdraw);
            }
        }
    }
}


