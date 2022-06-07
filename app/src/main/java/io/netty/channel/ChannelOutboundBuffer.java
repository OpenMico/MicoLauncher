package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.fourthline.cling.support.messagebox.parser.MessageElement;

/* loaded from: classes4.dex */
public final class ChannelOutboundBuffer {
    static final /* synthetic */ boolean a = !ChannelOutboundBuffer.class.desiredAssertionStatus();
    private static final InternalLogger b = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
    private static final FastThreadLocal<ByteBuffer[]> c = new FastThreadLocal<ByteBuffer[]>() { // from class: io.netty.channel.ChannelOutboundBuffer.1
        /* renamed from: a */
        public ByteBuffer[] initialValue() throws Exception {
            return new ByteBuffer[1024];
        }
    };
    private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> l;
    private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> n;
    private final Channel d;
    private a e;
    private a f;
    private a g;
    private int h;
    private int i;
    private long j;
    private boolean k;
    private volatile long m;
    private volatile int o;
    private volatile Runnable p;

    /* loaded from: classes4.dex */
    public interface MessageProcessor {
        boolean processMessage(Object obj) throws Exception;
    }

    @Deprecated
    public void recycle() {
    }

    static {
        AtomicIntegerFieldUpdater<ChannelOutboundBuffer> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(ChannelOutboundBuffer.class, "unwritable");
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "o");
        }
        n = newAtomicIntegerFieldUpdater;
        AtomicLongFieldUpdater<ChannelOutboundBuffer> newAtomicLongFieldUpdater = PlatformDependent.newAtomicLongFieldUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
        if (newAtomicLongFieldUpdater == null) {
            newAtomicLongFieldUpdater = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, MessageElement.XPATH_PREFIX);
        }
        l = newAtomicLongFieldUpdater;
    }

    public ChannelOutboundBuffer(AbstractChannel abstractChannel) {
        this.d = abstractChannel;
    }

    public void addMessage(Object obj, int i, ChannelPromise channelPromise) {
        a a2 = a.a(obj, i, a(obj), channelPromise);
        a aVar = this.g;
        if (aVar == null) {
            this.e = null;
            this.g = a2;
        } else {
            aVar.a = a2;
            this.g = a2;
        }
        if (this.f == null) {
            this.f = a2;
        }
        a(i, false);
    }

    public void addFlush() {
        a aVar = this.f;
        if (aVar != null) {
            if (this.e == null) {
                this.e = aVar;
            }
            do {
                this.h++;
                if (!aVar.e.setUncancellable()) {
                    a(aVar.a(), false, true);
                }
                aVar = aVar.a;
            } while (aVar != null);
            this.f = null;
        }
    }

    public void a(long j) {
        a(j, true);
    }

    private void a(long j, boolean z) {
        if (j != 0 && l.addAndGet(this, j) >= this.d.config().getWriteBufferHighWaterMark()) {
            b(z);
        }
    }

    public void b(long j) {
        a(j, true, true);
    }

    private void a(long j, boolean z, boolean z2) {
        if (j != 0) {
            long addAndGet = l.addAndGet(this, -j);
            if (!z2) {
                return;
            }
            if (addAndGet == 0 || addAndGet <= this.d.config().getWriteBufferLowWaterMark()) {
                a(z);
            }
        }
    }

    private static long a(Object obj) {
        if (obj instanceof ByteBuf) {
            return ((ByteBuf) obj).readableBytes();
        }
        if (obj instanceof FileRegion) {
            return ((FileRegion) obj).count();
        }
        if (obj instanceof ByteBufHolder) {
            return ((ByteBufHolder) obj).content().readableBytes();
        }
        return -1L;
    }

    public Object current() {
        a aVar = this.e;
        if (aVar == null) {
            return null;
        }
        return aVar.b;
    }

    public void progress(long j) {
        a aVar = this.e;
        if (a || aVar != null) {
            ChannelPromise channelPromise = aVar.e;
            if (channelPromise instanceof ChannelProgressivePromise) {
                long j2 = aVar.f + j;
                aVar.f = j2;
                ((ChannelProgressivePromise) channelPromise).tryProgress(j2, aVar.g);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    public boolean remove() {
        a aVar = this.e;
        if (aVar == null) {
            a();
            return false;
        }
        Object obj = aVar.b;
        ChannelPromise channelPromise = aVar.e;
        int i = aVar.h;
        a(aVar);
        if (!aVar.j) {
            ReferenceCountUtil.safeRelease(obj);
            a(channelPromise);
            a(i, false, true);
        }
        aVar.b();
        return true;
    }

    public boolean remove(Throwable th) {
        return b(th, true);
    }

    private boolean b(Throwable th, boolean z) {
        a aVar = this.e;
        if (aVar == null) {
            a();
            return false;
        }
        Object obj = aVar.b;
        ChannelPromise channelPromise = aVar.e;
        int i = aVar.h;
        a(aVar);
        if (!aVar.j) {
            ReferenceCountUtil.safeRelease(obj);
            a(channelPromise, th);
            a(i, false, z);
        }
        aVar.b();
        return true;
    }

    private void a(a aVar) {
        int i = this.h - 1;
        this.h = i;
        if (i == 0) {
            this.e = null;
            if (aVar == this.g) {
                this.g = null;
                this.f = null;
                return;
            }
            return;
        }
        this.e = aVar.a;
    }

    public void removeBytes(long j) {
        while (true) {
            Object current = current();
            if (current instanceof ByteBuf) {
                ByteBuf byteBuf = (ByteBuf) current;
                int readerIndex = byteBuf.readerIndex();
                long writerIndex = byteBuf.writerIndex() - readerIndex;
                if (writerIndex <= j) {
                    if (j != 0) {
                        progress(writerIndex);
                        j -= writerIndex;
                    }
                    remove();
                } else if (j != 0) {
                    byteBuf.readerIndex(readerIndex + ((int) j));
                    progress(j);
                }
            } else if (!a && j != 0) {
                throw new AssertionError();
            }
        }
        a();
    }

    private void a() {
        int i = this.i;
        if (i > 0) {
            this.i = 0;
            Arrays.fill(c.get(), 0, i, (Object) null);
        }
    }

    public ByteBuffer[] nioBuffers() {
        ByteBuf byteBuf;
        int readerIndex;
        int writerIndex;
        InternalThreadLocalMap internalThreadLocalMap = InternalThreadLocalMap.get();
        ByteBuffer[] byteBufferArr = c.get(internalThreadLocalMap);
        long j = 0;
        int i = 0;
        for (a aVar = this.e; b(aVar) && (aVar.b instanceof ByteBuf); aVar = aVar.a) {
            if (!aVar.j && (writerIndex = byteBuf.writerIndex() - (readerIndex = (byteBuf = (ByteBuf) aVar.b).readerIndex())) > 0) {
                if (Integer.MAX_VALUE - writerIndex < j) {
                    break;
                }
                j += writerIndex;
                int i2 = aVar.i;
                if (i2 == -1) {
                    i2 = byteBuf.nioBufferCount();
                    aVar.i = i2;
                }
                int i3 = i + i2;
                if (i3 > byteBufferArr.length) {
                    byteBufferArr = a(byteBufferArr, i3, i);
                    c.set(internalThreadLocalMap, byteBufferArr);
                }
                if (i2 == 1) {
                    ByteBuffer byteBuffer = aVar.d;
                    if (byteBuffer == null) {
                        byteBuffer = byteBuf.internalNioBuffer(readerIndex, writerIndex);
                        aVar.d = byteBuffer;
                    }
                    i++;
                    byteBufferArr[i] = byteBuffer;
                } else {
                    ByteBuffer[] byteBufferArr2 = aVar.c;
                    if (byteBufferArr2 == null) {
                        byteBufferArr2 = byteBuf.nioBuffers();
                        aVar.c = byteBufferArr2;
                    }
                    i = a(byteBufferArr2, byteBufferArr, i);
                }
            }
        }
        this.i = i;
        this.j = j;
        return byteBufferArr;
    }

    private static int a(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2, int i) {
        for (ByteBuffer byteBuffer : byteBufferArr) {
            if (byteBuffer == null) {
                break;
            }
            i++;
            byteBufferArr2[i] = byteBuffer;
        }
        return i;
    }

    private static ByteBuffer[] a(ByteBuffer[] byteBufferArr, int i, int i2) {
        int length = byteBufferArr.length;
        do {
            length <<= 1;
            if (length < 0) {
                throw new IllegalStateException();
            }
        } while (i > length);
        ByteBuffer[] byteBufferArr2 = new ByteBuffer[length];
        System.arraycopy(byteBufferArr, 0, byteBufferArr2, 0, i2);
        return byteBufferArr2;
    }

    public int nioBufferCount() {
        return this.i;
    }

    public long nioBufferSize() {
        return this.j;
    }

    public boolean isWritable() {
        return this.o == 0;
    }

    public boolean getUserDefinedWritability(int i) {
        return (c(i) & this.o) == 0;
    }

    public void setUserDefinedWritability(int i, boolean z) {
        if (z) {
            a(i);
        } else {
            b(i);
        }
    }

    private void a(int i) {
        int i2;
        int i3;
        int i4 = ~c(i);
        do {
            i2 = this.o;
            i3 = i2 & i4;
        } while (!n.compareAndSet(this, i2, i3));
        if (i2 != 0 && i3 == 0) {
            c(true);
        }
    }

    private void b(int i) {
        int i2;
        int i3;
        int c2 = c(i);
        do {
            i2 = this.o;
            i3 = i2 | c2;
        } while (!n.compareAndSet(this, i2, i3));
        if (i2 == 0 && i3 != 0) {
            c(true);
        }
    }

    private static int c(int i) {
        if (i >= 1 && i <= 31) {
            return 1 << i;
        }
        throw new IllegalArgumentException("index: " + i + " (expected: 1~31)");
    }

    private void a(boolean z) {
        int i;
        int i2;
        do {
            i = this.o;
            i2 = i & (-2);
        } while (!n.compareAndSet(this, i, i2));
        if (i != 0 && i2 == 0) {
            c(z);
        }
    }

    private void b(boolean z) {
        int i;
        int i2;
        do {
            i = this.o;
            i2 = i | 1;
        } while (!n.compareAndSet(this, i, i2));
        if (i == 0 && i2 != 0) {
            c(z);
        }
    }

    private void c(boolean z) {
        final ChannelPipeline pipeline = this.d.pipeline();
        if (z) {
            Runnable runnable = this.p;
            if (runnable == null) {
                runnable = new Runnable() { // from class: io.netty.channel.ChannelOutboundBuffer.2
                    @Override // java.lang.Runnable
                    public void run() {
                        pipeline.fireChannelWritabilityChanged();
                    }
                };
                this.p = runnable;
            }
            this.d.eventLoop().execute(runnable);
            return;
        }
        pipeline.fireChannelWritabilityChanged();
    }

    public int size() {
        return this.h;
    }

    public boolean isEmpty() {
        return this.h == 0;
    }

    public void a(Throwable th, boolean z) {
        if (!this.k) {
            try {
                this.k = true;
                do {
                } while (b(th, z));
            } finally {
                this.k = false;
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public void a(final ClosedChannelException closedChannelException) {
        if (this.k) {
            this.d.eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.ChannelOutboundBuffer.3
                @Override // java.lang.Runnable
                public void run() {
                    ChannelOutboundBuffer.this.a(closedChannelException);
                }
            });
            return;
        }
        this.k = true;
        if (this.d.isOpen()) {
            throw new IllegalStateException("close() must be invoked after the channel is closed.");
        } else if (isEmpty()) {
            try {
                for (a aVar = this.f; aVar != null; aVar = aVar.c()) {
                    l.addAndGet(this, -aVar.h);
                    if (!aVar.j) {
                        ReferenceCountUtil.safeRelease(aVar.b);
                        a(aVar.e, closedChannelException);
                    }
                }
                this.k = false;
                a();
            } catch (Throwable th) {
                this.k = false;
                throw th;
            }
        } else {
            throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
        }
    }

    private static void a(ChannelPromise channelPromise) {
        if (!(channelPromise instanceof g) && !channelPromise.trySuccess()) {
            Throwable cause = channelPromise.cause();
            if (cause == null) {
                b.warn("Failed to mark a promise as success because it has succeeded already: {}", channelPromise);
            } else {
                b.warn("Failed to mark a promise as success because it has failed already: {}, unnotified cause {}", channelPromise, a(cause));
            }
        }
    }

    private static void a(ChannelPromise channelPromise, Throwable th) {
        if (!(channelPromise instanceof g) && !channelPromise.tryFailure(th)) {
            Throwable cause = channelPromise.cause();
            if (cause == null) {
                b.warn("Failed to mark a promise as failure because it has succeeded already: {}", channelPromise, th);
            } else {
                b.warn("Failed to mark a promise as failure because it hass failed already: {}, unnotified cause {}", channelPromise, a(cause), th);
            }
        }
    }

    private static String a(Throwable th) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        try {
            return new String(byteArrayOutputStream.toByteArray());
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public long totalPendingWriteBytes() {
        return this.m;
    }

    public long bytesBeforeUnwritable() {
        long writeBufferHighWaterMark = this.d.config().getWriteBufferHighWaterMark() - this.m;
        if (writeBufferHighWaterMark <= 0 || !isWritable()) {
            return 0L;
        }
        return writeBufferHighWaterMark;
    }

    public long bytesBeforeWritable() {
        long writeBufferLowWaterMark = this.m - this.d.config().getWriteBufferLowWaterMark();
        if (writeBufferLowWaterMark <= 0 || isWritable()) {
            return 0L;
        }
        return writeBufferLowWaterMark;
    }

    public void forEachFlushedMessage(MessageProcessor messageProcessor) throws Exception {
        if (messageProcessor != null) {
            a aVar = this.e;
            if (aVar != null) {
                do {
                    if (aVar.j || messageProcessor.processMessage(aVar.b)) {
                        aVar = aVar.a;
                    } else {
                        return;
                    }
                } while (b(aVar));
                return;
            }
            return;
        }
        throw new NullPointerException("processor");
    }

    private boolean b(a aVar) {
        return (aVar == null || aVar == this.f) ? false : true;
    }

    /* loaded from: classes4.dex */
    public static final class a {
        private static final Recycler<a> k = new Recycler<a>() { // from class: io.netty.channel.ChannelOutboundBuffer.a.1
            /* renamed from: a */
            public a newObject(Recycler.Handle handle) {
                return new a(handle);
            }
        };
        a a;
        Object b;
        ByteBuffer[] c;
        ByteBuffer d;
        ChannelPromise e;
        long f;
        long g;
        int h;
        int i;
        boolean j;
        private final Recycler.Handle<a> l;

        private a(Recycler.Handle<a> handle) {
            this.i = -1;
            this.l = handle;
        }

        static a a(Object obj, int i, long j, ChannelPromise channelPromise) {
            a aVar = k.get();
            aVar.b = obj;
            aVar.h = i;
            aVar.g = j;
            aVar.e = channelPromise;
            return aVar;
        }

        int a() {
            if (this.j) {
                return 0;
            }
            this.j = true;
            int i = this.h;
            ReferenceCountUtil.safeRelease(this.b);
            this.b = Unpooled.EMPTY_BUFFER;
            this.h = 0;
            this.g = 0L;
            this.f = 0L;
            this.c = null;
            this.d = null;
            return i;
        }

        void b() {
            this.a = null;
            this.c = null;
            this.d = null;
            this.b = null;
            this.e = null;
            this.f = 0L;
            this.g = 0L;
            this.h = 0;
            this.i = -1;
            this.j = false;
            this.l.recycle(this);
        }

        a c() {
            a aVar = this.a;
            b();
            return aVar;
        }
    }
}
