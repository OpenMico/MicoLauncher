package com.xiaomi.passport.ui.internal;

import android.content.Context;
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
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PhoneNumUtil {
    private static final String TAG = "PhoneNumUtil";
    private static String pattern = "(\\+)?\\d{1,20}";
    private static Pattern regex = Pattern.compile(pattern);
    private static HashMap<String, CountryPhoneNumData> sMapCountryPhoneData;
    private static HashMap<String, CountryPhoneNumData> sMapRecommendCountryPhoneData;

    private static boolean isCNLanguage() {
        return Locale.getDefault().getLanguage().equals(Locale.CHINESE.getLanguage());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v12, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v8 */
    private static synchronized void ensureDataLoaded(Context context) {
        OutputStream outputStream;
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
                        context = context.getResources().openRawResource(isCNLanguage() ? R.raw.passport_countries_cn : R.raw.passport_countries);
                        try {
                            byte[] bArr = new byte[512];
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            while (true) {
                                try {
                                    int read = context.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    byteArrayOutputStream.write(bArr, 0, read);
                                } catch (IOException e3) {
                                    e = e3;
                                    AccountLog.e(TAG, "error when load area codes", e);
                                    IOUtils.closeQuietly((InputStream) context);
                                    IOUtils.closeQuietly(byteArrayOutputStream);
                                } catch (JSONException e4) {
                                    e2 = e4;
                                    AccountLog.e(TAG, "error when parse json", e2);
                                    IOUtils.closeQuietly((InputStream) context);
                                    IOUtils.closeQuietly(byteArrayOutputStream);
                                }
                            }
                            JSONObject jSONObject = new JSONObject(byteArrayOutputStream.toString());
                            sMapCountryPhoneData = processCountryDataFromJson(jSONObject.getJSONArray("countries"));
                            sMapRecommendCountryPhoneData = processCountryDataFromJson(jSONObject.getJSONArray("recommend countries"));
                            IOUtils.closeQuietly((InputStream) context);
                        } catch (IOException e5) {
                            e = e5;
                            byteArrayOutputStream = null;
                        } catch (JSONException e6) {
                            e2 = e6;
                            byteArrayOutputStream = null;
                        } catch (Throwable th2) {
                            th = th2;
                            outputStream = null;
                            IOUtils.closeQuietly((InputStream) context);
                            IOUtils.closeQuietly(outputStream);
                            throw th;
                        }
                    } catch (IOException e7) {
                        e = e7;
                        byteArrayOutputStream = null;
                        context = 0;
                    } catch (JSONException e8) {
                        e2 = e8;
                        byteArrayOutputStream = null;
                        context = 0;
                    } catch (Throwable th3) {
                        th = th3;
                        outputStream = null;
                        context = 0;
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

    public static List<CountryPhoneNumData> getCountryPhoneNumDataList(Context context) {
        ensureDataLoaded(context);
        return getCountryPhoneNumDataListFromData(sMapCountryPhoneData);
    }

    public static List<CountryPhoneNumData> getRecommendCountryPhoneNumDataList(Context context) {
        ensureDataLoaded(context);
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

    /* loaded from: classes4.dex */
    public static class CountryPhoneNumData implements Comparable<CountryPhoneNumData> {
        public String countryCode;
        public String countryISO;
        public String countryName;
        ArrayList<Integer> lengths;
        ArrayList<String> prefix;

        CountryPhoneNumData(String str, String str2, String str3) {
            this.countryName = str;
            this.countryCode = str2;
            this.countryISO = str3;
        }

        public int compareTo(CountryPhoneNumData countryPhoneNumData) {
            return this.countryName.compareTo(countryPhoneNumData.countryName);
        }
    }
}
