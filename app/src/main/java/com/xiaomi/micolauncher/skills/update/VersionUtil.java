package com.xiaomi.micolauncher.skills.update;

/* loaded from: classes3.dex */
public class VersionUtil {
    public static final String VERSION_CURRENT = "current";
    public static final String VERSION_STABLE = "stable";

    public static boolean isDevVersion() {
        return VERSION_STABLE.equals(RomUpdateAdapter.getInstance().getChannel());
    }

    public static boolean isDailyVersion() {
        return VERSION_CURRENT.equals(RomUpdateAdapter.getInstance().getChannel());
    }

    public static boolean isFacVersion() {
        return !isDevVersion() && !isDailyVersion();
    }
}
