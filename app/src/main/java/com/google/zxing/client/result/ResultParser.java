package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public abstract class ResultParser {
    private static final ResultParser[] a = {new BookmarkDoCoMoResultParser(), new AddressBookDoCoMoResultParser(), new EmailDoCoMoResultParser(), new AddressBookAUResultParser(), new VCardResultParser(), new BizcardResultParser(), new VEventResultParser(), new EmailAddressResultParser(), new SMTPResultParser(), new TelResultParser(), new SMSMMSResultParser(), new SMSTOMMSTOResultParser(), new GeoResultParser(), new WifiResultParser(), new URLTOResultParser(), new URIResultParser(), new ISBNResultParser(), new ProductResultParser(), new ExpandedProductResultParser(), new VINResultParser()};
    private static final Pattern b = Pattern.compile("\\d+");
    private static final Pattern c = Pattern.compile(MusicGroupListActivity.SPECIAL_SYMBOL);
    private static final Pattern d = Pattern.compile("=");

    protected static int parseHexDigit(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - '0';
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return (c2 - 'a') + 10;
        }
        if (c2 < 'A' || c2 > 'F') {
            return -1;
        }
        return (c2 - 'A') + 10;
    }

    public abstract ParsedResult parse(Result result);

    protected static String getMassagedText(Result result) {
        String text = result.getText();
        return text.startsWith("\ufeff") ? text.substring(1) : text;
    }

    public static ParsedResult parseResult(Result result) {
        for (ResultParser resultParser : a) {
            ParsedResult parse = resultParser.parse(result);
            if (parse != null) {
                return parse;
            }
        }
        return new TextParsedResult(result.getText(), null);
    }

    protected static void maybeAppend(String str, StringBuilder sb) {
        if (str != null) {
            sb.append('\n');
            sb.append(str);
        }
    }

    protected static void maybeAppend(String[] strArr, StringBuilder sb) {
        if (strArr != null) {
            for (String str : strArr) {
                sb.append('\n');
                sb.append(str);
            }
        }
    }

    protected static String[] maybeWrap(String str) {
        if (str == null) {
            return null;
        }
        return new String[]{str};
    }

    protected static String unescapeBackslash(String str) {
        int indexOf = str.indexOf(92);
        if (indexOf < 0) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length - 1);
        sb.append(str.toCharArray(), 0, indexOf);
        boolean z = false;
        while (indexOf < length) {
            char charAt = str.charAt(indexOf);
            if (z || charAt != '\\') {
                sb.append(charAt);
                z = false;
            } else {
                z = true;
            }
            indexOf++;
        }
        return sb.toString();
    }

    protected static boolean isStringOfDigits(CharSequence charSequence, int i) {
        return charSequence != null && i > 0 && i == charSequence.length() && b.matcher(charSequence).matches();
    }

    public static boolean isSubstringOfDigits(CharSequence charSequence, int i, int i2) {
        int i3;
        return charSequence != null && i2 > 0 && charSequence.length() >= (i3 = i2 + i) && b.matcher(charSequence.subSequence(i, i3)).matches();
    }

    static Map<String, String> b(String str) {
        int indexOf = str.indexOf(63);
        if (indexOf < 0) {
            return null;
        }
        HashMap hashMap = new HashMap(3);
        for (String str2 : c.split(str.substring(indexOf + 1))) {
            a(str2, hashMap);
        }
        return hashMap;
    }

    private static void a(CharSequence charSequence, Map<String, String> map) {
        String[] split = d.split(charSequence, 2);
        if (split.length == 2) {
            try {
                map.put(split[0], c(split[1]));
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    static String c(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    static String[] a(String str, String str2, char c2, boolean z) {
        int i;
        int length = str2.length();
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < length; i2 = i) {
            int indexOf = str2.indexOf(str, i2);
            if (indexOf < 0) {
                break;
            }
            int length2 = indexOf + str.length();
            boolean z2 = true;
            i = length2;
            while (z2) {
                int indexOf2 = str2.indexOf(c2, i);
                if (indexOf2 < 0) {
                    i = str2.length();
                    z2 = false;
                } else if (a(str2, indexOf2) % 2 != 0) {
                    i = indexOf2 + 1;
                } else {
                    if (arrayList == null) {
                        arrayList = new ArrayList(3);
                    }
                    String unescapeBackslash = unescapeBackslash(str2.substring(length2, indexOf2));
                    if (z) {
                        unescapeBackslash = unescapeBackslash.trim();
                    }
                    if (!unescapeBackslash.isEmpty()) {
                        arrayList.add(unescapeBackslash);
                    }
                    i = indexOf2 + 1;
                    z2 = false;
                }
            }
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static int a(CharSequence charSequence, int i) {
        int i2 = 0;
        for (int i3 = i - 1; i3 >= 0 && charSequence.charAt(i3) == '\\'; i3--) {
            i2++;
        }
        return i2;
    }

    static String b(String str, String str2, char c2, boolean z) {
        String[] a2 = a(str, str2, c2, z);
        if (a2 == null) {
            return null;
        }
        return a2[0];
    }
}
