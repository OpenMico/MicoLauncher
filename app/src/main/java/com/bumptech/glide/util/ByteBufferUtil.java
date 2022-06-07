package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ByteBufferUtil {
    private static final AtomicReference<byte[]> a = new AtomicReference<>();

    private ByteBufferUtil() {
    }

    @NonNull
    public static ByteBuffer fromFile(@NonNull File file) throws IOException {
        Throwable th;
        RandomAccessFile randomAccessFile;
        FileChannel fileChannel = null;
        try {
            long length = file.length();
            if (length > 2147483647L) {
                throw new IOException("File too large to map into memory");
            } else if (length != 0) {
                randomAccessFile = new RandomAccessFile(file, "r");
                try {
                    FileChannel channel = randomAccessFile.getChannel();
                    try {
                        MappedByteBuffer load = channel.map(FileChannel.MapMode.READ_ONLY, 0L, length).load();
                        if (channel != null) {
                            try {
                                channel.close();
                            } catch (IOException unused) {
                            }
                        }
                        try {
                            randomAccessFile.close();
                        } catch (IOException unused2) {
                        }
                        return load;
                    } catch (Throwable th2) {
                        th = th2;
                        fileChannel = channel;
                        if (fileChannel != null) {
                            try {
                                fileChannel.close();
                            } catch (IOException unused3) {
                            }
                        }
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close();
                            } catch (IOException unused4) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } else {
                throw new IOException("File unsuitable for memory mapping");
            }
        } catch (Throwable th4) {
            th = th4;
            randomAccessFile = null;
        }
    }

    public static void toFile(@NonNull ByteBuffer byteBuffer, @NonNull File file) throws IOException {
        Throwable th;
        RandomAccessFile randomAccessFile;
        rewind(byteBuffer);
        FileChannel fileChannel = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            try {
                fileChannel = randomAccessFile.getChannel();
                fileChannel.write(byteBuffer);
                fileChannel.force(false);
                fileChannel.close();
                randomAccessFile.close();
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException unused) {
                    }
                }
                try {
                    randomAccessFile.close();
                } catch (IOException unused2) {
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException unused3) {
                    }
                }
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            randomAccessFile = null;
        }
    }

    public static void toStream(@NonNull ByteBuffer byteBuffer, @NonNull OutputStream outputStream) throws IOException {
        b a2 = a(byteBuffer);
        if (a2 != null) {
            outputStream.write(a2.c, a2.a, a2.a + a2.b);
            return;
        }
        byte[] andSet = a.getAndSet(null);
        if (andSet == null) {
            andSet = new byte[16384];
        }
        while (byteBuffer.remaining() > 0) {
            int min = Math.min(byteBuffer.remaining(), andSet.length);
            byteBuffer.get(andSet, 0, min);
            outputStream.write(andSet, 0, min);
        }
        a.set(andSet);
    }

    @NonNull
    public static byte[] toBytes(@NonNull ByteBuffer byteBuffer) {
        b a2 = a(byteBuffer);
        if (a2 != null && a2.a == 0 && a2.b == a2.c.length) {
            return byteBuffer.array();
        }
        ByteBuffer asReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        byte[] bArr = new byte[asReadOnlyBuffer.limit()];
        rewind(asReadOnlyBuffer);
        asReadOnlyBuffer.get(bArr);
        return bArr;
    }

    @NonNull
    public static InputStream toStream(@NonNull ByteBuffer byteBuffer) {
        return new a(byteBuffer);
    }

    @NonNull
    public static ByteBuffer fromStream(@NonNull InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(16384);
        byte[] andSet = a.getAndSet(null);
        if (andSet == null) {
            andSet = new byte[16384];
        }
        while (true) {
            int read = inputStream.read(andSet);
            if (read >= 0) {
                byteArrayOutputStream.write(andSet, 0, read);
            } else {
                a.set(andSet);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                return rewind(ByteBuffer.allocateDirect(byteArray.length).put(byteArray));
            }
        }
    }

    public static ByteBuffer rewind(ByteBuffer byteBuffer) {
        return (ByteBuffer) byteBuffer.position(0);
    }

    @Nullable
    private static b a(@NonNull ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly() || !byteBuffer.hasArray()) {
            return null;
        }
        return new b(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class b {
        final int a;
        final int b;
        final byte[] c;

        b(@NonNull byte[] bArr, int i, int i2) {
            this.c = bArr;
            this.a = i;
            this.b = i2;
        }
    }

    /* loaded from: classes.dex */
    private static class a extends InputStream {
        @NonNull
        private final ByteBuffer a;
        private int b = -1;

        @Override // java.io.InputStream
        public boolean markSupported() {
            return true;
        }

        a(@NonNull ByteBuffer byteBuffer) {
            this.a = byteBuffer;
        }

        @Override // java.io.InputStream
        public int available() {
            return this.a.remaining();
        }

        @Override // java.io.InputStream
        public int read() {
            if (!this.a.hasRemaining()) {
                return -1;
            }
            return this.a.get() & 255;
        }

        @Override // java.io.InputStream
        public synchronized void mark(int i) {
            this.b = this.a.position();
        }

        @Override // java.io.InputStream
        public int read(@NonNull byte[] bArr, int i, int i2) {
            if (!this.a.hasRemaining()) {
                return -1;
            }
            int min = Math.min(i2, available());
            this.a.get(bArr, i, min);
            return min;
        }

        @Override // java.io.InputStream
        public synchronized void reset() throws IOException {
            if (this.b != -1) {
                this.a.position(this.b);
            } else {
                throw new IOException("Cannot reset to unset mark position");
            }
        }

        @Override // java.io.InputStream
        public long skip(long j) {
            if (!this.a.hasRemaining()) {
                return -1L;
            }
            long min = Math.min(j, available());
            ByteBuffer byteBuffer = this.a;
            byteBuffer.position((int) (byteBuffer.position() + min));
            return min;
        }
    }
}
