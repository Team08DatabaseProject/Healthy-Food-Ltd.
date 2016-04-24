package classpackage;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by paul thomas on 23.04.2016.
 */
public class EncryptionServiceTest {
    public EncryptionService encryptionService = new EncryptionService();


    @Test
    public void testAuthenticate() throws Exception {
        byte[] myArray = encryptionService.generateSalt();
        byte[] password = encryptionService.getEncryptedPassword("DataPassword", myArray);
        String password1 = "";
        System.out.println(password1);
        assertFalse(encryptionService.authenticate("sdfsdf", password, myArray));
        assertTrue(encryptionService.authenticate("DataPassword", password, myArray));
    }
}
