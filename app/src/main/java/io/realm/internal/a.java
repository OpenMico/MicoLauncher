package io.realm.internal;

import io.realm.log.RealmLog;
import java.lang.ref.ReferenceQueue;

/* compiled from: FinalizerRunnable.java */
/* loaded from: classes5.dex */
class a implements Runnable {
    private final ReferenceQueue<NativeObject> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(ReferenceQueue<NativeObject> referenceQueue) {
        this.a = referenceQueue;
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            try {
                ((NativeObjectReference) this.a.remove()).a();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                RealmLog.fatal("The FinalizerRunnable thread has been interrupted. Native resources cannot be freed anymore", new Object[0]);
                return;
            }
        }
    }
}
