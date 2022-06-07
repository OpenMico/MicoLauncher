package androidx.loader.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.os.OperationCanceledException;
import androidx.core.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public abstract class AsyncTaskLoader<D> extends Loader<D> {
    volatile AsyncTaskLoader<D>.a a;
    volatile AsyncTaskLoader<D>.a b;
    long c;
    long d;
    Handler e;
    private final Executor f;

    public void cancelLoadInBackground() {
    }

    @Nullable
    public abstract D loadInBackground();

    public void onCanceled(@Nullable D d) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class a extends ModernAsyncTask<Void, Void, D> implements Runnable {
        boolean a;
        private final CountDownLatch f = new CountDownLatch(1);

        a() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public D a(Void... voidArr) {
            try {
                return (D) AsyncTaskLoader.this.onLoadInBackground();
            } catch (OperationCanceledException e) {
                if (d()) {
                    return null;
                }
                throw e;
            }
        }

        @Override // androidx.loader.content.ModernAsyncTask
        protected void a(D d) {
            try {
                AsyncTaskLoader.this.b(this, d);
            } finally {
                this.f.countDown();
            }
        }

        @Override // androidx.loader.content.ModernAsyncTask
        protected void b(D d) {
            try {
                AsyncTaskLoader.this.a(this, d);
            } finally {
                this.f.countDown();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a = false;
            AsyncTaskLoader.this.a();
        }

        public void a() {
            try {
                this.f.await();
            } catch (InterruptedException unused) {
            }
        }
    }

    public AsyncTaskLoader(@NonNull Context context) {
        this(context, ModernAsyncTask.c);
    }

    private AsyncTaskLoader(@NonNull Context context, @NonNull Executor executor) {
        super(context);
        this.d = -10000L;
        this.f = executor;
    }

    public void setUpdateThrottle(long j) {
        this.c = j;
        if (j != 0) {
            this.e = new Handler();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.loader.content.Loader
    public void onForceLoad() {
        super.onForceLoad();
        cancelLoad();
        this.a = new a();
        a();
    }

    @Override // androidx.loader.content.Loader
    protected boolean onCancelLoad() {
        if (this.a == null) {
            return false;
        }
        if (!this.r) {
            this.u = true;
        }
        if (this.b != null) {
            if (this.a.a) {
                this.a.a = false;
                this.e.removeCallbacks(this.a);
            }
            this.a = null;
            return false;
        } else if (this.a.a) {
            this.a.a = false;
            this.e.removeCallbacks(this.a);
            this.a = null;
            return false;
        } else {
            boolean a2 = this.a.a(false);
            if (a2) {
                this.b = this.a;
                cancelLoadInBackground();
            }
            this.a = null;
            return a2;
        }
    }

    void a() {
        if (this.b == null && this.a != null) {
            if (this.a.a) {
                this.a.a = false;
                this.e.removeCallbacks(this.a);
            }
            if (this.c <= 0 || SystemClock.uptimeMillis() >= this.d + this.c) {
                this.a.a(this.f, null);
                return;
            }
            this.a.a = true;
            this.e.postAtTime(this.a, this.d + this.c);
        }
    }

    void a(AsyncTaskLoader<D>.a aVar, D d) {
        onCanceled(d);
        if (this.b == aVar) {
            rollbackContentChanged();
            this.d = SystemClock.uptimeMillis();
            this.b = null;
            deliverCancellation();
            a();
        }
    }

    void b(AsyncTaskLoader<D>.a aVar, D d) {
        if (this.a != aVar) {
            a(aVar, d);
        } else if (isAbandoned()) {
            onCanceled(d);
        } else {
            commitContentChanged();
            this.d = SystemClock.uptimeMillis();
            this.a = null;
            deliverResult(d);
        }
    }

    @Nullable
    protected D onLoadInBackground() {
        return loadInBackground();
    }

    public boolean isLoadInBackgroundCanceled() {
        return this.b != null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void waitForLoader() {
        AsyncTaskLoader<D>.a aVar = this.a;
        if (aVar != null) {
            aVar.a();
        }
    }

    @Override // androidx.loader.content.Loader
    @Deprecated
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        if (this.a != null) {
            printWriter.print(str);
            printWriter.print("mTask=");
            printWriter.print(this.a);
            printWriter.print(" waiting=");
            printWriter.println(this.a.a);
        }
        if (this.b != null) {
            printWriter.print(str);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.b);
            printWriter.print(" waiting=");
            printWriter.println(this.b.a);
        }
        if (this.c != 0) {
            printWriter.print(str);
            printWriter.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.c, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.d, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }
}
