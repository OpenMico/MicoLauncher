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
public class PrivacyQueryManager {
    private static final String REQUEST_URL = "https://data.sec.miui.com/privacy/latestVersion";
    private static final String TAG = "Privacy_QueryManager";

    private PrivacyQueryManager() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int handlePrivacyQueryTask(Context context, String str, String str2) {
        if (System.currentTimeMillis() - SharePreferenceUtils.getLong(context, str + "_privacy_query_time", 0L) < 86400000) {
            return -5;
        }
        SharePreferenceUtils.putLong(context, str + "_privacy_query_time", System.currentTimeMillis());
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
            String optString2 = jSONObject2.optString("data");
            if (TextUtils.isEmpty(optString2)) {
                return -3;
            }
            FileUtils.saveData(optString2, context, "privacy_version", str);
            return 1;
        } catch (Exception e2) {
            Log.e(TAG, "handlePrivacyAgreeTask error, ", e2);
            return -3;
        }
    }
}
