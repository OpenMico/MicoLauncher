package io.netty.resolver.dns;

import com.fasterxml.jackson.core.JsonPointer;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class DnsCacheEntry {
    static final /* synthetic */ boolean a = !DnsCacheEntry.class.desiredAssertionStatus();
    private final String b;
    private final InetAddress c;
    private final Throwable d;
    private volatile ScheduledFuture<?> e;

    public DnsCacheEntry(String str, InetAddress inetAddress) {
        this.b = (String) ObjectUtil.checkNotNull(str, "hostname");
        this.c = (InetAddress) ObjectUtil.checkNotNull(inetAddress, "address");
        this.d = null;
    }

    public DnsCacheEntry(String str, Throwable th) {
        this.b = (String) ObjectUtil.checkNotNull(str, "hostname");
        this.d = (Throwable) ObjectUtil.checkNotNull(th, "cause");
        this.c = null;
    }

    public String hostname() {
        return this.b;
    }

    public InetAddress address() {
        return this.c;
    }

    public Throwable cause() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(EventLoop eventLoop, Runnable runnable, long j, TimeUnit timeUnit) {
        if (a || this.e == null) {
            this.e = eventLoop.schedule(runnable, j, timeUnit);
            return;
        }
        throw new AssertionError("expiration task scheduled already");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        ScheduledFuture<?> scheduledFuture = this.e;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

    public String toString() {
        if (this.d == null) {
            return this.c.toString();
        }
        return this.b + JsonPointer.SEPARATOR + this.d;
    }
}
