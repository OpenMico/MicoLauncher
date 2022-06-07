package com.xiaomi.miplay.mylibrary.mirror;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import kotlin.UShort;

/* loaded from: classes4.dex */
public class ControlMessageReader {
    public static final int CLIPBOARD_TEXT_MAX_LENGTH = 4093;
    public static final int TEXT_MAX_LENGTH = 300;
    private final byte[] a = new byte[1024];
    private final ByteBuffer b = ByteBuffer.wrap(this.a);
    private final byte[] c = new byte[4093];

    private static int a(byte b) {
        return b & 255;
    }

    private static int a(short s) {
        return s & UShort.MAX_VALUE;
    }

    public ControlMessageReader() {
        this.b.limit(0);
    }

    public boolean isFull() {
        return this.b.remaining() == this.a.length;
    }

    public void readFrom(InputStream inputStream) throws IOException {
        if (!isFull()) {
            this.b.compact();
            int position = this.b.position();
            byte[] bArr = this.a;
            int read = inputStream.read(bArr, position, bArr.length - position);
            if (read != -1) {
                this.b.position(position + read);
                this.b.flip();
                return;
            }
            throw new EOFException("Controller socket closed");
        }
        throw new IllegalStateException("Buffer full, call next() to consume");
    }

    public ControlMessage next() {
        ControlMessage controlMessage = null;
        if (!this.b.hasRemaining()) {
            return null;
        }
        int position = this.b.position();
        byte b = this.b.get();
        switch (b) {
            case 0:
                controlMessage = a();
                break;
            case 1:
                controlMessage = c();
                break;
            case 2:
                controlMessage = d();
                break;
            case 3:
                controlMessage = e();
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
                controlMessage = ControlMessage.createEmpty(b);
                break;
            case 8:
                controlMessage = f();
                break;
            case 9:
                controlMessage = g();
                break;
            default:
                Ln.w("Unknown event type: " + ((int) b));
                break;
        }
        if (controlMessage == null) {
            this.b.position(position);
        }
        return controlMessage;
    }

    private ControlMessage a() {
        if (this.b.remaining() < 9) {
            return null;
        }
        return ControlMessage.createInjectKeycode(a(this.b.get()), this.b.getInt(), this.b.getInt());
    }

    private String b() {
        int a;
        if (this.b.remaining() < 2 || this.b.remaining() < (a = a(this.b.getShort()))) {
            return null;
        }
        this.b.get(this.c, 0, a);
        return new String(this.c, 0, a, StandardCharsets.UTF_8);
    }

    private ControlMessage c() {
        String b = b();
        if (b == null) {
            return null;
        }
        return ControlMessage.createInjectText(b);
    }

    private ControlMessage d() {
        if (this.b.remaining() < 21) {
            return null;
        }
        int a = a(this.b.get());
        long j = this.b.getLong();
        Position a2 = a(this.b);
        int a3 = a(this.b.getShort());
        return ControlMessage.createInjectTouchEvent(a, j, a2, a3 == 65535 ? 1.0f : a3 / 65536.0f, this.b.getInt());
    }

    private ControlMessage e() {
        if (this.b.remaining() < 20) {
            return null;
        }
        return ControlMessage.createInjectScrollEvent(a(this.b), this.b.getInt(), this.b.getInt());
    }

    private ControlMessage f() {
        String b = b();
        if (b == null) {
            return null;
        }
        return ControlMessage.createSetClipboard(b);
    }

    private ControlMessage g() {
        if (this.b.remaining() < 1) {
            return null;
        }
        return ControlMessage.createSetScreenPowerMode(this.b.get());
    }

    private static Position a(ByteBuffer byteBuffer) {
        return new Position(byteBuffer.getInt(), byteBuffer.getInt(), a(byteBuffer.getShort()), a(byteBuffer.getShort()));
    }
}
