package com.xiaomi.micolauncher.module.video.manager;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.module.video.ui.VideoThirdPartyJumpActivity;

/* loaded from: classes3.dex */
public class VideoThirdPartyJumpManager {
    public static boolean canOpenThirdPartyOrJumpActivity(Context context) {
        if (PreferenceUtils.getSettingInt(context, "pref_key_third_party_times", 0) >= 3) {
            return true;
        }
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context, VideoThirdPartyJumpActivity.class));
        return false;
    }

    public static void recordOpenThirdPartyActivity(Context context) {
        PreferenceUtils.setSettingInt(context, "pref_key_third_party_times", PreferenceUtils.getSettingInt(context, "pref_key_third_party_times", 0) + 1);
    }
}
