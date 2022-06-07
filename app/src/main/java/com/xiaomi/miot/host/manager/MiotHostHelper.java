package com.xiaomi.miot.host.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Pair;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.miot.host.manager.utils.Base64Coder;
import com.xiaomi.miot.host.manager.utils.Coder;
import com.xiaomi.miot.host.manager.utils.SystemApi;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Typography;
import org.hapjs.features.channel.IChannel;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotHostHelper {
    private static final String GET_BINDKEY_PATH = "/user/get_bindkey";
    private static final String MIOT_API_HOST = "https://api.io.mi.com/app";
    public static final String SID_XIAOMI_IO = "xiaomiio";

    /* loaded from: classes2.dex */
    public interface AppBindKeyCallback {
        void onFailed(String str);

        void onSuccess(String str);
    }

    private static boolean isValidOkHttpHeaderValue(char c) {
        return c > 31 && c < 127;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Pair<Long, Boolean> getServerTimeSync() {
        try {
            URLConnection openConnection = new URL(MIOT_API_HOST).openConnection();
            openConnection.setConnectTimeout(10000);
            try {
                openConnection.connect();
                return new Pair<>(Long.valueOf(openConnection.getDate() - System.currentTimeMillis()), true);
            } catch (Exception e) {
                e.printStackTrace();
                return new Pair<>(0L, false);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return new Pair<>(0L, false);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.xiaomi.miot.host.manager.MiotHostHelper$1] */
    public static void getAppBindKey(final Context context, final String str, final String str2, final String str3, final AppBindKeyCallback appBindKeyCallback) {
        if (appBindKeyCallback != null) {
            if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                appBindKeyCallback.onFailed("params is null");
            } else {
                new AsyncTask<String, Void, BindKeyResult>() { // from class: com.xiaomi.miot.host.manager.MiotHostHelper.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    public BindKeyResult doInBackground(String... strArr) {
                        BindKeyResult bindKeyResult = new BindKeyResult();
                        Pair serverTimeSync = MiotHostHelper.getServerTimeSync();
                        if (!((Boolean) serverTimeSync.second).booleanValue()) {
                            bindKeyResult.errorInfo = "getServerTimeSync failed";
                            return bindKeyResult;
                        }
                        try {
                            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://api.io.mi.com/app/user/get_bindkey").openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoInput(true);
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setUseCaches(false);
                            httpURLConnection.setConnectTimeout(10000);
                            httpURLConnection.setReadTimeout(10000);
                            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            httpURLConnection.setRequestProperty("User-agent", MiotHostHelper.getUserAgent(context));
                            httpURLConnection.setRequestProperty("Cookie", MiotHostHelper.getRequestCookies(str, str3));
                            httpURLConnection.setRequestProperty("X-XIAOMI-PROTOCAL-FLAG-CLI", "PROTOCAL-HTTP2");
                            httpURLConnection.connect();
                            String genRequestBody = MiotHostHelper.genRequestBody(MiotHostHelper.GET_BINDKEY_PATH, str2, ((Long) serverTimeSync.first).longValue(), new ArrayList());
                            if (TextUtils.isEmpty(genRequestBody)) {
                                bindKeyResult.errorInfo = "genRequestBody failed";
                                return bindKeyResult;
                            }
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                            bufferedWriter.write(genRequestBody);
                            bufferedWriter.close();
                            if (httpURLConnection.getResponseCode() == 200) {
                                String str4 = "";
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                while (true) {
                                    String readLine = bufferedReader.readLine();
                                    if (readLine != null) {
                                        str4 = str4 + readLine;
                                    } else {
                                        try {
                                            break;
                                        } catch (JSONException unused) {
                                            bindKeyResult.errorInfo = "parse result failed";
                                        }
                                    }
                                }
                                JSONObject jSONObject = new JSONObject(str4);
                                int i = jSONObject.getInt("code");
                                if (i == 0) {
                                    JSONObject optJSONObject = jSONObject.optJSONObject("result");
                                    if (optJSONObject != null) {
                                        String optString = optJSONObject.optString("bindkey");
                                        if (TextUtils.isEmpty(optString)) {
                                            bindKeyResult.errorInfo = "parse bindkey is null";
                                        } else {
                                            bindKeyResult.bindKey = optString;
                                        }
                                    } else {
                                        bindKeyResult.errorInfo = "result is null";
                                    }
                                } else {
                                    bindKeyResult.errorInfo = "result error: " + i;
                                }
                            } else {
                                bindKeyResult.errorInfo = "response error code: " + httpURLConnection.getResponseCode();
                            }
                            httpURLConnection.disconnect();
                            return bindKeyResult;
                        } catch (Exception e) {
                            e.printStackTrace();
                            bindKeyResult.errorInfo = "request bindkey failed";
                            return bindKeyResult;
                        }
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    public void onPostExecute(BindKeyResult bindKeyResult) {
                        if (bindKeyResult == null) {
                            appBindKeyCallback.onFailed("get bindkey failed");
                        } else if (!TextUtils.isEmpty(bindKeyResult.bindKey)) {
                            appBindKeyCallback.onSuccess(bindKeyResult.bindKey);
                        } else {
                            appBindKeyCallback.onFailed(bindKeyResult.errorInfo);
                        }
                    }
                }.execute(new String[0]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String genRequestBody(String str, String str2, long j, List<KeyValuePair> list) {
        String str3;
        String generateNonceTime = generateNonceTime(System.currentTimeMillis() + j);
        try {
            str3 = Coder.encryptBASE64(Coder.sha256Hash(byteArraysConcat(Coder.decryptBASE64(str2), Coder.decryptBASE64(generateNonceTime))));
        } catch (Exception e) {
            e.printStackTrace();
            str3 = null;
        }
        if (str3 == null) {
            return null;
        }
        TreeMap treeMap = new TreeMap();
        ArrayList<KeyValuePair> arrayList = new ArrayList();
        if (list != null) {
            for (KeyValuePair keyValuePair : list) {
                if (!TextUtils.isEmpty(keyValuePair.getKey()) && !TextUtils.isEmpty(keyValuePair.getValue())) {
                    treeMap.put(keyValuePair.getKey(), keyValuePair.getValue());
                }
            }
        }
        ArrayList<String> arrayList2 = new ArrayList();
        if (str != null) {
            arrayList2.add(str);
        }
        arrayList2.add(str3);
        arrayList2.add(generateNonceTime);
        boolean z = true;
        if (!treeMap.isEmpty()) {
            for (Map.Entry entry : new TreeMap((SortedMap) treeMap).entrySet()) {
                arrayList2.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }
        } else {
            arrayList2.add("data=");
        }
        StringBuilder sb = new StringBuilder();
        boolean z2 = true;
        for (String str4 : arrayList2) {
            if (!z2) {
                sb.append(Typography.amp);
            }
            sb.append(str4);
            z2 = false;
        }
        arrayList.add(new KeyValuePair(IChannel.EXTRA_OPEN_SIGNATURE, sha256_HMAC(sb.toString(), str3)));
        arrayList.add(new KeyValuePair("_nonce", generateNonceTime));
        arrayList.addAll(list);
        StringBuilder sb2 = new StringBuilder();
        for (KeyValuePair keyValuePair2 : arrayList) {
            if (z) {
                z = false;
            } else {
                sb2.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            }
            try {
                sb2.append(URLEncoder.encode(keyValuePair2.getKey(), "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(keyValuePair2.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return sb2.toString();
    }

    private static byte[] byteArraysConcat(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getUserAgent(Context context) {
        return sanitizeUA(SystemApi.getInstance().getOsName().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "") + Constants.ACCEPT_TIME_SEPARATOR_SERVER + SystemApi.getInstance().getOsVersion().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "") + Constants.ACCEPT_TIME_SEPARATOR_SERVER + SystemApi.getInstance().getAppVersion(context).replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "") + Constants.ACCEPT_TIME_SEPARATOR_SERVER + SystemApi.getInstance().getDeviceBrand().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "") + Constants.ACCEPT_TIME_SEPARATOR_SERVER + SystemApi.getInstance().getDeviceModel().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "") + Constants.ACCEPT_TIME_SEPARATOR_SERVER + SystemApi.getInstance().getOsIncremental().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "") + "--android-ot-sdk-4.3.4");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getRequestCookies(String str, String str2) {
        return "userId=" + str + ";serviceToken=" + str2;
    }

    private static String sanitizeUA(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (checkValid(str)) {
            return str;
        }
        try {
            StringBuilder sb = new StringBuilder(str.length());
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (!isValidOkHttpHeaderValue(charAt)) {
                    sb.append(URLEncoder.encode(charAt + "", "UTF-8"));
                } else {
                    sb.append(charAt);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private static boolean checkValid(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isValidOkHttpHeaderValue(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String generateNonceTime(long j) {
        SecureRandom secureRandom = new SecureRandom();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeLong(secureRandom.nextLong());
            dataOutputStream.writeInt((int) (j / 60000));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(Base64Coder.encode(byteArrayOutputStream.toByteArray()));
    }

    private static String sha256_HMAC(String str, String str2) {
        try {
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(new SecretKeySpec(Coder.decryptBASE64(str2), "HmacSHA256"));
            return Coder.encryptBASE64(instance.doFinal(str.getBytes("UTF-8")));
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class KeyValuePair {
        private final String key;
        private final String value;

        public KeyValuePair(String str, String str2) {
            if (str != null) {
                this.key = str;
                this.value = str2;
                return;
            }
            throw new IllegalArgumentException("Key may not be null");
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        public String toString() {
            return this.key + Constants.COLON_SEPARATOR + this.value;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class BindKeyResult {
        String bindKey;
        String errorInfo;

        private BindKeyResult() {
        }
    }
}
