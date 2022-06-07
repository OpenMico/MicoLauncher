package io.realm.internal.async;

import io.realm.RealmAsyncTask;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: classes5.dex */
public class RealmAsyncTaskImpl implements RealmAsyncTask {
    private final Future<?> a;
    private final ThreadPoolExecutor b;
    private volatile boolean c = false;

    public RealmAsyncTaskImpl(Future<?> future, ThreadPoolExecutor threadPoolExecutor) {
        this.a = future;
        this.b = threadPoolExecutor;
    }

    @Override // io.realm.RealmAsyncTask
    public void cancel() {
        this.a.cancel(true);
        this.c = true;
        this.b.getQueue().remove(this.a);
    }

    @Override // io.realm.RealmAsyncTask
    public boolean isCancelled() {
        return this.c;
    }
}
