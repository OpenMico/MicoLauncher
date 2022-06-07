package com.xiaomi.accountsdk.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.accountsdk.hasheddeviceidlib.HashedDeviceIdUtil;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RegionConfig {
    private static final long DEFAULT_SYNC_TIME_OUT = 10000;
    private static final String KEY_CHECK_SYNC_TIMEOUT = "check_timeout";
    private static final String KEY_DOWNLOAD_INTERVAL_TIME = "download_interval_time";
    private static final String KEY_LAST_DOWNLOAD_TIME = "last_download_time";
    private static final String KEY_REGION_JSON_STRING = "region_json";
    private static final String NAME_CLIENT_UPDATE_INTERVAL = "client.update.interval";
    private static final String NAME_REGION_CODES = "region.codes";
    private static final String NAME_REGISTER_CHECK_TIMEOUT = "register.check.timeout";
    private static final String NAME_REGISTER_DOMAIN = "register.domain";
    private static final long ONE_DAY_IN_MILLI = 86400000;
    private static final String SHARED_PREF_NAME = "region_config";
    private static final String SHARED_PREF_NAME_STAGING = "region_config_staging";
    private static final String TAG = "RegionConfig";
    private Context mAppContext;
    private SharedPreferences mSP;

    public RegionConfig(Context context) {
        this.mAppContext = context.getApplicationContext();
        this.mSP = this.mAppContext.getSharedPreferences(XMPassport.USE_PREVIEW ? SHARED_PREF_NAME_STAGING : SHARED_PREF_NAME, 0);
    }

    public Long blockingGetCheckSyncTimeout() {
        checkDownloadAndSave();
        return Long.valueOf(this.mSP.getLong(KEY_CHECK_SYNC_TIMEOUT, 10000L));
    }

    public String blockingGetRegHostName(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        checkDownloadAndSave();
        String string = this.mSP.getString(KEY_REGION_JSON_STRING, null);
        if (string == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(string);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                JSONObject jSONObject2 = jSONObject.getJSONObject((String) keys.next());
                if (contains(jSONObject2.optJSONArray(NAME_REGION_CODES), str.toString())) {
                    return jSONObject2.getString(NAME_REGISTER_DOMAIN);
                }
            }
        } catch (JSONException e) {
            AccountLog.e(TAG, "JSON ERROR", e);
        }
        return null;
    }

    private boolean contains(JSONArray jSONArray, String str) {
        if (jSONArray == null || TextUtils.isEmpty(str)) {
            return false;
        }
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            if (TextUtils.equals(jSONArray.optString(i), str)) {
                return true;
            }
        }
        return false;
    }

    private void checkDownloadAndSave() {
        long j = this.mSP.getLong(KEY_LAST_DOWNLOAD_TIME, 0L);
        if (Math.abs(System.currentTimeMillis() - j) < this.mSP.getLong(KEY_DOWNLOAD_INTERVAL_TIME, 86400000L)) {
            AccountLog.d(TAG, "not download twice within interval time");
            return;
        }
        try {
            saveRegionConfigJson(downloadRegionConfig());
        } catch (Exception e) {
            AccountLog.w(TAG, "download region config failed", e);
        }
    }

    private String downloadRegionConfig() throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException {
        EasyMap easyPut = new EasyMap().easyPut(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, new HashedDeviceIdUtil(this.mAppContext).getHashedDeviceIdNoThrow()).easyPut("_locale", XMPassportUtil.getISOLocaleString(Locale.getDefault()));
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.URL_ACCOUNT_BASE + "/regionConfig", null, easyPut, true);
        if (asString != null) {
            String removeSafePrefixAndGetRealBody = XMPassport.removeSafePrefixAndGetRealBody(asString);
            try {
                JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody);
                if (jSONObject.getInt("code") == 0) {
                    return jSONObject.getString("data");
                }
                throw new InvalidResponseException(removeSafePrefixAndGetRealBody.toString());
            } catch (JSONException e) {
                AccountLog.e(TAG, "JSON ERROR", e);
                throw new InvalidResponseException(e.getMessage());
            }
        } else {
            throw new InvalidResponseException("result content is null");
        }
    }

    private void saveRegionConfigJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("Config");
            this.mSP.edit().putLong(KEY_LAST_DOWNLOAD_TIME, System.currentTimeMillis()).putLong(KEY_DOWNLOAD_INTERVAL_TIME, jSONObject.getLong(NAME_CLIENT_UPDATE_INTERVAL) * 1000).putLong(KEY_CHECK_SYNC_TIMEOUT, jSONObject.getLong(NAME_REGISTER_CHECK_TIMEOUT) * 1000).putString(KEY_REGION_JSON_STRING, str).commit();
        } catch (JSONException e) {
            AccountLog.e(TAG, "JSON ERROR", e);
        }
    }
}
