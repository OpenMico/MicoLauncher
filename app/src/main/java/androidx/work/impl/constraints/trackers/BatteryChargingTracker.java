package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class BatteryChargingTracker extends BroadcastReceiverConstraintTracker<Boolean> {
    private static final String b = Logger.tagWithPrefix("BatteryChrgTracker");

    public BatteryChargingTracker(@NonNull Context context, @NonNull TaskExecutor taskExecutor) {
        super(context, taskExecutor);
    }

    @Override // androidx.work.impl.constraints.trackers.ConstraintTracker
    public Boolean getInitialState() {
        Intent registerReceiver = this.mAppContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver != null) {
            return Boolean.valueOf(a(registerReceiver));
        }
        Logger.get().error(b, "getInitialState - null intent received", new Throwable[0]);
        return null;
    }

    @Override // androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        if (Build.VERSION.SDK_INT >= 23) {
            intentFilter.addAction("android.os.action.CHARGING");
            intentFilter.addAction("android.os.action.DISCHARGING");
        } else {
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        }
        return intentFilter;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    @Override // androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBroadcastReceive(android.content.Context r6, @androidx.annotation.NonNull android.content.Intent r7) {
        /*
            r5 = this;
            java.lang.String r6 = r7.getAction()
            if (r6 != 0) goto L_0x0007
            return
        L_0x0007:
            androidx.work.Logger r7 = androidx.work.Logger.get()
            java.lang.String r0 = androidx.work.impl.constraints.trackers.BatteryChargingTracker.b
            java.lang.String r1 = "Received %s"
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r4 = 0
            r3[r4] = r6
            java.lang.String r1 = java.lang.String.format(r1, r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r4]
            r7.debug(r0, r1, r3)
            r7 = -1
            int r0 = r6.hashCode()
            r1 = -1886648615(0xffffffff8f8c06d9, float:-1.3807703E-29)
            if (r0 == r1) goto L_0x0056
            r1 = -54942926(0xfffffffffcb9a332, float:-7.711079E36)
            if (r0 == r1) goto L_0x004c
            r1 = 948344062(0x388694fe, float:6.41737E-5)
            if (r0 == r1) goto L_0x0042
            r1 = 1019184907(0x3cbf870b, float:0.023379823)
            if (r0 == r1) goto L_0x0038
            goto L_0x0060
        L_0x0038:
            java.lang.String r0 = "android.intent.action.ACTION_POWER_CONNECTED"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0060
            r6 = 2
            goto L_0x0061
        L_0x0042:
            java.lang.String r0 = "android.os.action.CHARGING"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0060
            r6 = r4
            goto L_0x0061
        L_0x004c:
            java.lang.String r0 = "android.os.action.DISCHARGING"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0060
            r6 = r2
            goto L_0x0061
        L_0x0056:
            java.lang.String r0 = "android.intent.action.ACTION_POWER_DISCONNECTED"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0060
            r6 = 3
            goto L_0x0061
        L_0x0060:
            r6 = r7
        L_0x0061:
            switch(r6) {
                case 0: goto L_0x007d;
                case 1: goto L_0x0075;
                case 2: goto L_0x006d;
                case 3: goto L_0x0065;
                default: goto L_0x0064;
            }
        L_0x0064:
            goto L_0x0084
        L_0x0065:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)
            r5.setState(r6)
            goto L_0x0084
        L_0x006d:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r2)
            r5.setState(r6)
            goto L_0x0084
        L_0x0075:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)
            r5.setState(r6)
            goto L_0x0084
        L_0x007d:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r2)
            r5.setState(r6)
        L_0x0084:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.constraints.trackers.BatteryChargingTracker.onBroadcastReceive(android.content.Context, android.content.Intent):void");
    }

    private boolean a(Intent intent) {
        if (Build.VERSION.SDK_INT < 23) {
            return intent.getIntExtra("plugged", 0) != 0;
        }
        int intExtra = intent.getIntExtra("status", -1);
        return intExtra == 2 || intExtra == 5;
    }
}
