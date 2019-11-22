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
package agents;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Objects;
import keyGenarator.RSAUtilities;
import multicast.Multicast;
import udp.UDPServer;

/**
 * Class of Process running created to the 2nd assignment of the curse Distributed Systems
 * in Computer Engineering program of UTFPR 
 * @author Caio Fabrao
 */
public class Process extends keyGenarator.KeyGenRSA {

	private int id;
	private HashSet<Service> servicesList;
	public Process() {
	}

	/**
	 * Object Process constructor
	 * 
	 * @param id - Integer number to identify the Process
	 */
	public Process(int id) {
		this.id = id;
		this.servicesList = new HashSet<>();
	}

	@Override
	public String toString() {
		return "Process{" + id + '}';
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 29 * hash + this.id;
		return hash;
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
		final Process other = (Process) obj;
		if (this.id != other.id) {
			return false;
		}
		return Objects.equals(this.id, other.id);
	}

	/**
	 * This method initialize some automatics properties of the Process object.
	 * It generate the key pair, puts the process in the Service list and
	 * initialize the masterStrike in 0.
	 */
	public void initialize() {
		this.generateKeys();
		this.servicesList.add(getSelfAsService());
	}

	public int getId() {
		return id;
	}

	/**
	 * This method returns a casting of the Process object to a Service object
	 *
	 * @return a Service object reference to the Process object
	 */
	public Service getSelfAsService() {
		return new Service(this.id, this.getPublicKey());
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * This method returns the Public Key in a readable String format
	 *
	 * @return a String with the public key
	 */
	public String getPublicKeyString() {
		return RSAUtilities.publicKeyString(publicKey);
	}

	/**
	 * This method return the list of all known Services
	 *
	 * @return HashSet with the known Services
	 */
	public HashSet<Service> getList() {
		return this.servicesList;
	}

	/**
	 * This method insert a Process in the known service list
	 *
	 * @param service - Service to be add to the list
	 */
	public void addService(Service service) {
		this.servicesList.add(service);
	}

	/**
	 * This method insert a Process in the known Service list from a String
	 *
	 * @param message - String with the Service parameters
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void addService(String message) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String out[] = message.split(",");
		Service service = new Service(Integer.parseInt(out[0]), out[1]);
		this.servicesList.add(service);
	}

	/**
	 * This method removes a Process from the known Service list
	 *
	 * @param service - Service to be removed
	 */
	public void removeList(Service service) {
		this.servicesList.remove(service);
	}

	/**
	 * This method removes a Process from the known Service list
	 * 
	 * @param message - String with the Service parameters
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void removeList(String message) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String out[] = message.split(",");
		Service service = new Service(Integer.parseInt(out[0]),out[1]);
		this.servicesList.remove(service);
	}

	/**
	 * This method returns the Process with a given id from the known Service list
	 * 
	 * @param id - Identification of the Process
	 * @return - Service Object of the Process
	 */
	public Service getService(int id) {
		for (Service service : servicesList)
			if (service.getId()==id) return service;
		return null;
	}
	//------------------------------String Generation------------------------------\\
	/**
	 * Generate a String in a hello format
	 *
	 * @return a String in the format "/h <id>,<publicKey>,"
	 */
	public String sayHello() {
		return "/h " + this.id + "," + this.getPublicKeyString() +  ",";
	}

	/**
	 * Generate a String in the "tie breaker vote" format
	 * A good practice is to convert numbers to string before signing
	 *
	 * @param message - String with the message to be signed 
	 * @return a String in the format "/c <id>,<message>,<signned(message)>,"
	 * @throws java.lang.Exception
	 */
	public String signMessage(String message) throws Exception {
		return "/c " + this.id + "," + message + "," + RSAUtilities.sign(message, privateKey) + ",";
	}

	/**
	 * This method parses a String looking for specific tokens and execute a correspondent action
	 *
	 * Token -- Description -- Expected Message -- Action
	 * /h -- Hello from other Process -- /h <id>,<publicKey> -- Add process into the service list
	 * /c -- Receive a signed message -- /t <id>,<message>,<hash(message)> -- 
	 *
	 * @param message - the String to be parsed
	 * @throws Exception 
	 */
	public void parseMessage(String message) throws Exception {
		String out[]; 
		if (message.startsWith("/h")) {
			message = message.substring(3);
			addService(message);
		} else if (message.startsWith("/c")) {
			out = message.substring(3).split(",");
			int serviceID = Integer.parseInt(out[0]);
			Service service = getService(serviceID);
			if (RSAUtilities.verify(out[1], out[2], service.getPublicKey())) {
			}
		} 
	}

	/**
	 * Main activities of the Process are executed here
	 */
	public void run() {
		int port = 2424;
		System.out.println("Process " + this.id + ": Started Running");

		//Start the Multicast connection
		Multicast multicast = new Multicast("224.42.42.42", 4242, this);
		multicast.start();

		//Start the service UDP Server
		UDPServer server = new UDPServer(port, this);
		server.run();
	}
}
