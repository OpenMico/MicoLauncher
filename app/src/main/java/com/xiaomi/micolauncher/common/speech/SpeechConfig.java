package com.xiaomi.micolauncher.common.speech;

import android.content.Context;
import android.os.SystemProperties;
import android.util.Patterns;
import androidx.annotation.GuardedBy;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;

/* loaded from: classes3.dex */
public class SpeechConfig {
    @GuardedBy("wakeupSoundLock")
    private static int a = -1;
    private static final Object b = new Object();
    private static final int c = a();
    private static final int d = c();

    public static boolean dialogForceAllDomain() {
        return false;
    }

    public static int getAudioChannels() {
        return 3;
    }

    public static int getAudioSampleRates() {
        return 16000;
    }

    public static boolean supportDialogSpeech() {
        return true;
    }

    public static boolean supportHourlyChime() {
        return true;
    }

    public static boolean supportNearByWakeup() {
        return true;
    }

    static int a() {
        char c2;
        String str = SystemProperties.get("mi.lab.vad.type");
        int hashCode = str.hashCode();
        int i = 0;
        if (hashCode == 94756405) {
            if (str.equals("cloud")) {
                c2 = 1;
            }
            c2 = 65535;
        } else if (hashCode != 103145323) {
            if (hashCode == 313349459 && str.equals("local-cloud")) {
                c2 = 2;
            }
            c2 = 65535;
        } else {
            if (str.equals("local")) {
                c2 = 0;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                i = 1;
                break;
            case 1:
                i = 2;
                break;
            case 2:
                i = 3;
                break;
        }
        if (i != 0) {
            return i;
        }
        if (Hardware.isLx04()) {
            return 2;
        }
        return Hardware.isBigScreen() ? 3 : 1;
    }

    public static boolean isLabTest() {
        int i;
        String str = SystemProperties.get("persist.mi.lab.test");
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            L.base.e("setWakeupSound.isLabTest.e=", e);
            i = 0;
        }
        return i > 0;
    }

    public static boolean isAutoTest() {
        int i;
        String str = SystemProperties.get("persist.mi.auto.test");
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            L.base.e("setWakeupSound.isAutoTest.e=", e);
            i = 0;
        }
        return i > 0;
    }

    public static boolean muteWakeupSound() {
        int i;
        String str = SystemProperties.get("persist.mi.lab.test.mute.wakeup");
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            L.base.e("SpeechConfig.muteWakeupSound.e=", e);
            i = 0;
        }
        return i > 0 && isLabTest();
    }

    public static boolean isLocalVad() {
        int i = c;
        return i == 1 || i == 3;
    }

    public static boolean isUsingCloudVad() {
        int i = c;
        return i == 2 || i == 3;
    }

    public static boolean showWakeupViewInSceneMode() {
        return Hardware.isBigScreen();
    }

    @Deprecated
    public static int b() {
        String str = SystemProperties.get("persist.mi.wakeup.sense");
        if (str == null || str.length() == 0) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            L.base.e("SpeechConfig.getWakeupSense.e=", e);
            return 0;
        }
    }

    public static boolean supportTurnOffWakeupSound() {
        return !Hardware.isLx04();
    }

    public static boolean getWakeupSound() {
        boolean z;
        synchronized (b) {
            z = false;
            if (a == -1) {
                if (supportTurnOffWakeupSound()) {
                    a = SystemSetting.getWakeupSoundEnable() ? 1 : 0;
                } else {
                    a = 1;
                }
            }
            if (a > 0) {
                z = true;
            }
        }
        return z;
    }

    public static void setWakeupSound(boolean z) {
        if (supportTurnOffWakeupSound()) {
            synchronized (b) {
                a = z ? 1 : 0;
                SystemSetting.setWakeupSoundEnable(z);
                L.base.d("SpeechConfig.setWakeupSound=%s", Integer.valueOf(a));
            }
        }
    }

    public static int getTtsVolume() {
        String str;
        if (!DebugHelper.isDebugVersion() || (str = SystemProperties.get("persist.mi.tts.volume")) == null || str.length() == 0) {
            return 100;
        }
        try {
            int parseInt = Integer.parseInt(str);
            if (parseInt < 0 || parseInt > 100) {
                return 100;
            }
            return parseInt;
        } catch (NumberFormatException e) {
            L.base.e("SpeechConfig.getTtsVolume.e=", e);
            return 100;
        }
    }

    public static boolean needToSaveTts() {
        return DebugHelper.isDebugVersion();
    }

    public static boolean a(Context context) {
        return Hardware.isX10();
    }

    private static int c() {
        String str = SystemProperties.get("persist.mi.lab.save.power");
        if (str == null || str.length() == 0) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            L.base.e("SpeechConfig.isSavePowerMode.e=", e);
            return 0;
        }
    }

    public static boolean isSavePowerMode() {
        return d > 0;
    }

    public static int getPowerSaveModeSleepMs() {
        return d;
    }

    public static String getAivsConnectUrl() {
        String str = SystemProperties.get("persist.mi.aivs.ip");
        if (str == null || str.length() == 0 || !Patterns.IP_ADDRESS.matcher(str).matches()) {
            return null;
        }
        return "ws://" + str + ":80/speech/v1.0/longaccess";
    }

    public static boolean isXiaomiWakeupAlgo() {
        return Hardware.isX08E() || Hardware.isX10() || Hardware.isX6A();
    }
}
