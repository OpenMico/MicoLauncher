package androidx.core.os;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes.dex */
public final class ExecutorCompat {
    @NonNull
    public static Executor create(@NonNull Handler handler) {
        return new a(handler);
    }

    private ExecutorCompat() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a implements Executor {
        private final Handler a;

        a(@NonNull Handler handler) {
            this.a = (Handler) Preconditions.checkNotNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            if (!this.a.post((Runnable) Preconditions.checkNotNull(runnable))) {
                throw new RejectedExecutionException(this.a + " is shutting down");
            }
        }
    }
}
