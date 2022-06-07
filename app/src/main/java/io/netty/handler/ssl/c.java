package io.netty.handler.ssl;

import com.xiaomi.smarthome.library.crypto.rc4coder.RC4Coder;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CipherSuiteConverter.java */
/* loaded from: classes4.dex */
public final class c {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(c.class);
    private static final Pattern b = Pattern.compile("^(?:TLS|SSL)_((?:(?!_WITH_).)+)_WITH_(.*)_(.*)$");
    private static final Pattern c = Pattern.compile("^(?:((?:(?:EXP-)?(?:(?:DHE|EDH|ECDH|ECDHE|SRP)-(?:DSS|RSA|ECDSA)|(?:ADH|AECDH|KRB5|PSK|SRP)))|EXP)-)?(.*)-(.*)$");
    private static final Pattern d = Pattern.compile("^(AES)_([0-9]+)_CBC$");
    private static final Pattern e = Pattern.compile("^(AES)_([0-9]+)_(.*)$");
    private static final Pattern f = Pattern.compile("^(AES)([0-9]+)$");
    private static final Pattern g = Pattern.compile("^(AES)([0-9]+)-(.*)$");
    private static final ConcurrentMap<String, String> h = PlatformDependent.newConcurrentHashMap();
    private static final ConcurrentMap<String, Map<String, String>> i = PlatformDependent.newConcurrentHashMap();

    private static String g(String str) {
        return str;
    }

    private static String i(String str) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(Iterable<String> iterable) {
        String next;
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = iterable.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            String a2 = a(next);
            if (a2 != null) {
                next = a2;
            }
            sb.append(next);
            sb.append(':');
        }
        if (sb.length() <= 0) {
            return "";
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str) {
        String str2 = h.get(str);
        return str2 != null ? str2 : d(str);
    }

    private static String d(String str) {
        String b2 = b(str);
        if (b2 == null) {
            return null;
        }
        h.putIfAbsent(str, b2);
        String substring = str.substring(4);
        HashMap hashMap = new HashMap(4);
        hashMap.put("", substring);
        hashMap.put("SSL", "SSL_" + substring);
        hashMap.put("TLS", "TLS_" + substring);
        i.put(b2, hashMap);
        a.debug("Cipher suite mapping: {} => {}", str, b2);
        return b2;
    }

    static String b(String str) {
        Matcher matcher = b.matcher(str);
        if (!matcher.matches()) {
            return null;
        }
        String e2 = e(matcher.group(1));
        String f2 = f(matcher.group(2));
        String g2 = g(matcher.group(3));
        if (e2.length() == 0) {
            return f2 + '-' + g2;
        }
        return e2 + '-' + f2 + '-' + g2;
    }

    private static String e(String str) {
        boolean endsWith = str.endsWith("_EXPORT");
        if (endsWith) {
            str = str.substring(0, str.length() - 7);
        }
        if ("RSA".equals(str)) {
            str = "";
        } else if (str.endsWith("_anon")) {
            str = 'A' + str.substring(0, str.length() - 5);
        }
        if (endsWith) {
            if (str.length() == 0) {
                str = "EXP";
            } else {
                str = "EXP-" + str;
            }
        }
        return str.replace('_', '-');
    }

    private static String f(String str) {
        if (str.startsWith("AES_")) {
            Matcher matcher = d.matcher(str);
            if (matcher.matches()) {
                return matcher.replaceFirst("$1$2");
            }
            Matcher matcher2 = e.matcher(str);
            if (matcher2.matches()) {
                return matcher2.replaceFirst("$1$2-$3");
            }
        }
        return "3DES_EDE_CBC".equals(str) ? "DES-CBC3" : ("RC4_128".equals(str) || "RC4_40".equals(str)) ? RC4Coder.RC4_ALGORITHM : ("DES40_CBC".equals(str) || "DES_CBC_40".equals(str)) ? "DES-CBC" : "RC2_CBC_40".equals(str) ? "RC2-CBC" : str.replace('_', '-');
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str, String str2) {
        Map<String, String> map = i.get(str);
        if (map == null) {
            map = h(str);
        }
        String str3 = map.get(str2);
        if (str3 != null) {
            return str3;
        }
        return str2 + '_' + map.get("");
    }

    private static Map<String, String> h(String str) {
        String c2 = c(str);
        if (c2 == null) {
            return null;
        }
        String str2 = "SSL_" + c2;
        String str3 = "TLS_" + c2;
        HashMap hashMap = new HashMap(4);
        hashMap.put("", c2);
        hashMap.put("SSL", str2);
        hashMap.put("TLS", str3);
        i.putIfAbsent(str, hashMap);
        h.putIfAbsent(str3, str);
        h.putIfAbsent(str2, str);
        a.debug("Cipher suite mapping: {} => {}", str3, str);
        a.debug("Cipher suite mapping: {} => {}", str2, str);
        return hashMap;
    }

    static String c(String str) {
        Matcher matcher = c.matcher(str);
        if (!matcher.matches()) {
            return null;
        }
        boolean z = true;
        String group = matcher.group(1);
        if (group == null) {
            group = "";
            z = false;
        } else if (group.startsWith("EXP-")) {
            group = group.substring(4);
        } else if ("EXP".equals(group)) {
            group = "";
        } else {
            z = false;
        }
        return a(group, z) + "_WITH_" + b(matcher.group(2), z) + '_' + i(matcher.group(3));
    }

    private static String a(String str, boolean z) {
        if (str.length() == 0) {
            str = "RSA";
        } else if ("ADH".equals(str)) {
            str = "DH_anon";
        } else if ("AECDH".equals(str)) {
            str = "ECDH_anon";
        }
        String replace = str.replace('-', '_');
        if (!z) {
            return replace;
        }
        return replace + "_EXPORT";
    }

    private static String b(String str, boolean z) {
        if (str.startsWith("AES")) {
            Matcher matcher = f.matcher(str);
            if (matcher.matches()) {
                return matcher.replaceFirst("$1_$2_CBC");
            }
            Matcher matcher2 = g.matcher(str);
            if (matcher2.matches()) {
                return matcher2.replaceFirst("$1_$2_$3");
            }
        }
        if ("DES-CBC3".equals(str)) {
            return "3DES_EDE_CBC";
        }
        if (RC4Coder.RC4_ALGORITHM.equals(str)) {
            return z ? "RC4_40" : "RC4_128";
        }
        if ("DES-CBC".equals(str)) {
            return z ? "DES_CBC_40" : "DES_CBC";
        }
        if ("RC2-CBC".equals(str)) {
            return z ? "RC2_CBC_40" : "RC2_CBC";
        }
        return str.replace('-', '_');
    }

    private c() {
    }
}
