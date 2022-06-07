package io.netty.channel.unix;

import com.umeng.analytics.pro.ai;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* loaded from: classes4.dex */
public class FileDescriptor {
    private static final AtomicIntegerFieldUpdater<FileDescriptor> c;
    volatile int a;
    final int b;

    static boolean a(int i) {
        return (i & 1) != 0;
    }

    static boolean b(int i) {
        return (i & 2) != 0;
    }

    static boolean c(int i) {
        return (i & 4) != 0;
    }

    private static native int close(int i);

    static int d(int i) {
        return i | 2;
    }

    static int e(int i) {
        return i | 4;
    }

    private static native long newPipe();

    private static native int open(String str);

    private static native int read(int i, ByteBuffer byteBuffer, int i2, int i3);

    private static native int readAddress(int i, long j, int i2, int i3);

    private static native int write(int i, ByteBuffer byteBuffer, int i2, int i3);

    private static native int writeAddress(int i, long j, int i2, int i3);

    private static native long writev(int i, ByteBuffer[] byteBufferArr, int i2, int i3);

    private static native long writevAddresses(int i, long j, int i2);

    static {
        AtomicIntegerFieldUpdater<FileDescriptor> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(FileDescriptor.class, XiaomiOAuthConstants.EXTRA_STATE_2);
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(FileDescriptor.class, ai.at);
        }
        c = newAtomicIntegerFieldUpdater;
    }

    public FileDescriptor(int i) {
        if (i >= 0) {
            this.b = i;
            return;
        }
        throw new IllegalArgumentException("fd must be >= 0");
    }

    public int intValue() {
        return this.b;
    }

    public void close() throws IOException {
        int i;
        do {
            i = this.a;
            if (a(i)) {
                return;
            }
        } while (!a(i, i | 7));
        int close = close(this.b);
        if (close < 0) {
            throw Errors.newIOException("close", close);
        }
    }

    public boolean isOpen() {
        return !a(this.a);
    }

    public final int write(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        int write = write(this.b, byteBuffer, i, i2);
        return write >= 0 ? write : Errors.ioResult("write", write, Errors.c);
    }

    public final int writeAddress(long j, int i, int i2) throws IOException {
        int writeAddress = writeAddress(this.b, j, i, i2);
        return writeAddress >= 0 ? writeAddress : Errors.ioResult("writeAddress", writeAddress, Errors.c);
    }

    public final long writev(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        long writev = writev(this.b, byteBufferArr, i, i2);
        return writev >= 0 ? writev : Errors.ioResult("writev", (int) writev, Errors.d);
    }

    public final long writevAddresses(long j, int i) throws IOException {
        long writevAddresses = writevAddresses(this.b, j, i);
        return writevAddresses >= 0 ? writevAddresses : Errors.ioResult("writevAddresses", (int) writevAddresses, Errors.d);
    }

    public final int read(ByteBuffer byteBuffer, int i, int i2) throws IOException {
        int read = read(this.b, byteBuffer, i, i2);
        if (read > 0) {
            return read;
        }
        if (read == 0) {
            return -1;
        }
        return Errors.ioResult("read", read, Errors.e);
    }

    public final int readAddress(long j, int i, int i2) throws IOException {
        int readAddress = readAddress(this.b, j, i, i2);
        if (readAddress > 0) {
            return readAddress;
        }
        if (readAddress == 0) {
            return -1;
        }
        return Errors.ioResult("readAddress", readAddress, Errors.e);
    }

    public String toString() {
        return "FileDescriptor{fd=" + this.b + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FileDescriptor) && this.b == ((FileDescriptor) obj).b;
    }

    public int hashCode() {
        return this.b;
    }

    public static FileDescriptor from(String str) throws IOException {
        ObjectUtil.checkNotNull(str, "path");
        int open = open(str);
        if (open >= 0) {
            return new FileDescriptor(open);
        }
        throw Errors.newIOException(AbstractCircuitBreaker.PROPERTY_NAME, open);
    }

    public static FileDescriptor from(File file) throws IOException {
        return from(((File) ObjectUtil.checkNotNull(file, "file")).getPath());
    }

    public static FileDescriptor[] pipe() throws IOException {
        long newPipe = newPipe();
        if (newPipe >= 0) {
            return new FileDescriptor[]{new FileDescriptor((int) (newPipe >>> 32)), new FileDescriptor((int) newPipe)};
        }
        throw Errors.newIOException("newPipe", (int) newPipe);
    }

    final boolean a(int i, int i2) {
        return c.compareAndSet(this, i, i2);
    }
}
