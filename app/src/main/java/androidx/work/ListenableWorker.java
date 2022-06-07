package androidx.work;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Network;
import android.net.Uri;
import androidx.annotation.IntRange;
import androidx.annotation.Keep;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public abstract class ListenableWorker {
    @NonNull
    private Context a;
    @NonNull
    private WorkerParameters b;
    private volatile boolean c;
    private boolean d;
    private boolean e;

    public void onStopped() {
    }

    @NonNull
    @MainThread
    public abstract ListenableFuture<Result> startWork();

    @Keep
    @SuppressLint({"BanKeepAnnotation"})
    public ListenableWorker(@NonNull Context context, @NonNull WorkerParameters workerParameters) {
        if (context == null) {
            throw new IllegalArgumentException("Application Context is null");
        } else if (workerParameters != null) {
            this.a = context;
            this.b = workerParameters;
        } else {
            throw new IllegalArgumentException("WorkerParameters is null");
        }
    }

    @NonNull
    public final Context getApplicationContext() {
        return this.a;
    }

    @NonNull
    public final UUID getId() {
        return this.b.getId();
    }

    @NonNull
    public final Data getInputData() {
        return this.b.getInputData();
    }

    @NonNull
    public final Set<String> getTags() {
        return this.b.getTags();
    }

    @NonNull
    @RequiresApi(24)
    public final List<Uri> getTriggeredContentUris() {
        return this.b.getTriggeredContentUris();
    }

    @NonNull
    @RequiresApi(24)
    public final List<String> getTriggeredContentAuthorities() {
        return this.b.getTriggeredContentAuthorities();
    }

    @Nullable
    @RequiresApi(28)
    public final Network getNetwork() {
        return this.b.getNetwork();
    }

    @IntRange(from = 0)
    public final int getRunAttemptCount() {
        return this.b.getRunAttemptCount();
    }

    @NonNull
    public ListenableFuture<Void> setProgressAsync(@NonNull Data data) {
        return this.b.getProgressUpdater().updateProgress(getApplicationContext(), getId(), data);
    }

    @NonNull
    public final ListenableFuture<Void> setForegroundAsync(@NonNull ForegroundInfo foregroundInfo) {
        this.e = true;
        return this.b.getForegroundUpdater().setForegroundAsync(getApplicationContext(), getId(), foregroundInfo);
    }

    public final boolean isStopped() {
        return this.c;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void stop() {
        this.c = true;
        onStopped();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final boolean isUsed() {
        return this.d;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void setUsed() {
        this.d = true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRunInForeground() {
        return this.e;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Executor getBackgroundExecutor() {
        return this.b.getBackgroundExecutor();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public TaskExecutor getTaskExecutor() {
        return this.b.getTaskExecutor();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkerFactory getWorkerFactory() {
        return this.b.getWorkerFactory();
    }

    /* loaded from: classes.dex */
    public static abstract class Result {
        @NonNull
        public abstract Data getOutputData();

        @NonNull
        public static Result success() {
            return new Success();
        }

        @NonNull
        public static Result success(@NonNull Data data) {
            return new Success(data);
        }

        @NonNull
        public static Result retry() {
            return new Retry();
        }

        @NonNull
        public static Result failure() {
            return new Failure();
        }

        @NonNull
        public static Result failure(@NonNull Data data) {
            return new Failure(data);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        Result() {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes.dex */
        public static final class Success extends Result {
            private final Data a;

            public Success() {
                this(Data.EMPTY);
            }

            public Success(@NonNull Data data) {
                this.a = data;
            }

            @Override // androidx.work.ListenableWorker.Result
            @NonNull
            public Data getOutputData() {
                return this.a;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                return this.a.equals(((Success) obj).a);
            }

            public int hashCode() {
                return (Success.class.getName().hashCode() * 31) + this.a.hashCode();
            }

            public String toString() {
                return "Success {mOutputData=" + this.a + '}';
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes.dex */
        public static final class Failure extends Result {
            private final Data a;

            public Failure() {
                this(Data.EMPTY);
            }

            public Failure(@NonNull Data data) {
                this.a = data;
            }

            @Override // androidx.work.ListenableWorker.Result
            @NonNull
            public Data getOutputData() {
                return this.a;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                return this.a.equals(((Failure) obj).a);
            }

            public int hashCode() {
                return (Failure.class.getName().hashCode() * 31) + this.a.hashCode();
            }

            public String toString() {
                return "Failure {mOutputData=" + this.a + '}';
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes.dex */
        public static final class Retry extends Result {
            public String toString() {
                return "Retry";
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass();
            }

            public int hashCode() {
                return Retry.class.getName().hashCode();
            }

            @Override // androidx.work.ListenableWorker.Result
            @NonNull
            public Data getOutputData() {
                return Data.EMPTY;
            }
        }
    }
}
