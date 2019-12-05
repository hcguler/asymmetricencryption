package diffiehelman;

import java.io.IOException;
import util.AeUtils;

public class DiffieHellmanApplication {

  public static void main(String[] args) {
    DiffieHellmanNode server = new DiffieHellmanNode();
    DiffieHellmanNode client = new DiffieHellmanNode();

    server.setReceiverPublicKey(client.getPublickey());
    client.setReceiverPublicKey(server.getPublickey());

    String plainText = "Test Metin";

    String encryptedText = server.encrypt(plainText);

    System.out.println("Input Text: " + plainText);
    System.out.println("Input Text byteArray: " + AeUtils.ByteToString(plainText.getBytes()));
    System.out.println("Encrypted byteArray: " + encryptedText);
    System.out.println("Decrypted byteArray: " + AeUtils.ByteToString(client.decrypt(encryptedText).getBytes()));
    System.out.println("Decrypted Text: " + client.decrypt(encryptedText));

  }
}
