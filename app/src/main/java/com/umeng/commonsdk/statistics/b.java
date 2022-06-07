package com.umeng.commonsdk.statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.az;
import com.umeng.analytics.pro.c;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.internal.a;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.stateless.d;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.ULog;
import com.umeng.commonsdk.statistics.idtracking.Envelope;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.idtracking.e;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import com.xiaomi.smarthome.library.common.network.Network;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: EnvelopeManager.java */
/* loaded from: classes2.dex */
public class b {
    public static String a = null;
    public static String b = "";
    private static final String c = "EnvelopeManager";
    private static final String d = "debug.umeng.umTaskId";
    private static final String e = "debug.umeng.umCaseId";
    private static final String f = "empty";
    private static String g = "";
    private static String h = "";
    private static String i;
    private static boolean k;
    private int j = 0;

    public static void a() {
        if (i != null) {
            i = null;
            e.a();
        }
    }

    public static long a(Context context) {
        long j = DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX - DataHelper.ENVELOPE_EXTRA_LENGTH;
        if (ULog.DEBUG) {
            Log.i(c, "free size is " + j);
        }
        return j;
    }

    private JSONObject a(int i2, JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                jSONObject.put("exception", i2);
            } catch (Exception unused) {
            }
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("exception", i2);
        } catch (Exception unused2) {
        }
        return jSONObject2;
    }

    private static boolean b() {
        g = UMUtils.getSystemProperty(d, "");
        h = UMUtils.getSystemProperty(e, "");
        return (!TextUtils.isEmpty(g) && !f.equals(g)) && (!TextUtils.isEmpty(h) && !f.equals(h));
    }

    public JSONObject a(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str, String str2, String str3) {
        JSONObject jSONObject3;
        String str4;
        String str5;
        Envelope envelope;
        String str6;
        if (!(!ULog.DEBUG || jSONObject == null || jSONObject2 == null)) {
            Log.i(c, "headerJSONObject size is " + jSONObject.toString().getBytes().length);
            Log.i(c, "bodyJSONObject size is " + jSONObject2.toString().getBytes().length);
        }
        JSONObject jSONObject4 = null;
        if (context == null || jSONObject2 == null) {
            return a(110, (JSONObject) null);
        }
        try {
            JSONObject b2 = b(context);
            if (!(b2 == null || jSONObject == null)) {
                b2 = a(b2, jSONObject);
            }
            if (!(b2 == null || jSONObject2 == null)) {
                Iterator keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    Object next = keys.next();
                    if (!(next == null || !(next instanceof String) || (str6 = (String) next) == null || jSONObject2.opt(str6) == null)) {
                        try {
                            b2.put(str6, jSONObject2.opt(str6));
                        } catch (Exception unused) {
                        }
                    }
                }
            }
            str2 = ai.aE;
            str3 = "1.0.0";
            if (!TextUtils.isEmpty(str2)) {
            }
            if (!TextUtils.isEmpty(str3)) {
            }
            if (b2 != null) {
                str5 = str2 + "==" + str3 + "&=";
                if (TextUtils.isEmpty(str5)) {
                    return a(101, b2);
                }
                if (str5.endsWith("&=")) {
                    str5 = str5.substring(0, str5.length() - 2);
                }
            } else {
                str5 = null;
            }
            if (b2 != null) {
                try {
                    e a2 = e.a(context);
                    if (a2 != null) {
                        a2.b();
                        String encodeToString = Base64.encodeToString(new az().a(a2.c()), 0);
                        if (!TextUtils.isEmpty(encodeToString)) {
                            JSONObject jSONObject5 = b2.getJSONObject("header");
                            jSONObject5.put(ai.Y, encodeToString);
                            b2.put("header", jSONObject5);
                        }
                    }
                } catch (Exception unused2) {
                }
            }
            if (b2 == null || !DataHelper.largeThanMaxSize(b2.toString().getBytes().length, DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX)) {
                if (b2 != null) {
                    Envelope a3 = a(context, b2.toString().getBytes());
                    if (a3 == null) {
                        return a(111, b2);
                    }
                    envelope = a3;
                } else {
                    envelope = null;
                }
                if (envelope != null && DataHelper.largeThanMaxSize(envelope.toBinary().length, DataHelper.ENVELOPE_LENGTH_MAX)) {
                    return a(114, b2);
                }
                int a4 = a(context, envelope, str5, b2 != null ? b2.optJSONObject("header").optString("app_version") : null, str);
                if (a4 != 0) {
                    return a(a4, b2);
                }
                if (ULog.DEBUG) {
                    Log.i(c, "constructHeader size is " + b2.toString().getBytes().length);
                }
                if (!str5.startsWith(ai.aB) && !str5.startsWith(ai.aA) && !str5.startsWith(ai.aF) && !str5.startsWith(ai.at) && !com.umeng.commonsdk.stateless.b.a()) {
                    new com.umeng.commonsdk.stateless.b(context);
                    com.umeng.commonsdk.stateless.b.b();
                }
                return b2;
            }
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (sharedPreferences != null) {
                sharedPreferences.edit().putInt("serial", sharedPreferences.getInt("serial", 1) + 1).commit();
            }
            return a(113, b2);
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
            if (jSONObject != null) {
                try {
                    jSONObject3 = new JSONObject();
                } catch (Exception e2) {
                    e = e2;
                }
                try {
                    jSONObject3.put("header", jSONObject);
                } catch (JSONException unused3) {
                } catch (Exception e3) {
                    e = e3;
                    jSONObject4 = jSONObject3;
                    UMCrashManager.reportCrash(context, e);
                    return a(110, jSONObject4);
                }
                jSONObject4 = jSONObject3;
            }
            if (jSONObject2 != null) {
                if (jSONObject4 == null) {
                    jSONObject4 = new JSONObject();
                }
                if (jSONObject2 != null) {
                    Iterator keys2 = jSONObject2.keys();
                    while (keys2.hasNext()) {
                        Object next2 = keys2.next();
                        if (!(next2 == null || !(next2 instanceof String) || (str4 = (String) next2) == null || jSONObject2.opt(str4) == null)) {
                            try {
                                jSONObject4.put(str4, jSONObject2.opt(str4));
                            } catch (Exception unused4) {
                            }
                        }
                    }
                }
            }
            return a(110, jSONObject4);
        }
    }

    public JSONObject a(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str) {
        Envelope envelope;
        String str2;
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("header", new JSONObject());
            try {
                if (b()) {
                    jSONObject.put("umTaskId", g);
                    jSONObject.put("umCaseId", h);
                }
            } catch (Throwable unused) {
            }
            if (jSONObject != null) {
                jSONObject3 = a(jSONObject3, jSONObject);
            }
            if (!(jSONObject3 == null || jSONObject2 == null)) {
                Iterator keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    Object next = keys.next();
                    if (!(next == null || !(next instanceof String) || (str2 = (String) next) == null || jSONObject2.opt(str2) == null)) {
                        try {
                            jSONObject3.put(str2, jSONObject2.opt(str2));
                        } catch (Exception unused2) {
                        }
                    }
                }
            }
            if (jSONObject3 == null || !DataHelper.largeThanMaxSize(jSONObject3.toString().getBytes().length, DataHelper.ENVELOPE_ENTITY_RAW_LENGTH_MAX)) {
                if (jSONObject3 != null) {
                    Envelope a2 = a(context, jSONObject3.toString().getBytes());
                    if (a2 == null) {
                        return a(111, jSONObject3);
                    }
                    envelope = a2;
                } else {
                    envelope = null;
                }
                if (envelope != null && DataHelper.largeThanMaxSize(envelope.toBinary().length, DataHelper.ENVELOPE_LENGTH_MAX)) {
                    return a(114, jSONObject3);
                }
                int a3 = a(context, envelope, "z==1.2.0", jSONObject3 != null ? jSONObject3.optJSONObject("header").optString("app_version") : null, str);
                if (a3 != 0) {
                    return a(a3, jSONObject3);
                }
                if (ULog.DEBUG) {
                    Log.i(c, "constructHeader size is " + jSONObject3.toString().getBytes().length);
                }
                return jSONObject3;
            }
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            if (sharedPreferences != null) {
                sharedPreferences.edit().putInt("serial", sharedPreferences.getInt("serial", 1) + 1).commit();
            }
            return a(113, jSONObject3);
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
            return a(110, new JSONObject());
        }
    }

    private static JSONObject b(Context context) {
        SharedPreferences sharedPreferences;
        JSONObject jSONObject;
        try {
            sharedPreferences = PreferenceWrapper.getDefault(context);
            if (!TextUtils.isEmpty(i)) {
                try {
                    jSONObject = new JSONObject(i);
                } catch (Exception unused) {
                    jSONObject = null;
                }
            } else {
                UMUtils.saveSDKComponent();
                jSONObject = new JSONObject();
                jSONObject.put(ai.p, DeviceConfig.getAppMD5Signature(context));
                jSONObject.put(ai.q, DeviceConfig.getAppSHA1Key(context));
                jSONObject.put(ai.r, DeviceConfig.getAppHashKey(context));
                jSONObject.put("app_version", DeviceConfig.getAppVersionName(context));
                jSONObject.put("version_code", Integer.parseInt(DeviceConfig.getAppVersionCode(context)));
                jSONObject.put(ai.v, DeviceConfig.getDeviceIdUmengMD5(context));
                jSONObject.put(ai.w, DeviceConfig.getCPU());
                String mccmnc = DeviceConfig.getMCCMNC(context);
                if (!TextUtils.isEmpty(mccmnc)) {
                    jSONObject.put(ai.B, mccmnc);
                    b = mccmnc;
                } else {
                    jSONObject.put(ai.B, "");
                }
                String subOSName = DeviceConfig.getSubOSName(context);
                if (!TextUtils.isEmpty(subOSName)) {
                    jSONObject.put(ai.K, subOSName);
                }
                String subOSVersion = DeviceConfig.getSubOSVersion(context);
                if (!TextUtils.isEmpty(subOSVersion)) {
                    jSONObject.put(ai.L, subOSVersion);
                }
                String deviceType = DeviceConfig.getDeviceType(context);
                if (!TextUtils.isEmpty(deviceType)) {
                    jSONObject.put(ai.ai, deviceType);
                }
                jSONObject.put("package_name", DeviceConfig.getPackageName(context));
                jSONObject.put(ai.u, "Android");
                jSONObject.put("device_id", DeviceConfig.getDeviceId(context));
                jSONObject.put("device_model", Build.MODEL);
                jSONObject.put(ai.E, Build.BOARD);
                jSONObject.put(ai.F, Build.BRAND);
                jSONObject.put(ai.G, Build.TIME);
                jSONObject.put(ai.H, Build.MANUFACTURER);
                jSONObject.put(ai.I, Build.ID);
                jSONObject.put(ai.J, Build.DEVICE);
                jSONObject.put(ai.y, Build.VERSION.RELEASE);
                jSONObject.put(ai.x, "Android");
                int[] resolutionArray = DeviceConfig.getResolutionArray(context);
                if (resolutionArray != null) {
                    jSONObject.put("resolution", resolutionArray[1] + "*" + resolutionArray[0]);
                }
                jSONObject.put(ai.A, DeviceConfig.getMac(context));
                jSONObject.put(ai.M, DeviceConfig.getTimeZone(context));
                String[] localeInfo = DeviceConfig.getLocaleInfo(context);
                jSONObject.put(ai.O, localeInfo[0]);
                jSONObject.put(ai.N, localeInfo[1]);
                jSONObject.put(ai.P, DeviceConfig.getNetworkOperatorName(context));
                jSONObject.put(ai.s, DeviceConfig.getAppName(context));
                String[] networkAccessMode = DeviceConfig.getNetworkAccessMode(context);
                if ("Wi-Fi".equals(networkAccessMode[0])) {
                    jSONObject.put(ai.Q, Network.NETWORK_TYPE_WIFI);
                } else if ("2G/3G".equals(networkAccessMode[0])) {
                    jSONObject.put(ai.Q, "2G/3G");
                } else {
                    jSONObject.put(ai.Q, "unknow");
                }
                if (!"".equals(networkAccessMode[1])) {
                    jSONObject.put(ai.R, networkAccessMode[1]);
                }
                if (FieldManager.allow(com.umeng.commonsdk.utils.b.H)) {
                    jSONObject.put(ai.S, DeviceConfig.getIPAddress(context));
                }
                jSONObject.put(ai.T, DeviceConfig.getNetworkType(context));
                jSONObject.put(ai.b, "9.3.8");
                jSONObject.put(ai.c, SdkVersion.SDK_TYPE);
                jSONObject.put(ai.d, "1");
                if (!TextUtils.isEmpty(a)) {
                    jSONObject.put(ai.e, a);
                }
                jSONObject.put(ai.aj, Build.VERSION.SDK_INT);
                if (!TextUtils.isEmpty(UMUtils.VALUE_REC_VERSION_NAME)) {
                    jSONObject.put(ai.af, UMUtils.VALUE_REC_VERSION_NAME);
                }
                try {
                    String uUIDForZid = UMUtils.getUUIDForZid(context);
                    if (TextUtils.isEmpty(uUIDForZid)) {
                        UMUtils.setUUIDForZid(context);
                        uUIDForZid = UMUtils.getUUIDForZid(context);
                    }
                    jSONObject.put("session_id", uUIDForZid);
                } catch (Throwable unused2) {
                }
                i = jSONObject.toString();
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
        if (jSONObject == null) {
            return null;
        }
        try {
            jSONObject.put(ai.ak, UMUtils.getOaidRequiredTime(context));
        } catch (Exception unused3) {
        }
        try {
            jSONObject.put(ai.U, sharedPreferences.getInt("successful_request", 0));
            jSONObject.put(ai.V, sharedPreferences.getInt(ai.V, 0));
            jSONObject.put(ai.W, sharedPreferences.getInt("last_request_spent_ms", 0));
            String zid = UMUtils.getZid(context);
            if (!TextUtils.isEmpty(zid)) {
                jSONObject.put(ai.al, zid);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_ASMS_VERSION)) {
                jSONObject.put(ai.am, UMUtils.VALUE_ASMS_VERSION);
            }
        } catch (Exception unused4) {
        }
        jSONObject.put("channel", UMUtils.getChannel(context));
        jSONObject.put("appkey", UMUtils.getAppkey(context));
        try {
            String deviceToken = UMUtils.getDeviceToken(context);
            if (!TextUtils.isEmpty(deviceToken)) {
                jSONObject.put(ai.a, deviceToken);
            }
        } catch (Exception e2) {
            UMCrashManager.reportCrash(context, e2);
        }
        try {
            String imprintProperty = UMEnvelopeBuild.imprintProperty(context, ai.g, null);
            if (!TextUtils.isEmpty(imprintProperty)) {
                jSONObject.put(ai.g, imprintProperty);
            }
        } catch (Exception e3) {
            UMCrashManager.reportCrash(context, e3);
        }
        try {
            jSONObject.put("wrapper_type", a.a);
            jSONObject.put("wrapper_version", a.b);
        } catch (Exception unused5) {
        }
        try {
            int targetSdkVersion = UMUtils.getTargetSdkVersion(context);
            boolean checkPermission = UMUtils.checkPermission(context, "android.permission.READ_PHONE_STATE");
            jSONObject.put(ai.aS, targetSdkVersion);
            if (checkPermission) {
                jSONObject.put(ai.aT, "yes");
            } else {
                jSONObject.put(ai.aT, "no");
            }
        } catch (Throwable unused6) {
        }
        try {
            if (b()) {
                jSONObject.put("umTaskId", g);
                jSONObject.put("umCaseId", h);
            }
        } catch (Throwable unused7) {
        }
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(ai.aX, a.e);
            if (!TextUtils.isEmpty(UMUtils.VALUE_ANALYTICS_VERSION)) {
                jSONObject2.put(ai.aY, UMUtils.VALUE_ANALYTICS_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_GAME_VERSION)) {
                jSONObject2.put(ai.aZ, UMUtils.VALUE_GAME_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_PUSH_VERSION)) {
                jSONObject2.put(ai.ba, UMUtils.VALUE_PUSH_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_SHARE_VERSION)) {
                jSONObject2.put(ai.bb, UMUtils.VALUE_SHARE_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_APM_VERSION)) {
                jSONObject2.put(ai.bc, UMUtils.VALUE_APM_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_VERIFY_VERSION)) {
                jSONObject2.put(ai.bd, UMUtils.VALUE_VERIFY_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_SMS_VERSION)) {
                jSONObject2.put(ai.be, UMUtils.VALUE_SMS_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_REC_VERSION_NAME)) {
                jSONObject2.put(ai.bf, UMUtils.VALUE_REC_VERSION_NAME);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_VISUAL_VERSION)) {
                jSONObject2.put(ai.bg, UMUtils.VALUE_VISUAL_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_ASMS_VERSION)) {
                jSONObject2.put(ai.bh, UMUtils.VALUE_ASMS_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_LINK_VERSION)) {
                jSONObject2.put(ai.bi, UMUtils.VALUE_LINK_VERSION);
            }
            if (!TextUtils.isEmpty(UMUtils.VALUE_ABTEST_VERSION)) {
                jSONObject2.put(ai.bj, UMUtils.VALUE_ABTEST_VERSION);
            }
            jSONObject.put(ai.aW, jSONObject2);
        } catch (Throwable unused8) {
        }
        try {
            String apmFlag = UMUtils.getApmFlag();
            if (!TextUtils.isEmpty(apmFlag)) {
                jSONObject.put(ai.bk, apmFlag);
            }
        } catch (Throwable unused9) {
        }
        byte[] a2 = ImprintHandler.getImprintService(context).a();
        if (a2 != null && a2.length > 0) {
            try {
                jSONObject.put(ai.X, Base64.encodeToString(a2, 0));
            } catch (JSONException e4) {
                UMCrashManager.reportCrash(context, e4);
            }
        }
        if (jSONObject != null && jSONObject.length() > 0) {
            return new JSONObject().put("header", jSONObject);
        }
        return null;
    }

    private JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) {
        String str;
        if (!(jSONObject == null || jSONObject2 == null || jSONObject.opt("header") == null || !(jSONObject.opt("header") instanceof JSONObject))) {
            JSONObject jSONObject3 = (JSONObject) jSONObject.opt("header");
            Iterator keys = jSONObject2.keys();
            while (keys.hasNext()) {
                Object next = keys.next();
                if (!(next == null || !(next instanceof String) || (str = (String) next) == null || jSONObject2.opt(str) == null)) {
                    try {
                        jSONObject3.put(str, jSONObject2.opt(str));
                        if (str.equals(c.i) && (jSONObject2.opt(str) instanceof Integer)) {
                            this.j = ((Integer) jSONObject2.opt(str)).intValue();
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        }
        return jSONObject;
    }

    private Envelope a(Context context, byte[] bArr) {
        String imprintProperty = UMEnvelopeBuild.imprintProperty(context, "codex", null);
        int i2 = -1;
        try {
            if (!TextUtils.isEmpty(imprintProperty)) {
                i2 = Integer.valueOf(imprintProperty).intValue();
            }
        } catch (NumberFormatException e2) {
            UMCrashManager.reportCrash(context, e2);
        }
        if (i2 == 0) {
            return Envelope.genEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        if (i2 == 1) {
            return Envelope.genEncryptEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        if (k) {
            return Envelope.genEncryptEnvelope(context, UMUtils.getAppkey(context), bArr);
        }
        return Envelope.genEnvelope(context, UMUtils.getAppkey(context), bArr);
    }

    private int a(Context context, Envelope envelope, String str, String str2, String str3) {
        if (context == null || envelope == null || TextUtils.isEmpty(str)) {
            return 101;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = DeviceConfig.getAppVersionName(context);
        }
        String b2 = d.b(str3);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("&&");
        sb.append(str2);
        sb.append("_");
        sb.append(System.currentTimeMillis());
        sb.append("_");
        sb.append(b2);
        sb.append(".log");
        byte[] binary = envelope.toBinary();
        if (str.startsWith(ai.aB) || str.startsWith(ai.aA) || str.startsWith(ai.at) || str.startsWith(ai.aF)) {
            return UMFrUtils.saveEnvelopeFile(context, sb.toString(), binary);
        }
        return d.a(context, com.umeng.commonsdk.stateless.a.f, sb.toString(), binary);
    }

    public static void a(boolean z) {
        k = z;
    }
}
