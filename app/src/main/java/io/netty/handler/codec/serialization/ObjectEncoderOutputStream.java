package io.netty.handler.codec.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class ObjectEncoderOutputStream extends OutputStream implements ObjectOutput {
    private final DataOutputStream a;
    private final int b;

    public ObjectEncoderOutputStream(OutputStream outputStream) {
        this(outputStream, 512);
    }

    public ObjectEncoderOutputStream(OutputStream outputStream, int i) {
        if (outputStream == null) {
            throw new NullPointerException("out");
        } else if (i >= 0) {
            if (outputStream instanceof DataOutputStream) {
                this.a = (DataOutputStream) outputStream;
            } else {
                this.a = new DataOutputStream(outputStream);
            }
            this.b = i;
        } else {
            throw new IllegalArgumentException("estimatedLength: " + i);
        }
    }

    @Override // java.io.ObjectOutput
    public void writeObject(Object obj) throws IOException {
        ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(Unpooled.buffer(this.b));
        d dVar = new d(byteBufOutputStream);
        dVar.writeObject(obj);
        dVar.flush();
        dVar.close();
        ByteBuf buffer = byteBufOutputStream.buffer();
        int readableBytes = buffer.readableBytes();
        writeInt(readableBytes);
        buffer.getBytes(0, this, readableBytes);
    }

    @Override // java.io.OutputStream, java.io.ObjectOutput, java.io.DataOutput
    public void write(int i) throws IOException {
        this.a.write(i);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable, java.io.ObjectOutput
    public void close() throws IOException {
        this.a.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable, java.io.ObjectOutput
    public void flush() throws IOException {
        this.a.flush();
    }

    public final int size() {
        return this.a.size();
    }

    @Override // java.io.OutputStream, java.io.ObjectOutput, java.io.DataOutput
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.a.write(bArr, i, i2);
    }

    @Override // java.io.OutputStream, java.io.ObjectOutput, java.io.DataOutput
    public void write(byte[] bArr) throws IOException {
        this.a.write(bArr);
    }

    @Override // java.io.DataOutput
    public final void writeBoolean(boolean z) throws IOException {
        this.a.writeBoolean(z);
    }

    @Override // java.io.DataOutput
    public final void writeByte(int i) throws IOException {
        this.a.writeByte(i);
    }

    @Override // java.io.DataOutput
    public final void writeBytes(String str) throws IOException {
        this.a.writeBytes(str);
    }

    @Override // java.io.DataOutput
    public final void writeChar(int i) throws IOException {
        this.a.writeChar(i);
    }

    @Override // java.io.DataOutput
    public final void writeChars(String str) throws IOException {
        this.a.writeChars(str);
    }

    @Override // java.io.DataOutput
    public final void writeDouble(double d) throws IOException {
        this.a.writeDouble(d);
    }

    @Override // java.io.DataOutput
    public final void writeFloat(float f) throws IOException {
        this.a.writeFloat(f);
    }

    @Override // java.io.DataOutput
    public final void writeInt(int i) throws IOException {
        this.a.writeInt(i);
    }

    @Override // java.io.DataOutput
    public final void writeLong(long j) throws IOException {
        this.a.writeLong(j);
    }

    @Override // java.io.DataOutput
    public final void writeShort(int i) throws IOException {
        this.a.writeShort(i);
    }

    @Override // java.io.DataOutput
    public final void writeUTF(String str) throws IOException {
        this.a.writeUTF(str);
    }
}
