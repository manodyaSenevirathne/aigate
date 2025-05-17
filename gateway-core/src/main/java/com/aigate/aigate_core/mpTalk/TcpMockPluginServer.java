package com.aigate.aigate_core.mpTalk;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TcpMockPluginServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7070);
        System.out.println("✅ Plugin TCP Server started on port 7070");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("🔌 Client connected");

            try (
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream())
            ) {
                // First read 8 bytes: header
                byte[] header = new byte[8];
                in.readFully(header);

                ByteBuffer headerBuf = ByteBuffer.wrap(header);
                short magic = headerBuf.getShort();
                byte version = headerBuf.get();
                byte type = headerBuf.get();
                int length = headerBuf.getInt();

                // Now read payload
                byte[] payloadBuffer = new byte[length];
                in.readFully(payloadBuffer);

                // Combine and deserialize
                ByteArrayOutputStream fullMessage = new ByteArrayOutputStream();
                fullMessage.write(header);
                fullMessage.write(payloadBuffer);

                Payload payload = BinaryMessageUtil.deserialize(fullMessage.toByteArray());
                System.out.println("📥 Received: " + payload.getBody());

                // Process
                payload.setBody(payload.getBody() + " | Processed by plugin");

                // Serialize and respond
                byte[] response = BinaryMessageUtil.serialize(payload);
                out.write(response);
                out.flush();

                System.out.println("📤 Sent response to client");
            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                socket.close();
            }
        }
    }
}
