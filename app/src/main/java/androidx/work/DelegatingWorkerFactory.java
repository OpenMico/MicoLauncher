package androidx.work;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class DelegatingWorkerFactory extends WorkerFactory {
    private static final String a = Logger.tagWithPrefix("DelegatingWkrFctry");
    private final List<WorkerFactory> b = new CopyOnWriteArrayList();

    public final void addFactory(@NonNull WorkerFactory workerFactory) {
        this.b.add(workerFactory);
    }

    @Override // androidx.work.WorkerFactory
    @Nullable
    public final ListenableWorker createWorker(@NonNull Context context, @NonNull String str, @NonNull WorkerParameters workerParameters) {
        for (WorkerFactory workerFactory : this.b) {
            try {
                ListenableWorker createWorker = workerFactory.createWorker(context, str, workerParameters);
                if (createWorker != null) {
                    return createWorker;
                }
            } catch (Throwable th) {
                Logger.get().error(a, String.format("Unable to instantiate a ListenableWorker (%s)", str), th);
                throw th;
            }
        }
        return null;
    }
}
