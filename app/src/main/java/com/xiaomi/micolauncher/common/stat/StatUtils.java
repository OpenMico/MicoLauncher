package com.xiaomi.micolauncher.common.stat;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class StatUtils {
    private static final String LOG_TAG = "[StatUtils]: ";
    public static final boolean USE_STAT3 = false;
    private static String mActiveActivity = "";
    private static ActivityManager mActivityManager;

    /* loaded from: classes3.dex */
    public interface StatPointsPostListener {
        void onFail(String str);

        void onSuccess();
    }

    public static void init(Context context, String str, String str2, String str3) {
        try {
            Analytics.initialize(context, str3);
        } catch (Exception e) {
            L.base.e("[StatUtils]: stat setup", e);
        }
    }

    private static void recordCountEventWithParams(String str, String str2, Map<String, String> map) {
        try {
            if (map != null) {
                final HashMap hashMap = new HashMap(map.size());
                hashMap.getClass();
                map.forEach(new BiConsumer() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$SkjkXlkSkC3kbhwCM-YaDDUVx9U
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        hashMap.put((String) obj, (String) obj2);
                    }
                });
                Analytics.track(str2, hashMap);
            } else {
                Analytics.track(str2);
            }
        } catch (ExceptionInInitializerError | IllegalStateException unused) {
        }
    }

    public static void recordCountEvent(StatKey statKey, String... strArr) {
        if (strArr != null && strArr.length % 2 == 0) {
            HashMap hashMap = new HashMap();
            for (int i = 0; i < strArr.length; i += 2) {
                hashMap.put(strArr[i], strArr[i + 1]);
            }
            recordCountEventWithParams(statKey.getCategory(), statKey.getKey(), hashMap);
        }
    }

    public static void recordCountEvent(String str, String str2) {
        L.statistics.d("record count event %s, %s", str, str2);
        recordCountEventWithParams(str, str2, null);
    }

    public static void recordCountEvent(String str, String str2, String... strArr) {
        if (strArr.length % 2 == 0) {
            HashMap hashMap = new HashMap();
            for (int i = 0; i < strArr.length; i += 2) {
                hashMap.put(strArr[i], strArr[i + 1]);
            }
            recordCountEventWithParams(str, str2, hashMap);
            return;
        }
        L.statistics.e("args length is odd %s", Integer.valueOf(strArr.length));
    }

    public static void recordCountEvent(String str, String str2, Map<String, String> map) {
        recordCountEventWithParams(str, str2, map);
    }

    public static boolean getHalfWakeupViewFlag(Context context) {
        String str = "";
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
        }
        ActivityManager activityManager = mActivityManager;
        if (activityManager != null) {
            str = activityManager.getRunningTasks(1).get(0).topActivity.getShortClassName();
        }
        Logger logger = L.statistics;
        logger.d("[StatUtils]: mActiveActivity=" + mActiveActivity + ", activity=" + str);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.contains("VideoDetailActivity") || str.contains("VideoPlayerActivity") || str.contains("VideoRecommendationActivity") || str.contains("org.qiyi.speaker.activity") || str.contains("AlarmListActivity") || str.contains("OpenPlatformChatListActivity") || str.contains("miui10.MIUI10") || str.contains("MiotMultipleDevicesSelectActivity") || str.contains("MeshScanActivity") || str.contains("MeshDevicesShowActivity") || str.contains(".camera.page.CameraP2pActivity") || str.contains("CallingActivityV2") || str.contains("CallingActivity") || str.contains("VoicePrintRegisterActivity") || str.contains("VoiceReceiverActivity") || str.contains("VideoPatchWallActivity") || str.contains("VideoGroupListActivity") || str.contains("main.MainActivity") || str.contains("livingroom.LivingRoomActivity") || str.contains("VideoChatViewActivity") || str.contains("QueryResultActivity");
    }
}
