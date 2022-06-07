package androidx.work;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

/* loaded from: classes.dex */
public abstract class WorkerFactory {
    private static final String a = Logger.tagWithPrefix("WorkerFactory");

    @Nullable
    public abstract ListenableWorker createWorker(@NonNull Context context, @NonNull String str, @NonNull WorkerParameters workerParameters);

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final ListenableWorker createWorkerWithDefaultFallback(@NonNull Context context, @NonNull String str, @NonNull WorkerParameters workerParameters) {
        ListenableWorker listenableWorker;
        ListenableWorker createWorker = createWorker(context, str, workerParameters);
        if (createWorker == null) {
            Class cls = null;
            try {
                cls = Class.forName(str).asSubclass(ListenableWorker.class);
            } catch (Throwable th) {
                Logger logger = Logger.get();
                String str2 = a;
                logger.error(str2, "Invalid class: " + str, th);
            }
            if (cls != null) {
                try {
                    listenableWorker = (ListenableWorker) cls.getDeclaredConstructor(Context.class, WorkerParameters.class).newInstance(context, workerParameters);
                } catch (Throwable th2) {
                    Logger logger2 = Logger.get();
                    String str3 = a;
                    logger2.error(str3, "Could not instantiate " + str, th2);
                }
                if (listenableWorker != null || !listenableWorker.isUsed()) {
                    return listenableWorker;
                }
                throw new IllegalStateException(String.format("WorkerFactory (%s) returned an instance of a ListenableWorker (%s) which has already been invoked. createWorker() must always return a new instance of a ListenableWorker.", getClass().getName(), str));
            }
        }
        listenableWorker = createWorker;
        if (listenableWorker != null) {
        }
        return listenableWorker;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static WorkerFactory getDefaultWorkerFactory() {
        return new WorkerFactory() { // from class: androidx.work.WorkerFactory.1
            @Override // androidx.work.WorkerFactory
            @Nullable
            public ListenableWorker createWorker(@NonNull Context context, @NonNull String str, @NonNull WorkerParameters workerParameters) {
                return null;
            }
        };
    }
}
