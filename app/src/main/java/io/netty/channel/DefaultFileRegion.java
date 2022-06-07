package io.netty.channel;

import io.netty.util.AbstractReferenceCounted;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes4.dex */
public class DefaultFileRegion extends AbstractReferenceCounted implements FileRegion {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
    private final File b;
    private final long c;
    private final long d;
    private long e;
    private FileChannel f;

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FileRegion touch() {
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public FileRegion touch(Object obj) {
        return this;
    }

    public DefaultFileRegion(FileChannel fileChannel, long j, long j2) {
        if (fileChannel == null) {
            throw new NullPointerException("file");
        } else if (j < 0) {
            throw new IllegalArgumentException("position must be >= 0 but was " + j);
        } else if (j2 >= 0) {
            this.f = fileChannel;
            this.c = j;
            this.d = j2;
            this.b = null;
        } else {
            throw new IllegalArgumentException("count must be >= 0 but was " + j2);
        }
    }

    public DefaultFileRegion(File file, long j, long j2) {
        if (file == null) {
            throw new NullPointerException("f");
        } else if (j < 0) {
            throw new IllegalArgumentException("position must be >= 0 but was " + j);
        } else if (j2 >= 0) {
            this.c = j;
            this.d = j2;
            this.b = file;
        } else {
            throw new IllegalArgumentException("count must be >= 0 but was " + j2);
        }
    }

    public boolean isOpen() {
        return this.f != null;
    }

    public void open() throws IOException {
        if (!isOpen() && refCnt() > 0) {
            this.f = new RandomAccessFile(this.b, "r").getChannel();
        }
    }

    @Override // io.netty.channel.FileRegion
    public long position() {
        return this.c;
    }

    @Override // io.netty.channel.FileRegion
    public long count() {
        return this.d;
    }

    @Override // io.netty.channel.FileRegion
    @Deprecated
    public long transfered() {
        return this.e;
    }

    @Override // io.netty.channel.FileRegion
    public long transferred() {
        return this.e;
    }

    @Override // io.netty.channel.FileRegion
    public long transferTo(WritableByteChannel writableByteChannel, long j) throws IOException {
        long j2 = this.d - j;
        int i = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
        if (i < 0 || j < 0) {
            throw new IllegalArgumentException("position out of range: " + j + " (expected: 0 - " + (this.d - 1) + ')');
        } else if (i == 0) {
            return 0L;
        } else {
            if (refCnt() != 0) {
                open();
                long transferTo = this.f.transferTo(this.c + j, j2, writableByteChannel);
                if (transferTo > 0) {
                    this.e += transferTo;
                }
                return transferTo;
            }
            throw new IllegalReferenceCountException(0);
        }
    }

    @Override // io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        FileChannel fileChannel = this.f;
        if (fileChannel != null) {
            this.f = null;
            try {
                fileChannel.close();
            } catch (IOException e) {
                if (a.isWarnEnabled()) {
                    a.warn("Failed to close a file.", (Throwable) e);
                }
            }
        }
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FileRegion retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public FileRegion retain(int i) {
        super.retain(i);
        return this;
    }
}
