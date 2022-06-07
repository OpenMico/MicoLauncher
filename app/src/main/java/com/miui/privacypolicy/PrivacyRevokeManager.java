package com.miui.privacypolicy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.miui.privacypolicy.NetUtils;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.StatConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PrivacyRevokeManager {
    private static final String REQUEST_URL = "https://data.sec.miui.com/privacy/revoke/v1";
    private static final String TAG = "Privacy_RevokeManager";

    private PrivacyRevokeManager() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int handlePrivacyRevokeTask(Context context, String str, String str2, String str3, String str4) {
        HashMap hashMap = new HashMap();
        if (TextUtils.isEmpty(str3)) {
            str3 = context.getPackageName();
        }
        hashMap.put(OneTrack.Param.PKG, str3);
        hashMap.put("policyName", str);
        hashMap.put("idContent", str2);
        hashMap.put("idStatus", "1");
        hashMap.put("miuiVersion", NetUtils.MIUI_VERSION_NAME);
        if (TextUtils.isEmpty(str4)) {
            str4 = PackageUtils.getAppVersionName(context);
        }
        hashMap.put("apkVersion", str4);
        String readData = FileUtils.readData(context, "privacy_version", str);
        if (TextUtils.isEmpty(readData)) {
            readData = "";
        }
        hashMap.put("policyVersion", readData);
        hashMap.put(ai.N, Locale.getDefault().getLanguage());
        hashMap.put("region", Locale.getDefault().getCountry());
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry entry : hashMap.entrySet()) {
                jSONObject.put((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (Exception e) {
            Log.e(TAG, "build jsonObject error, ", e);
        }
        String request = NetUtils.request(hashMap, REQUEST_URL, NetUtils.HttpMethod.POST, jSONObject);
        if (TextUtils.isEmpty(request)) {
            return -2;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(request);
            int optInt = jSONObject2.optInt("code");
            String optString = jSONObject2.optString("message");
            if (optInt != 200 || !StatConstants.BIND_SUCCESS.equals(optString)) {
                return -3;
            }
            FileUtils.deleteFile(context, "privacy_version", str);
            FileUtils.deleteFile(context, "privacy_update", str);
            FileUtils.deleteFile(context, "privacy_temp_update_version", str);
            FileUtils.deleteFile(context, "privacy_agree_error", str);
            SharePreferenceUtils.clear(context);
            return 1;
        } catch (Exception e2) {
            Log.e(TAG, "handlePrivacyRevokeTask error, ", e2);
            return -3;
        }
    }
}
