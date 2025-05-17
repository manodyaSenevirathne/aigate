package com.aigate.aigate_core.mpTalk;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Sender {

    public static Payload sendPayload(String host, int port, Payload payload) {
        try (Socket socket = new Socket(host, port)) {
            OutputStream out = socket.getOutputStream();
            DataInputStream in = new DataInputStream(socket.getInputStream());

            byte[] serialized = BinaryMessageUtil.serialize(payload);
            out.write(serialized);
            out.flush();

            // First read 8 bytes
            byte[] header = new byte[8];
            in.readFully(header);
            ByteBuffer headerBuf = ByteBuffer.wrap(header);
            short magic = headerBuf.getShort();
            byte version = headerBuf.get();
            byte type = headerBuf.get();
            int length = headerBuf.getInt();

            // Read payload
            byte[] payloadBuffer = new byte[length];
            in.readFully(payloadBuffer);

            // Combine
            ByteArrayOutputStream full = new ByteArrayOutputStream();
            full.write(header);
            full.write(payloadBuffer);

            return BinaryMessageUtil.deserialize(full.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
