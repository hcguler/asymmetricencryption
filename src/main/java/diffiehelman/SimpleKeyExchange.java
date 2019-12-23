package diffiehelman;

import com.sun.crypto.provider.DHKeyPairGenerator;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;

public class SimpleKeyExchange {

  public static final int SIZE_OF_KEY = 512;
  public static final String DIFFIE_HELLMAN = "DH";

  public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
    DHKeyPairGenerator dhbobKeyPairGenerator = new DHKeyPairGenerator();
    dhbobKeyPairGenerator.initialize(SIZE_OF_KEY, new SecureRandom());
    DHKeyPairGenerator dhAliceKeyPairGenerator = new DHKeyPairGenerator();
    dhAliceKeyPairGenerator.initialize(SIZE_OF_KEY, new SecureRandom() );

    KeyPair bobKeyPair = dhbobKeyPairGenerator.generateKeyPair();
    KeyAgreement bobKeyAgreement = KeyAgreement.getInstance(DIFFIE_HELLMAN);
    bobKeyAgreement.init(bobKeyPair.getPrivate());

    DHPublicKey bobPublicKey = (DHPublicKey) bobKeyPair.getPublic();
    DHPrivateKey bobPrivateKey = (DHPrivateKey) bobKeyPair.getPrivate();

    System.out.println("Bob info");
    System.out.println("Public Key Info");
    System.out.println("p: " + bobPublicKey.getParams().getP());
    System.out.println("g: " + bobPublicKey.getParams().getG());
    System.out.println("b: " + bobPublicKey.getY());
    System.out.println("Private Key Info");
    System.out.println("a: " + bobPrivateKey.getX());

    KeyPair aliceKeyPair = dhAliceKeyPairGenerator.generateKeyPair();
    DHPublicKey alicePublicKey = (DHPublicKey) aliceKeyPair.getPublic();
    DHPrivateKey alicePrivateKey = (DHPrivateKey) aliceKeyPair.getPrivate();

    KeyAgreement aliceKeyAgreement = KeyAgreement.getInstance(DIFFIE_HELLMAN);
    aliceKeyAgreement.init(aliceKeyPair.getPrivate());

    System.out.println("Alice info");
    System.out.println("Public Key Info");
    System.out.println("p: " + alicePublicKey.getParams().getP());
    System.out.println("g: " + alicePublicKey.getParams().getG());
    System.out.println("b: " + alicePublicKey.getY());
    System.out.println("Private Key Info");
    System.out.println("b: " + alicePrivateKey.getX());

    //Key exchange
    bobKeyAgreement.doPhase(aliceKeyPair.getPublic(), true);
    byte[] sharedsecretBob = bobKeyAgreement.generateSecret();
    BigInteger sValueCalculatedByBob = new BigInteger(1, sharedsecretBob);

    System.out.println("bob s value: " + sValueCalculatedByBob);

    aliceKeyAgreement.doPhase(bobPublicKey, true);
    byte[] sharedsecretAlice = aliceKeyAgreement.generateSecret();
    BigInteger sValueCalculatedByAlice = new BigInteger(1, sharedsecretAlice);

    System.out.println("alice s value: " + sValueCalculatedByAlice);

    if (sValueCalculatedByBob.equals(sValueCalculatedByAlice)) {
      System.out.println("KeyExchange SuccessFully");
    }
  }
}
