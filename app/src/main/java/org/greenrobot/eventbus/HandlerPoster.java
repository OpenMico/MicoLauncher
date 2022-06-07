package org.greenrobot.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* loaded from: classes5.dex */
public class HandlerPoster extends Handler implements e {
    private final d a = new d();
    private final int b;
    private final EventBus c;
    private boolean d;

    /* JADX INFO: Access modifiers changed from: protected */
    public HandlerPoster(EventBus eventBus, Looper looper, int i) {
        super(looper);
        this.c = eventBus;
        this.b = i;
    }

    @Override // org.greenrobot.eventbus.e
    public void enqueue(g gVar, Object obj) {
        c a = c.a(gVar, obj);
        synchronized (this) {
            this.a.a(a);
            if (!this.d) {
                this.d = true;
                if (!sendMessage(obtainMessage())) {
                    throw new EventBusException("Could not send handler message");
                }
            }
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        boolean z = false;
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            do {
                c a = this.a.a();
                if (a == null) {
                    synchronized (this) {
                        a = this.a.a();
                        if (a == null) {
                            this.d = z;
                            return;
                        }
                    }
                }
                this.c.a(a);
            } while (SystemClock.uptimeMillis() - uptimeMillis < this.b);
            if (sendMessage(obtainMessage())) {
                z = true;
                return;
            }
            throw new EventBusException("Could not send handler message");
        } finally {
            this.d = z;
        }
    }
}
