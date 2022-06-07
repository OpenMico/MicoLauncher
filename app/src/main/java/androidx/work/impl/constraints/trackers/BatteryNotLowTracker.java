package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.xiaomi.onetrack.a.a;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class BatteryNotLowTracker extends BroadcastReceiverConstraintTracker<Boolean> {
    private static final String b = Logger.tagWithPrefix("BatteryNotLowTracker");

    public BatteryNotLowTracker(@NonNull Context context, @NonNull TaskExecutor taskExecutor) {
        super(context, taskExecutor);
    }

    @Override // androidx.work.impl.constraints.trackers.ConstraintTracker
    public Boolean getInitialState() {
        Intent registerReceiver = this.mAppContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver == null) {
            Logger.get().error(b, "getInitialState - null intent received", new Throwable[0]);
            return null;
        }
        int intExtra = registerReceiver.getIntExtra("status", -1);
        float intExtra2 = registerReceiver.getIntExtra(a.d, -1) / registerReceiver.getIntExtra("scale", -1);
        boolean z = true;
        if (intExtra != 1 && intExtra2 <= 0.15f) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    @Override // androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_OKAY");
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        return intentFilter;
    }

    @Override // androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
    public void onBroadcastReceive(Context context, @NonNull Intent intent) {
        if (intent.getAction() != null) {
            Logger.get().debug(b, String.format("Received %s", intent.getAction()), new Throwable[0]);
            String action = intent.getAction();
            char c = 65535;
            int hashCode = action.hashCode();
            if (hashCode != -1980154005) {
                if (hashCode == 490310653 && action.equals("android.intent.action.BATTERY_LOW")) {
                    c = 1;
                }
            } else if (action.equals("android.intent.action.BATTERY_OKAY")) {
                c = 0;
            }
            switch (c) {
                case 0:
                    setState(true);
                    return;
                case 1:
                    setState(false);
                    return;
                default:
                    return;
            }
        }
    }
}
