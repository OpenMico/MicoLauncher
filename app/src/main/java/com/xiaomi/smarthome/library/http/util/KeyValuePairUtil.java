package com.xiaomi.smarthome.library.http.util;

import android.text.TextUtils;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.smarthome.library.http.KeyValuePair;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/* loaded from: classes4.dex */
public class KeyValuePairUtil {
    public static String getUrlWithQueryString(String str, List<KeyValuePair> list) {
        if (list == null || list.isEmpty()) {
            return str;
        }
        String a = a(list, "UTF-8");
        if (!str.contains("?")) {
            return str + "?" + a;
        }
        return str + MusicGroupListActivity.SPECIAL_SYMBOL + a;
    }

    private static String a(List<? extends KeyValuePair> list, String str) {
        StringBuilder sb = new StringBuilder();
        for (KeyValuePair keyValuePair : list) {
            String a = a(keyValuePair.getKey(), str);
            String value = keyValuePair.getValue();
            String a2 = value != null ? a(value, str) : "";
            if (sb.length() > 0) {
                sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            }
            sb.append(a);
            sb.append("=");
            sb.append(a2);
        }
        return sb.toString();
    }

    private static String a(String str, String str2) {
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        try {
            return URLEncoder.encode(str, str2);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static RequestBody getRequestBody(List<KeyValuePair> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (KeyValuePair keyValuePair : list) {
            String key = keyValuePair.getKey();
            String value = keyValuePair.getValue();
            if (!TextUtils.isEmpty(key) && value != null) {
                builder.add(key, value);
            }
        }
        return builder.build();
    }
}
