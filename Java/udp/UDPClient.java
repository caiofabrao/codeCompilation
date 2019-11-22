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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A custom UDP Client Class that sends two types of messafes with the server
 * 
 * @author Caio Fabrao
 */
public class UDPClient {

    public UDPClient() {
    }

    /**
     *  This method creates a socket to request the current time of the a given UDP Server. It calculates the 
     *   the RTT (Round Trip Time) of the request and returns a estimative of the accurate server time 
     * 
     * @param server - address of the server
     * @param serverPort - port number of the server
     * @param message - message to be send over the socket
     * @return a Long with the estimate server time
     */
    public Long requestTime(String server, int serverPort, String message) {
        try (DatagramSocket socket = new DatagramSocket()) { // Get the first available port
            
            byte[] m = message.getBytes(UTF_8);
            InetAddress aHost = InetAddress.getByName(server);
            // Begin of the RTT count [---
            long msRTT = System.currentTimeMillis();
            DatagramPacket request
                    = new DatagramPacket(m, message.length(), aHost, serverPort);
            socket.send(request);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            msRTT = (System.currentTimeMillis() - msRTT) / 2;
            // End of the RTT count---]
            long time = Long.parseLong(new String(reply.getData(), 0, reply.getLength()));
            socket.close();
            return (time + msRTT);
        } catch (Exception ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            return new Long(-1);
        }
    }

    /**
     *  This method sends a time adjustment to a given UDP Server.
     * 
     * @param server - address of the server
     * @param serverPort - port number of the server
     * @param message - message to be send over the socket
     */
    public void sendAdjustment(String server, int serverPort, String message) {
        try (DatagramSocket socket = new DatagramSocket()) { // Get the first available port
            socket.setSoTimeout(5000);
            byte[] m = message.getBytes(UTF_8);
            InetAddress host = InetAddress.getByName(server);
            DatagramPacket request
                    = new DatagramPacket(m, message.length(), host, serverPort);
            socket.send(request);
            socket.close();
        } catch (Exception ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
