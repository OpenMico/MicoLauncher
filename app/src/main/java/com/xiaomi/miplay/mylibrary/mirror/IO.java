package com.xiaomi.miplay.mylibrary.mirror;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class IO {
    private IO() {
    }

    public static void writeFully(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws IOException {
        int write;
        int remaining = byteBuffer.remaining();
        while (remaining > 0) {
            try {
                write = Os.write(fileDescriptor, byteBuffer);
            } catch (ErrnoException e) {
                if (e.errno != OsConstants.EINTR) {
                    throw new IOException(e);
                }
            }
            if (write < 0) {
                throw new AssertionError("Os.write() returned a negative value (" + write + ")");
                break;
            }
            remaining -= write;
        }
    }

    public static void writeFully(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2) throws IOException {
        writeFully(fileDescriptor, ByteBuffer.wrap(bArr, i, i2));
    }
}
