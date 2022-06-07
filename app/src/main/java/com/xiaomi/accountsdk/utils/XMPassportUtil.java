package com.xiaomi.accountsdk.utils;

import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class XMPassportUtil {
    private static final String KEY_LOCALE = "_locale";

    public static String getISOLocaleString(Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return TextUtils.isEmpty(country) ? language : String.format("%s_%s", language, country);
    }

    public static Map<String, String> getDefaultLocaleParam() {
        HashMap hashMap = new HashMap();
        hashMap.put(KEY_LOCALE, getISOLocaleString(Locale.getDefault()));
        return hashMap;
    }

    public static String buildUrlWithLocaleQueryParam(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Uri parse = Uri.parse(str);
        String queryParameter = parse.getQueryParameter(KEY_LOCALE);
        Uri.Builder buildUpon = parse.buildUpon();
        String iSOLocaleString = getISOLocaleString(Locale.getDefault());
        if (TextUtils.isEmpty(queryParameter) && !TextUtils.isEmpty(iSOLocaleString)) {
            buildUpon.appendQueryParameter(KEY_LOCALE, iSOLocaleString);
        }
        return buildUpon.build().toString();
    }

    public static String extractPasstokenFromNotificationLoginEndCookie(String str) {
        return extractFromCookieString(str, BaseConstants.EXTRA_PASSTOKEN);
    }

    public static String extractUserIdFromNotificationLoginEndCookie(String str) {
        return extractFromCookieString(str, BaseConstants.EXTRA_USER_ID);
    }

    public static String extractFromCookieString(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        String[] split = str.split(";");
        for (String str3 : split) {
            if (str3.contains(str2) && str3.split("=")[0].trim().equals(str2)) {
                return str3.substring(str3.indexOf("=") + 1);
            }
        }
        return null;
    }

    public static String getSimpleDateFormat() {
        Locale locale = Locale.getDefault();
        if (!locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            return SimpleDateFormat.default_not_en.toString();
        }
        if (locale.getCountry().equals(Locale.US.getCountry())) {
            return SimpleDateFormat.en_US.toString();
        }
        return SimpleDateFormat.en_not_US.toString();
    }

    /* loaded from: classes2.dex */
    private enum SimpleDateFormat {
        en_US("MM-dd-yyyy"),
        en_not_US("dd-MM-yyyy"),
        default_not_en(XMPassport.SIMPLE_DATE_FORMAT);
        
        private String value;

        SimpleDateFormat(String str) {
            this.value = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.value;
        }
    }
}
