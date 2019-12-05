import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Scanner;
import javax.crypto.Cipher;

public class RsaApplication {

  public static final String RSA = "RSA";

  public static void main(String args[]) throws
      Exception {
    String plaintext = "Test Metin";

    KeyPair anahtar = KeyPairGenerator.getInstance(RSA).generateKeyPair();

    RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) anahtar.getPrivate();
    RSAPublicKey publicKey = (RSAPublicKey) anahtar.getPublic();

    System.out.println("RSA information");
    System.out.println("");
    System.out.println("Private Key Information");
    System.out.println("n: " + privateKey.getModulus());
    System.out.println("e: " + privateKey.getPublicExponent());
    System.out.println("d: " + privateKey.getPrivateExponent());
    System.out.println("p: " + privateKey.getPrimeP());
    System.out.println("q: " + privateKey.getPrimeQ());
    System.out.println("");
    System.out.println("Public Key Information");
    System.out.println("n: " + publicKey.getModulus());
    System.out.println("p: " + publicKey.getPublicExponent());
    System.out.println("");
    System.out.println("Input Text: " + plaintext);
    System.out.println("Input Text byteArray: " + ByteToString(plaintext.getBytes()));

    Cipher cipher = Cipher.getInstance(RSA);

    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    byte[] sifreliMetin = cipher.doFinal(plaintext.getBytes());
    System.out.println("Encrypted byteArray: " + ByteToString(sifreliMetin));

    cipher.init(Cipher.DECRYPT_MODE, publicKey);
    byte[] desifreliMetin = cipher.doFinal(sifreliMetin);
    System.out.println("Decrypted byteArray " + ByteToString(desifreliMetin));
  }

  private static String ByteToString(byte[] buffer) {
    StringBuilder s = new StringBuilder();
    for (byte b : buffer) {
      String temp = Integer.toHexString(0x00FF & b);
      if (temp.length() == 1) {
        s.append("0" + temp);
      } else {
        s.append(temp);
      }
    }
    return s.toString();
  }

}