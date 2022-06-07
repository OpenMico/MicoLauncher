package org.greenrobot.eventbus;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PendingPostQueue.java */
/* loaded from: classes5.dex */
public final class d {
    private c a;
    private c b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(c cVar) {
        try {
            if (cVar != null) {
                if (this.b != null) {
                    this.b.c = cVar;
                    this.b = cVar;
                } else if (this.a == null) {
                    this.b = cVar;
                    this.a = cVar;
                } else {
                    throw new IllegalStateException("Head present, but no tail");
                }
                notifyAll();
            } else {
                throw new NullPointerException("null cannot be enqueued");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized c a() {
        c cVar;
        cVar = this.a;
        if (this.a != null) {
            this.a = this.a.c;
            if (this.a == null) {
                this.b = null;
            }
        }
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized c a(int i) throws InterruptedException {
        if (this.a == null) {
            wait(i);
        }
        return a();
    }
}
