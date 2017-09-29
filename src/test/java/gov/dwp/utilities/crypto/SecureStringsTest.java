package gov.dwp.utilities.crypto;

import org.junit.Before;
import org.junit.Test;

import javax.crypto.SealedObject;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;


public class SecureStringsTest {
    private SecureStrings localSecureString;
    private SealedObject anObject;

    @Before
    public void setUp() throws Exception {
        localSecureString = new SecureStrings();
        anObject = null;
    }

    @Test
    public void testSealString() throws Exception {
        assertTrue(localSecureString.sealString("APassword") != null);
    }

    @Test
    public void testRevealString() throws Exception {
        anObject = localSecureString.sealString("Test Password");
        assertTrue(localSecureString.revealString(anObject).equals("Test Password"));
    }

    @Test
    public void testRevealStringNullObject() throws Exception {
        assertTrue(localSecureString.revealString(null) == null);
    }

    @Test
    public void testSealStringNullString() throws Exception {
        assertTrue(localSecureString.sealString(null) != null);
    }

    @Test
    public void testRevealStringWrongObject() throws Exception {
        SecureStrings testSecureString = new SecureStrings();
        anObject = testSecureString.sealString("Test Password");
        assertTrue(localSecureString.revealString(anObject) == null);
    }

    @Test(expected = NoSuchAlgorithmException.class)
    public void testToValidateConstructorWithInvalidCryptoType() throws NoSuchAlgorithmException {
        SecureStrings testSecureString = new SecureStrings("Bob");
    }

    @Test
    public void testStoringStringAsNullCanBeDecrypted() {
        anObject = localSecureString.sealString(null);
        assertTrue("This should return null", localSecureString.revealString(anObject) == null);
    }
}