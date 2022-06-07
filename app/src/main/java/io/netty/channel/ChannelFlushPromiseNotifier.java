package io.netty.channel;

import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes4.dex */
public final class ChannelFlushPromiseNotifier {
    private long a;
    private final Queue<b> b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface b {
        long flushCheckpoint();

        void flushCheckpoint(long j);

        ChannelPromise promise();
    }

    public ChannelFlushPromiseNotifier(boolean z) {
        this.b = new ArrayDeque();
        this.c = z;
    }

    public ChannelFlushPromiseNotifier() {
        this(false);
    }

    @Deprecated
    public ChannelFlushPromiseNotifier add(ChannelPromise channelPromise, int i) {
        return add(channelPromise, i);
    }

    public ChannelFlushPromiseNotifier add(ChannelPromise channelPromise, long j) {
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        } else if (j >= 0) {
            long j2 = this.a + j;
            if (channelPromise instanceof b) {
                b bVar = (b) channelPromise;
                bVar.flushCheckpoint(j2);
                this.b.add(bVar);
            } else {
                this.b.add(new a(j2, channelPromise));
            }
            return this;
        } else {
            throw new IllegalArgumentException("pendingDataSize must be >= 0 but was " + j);
        }
    }

    public ChannelFlushPromiseNotifier increaseWriteCounter(long j) {
        if (j >= 0) {
            this.a += j;
            return this;
        }
        throw new IllegalArgumentException("delta must be >= 0 but was " + j);
    }

    public long writeCounter() {
        return this.a;
    }

    public ChannelFlushPromiseNotifier notifyPromises() {
        a(null);
        return this;
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures() {
        return notifyPromises();
    }

    public ChannelFlushPromiseNotifier notifyPromises(Throwable th) {
        notifyPromises();
        while (true) {
            b poll = this.b.poll();
            if (poll == null) {
                return this;
            }
            if (this.c) {
                poll.promise().tryFailure(th);
            } else {
                poll.promise().setFailure(th);
            }
        }
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable th) {
        return notifyPromises(th);
    }

    public ChannelFlushPromiseNotifier notifyPromises(Throwable th, Throwable th2) {
        a(th);
        while (true) {
            b poll = this.b.poll();
            if (poll == null) {
                return this;
            }
            if (this.c) {
                poll.promise().tryFailure(th2);
            } else {
                poll.promise().setFailure(th2);
            }
        }
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable th, Throwable th2) {
        return notifyPromises(th, th2);
    }

    private void a(Throwable th) {
        if (this.b.isEmpty()) {
            this.a = 0L;
            return;
        }
        long j = this.a;
        while (true) {
            b peek = this.b.peek();
            if (peek == null) {
                this.a = 0L;
                break;
            } else if (peek.flushCheckpoint() <= j) {
                this.b.remove();
                ChannelPromise promise = peek.promise();
                if (th == null) {
                    if (this.c) {
                        promise.trySuccess();
                    } else {
                        promise.setSuccess();
                    }
                } else if (this.c) {
                    promise.tryFailure(th);
                } else {
                    promise.setFailure(th);
                }
            } else if (j > 0 && this.b.size() == 1) {
                this.a = 0L;
                peek.flushCheckpoint(peek.flushCheckpoint() - j);
            }
        }
        long j2 = this.a;
        if (j2 >= 549755813888L) {
            this.a = 0L;
            for (b bVar : this.b) {
                bVar.flushCheckpoint(bVar.flushCheckpoint() - j2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a implements b {
        private long a;
        private final ChannelPromise b;

        a(long j, ChannelPromise channelPromise) {
            this.a = j;
            this.b = channelPromise;
        }

        @Override // io.netty.channel.ChannelFlushPromiseNotifier.b
        public long flushCheckpoint() {
            return this.a;
        }

        @Override // io.netty.channel.ChannelFlushPromiseNotifier.b
        public void flushCheckpoint(long j) {
            this.a = j;
        }

        @Override // io.netty.channel.ChannelFlushPromiseNotifier.b
        public ChannelPromise promise() {
            return this.b;
        }
    }
}
