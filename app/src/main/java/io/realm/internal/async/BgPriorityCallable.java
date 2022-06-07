package io.realm.internal.async;

import android.os.Process;
import java.util.concurrent.Callable;

/* loaded from: classes5.dex */
public class BgPriorityCallable<T> implements Callable<T> {
    private final Callable<T> a;

    @Override // java.util.concurrent.Callable
    public T call() throws Exception {
        Process.setThreadPriority(10);
        return this.a.call();
    }
}
