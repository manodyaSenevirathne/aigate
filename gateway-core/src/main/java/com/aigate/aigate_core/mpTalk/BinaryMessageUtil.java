package com.aigate.aigate_core.mpTalk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;

public class BinaryMessageUtil {
    private static final short MAGIC = (short) 0xA1B2;
    private static final byte VERSION = 1;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static byte[] serialize(Payload payload) throws Exception {
        byte[] payloadBytes = mapper.writeValueAsBytes(payload);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        out.writeShort(MAGIC);           // 2 bytes
        out.writeByte(VERSION);          // 1 byte
        out.writeByte(1);                // 1 byte: type = 1 (request)
        out.writeInt(payloadBytes.length); // 4 bytes
        out.write(payloadBytes);         // variable

        return baos.toByteArray();
    }

    public static Payload deserialize(byte[] data) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        short magic = buffer.getShort();
        if (magic != MAGIC) throw new IllegalArgumentException("Invalid magic number");

        byte version = buffer.get();
        byte type = buffer.get();
        int length = buffer.getInt();

        byte[] payloadBytes = new byte[length];
        buffer.get(payloadBytes);

        return mapper.readValue(payloadBytes, Payload.class);
    }
}
