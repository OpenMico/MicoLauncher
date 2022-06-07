package com.google.android.exoplayer2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public final class AtomicFile {
    private final File a;
    private final File b;

    public AtomicFile(File file) {
        this.a = file;
        this.b = new File(String.valueOf(file.getPath()).concat(".bak"));
    }

    public boolean exists() {
        return this.a.exists() || this.b.exists();
    }

    public void delete() {
        this.a.delete();
        this.b.delete();
    }

    public OutputStream startWrite() throws IOException {
        if (this.a.exists()) {
            if (this.b.exists()) {
                this.a.delete();
            } else if (!this.a.renameTo(this.b)) {
                String valueOf = String.valueOf(this.a);
                String valueOf2 = String.valueOf(this.b);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37 + String.valueOf(valueOf2).length());
                sb.append("Couldn't rename file ");
                sb.append(valueOf);
                sb.append(" to backup file ");
                sb.append(valueOf2);
                Log.w("AtomicFile", sb.toString());
            }
        }
        try {
            return new a(this.a);
        } catch (FileNotFoundException e) {
            File parentFile = this.a.getParentFile();
            if (parentFile == null || !parentFile.mkdirs()) {
                String valueOf3 = String.valueOf(this.a);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 16);
                sb2.append("Couldn't create ");
                sb2.append(valueOf3);
                throw new IOException(sb2.toString(), e);
            }
            try {
                return new a(this.a);
            } catch (FileNotFoundException e2) {
                String valueOf4 = String.valueOf(this.a);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf4).length() + 16);
                sb3.append("Couldn't create ");
                sb3.append(valueOf4);
                throw new IOException(sb3.toString(), e2);
            }
        }
    }

    public void endWrite(OutputStream outputStream) throws IOException {
        outputStream.close();
        this.b.delete();
    }

    public InputStream openRead() throws FileNotFoundException {
        a();
        return new FileInputStream(this.a);
    }

    private void a() {
        if (this.b.exists()) {
            this.a.delete();
            this.b.renameTo(this.a);
        }
    }

    /* loaded from: classes2.dex */
    private static final class a extends OutputStream {
        private final FileOutputStream a;
        private boolean b = false;

        public a(File file) throws FileNotFoundException {
            this.a = new FileOutputStream(file);
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (!this.b) {
                this.b = true;
                flush();
                try {
                    this.a.getFD().sync();
                } catch (IOException e) {
                    Log.w("AtomicFile", "Failed to sync file descriptor:", e);
                }
                this.a.close();
            }
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this.a.flush();
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            this.a.write(i);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.a.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.a.write(bArr, i, i2);
        }
    }
}
