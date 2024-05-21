package uk.gov.dwp.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This project is to make it easy to store passwords in an encrypted form using a cipher that is
 * constructed inside the application and is different for every instance.
 */
public class SecureStrings {
  private static final Logger LOGGER = LoggerFactory.getLogger(SecureStrings.class.getName());
  private Cipher cipherEncrypt = null;
  private Cipher cipherDecrypt = null;

  /**
   * Default constructor. Assumes AES encryption, generates a key and sets up the internal ciphers
   *
   * @throws NoSuchPaddingException : cipher exception
   * @throws NoSuchAlgorithmException : cipher exception
   * @throws InvalidKeyException : cipher exception
   */
  public SecureStrings()
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
    initialiseCiphers("AES");
  }

  /**
   * Overloaded constructor Takes in crypto Type, generates a key and sets up the internal ciphers.
   *
   * @param cryptoType - string cryptography type (eg AES)
   * @throws NoSuchAlgorithmException - if the input string is not a valid crypto type
   * @throws InvalidKeyException - if the input string is not a valid crypto type
   * @throws NoSuchPaddingException - if the input string is not a valid crypto type
   */
  public SecureStrings(String cryptoType)
      throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
    initialiseCiphers(cryptoType);
  }

  /**
   * initialiseCiphers Takes in crypto Type, generates a key and sets up the internal ciphers.
   *
   * @param cryptoType - string cryptography type (eg AES)
   * @throws NoSuchAlgorithmException - if the input string is not a valid crypto type
   * @throws NoSuchPaddingException - if the input string is not a valid crypto type
   * @throws InvalidKeyException - if the input string is not a valid crypto type
   */
  private void initialiseCiphers(String cryptoType)
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
    cipherEncrypt = Cipher.getInstance(cryptoType);
    cipherDecrypt = Cipher.getInstance(cryptoType);

    Key tempKey = KeyGenerator.getInstance(cryptoType).generateKey();
    cipherEncrypt.init(Cipher.ENCRYPT_MODE, tempKey);
    cipherDecrypt.init(Cipher.DECRYPT_MODE, tempKey);
  }

  /**
   * sealString Takes a string and seals it within a SealedObject using the auto-generated ciphers.
   *
   * @param input the string to encapsulate within the returned SealedObject
   * @return SealedObject Object containing encrypted input
   * @throws IOException - if an error occurs during serialization
   * @throws IllegalBlockSizeException if the given cipher is a block cipher, no padding has been
   *     requested, and the total input length (i.e., the length of the serialized object contents)
   *     is not a multiple of the cipher's block size
   */
  public SealedObject sealString(String input) throws IOException, IllegalBlockSizeException {
    return new SealedObject(input, cipherEncrypt);
  }

  /**
   * revealString Uses the auto generated ciphers to retrieve a string from the SealedObject.
   *
   * @param inputObject object containing a string to return. For success, must be generated by a
   *     call to sealString in the same SecureStrings instance.
   * @return String, null on error.
   */
  public String revealString(SealedObject inputObject) {
    if (null != inputObject) {
      try {
        return (String)
            inputObject.getObject(cipherDecrypt); // using cast to allow for null string input
      } catch (IOException
               | BadPaddingException
               | IllegalBlockSizeException
               | ClassNotFoundException e) {
        LOGGER.error(String.format("error unsealing string : %s", e.getMessage()));
        LOGGER.debug(e.getMessage(), e);
      }
    }

    return null;
  }
}
