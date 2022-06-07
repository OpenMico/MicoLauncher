package androidx.work;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.WorkInfo;
import androidx.work.impl.model.WorkSpec;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public abstract class WorkRequest {
    public static final long DEFAULT_BACKOFF_DELAY_MILLIS = 30000;
    @SuppressLint({"MinMaxConstant"})
    public static final long MAX_BACKOFF_MILLIS = 18000000;
    @SuppressLint({"MinMaxConstant"})
    public static final long MIN_BACKOFF_MILLIS = 10000;
    @NonNull
    private UUID a;
    @NonNull
    private WorkSpec b;
    @NonNull
    private Set<String> c;

    /* JADX INFO: Access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkRequest(@NonNull UUID uuid, @NonNull WorkSpec workSpec, @NonNull Set<String> set) {
        this.a = uuid;
        this.b = workSpec;
        this.c = set;
    }

    @NonNull
    public UUID getId() {
        return this.a;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public String getStringId() {
        return this.a.toString();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkSpec getWorkSpec() {
        return this.b;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Set<String> getTags() {
        return this.c;
    }

    /* loaded from: classes.dex */
    public static abstract class Builder<B extends Builder<?, ?>, W extends WorkRequest> {
        WorkSpec c;
        Class<? extends ListenableWorker> e;
        boolean a = false;
        Set<String> d = new HashSet();
        UUID b = UUID.randomUUID();

        @NonNull
        abstract B c();

        @NonNull
        abstract W d();

        /* JADX INFO: Access modifiers changed from: package-private */
        public Builder(@NonNull Class<? extends ListenableWorker> cls) {
            this.e = cls;
            this.c = new WorkSpec(this.b.toString(), cls.getName());
            addTag(cls.getName());
        }

        @NonNull
        public final B setBackoffCriteria(@NonNull BackoffPolicy backoffPolicy, long j, @NonNull TimeUnit timeUnit) {
            this.a = true;
            WorkSpec workSpec = this.c;
            workSpec.backoffPolicy = backoffPolicy;
            workSpec.setBackoffDelayDuration(timeUnit.toMillis(j));
            return c();
        }

        @NonNull
        @RequiresApi(26)
        public final B setBackoffCriteria(@NonNull BackoffPolicy backoffPolicy, @NonNull Duration duration) {
            this.a = true;
            WorkSpec workSpec = this.c;
            workSpec.backoffPolicy = backoffPolicy;
            workSpec.setBackoffDelayDuration(duration.toMillis());
            return c();
        }

        @NonNull
        public final B setConstraints(@NonNull Constraints constraints) {
            this.c.constraints = constraints;
            return c();
        }

        @NonNull
        public final B setInputData(@NonNull Data data) {
            this.c.input = data;
            return c();
        }

        @NonNull
        public final B addTag(@NonNull String str) {
            this.d.add(str);
            return c();
        }

        @NonNull
        public final B keepResultsForAtLeast(long j, @NonNull TimeUnit timeUnit) {
            this.c.minimumRetentionDuration = timeUnit.toMillis(j);
            return c();
        }

        @NonNull
        @RequiresApi(26)
        public final B keepResultsForAtLeast(@NonNull Duration duration) {
            this.c.minimumRetentionDuration = duration.toMillis();
            return c();
        }

        @NonNull
        public B setInitialDelay(long j, @NonNull TimeUnit timeUnit) {
            this.c.initialDelay = timeUnit.toMillis(j);
            if (Long.MAX_VALUE - System.currentTimeMillis() > this.c.initialDelay) {
                return c();
            }
            throw new IllegalArgumentException("The given initial delay is too large and will cause an overflow!");
        }

        @NonNull
        @RequiresApi(26)
        public B setInitialDelay(@NonNull Duration duration) {
            this.c.initialDelay = duration.toMillis();
            if (Long.MAX_VALUE - System.currentTimeMillis() > this.c.initialDelay) {
                return c();
            }
            throw new IllegalArgumentException("The given initial delay is too large and will cause an overflow!");
        }

        @NonNull
        public final W build() {
            W d = d();
            this.b = UUID.randomUUID();
            this.c = new WorkSpec(this.c);
            this.c.id = this.b.toString();
            return d;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @VisibleForTesting
        public final B setInitialState(@NonNull WorkInfo.State state) {
            this.c.state = state;
            return c();
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @VisibleForTesting
        public final B setInitialRunAttemptCount(int i) {
            this.c.runAttemptCount = i;
            return c();
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @VisibleForTesting
        public final B setPeriodStartTime(long j, @NonNull TimeUnit timeUnit) {
            this.c.periodStartTime = timeUnit.toMillis(j);
            return c();
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @VisibleForTesting
        public final B setScheduleRequestedAt(long j, @NonNull TimeUnit timeUnit) {
            this.c.scheduleRequestedAt = timeUnit.toMillis(j);
            return c();
        }
    }
}
