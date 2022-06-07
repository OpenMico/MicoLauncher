package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class FileBackedOutputStream extends OutputStream {
    private final int a;
    private final boolean b;
    private final ByteSource c;
    private OutputStream d;
    private a e;
    @NullableDecl
    private File f;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a extends ByteArrayOutputStream {
        private a() {
        }

        byte[] a() {
            return this.buf;
        }

        int b() {
            return this.count;
        }
    }

    public FileBackedOutputStream(int i) {
        this(i, false);
    }

    public FileBackedOutputStream(int i, boolean z) {
        this.a = i;
        this.b = z;
        this.e = new a();
        this.d = this.e;
        if (z) {
            this.c = new ByteSource() { // from class: com.google.common.io.FileBackedOutputStream.1
                @Override // com.google.common.io.ByteSource
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.a();
                }

                protected void finalize() {
                    try {
                        FileBackedOutputStream.this.reset();
                    } catch (Throwable th) {
                        th.printStackTrace(System.err);
                    }
                }
            };
        } else {
            this.c = new ByteSource() { // from class: com.google.common.io.FileBackedOutputStream.2
                @Override // com.google.common.io.ByteSource
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.a();
                }
            };
        }
    }

    public ByteSource asByteSource() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized InputStream a() throws IOException {
        if (this.f != null) {
            return new FileInputStream(this.f);
        }
        return new ByteArrayInputStream(this.e.a(), 0, this.e.b());
    }

    public synchronized void reset() throws IOException {
        close();
        if (this.e == null) {
            this.e = new a();
        } else {
            this.e.reset();
        }
        this.d = this.e;
        if (this.f != null) {
            File file = this.f;
            this.f = null;
            if (!file.delete()) {
                throw new IOException("Could not delete: " + file);
            }
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(int i) throws IOException {
        a(1);
        this.d.write(i);
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] bArr, int i, int i2) throws IOException {
        a(i2);
        this.d.write(bArr, i, i2);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.d.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public synchronized void flush() throws IOException {
        this.d.flush();
    }

    private void a(int i) throws IOException {
        if (this.f == null && this.e.b() + i > this.a) {
            File createTempFile = File.createTempFile("FileBackedOutputStream", null);
            if (this.b) {
                createTempFile.deleteOnExit();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            fileOutputStream.write(this.e.a(), 0, this.e.b());
            fileOutputStream.flush();
            this.d = fileOutputStream;
            this.f = createTempFile;
            this.e = null;
        }
    }
}
