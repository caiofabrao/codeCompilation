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

/**
 * A custom Multicast Class that manager a service in a given group of Time
 * Synchronization. It is responsible to enter the multicast group and start all
 * the secondary threads, e.g. start a listener, announce itself in the group
 * (hello), verify the presence of a Master, call election for new Master. If
 * the service become the Master, it starts the thread TimeServer 
 *
 * @author Caio Fabrao
 */
import java.net.*;
import java.io.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.logging.Level;
import java.util.logging.Logger;

import agents.Process;

public class Multicast extends Thread {

	//ENUM = { HELLO, VOTING, ENDING}
    private final String multicastServer;
    private final int multicastPort;
    private final Process process;
    
    public Multicast() {
    	this.multicastServer = "244.244.244.244";
    	this.multicastPort = 4242;
    	this.process = new Process();
    }
    
    public Multicast(String multicastServer, int multicastPort, Process process) {
        this.multicastServer = multicastServer;
        this.multicastPort = multicastPort;
        this.process = process;
    }

    @Override
    public void run() {
        MulticastSocket multicastSocket = null;
        try {
            // Joining a multicast group
        	InetAddress group = InetAddress.getByName(multicastServer);
            multicastSocket = new MulticastSocket(multicastPort);
            multicastSocket.joinGroup(group);
            // Execution Milestone
            System.out.println("Multicast Group Joined");

            // Starting Listener
            ListenerMulticast navi = new ListenerMulticast(multicastSocket, process);
            navi.start();

            // First Hello
    		byte[] messageByte = process.sayHello().getBytes(UTF_8);
            DatagramPacket messageOut = new DatagramPacket(messageByte, messageByte.length, group, multicastPort);
            multicastSocket.send(messageOut);
            
            multicastSocket.leaveGroup(group);	
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Multicast.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
        } catch (IOException ex) {
            Logger.getLogger(Multicast.class.getName()).log(Level.SEVERE, null, ex);
        } finally {if(multicastSocket != null) multicastSocket.close();}
    }
}