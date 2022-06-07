package io.netty.buffer;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class ByteBufOutputStream extends OutputStream implements DataOutput {
    private final ByteBuf a;
    private final int b;
    private final DataOutputStream c = new DataOutputStream(this);

    public ByteBufOutputStream(ByteBuf byteBuf) {
        if (byteBuf != null) {
            this.a = byteBuf;
            this.b = byteBuf.writerIndex();
            return;
        }
        throw new NullPointerException("buffer");
    }

    public int writtenBytes() {
        return this.a.writerIndex() - this.b;
    }

    @Override // java.io.OutputStream, java.io.DataOutput
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i2 != 0) {
            this.a.writeBytes(bArr, i, i2);
        }
    }

    @Override // java.io.OutputStream, java.io.DataOutput
    public void write(byte[] bArr) throws IOException {
        this.a.writeBytes(bArr);
    }

    @Override // java.io.OutputStream, java.io.DataOutput
    public void write(int i) throws IOException {
        this.a.writeByte((byte) i);
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean z) throws IOException {
        write(z ? 1 : 0);
    }

    @Override // java.io.DataOutput
    public void writeByte(int i) throws IOException {
        write(i);
    }

    @Override // java.io.DataOutput
    public void writeBytes(String str) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            write((byte) str.charAt(i));
        }
    }

    @Override // java.io.DataOutput
    public void writeChar(int i) throws IOException {
        writeShort((short) i);
    }

    @Override // java.io.DataOutput
    public void writeChars(String str) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            writeChar(str.charAt(i));
        }
    }

    @Override // java.io.DataOutput
    public void writeDouble(double d) throws IOException {
        writeLong(Double.doubleToLongBits(d));
    }

    @Override // java.io.DataOutput
    public void writeFloat(float f) throws IOException {
        writeInt(Float.floatToIntBits(f));
    }

    @Override // java.io.DataOutput
    public void writeInt(int i) throws IOException {
        this.a.writeInt(i);
    }

    @Override // java.io.DataOutput
    public void writeLong(long j) throws IOException {
        this.a.writeLong(j);
    }

    @Override // java.io.DataOutput
    public void writeShort(int i) throws IOException {
        this.a.writeShort((short) i);
    }

    @Override // java.io.DataOutput
    public void writeUTF(String str) throws IOException {
        this.c.writeUTF(str);
    }

    public ByteBuf buffer() {
        return this.a;
    }
}
