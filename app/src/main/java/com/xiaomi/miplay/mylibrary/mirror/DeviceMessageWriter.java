package com.xiaomi.miplay.mylibrary.mirror;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/* loaded from: classes4.dex */
public class DeviceMessageWriter {
    public static final int CLIPBOARD_TEXT_MAX_LENGTH = 4093;
    private final byte[] a = new byte[4096];
    private final ByteBuffer b = ByteBuffer.wrap(this.a);

    public void writeTo(DeviceMessage deviceMessage, OutputStream outputStream) throws IOException {
        this.b.clear();
        this.b.put((byte) 0);
        if (deviceMessage.getType() != 0) {
            Ln.w("Unknown device message: " + deviceMessage.getType());
            return;
        }
        byte[] bytes = deviceMessage.getText().getBytes(StandardCharsets.UTF_8);
        int utf8TruncationIndex = StringUtils.getUtf8TruncationIndex(bytes, 4093);
        this.b.putShort((short) utf8TruncationIndex);
        this.b.put(bytes, 0, utf8TruncationIndex);
        outputStream.write(this.a, 0, this.b.position());
    }
}
