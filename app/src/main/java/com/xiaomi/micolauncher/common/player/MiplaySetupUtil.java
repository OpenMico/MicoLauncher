package com.xiaomi.micolauncher.common.player;

import android.content.Context;
import android.os.Bundle;
import com.mi.milink.mediacore.MediaCoreConfig;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.notification.NotifycationUtils;
import java.io.File;
import java.util.Random;

/* loaded from: classes3.dex */
public class MiplaySetupUtil {
    public static final int DEVICE_CATEGORY_SOUND = 16;
    public static final int NOT_CHECK_DEV_VALUE = 1;

    public static void setup(Context context) {
        Bundle bundle = new Bundle();
        bundle.putInt("notCheckDev", 1);
        File externalFilesDir = context.getExternalFilesDir("mediacore");
        if (!externalFilesDir.exists()) {
            externalFilesDir.mkdirs();
        }
        bundle.putString("logPath", externalFilesDir.getAbsolutePath());
        MediaCoreConfig.edit().setDeviceCategory(16).setOptionArguments(bundle).setForegroundNotification(new Random().nextInt(5000) + 5000, NotifycationUtils.getNotification(context)).commit();
        L.startUpProfile.d("MiPlayerSetup");
    }

    public static boolean isMiplayProcess(String str) {
        if (str == null) {
            return false;
        }
        return str.endsWith("pushservice");
    }
}
