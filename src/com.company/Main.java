package com.company;

/*  0                1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9  0  1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |LI | VN  |Mode |    Stratum    |     Poll      |   Precision    |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                          Root  Delay                           |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                       Root  Dispersion                         |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                     Reference Identifier                       |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                                                                |
    |                    Reference Timestamp (64)                    |
    |                                                                |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                                                                |
    |                    Originate Timestamp (64)                    |
    |                                                                |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                                                                |
    |                     Receive Timestamp (64)                     |
    |                                                                |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                                                                |
    |                     Transmit Timestamp (64)                    |
    |                                                                |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                 Key Identifier (optional) (32)                 |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                                                                |
    |                                                                |
    |                 Message Digest (optional) (128)                |
    |                                                                |
    |                                                                |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+*/


import java.io.IOException;
import java.net.*;

public class Main {

    public static void main(String[] args) {
        String sntpToArray[] = {
                "gbg1.ntp.se", "gbg2.ntp.se",
                "mmo1.ntp.se", "mmo2.ntp.se",
                "sth1.ntp.se", "sth2.ntp.se",
                "svl1.ntp.se", "svl2.ntp.se"};
        int serverStep = 0;

        do {

            try {

                DatagramSocket socket = new DatagramSocket();
                InetAddress address = InetAddress.getByName(sntpToArray[serverStep]);
                SNTPMessage message = new SNTPMessage();
                byte[] buf = message.toByteArray();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);
                System.out.println("Lyckades ansluta till: " + sntpToArray[serverStep]);
                serverStep = 0;

                socket.send(packet);
                System.out.println("Sent request");
                socket.receive(packet);
                SNTPMessage response = new SNTPMessage(packet.getData());
                System.out.println("Got reply");
                socket.close();
                System.out.println();
                response.time();
                response.toString();


            } catch (IOException e) {
                System.err.println("Could Not Connect To: " + sntpToArray[serverStep]);
                serverStep++;
                //e.printStackTrace();
            }
        }
        while (serverStep != 0);

    }
//Nedan är ett exempel på ett meddelande från en time server
/*        byte [] buf = {  36,  1,  0, -25,
                0,   0,  0,   0,
                0,   0,  0,   2,
                80,  80, 83,   0,
                -29, 116,  5, 61,  0,  0,    0,   0,
                -29, 116,  5, 59, 14, 86,    0,   0,
                -29, 116,  5, 62,  0, 47, -121, -38,
                -29, 116,  5, 62,  0, 47, -113,  -1};*/

    //Första byten 36
    //  0 1  2 3 4 5 6 7
    // |LI |  VN  | Mode |
    //  0 0 1 0 0  1 0 0
    //   0    4      4
    //
    // 0000 0000 -> 0
    // 0000 0001 -> 1
    // 0000 0010 -> 2
    // 0000 0011 -> 3
    // 0000 0100 -> 4
    // 0000 0101 -> 5
/*
        SNTPMessage msg = new SNTPMessage(buf);*/

}
