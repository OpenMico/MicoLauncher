package org.greenrobot.eventbus;

/* compiled from: AsyncPoster.java */
/* loaded from: classes5.dex */
class a implements Runnable, e {
    private final d a = new d();
    private final EventBus b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(EventBus eventBus) {
        this.b = eventBus;
    }

    @Override // org.greenrobot.eventbus.e
    public void enqueue(g gVar, Object obj) {
        this.a.a(c.a(gVar, obj));
        this.b.a().execute(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        c a = this.a.a();
        if (a != null) {
            this.b.a(a);
            return;
        }
        throw new IllegalStateException("No pending post available");
    }
}
