package uk.gov.dwp.crypto;

import com.fasterxml.jackson.core.JsonParseException;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.SealedObject;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SecureStringsTest {
  private static final String TEST_STRING = "i-am-a-string-to-seal";
  private SecureStrings classInstance;
  private SealedObject sealedObject;

  @Before
  public void setUp() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
    classInstance = new SecureStrings();
    sealedObject = null;
  }

  @Test(expected = NoSuchAlgorithmException.class)
  public void exceptionConstructor()
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
    new SecureStrings("Not-Valid");
  }

  @Test
  public void testSealStringPassingCryptoType()
      throws IOException, IllegalBlockSizeException, NoSuchPaddingException,
          NoSuchAlgorithmException, InvalidKeyException {
    assertNotNull(new SecureStrings("DES").sealString("APassword"));
  }

  @Test
  public void testSealString() throws IOException, IllegalBlockSizeException {
    assertNotNull(classInstance.sealString("APassword"));
  }

  @Test
  public void testRevealString() throws IOException, IllegalBlockSizeException {
    sealedObject = classInstance.sealString(TEST_STRING);
    assertThat(classInstance.revealString(sealedObject), is(equalTo(TEST_STRING)));
  }

  @Test
  public void testRevealstringNullObject() {
    assertNull(classInstance.revealString(null));
  }

  @Test
  public void testSealStringNullString() throws IOException, IllegalBlockSizeException {
    assertNotNull(classInstance.sealString(null));
  }

  @Test
  public void testRevealStringWrongObject()
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException,
          IllegalBlockSizeException {
    SecureStrings localInstance = new SecureStrings();
    sealedObject = localInstance.sealString(TEST_STRING);
    assertNull(classInstance.revealString(sealedObject));
  }

  @Test(expected = NoSuchAlgorithmException.class)
  public void testToValidateConstructorWithInvalidCryptoType()
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
    new SecureStrings("Bob");
  }

  @Test
  public void testStoringStringAsNullCanBeDecrypted()
      throws IOException, IllegalBlockSizeException {
    sealedObject = classInstance.sealString(null);
    assertNull("This should return null", classInstance.revealString(sealedObject));
  }
}
