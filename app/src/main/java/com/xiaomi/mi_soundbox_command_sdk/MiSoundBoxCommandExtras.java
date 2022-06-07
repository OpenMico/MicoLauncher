package com.xiaomi.mi_soundbox_command_sdk;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class MiSoundBoxCommandExtras {
    public static final String DURATION_MILLIS = "durationMillis";
    public static final String INDEX = "index";
    public static final String RESOLUTION = "resolution";
    public static final String URI = "uri";
    @SerializedName("args")
    private Map<String, String> a = new HashMap();

    public String getValue(String str) {
        Map<String, String> map = this.a;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public Map<String, String> getArgs() {
        return this.a;
    }

    public boolean isEmpty() {
        Map<String, String> map = this.a;
        return map == null || map.isEmpty();
    }

    public void put(String str, String str2) {
        this.a.put(str, str2);
    }
}
