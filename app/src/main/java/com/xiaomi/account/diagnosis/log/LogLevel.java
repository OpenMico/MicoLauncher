package com.xiaomi.account.diagnosis.log;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;

/* loaded from: classes2.dex */
public enum LogLevel {
    VERBOSE(ExifInterface.GPS_MEASUREMENT_INTERRUPTED),
    DEBUG(HomePageRecordHelper.AREA_D),
    INFO("I"),
    WARN(ExifInterface.LONGITUDE_WEST),
    ERROR("E");
    
    private final String mLevelStr;

    LogLevel(String str) {
        this.mLevelStr = str;
    }

    public static LogLevel fromInt(int i) {
        switch (i) {
            case 2:
                return VERBOSE;
            case 3:
                return DEBUG;
            case 4:
                return INFO;
            case 5:
                return WARN;
            case 6:
                return ERROR;
            default:
                return VERBOSE;
        }
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.mLevelStr;
    }
}
