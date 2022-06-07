package io.netty.buffer;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UShort;

/* loaded from: classes4.dex */
public class ByteBufInputStream extends InputStream implements DataInput {
    private final ByteBuf a;
    private final int b;
    private final int c;
    private final StringBuilder d;

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    public ByteBufInputStream(ByteBuf byteBuf) {
        this(byteBuf, byteBuf.readableBytes());
    }

    public ByteBufInputStream(ByteBuf byteBuf, int i) {
        this.d = new StringBuilder();
        if (byteBuf == null) {
            throw new NullPointerException("buffer");
        } else if (i < 0) {
            throw new IllegalArgumentException("length: " + i);
        } else if (i <= byteBuf.readableBytes()) {
            this.a = byteBuf;
            this.b = byteBuf.readerIndex();
            this.c = this.b + i;
            byteBuf.markReaderIndex();
        } else {
            throw new IndexOutOfBoundsException("Too many bytes to be read - Needs " + i + ", maximum is " + byteBuf.readableBytes());
        }
    }

    public int readBytes() {
        return this.a.readerIndex() - this.b;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.c - this.a.readerIndex();
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.a.markReaderIndex();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (!this.a.isReadable()) {
            return -1;
        }
        return this.a.readByte() & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int available = available();
        if (available == 0) {
            return -1;
        }
        int min = Math.min(available, i2);
        this.a.readBytes(bArr, i, min);
        return min;
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        this.a.resetReaderIndex();
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (j > 2147483647L) {
            return skipBytes(Integer.MAX_VALUE);
        }
        return skipBytes((int) j);
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws IOException {
        a(1);
        return read() != 0;
    }

    @Override // java.io.DataInput
    public byte readByte() throws IOException {
        if (this.a.isReadable()) {
            return this.a.readByte();
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public char readChar() throws IOException {
        return (char) readShort();
    }

    @Override // java.io.DataInput
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @Override // java.io.DataInput
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        a(i2);
        this.a.readBytes(bArr, i, i2);
    }

    @Override // java.io.DataInput
    public int readInt() throws IOException {
        a(4);
        return this.a.readInt();
    }

    @Override // java.io.DataInput
    public String readLine() throws IOException {
        this.d.setLength(0);
        while (this.a.isReadable()) {
            short readUnsignedByte = this.a.readUnsignedByte();
            if (readUnsignedByte != 10) {
                if (readUnsignedByte != 13) {
                    this.d.append((char) readUnsignedByte);
                } else if (this.a.isReadable()) {
                    ByteBuf byteBuf = this.a;
                    if (((char) byteBuf.getUnsignedByte(byteBuf.readerIndex())) == '\n') {
                        this.a.skipBytes(1);
                    }
                }
            }
            return this.d.toString();
        }
        if (this.d.length() > 0) {
            return this.d.toString();
        }
        return null;
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        a(8);
        return this.a.readLong();
    }

    @Override // java.io.DataInput
    public short readShort() throws IOException {
        a(2);
        return this.a.readShort();
    }

    @Override // java.io.DataInput
    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws IOException {
        return readByte() & 255;
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws IOException {
        return readShort() & UShort.MAX_VALUE;
    }

    @Override // java.io.DataInput
    public int skipBytes(int i) throws IOException {
        int min = Math.min(available(), i);
        this.a.skipBytes(min);
        return min;
    }

    private void a(int i) throws IOException {
        if (i < 0) {
            throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
        } else if (i > available()) {
            throw new EOFException("fieldSize is too long! Length is " + i + ", but maximum is " + available());
        }
    }
}
