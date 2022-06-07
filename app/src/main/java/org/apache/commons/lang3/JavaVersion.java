package org.apache.commons.lang3;

import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.zlw.main.recorderlib.BuildConfig;
import org.apache.commons.lang3.math.NumberUtils;

/* loaded from: classes5.dex */
public enum JavaVersion {
    JAVA_0_9(1.5f, "0.9"),
    JAVA_1_1(1.1f, "1.1"),
    JAVA_1_2(1.2f, BuildConfig.VERSION_NAME),
    JAVA_1_3(1.3f, "1.3"),
    JAVA_1_4(1.4f, "1.4"),
    JAVA_1_5(1.5f, "1.5"),
    JAVA_1_6(1.6f, "1.6"),
    JAVA_1_7(1.7f, "1.7"),
    JAVA_1_8(1.8f, com.mijiasdk.bleserver.BuildConfig.VERSION_NAME),
    JAVA_1_9(9.0f, Commands.ResolutionValues.BITSTREAM_4K),
    JAVA_9(9.0f, Commands.ResolutionValues.BITSTREAM_4K),
    JAVA_RECENT(a(), Float.toString(a()));
    
    private final String name;
    private final float value;

    JavaVersion(float f, String str) {
        this.value = f;
        this.name = str;
    }

    public boolean atLeast(JavaVersion javaVersion) {
        return this.value >= javaVersion.value;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static JavaVersion a(String str) {
        if ("0.9".equals(str)) {
            return JAVA_0_9;
        }
        if ("1.1".equals(str)) {
            return JAVA_1_1;
        }
        if (BuildConfig.VERSION_NAME.equals(str)) {
            return JAVA_1_2;
        }
        if ("1.3".equals(str)) {
            return JAVA_1_3;
        }
        if ("1.4".equals(str)) {
            return JAVA_1_4;
        }
        if ("1.5".equals(str)) {
            return JAVA_1_5;
        }
        if ("1.6".equals(str)) {
            return JAVA_1_6;
        }
        if ("1.7".equals(str)) {
            return JAVA_1_7;
        }
        if (com.mijiasdk.bleserver.BuildConfig.VERSION_NAME.equals(str)) {
            return JAVA_1_8;
        }
        if (Commands.ResolutionValues.BITSTREAM_4K.equals(str)) {
            return JAVA_9;
        }
        if (str != null && b(str) - 1.0d < 1.0d) {
            int max = Math.max(str.indexOf(46), str.indexOf(44));
            if (Float.parseFloat(str.substring(max + 1, Math.max(str.length(), str.indexOf(44, max)))) > 0.9f) {
                return JAVA_RECENT;
            }
        }
        return null;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.name;
    }

    private static float a() {
        float b = b(System.getProperty("java.specification.version", "99.0"));
        if (b > 0.0f) {
            return b;
        }
        return 99.0f;
    }

    private static float b(String str) {
        if (!str.contains(".")) {
            return NumberUtils.toFloat(str, -1.0f);
        }
        String[] split = str.split("\\.");
        if (split.length < 2) {
            return -1.0f;
        }
        return NumberUtils.toFloat(split[0] + '.' + split[1], -1.0f);
    }
}
