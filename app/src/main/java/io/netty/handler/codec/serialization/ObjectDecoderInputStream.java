package io.netty.handler.codec.serialization;

import com.xiaomi.smarthome.setting.ServerSetting;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;

/* loaded from: classes4.dex */
public class ObjectDecoderInputStream extends InputStream implements ObjectInput {
    private final DataInputStream a;
    private final int b;
    private final ClassResolver c;

    public ObjectDecoderInputStream(InputStream inputStream) {
        this(inputStream, (ClassLoader) null);
    }

    public ObjectDecoderInputStream(InputStream inputStream, ClassLoader classLoader) {
        this(inputStream, classLoader, 1048576);
    }

    public ObjectDecoderInputStream(InputStream inputStream, int i) {
        this(inputStream, null, i);
    }

    public ObjectDecoderInputStream(InputStream inputStream, ClassLoader classLoader, int i) {
        if (inputStream == null) {
            throw new NullPointerException(ServerSetting.SERVER_IN);
        } else if (i > 0) {
            if (inputStream instanceof DataInputStream) {
                this.a = (DataInputStream) inputStream;
            } else {
                this.a = new DataInputStream(inputStream);
            }
            this.c = ClassResolvers.weakCachingResolver(classLoader);
            this.b = i;
        } else {
            throw new IllegalArgumentException("maxObjectSize: " + i);
        }
    }

    @Override // java.io.ObjectInput
    public Object readObject() throws ClassNotFoundException, IOException {
        int readInt = readInt();
        if (readInt <= 0) {
            throw new StreamCorruptedException("invalid data length: " + readInt);
        } else if (readInt <= this.b) {
            return new c(this.a, this.c).readObject();
        } else {
            throw new StreamCorruptedException("data length too big: " + readInt + " (max: " + this.b + ')');
        }
    }

    @Override // java.io.InputStream, java.io.ObjectInput
    public int available() throws IOException {
        return this.a.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable, java.io.ObjectInput
    public void close() throws IOException {
        this.a.close();
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.a.mark(i);
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.a.markSupported();
    }

    @Override // java.io.InputStream, java.io.ObjectInput
    public int read() throws IOException {
        return this.a.read();
    }

    @Override // java.io.InputStream, java.io.ObjectInput
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        return this.a.read(bArr, i, i2);
    }

    @Override // java.io.InputStream, java.io.ObjectInput
    public final int read(byte[] bArr) throws IOException {
        return this.a.read(bArr);
    }

    @Override // java.io.DataInput
    public final boolean readBoolean() throws IOException {
        return this.a.readBoolean();
    }

    @Override // java.io.DataInput
    public final byte readByte() throws IOException {
        return this.a.readByte();
    }

    @Override // java.io.DataInput
    public final char readChar() throws IOException {
        return this.a.readChar();
    }

    @Override // java.io.DataInput
    public final double readDouble() throws IOException {
        return this.a.readDouble();
    }

    @Override // java.io.DataInput
    public final float readFloat() throws IOException {
        return this.a.readFloat();
    }

    @Override // java.io.DataInput
    public final void readFully(byte[] bArr, int i, int i2) throws IOException {
        this.a.readFully(bArr, i, i2);
    }

    @Override // java.io.DataInput
    public final void readFully(byte[] bArr) throws IOException {
        this.a.readFully(bArr);
    }

    @Override // java.io.DataInput
    public final int readInt() throws IOException {
        return this.a.readInt();
    }

    @Override // java.io.DataInput
    @Deprecated
    public final String readLine() throws IOException {
        return this.a.readLine();
    }

    @Override // java.io.DataInput
    public final long readLong() throws IOException {
        return this.a.readLong();
    }

    @Override // java.io.DataInput
    public final short readShort() throws IOException {
        return this.a.readShort();
    }

    @Override // java.io.DataInput
    public final int readUnsignedByte() throws IOException {
        return this.a.readUnsignedByte();
    }

    @Override // java.io.DataInput
    public final int readUnsignedShort() throws IOException {
        return this.a.readUnsignedShort();
    }

    @Override // java.io.DataInput
    public final String readUTF() throws IOException {
        return this.a.readUTF();
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        this.a.reset();
    }

    @Override // java.io.InputStream, java.io.ObjectInput
    public long skip(long j) throws IOException {
        return this.a.skip(j);
    }

    @Override // java.io.DataInput
    public final int skipBytes(int i) throws IOException {
        return this.a.skipBytes(i);
    }
}
