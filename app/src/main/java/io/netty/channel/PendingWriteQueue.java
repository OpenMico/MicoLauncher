package io.netty.channel;

import io.netty.channel.MessageSizeEstimator;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.PromiseCombiner;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/* loaded from: classes4.dex */
public final class PendingWriteQueue {
    static final /* synthetic */ boolean a = !PendingWriteQueue.class.desiredAssertionStatus();
    private static final InternalLogger b = InternalLoggerFactory.getInstance(PendingWriteQueue.class);
    private final ChannelHandlerContext c;
    private final ChannelOutboundBuffer d;
    private final MessageSizeEstimator.Handle e;
    private a f;
    private a g;
    private int h;

    public PendingWriteQueue(ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext != null) {
            this.c = channelHandlerContext;
            this.d = channelHandlerContext.channel().unsafe().outboundBuffer();
            this.e = channelHandlerContext.channel().config().getMessageSizeEstimator().newHandle();
            return;
        }
        throw new NullPointerException("ctx");
    }

    public boolean isEmpty() {
        if (a || this.c.executor().inEventLoop()) {
            return this.f == null;
        }
        throw new AssertionError();
    }

    public int size() {
        if (a || this.c.executor().inEventLoop()) {
            return this.h;
        }
        throw new AssertionError();
    }

    public void add(Object obj, ChannelPromise channelPromise) {
        if (!a && !this.c.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (obj == null) {
            throw new NullPointerException("msg");
        } else if (channelPromise != null) {
            int size = this.e.size(obj);
            if (size < 0) {
                size = 0;
            }
            a a2 = a.a(obj, size, channelPromise);
            a aVar = this.g;
            if (aVar == null) {
                this.f = a2;
                this.g = a2;
            } else {
                aVar.c = a2;
                this.g = a2;
            }
            this.h++;
            ChannelOutboundBuffer channelOutboundBuffer = this.d;
            if (channelOutboundBuffer != null) {
                channelOutboundBuffer.a(a2.d);
            }
        } else {
            throw new NullPointerException("promise");
        }
    }

    public void removeAndFailAll(Throwable th) {
        if (a || this.c.executor().inEventLoop()) {
            if (th == null) {
                throw new NullPointerException("cause");
            }
            while (true) {
                a aVar = this.f;
                if (aVar != null) {
                    this.g = null;
                    this.f = null;
                    this.h = 0;
                    while (aVar != null) {
                        a aVar2 = aVar.c;
                        ReferenceCountUtil.safeRelease(aVar.f);
                        ChannelPromise channelPromise = aVar.e;
                        a(aVar, false);
                        a(channelPromise, th);
                        aVar = aVar2;
                    }
                } else {
                    a();
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public void removeAndFail(Throwable th) {
        if (!a && !this.c.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (th != null) {
            a aVar = this.f;
            if (aVar != null) {
                ReferenceCountUtil.safeRelease(aVar.f);
                a(aVar.e, th);
                a(aVar, true);
            }
        } else {
            throw new NullPointerException("cause");
        }
    }

    public ChannelFuture removeAndWriteAll() {
        if (!a && !this.c.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (this.h == 1) {
            return removeAndWrite();
        } else {
            a aVar = this.f;
            if (aVar == null) {
                return null;
            }
            this.g = null;
            this.f = null;
            this.h = 0;
            ChannelPromise newPromise = this.c.newPromise();
            PromiseCombiner promiseCombiner = new PromiseCombiner();
            while (aVar != null) {
                try {
                    a aVar2 = aVar.c;
                    Object obj = aVar.f;
                    ChannelPromise channelPromise = aVar.e;
                    a(aVar, false);
                    promiseCombiner.add(channelPromise);
                    this.c.write(obj, channelPromise);
                    aVar = aVar2;
                } catch (Throwable th) {
                    newPromise.setFailure(th);
                }
            }
            a();
            promiseCombiner.finish(newPromise);
            return newPromise;
        }
    }

    private void a() {
        if (a) {
            return;
        }
        if (this.g != null || this.f != null || this.h != 0) {
            throw new AssertionError();
        }
    }

    public ChannelFuture removeAndWrite() {
        if (a || this.c.executor().inEventLoop()) {
            a aVar = this.f;
            if (aVar == null) {
                return null;
            }
            Object obj = aVar.f;
            ChannelPromise channelPromise = aVar.e;
            a(aVar, true);
            return this.c.write(obj, channelPromise);
        }
        throw new AssertionError();
    }

    public ChannelPromise remove() {
        if (a || this.c.executor().inEventLoop()) {
            a aVar = this.f;
            if (aVar == null) {
                return null;
            }
            ChannelPromise channelPromise = aVar.e;
            ReferenceCountUtil.safeRelease(aVar.f);
            a(aVar, true);
            return channelPromise;
        }
        throw new AssertionError();
    }

    public Object current() {
        if (a || this.c.executor().inEventLoop()) {
            a aVar = this.f;
            if (aVar == null) {
                return null;
            }
            return aVar.f;
        }
        throw new AssertionError();
    }

    private void a(a aVar, boolean z) {
        a aVar2 = aVar.c;
        long j = aVar.d;
        if (z) {
            if (aVar2 == null) {
                this.g = null;
                this.f = null;
                this.h = 0;
            } else {
                this.f = aVar2;
                this.h--;
                if (!a && this.h <= 0) {
                    throw new AssertionError();
                }
            }
        }
        aVar.a();
        ChannelOutboundBuffer channelOutboundBuffer = this.d;
        if (channelOutboundBuffer != null) {
            channelOutboundBuffer.b(j);
        }
    }

    private static void a(ChannelPromise channelPromise, Throwable th) {
        if (!(channelPromise instanceof g) && !channelPromise.tryFailure(th)) {
            b.warn("Failed to mark a promise as failure because it's done already: {}", channelPromise, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a {
        private static final Recycler<a> a = new Recycler<a>() { // from class: io.netty.channel.PendingWriteQueue.a.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public a newObject(Recycler.Handle<a> handle) {
                return new a(handle);
            }
        };
        private final Recycler.Handle<a> b;
        private a c;
        private long d;
        private ChannelPromise e;
        private Object f;

        private a(Recycler.Handle<a> handle) {
            this.b = handle;
        }

        static a a(Object obj, int i, ChannelPromise channelPromise) {
            a aVar = a.get();
            aVar.d = i;
            aVar.f = obj;
            aVar.e = channelPromise;
            return aVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            this.d = 0L;
            this.c = null;
            this.f = null;
            this.e = null;
            this.b.recycle(this);
        }
    }
}
