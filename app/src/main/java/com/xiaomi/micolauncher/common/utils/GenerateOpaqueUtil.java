package com.xiaomi.micolauncher.common.utils;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class GenerateOpaqueUtil {
    private static final String a = "&deviceid=".concat(SystemSetting.getDeviceID());
    public static String CODEVER_STRING = "&codever=" + ChildVideoDataManager.CODEVER;

    public static String getOpaqueString(String str, LinkedHashMap<String, Object> linkedHashMap) {
        String a2 = a(str, linkedHashMap);
        L.childContent.i("generateOpaqueString  %s", a2);
        return a(a2);
    }

    public static String getOpaqueString(String str) {
        return getOpaqueString(str, null);
    }

    private static String a(String str) {
        return a.a(str.getBytes(), "581582928c881b42eedce96331bff5d3".getBytes());
    }

    private static String a(String str, LinkedHashMap<String, Object> linkedHashMap) {
        if (!str.contains("?")) {
            str = str.concat("?");
        }
        if (ContainerUtil.hasData(linkedHashMap)) {
            StringBuilder sb = new StringBuilder(str);
            for (Map.Entry<String, Object> entry : linkedHashMap.entrySet()) {
                if (!sb.toString().endsWith("?")) {
                    sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                }
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            str = sb.toString();
        }
        return str + "&ptf=13201&app=21" + CODEVER_STRING + a + "&token=0f9dfa001cba164d7bda671649c50abf";
    }
}
