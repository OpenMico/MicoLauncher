package com.jakewharton.processphoenix;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import com.xiaomi.idm.api.IDMServer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public final class ProcessPhoenix extends Activity {
    public static void triggerRebirth(Context context) {
        triggerRebirth(context, a(context));
    }

    public static void triggerRebirth(Context context, Intent... intentArr) {
        Intent intent = new Intent(context, ProcessPhoenix.class);
        intent.addFlags(268435456);
        intent.putParcelableArrayListExtra("phoenix_restart_intents", new ArrayList<>(Arrays.asList(intentArr)));
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
        Runtime.getRuntime().exit(0);
    }

    private static Intent a(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addFlags(268468224);
        intent.addCategory("android.intent.category.DEFAULT");
        String packageName = context.getPackageName();
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 0)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo.packageName.equals(packageName)) {
                intent.setComponent(new ComponentName(packageName, activityInfo.name));
                return intent;
            }
        }
        throw new IllegalStateException("Unable to determine default activity for " + packageName + ". Does an activity specify the DEFAULT category in its intent filter?");
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ArrayList parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("phoenix_restart_intents");
        startActivities((Intent[]) parcelableArrayListExtra.toArray(new Intent[parcelableArrayListExtra.size()]));
        finish();
        Runtime.getRuntime().exit(0);
    }

    public static boolean isPhoenixProcess(Context context) {
        int myPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == myPid && runningAppProcessInfo.processName.endsWith(":phoenix")) {
                return true;
            }
        }
        return false;
    }
}
