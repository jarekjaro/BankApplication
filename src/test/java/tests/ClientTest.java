package tests;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClientTest {

    Client[] clientInit = new Client[]{
            new Client("Janusz", Gender.MALE),
            new Client("Zosia", Gender.FEMALE),
            new Client("Maryla", Gender.FEMALE),
            new Client("Pawel", Gender.MALE),
            new Client("Stefan", Gender.MALE),
            new Client("Filip", Gender.MALE)};

    @Test
    public void testEquals() throws Exception {
        Client[] clientInit2 = new Client[]{
                new Client("Janusz", Gender.MALE),
                new Client("Zosia", Gender.FEMALE),
                new Client("Maryla", Gender.FEMALE),
                new Client("Pawel", Gender.MALE),
                new Client("Stefan", Gender.MALE),
                new Client("Filip", Gender.MALE)};

        for(int i = 0;i<clientInit.length;i++){
            assertEquals(clientInit[i], clientInit2[i]);
        }
    }

    @Test
    public void testHashCode() throws Exception {
    }
}