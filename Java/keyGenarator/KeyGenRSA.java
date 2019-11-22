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

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  A Class to create/store a key pair for encryption. (Publick Key and Private Key)
 * 
 * @author Caio Fabrao
 */
public class KeyGenRSA {

    public KeyPairGenerator keyGen;
    public KeyPair keyPair;
    public PrivateKey privateKey;
    public PublicKey publicKey;

    /**
     * This method generates the asymmetric key pair
     */
    public void generateKeys() {
        try {
            this.keyGen = KeyPairGenerator.getInstance("RSA");
            this.keyGen.initialize(1024);
            this.keyPair = keyGen.genKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyGenRSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
