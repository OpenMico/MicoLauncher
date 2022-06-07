package com.xiaomi.mico.settingslib.core;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildModeConfig {
    public boolean isChildMode = false;
    @SerializedName("eyeProtection")
    private AntiAddiction a = new AntiAddiction();

    /* loaded from: classes3.dex */
    public enum AntiAddictionType {
        ALL_FUNCTION,
        SCREEN_FUNCTION,
        VIDEO_AUDIO,
        VIDEO
    }

    public AntiAddiction getAntiAddiction() {
        return this.a;
    }

    public static ChildModeConfig getDisabledChildModeConfig() {
        ChildModeConfig childModeConfig = new ChildModeConfig();
        childModeConfig.isChildMode = false;
        return childModeConfig;
    }

    public static ChildModeConfig getChildModeConfig(Context context) {
        ChildModeConfig a = MicoSettings.a(context);
        return a == null ? new ChildModeConfig() : a;
    }

    public static boolean saveChildModeConfig(Context context, boolean z) {
        ChildModeConfig childModeConfig = getChildModeConfig(context);
        childModeConfig.isChildMode = z;
        return MicoSettings.a(context, childModeConfig);
    }

    public static boolean saveChildModeConfig(Context context, ChildModeConfig childModeConfig) {
        return MicoSettings.a(context, childModeConfig);
    }

    public static boolean isChildModeEnable(Context context) {
        return getChildModeConfig(context).isChildMode;
    }

    public String toJsonStr() {
        return new Gson().toJson(this);
    }

    public static ChildModeConfig parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (ChildModeConfig) new Gson().fromJson(str, (Class<Object>) ChildModeConfig.class);
    }

    public String toString() {
        return "ChildModeConfig{isChildMode=" + this.isChildMode + ", antiAddiction=" + this.a + '}';
    }

    /* loaded from: classes3.dex */
    public static class AntiAddiction {
        @SerializedName("watchTimeRestricted")
        public boolean watchTimeRestricted;
        @SerializedName("workMode")
        public int workMode;
        @SerializedName("watchMaxMinutesEachDay")
        public int watchMaxMinutesEachDay = 60;
        @SerializedName("watchTimeMaxMinutesEachTime")
        public int watchTimeMaxMinutesEachTime = 30;
        @SerializedName("forbidWatchVideoStartTime")
        public WallClockTime forbidWatchVideoStartTime = new WallClockTime(21, 0);
        @SerializedName("forbidWatchVideoEndTime")
        public WallClockTime forbidWatchVideoEndTime = new WallClockTime(7, 0);
        @SerializedName("antiAddictionType")
        public AntiAddictionType antiAddictionType = AntiAddictionType.VIDEO;

        public String toString() {
            return "AntiAddiction{antiAddictionType=" + this.antiAddictionType + "watchTimeRestricted=" + this.watchTimeRestricted + ", watchTimeMaxMinutesEachTime=" + this.watchTimeMaxMinutesEachTime + ", watchMaxMinutesEachDay=" + this.watchMaxMinutesEachDay + ", forbidWatchVideoStartTime=" + this.forbidWatchVideoStartTime + ", forbidWatchVideoEndTime=" + this.forbidWatchVideoEndTime + '}';
        }
    }

    /* loaded from: classes3.dex */
    public static class WallClockTime implements Serializable, Comparable<WallClockTime> {
        @SerializedName("hour")
        public int hour;
        @SerializedName("minute")
        public int minute;

        public WallClockTime() {
        }

        public WallClockTime(int i, int i2) {
            this.hour = i;
            this.minute = i2;
        }

        public int compareTo(@NonNull WallClockTime wallClockTime) {
            return Long.signum((TimeUnit.HOURS.toMinutes(this.hour) + this.minute) - (TimeUnit.HOURS.toMinutes(wallClockTime.hour) + wallClockTime.minute));
        }

        public boolean lessOrEqualThanMills(long j) {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(j);
            int i = instance.get(11);
            int i2 = instance.get(12);
            int i3 = this.hour;
            return i > i3 || (i == i3 && i2 >= this.minute);
        }

        public boolean largerOrEqualThanMills(long j) {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(j);
            int i = instance.get(11);
            int i2 = instance.get(12);
            int i3 = this.hour;
            return i < i3 || (i == i3 && i2 <= this.minute);
        }

        public String toString() {
            return "WallClockTime{hour=" + this.hour + ", minute=" + this.minute + '}';
        }
    }
}
