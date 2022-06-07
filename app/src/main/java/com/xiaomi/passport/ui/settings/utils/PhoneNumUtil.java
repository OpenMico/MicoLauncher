package com.xiaomi.passport.ui.settings.utils;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.IOUtils;
import com.xiaomi.passport.ui.R;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class PhoneNumUtil {
    private static final String TAG = "PhoneNumUtil";
    private static String pattern = "(\\+)?\\d{1,20}";
    private static Pattern regex = Pattern.compile(pattern);
    private static Context sContext;
    private static HashMap<String, CountryPhoneNumData> sMapCountryPhoneData;
    private static HashMap<String, CountryPhoneNumData> sMapRecommendCountryPhoneData;

    public static void initializeCountryPhoneData(Context context) {
        if (sContext == null) {
            sContext = context.getApplicationContext();
        }
    }

    public static synchronized void ensureDataLoaded() {
        OutputStream outputStream;
        InputStream inputStream;
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        IOException e;
        JSONException e2;
        synchronized (PhoneNumUtil.class) {
            if (sMapCountryPhoneData == null || sMapRecommendCountryPhoneData == null) {
                sMapCountryPhoneData = new HashMap<>();
                sMapRecommendCountryPhoneData = new HashMap<>();
                try {
                    try {
                        inputStream = sContext.getResources().openRawResource(R.raw.passport_countries);
                        try {
                            byte[] bArr = new byte[512];
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            while (true) {
                                try {
                                    int read = inputStream.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    byteArrayOutputStream.write(bArr, 0, read);
                                } catch (IOException e3) {
                                    e = e3;
                                    AccountLog.e(TAG, "error when load area codes", e);
                                    IOUtils.closeQuietly(inputStream);
                                    IOUtils.closeQuietly(byteArrayOutputStream);
                                } catch (JSONException e4) {
                                    e2 = e4;
                                    AccountLog.e(TAG, "error when parse json", e2);
                                    IOUtils.closeQuietly(inputStream);
                                    IOUtils.closeQuietly(byteArrayOutputStream);
                                }
                            }
                            sMapCountryPhoneData = processCountryDataFromJson(new JSONObject(byteArrayOutputStream.toString()).getJSONArray("countries"));
                            IOUtils.closeQuietly(inputStream);
                        } catch (IOException e5) {
                            e = e5;
                            byteArrayOutputStream = null;
                        } catch (JSONException e6) {
                            e2 = e6;
                            byteArrayOutputStream = null;
                        } catch (Throwable th2) {
                            th = th2;
                            outputStream = null;
                            IOUtils.closeQuietly(inputStream);
                            IOUtils.closeQuietly(outputStream);
                            throw th;
                        }
                    } catch (IOException e7) {
                        e = e7;
                        byteArrayOutputStream = null;
                        inputStream = null;
                    } catch (JSONException e8) {
                        e2 = e8;
                        byteArrayOutputStream = null;
                        inputStream = null;
                    } catch (Throwable th3) {
                        th = th3;
                        outputStream = null;
                        inputStream = null;
                    }
                    IOUtils.closeQuietly(byteArrayOutputStream);
                } catch (Throwable th4) {
                    th = th4;
                }
            }
        }
    }

    public static HashMap<String, CountryPhoneNumData> processCountryDataFromJson(JSONArray jSONArray) throws JSONException {
        HashMap<String, CountryPhoneNumData> hashMap = new HashMap<>();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            String string = jSONObject.getString(ServerSetting.SERVER_CN);
            String string2 = jSONObject.getString("ic");
            String string3 = jSONObject.getString("iso");
            CountryPhoneNumData countryPhoneNumData = new CountryPhoneNumData(string, string2, string3);
            JSONArray optJSONArray = jSONObject.optJSONArray("len");
            if (optJSONArray != null) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                    arrayList.add(Integer.valueOf(optJSONArray.getInt(i2)));
                }
                countryPhoneNumData.lengths = arrayList;
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray(ai.A);
            if (optJSONArray2 != null) {
                ArrayList<String> arrayList2 = new ArrayList<>();
                for (int i3 = 0; i3 < optJSONArray2.length(); i3++) {
                    arrayList2.add(optJSONArray2.getString(i3));
                }
                countryPhoneNumData.prefix = arrayList2;
            }
            hashMap.put(string3, countryPhoneNumData);
        }
        return hashMap;
    }

    public static List<String> getCountryList() {
        ensureDataLoaded();
        ArrayList arrayList = new ArrayList(sMapCountryPhoneData.size());
        for (Map.Entry<String, CountryPhoneNumData> entry : sMapCountryPhoneData.entrySet()) {
            arrayList.add(entry.getValue().countryName);
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public static List<CountryPhoneNumData> getCountryPhoneNumDataList() {
        ensureDataLoaded();
        return getCountryPhoneNumDataListFromData(sMapCountryPhoneData);
    }

    public static List<CountryPhoneNumData> getRecommendCountryPhoneNumDataList() {
        ensureDataLoaded();
        return getCountryPhoneNumDataListFromData(sMapRecommendCountryPhoneData);
    }

    private static List<CountryPhoneNumData> getCountryPhoneNumDataListFromData(HashMap<String, CountryPhoneNumData> hashMap) {
        if (hashMap == null || hashMap.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(hashMap.size());
        for (CountryPhoneNumData countryPhoneNumData : hashMap.values()) {
            arrayList.add(countryPhoneNumData);
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public static String getCountryISOFromPhoneNum(String str) {
        if (!str.startsWith(Marker.ANY_NON_NULL_MARKER)) {
            return "CN";
        }
        ensureDataLoaded();
        for (Map.Entry<String, CountryPhoneNumData> entry : sMapCountryPhoneData.entrySet()) {
            if (str.startsWith(Marker.ANY_NON_NULL_MARKER + entry.getValue().countryCode)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static CountryPhoneNumData getCounrtyPhoneDataFromIso(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ensureDataLoaded();
        CountryPhoneNumData countryPhoneNumData = sMapRecommendCountryPhoneData.get(str.toUpperCase());
        return countryPhoneNumData != null ? countryPhoneNumData : sMapCountryPhoneData.get(str.toUpperCase());
    }

    public static String checkNumber(String str, CountryPhoneNumData countryPhoneNumData) {
        if (str == null || str.startsWith(Marker.ANY_NON_NULL_MARKER) || str.startsWith("00") || countryPhoneNumData == null || !regex.matcher(str).matches()) {
            AccountLog.e(TAG, "phoneNumber 为空或者是 data为空");
            return null;
        } else if (countryPhoneNumData.countryISO.equals("CN")) {
            return str;
        } else {
            return Marker.ANY_NON_NULL_MARKER + countryPhoneNumData.countryCode + str;
        }
    }

    public static String checkStrictNumber(String str, CountryPhoneNumData countryPhoneNumData) {
        boolean z;
        if (str == null || str.startsWith(Marker.ANY_NON_NULL_MARKER) || str.startsWith("00") || countryPhoneNumData == null || !regex.matcher(str).matches()) {
            AccountLog.e(TAG, "phoneNumber 为空或者是 data为空");
            return null;
        }
        boolean z2 = true;
        if (countryPhoneNumData.lengths != null) {
            Iterator<Integer> it = countryPhoneNumData.lengths.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (str.length() == it.next().intValue()) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return null;
            }
        }
        if (countryPhoneNumData.prefix != null) {
            Iterator<String> it2 = countryPhoneNumData.prefix.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z2 = false;
                    break;
                }
                String next = it2.next();
                if (next.startsWith("x")) {
                    int lastIndexOf = next.lastIndexOf("x");
                    if (lastIndexOf > str.length()) {
                        continue;
                    } else {
                        int i = lastIndexOf + 1;
                        if (str.substring(i).startsWith(next.substring(i))) {
                            break;
                        }
                    }
                } else if (str.startsWith(next)) {
                    break;
                }
            }
            if (!z2) {
                return null;
            }
        }
        if (countryPhoneNumData.countryISO.equals("CN")) {
            return str;
        }
        return Marker.ANY_NON_NULL_MARKER + countryPhoneNumData.countryCode + str;
    }

    /* loaded from: classes4.dex */
    public static class CountryPhoneNumData implements Comparable<CountryPhoneNumData> {
        public String countryCode;
        public String countryISO;
        public String countryName;
        ArrayList<Integer> lengths;
        ArrayList<String> prefix;

        public CountryPhoneNumData(String str, String str2, String str3) {
            this.countryName = str;
            this.countryCode = str2;
            this.countryISO = str3;
        }

        public int compareTo(CountryPhoneNumData countryPhoneNumData) {
            return this.countryName.compareTo(countryPhoneNumData.countryName);
        }
    }
}
