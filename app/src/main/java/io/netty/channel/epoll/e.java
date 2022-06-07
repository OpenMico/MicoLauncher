package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IovArray.java */
/* loaded from: classes4.dex */
public final class e implements ChannelOutboundBuffer.MessageProcessor {
    static final /* synthetic */ boolean a = !e.class.desiredAssertionStatus();
    private static final int b = PlatformDependent.addressSize();
    private static final int c = b * 2;
    private static final int d = Native.IOV_MAX * c;
    private final long e = PlatformDependent.allocateMemory(d);
    private int f;
    private long g;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.f = 0;
        this.g = 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(ByteBuf byteBuf) {
        if (this.f == Native.IOV_MAX) {
            return false;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            return true;
        }
        return a(byteBuf.memoryAddress(), byteBuf.readerIndex(), readableBytes);
    }

    private boolean a(long j, int i, int i2) {
        if (i2 == 0) {
            return true;
        }
        int i3 = this.f;
        this.f = i3 + 1;
        long a2 = a(i3);
        long j2 = b + a2;
        long j3 = i2;
        long j4 = this.g;
        if (Native.SSIZE_MAX - j3 < j4) {
            return false;
        }
        this.g = j4 + j3;
        int i4 = b;
        if (i4 == 8) {
            PlatformDependent.putLong(a2, j + i);
            PlatformDependent.putLong(j2, j3);
        } else if (a || i4 == 4) {
            PlatformDependent.putInt(a2, ((int) j) + i);
            PlatformDependent.putInt(j2, i2);
        } else {
            throw new AssertionError();
        }
        return true;
    }

    boolean a(CompositeByteBuf compositeByteBuf) {
        ByteBuffer[] nioBuffers = compositeByteBuf.nioBuffers();
        if (this.f + nioBuffers.length >= Native.IOV_MAX) {
            return false;
        }
        for (ByteBuffer byteBuffer : nioBuffers) {
            int position = byteBuffer.position();
            int limit = byteBuffer.limit() - byteBuffer.position();
            if (limit != 0 && !a(PlatformDependent.directBufferAddress(byteBuffer), position, limit)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long a(int i, long j) {
        long a2 = a(i);
        int i2 = b;
        long j2 = i2 + a2;
        if (i2 == 8) {
            long j3 = PlatformDependent.getLong(j2);
            if (j3 <= j) {
                return j3;
            }
            PlatformDependent.putLong(a2, PlatformDependent.getLong(a2) + j);
            PlatformDependent.putLong(j2, j3 - j);
            return -1L;
        } else if (a || i2 == 4) {
            long j4 = PlatformDependent.getInt(j2);
            if (j4 <= j) {
                return j4;
            }
            PlatformDependent.putInt(a2, (int) (PlatformDependent.getInt(a2) + j));
            PlatformDependent.putInt(j2, (int) (j4 - j));
            return -1L;
        } else {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long c() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long a(int i) {
        return this.e + (c * i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d() {
        PlatformDependent.freeMemory(this.e);
    }

    @Override // io.netty.channel.ChannelOutboundBuffer.MessageProcessor
    public boolean processMessage(Object obj) throws Exception {
        if (!(obj instanceof ByteBuf)) {
            return false;
        }
        if (obj instanceof CompositeByteBuf) {
            return a((CompositeByteBuf) obj);
        }
        return a((ByteBuf) obj);
    }
}
