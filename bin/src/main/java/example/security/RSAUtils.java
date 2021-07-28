package example.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class RSAUtils {

  static final String RSA = "RSA";

  // Generating public & private keys
  // using RSA algorithm.
  public static KeyPair generateRSAKeyPair()
      throws Exception
  {
      SecureRandom secureRandom = new SecureRandom();
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);

      keyPairGenerator.initialize(2048, secureRandom);

      KeyPair keypair = keyPairGenerator.generateKeyPair();

      System.out.println(
          "The Public Key is: "
          + DatatypeConverter.printHexBinary(
                keypair.getPublic().getEncoded()));

      System.out.println(
          "The Private Key is: "
          + DatatypeConverter.printHexBinary(
                keypair.getPrivate().getEncoded()));

      return keypair;
  }

  // Encryption function which converts
  // the plainText into a cipherText
  // using private Key.
  public static byte[] encrypt(String plainText, KeyPair keypair) throws Exception
  {

    PrivateKey privateKey = keypair.getPrivate();

      Cipher cipher
          = Cipher.getInstance(RSA);

      cipher.init(
          Cipher.ENCRYPT_MODE, privateKey);

      byte[] cipherText = cipher.doFinal(plainText.getBytes());

      System.out.print("The Encrypted Text is: ");

      String rsaText = DatatypeConverter.printHexBinary(cipherText);

      System.out.println(rsaText);

      System.out.println(String.format("The Encrypted Text length is: %d", rsaText.length()));

      //return cipherText;

      return GZIPCompression.compress(rsaText);
  }

  // Decryption function which converts
  // the ciphertext back to the
  // original plaintext.
  public static String decrypt(byte[] cipherText, KeyPair keypair) throws Exception
  {

    cipherText = GZIPCompression.decompress(cipherText).getBytes();

    PublicKey publicKey = keypair.getPublic();

      Cipher cipher
          = Cipher.getInstance(RSA);

      cipher.init(Cipher.DECRYPT_MODE, publicKey);

      byte[] result = cipher.doFinal(cipherText);
      String rsaText = new String(result);

      System.out.println("The Decrypted text is: " + rsaText);

      return rsaText;
  }

}
