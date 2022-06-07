package androidx.room;

import android.annotation.SuppressLint;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.LiveData;
import androidx.room.InvalidationTracker;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RoomTrackingLiveData.java */
/* loaded from: classes.dex */
public class l<T> extends LiveData<T> {
    final RoomDatabase a;
    final boolean b;
    final Callable<T> c;
    final InvalidationTracker.Observer d;
    final AtomicBoolean e = new AtomicBoolean(true);
    final AtomicBoolean f = new AtomicBoolean(false);
    final AtomicBoolean g = new AtomicBoolean(false);
    final Runnable h = new Runnable() { // from class: androidx.room.l.1
        @Override // java.lang.Runnable
        @WorkerThread
        public void run() {
            boolean z;
            if (l.this.g.compareAndSet(false, true)) {
                l.this.a.getInvalidationTracker().addWeakObserver(l.this.d);
            }
            do {
                if (l.this.f.compareAndSet(false, true)) {
                    T t = null;
                    z = false;
                    while (l.this.e.compareAndSet(true, false)) {
                        try {
                            try {
                                t = l.this.c.call();
                                z = true;
                            } catch (Exception e) {
                                throw new RuntimeException("Exception while computing database live data.", e);
                            }
                        } finally {
                            l.this.f.set(false);
                        }
                    }
                    if (z) {
                        l.this.postValue(t);
                    }
                } else {
                    z = false;
                }
                if (!z) {
                    return;
                }
            } while (l.this.e.get());
        }
    };
    final Runnable i = new Runnable() { // from class: androidx.room.l.2
        @Override // java.lang.Runnable
        @MainThread
        public void run() {
            boolean hasActiveObservers = l.this.hasActiveObservers();
            if (l.this.e.compareAndSet(false, true) && hasActiveObservers) {
                l.this.a().execute(l.this.h);
            }
        }
    };
    private final e j;

    /* JADX INFO: Access modifiers changed from: package-private */
    @SuppressLint({"RestrictedApi"})
    public l(RoomDatabase roomDatabase, e eVar, boolean z, Callable<T> callable, String[] strArr) {
        this.a = roomDatabase;
        this.b = z;
        this.c = callable;
        this.j = eVar;
        this.d = new InvalidationTracker.Observer(strArr) { // from class: androidx.room.l.3
            @Override // androidx.room.InvalidationTracker.Observer
            public void onInvalidated(@NonNull Set<String> set) {
                ArchTaskExecutor.getInstance().executeOnMainThread(l.this.i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.LiveData
    public void onActive() {
        super.onActive();
        this.j.a(this);
        a().execute(this.h);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.LiveData
    public void onInactive() {
        super.onInactive();
        this.j.b(this);
    }

    Executor a() {
        if (this.b) {
            return this.a.getTransactionExecutor();
        }
        return this.a.getQueryExecutor();
    }
}
