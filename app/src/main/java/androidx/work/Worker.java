package androidx.work;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.work.ListenableWorker;
import androidx.work.impl.utils.futures.SettableFuture;
import com.google.common.util.concurrent.ListenableFuture;

/* loaded from: classes.dex */
public abstract class Worker extends ListenableWorker {
    SettableFuture<ListenableWorker.Result> a;

    @NonNull
    @WorkerThread
    public abstract ListenableWorker.Result doWork();

    @Keep
    @SuppressLint({"BanKeepAnnotation"})
    public Worker(@NonNull Context context, @NonNull WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    @Override // androidx.work.ListenableWorker
    @NonNull
    public final ListenableFuture<ListenableWorker.Result> startWork() {
        this.a = SettableFuture.create();
        getBackgroundExecutor().execute(new Runnable() { // from class: androidx.work.Worker.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Worker.this.a.set(Worker.this.doWork());
                } catch (Throwable th) {
                    Worker.this.a.setException(th);
                }
            }
        });
        return this.a;
    }
}
