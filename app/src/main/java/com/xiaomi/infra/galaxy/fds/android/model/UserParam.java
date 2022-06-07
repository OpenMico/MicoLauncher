package com.xiaomi.infra.galaxy.fds.android.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public class UserParam {
    protected final Map<String, String> params = new HashMap();

    public Map<String, String> getParams() {
        return Collections.unmodifiableMap(this.params);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            if (i != 0) {
                sb.append(Typography.amp);
            }
            sb.append(entry.getKey());
            String value = entry.getValue();
            if (value != null) {
                sb.append('=');
                sb.append(value);
            }
            i++;
        }
        return sb.toString();
    }
}
