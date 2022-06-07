package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.mipush.sdk.Constants;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class VCardResultParser extends ResultParser {
    private static final Pattern a = Pattern.compile("BEGIN:VCARD", 2);
    private static final Pattern b = Pattern.compile("\\d{4}-?\\d{2}-?\\d{2}");
    private static final Pattern c = Pattern.compile("\r\n[ \t]");
    private static final Pattern d = Pattern.compile("\\\\[nN]");
    private static final Pattern e = Pattern.compile("\\\\([,;\\\\])");
    private static final Pattern f = Pattern.compile("=");
    private static final Pattern g = Pattern.compile(";");
    private static final Pattern h = Pattern.compile("(?<!\\\\);+");
    private static final Pattern i = Pattern.compile(Constants.ACCEPT_TIME_SEPARATOR_SP);
    private static final Pattern j = Pattern.compile("[;,]");

    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        Matcher matcher = a.matcher(massagedText);
        if (!matcher.find() || matcher.start() != 0) {
            return null;
        }
        List<List<String>> a2 = a((CharSequence) "FN", massagedText, true, false);
        if (a2 == null) {
            a2 = a((CharSequence) "N", massagedText, true, false);
            a((Iterable<List<String>>) a2);
        }
        List<String> b2 = b((CharSequence) "NICKNAME", massagedText, true, false);
        String[] split = b2 == null ? null : i.split(b2.get(0));
        List<List<String>> a3 = a((CharSequence) "TEL", massagedText, true, false);
        List<List<String>> a4 = a((CharSequence) "EMAIL", massagedText, true, false);
        List<String> b3 = b((CharSequence) "NOTE", massagedText, false, false);
        List<List<String>> a5 = a((CharSequence) "ADR", massagedText, true, true);
        List<String> b4 = b((CharSequence) "ORG", massagedText, true, true);
        List<String> b5 = b((CharSequence) "BDAY", massagedText, true, false);
        List<String> list = (b5 == null || a(b5.get(0))) ? b5 : null;
        List<String> b6 = b((CharSequence) "TITLE", massagedText, true, false);
        List<List<String>> a6 = a((CharSequence) "URL", massagedText, true, false);
        List<String> b7 = b((CharSequence) "IMPP", massagedText, true, false);
        List<String> b8 = b((CharSequence) "GEO", massagedText, true, false);
        String[] split2 = b8 == null ? null : j.split(b8.get(0));
        return new AddressBookParsedResult(a((Collection<List<String>>) a2), split, null, a((Collection<List<String>>) a3), b(a3), a((Collection<List<String>>) a4), b(a4), a(b7), a(b3), a((Collection<List<String>>) a5), b(a5), a(b4), a(list), a(b6), a((Collection<List<String>>) a6), (split2 == null || split2.length == 2) ? split2 : null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<List<String>> a(CharSequence charSequence, String str, boolean z, boolean z2) {
        String str2;
        String str3;
        int i2;
        ArrayList arrayList;
        int indexOf;
        String str4;
        int i3;
        int length = str.length();
        int i4 = 0;
        int i5 = 0;
        ArrayList arrayList2 = null;
        while (i5 < length) {
            int i6 = 2;
            Matcher matcher = Pattern.compile("(?:^|\n)" + ((Object) charSequence) + "(?:;([^:]*))?:", 2).matcher(str);
            if (i5 > 0) {
                i5--;
            }
            if (!matcher.find(i5)) {
                break;
            }
            int end = matcher.end(i4);
            String group = matcher.group(1);
            if (group != null) {
                String[] split = g.split(group);
                int length2 = split.length;
                int i7 = i4;
                i2 = i7;
                arrayList = null;
                str3 = null;
                str2 = null;
                while (i7 < length2) {
                    String str5 = split[i7];
                    if (arrayList == null) {
                        arrayList = new ArrayList(1);
                    }
                    arrayList.add(str5);
                    String[] split2 = f.split(str5, i6);
                    if (split2.length > 1) {
                        String str6 = split2[0];
                        String str7 = split2[1];
                        if ("ENCODING".equalsIgnoreCase(str6) && "QUOTED-PRINTABLE".equalsIgnoreCase(str7)) {
                            i2 = 1;
                        } else if ("CHARSET".equalsIgnoreCase(str6)) {
                            str3 = str7;
                        } else if ("VALUE".equalsIgnoreCase(str6)) {
                            str2 = str7;
                        }
                    }
                    i7++;
                    i6 = 2;
                }
            } else {
                arrayList = null;
                i2 = 0;
                str3 = null;
                str2 = null;
            }
            int i8 = end;
            while (true) {
                indexOf = str.indexOf(10, i8);
                if (indexOf < 0) {
                    break;
                }
                if (indexOf < str.length() - 1) {
                    int i9 = indexOf + 1;
                    if (str.charAt(i9) == ' ' || str.charAt(i9) == '\t') {
                        i8 = indexOf + 2;
                    }
                }
                if (i2 == 0) {
                    break;
                }
                if (indexOf <= 0) {
                    i3 = 2;
                } else if (str.charAt(indexOf - 1) != '=') {
                    i3 = 2;
                } else {
                    i8 = indexOf + 1;
                }
                if (indexOf < i3) {
                    break;
                }
                if (str.charAt(indexOf - 2) != '=') {
                    break;
                }
                i8 = indexOf + 1;
            }
            if (indexOf < 0) {
                i5 = length;
                i4 = 0;
            } else if (indexOf > end) {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                }
                if (indexOf > 0 && str.charAt(indexOf - 1) == '\r') {
                    indexOf--;
                }
                String substring = str.substring(end, indexOf);
                if (z) {
                    substring = substring.trim();
                }
                if (i2 != 0) {
                    str4 = a(substring, str3);
                    if (z2) {
                        str4 = h.matcher(str4).replaceAll("\n").trim();
                    }
                } else {
                    if (z2) {
                        substring = h.matcher(substring).replaceAll("\n").trim();
                    }
                    str4 = e.matcher(d.matcher(c.matcher(substring).replaceAll("")).replaceAll("\n")).replaceAll("$1");
                }
                if (MiSoundBoxCommandExtras.URI.equals(str2)) {
                    try {
                        str4 = URI.create(str4).getSchemeSpecificPart();
                    } catch (IllegalArgumentException unused) {
                    }
                }
                if (arrayList == null) {
                    ArrayList arrayList3 = new ArrayList(1);
                    arrayList3.add(str4);
                    arrayList2.add(arrayList3);
                    i4 = 0;
                } else {
                    i4 = 0;
                    arrayList.add(0, str4);
                    arrayList2.add(arrayList);
                }
                i5 = indexOf + 1;
            } else {
                i4 = 0;
                i5 = indexOf + 1;
            }
        }
        return arrayList2;
    }

    private static String a(CharSequence charSequence, String str) {
        char charAt;
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        while (i2 < length) {
            char charAt2 = charSequence.charAt(i2);
            if (!(charAt2 == '\n' || charAt2 == '\r')) {
                if (charAt2 != '=') {
                    a(byteArrayOutputStream, str, sb);
                    sb.append(charAt2);
                } else if (!(i2 >= length - 2 || (charAt = charSequence.charAt(i2 + 1)) == '\r' || charAt == '\n')) {
                    i2 += 2;
                    char charAt3 = charSequence.charAt(i2);
                    int parseHexDigit = parseHexDigit(charAt);
                    int parseHexDigit2 = parseHexDigit(charAt3);
                    if (parseHexDigit >= 0 && parseHexDigit2 >= 0) {
                        byteArrayOutputStream.write((parseHexDigit << 4) + parseHexDigit2);
                    }
                }
            }
            i2++;
        }
        a(byteArrayOutputStream, str, sb);
        return sb.toString();
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, String str, StringBuilder sb) {
        String str2;
        if (byteArrayOutputStream.size() > 0) {
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (str == null) {
                str2 = new String(byteArray, StandardCharsets.UTF_8);
            } else {
                try {
                    str2 = new String(byteArray, str);
                } catch (UnsupportedEncodingException unused) {
                    str2 = new String(byteArray, StandardCharsets.UTF_8);
                }
            }
            byteArrayOutputStream.reset();
            sb.append(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<String> b(CharSequence charSequence, String str, boolean z, boolean z2) {
        List<List<String>> a2 = a(charSequence, str, z, z2);
        if (a2 == null || a2.isEmpty()) {
            return null;
        }
        return a2.get(0);
    }

    private static String a(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private static String[] a(Collection<List<String>> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (List<String> list : collection) {
            String str = list.get(0);
            if (str != null && !str.isEmpty()) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static String[] b(Collection<List<String>> collection) {
        String str;
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (List<String> list : collection) {
            String str2 = list.get(0);
            if (str2 != null && !str2.isEmpty()) {
                int i2 = 1;
                while (true) {
                    if (i2 >= list.size()) {
                        str = null;
                        break;
                    }
                    String str3 = list.get(i2);
                    int indexOf = str3.indexOf(61);
                    if (indexOf < 0) {
                        str = str3;
                        break;
                    } else if ("TYPE".equalsIgnoreCase(str3.substring(0, indexOf))) {
                        str = str3.substring(indexOf + 1);
                        break;
                    } else {
                        i2++;
                    }
                }
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static boolean a(CharSequence charSequence) {
        return charSequence == null || b.matcher(charSequence).matches();
    }

    private static void a(Iterable<List<String>> iterable) {
        int indexOf;
        if (iterable != null) {
            for (List<String> list : iterable) {
                String str = list.get(0);
                String[] strArr = new String[5];
                int i2 = 0;
                int i3 = 0;
                while (i2 < 4 && (indexOf = str.indexOf(59, i3)) >= 0) {
                    strArr[i2] = str.substring(i3, indexOf);
                    i2++;
                    i3 = indexOf + 1;
                }
                strArr[i2] = str.substring(i3);
                StringBuilder sb = new StringBuilder(100);
                a(strArr, 3, sb);
                a(strArr, 1, sb);
                a(strArr, 2, sb);
                a(strArr, 0, sb);
                a(strArr, 4, sb);
                list.set(0, sb.toString().trim());
            }
        }
    }

    private static void a(String[] strArr, int i2, StringBuilder sb) {
        if (strArr[i2] != null && !strArr[i2].isEmpty()) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(strArr[i2]);
        }
    }
}
