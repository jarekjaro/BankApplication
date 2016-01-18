package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.service.BankClientMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankServerThreadedTest {

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


