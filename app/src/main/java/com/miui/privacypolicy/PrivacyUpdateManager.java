package com.miui.privacypolicy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.miui.privacypolicy.NetUtils;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.StatConstants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PrivacyUpdateManager {
    private static final String REQUEST_URL = "https://data.sec.miui.com/privacy/get/v1";
    private static final String TAG = "Privacy_UpdateManager";

    private PrivacyUpdateManager() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String handlePrivacyUpdateTask(Context context, String str, String str2) {
        Context applicationContext = context.getApplicationContext();
        if (System.currentTimeMillis() - SharePreferenceUtils.getLong(applicationContext, str + "_privacy_update_time", 0L) < 86400000) {
            String readData = FileUtils.readData(context, "privacy_version", str);
            String readData2 = FileUtils.readData(context, "privacy_update", str);
            if (!TextUtils.isEmpty(readData2)) {
                try {
                    return compareVersion(readData, new JSONObject(readData2).optString("version")) ? readData2 : String.valueOf(-8);
                } catch (Exception e) {
                    Log.e(TAG, "handlePrivacyUpdateTask parse temp version error, ", e);
                }
            }
            return String.valueOf(-5);
        }
        Context applicationContext2 = context.getApplicationContext();
        SharePreferenceUtils.putLong(applicationContext2, str + "_privacy_update_time", System.currentTimeMillis());
        HashMap hashMap = new HashMap();
        if (TextUtils.isEmpty(str2)) {
            str2 = context.getPackageName();
        }
        hashMap.put(OneTrack.Param.PKG, str2);
        hashMap.put("policyName", str);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry entry : hashMap.entrySet()) {
                jSONObject.put((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (Exception e2) {
            Log.e(TAG, "build jsonObject error, ", e2);
        }
        String request = NetUtils.request(hashMap, REQUEST_URL, NetUtils.HttpMethod.POST, jSONObject);
        if (TextUtils.isEmpty(request)) {
            return String.valueOf(-2);
        }
        try {
            JSONObject jSONObject2 = new JSONObject(request);
            int optInt = jSONObject2.optInt("code");
            String optString = jSONObject2.optString("message");
            if (optInt == 200 && StatConstants.BIND_SUCCESS.equals(optString)) {
                String optString2 = jSONObject2.optString("data");
                String optString3 = new JSONObject(optString2).optString("version");
                if (!compareVersion(FileUtils.readData(context, "privacy_version", str), optString3)) {
                    return String.valueOf(-8);
                }
                FileUtils.saveData(optString2, context, "privacy_update", str);
                FileUtils.saveData(optString3, context, "privacy_temp_update_version", str);
                return optString2;
            }
        } catch (Exception e3) {
            Log.e(TAG, "handlePrivacyRevokeTask error, ", e3);
        }
        return String.valueOf(-3);
    }

    private static boolean compareVersion(String str, String str2) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || str.compareToIgnoreCase(str2) < 0;
    }
}
