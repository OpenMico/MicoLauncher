package com.xiaomi.mico.base.utils;

import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.UtilsConfig;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.GKidAppCommandProcessor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class ActivityUtil {
    public static final String PACKAGE_NAME_QIYI = "com.qiyi.video.speaker";
    public static final String TAG = "[MICO.ActivityUtil] ";
    static final /* synthetic */ boolean a = !ActivityUtil.class.desiredAssertionStatus();
    private static final String[] b = {"com.youku.iot", "tv.danmaku.bili", GKidAppCommandProcessor.PACKAGE_NAME_GKID_BIGSCREEN, "com.ss.android.ugc.aweme", "com.duowan.yytv"};
    private static List<PackageInfo> c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface a {
        void onProcess(ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo);
    }

    public static boolean isImportanceOfAppGone(int i) {
        return i == 1000;
    }

    public static boolean isImportanceOfForegroundOrVisible(int i) {
        return i == 100 || i == 200;
    }

    public static synchronized List<PackageInfo> getInstalledApps(@NotNull Context context) {
        List<PackageInfo> list;
        synchronized (ActivityUtil.class) {
            if (c == null) {
                c = context.getPackageManager().getInstalledPackages(0);
            }
            list = c;
        }
        return list;
    }

    public static boolean isLauncherInForeground(Context context) {
        return isPackageInForeground(context, "com.xiaomi.micolauncher");
    }

    @Deprecated
    public static boolean isPackageInForeground(Context context, String str) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
        if (a || activityManager != null) {
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (!runningTasks.isEmpty()) {
                return runningTasks.get(0).topActivity.getPackageName().equals(str);
            }
            return false;
        }
        throw new AssertionError();
    }

    public static String getForegroundPackage(Context context) {
        UsageEvents queryEvents = ((UsageStatsManager) context.getSystemService("usagestats")).queryEvents(System.currentTimeMillis() - 86400000, System.currentTimeMillis());
        UsageEvents.Event event = new UsageEvents.Event();
        String str = null;
        while (queryEvents.hasNextEvent()) {
            queryEvents.getNextEvent(event);
            if (event.getEventType() == 1) {
                str = event.getPackageName();
            }
        }
        return str;
    }

    public static List<String> getRecentUsedApps(@NotNull Context context) {
        UsageEvents queryEvents = ((UsageStatsManager) context.getSystemService("usagestats")).queryEvents(System.currentTimeMillis() - 86400000, System.currentTimeMillis());
        UsageEvents.Event event = new UsageEvents.Event();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList arrayList = new ArrayList();
        while (queryEvents.hasNextEvent()) {
            queryEvents.getNextEvent(event);
            if (event.getEventType() == 1) {
                if (linkedHashSet.contains(event.getPackageName())) {
                    linkedHashSet.remove(event.getPackageName());
                }
                linkedHashSet.add(event.getPackageName());
            }
        }
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            arrayList.add((String) it.next());
        }
        return arrayList;
    }

    public static void forceStopThirdPartyTask(final Context context) {
        a(context, new a() { // from class: com.xiaomi.mico.base.utils.-$$Lambda$ActivityUtil$ZVpb1GMjwpYCk5uAazSfeB7C_Fw
            @Override // com.xiaomi.mico.base.utils.ActivityUtil.a
            public final void onProcess(ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
                ActivityUtil.a(context, activityManager, runningAppProcessInfo);
            }
        }, new a() { // from class: com.xiaomi.mico.base.utils.-$$Lambda$ActivityUtil$sX4KxGLNCwXWi0VU-UwBr6bNRbo
            @Override // com.xiaomi.mico.base.utils.ActivityUtil.a
            public final void onProcess(ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
                ActivityUtil.c(context, activityManager, runningAppProcessInfo);
            }
        }, $$Lambda$ActivityUtil$_dIbewu4Dlv0vvJhLYBzK6K0YEk.INSTANCE);
        a(context, $$Lambda$ActivityUtil$pUDhIrpFohPRMTwHKNAzUWVYtyc.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Context context, ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        String str = runningAppProcessInfo.processName;
        if (str != null && str.contains("com.xiaomi.smarthome")) {
            Intent intent = new Intent("com.xiaomi.mico.action.smartmiot.camera.close");
            intent.setPackage("com.xiaomi.smarthome");
            context.sendBroadcast(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        String str = runningAppProcessInfo.processName;
        if (str != null) {
            if (isImportanceOfForegroundOrVisible(runningAppProcessInfo.importance)) {
                UtilsConfig.getLogCallback().i("%s don't kill foreground or visible process.", TAG);
                return;
            }
            String[] strArr = b;
            for (String str2 : strArr) {
                if (str.contains(str2)) {
                    a(activityManager, runningAppProcessInfo, str2);
                }
            }
        }
    }

    public static void forceStopPackage(Context context, final String str) {
        if (str != null) {
            a(context, new a() { // from class: com.xiaomi.mico.base.utils.-$$Lambda$ActivityUtil$8K7y3DiBmMJiu-W5akG2lPh78iA
                @Override // com.xiaomi.mico.base.utils.ActivityUtil.a
                public final void onProcess(ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
                    ActivityUtil.a(str, activityManager, runningAppProcessInfo);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        if (runningAppProcessInfo.processName != null) {
            a(activityManager, runningAppProcessInfo, str);
        }
    }

    private static void a(ActivityManager activityManager, String str) {
        activityManager.forceStopPackage(str);
        UtilsConfig.getLogCallback().i("%s : force stop package %s", TAG, str);
    }

    private static void a(Context context, a... aVarArr) {
        for (a aVar : aVarArr) {
            a(context, aVar);
        }
    }

    public static void stopQiyiVideo(final Context context) {
        a(context, new a() { // from class: com.xiaomi.mico.base.utils.-$$Lambda$ActivityUtil$-I5l4-ftZvXE3AEUUsPJkzXNU78
            @Override // com.xiaomi.mico.base.utils.ActivityUtil.a
            public final void onProcess(ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
                ActivityUtil.a(context, activityManager, runningAppProcessInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Context context, ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        if ("com.qiyi.video.speaker".equals(runningAppProcessInfo.processName)) {
            UtilsConfig.getLogCallback().i("%s doStopQiyi", TAG);
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/app?command=exit_app")));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        a(activityManager, runningAppProcessInfo, "com.android.gallery3d");
    }

    private static void a(ActivityManager activityManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, String str) {
        String str2 = runningAppProcessInfo.processName;
        if (str2 != null && str2.contains(str)) {
            String[] strArr = runningAppProcessInfo.pkgList;
            if (!ContainerUtil.isEmpty(strArr) && strArr.length > 0) {
                a(activityManager, strArr[0]);
            }
        }
    }

    private static void a(Context context, a aVar) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
        if (a || activityManager != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                aVar.onProcess(activityManager, runningAppProcessInfo);
            }
            return;
        }
        throw new AssertionError();
    }
}
