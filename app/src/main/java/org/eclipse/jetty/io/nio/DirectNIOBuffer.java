package org.eclipse.jetty.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import org.eclipse.jetty.io.AbstractBuffer;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes5.dex */
public class DirectNIOBuffer extends AbstractBuffer implements NIOBuffer {
    protected final ByteBuffer _buf;
    private ReadableByteChannel _in;
    private InputStream _inStream;
    private WritableByteChannel _out;
    private OutputStream _outStream;

    @Override // org.eclipse.jetty.io.Buffer
    public byte[] array() {
        return null;
    }

    @Override // org.eclipse.jetty.io.nio.NIOBuffer
    public boolean isDirect() {
        return true;
    }

    public DirectNIOBuffer(int i) {
        super(2, false);
        this._buf = ByteBuffer.allocateDirect(i);
        this._buf.position(0);
        ByteBuffer byteBuffer = this._buf;
        byteBuffer.limit(byteBuffer.capacity());
    }

    public DirectNIOBuffer(ByteBuffer byteBuffer, boolean z) {
        super(z ? 0 : 2, false);
        if (byteBuffer.isDirect()) {
            this._buf = byteBuffer;
            setGetIndex(byteBuffer.position());
            setPutIndex(byteBuffer.limit());
            return;
        }
        throw new IllegalArgumentException();
    }

    public DirectNIOBuffer(File file) throws IOException {
        super(1, false);
        this._buf = new FileInputStream(file).getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
        setGetIndex(0);
        setPutIndex((int) file.length());
        this._access = 0;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int capacity() {
        return this._buf.capacity();
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte peek(int i) {
        return this._buf.get(i);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int peek(int i, byte[] bArr, int i2, int i3) {
        if ((i + i3 > capacity() && (i3 = capacity() - i) == 0) || i3 < 0) {
            return -1;
        }
        try {
            this._buf.position(i);
            this._buf.get(bArr, i2, i3);
            return i3;
        } finally {
            this._buf.position(0);
        }
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void poke(int i, byte b) {
        if (isReadOnly()) {
            throw new IllegalStateException("READONLY");
        } else if (i < 0) {
            throw new IllegalArgumentException("index<0: " + i + "<0");
        } else if (i <= capacity()) {
            this._buf.put(i, b);
        } else {
            throw new IllegalArgumentException("index>capacity(): " + i + ">" + capacity());
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i, Buffer buffer) {
        if (!isReadOnly()) {
            byte[] array = buffer.array();
            if (array != null) {
                return poke(i, array, buffer.getIndex(), buffer.length());
            }
            Buffer buffer2 = buffer.buffer();
            if (!(buffer2 instanceof DirectNIOBuffer)) {
                return super.poke(i, buffer);
            }
            ByteBuffer byteBuffer = ((DirectNIOBuffer) buffer2)._buf;
            ByteBuffer byteBuffer2 = this._buf;
            if (byteBuffer == byteBuffer2) {
                byteBuffer = byteBuffer2.duplicate();
            }
            try {
                this._buf.position(i);
                int remaining = this._buf.remaining();
                int length = buffer.length();
                if (length <= remaining) {
                    remaining = length;
                }
                byteBuffer.position(buffer.getIndex());
                byteBuffer.limit(buffer.getIndex() + remaining);
                this._buf.put(byteBuffer);
                return remaining;
            } finally {
                this._buf.position(0);
                byteBuffer.limit(byteBuffer.capacity());
                byteBuffer.position(0);
            }
        } else {
            throw new IllegalStateException("READONLY");
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i, byte[] bArr, int i2, int i3) {
        if (isReadOnly()) {
            throw new IllegalStateException("READONLY");
        } else if (i < 0) {
            throw new IllegalArgumentException("index<0: " + i + "<0");
        } else if (i + i3 <= capacity() || (i3 = capacity() - i) >= 0) {
            try {
                this._buf.position(i);
                int remaining = this._buf.remaining();
                if (i3 <= remaining) {
                    remaining = i3;
                }
                if (remaining > 0) {
                    this._buf.put(bArr, i2, remaining);
                }
                return remaining;
            } finally {
                this._buf.position(0);
            }
        } else {
            throw new IllegalArgumentException("index>capacity(): " + i + ">" + capacity());
        }
    }

    @Override // org.eclipse.jetty.io.nio.NIOBuffer
    public ByteBuffer getByteBuffer() {
        return this._buf;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0045, code lost:
        r9._in = null;
        r9._inStream = r10;
     */
    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int readFrom(java.io.InputStream r10, int r11) throws java.io.IOException {
        /*
            r9 = this;
            java.nio.channels.ReadableByteChannel r0 = r9._in
            if (r0 == 0) goto L_0x000e
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto L_0x000e
            java.io.InputStream r0 = r9._inStream
            if (r10 == r0) goto L_0x0016
        L_0x000e:
            java.nio.channels.ReadableByteChannel r0 = java.nio.channels.Channels.newChannel(r10)
            r9._in = r0
            r9._inStream = r10
        L_0x0016:
            if (r11 < 0) goto L_0x001e
            int r0 = r9.space()
            if (r11 <= r0) goto L_0x0022
        L_0x001e:
            int r11 = r9.space()
        L_0x0022:
            int r0 = r9.putIndex()
            r1 = 0
            r3 = r11
            r2 = r0
            r0 = r1
            r4 = r0
            r5 = r4
        L_0x002c:
            r6 = 0
            if (r0 >= r11) goto L_0x0087
            java.nio.ByteBuffer r5 = r9._buf     // Catch: IOException -> 0x0064, all -> 0x0062
            r5.position(r2)     // Catch: IOException -> 0x0064, all -> 0x0062
            java.nio.ByteBuffer r5 = r9._buf     // Catch: IOException -> 0x0064, all -> 0x0062
            int r7 = r2 + r3
            r5.limit(r7)     // Catch: IOException -> 0x0064, all -> 0x0062
            java.nio.channels.ReadableByteChannel r5 = r9._in     // Catch: IOException -> 0x0064, all -> 0x0062
            java.nio.ByteBuffer r7 = r9._buf     // Catch: IOException -> 0x0064, all -> 0x0062
            int r5 = r5.read(r7)     // Catch: IOException -> 0x0064, all -> 0x0062
            if (r5 >= 0) goto L_0x004a
            r9._in = r6     // Catch: IOException -> 0x0064, all -> 0x0062
            r9._inStream = r10     // Catch: IOException -> 0x0064, all -> 0x0062
            goto L_0x0087
        L_0x004a:
            if (r5 <= 0) goto L_0x0054
            int r2 = r2 + r5
            int r0 = r0 + r5
            int r3 = r3 - r5
            r9.setPutIndex(r2)     // Catch: IOException -> 0x0064, all -> 0x0062
            r4 = r1
            goto L_0x005b
        L_0x0054:
            int r7 = r4 + 1
            r8 = 1
            if (r4 <= r8) goto L_0x005a
            goto L_0x0087
        L_0x005a:
            r4 = r7
        L_0x005b:
            int r7 = r10.available()     // Catch: IOException -> 0x0064, all -> 0x0062
            if (r7 > 0) goto L_0x002c
            goto L_0x0087
        L_0x0062:
            r11 = move-exception
            goto L_0x006a
        L_0x0064:
            r11 = move-exception
            r9._in = r6     // Catch: all -> 0x0062
            r9._inStream = r10     // Catch: all -> 0x0062
            throw r11     // Catch: all -> 0x0062
        L_0x006a:
            java.nio.channels.ReadableByteChannel r0 = r9._in
            if (r0 == 0) goto L_0x0078
            boolean r0 = r0.isOpen()
            if (r0 != 0) goto L_0x0078
            r9._in = r6
            r9._inStream = r10
        L_0x0078:
            java.nio.ByteBuffer r10 = r9._buf
            r10.position(r1)
            java.nio.ByteBuffer r10 = r9._buf
            int r0 = r10.capacity()
            r10.limit(r0)
            throw r11
        L_0x0087:
            if (r5 >= 0) goto L_0x00a9
            if (r0 != 0) goto L_0x00a9
            r11 = -1
            java.nio.channels.ReadableByteChannel r0 = r9._in
            if (r0 == 0) goto L_0x009a
            boolean r0 = r0.isOpen()
            if (r0 != 0) goto L_0x009a
            r9._in = r6
            r9._inStream = r10
        L_0x009a:
            java.nio.ByteBuffer r10 = r9._buf
            r10.position(r1)
            java.nio.ByteBuffer r10 = r9._buf
            int r0 = r10.capacity()
            r10.limit(r0)
            return r11
        L_0x00a9:
            java.nio.channels.ReadableByteChannel r11 = r9._in
            if (r11 == 0) goto L_0x00b7
            boolean r11 = r11.isOpen()
            if (r11 != 0) goto L_0x00b7
            r9._in = r6
            r9._inStream = r10
        L_0x00b7:
            java.nio.ByteBuffer r10 = r9._buf
            r10.position(r1)
            java.nio.ByteBuffer r10 = r9._buf
            int r11 = r10.capacity()
            r10.limit(r11)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.io.nio.DirectNIOBuffer.readFrom(java.io.InputStream, int):int");
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public void writeTo(OutputStream outputStream) throws IOException {
        WritableByteChannel writableByteChannel = this._out;
        if (writableByteChannel == null || !writableByteChannel.isOpen() || outputStream != this._outStream) {
            this._out = Channels.newChannel(outputStream);
            this._outStream = outputStream;
        }
        synchronized (this._buf) {
            int i = 0;
            while (hasContent() && this._out.isOpen()) {
                try {
                    this._buf.position(getIndex());
                    this._buf.limit(putIndex());
                    int write = this._out.write(this._buf);
                    if (write < 0) {
                        break;
                    } else if (write > 0) {
                        skip(write);
                        i = 0;
                    } else {
                        i++;
                        if (i > 1) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    this._out = null;
                    this._outStream = null;
                    throw e;
                }
            }
            if (this._out != null && !this._out.isOpen()) {
                this._out = null;
                this._outStream = null;
            }
            this._buf.position(0);
            this._buf.limit(this._buf.capacity());
        }
    }
}
