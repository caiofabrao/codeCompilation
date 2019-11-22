/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

/**
 *
 * @author Alex Cid
 */
import java.net.*;
import java.io.*;

public class TCPClient {

    public static void main(String args[]) {
        String message = "ULTRA COMBO";
        String server = "localhost";
        int serverPort = 7896;

        sendMsg(message, server, serverPort);
    }

    public static void sendMsg(String message, String server, int serverPort) {
        // arguments supply message and hostname
        Socket s = null;
        try {
            s = new Socket(server, serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeUTF(message);      	// UTF is a string encoding see Sn. 4.4
            String data = in.readUTF();	    // read a line of data from the stream
            System.out.println("Received: " + data);
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
}
