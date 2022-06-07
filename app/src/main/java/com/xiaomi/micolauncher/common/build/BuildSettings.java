package com.xiaomi.micolauncher.common.build;

import android.content.Context;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.passport.snscorelib.internal.utils.SNSType;

/* loaded from: classes3.dex */
public class BuildSettings {
    public static final String DAILY = "DAILY";
    public static final boolean IsDailyBuild;
    public static final boolean IsDevBuild;
    public static final boolean IsLogableBuild;
    public static final boolean IsPlayBuild;
    public static final boolean IsTunnelBuild;
    public static final boolean IsWeixinBuild;
    public static final String PLAY = "PLAY";
    public static final String XIAOMI = "XIAOMI";
    public static final boolean IsDefaultChannel = "DEV".contains("2A2FE0D7");
    public static final boolean IsDebugBuild = "DEBUG".equalsIgnoreCase("DEV");
    public static final boolean IsTestBuild = "DEV".equalsIgnoreCase("TEST");
    public static final boolean IsStagingBuild = "DEV".equalsIgnoreCase("STAGING");
    public static final boolean IsPreviewBuild = "DEV".equalsIgnoreCase("PREVIEW");

    static {
        boolean z = false;
        IsLogableBuild = "LOGABLE".equalsIgnoreCase("DEV") || IsTestBuild || IsDebugBuild || IsStagingBuild;
        IsDevBuild = "DEV".equalsIgnoreCase("DEV");
        IsDailyBuild = "DEV".equalsIgnoreCase(DAILY);
        IsPlayBuild = "DEV".equalsIgnoreCase(PLAY);
        IsTunnelBuild = IsDebugBuild || "TUNNEL".equalsIgnoreCase("DEV");
        if (IsDailyBuild || IsDebugBuild || SNSType.WEIXIN.equalsIgnoreCase("DEV")) {
            z = true;
        }
        IsWeixinBuild = z;
    }

    public static String getReleaseChannel(Context context) {
        if (IsDefaultChannel) {
            return PreferenceUtils.getSettingString(context, "build_channel", "DEV");
        }
        PreferenceUtils.setSettingString(context, "build_channel", "DEV");
        return "DEV";
    }
}
