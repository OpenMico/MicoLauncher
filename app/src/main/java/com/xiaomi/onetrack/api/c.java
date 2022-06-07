package com.xiaomi.onetrack.api;

import android.content.Context;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.ServiceQualityEvent;
import com.xiaomi.onetrack.b.g;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.e.b;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.v;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c {
    public static final String a = "B";
    public static final String b = "H";
    private static final String c = "EventDataBuilder";

    public static String a(String str, JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, v vVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, b.a(str, configuration, iEventHook, vVar));
        jSONObject3.put("B", r.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(String str, String str2, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject, boolean z, v vVar) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, b.a(str2, configuration, iEventHook, vVar));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("class", str);
        jSONObject3.put("type", 1);
        jSONObject3.put(b.t, z);
        jSONObject2.put("B", r.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(String str, String str2, long j, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject, v vVar) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, b.a(str2, configuration, iEventHook, vVar));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("class", str);
        jSONObject3.put("type", 2);
        jSONObject3.put("duration", j);
        jSONObject2.put("B", r.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(String str, String str2, String str3, String str4, String str5, long j, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject, v vVar) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        JSONObject a2 = b.a("onetrack_bug_report", configuration, iEventHook, vVar);
        if (str5 != null) {
            a2.put(b.C0178b.o, str5);
        }
        jSONObject2.put(b, a2);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("exception", str);
        jSONObject3.put("type", str3);
        jSONObject3.put("message", str2);
        jSONObject3.put(b.n, str4);
        jSONObject3.put(b.o, j);
        jSONObject2.put("B", r.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject, v vVar) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, b.a(b.h, configuration, iEventHook, vVar));
        JSONObject jSONObject3 = new JSONObject();
        Context a2 = a.a();
        boolean s = aa.s();
        if (s) {
            aa.c(false);
        }
        jSONObject3.put("first_open", s);
        if (!(q.a() ? q.x() : configuration.isInternational())) {
            if (configuration.isIMEIEnable()) {
                jSONObject3.put(b.y, (Object) DeviceUtil.q(a2));
            }
            if (configuration.isIMSIEnable()) {
                jSONObject3.put(b.z, DeviceUtil.v(a2));
            }
        }
        jSONObject3.put(b.C, a.a(configuration));
        jSONObject2.put("B", r.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, v vVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, b.a(b.c, configuration, iEventHook, vVar));
        jSONObject3.put("B", r.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String b(JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, v vVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, b.a(b.d, configuration, iEventHook, vVar));
        jSONObject3.put("B", r.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(String str, String str2, JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, v vVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, b.a(str2, configuration, iEventHook, str, vVar));
        jSONObject3.put("B", r.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(Configuration configuration, OneTrack.IEventHook iEventHook, v vVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, b.a(b.i, configuration, iEventHook, vVar));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b.p, g.b());
        jSONObject.put("B", jSONObject2);
        return jSONObject.toString();
    }

    public static String c(JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, v vVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, b.a(b.a, configuration, iEventHook, vVar));
        jSONObject3.put("B", r.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String d(JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, v vVar) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, b.a(b.b, configuration, iEventHook, vVar));
        jSONObject3.put("B", r.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(ServiceQualityEvent serviceQualityEvent, Configuration configuration, OneTrack.IEventHook iEventHook, v vVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, b.a(b.e, configuration, iEventHook, vVar));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b.D, serviceQualityEvent.getScheme());
        jSONObject2.put(b.E, serviceQualityEvent.getHost());
        jSONObject2.put("port", serviceQualityEvent.getPort());
        jSONObject2.put("path", serviceQualityEvent.getPath());
        jSONObject2.put(b.H, serviceQualityEvent.getIps());
        jSONObject2.put(b.I, serviceQualityEvent.getResponseCode());
        jSONObject2.put("status", serviceQualityEvent.getStatusCode());
        jSONObject2.put("exception", serviceQualityEvent.getExceptionTag());
        jSONObject2.put("result", serviceQualityEvent.getResultType());
        jSONObject2.put(b.M, serviceQualityEvent.getRetryCount());
        jSONObject2.put(b.N, serviceQualityEvent.getRequestTimestamp());
        jSONObject2.put(b.O, serviceQualityEvent.getRequestNetType());
        jSONObject2.put(b.P, serviceQualityEvent.getDnsLookupTime());
        jSONObject2.put(b.Q, serviceQualityEvent.getTcpConnectTime());
        jSONObject2.put(b.S, serviceQualityEvent.getHandshakeTime());
        jSONObject2.put(b.T, serviceQualityEvent.getReceiveFirstByteTime());
        jSONObject2.put(b.U, serviceQualityEvent.getReceiveAllByteTime());
        jSONObject2.put(b.R, serviceQualityEvent.getRequestDataSendTime());
        jSONObject2.put("duration", serviceQualityEvent.getDuration());
        jSONObject2.put(b.W, serviceQualityEvent.getNetSdkVersion());
        Map<String, Object> extraParams = serviceQualityEvent.getExtraParams();
        if (extraParams != null && extraParams.size() > 0) {
            for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (r.b(value)) {
                    jSONObject2.put(key, value);
                }
            }
        }
        jSONObject.put("B", jSONObject2);
        return jSONObject.toString();
    }

    public static String a(long j, String str, long j2, long j3, Configuration configuration, OneTrack.IEventHook iEventHook, v vVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, b.a(b.j, configuration, iEventHook, vVar));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b.X, j);
        jSONObject2.put(b.Y, str);
        jSONObject2.put(b.Z, j2);
        jSONObject2.put(b.aa, j3);
        jSONObject.put("B", jSONObject2);
        return jSONObject.toString();
    }
}
