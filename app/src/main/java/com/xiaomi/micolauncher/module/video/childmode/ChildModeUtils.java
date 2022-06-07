package com.xiaomi.micolauncher.module.video.childmode;

import android.util.Pair;
import androidx.fragment.app.Fragment;
import com.xiaomi.mico.settingslib.core.ChildModeConfig;
import com.xiaomi.micolauncher.common.L;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildModeUtils {
    public static final int MAX_MINUTE = 1440;
    public static final int REMAIN_TIME_TYPE_EACH_DAY = 1;
    public static final int REMAIN_TIME_TYPE_EACH_ONCE = 0;
    public static final int REMAIN_TIME_TYPE_FORBID_PERIOD = 2;
    public static final int REMAIN_TIME_TYPE_NONE = -1;
    private static final Calendar a = Calendar.getInstance();

    public static Pair<Integer, Integer> calculateTimeRemainInMinutes(ChildModeConfig childModeConfig, int i, long j) {
        if (childModeConfig == null || childModeConfig.getAntiAddiction() == null || !childModeConfig.getAntiAddiction().watchTimeRestricted) {
            return new Pair<>(-1, Integer.valueOf((int) MAX_MINUTE));
        }
        L.childmode.i("after forbid time %s  used time %s", Long.valueOf(System.currentTimeMillis() - j), Integer.valueOf(i));
        if (System.currentTimeMillis() - j <= TimeUnit.HOURS.toMillis(1L)) {
            return new Pair<>(-1, 0);
        }
        int i2 = childModeConfig.getAntiAddiction().watchTimeMaxMinutesEachTime;
        int i3 = childModeConfig.getAntiAddiction().watchMaxMinutesEachDay - i;
        int remainTimeForbidWatch = remainTimeForbidWatch(childModeConfig.getAntiAddiction(), Math.min(i2, i3));
        L.childmode.i("calculateTimeRemainInMinutes remainTimeForbid %d", Integer.valueOf(remainTimeForbidWatch));
        if (remainTimeForbidWatch == i2) {
            return new Pair<>(0, Integer.valueOf(remainTimeForbidWatch));
        }
        if (remainTimeForbidWatch == i3) {
            return new Pair<>(1, Integer.valueOf(remainTimeForbidWatch));
        }
        return new Pair<>(2, Integer.valueOf(remainTimeForbidWatch));
    }

    public static int remainTimeForbidWatch(ChildModeConfig.AntiAddiction antiAddiction, int i) {
        if (i <= 0 || !isGoThroughTheForbidTimePeriod(antiAddiction, System.currentTimeMillis() + (i * 60 * 1000))) {
            return i;
        }
        ChildModeConfig.WallClockTime wallClockTime = antiAddiction.forbidWatchVideoStartTime;
        long currentTimeMillis = System.currentTimeMillis();
        a.setTimeInMillis(currentTimeMillis);
        a.set(11, wallClockTime.hour);
        a.set(12, wallClockTime.minute);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(a.getTimeInMillis() - currentTimeMillis);
        L.childmode.i("remainTimeForbidWatch minutes %d", Long.valueOf(minutes));
        return (int) minutes;
    }

    public static boolean isInForbidTimePeriod(ChildModeConfig.AntiAddiction antiAddiction, long j) {
        if (antiAddiction != null && antiAddiction.watchTimeRestricted) {
            ChildModeConfig.WallClockTime wallClockTime = antiAddiction.forbidWatchVideoStartTime;
            ChildModeConfig.WallClockTime wallClockTime2 = antiAddiction.forbidWatchVideoEndTime;
            if (!(wallClockTime == null || wallClockTime2 == null)) {
                return wallClockTime.compareTo(wallClockTime2) > 0 ? wallClockTime.lessOrEqualThanMills(j) || wallClockTime2.largerOrEqualThanMills(j) : wallClockTime.lessOrEqualThanMills(j) && wallClockTime2.largerOrEqualThanMills(j);
            }
        }
        return false;
    }

    public static boolean isGoThroughTheForbidTimePeriod(ChildModeConfig.AntiAddiction antiAddiction, long j) {
        if (antiAddiction != null && antiAddiction.watchTimeRestricted) {
            ChildModeConfig.WallClockTime wallClockTime = antiAddiction.forbidWatchVideoStartTime;
            long currentTimeMillis = System.currentTimeMillis();
            a.setTimeInMillis(currentTimeMillis);
            ChildModeConfig.WallClockTime wallClockTime2 = new ChildModeConfig.WallClockTime(a.get(11), a.get(12));
            a.setTimeInMillis(j);
            ChildModeConfig.WallClockTime wallClockTime3 = new ChildModeConfig.WallClockTime(a.get(11), a.get(12));
            if (wallClockTime != null) {
                return wallClockTime2.compareTo(wallClockTime3) > 0 ? wallClockTime.largerOrEqualThanMills(currentTimeMillis) || wallClockTime.lessOrEqualThanMills(j) : wallClockTime.largerOrEqualThanMills(currentTimeMillis) && wallClockTime.lessOrEqualThanMills(j);
            }
        }
        return false;
    }

    public static void setEyeProtectionMode(boolean z) {
        try {
            L.childmode.i("setEyeProtectionMode,enable=%s,set result:%s", Boolean.valueOf(z), Class.forName("com.mediatek.pq.PictureQuality").getMethod("enableBlueLight", Boolean.TYPE).invoke(null, Boolean.valueOf(z)));
        } catch (Fragment.InstantiationException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            L.childmode.e("setEyeProtectionMode", e);
        }
    }
}
