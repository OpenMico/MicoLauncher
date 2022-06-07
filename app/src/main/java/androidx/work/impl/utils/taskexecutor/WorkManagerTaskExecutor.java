package androidx.work.impl.utils.taskexecutor;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.impl.utils.SerialExecutor;
import java.util.concurrent.Executor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkManagerTaskExecutor implements TaskExecutor {
    private final SerialExecutor a;
    private final Handler b = new Handler(Looper.getMainLooper());
    private final Executor c = new Executor() { // from class: androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor.1
        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            WorkManagerTaskExecutor.this.postToMainThread(runnable);
        }
    };

    public WorkManagerTaskExecutor(@NonNull Executor executor) {
        this.a = new SerialExecutor(executor);
    }

    @Override // androidx.work.impl.utils.taskexecutor.TaskExecutor
    public void postToMainThread(Runnable runnable) {
        this.b.post(runnable);
    }

    @Override // androidx.work.impl.utils.taskexecutor.TaskExecutor
    public Executor getMainThreadExecutor() {
        return this.c;
    }

    @Override // androidx.work.impl.utils.taskexecutor.TaskExecutor
    public void executeOnBackgroundThread(Runnable runnable) {
        this.a.execute(runnable);
    }

    @Override // androidx.work.impl.utils.taskexecutor.TaskExecutor
    @NonNull
    public SerialExecutor getBackgroundExecutor() {
        return this.a;
    }
}
