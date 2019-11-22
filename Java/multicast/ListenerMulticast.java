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
package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;
import agents.Process;

/**
 * A custom Class that implements a multicast listener for all the receive
 * messages in a given multicast socket. It also parses the receive messages and
 * execute a correspondent action
 *
 * @author Caio Fabrao
 */
public class ListenerMulticast extends Thread {

	MulticastSocket multicastSocket;
	Process process;

	public ListenerMulticast(MulticastSocket multicastSocket, Process process) {
		this.multicastSocket = multicastSocket;
		this.process = process;
	}

	@Override
	public void run() {
		System.out.println("Multicast Listener");
		try {
			while (true) {
				byte[] buffer = new byte[1000];
				DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
				multicastSocket.receive(messageIn);
				String message = (new String(messageIn.getData()));
				parseMessage(message);
			}
		} catch (SocketException e) {
			System.err.println("ThrSocket: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("ThrIO: " + e.getMessage());
		}
	}

	public void parseMessage(String message) {
		System.out.println(message);
	}
}
