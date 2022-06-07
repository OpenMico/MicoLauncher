package androidx.work;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.work.impl.DefaultRunnableScheduler;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public final class Configuration {
    @SuppressLint({"MinMaxConstant"})
    public static final int MIN_SCHEDULER_LIMIT = 20;
    @NonNull
    final Executor a;
    @NonNull
    final Executor b;
    @NonNull
    final WorkerFactory c;
    @NonNull
    final InputMergerFactory d;
    @NonNull
    final RunnableScheduler e;
    @Nullable
    final InitializationExceptionHandler f;
    @Nullable
    final String g;
    final int h;
    final int i;
    final int j;
    final int k;
    private final boolean l;

    /* loaded from: classes.dex */
    public interface Provider {
        @NonNull
        Configuration getWorkManagerConfiguration();
    }

    Configuration(@NonNull Builder builder) {
        if (builder.a == null) {
            this.a = a();
        } else {
            this.a = builder.a;
        }
        if (builder.d == null) {
            this.l = true;
            this.b = a();
        } else {
            this.l = false;
            this.b = builder.d;
        }
        if (builder.b == null) {
            this.c = WorkerFactory.getDefaultWorkerFactory();
        } else {
            this.c = builder.b;
        }
        if (builder.c == null) {
            this.d = InputMergerFactory.getDefaultInputMergerFactory();
        } else {
            this.d = builder.c;
        }
        if (builder.e == null) {
            this.e = new DefaultRunnableScheduler();
        } else {
            this.e = builder.e;
        }
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
        this.k = builder.k;
        this.f = builder.f;
        this.g = builder.g;
    }

    @NonNull
    public Executor getExecutor() {
        return this.a;
    }

    @NonNull
    public Executor getTaskExecutor() {
        return this.b;
    }

    @NonNull
    public WorkerFactory getWorkerFactory() {
        return this.c;
    }

    @NonNull
    public InputMergerFactory getInputMergerFactory() {
        return this.d;
    }

    @NonNull
    public RunnableScheduler getRunnableScheduler() {
        return this.e;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getMinimumLoggingLevel() {
        return this.h;
    }

    public int getMinJobSchedulerId() {
        return this.i;
    }

    public int getMaxJobSchedulerId() {
        return this.j;
    }

    @Nullable
    public String getDefaultProcessName() {
        return this.g;
    }

    @IntRange(from = DefaultLivePlaybackSpeedControl.DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED, to = 50)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getMaxSchedulerLimit() {
        if (Build.VERSION.SDK_INT == 23) {
            return this.k / 2;
        }
        return this.k;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isUsingDefaultTaskExecutor() {
        return this.l;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public InitializationExceptionHandler getExceptionHandler() {
        return this.f;
    }

    @NonNull
    private Executor a() {
        return Executors.newFixedThreadPool(Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4)));
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        Executor a;
        WorkerFactory b;
        InputMergerFactory c;
        Executor d;
        RunnableScheduler e;
        @Nullable
        InitializationExceptionHandler f;
        @Nullable
        String g;
        int h;
        int i;
        int j;
        int k;

        public Builder() {
            this.h = 4;
            this.i = 0;
            this.j = Integer.MAX_VALUE;
            this.k = 20;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder(@NonNull Configuration configuration) {
            this.a = configuration.a;
            this.b = configuration.c;
            this.c = configuration.d;
            this.d = configuration.b;
            this.h = configuration.h;
            this.i = configuration.i;
            this.j = configuration.j;
            this.k = configuration.k;
            this.e = configuration.e;
            this.f = configuration.f;
            this.g = configuration.g;
        }

        @NonNull
        public Builder setWorkerFactory(@NonNull WorkerFactory workerFactory) {
            this.b = workerFactory;
            return this;
        }

        @NonNull
        public Builder setInputMergerFactory(@NonNull InputMergerFactory inputMergerFactory) {
            this.c = inputMergerFactory;
            return this;
        }

        @NonNull
        public Builder setExecutor(@NonNull Executor executor) {
            this.a = executor;
            return this;
        }

        @NonNull
        public Builder setTaskExecutor(@NonNull Executor executor) {
            this.d = executor;
            return this;
        }

        @NonNull
        public Builder setJobSchedulerJobIdRange(int i, int i2) {
            if (i2 - i >= 1000) {
                this.i = i;
                this.j = i2;
                return this;
            }
            throw new IllegalArgumentException("WorkManager needs a range of at least 1000 job ids.");
        }

        @NonNull
        public Builder setMaxSchedulerLimit(int i) {
            if (i >= 20) {
                this.k = Math.min(i, 50);
                return this;
            }
            throw new IllegalArgumentException("WorkManager needs to be able to schedule at least 20 jobs in JobScheduler.");
        }

        @NonNull
        public Builder setMinimumLoggingLevel(int i) {
            this.h = i;
            return this;
        }

        @NonNull
        public Builder setRunnableScheduler(@NonNull RunnableScheduler runnableScheduler) {
            this.e = runnableScheduler;
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setInitializationExceptionHandler(@NonNull InitializationExceptionHandler initializationExceptionHandler) {
            this.f = initializationExceptionHandler;
            return this;
        }

        @NonNull
        public Builder setDefaultProcessName(@NonNull String str) {
            this.g = str;
            return this;
        }

        @NonNull
        public Configuration build() {
            return new Configuration(this);
        }
    }
}
