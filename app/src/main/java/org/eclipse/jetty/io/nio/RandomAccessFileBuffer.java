package org.eclipse.jetty.io.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import org.eclipse.jetty.io.AbstractBuffer;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes5.dex */
public class RandomAccessFileBuffer extends AbstractBuffer implements Buffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final int _capacity;
    final FileChannel _channel;
    final RandomAccessFile _file;

    @Override // org.eclipse.jetty.io.Buffer
    public byte[] array() {
        return null;
    }

    public RandomAccessFileBuffer(File file) throws FileNotFoundException {
        super(2, true);
        this._file = new RandomAccessFile(file, "rw");
        this._channel = this._file.getChannel();
        this._capacity = Integer.MAX_VALUE;
        setGetIndex(0);
        setPutIndex((int) file.length());
    }

    public RandomAccessFileBuffer(File file, int i) throws FileNotFoundException {
        super(2, true);
        this._capacity = i;
        this._file = new RandomAccessFile(file, "rw");
        this._channel = this._file.getChannel();
        setGetIndex(0);
        setPutIndex((int) file.length());
    }

    public RandomAccessFileBuffer(File file, int i, int i2) throws FileNotFoundException {
        super(i2, true);
        this._capacity = i;
        this._file = new RandomAccessFile(file, i2 == 2 ? "rw" : "r");
        this._channel = this._file.getChannel();
        setGetIndex(0);
        setPutIndex((int) file.length());
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int capacity() {
        return this._capacity;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public void clear() {
        try {
            synchronized (this._file) {
                super.clear();
                this._file.setLength(0L);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public byte peek() {
        byte readByte;
        try {
            synchronized (this._file) {
                try {
                    if (this._get != this._file.getFilePointer()) {
                        this._file.seek(this._get);
                    }
                    readByte = this._file.readByte();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return readByte;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte peek(int i) {
        byte readByte;
        try {
            synchronized (this._file) {
                try {
                    this._file.seek(i);
                    readByte = this._file.readByte();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return readByte;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int peek(int i, byte[] bArr, int i2, int i3) {
        int read;
        try {
            synchronized (this._file) {
                try {
                    this._file.seek(i);
                    read = this._file.read(bArr, i2, i3);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return read;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void poke(int i, byte b) {
        try {
            synchronized (this._file) {
                try {
                    this._file.seek(i);
                    this._file.writeByte(b);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i, byte[] bArr, int i2, int i3) {
        try {
            synchronized (this._file) {
                try {
                    this._file.seek(i);
                    this._file.write(bArr, i2, i3);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return i3;
        } catch (Throwable th) {
            throw th;
        }
    }

    public int writeTo(WritableByteChannel writableByteChannel, int i, int i2) throws IOException {
        int transferTo;
        synchronized (this._file) {
            transferTo = (int) this._channel.transferTo(i, i2, writableByteChannel);
        }
        return transferTo;
    }
}
