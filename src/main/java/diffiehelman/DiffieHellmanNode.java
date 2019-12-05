package diffiehelman;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DiffieHellmanNode {

  public static final String ALGORITHM = "AES";
  public static final String EPHEMERAL_ELLIPTIC_CURVE = "EC";
  public static final String EPHEMERAL_ELLIPTIC_CURVE_DIFFIE_HELLMAN = "ECDH";
  public static final int SIZE_OF_KEY = 128;

  private PublicKey publickey;
  KeyAgreement keyAgreement;
  byte[] sharedsecret;


  DiffieHellmanNode() {
    makeKeyExchangeParams();
  }

  private void makeKeyExchangeParams() {
    KeyPairGenerator kpg = null;
    try {
      kpg = KeyPairGenerator.getInstance(EPHEMERAL_ELLIPTIC_CURVE);
      kpg.initialize(SIZE_OF_KEY);
      KeyPair kp = kpg.generateKeyPair();
      publickey = kp.getPublic();
      keyAgreement = KeyAgreement.getInstance(EPHEMERAL_ELLIPTIC_CURVE_DIFFIE_HELLMAN);
      keyAgreement.init(kp.getPrivate());

    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  public void setReceiverPublicKey(PublicKey publickey) {
    try {
      keyAgreement.doPhase(publickey, true);
      sharedsecret = keyAgreement.generateSecret();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  public String encrypt(String msg) {
    try {
      Key key = generateKey();
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(Cipher.ENCRYPT_MODE, key);
      byte[] encVal = c.doFinal(msg.getBytes());
      return new BASE64Encoder().encode(encVal);
    } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return msg;
  }

  public String decrypt(String encryptedData) {
    try {
      Key key = generateKey();
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(Cipher.DECRYPT_MODE, key);
      byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
      byte[] decValue = c.doFinal(decordedValue);
      return new String(decValue);
    } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }
    return encryptedData;
  }

  public PublicKey getPublickey() {
    return publickey;
  }

  protected Key generateKey() {
    return new SecretKeySpec(sharedsecret, ALGORITHM);
  }
}