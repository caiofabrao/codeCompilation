/*
 * Copyright (C) 2019 Caio Fabrao
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package keyGenarator;

import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 * This Class contains a compiled of utilities for RSA encryption
 *
 * @author Caio Fabrao
 */
public class RSAUtilities {

    /**
     * Method to convert a public key object from a byte array
     *
     * @param publicKeyBytes - a byte array with the public key bytes
     * @return publicKey - the new public key object
     * @throws NoSuchAlgorithmException - if no Provider supports a
     * KeyPairGeneratorSpi implementation for the specified algorithm.
     * @throws InvalidKeySpecException
     */
    public static PublicKey recoverPublicKey(byte[] publicKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKeyRecovered = keyFactory.generatePublic(publicKeySpec);
        return publicKeyRecovered;
    }

    /**
     * Method to convert a public key object from a String
     *
     * @param publicKey - a String with the public key
     * @return publicKey - the new public key object
     * @throws NoSuchAlgorithmException - if no Provider supports a
     * KeyPairGeneratorSpi implementation for the specified algorithm.
     * @throws InvalidKeySpecException
     */
    public static PublicKey recoverPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        PublicKey publicKeyRecovered = keyFactory.generatePublic(publicKeySpec);
        return publicKeyRecovered;
    }

    public static PublicKey recPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey.getBytes());
        PublicKey publicKeyRecovered = keyFactory.generatePublic(publicKeySpec);
        return publicKeyRecovered;
    }

    /**
     * Method to convert a private key object from a byte array
     *
     * @param publicKeyBytes - a byte array with the private key bytes
     * @return privateKey - the new private key object
     * @throws NoSuchAlgorithmException - if no Provider supports a
     * KeyPairGeneratorSpi implementation for the specified algorithm.
     * @throws InvalidKeySpecException
     */
    public static PrivateKey recoverPrivateKey(byte[] publicKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(publicKeyBytes);
        PrivateKey privateKeyRecovered = keyFactory.generatePrivate(privateKeySpec);
        return privateKeyRecovered;
    }

    /**
     * Method to convert a private key object from a String
     *
     * @param privateKey - a String with the private key
     * @return privateKey - the new private key object
     * @throws NoSuchAlgorithmException - if no Provider supports a
     * KeyPairGeneratorSpi implementation for the specified algorithm.
     * @throws InvalidKeySpecException
     */
    public static PrivateKey recoverPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        PrivateKey privateKeyRecovered = keyFactory.generatePrivate(privateKeySpec);
        return privateKeyRecovered;
    }

    /**
     * Sign a message with the senders private key (Digital Signature)
     *
     * Returns a String with the signature for the input message
     *
     * @param plainText - message to be signed
     * @param privateKey - senders private key object
     * @return a String with the signature
     * @throws Exception
     */
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));
        byte[] signature = privateSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    /**
     * Verifies the signature of a message with the senders public key (Digital
     * Signature)
     *
     * Returns the boolean with the veracity of the signature
     *
     * @param plainText - message to be verified
     * @param signature - signature for the input message
     * @param publicKey - senders public key object
     * @return the veracity of the signature
     * @throws Exception
     */
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return publicSignature.verify(signatureBytes);
    }

    /**
     * Encrypts a message using the receiver public key (Security)
     *
     * Returns a String with the encrypted message
     *
     * @param plainText - the message to be encrypted
     * @param publicKey - the receiver public key object
     * @return a Base64 String with the encrypted message
     * @throws Exception
     */
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * Decrypts a message using the private key (Security)
     *
     * Returns a plain String in Base64
     *
     * @param cipherText - the ciphered message to be decrypted
     * @param privateKey - the private key object
     * @return a String with de decrypted message
     * @throws Exception
     */
    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }

    /**
     * Encrypts a message using the receiver public key (Security)
     *
     * Returns the byte array of the encrypted message
     *
     * @param plainText - the message to be encrypted
     * @param publicKey - the receiver public key object
     * @return the byte array with the encrypted message
     * @throws Exception
     */
    public static byte[] encrypBytes(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes(UTF_8));
    }

    /**
     * Decrypts a message using the private key (Security)
     *
     * Returns the byte array of the decrypted message
     *
     * @param encrypted - the byte array contending the encrypted message
     * @param privateKey - the private key object
     * @return the byte array with the decrypted message
     * @throws Exception
     */
    public static byte[] decryptBytes(byte[] encrypted, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encrypted);
    }

    /**
     * Return a String that represents the public key object
     *
     * @param publicKey - public key to be converted
     * @return String equivalent of the public key
     */
    public static String publicKeyString(PublicKey publicKey) {
        byte[] encodedPublicKey = publicKey.getEncoded();
        String b64PublicKey = Base64.getEncoder().encodeToString(encodedPublicKey);
        return b64PublicKey;
    }
}
