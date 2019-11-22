package keyGenarator;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author Caio Fabrao
 */
public class KeyGenerator {
    public KeyPairGenerator keyGen;
    public KeyPair keyPair;
    public PrivateKey privateKey;
    public PublicKey publicKey;
    
    /**
     * This method generates the asymmetric key pair (public and private key)
     * 
     * @param type - the name of the requested key algorithm (RSA, DSA, ...)
     * @param bits - the parameter set used to generate the keys.
     * @throws NoSuchAlgorithmException
     */
    public void generateKeys(String type, int bits) throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance(type);
        this.keyGen.initialize(bits);
        this.keyPair = keyGen.genKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    /**
     * Method to converts a public key object from a byte array
     * 
     * @param type - the name of the requested key algorithm (RSA, DSA, ...)
     * @param publicKeyBytes - a byte array cwith the public key bytes
     * @return publicKey - the new public key object
     * @throws NoSuchAlgorithmException - if no Provider supports a KeyPairGeneratorSpi implementation for the specified algorithm.
     * @throws InvalidKeySpecException
     */
    public PublicKey recoverPK(String type, byte[] publicKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(type);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKeyR = keyFactory.generatePublic(publicKeySpec);
        return publicKeyR;
    }

    /**
     * Encrypts a message using a private key object (Digital Signage)
     * 
     * Returns a byte array contending the encrypted message
     * 
     * @param type - the name of the requested key algorithm (RSA, DSA, ...)
     * @param privateKey - the private key object
     * @param message - the message to be encrypted
     * @return a byte array contending the encrypted message
     * @throws Exception
     */
    public byte[] encryptSignage(String type, PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance(type);  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        return cipher.doFinal(message.getBytes());  
    }
    
    /**
     * Decrypts a message from a verified source with the public key of the source (Digital Signage)
     *
     * Returns a byte array with the decrypted message
     * 
     * @param type - the name of the requested key algorithm (RSA, DSA, ...)
     * @param publicKey - the source public key object
     * @param encrypted - the byte array contending the encrypted message
     * @return the bytes of the decrypted message 
     * @throws Exception
     */
    public byte[] decryptSignage(String type, PublicKey publicKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(type);  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(encrypted);
    }

    /**
     * Encrypts a message using the reciver public key (Security)
     * 
     * Returns the byte array of the encrypted message
     * 
     * @param type - the name of the requested key algorithm (RSA, DSA, ...)
     * @param publicKey - the reciver public key object
     * @param message - the message to be encrypted
     * @return
     * @throws Exception
     */
    public byte[] encryptSecurity(String type, PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance(type);  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        return cipher.doFinal(message.getBytes());  
    }
  
    /**
     * Decrypts a message using the private key (Security)
     * 
     * Returns the byte array of the decrypted message
     * 
     * @param type - the name of the requested key algorithm (RSA, DSA, ...)
     * @param privateKey - the private key object
     * @param encrypted - the byte array contending the encrypted message
     * @return the byte array with the decrypted message
     * @throws Exception
     */
    public byte[] decryptSecurity(String type, PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(type);  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encrypted);
    }
}
