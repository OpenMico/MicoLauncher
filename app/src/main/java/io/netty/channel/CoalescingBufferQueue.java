package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;

/* loaded from: classes4.dex */
public final class CoalescingBufferQueue {
    static final /* synthetic */ boolean a = !CoalescingBufferQueue.class.desiredAssertionStatus();
    private final Channel b;
    private final ArrayDeque<Object> c;
    private int d;

    public CoalescingBufferQueue(Channel channel) {
        this(channel, 4);
    }

    public CoalescingBufferQueue(Channel channel, int i) {
        this.b = (Channel) ObjectUtil.checkNotNull(channel, "channel");
        this.c = new ArrayDeque<>(i);
    }

    public void add(ByteBuf byteBuf) {
        add(byteBuf, (ChannelFutureListener) null);
    }

    public void add(ByteBuf byteBuf, ChannelPromise channelPromise) {
        ObjectUtil.checkNotNull(channelPromise, "promise");
        add(byteBuf, channelPromise.isVoid() ? null : new ChannelPromiseNotifier(channelPromise));
    }

    public void add(ByteBuf byteBuf, ChannelFutureListener channelFutureListener) {
        ObjectUtil.checkNotNull(byteBuf, "buf");
        if (this.d <= Integer.MAX_VALUE - byteBuf.readableBytes()) {
            this.c.add(byteBuf);
            if (channelFutureListener != null) {
                this.c.add(channelFutureListener);
            }
            this.d += byteBuf.readableBytes();
            return;
        }
        throw new IllegalStateException("buffer queue length overflow: " + this.d + " + " + byteBuf.readableBytes());
    }

    public ByteBuf remove(int i, ChannelPromise channelPromise) {
        if (i >= 0) {
            ObjectUtil.checkNotNull(channelPromise, "aggregatePromise");
            if (this.c.isEmpty()) {
                return Unpooled.EMPTY_BUFFER;
            }
            int min = Math.min(i, this.d);
            ByteBuf byteBuf = null;
            int i2 = min;
            while (true) {
                Object poll = this.c.poll();
                if (poll == null) {
                    break;
                } else if (poll instanceof ChannelFutureListener) {
                    channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) ((ChannelFutureListener) poll));
                } else {
                    ByteBuf byteBuf2 = (ByteBuf) poll;
                    if (byteBuf2.readableBytes() > i2) {
                        this.c.addFirst(byteBuf2);
                        if (i2 > 0) {
                            byteBuf = a(byteBuf, byteBuf2.readRetainedSlice(i2));
                            i2 = 0;
                        }
                    } else {
                        byteBuf = a(byteBuf, byteBuf2);
                        i2 -= byteBuf2.readableBytes();
                    }
                }
            }
            this.d -= min - i2;
            if (a || this.d >= 0) {
                return byteBuf;
            }
            throw new AssertionError();
        }
        throw new IllegalArgumentException("bytes (expected >= 0): " + i);
    }

    private ByteBuf a(ByteBuf byteBuf, ByteBuf byteBuf2) {
        if (byteBuf == null) {
            return byteBuf2;
        }
        if (byteBuf instanceof CompositeByteBuf) {
            CompositeByteBuf compositeByteBuf = (CompositeByteBuf) byteBuf;
            compositeByteBuf.addComponent(true, byteBuf2);
            return compositeByteBuf;
        }
        CompositeByteBuf compositeBuffer = this.b.alloc().compositeBuffer(this.c.size() + 2);
        compositeBuffer.addComponent(true, byteBuf);
        compositeBuffer.addComponent(true, byteBuf2);
        return compositeBuffer;
    }

    public int readableBytes() {
        return this.d;
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    public void releaseAndFailAll(Throwable th) {
        a(this.b.newFailedFuture(th));
    }

    private void a(ChannelFuture channelFuture) {
        this.d = 0;
        Throwable th = null;
        while (true) {
            Object poll = this.c.poll();
            if (poll == null) {
                break;
            }
            try {
                if (poll instanceof ByteBuf) {
                    ReferenceCountUtil.safeRelease(poll);
                } else {
                    ((ChannelFutureListener) poll).operationComplete(channelFuture);
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        if (th != null) {
            throw new IllegalStateException(th);
        }
    }

    public void copyTo(CoalescingBufferQueue coalescingBufferQueue) {
        coalescingBufferQueue.c.addAll(this.c);
        coalescingBufferQueue.d += this.d;
    }
}
