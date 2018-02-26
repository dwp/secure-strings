package uk.gov.dwp.crypto;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.SealedObject;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SecureStringsTest {
    private SecureStrings localSecureString;
    private SealedObject anObject;

    @Before
    public void setUp() {
        localSecureString = new SecureStrings();
        anObject = null;
    }

    @Test
    public void test_SealString() throws Exception {
        assertTrue(localSecureString.sealString("APassword") != null);
    }

    @Test
    public void test_RevealString() throws Exception {
        anObject = localSecureString.sealString("Test Password");
        assertTrue(localSecureString.revealString(anObject).equals("Test Password"));
    }

    @Test
    public void test_RevealString_Null_Object() throws Exception {
        assertTrue(localSecureString.revealString(null) == null);
    }

    @Test
    public void test_SealString_Null_String() throws Exception {
        assertTrue(localSecureString.sealString(null) != null);
    }

    @Test
    public void test_RevealString_Wrong_Object() throws Exception {
        SecureStrings testSecureString = new SecureStrings();
        anObject = testSecureString.sealString("Test Password");
        assertTrue(localSecureString.revealString(anObject) == null);
    }

    @Test(expected = NoSuchAlgorithmException.class)
    public void test_To_Validate_Constructor_With_Invalid_Crypto_Type() throws NoSuchAlgorithmException {
        new SecureStrings("Bob");
    }

    @Test
    public void test_Storing_String_As_Null_Can_Be_Decrypted() {
        anObject = localSecureString.sealString(null);
        assertTrue("This should return null", localSecureString.revealString(anObject) == null);
    }

    @Test
    public void test_For_escapeJSONObject_With_Valid_JSON_String() throws IOException {
        String testJsonString = "{" +
                "\"testBoolean\":true," +
                "\"testString\":\"string\"," +
                "\"testInteger\":42" +
                "}";
        TestClassForSerialisation testing = SecureStrings.escapedJSONObjectFromString(testJsonString, TestClassForSerialisation.class);
        assertThat(testing.isTestBoolean(), is(true));
        assertThat(testing.getTestString(), containsString("string"));
        assertThat(testing.getTestInteger(), is(42));
    }

    @Test (expected = JsonParseException.class)
    public void test_For_escapeJSONObject_With_Invalid_JSON_String() throws IOException {
        String testJsonString = "{" +
                "\"testBoolean\":true," +
                "}";
        TestClassForSerialisation testing = SecureStrings.escapedJSONObjectFromString(testJsonString, TestClassForSerialisation.class);
        throw new AssertionError("Should never get here!");
    }

    @Test (expected = InvalidParameterException.class)
    public void test_For_escapeJSONObject_With_Blank_JSON_String() throws IOException {
        String testJsonString = "";
        TestClassForSerialisation testing = SecureStrings.escapedJSONObjectFromString(testJsonString, TestClassForSerialisation.class);
        throw new AssertionError("Should never get here!");
    }
}