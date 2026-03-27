package edu.hitsz.network;

import edu.hitsz.aircraft.RemotePlayerAircraft;
import edu.hitsz.application.RemotePlayerController;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;


public class Client {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    private int PlayerId = -1;
    private boolean isRunning = true;

    public Client(String ip, int port) throws Exception {
        this.serverAddress = InetAddress.getByName(ip);
        this.serverPort = port;
        this.socket = new DatagramSocket();
    }

    public void startListening() {
        new Thread(() -> {
            System.out.println("Starting");
            byte[] receiveBuffer = new byte[1024];

            while (isRunning) {
                try {
                    DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socket.receive(packet);
                    handlePacket(packet.getData(), packet.getLength());
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        }).start();
    }

    private void handlePacket(byte[] data, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(data, 0, length);

        byte opCode = buffer.get();

        switch(opCode) {
            case 0x02:
                int assignedId = buffer.getInt();
                this.PlayerId = assignedId;
                System.out.println("Enter Room ! My ID is " + assignedId);
                break;

            case 0x03:
                int playerId = buffer.getInt();
                int x = buffer.getInt();
                int y = buffer.getInt();
                System.out.printf(" %d is at %d %d", playerId, x, y);
                RemotePlayerController.updateRemotePlayer(playerId, x, y, 100);
                break;

        }
    }

    public void sendJoinRequest() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1);
            buffer.put((byte) 0x01);

            byte[] data = buffer.array();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Sending Request");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateState(int locationX, int locationY, int hp) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(13);
            buffer.put((byte) 0x03);
            buffer.putInt(PlayerId);
            buffer.putInt(locationX);
            buffer.putInt(locationY);

            byte[] data = buffer.array();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
