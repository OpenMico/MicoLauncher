package com.xiaomi.smarthome.library.apache.http.client.utils;

import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.smarthome.library.apache.http.Header;
import com.xiaomi.smarthome.library.apache.http.HttpEntity;
import com.xiaomi.smarthome.library.apache.http.NameValuePair;
import com.xiaomi.smarthome.library.apache.http.message.BasicNameValuePair;
import com.xiaomi.smarthome.library.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/* loaded from: classes4.dex */
public class URLEncodedUtils {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static List<NameValuePair> parse(URI uri, String str) {
        List<NameValuePair> emptyList = Collections.emptyList();
        String rawQuery = uri.getRawQuery();
        if (rawQuery == null || rawQuery.length() <= 0) {
            return emptyList;
        }
        ArrayList arrayList = new ArrayList();
        parse(arrayList, new Scanner(rawQuery), str);
        return arrayList;
    }

    public static List<NameValuePair> parse(HttpEntity httpEntity) throws IOException {
        List<NameValuePair> emptyList = Collections.emptyList();
        if (isEncoded(httpEntity)) {
            String entityUtils = EntityUtils.toString(httpEntity);
            Header contentEncoding = httpEntity.getContentEncoding();
            if (entityUtils != null && entityUtils.length() > 0) {
                emptyList = new ArrayList<>();
                parse(emptyList, new Scanner(entityUtils), contentEncoding != null ? contentEncoding.getValue() : null);
            }
        }
        return emptyList;
    }

    public static boolean isEncoded(HttpEntity httpEntity) {
        Header contentType = httpEntity.getContentType();
        return contentType != null && contentType.getValue().equalsIgnoreCase("application/x-www-form-urlencoded");
    }

    public static void parse(List<NameValuePair> list, Scanner scanner, String str) {
        scanner.useDelimiter(MusicGroupListActivity.SPECIAL_SYMBOL);
        while (scanner.hasNext()) {
            String[] split = scanner.next().split("=");
            if (split.length == 0 || split.length > 2) {
                throw new IllegalArgumentException("bad parameter");
            }
            String a = a(split[0], str);
            String str2 = null;
            if (split.length == 2) {
                str2 = a(split[1], str);
            }
            list.add(new BasicNameValuePair(a, str2));
        }
    }

    public static String format(List<? extends NameValuePair> list, String str) {
        StringBuilder sb = new StringBuilder();
        for (NameValuePair nameValuePair : list) {
            String b = b(nameValuePair.getName(), str);
            String value = nameValuePair.getValue();
            String b2 = value != null ? b(value, str) : "";
            if (sb.length() > 0) {
                sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            }
            sb.append(b);
            sb.append("=");
            sb.append(b2);
        }
        return sb.toString();
    }

    private static String a(String str, String str2) {
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(str, str2);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String b(String str, String str2) {
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        try {
            return URLEncoder.encode(str, str2);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
