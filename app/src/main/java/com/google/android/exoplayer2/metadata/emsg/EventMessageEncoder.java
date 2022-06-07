package com.google.android.exoplayer2.metadata.emsg;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class EventMessageEncoder {
    private final ByteArrayOutputStream a = new ByteArrayOutputStream(512);
    private final DataOutputStream b = new DataOutputStream(this.a);

    public byte[] encode(EventMessage eventMessage) {
        this.a.reset();
        try {
            a(this.b, eventMessage.schemeIdUri);
            a(this.b, eventMessage.value != null ? eventMessage.value : "");
            this.b.writeLong(eventMessage.durationMs);
            this.b.writeLong(eventMessage.id);
            this.b.write(eventMessage.messageData);
            this.b.flush();
            return this.a.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void a(DataOutputStream dataOutputStream, String str) throws IOException {
        dataOutputStream.writeBytes(str);
        dataOutputStream.writeByte(0);
    }
}
