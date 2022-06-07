package com.xiaomi.analytics;

import android.util.Log;
import com.umeng.analytics.pro.ai;
import com.xiaomi.analytics.internal.util.ALog;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class LogEvent {
    private LogType a;
    private long b;
    private JSONObject c;
    private JSONObject d;
    private IdType e;

    /* loaded from: classes3.dex */
    public enum LogType {
        TYPE_EVENT(0),
        TYPE_AD(1);
        
        private int mValue;

        LogType(int i) {
            this.mValue = 0;
            this.mValue = i;
        }

        public static LogType valueOf(int i) {
            if (i != 1) {
                return TYPE_EVENT;
            }
            return TYPE_AD;
        }

        public int value() {
            return this.mValue;
        }
    }

    /* loaded from: classes3.dex */
    public enum IdType {
        TYPE_DEFAULT(0),
        TYPE_IMEI(1),
        TYPE_MAC(2),
        TYPE_ANDROID_ID(3),
        TYPE_AAID(4),
        TYPE_GAID(5),
        TYPE_GUID(6);
        
        private int mValue;

        IdType(int i) {
            this.mValue = 0;
            this.mValue = i;
        }

        public int value() {
            return this.mValue;
        }
    }

    public LogEvent() {
        this.a = LogType.TYPE_EVENT;
        this.c = new JSONObject();
        this.d = new JSONObject();
        this.e = IdType.TYPE_DEFAULT;
        this.b = System.currentTimeMillis();
    }

    public LogEvent(LogType logType) {
        this.a = LogType.TYPE_EVENT;
        this.c = new JSONObject();
        this.d = new JSONObject();
        this.e = IdType.TYPE_DEFAULT;
        this.b = System.currentTimeMillis();
        if (logType != null) {
            this.a = logType;
        }
    }

    public LogEvent(IdType idType) {
        this.a = LogType.TYPE_EVENT;
        this.c = new JSONObject();
        this.d = new JSONObject();
        this.e = IdType.TYPE_DEFAULT;
        this.b = System.currentTimeMillis();
        if (idType != null) {
            this.e = idType;
        }
    }

    public LogEvent(LogType logType, IdType idType) {
        this.a = LogType.TYPE_EVENT;
        this.c = new JSONObject();
        this.d = new JSONObject();
        this.e = IdType.TYPE_DEFAULT;
        this.b = System.currentTimeMillis();
        if (logType != null) {
            this.a = logType;
        }
        if (idType != null) {
            this.e = idType;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LogEvent a(JSONObject jSONObject) {
        if (jSONObject != null) {
            this.d = jSONObject;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LogEvent b(JSONObject jSONObject) {
        if (jSONObject != null) {
            this.c = jSONObject;
        }
        return this;
    }

    public String pack(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ai.aC, 2);
            jSONObject.put("appId", str);
            jSONObject.put("sessionId", str3);
            jSONObject.put("configKey", str2);
            jSONObject.put("content", this.c.toString());
            jSONObject.put("eventTime", this.b);
            jSONObject.put("logType", this.a.value());
            jSONObject.put("extra", this.d.toString());
            jSONObject.put("idType", this.e.value());
        } catch (Exception e) {
            Log.e(ALog.addPrefix("LogEvent"), "pack e", e);
        }
        return jSONObject.toString();
    }

    public static LogEvent create() {
        return new LogEvent();
    }

    public static LogEvent create(IdType idType) {
        return new LogEvent(idType);
    }

    public static LogEvent create(LogType logType) {
        return new LogEvent(logType);
    }

    public static LogEvent create(LogType logType, IdType idType) {
        return new LogEvent(logType, idType);
    }
}
