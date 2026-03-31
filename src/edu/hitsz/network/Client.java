package edu.hitsz.network;

import edu.hitsz.aircraft.RemotePlayerAircraft;
import edu.hitsz.application.RemotePlayerController;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Client {
    private static final Client instance = new Client();

    public static Client getInstance() {
        return instance;
    }

    private Client() {}

    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    private int PlayerId = -1;
    private boolean isRunning = true;

    private final ConcurrentLinkedQueue<NetEvent> inboundQueue = new ConcurrentLinkedQueue<>();

    private final ConcurrentLinkedQueue<byte[]> outboundQueue = new ConcurrentLinkedQueue<>();


    public void connect(String ip, int port) throws Exception {
        this.serverAddress = InetAddress.getByName(ip);
        this.serverPort = port;
        this.socket = new DatagramSocket();
        this.isRunning = true;

        startListening();
        startSending(); // 启动专属发送线程
    }

    public void startListening() {
        new Thread(() -> {
            System.out.println("Client Start Listening Thread");
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

        }, "Net-Receive-Thread").start();
    }

    public void startSending() {
        new Thread(() -> {
            System.out.println("Client Start Sending Thread");
            while (isRunning) {
                try {
                    byte[] data = outboundQueue.poll();
                    if (data != null) {
                        DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
                        socket.send(packet);
                    } else {
                        Thread.sleep(2);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Net-Send-Thread").start();
    }

    public NetEvent pollEvent() {
        return inboundQueue.poll();
    }

    public void sendPacket(byte[] data) {
        outboundQueue.offer(data);
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
                PlayerMoveEvent event = new PlayerMoveEvent(x, y);
                inboundQueue.offer(event);
                break;

        }
    }

    public void sendJoinRequest() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) 0x01);
        sendPacket(buffer.array());
        System.out.println("SendingRequest");
    }

    public void updateLocation(int locationX, int locationY) {
        ByteBuffer buffer = ByteBuffer.allocate(13);
        buffer.put((byte) 0x03);
        buffer.putInt(PlayerId);
        buffer.putInt(locationX);
        buffer.putInt(locationY);

        sendPacket(buffer.array());
    }

}
