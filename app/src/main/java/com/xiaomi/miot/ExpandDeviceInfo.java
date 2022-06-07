package com.xiaomi.miot;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ExpandDeviceInfo {
    private Map<String, ModelInfo> modelInfoMap;

    public static ExpandDeviceInfo fromJson(String str) {
        return null;
    }

    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"expandDeviceInfo\":{");
        Map<String, ModelInfo> map = this.modelInfoMap;
        if (map != null && !map.isEmpty()) {
            sb.append("\"modelInfo\":[");
            boolean z = false;
            for (String str : this.modelInfoMap.keySet()) {
                sb.append("{\"model\":\"" + str + "\",\"modelInfo\":" + this.modelInfoMap.get(str).toJsonString() + "},");
                z = true;
            }
            int length = sb.length();
            if (z) {
                sb.deleteCharAt(length - 1);
            }
            sb.append("]");
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }

    public void putModelInfo(ModelInfo modelInfo) {
        if (modelInfo != null && !TextUtils.isEmpty(modelInfo.getModel())) {
            if (this.modelInfoMap == null) {
                this.modelInfoMap = new HashMap(4);
            }
            this.modelInfoMap.put(modelInfo.getModel(), modelInfo);
        }
    }

    public ModelInfo getModelInfo(String str) {
        Map<String, ModelInfo> map;
        if (!TextUtils.isEmpty(str) && (map = this.modelInfoMap) != null && !map.isEmpty()) {
            return this.modelInfoMap.get(str);
        }
        return null;
    }
}
