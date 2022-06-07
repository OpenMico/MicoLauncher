package com.xiaomi.micolauncher.common.speech.utils;

import android.os.Handler;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class NetWorkDetect {
    private static int d;
    private static final int a = (int) TimeUnit.SECONDS.toMillis(6);
    private static final int b = (int) TimeUnit.SECONDS.toMillis(6);
    private static final long c = TimeUnit.SECONDS.toMillis(6);
    private static Handler e = new Handler();
    private static Runnable f = new AnonymousClass1();

    /* renamed from: com.xiaomi.micolauncher.common.speech.utils.NetWorkDetect$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ThreadUtil.getIoThreadPool().submit($$Lambda$NetWorkDetect$1$1oLwFtlD5ZIPn00XwhOxzl9I_WY.INSTANCE);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a() {
            JSONObject jSONObject = new JSONObject();
            try {
                NetWorkDetect.b(jSONObject, "http://www.baidu.com", null);
                L.speech.e("[NetWorkDetect]: mPostRunnable.Thread.run.json=");
                L.speech.json(jSONObject.toString());
                if (jSONObject.getInt("detect_status") != 0) {
                    String string = jSONObject.getString("detect_msg");
                    StatPoints.Event event = StatPoints.Event.micolog_speech;
                    StatPoints.SpeechKey speechKey = StatPoints.SpeechKey.app_network_detect;
                    StatPoints.recordPoint(event, speechKey, NetWorkDetect.d + "_" + string);
                }
            } catch (JSONException e) {
                Logger logger = L.speech;
                logger.e("[NetWorkDetect]: mPostRunnable.Thread.run.exception=" + e);
            }
        }
    }

    private static String a(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x012f, code lost:
        if (r1 == null) goto L_0x0134;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(org.json.JSONObject r7, java.lang.String r8, java.util.Map<java.lang.String, java.lang.String> r9) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.speech.utils.NetWorkDetect.b(org.json.JSONObject, java.lang.String, java.util.Map):java.lang.String");
    }

    public static void checkNetWork(int i) {
        e.removeCallbacks(f);
        d = i;
        e.postDelayed(f, c);
    }
}
