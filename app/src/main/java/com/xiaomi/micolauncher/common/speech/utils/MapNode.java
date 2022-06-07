package com.xiaomi.micolauncher.common.speech.utils;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class MapNode {
    public String requestId;
    public final HashMap<String, Long> timeValue = new HashMap<>();
    public final HashMap<String, Integer> codeValue = new HashMap<>();
    public final HashMap<String, String> stateValue = new HashMap<>();

    public boolean isEmpty() {
        if (TextUtils.isEmpty(this.requestId)) {
            return true;
        }
        return this.timeValue.isEmpty() && this.codeValue.isEmpty() && this.stateValue.isEmpty();
    }

    public void put(String str, Long l) {
        this.timeValue.put(str, l);
    }

    public void putResult(String str, Integer num) {
        this.codeValue.put(str, num);
    }

    public void putResult(String str, String str2) {
        this.stateValue.put(str, str2);
    }

    public String dump() {
        StringBuilder sb = new StringBuilder("|大一统");
        HashMap<String, Long> hashMap = this.timeValue;
        if (hashMap != null && !hashMap.isEmpty()) {
            for (Map.Entry<String, Long> entry : this.timeValue.entrySet()) {
                String key = entry.getKey();
                Long value = entry.getValue();
                if (!(key == null || value == null)) {
                    sb.append("|");
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                }
            }
        }
        HashMap<String, Integer> hashMap2 = this.codeValue;
        if (hashMap2 != null && !hashMap2.isEmpty()) {
            for (Map.Entry<String, Integer> entry2 : this.codeValue.entrySet()) {
                String key2 = entry2.getKey();
                Integer value2 = entry2.getValue();
                if (!(key2 == null || value2 == null)) {
                    sb.append("|");
                    sb.append(key2);
                    sb.append("=");
                    sb.append(value2);
                }
            }
        }
        HashMap<String, String> hashMap3 = this.stateValue;
        if (hashMap3 != null && !hashMap3.isEmpty()) {
            for (Map.Entry<String, String> entry3 : this.stateValue.entrySet()) {
                String key3 = entry3.getKey();
                String value3 = entry3.getValue();
                if (!(key3 == null || value3 == null)) {
                    sb.append("|");
                    sb.append(key3);
                    sb.append("=");
                    sb.append(value3);
                }
            }
        }
        sb.append("|");
        return sb.toString();
    }
}
