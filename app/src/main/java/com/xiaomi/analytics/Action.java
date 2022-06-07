package com.xiaomi.analytics;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.analytics.internal.util.ALog;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public abstract class Action {
    protected static final String ACTION = "_action_";
    protected static final String CATEGORY = "_category_";
    protected static final String EVENT_ID = "_event_id_";
    protected static final String LABEL = "_label_";
    protected static final String VALUE = "_value_";
    private static Set<String> c = new HashSet();
    private JSONObject a = new JSONObject();
    private JSONObject b = new JSONObject();

    static {
        c.add(EVENT_ID);
        c.add(CATEGORY);
        c.add(ACTION);
        c.add(LABEL);
        c.add(VALUE);
    }

    protected Action addEventId(String str) {
        a(EVENT_ID, (Object) str);
        return this;
    }

    public Action addParam(String str, JSONObject jSONObject) {
        a(str);
        a(str, jSONObject);
        return this;
    }

    public Action addParam(String str, int i) {
        a(str);
        a(str, i);
        return this;
    }

    public Action addParam(String str, long j) {
        a(str);
        a(str, j);
        return this;
    }

    public Action addParam(String str, String str2) {
        a(str);
        a(str, (Object) str2);
        return this;
    }

    void a(String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            try {
                this.a.put(str, i);
            } catch (Exception e) {
                Log.e(ALog.addPrefix("Action"), "addContent int value e", e);
            }
        }
    }

    void a(String str, long j) {
        if (!TextUtils.isEmpty(str)) {
            try {
                this.a.put(str, j);
            } catch (Exception e) {
                Log.e(ALog.addPrefix("Action"), "addContent long value e", e);
            }
        }
    }

    void a(String str, Object obj) {
        if (!TextUtils.isEmpty(str)) {
            try {
                this.a.put(str, obj);
            } catch (Exception e) {
                Log.e(ALog.addPrefix("Action"), "addContent Object value e", e);
            }
        }
    }

    public void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String obj = keys.next().toString();
                a(obj);
                try {
                    this.a.put(obj, jSONObject.get(obj));
                } catch (Exception e) {
                    Log.e(ALog.addPrefix("Action"), "addContent e", e);
                }
            }
        }
    }

    void a(String str, String str2) {
        try {
            this.b.put(str, str2);
        } catch (Exception e) {
            Log.e(ALog.addPrefix("Action"), "addExtra e", e);
        }
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str) && c.contains(str)) {
            throw new IllegalArgumentException("this key " + str + " is built-in, please pick another key.");
        }
    }

    public final JSONObject a() {
        return this.a;
    }

    public final JSONObject b() {
        return this.b;
    }
}
