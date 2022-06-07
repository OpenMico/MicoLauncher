package com.miui.privacypolicy;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PrivacyManager {
    protected static final boolean DEBUG = false;
    private static final String TAG = "Privacy_PrivacyManager";

    private PrivacyManager() {
    }

    public static synchronized int privacyAgree(Context context, String str, String str2) {
        synchronized (PrivacyManager.class) {
            checkThreadPermission("can not request privacy agree in main thread!");
            if (NetUtils.IS_INTERNATIONAL_BUILD) {
                return -4;
            }
            return PrivacyAgreeManager.handlePrivacyAgreeTask(context.getApplicationContext(), str, str2, String.valueOf(System.currentTimeMillis()), null, null);
        }
    }

    public static synchronized int privacyAgree(Context context, String str, String str2, String str3, String str4) {
        synchronized (PrivacyManager.class) {
            checkThreadPermission("can not request privacy agree in main thread!");
            if (NetUtils.IS_INTERNATIONAL_BUILD) {
                return -4;
            }
            return PrivacyAgreeManager.handlePrivacyAgreeTask(context.getApplicationContext(), str, str2, String.valueOf(System.currentTimeMillis()), str3, str4);
        }
    }

    public static synchronized int privacyRevoke(Context context, String str, String str2) {
        synchronized (PrivacyManager.class) {
            checkThreadPermission("can not request privacy revoke in main thread!");
            if (NetUtils.IS_INTERNATIONAL_BUILD) {
                return -4;
            }
            return PrivacyRevokeManager.handlePrivacyRevokeTask(context.getApplicationContext(), str, str2, null, null);
        }
    }

    public static synchronized int privacyRevoke(Context context, String str, String str2, String str3, String str4) {
        synchronized (PrivacyManager.class) {
            checkThreadPermission("can not request privacy revoke in main thread!");
            if (NetUtils.IS_INTERNATIONAL_BUILD) {
                return -4;
            }
            return PrivacyRevokeManager.handlePrivacyRevokeTask(context.getApplicationContext(), str, str2, str3, str4);
        }
    }

    public static synchronized String requestPrivacyUpdate(Context context, String str, String str2) {
        synchronized (PrivacyManager.class) {
            checkThreadPermission("can not request privacy update in main thread!");
            if (NetUtils.IS_INTERNATIONAL_BUILD) {
                return String.valueOf(-4);
            }
            return requestPrivacyUpdate(context, str, str2, null, null);
        }
    }

    public static synchronized String requestPrivacyUpdate(Context context, String str, String str2, String str3, String str4) {
        String str5;
        synchronized (PrivacyManager.class) {
            checkThreadPermission("can not request privacy update in main thread!");
            if (NetUtils.IS_INTERNATIONAL_BUILD) {
                return String.valueOf(-4);
            } else if (!FileUtils.isVersionFileExit(context.getApplicationContext(), str)) {
                int handlePrivacyQueryTask = PrivacyQueryManager.handlePrivacyQueryTask(context.getApplicationContext(), str, str3);
                if (handlePrivacyQueryTask == 1) {
                    handlePrivacyQueryTask = -7;
                }
                return String.valueOf(handlePrivacyQueryTask);
            } else if (FileUtils.isAgreeErrorFileExit(context.getApplicationContext(), str)) {
                Context applicationContext = context.getApplicationContext();
                if (System.currentTimeMillis() - SharePreferenceUtils.getLong(applicationContext, str + "_privacy_update_time", 0L) < 86400000) {
                    return String.valueOf(-5);
                }
                Context applicationContext2 = context.getApplicationContext();
                SharePreferenceUtils.putLong(applicationContext2, str + "_privacy_update_time", System.currentTimeMillis());
                String valueOf = String.valueOf(System.currentTimeMillis());
                String readData = FileUtils.readData(context.getApplicationContext(), "privacy_agree_error", str);
                if (!TextUtils.isEmpty(readData)) {
                    try {
                        JSONObject jSONObject = new JSONObject(readData);
                        str2 = jSONObject.optString("idContent", str2);
                        str = jSONObject.optString("policyName", str);
                        String optString = jSONObject.optString("timestamp", valueOf);
                        str = str;
                        str2 = str2;
                        str5 = optString;
                    } catch (Exception e) {
                        Log.e(TAG, "parse last jsonObject error, ", e);
                        str5 = valueOf;
                    }
                } else {
                    str = str;
                    str2 = str2;
                    str5 = valueOf;
                }
                int handlePrivacyAgreeTask = PrivacyAgreeManager.handlePrivacyAgreeTask(context.getApplicationContext(), str, str2, str5, str3, str4);
                if (handlePrivacyAgreeTask == 1) {
                    handlePrivacyAgreeTask = -6;
                }
                return String.valueOf(handlePrivacyAgreeTask);
            } else {
                return PrivacyUpdateManager.handlePrivacyUpdateTask(context.getApplicationContext(), str, str3);
            }
        }
    }

    private static void checkThreadPermission(String str) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w(TAG, str);
            throw new IllegalStateException(str);
        }
    }
}
