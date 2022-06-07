package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;

/* loaded from: classes3.dex */
public class ResettingUtil {
    public static void doMasterClear(Context context, boolean z) {
        if (z) {
            PromptSoundPlayer.getInstance().play(context, R.raw.reset);
        }
        doMasterClear();
    }

    public static void doMasterClear() {
        b().post($$Lambda$ResettingUtil$eLoAsisB6TeErMv0RJtcgU7nTzk.INSTANCE);
    }

    public static /* synthetic */ void d() {
        if (isAutomatorRun()) {
            L.base.i("won't do master clear, for debug file exists");
        } else {
            a();
        }
    }

    public static boolean isAutomatorRun() {
        return DebugHelper.isAutomatorRun();
    }

    private static void a() {
        ThreadUtil.postInMainThread($$Lambda$ResettingUtil$mcTYvDxxGRvEAjIVDaYoCJ_fCNY.INSTANCE);
    }

    public static /* synthetic */ void c() {
        AudioPolicyService.getInstance().requestForceStopAll(AudioSource.AUDIO_SOURCE_FACTORY_RESET);
        Intent intent = new Intent("android.intent.action.FACTORY_RESET");
        intent.setPackage("android");
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.REASON", "MasterClearConfirm");
        MicoApplication.getGlobalContext().sendBroadcast(intent);
        L.base.d("ACTION_FACTORY_RESET");
    }

    public static void rebootSystem(final String str) {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$ResettingUtil$80vF-ZNLGWx9rKD9HydlTZiNUL0
            @Override // java.lang.Runnable
            public final void run() {
                ResettingUtil.a(str);
            }
        });
    }

    public static /* synthetic */ void a(String str) {
        if (isAutomatorRun()) {
            L.debug.w("reboot cancelled for debug file existed, request reboot reason %s", str);
        } else {
            SystemPowerUtil.rebootImp(str);
        }
    }

    private static Handler b() {
        return Threads.getLightWorkHandler();
    }
}
