package com.xiaomi.infra.galaxy.fds.android.util;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionManager;

/* loaded from: classes3.dex */
public final class IdleConnectionReaper extends Thread {
    private final ClientConnectionManager a;

    public IdleConnectionReaper(ClientConnectionManager clientConnectionManager) {
        super("java-sdk-http-connection-reaper");
        this.a = clientConnectionManager;
        setDaemon(true);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000L);
                this.a.closeIdleConnections(60L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
