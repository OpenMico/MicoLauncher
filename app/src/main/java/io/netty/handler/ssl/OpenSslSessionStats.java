package io.netty.handler.ssl;

import org.apache.tomcat.jni.SSLContext;

/* loaded from: classes4.dex */
public final class OpenSslSessionStats {
    private final long a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSslSessionStats(long j) {
        this.a = j;
    }

    public long number() {
        return SSLContext.sessionNumber(this.a);
    }

    public long connect() {
        return SSLContext.sessionConnect(this.a);
    }

    public long connectGood() {
        return SSLContext.sessionConnectGood(this.a);
    }

    public long connectRenegotiate() {
        return SSLContext.sessionConnectRenegotiate(this.a);
    }

    public long accept() {
        return SSLContext.sessionAccept(this.a);
    }

    public long acceptGood() {
        return SSLContext.sessionAcceptGood(this.a);
    }

    public long acceptRenegotiate() {
        return SSLContext.sessionAcceptRenegotiate(this.a);
    }

    public long hits() {
        return SSLContext.sessionHits(this.a);
    }

    public long cbHits() {
        return SSLContext.sessionCbHits(this.a);
    }

    public long misses() {
        return SSLContext.sessionMisses(this.a);
    }

    public long timeouts() {
        return SSLContext.sessionTimeouts(this.a);
    }

    public long cacheFull() {
        return SSLContext.sessionCacheFull(this.a);
    }
}
