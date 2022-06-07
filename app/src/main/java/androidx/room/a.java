package androidx.room;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.room.util.SneakyThrow;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: AutoCloser.java */
/* loaded from: classes.dex */
public final class a {
    final long c;
    @NonNull
    final Executor d;
    @Nullable
    @GuardedBy("mLock")
    SupportSQLiteDatabase g;
    @Nullable
    private SupportSQLiteOpenHelper i = null;
    @NonNull
    private final Handler j = new Handler(Looper.getMainLooper());
    @Nullable
    Runnable a = null;
    @NonNull
    final Object b = new Object();
    @GuardedBy("mLock")
    int e = 0;
    @GuardedBy("mLock")
    long f = SystemClock.uptimeMillis();
    private boolean k = false;
    private final Runnable l = new Runnable() { // from class: androidx.room.a.1
        @Override // java.lang.Runnable
        public void run() {
            a.this.d.execute(a.this.h);
        }
    };
    @NonNull
    final Runnable h = new Runnable() { // from class: androidx.room.a.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (a.this.b) {
                if (SystemClock.uptimeMillis() - a.this.f >= a.this.c) {
                    if (a.this.e == 0) {
                        if (a.this.a != null) {
                            a.this.a.run();
                            if (a.this.g != null && a.this.g.isOpen()) {
                                try {
                                    a.this.g.close();
                                } catch (IOException e) {
                                    SneakyThrow.reThrow(e);
                                }
                                a.this.g = null;
                            }
                            return;
                        }
                        throw new IllegalStateException("mOnAutoCloseCallback is null but it should have been set before use. Please file a bug against Room at: https://issuetracker.google.com/issues/new?component=413107&template=1096568");
                    }
                }
            }
        }
    };

    public a(long j, @NonNull TimeUnit timeUnit, @NonNull Executor executor) {
        this.c = timeUnit.toMillis(j);
        this.d = executor;
    }

    public void a(@NonNull SupportSQLiteOpenHelper supportSQLiteOpenHelper) {
        if (this.i != null) {
            Log.e("ROOM", "AutoCloser initialized multiple times. Please file a bug against room at: https://issuetracker.google.com/issues/new?component=413107&template=1096568");
        } else {
            this.i = supportSQLiteOpenHelper;
        }
    }

    @Nullable
    public <V> V a(@NonNull Function<SupportSQLiteDatabase, V> function) {
        try {
            return function.apply(a());
        } finally {
            b();
        }
    }

    @NonNull
    public SupportSQLiteDatabase a() {
        synchronized (this.b) {
            this.j.removeCallbacks(this.l);
            this.e++;
            if (this.k) {
                throw new IllegalStateException("Attempting to open already closed database.");
            } else if (this.g != null && this.g.isOpen()) {
                return this.g;
            } else if (this.i != null) {
                this.g = this.i.getWritableDatabase();
                return this.g;
            } else {
                throw new IllegalStateException("AutoCloser has not been initialized. Please file a bug against Room at: https://issuetracker.google.com/issues/new?component=413107&template=1096568");
            }
        }
    }

    public void b() {
        synchronized (this.b) {
            if (this.e > 0) {
                this.e--;
                if (this.e == 0) {
                    if (this.g != null) {
                        this.j.postDelayed(this.l, this.c);
                    } else {
                        return;
                    }
                }
                return;
            }
            throw new IllegalStateException("ref count is 0 or lower but we're supposed to decrement");
        }
    }

    @Nullable
    public SupportSQLiteDatabase c() {
        SupportSQLiteDatabase supportSQLiteDatabase;
        synchronized (this.b) {
            supportSQLiteDatabase = this.g;
        }
        return supportSQLiteDatabase;
    }

    public void d() throws IOException {
        synchronized (this.b) {
            this.k = true;
            if (this.g != null) {
                this.g.close();
            }
            this.g = null;
        }
    }

    public boolean e() {
        return !this.k;
    }

    public void a(Runnable runnable) {
        this.a = runnable;
    }
}
