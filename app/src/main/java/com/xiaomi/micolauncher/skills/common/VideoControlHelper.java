package com.xiaomi.micolauncher.skills.common;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VideoControlHelper {
    public static void fastForward(Context context, String str) {
        ThirdPartyAppProxy.getInstance().fastForward(context, TimeUnit.SECONDS.toMillis(a(str)));
    }

    public static void fastBackward(Context context, String str) {
        ThirdPartyAppProxy.getInstance().fastBackward(context, TimeUnit.SECONDS.toMillis(a(str)));
    }

    private static int a(String str) {
        JSONObject parseObject = JSONObject.parseObject(str);
        return TimeUtils.getSeconds(parseObject.getString("hour"), parseObject.getString("minute"), parseObject.getString("second"));
    }

    public static boolean select(Context context, String str) {
        int selectionIndex = getSelectionIndex(str);
        if (selectionIndex < 0) {
            return false;
        }
        return ThirdPartyAppProxy.getInstance().select(context, selectionIndex);
    }

    public static int getSelectionIndex(String str) {
        String string = JSONObject.parseObject(str).getString(MiSoundBoxCommandExtras.INDEX);
        if (string == null || string.length() <= 0) {
            return -1;
        }
        return Integer.parseInt(string);
    }
}
