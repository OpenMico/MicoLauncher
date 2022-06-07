package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class WebSocketExtensionUtil {
    private static final Pattern a = Pattern.compile("^([^=]+)(=[\\\"]?([^\\\"]+)[\\\"]?)?$");

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(HttpHeaders httpHeaders) {
        return httpHeaders.containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, true) && httpHeaders.contains((CharSequence) HttpHeaderNames.UPGRADE, (CharSequence) HttpHeaderValues.WEBSOCKET, true);
    }

    public static List<WebSocketExtensionData> extractExtensions(String str) {
        Map map;
        String[] split = StringUtil.split(str, StringUtil.COMMA);
        if (split.length <= 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(split.length);
        for (String str2 : split) {
            String[] split2 = StringUtil.split(str2, ';');
            String trim = split2[0].trim();
            if (split2.length > 1) {
                map = new HashMap(split2.length - 1);
                for (int i = 1; i < split2.length; i++) {
                    Matcher matcher = a.matcher(split2[i].trim());
                    if (matcher.matches() && matcher.group(1) != null) {
                        map.put(matcher.group(1), matcher.group(3));
                    }
                }
            } else {
                map = Collections.emptyMap();
            }
            arrayList.add(new WebSocketExtensionData(trim, map));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str, String str2, Map<String, String> map) {
        StringBuilder sb = new StringBuilder(str != null ? str.length() : str2.length() + 0 + 1);
        if (str != null && !str.trim().isEmpty()) {
            sb.append(str);
            sb.append(StringUtil.COMMA);
        }
        sb.append(str2);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(';');
            sb.append(entry.getKey());
            if (entry.getValue() != null) {
                sb.append('=');
                sb.append(entry.getValue());
            }
        }
        return sb.toString();
    }

    private WebSocketExtensionUtil() {
    }
}
