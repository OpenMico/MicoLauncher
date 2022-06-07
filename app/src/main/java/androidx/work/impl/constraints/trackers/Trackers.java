package androidx.work.impl.constraints.trackers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class Trackers {
    private static Trackers a;
    private BatteryChargingTracker b;
    private BatteryNotLowTracker c;
    private NetworkStateTracker d;
    private StorageNotLowTracker e;

    @NonNull
    public static synchronized Trackers getInstance(Context context, TaskExecutor taskExecutor) {
        Trackers trackers;
        synchronized (Trackers.class) {
            if (a == null) {
                a = new Trackers(context, taskExecutor);
            }
            trackers = a;
        }
        return trackers;
    }

    @VisibleForTesting
    public static synchronized void setInstance(@NonNull Trackers trackers) {
        synchronized (Trackers.class) {
            a = trackers;
        }
    }

    private Trackers(@NonNull Context context, @NonNull TaskExecutor taskExecutor) {
        Context applicationContext = context.getApplicationContext();
        this.b = new BatteryChargingTracker(applicationContext, taskExecutor);
        this.c = new BatteryNotLowTracker(applicationContext, taskExecutor);
        this.d = new NetworkStateTracker(applicationContext, taskExecutor);
        this.e = new StorageNotLowTracker(applicationContext, taskExecutor);
    }

    @NonNull
    public BatteryChargingTracker getBatteryChargingTracker() {
        return this.b;
    }

    @NonNull
    public BatteryNotLowTracker getBatteryNotLowTracker() {
        return this.c;
    }

    @NonNull
    public NetworkStateTracker getNetworkStateTracker() {
        return this.d;
    }

    @NonNull
    public StorageNotLowTracker getStorageNotLowTracker() {
        return this.e;
    }
}
