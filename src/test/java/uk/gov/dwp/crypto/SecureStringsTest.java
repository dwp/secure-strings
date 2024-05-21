package uk.gov.dwp.crypto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecureStringsTest {

  private static final String TEST_STRING = "i-am-a-string-to-seal";
  private SecureStrings classInstance;
  private SealedObject sealedObject;

  @BeforeEach
  void setUp() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
    classInstance = new SecureStrings();
    sealedObject = null;
  }

  @Test
  void exceptionConstructor() {
    assertThatThrownBy(() -> new SecureStrings("Not-Valid"))
        .isInstanceOf(NoSuchAlgorithmException.class);
  }

  @Test
  void sealStringPassingCryptoType()
      throws IOException, IllegalBlockSizeException, NoSuchPaddingException,
      NoSuchAlgorithmException, InvalidKeyException {
    assertThat(new SecureStrings("DES").sealString("APassword")).isNotNull();
  }

  @Test
  void sealString() throws IOException, IllegalBlockSizeException {
    assertThat(classInstance.sealString("APassword")).isNotNull();
  }

  @Test
  void revealString() throws IOException, IllegalBlockSizeException {
    sealedObject = classInstance.sealString(TEST_STRING);
    assertThat(classInstance.revealString(sealedObject))
        .isEqualTo(TEST_STRING);
  }

  @Test
  void revealStringNullObject() {
    assertThat(classInstance.revealString(null)).isNull();
  }

  @Test
  void sealStringNullString() throws IOException, IllegalBlockSizeException {
    assertThat(classInstance.sealString(null)).isNotNull();
  }

  @Test
  void revealStringWrongObject()
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException,
      IllegalBlockSizeException {
    SecureStrings localInstance = new SecureStrings();
    sealedObject = localInstance.sealString(TEST_STRING);
    assertThat(classInstance.revealString(sealedObject)).isNull();
  }

  @Test
  void toValidateConstructorWithInvalidCryptoType() {
    assertThatThrownBy(() -> new SecureStrings("Bob"))
        .isInstanceOf(NoSuchAlgorithmException.class);
  }

  @Test
  void storingStringAsNullCanBeDecrypted()
      throws IOException, IllegalBlockSizeException {
    sealedObject = classInstance.sealString(null);
    assertThat(classInstance.revealString(sealedObject)).as("This should return null").isNull();
  }
}
