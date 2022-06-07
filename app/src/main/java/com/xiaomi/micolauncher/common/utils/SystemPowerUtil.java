package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;

/* loaded from: classes3.dex */
public class SystemPowerUtil {
    private static Runnable a = $$Lambda$SystemPowerUtil$jCoW6N59kdnlVQdXn233Zj8aj5w.INSTANCE;
    private static Runnable b = $$Lambda$SystemPowerUtil$SGKrcP2pRYIR9Gl8YlN8DaC5Q.INSTANCE;

    private static Handler a() {
        return Threads.getLightWorkHandler();
    }

    public static void rebootImp(final String str) {
        AudioPolicyService.getInstance().requestForceStopAll(AudioSource.AUDIO_SOURCE_SHUT_DOWN);
        L.base.i("!!! do Reboot !!!, reason %s", str);
        DebugHelper.printStackTrace("rebootStackTrace");
        a().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$SystemPowerUtil$jSXV9ie3kbv12W9yfNXsIdzXNqA
            @Override // java.lang.Runnable
            public final void run() {
                SystemPowerUtil.a(str);
            }
        }, 1000L);
    }

    public static /* synthetic */ void a(String str) {
        rebootDevice(MicoApplication.getGlobalContext(), str);
    }

    public static void rebootDevice(Context context, String str) {
        ((PowerManager) context.getSystemService("power")).reboot(str);
    }

    public static void shutDownDevice(Context context, String str) {
        ((PowerManager) context.getSystemService("power")).shutdown(false, str, true);
    }

    public static void rebootDelay(long j) {
        a().removeCallbacks(a);
        a().postDelayed(a, j);
    }

    public static void shutdownOrScreenOff(long j) {
        a().removeCallbacks(b);
        AudioPolicyService.getInstance().playbackController(PlaybackControl.PAUSE);
        a().postDelayed(b, j);
    }

    public static /* synthetic */ void c() {
        rebootDevice(MicoApplication.getGlobalContext(), "deviceowner");
    }

    public static /* synthetic */ void b() {
        if (Hardware.isX08E()) {
            shutDownDevice(MicoApplication.getGlobalContext(), "userrequested");
        } else {
            ScreenUtil.turnScreenOff(MicoApplication.getGlobalContext());
        }
    }
}
