package com.xiaomi.micolauncher.module.multicp;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.MangguoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.player.VideoPlayerActivity;

/* loaded from: classes3.dex */
public class MultiCpUtils {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String findTargetViewId(String str) {
        char c;
        switch (str.hashCode()) {
            case -2008684377:
                if (str.equals(MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -426347972:
                if (str.equals("com.youku.iot")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -338862911:
                if (str.equals("com.tencent.qqlive.audiobox")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -264182514:
                if (str.equals("com.xiaomi.micolauncher")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 409393875:
                if (str.equals("com.qiyi.video.speaker")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1994036591:
                if (str.equals("tv.danmaku.bili")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "com.tencent.qqlive.audiobox:id/qqlive_player_root_view";
            case 1:
                return "com.mgtv.tv:id/vod_player_parent";
            case 2:
                return "com.qiyi.video.speaker:id/player_root";
            case 3:
                return "tv.danmaku.bili:id/controller_group";
            case 4:
                return "com.youku.iot:id/tvBoxVideoView";
            case 5:
                if (b()) {
                    return "com.xiaomi.micolauncher:id/vp_video_container";
                }
                return null;
            default:
                return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String findAnchorViewId(String str) {
        char c;
        switch (str.hashCode()) {
            case -2008684377:
                if (str.equals(MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -426347972:
                if (str.equals("com.youku.iot")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -338862911:
                if (str.equals("com.tencent.qqlive.audiobox")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -264182514:
                if (str.equals("com.xiaomi.micolauncher")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 409393875:
                if (str.equals("com.qiyi.video.speaker")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1994036591:
                if (str.equals("tv.danmaku.bili")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "com.tencent.qqlive.audiobox:id/lw_title_container";
            case 1:
                return "com.mgtv.tv:id/sdkplayer_playback_seek_layout";
            case 2:
                return "com.qiyi.video.speaker:id/btn_pause";
            case 3:
                return "tv.danmaku.bili:id/seekBar";
            case 4:
                return "com.youku.iot:id/view_controller_pause_icon";
            case 5:
                if (b()) {
                    return "com.xiaomi.micolauncher:id/video_controller";
                }
                return null;
            default:
                return null;
        }
    }

    public static boolean isVideoPlayingQuited(boolean z) {
        boolean a = a();
        boolean b = b();
        boolean z2 = a && !b;
        if (z) {
            L.accessibility.d("launcherResumed:%s,micoPlayerResumed:%s,isVideoPlayingQuited:%s", Boolean.valueOf(a), Boolean.valueOf(b), Boolean.valueOf(z2));
        }
        return z2;
    }

    private static boolean a() {
        return ActivityLifeCycleManager.getInstance().getTopActivity() != null && ActivityLifeCycleManager.getInstance().getTopActivity().getWindow().getDecorView().getVisibility() == 0;
    }

    private static boolean b() {
        return ActivityLifeCycleManager.getInstance().getTopActivity() != null && (ActivityLifeCycleManager.getInstance().getTopActivity() instanceof VideoPlayerActivity);
    }

    public static boolean needShow() {
        VideoModel.ShotType shotType = VideoModel.getInstance().getShotType();
        boolean z = shotType == VideoModel.ShotType.TYPE_MULTI_MULTI || shotType == VideoModel.ShotType.TYPE_SINGLE_MULTI;
        Logger logger = L.accessibility;
        logger.d("needShow:" + z);
        return z;
    }

    public static boolean isAccessibilitySettingsOn(Context context, Class<? extends AccessibilityService> cls) {
        int i;
        String string;
        String str = context.getPackageName() + "/" + cls.getCanonicalName();
        try {
            i = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), "accessibility_enabled");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            i = 0;
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        if (i == 1 && (string = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "enabled_accessibility_services")) != null) {
            simpleStringSplitter.setString(string);
            while (simpleStringSplitter.hasNext()) {
                if (simpleStringSplitter.next().equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void enableAccessibilityService() {
        Settings.Secure.putString(MicoApplication.getApp().getContentResolver(), "enabled_accessibility_services", "com.xiaomi.micolauncher/com.xiaomi.micolauncher.module.multicp.HookThirdPartyAppsService");
        Settings.Secure.putInt(MicoApplication.getApp().getContentResolver(), "accessibility_enabled", 1);
    }
}
