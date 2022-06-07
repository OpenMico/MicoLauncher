package androidx.work;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.WorkRequest;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class PeriodicWorkRequest extends WorkRequest {
    @SuppressLint({"MinMaxConstant"})
    public static final long MIN_PERIODIC_FLEX_MILLIS = 300000;
    @SuppressLint({"MinMaxConstant"})
    public static final long MIN_PERIODIC_INTERVAL_MILLIS = 900000;

    PeriodicWorkRequest(Builder builder) {
        super(builder.b, builder.c, builder.d);
    }

    /* loaded from: classes.dex */
    public static final class Builder extends WorkRequest.Builder<Builder, PeriodicWorkRequest> {
        @NonNull
        /* renamed from: b */
        public Builder c() {
            return this;
        }

        public Builder(@NonNull Class<? extends ListenableWorker> cls, long j, @NonNull TimeUnit timeUnit) {
            super(cls);
            this.c.setPeriodic(timeUnit.toMillis(j));
        }

        @RequiresApi(26)
        public Builder(@NonNull Class<? extends ListenableWorker> cls, @NonNull Duration duration) {
            super(cls);
            this.c.setPeriodic(duration.toMillis());
        }

        public Builder(@NonNull Class<? extends ListenableWorker> cls, long j, @NonNull TimeUnit timeUnit, long j2, @NonNull TimeUnit timeUnit2) {
            super(cls);
            this.c.setPeriodic(timeUnit.toMillis(j), timeUnit2.toMillis(j2));
        }

        @RequiresApi(26)
        public Builder(@NonNull Class<? extends ListenableWorker> cls, @NonNull Duration duration, @NonNull Duration duration2) {
            super(cls);
            this.c.setPeriodic(duration.toMillis(), duration2.toMillis());
        }

        @NonNull
        /* renamed from: a */
        public PeriodicWorkRequest d() {
            if (this.a && Build.VERSION.SDK_INT >= 23 && this.c.constraints.requiresDeviceIdle()) {
                throw new IllegalArgumentException("Cannot set backoff criteria on an idle mode job");
            } else if (!this.c.runInForeground || Build.VERSION.SDK_INT < 23 || !this.c.constraints.requiresDeviceIdle()) {
                return new PeriodicWorkRequest(this);
            } else {
                throw new IllegalArgumentException("Cannot run in foreground with an idle mode constraint");
            }
        }
    }
}
