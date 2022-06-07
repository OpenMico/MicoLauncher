package androidx.work;

import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.room.ColumnInfo;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class Constraints {
    public static final Constraints NONE = new Builder().build();
    @ColumnInfo(name = "required_network_type")
    private NetworkType a;
    @ColumnInfo(name = "requires_charging")
    private boolean b;
    @ColumnInfo(name = "requires_device_idle")
    private boolean c;
    @ColumnInfo(name = "requires_battery_not_low")
    private boolean d;
    @ColumnInfo(name = "requires_storage_not_low")
    private boolean e;
    @ColumnInfo(name = "trigger_content_update_delay")
    private long f;
    @ColumnInfo(name = "trigger_max_content_delay")
    private long g;
    @ColumnInfo(name = "content_uri_triggers")
    private ContentUriTriggers h;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Constraints() {
        this.a = NetworkType.NOT_REQUIRED;
        this.f = -1L;
        this.g = -1L;
        this.h = new ContentUriTriggers();
    }

    Constraints(Builder builder) {
        this.a = NetworkType.NOT_REQUIRED;
        this.f = -1L;
        this.g = -1L;
        this.h = new ContentUriTriggers();
        this.b = builder.a;
        this.c = Build.VERSION.SDK_INT >= 23 && builder.b;
        this.a = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        if (Build.VERSION.SDK_INT >= 24) {
            this.h = builder.h;
            this.f = builder.f;
            this.g = builder.g;
        }
    }

    public Constraints(@NonNull Constraints constraints) {
        this.a = NetworkType.NOT_REQUIRED;
        this.f = -1L;
        this.g = -1L;
        this.h = new ContentUriTriggers();
        this.b = constraints.b;
        this.c = constraints.c;
        this.a = constraints.a;
        this.d = constraints.d;
        this.e = constraints.e;
        this.h = constraints.h;
    }

    @NonNull
    public NetworkType getRequiredNetworkType() {
        return this.a;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiredNetworkType(@NonNull NetworkType networkType) {
        this.a = networkType;
    }

    public boolean requiresCharging() {
        return this.b;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiresCharging(boolean z) {
        this.b = z;
    }

    @RequiresApi(23)
    public boolean requiresDeviceIdle() {
        return this.c;
    }

    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiresDeviceIdle(boolean z) {
        this.c = z;
    }

    public boolean requiresBatteryNotLow() {
        return this.d;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiresBatteryNotLow(boolean z) {
        this.d = z;
    }

    public boolean requiresStorageNotLow() {
        return this.e;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiresStorageNotLow(boolean z) {
        this.e = z;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public long getTriggerContentUpdateDelay() {
        return this.f;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setTriggerContentUpdateDelay(long j) {
        this.f = j;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public long getTriggerMaxContentDelay() {
        return this.g;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setTriggerMaxContentDelay(long j) {
        this.g = j;
    }

    @RequiresApi(24)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setContentUriTriggers(@Nullable ContentUriTriggers contentUriTriggers) {
        this.h = contentUriTriggers;
    }

    @NonNull
    @RequiresApi(24)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ContentUriTriggers getContentUriTriggers() {
        return this.h;
    }

    @RequiresApi(24)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean hasContentUriTriggers() {
        return this.h.size() > 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Constraints constraints = (Constraints) obj;
        if (this.b == constraints.b && this.c == constraints.c && this.d == constraints.d && this.e == constraints.e && this.f == constraints.f && this.g == constraints.g && this.a == constraints.a) {
            return this.h.equals(constraints.h);
        }
        return false;
    }

    public int hashCode() {
        long j = this.f;
        long j2 = this.g;
        return (((((((((((((this.a.hashCode() * 31) + (this.b ? 1 : 0)) * 31) + (this.c ? 1 : 0)) * 31) + (this.d ? 1 : 0)) * 31) + (this.e ? 1 : 0)) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + this.h.hashCode();
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        boolean a = false;
        boolean b = false;
        NetworkType c = NetworkType.NOT_REQUIRED;
        boolean d = false;
        boolean e = false;
        long f = -1;
        long g = -1;
        ContentUriTriggers h = new ContentUriTriggers();

        @NonNull
        public Builder setRequiresCharging(boolean z) {
            this.a = z;
            return this;
        }

        @NonNull
        @RequiresApi(23)
        public Builder setRequiresDeviceIdle(boolean z) {
            this.b = z;
            return this;
        }

        @NonNull
        public Builder setRequiredNetworkType(@NonNull NetworkType networkType) {
            this.c = networkType;
            return this;
        }

        @NonNull
        public Builder setRequiresBatteryNotLow(boolean z) {
            this.d = z;
            return this;
        }

        @NonNull
        public Builder setRequiresStorageNotLow(boolean z) {
            this.e = z;
            return this;
        }

        @NonNull
        @RequiresApi(24)
        public Builder addContentUriTrigger(@NonNull Uri uri, boolean z) {
            this.h.add(uri, z);
            return this;
        }

        @NonNull
        @RequiresApi(24)
        public Builder setTriggerContentUpdateDelay(long j, @NonNull TimeUnit timeUnit) {
            this.f = timeUnit.toMillis(j);
            return this;
        }

        @NonNull
        @RequiresApi(26)
        public Builder setTriggerContentUpdateDelay(Duration duration) {
            this.f = duration.toMillis();
            return this;
        }

        @NonNull
        @RequiresApi(24)
        public Builder setTriggerContentMaxDelay(long j, @NonNull TimeUnit timeUnit) {
            this.g = timeUnit.toMillis(j);
            return this;
        }

        @NonNull
        @RequiresApi(26)
        public Builder setTriggerContentMaxDelay(Duration duration) {
            this.g = duration.toMillis();
            return this;
        }

        @NonNull
        public Constraints build() {
            return new Constraints(this);
        }
    }
}
