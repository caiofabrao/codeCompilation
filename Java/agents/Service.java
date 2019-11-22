/*
 * Copyright (C) 2019 caiof
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
package agents;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;
import keyGenarator.RSAUtilities;

/**
 * This class contains a object of Process viewed by other objects. It has only
 * the public parameters of the Process Class that are: the Address, the port
 * number and the public key
 *
 * @author Caio Fabrao
 */
public class Service {

    private int id;
    private PublicKey publicKey;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Service other = (Service) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.publicKey, other.publicKey);
    }

    @Override
    public String toString() {
        return  "Service: {" + id + "," + RSAUtilities.publicKeyString(publicKey) + '}';
    }

    public Service() {
    }

    /**
     * Constructor for a object Service without the public key
     *
     * @param ip
     * @param port
     */
    public Service(int id) {
        this.id = id;
    }

    /**
     * Constructor for a object Service
     *
     * @param ip
     * @param port
     * @param publicKey
     */
    public Service(int id, PublicKey publicKey) {
        this.id = id;
        this.publicKey = publicKey;
    }

    public Service(int id, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.id = id;
        this.publicKey = RSAUtilities.recoverPublicKey(publicKey);
    }

    public int getId() {
        return id;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

}
