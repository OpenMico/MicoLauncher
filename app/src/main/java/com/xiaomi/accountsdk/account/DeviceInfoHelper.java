package com.xiaomi.accountsdk.account;

import android.text.TextUtils;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.DevInfoKeys;
import com.xiaomi.accountsdk.account.data.DeviceModelInfo;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.data.PhoneInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.ObjectUtils;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.onetrack.api.b;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DeviceInfoHelper {
    private static final Integer INT_0 = 0;
    private static final int RESULT_CODE_SUCCESS = 0;
    private static final String TAG = "DeviceInfoHelper";

    public static HashMap<String, Object> getDeviceInfo(PassportInfo passportInfo, String str, List<String> list) throws IOException, AccessDeniedException, CipherException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("null passportInfo");
        } else if (list == null || list.size() == 0) {
            return null;
        } else {
            JSONArray jSONArray = new JSONArray();
            for (String str2 : list) {
                jSONArray.put(str2.toString());
            }
            SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(URLs.URL_DEV_SETTING, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut(DevInfoKeys.DEVICEID, str).easyPut("meta", jSONArray.toString()), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (asMap != null) {
                Object fromBody = asMap.getFromBody("code");
                AccountLog.w(TAG, "getDeviceInfo code : " + fromBody);
                if (INT_0.equals(fromBody)) {
                    Object fromBody2 = asMap.getFromBody("data");
                    if (fromBody2 instanceof Map) {
                        Object obj = ((Map) fromBody2).get("settings");
                        if (obj instanceof ArrayList) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            Iterator it = ((ArrayList) obj).iterator();
                            while (it.hasNext()) {
                                HashMap hashMap2 = (HashMap) it.next();
                                hashMap.put((String) hashMap2.get("name"), hashMap2.get(b.p));
                            }
                            processDevicesSettingResult(hashMap);
                            return hashMap;
                        }
                    }
                }
                throw new InvalidResponseException("failed to get device info : " + asMap.toString());
            }
            throw new IOException("failed to get device info");
        }
    }

    public static ArrayList<HashMap<String, Object>> getAllDevicesInfo(PassportInfo passportInfo, ArrayList<String> arrayList) throws IOException, AccessDeniedException, AuthenticationFailureException, CipherException, InvalidResponseException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("null passportInfo");
        } else if (arrayList == null) {
            return null;
        } else {
            JSONArray jSONArray = new JSONArray();
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().toString());
            }
            SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(URLs.URL_DEVICES_SETTING, new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("meta", jSONArray.toString()), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
            if (asMap != null) {
                Object fromBody = asMap.getFromBody("code");
                AccountLog.w(TAG, "getDeviceList code : " + fromBody);
                if (INT_0.equals(fromBody)) {
                    Object fromBody2 = asMap.getFromBody("data");
                    if (fromBody2 instanceof Map) {
                        Object obj = ((Map) fromBody2).get("all_device_settings");
                        if (obj instanceof ArrayList) {
                            ArrayList<HashMap<String, Object>> arrayList2 = (ArrayList) obj;
                            Iterator<HashMap<String, Object>> it2 = arrayList2.iterator();
                            while (it2.hasNext()) {
                                processDevicesSettingResult(it2.next());
                            }
                            return arrayList2;
                        }
                    }
                }
                return null;
            }
            throw new IOException("failed to get devices list");
        }
    }

    private static void processDevicesSettingResult(HashMap<String, Object> hashMap) {
        if (hashMap != null) {
            if (hashMap.get(DevInfoKeys.PHONE_INFO) != null) {
                hashMap.put(DevInfoKeys.PHONE_INFO, processDevicePhoneInfoContent(hashMap.get(DevInfoKeys.PHONE_INFO).toString()));
            }
            if (hashMap.get("model") != null && hashMap.get(DevInfoKeys.MODEL_INFO) != null) {
                hashMap.put(DevInfoKeys.MODEL_INFO, processDeviceModelInfoContent(hashMap.get("model").toString(), hashMap.get(DevInfoKeys.MODEL_INFO).toString()));
            }
        }
    }

    private static ArrayList<PhoneInfo> processDevicePhoneInfoContent(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList<PhoneInfo> arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject optJSONObject = jSONArray.optJSONObject(i);
                arrayList.add(new PhoneInfo(optJSONObject.optString("simId"), optJSONObject.optString("phone")));
            }
        } catch (JSONException e) {
            AccountLog.e(TAG, "setPhoneInfo", e);
        }
        return arrayList;
    }

    private static DeviceModelInfo processDeviceModelInfoContent(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        DeviceModelInfo deviceModelInfo = new DeviceModelInfo(str);
        try {
            JSONObject jSONObject = new JSONObject(str2);
            deviceModelInfo.setImageUrl(jSONObject.optString("fullImageUrl"));
            JSONObject optJSONObject = jSONObject.optJSONObject(XMPassportUtil.getISOLocaleString(Locale.getDefault()));
            if (optJSONObject == null) {
                optJSONObject = jSONObject.optJSONObject("default");
            }
            if (optJSONObject != null) {
                deviceModelInfo.setModelName(optJSONObject.optString("modelName"));
                deviceModelInfo.setDefaultDeviceName(optJSONObject.optString("deviceName"));
            }
        } catch (JSONException e) {
            AccountLog.w(TAG, e);
        }
        return deviceModelInfo;
    }

    @Deprecated
    public static boolean uploadDeviceInfo(String str, String str2, String str3, String str4, String str5, Map<String, Object> map) throws IOException, AccessDeniedException, InvalidResponseException, CipherException, AuthenticationFailureException {
        return uploadDeviceInfo(new PassportInfo(str, str2, null, str3, str4), str5, map);
    }

    public static boolean uploadDeviceInfo(PassportInfo passportInfo, String str, Map<String, Object> map) throws IOException, AccessDeniedException, InvalidResponseException, CipherException, AuthenticationFailureException {
        if (passportInfo == null || map == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        JSONArray convertDevSettingValues = convertDevSettingValues(map);
        HashMap hashMap = new HashMap();
        hashMap.put(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId());
        hashMap.put(DevInfoKeys.DEVICEID, str);
        hashMap.put("content", convertDevSettingValues.toString());
        SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URLs.URL_DEV_SETTING, hashMap, getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (postAsMap != null) {
            Object fromBody = postAsMap.getFromBody("code");
            postAsMap.getFromBody("description");
            if (INT_0.equals(fromBody)) {
                return true;
            }
            AccountLog.d(TAG, "failed upload dev name, code: " + fromBody);
            return false;
        }
        throw new IOException("failed to upload device settings info");
    }

    private static JSONArray convertDevSettingValues(Map<String, Object> map) {
        JSONArray jSONArray = new JSONArray();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object convertObjectToJson = ObjectUtils.convertObjectToJson(entry.getValue());
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("name", key);
                jSONObject.put(b.p, convertObjectToJson);
            } catch (JSONException e) {
                AccountLog.e(TAG, "convertDevSettingValues", e);
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static HashMap<String, DeviceModelInfo> getDeviceModelInfos(ArrayList<String> arrayList) throws AccessDeniedException, AuthenticationFailureException, IOException, InvalidResponseException {
        if (arrayList != null) {
            HashMap<String, DeviceModelInfo> hashMap = new HashMap<>();
            if (arrayList.size() == 0) {
                return hashMap;
            }
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.URL_GET_DEVICE_MODEL_INFOS, new EasyMap().easyPut("models", new JSONArray((Collection) arrayList).toString()), null, true);
            if (asString != null) {
                try {
                    JSONObject jSONObject = new JSONObject(asString.getBody());
                    if (jSONObject.getInt("code") == 0) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        String iSOLocaleString = XMPassportUtil.getISOLocaleString(Locale.getDefault());
                        for (int i = 0; i < arrayList.size(); i++) {
                            String str = arrayList.get(i);
                            JSONObject optJSONObject = jSONObject2.optJSONObject(str);
                            DeviceModelInfo deviceModelInfo = new DeviceModelInfo(str);
                            deviceModelInfo.setModelName(str);
                            if (optJSONObject != null) {
                                deviceModelInfo.setImageUrl(optJSONObject.optString("fullImageUrl"));
                                JSONObject optJSONObject2 = optJSONObject.optJSONObject(iSOLocaleString);
                                if (optJSONObject2 == null) {
                                    optJSONObject2 = optJSONObject.optJSONObject("default");
                                }
                                if (optJSONObject2 != null) {
                                    deviceModelInfo.setDefaultDeviceName(optJSONObject2.optString("deviceName"));
                                    deviceModelInfo.setModelName(optJSONObject2.optString("modelName"));
                                }
                            }
                            hashMap.put(str, deviceModelInfo);
                        }
                    }
                    return hashMap;
                } catch (JSONException e) {
                    AccountLog.w(TAG, "fail to parse JSONObject", e);
                    throw new InvalidResponseException(asString.toString());
                }
            } else {
                throw new InvalidResponseException("failed to getModelInfos");
            }
        } else {
            throw new IllegalArgumentException("invalid parameter");
        }
    }

    private static EasyMap<String, String> getPassportCookie(PassportInfo passportInfo) {
        if (passportInfo != null) {
            EasyMap<String, String> easyPut = new EasyMap().easyPut(AuthorizeActivityBase.KEY_SERVICETOKEN, passportInfo.getServiceToken());
            if (TextUtils.isEmpty(passportInfo.getEncryptedUserId())) {
                easyPut.easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId());
            } else {
                easyPut.easyPut("cUserId", passportInfo.getEncryptedUserId());
            }
            return easyPut;
        }
        throw new IllegalArgumentException("passportInfo is null");
    }
}
