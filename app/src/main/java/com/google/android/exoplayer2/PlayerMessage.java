package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public final class PlayerMessage {
    private final Target a;
    private final Sender b;
    private final Clock c;
    private final Timeline d;
    private int e;
    @Nullable
    private Object f;
    private Looper g;
    private int h;
    private long i = C.TIME_UNSET;
    private boolean j = true;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;

    /* loaded from: classes.dex */
    public interface Sender {
        void sendMessage(PlayerMessage playerMessage);
    }

    /* loaded from: classes.dex */
    public interface Target {
        void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException;
    }

    public PlayerMessage(Sender sender, Target target, Timeline timeline, int i, Clock clock, Looper looper) {
        this.b = sender;
        this.a = target;
        this.d = timeline;
        this.g = looper;
        this.c = clock;
        this.h = i;
    }

    public Timeline getTimeline() {
        return this.d;
    }

    public Target getTarget() {
        return this.a;
    }

    public PlayerMessage setType(int i) {
        Assertions.checkState(!this.k);
        this.e = i;
        return this;
    }

    public int getType() {
        return this.e;
    }

    public PlayerMessage setPayload(@Nullable Object obj) {
        Assertions.checkState(!this.k);
        this.f = obj;
        return this;
    }

    @Nullable
    public Object getPayload() {
        return this.f;
    }

    @Deprecated
    public PlayerMessage setHandler(Handler handler) {
        return setLooper(handler.getLooper());
    }

    public PlayerMessage setLooper(Looper looper) {
        Assertions.checkState(!this.k);
        this.g = looper;
        return this;
    }

    public Looper getLooper() {
        return this.g;
    }

    public long getPositionMs() {
        return this.i;
    }

    public PlayerMessage setPosition(long j) {
        Assertions.checkState(!this.k);
        this.i = j;
        return this;
    }

    public PlayerMessage setPosition(int i, long j) {
        boolean z = true;
        Assertions.checkState(!this.k);
        if (j == C.TIME_UNSET) {
            z = false;
        }
        Assertions.checkArgument(z);
        if (i < 0 || (!this.d.isEmpty() && i >= this.d.getWindowCount())) {
            throw new IllegalSeekPositionException(this.d, i, j);
        }
        this.h = i;
        this.i = j;
        return this;
    }

    public int getWindowIndex() {
        return this.h;
    }

    public PlayerMessage setDeleteAfterDelivery(boolean z) {
        Assertions.checkState(!this.k);
        this.j = z;
        return this;
    }

    public boolean getDeleteAfterDelivery() {
        return this.j;
    }

    public PlayerMessage send() {
        Assertions.checkState(!this.k);
        if (this.i == C.TIME_UNSET) {
            Assertions.checkArgument(this.j);
        }
        this.k = true;
        this.b.sendMessage(this);
        return this;
    }

    public synchronized PlayerMessage cancel() {
        Assertions.checkState(this.k);
        this.n = true;
        markAsProcessed(false);
        return this;
    }

    public synchronized boolean isCanceled() {
        return this.n;
    }

    public synchronized void markAsProcessed(boolean z) {
        this.l = z | this.l;
        this.m = true;
        notifyAll();
    }

    public synchronized boolean blockUntilDelivered() throws InterruptedException {
        Assertions.checkState(this.k);
        Assertions.checkState(this.g.getThread() != Thread.currentThread());
        while (!this.m) {
            wait();
        }
        return this.l;
    }

    public synchronized boolean blockUntilDelivered(long j) throws InterruptedException, TimeoutException {
        Assertions.checkState(this.k);
        Assertions.checkState(this.g.getThread() != Thread.currentThread());
        long elapsedRealtime = this.c.elapsedRealtime() + j;
        while (!this.m && j > 0) {
            this.c.onThreadBlocked();
            wait(j);
            j = elapsedRealtime - this.c.elapsedRealtime();
        }
        if (!this.m) {
            throw new TimeoutException("Message delivery timed out.");
        }
        return this.l;
    }
}
