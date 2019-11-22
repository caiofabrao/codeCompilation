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
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import static java.nio.charset.StandardCharsets.UTF_8;
import agents.Process;

/**
 * A custom UDP Server Class that wait for two signed commands from a time server
 * 
 *  /t is a command that request the current local time
 * 
 *  /a is a command that sends a time adjustment
 * 
 * @author Caio Fabrao
 */
public class UDPServer {
    private final int port;
    private final Process process;

    public UDPServer(int port, Process process) {
        this.port = port;
        this.process = process;
    }

    public void run() {
        // Create socket at agreed port
        try (DatagramSocket socket = new DatagramSocket(port)) {
            byte[] buffer = new byte[1000];
            System.out.println("UDP Server Started");

            process.getId();
            
            while (true) {
            	// Received Messages
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);
                String message = new String(request.getData());

                
                // Send Messages
                byte[] messageBytes  = message.getBytes(UTF_8);
                DatagramPacket reply = new DatagramPacket(messageBytes, messageBytes.length,
                		request.getAddress(), request.getPort());
                socket.send(reply);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}
