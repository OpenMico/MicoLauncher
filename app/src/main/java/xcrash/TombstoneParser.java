package xcrash;

import android.os.Build;
import android.text.TextUtils;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.CrashAnalysis;
import com.xiaomi.onetrack.util.b;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes6.dex */
public class TombstoneParser {
    public static final String keyCode = "code";
    public static final String keyFaultAddr = "fault addr";
    public static final String keyForeground = "foreground";
    public static final String keyMemoryInfo = "memory info";
    public static final String keyMemoryNear = "memory near";
    public static final String keyModel = "Model";
    public static final String keyNetworkInfo = "network info";
    public static final String keyOtherThreads = "other threads";
    public static final String keyProcessId = "pid";
    public static final String keyProcessName = "pname";
    public static final String keyRegisters = "registers";
    public static final String keySignal = "signal";
    public static final String keyThreadId = "tid";
    public static final String keyThreadName = "tname";
    private static final Pattern a = Pattern.compile("^(.*):\\s'(.*?)'$");
    private static final Pattern b = Pattern.compile("^pid:\\s(.*),\\stid:\\s(.*),\\sname:\\s(.*)\\s+>>>\\s(.*)\\s<<<$");
    private static final Pattern c = Pattern.compile("^pid:\\s(.*)\\s+>>>\\s(.*)\\s<<<$");
    private static final Pattern d = Pattern.compile("^signal\\s(.*),\\scode\\s(.*),\\sfault\\saddr\\s(.*)$");
    private static final Pattern e = Pattern.compile("^(\\d{20})_(.*)__(.*)$");
    public static final String keyTombstoneMaker = "Tombstone maker";
    public static final String keyCrashType = "Crash type";
    public static final String keyStartTime = "Start time";
    public static final String keyCrashTime = "Crash time";
    public static final String keyAppId = "App ID";
    public static final String keyAppVersion = "App version";
    public static final String keyRooted = "Rooted";
    public static final String keyApiLevel = "API level";
    public static final String keyOsVersion = "OS version";
    public static final String keyKernelVersion = "Kernel version";
    public static final String keyAbiList = "ABI list";
    public static final String keyManufacturer = "Manufacturer";
    public static final String keyBrand = "Brand";
    public static final String keyBuildFingerprint = "Build fingerprint";
    public static final String keyAbi = "ABI";
    public static final String keyAbortMessage = "Abort message";
    private static final Set<String> f = new HashSet(Arrays.asList(keyTombstoneMaker, keyCrashType, keyStartTime, keyCrashTime, keyAppId, keyAppVersion, keyRooted, keyApiLevel, keyOsVersion, keyKernelVersion, keyAbiList, keyManufacturer, keyBrand, "Model", keyBuildFingerprint, keyAbi, keyAbortMessage));
    public static final String keyBacktrace = "backtrace";
    public static final String keyBuildId = "build id";
    public static final String keyStack = "stack";
    public static final String keyMemoryMap = "memory map";
    public static final String keyLogcat = "logcat";
    public static final String keyOpenFiles = "open files";
    public static final String keyJavaStacktrace = "java stacktrace";
    public static final String keyXCrashError = "xcrash error";
    public static final String keyXCrashErrorDebug = "xcrash error debug";
    private static final Set<String> g = new HashSet(Arrays.asList(keyBacktrace, keyBuildId, keyStack, keyMemoryMap, keyLogcat, keyOpenFiles, keyJavaStacktrace, keyXCrashError, keyXCrashErrorDebug));
    private static final Set<String> h = new HashSet(Arrays.asList("foreground"));

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes6.dex */
    public enum a {
        UNKNOWN,
        HEAD,
        SECTION
    }

    private TombstoneParser() {
    }

    public static Map<String, String> parse(File file) throws IOException {
        return parse(file.getAbsolutePath(), null);
    }

    public static Map<String, String> parse(String str) throws IOException {
        return parse(str, null);
    }

    public static Map<String, String> parse(String str, String str2) throws IOException {
        HashMap hashMap = new HashMap();
        if (str != null) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            a((Map<String, String>) hashMap, bufferedReader, true);
            bufferedReader.close();
        }
        if (str2 != null) {
            BufferedReader bufferedReader2 = new BufferedReader(new StringReader(str2));
            a((Map<String, String>) hashMap, bufferedReader2, false);
            bufferedReader2.close();
        }
        a(hashMap, str);
        if (TextUtils.isEmpty((String) hashMap.get(keyAppVersion))) {
            String b2 = XCrash.b();
            if (TextUtils.isEmpty(b2)) {
                b2 = "unknown";
            }
            hashMap.put(keyAppVersion, b2);
        }
        a(hashMap);
        return hashMap;
    }

    private static void a(Map<String, String> map, String str) {
        String str2;
        if (str != null) {
            if (TextUtils.isEmpty(map.get(keyCrashTime))) {
                map.put(keyCrashTime, new SimpleDateFormat(StdDateFormat.DATE_FORMAT_STR_ISO8601, Locale.US).format(new Date(new File(str).lastModified())));
            }
            String str3 = map.get(keyStartTime);
            String str4 = map.get(keyAppVersion);
            String str5 = map.get(keyProcessName);
            String str6 = map.get(keyCrashType);
            if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str5) || TextUtils.isEmpty(str6)) {
                String substring = str.substring(str.lastIndexOf(47) + 1);
                if (!substring.isEmpty() && substring.startsWith("tombstone_")) {
                    String substring2 = substring.substring(10);
                    if (substring2.endsWith(".java.xcrash")) {
                        if (TextUtils.isEmpty(str6)) {
                            map.put(keyCrashType, "java");
                        }
                        str2 = substring2.substring(0, substring2.length() - 12);
                    } else if (substring2.endsWith(".native.xcrash")) {
                        if (TextUtils.isEmpty(str6)) {
                            map.put(keyCrashType, CrashAnalysis.NATIVE_CRASH);
                        }
                        str2 = substring2.substring(0, substring2.length() - 14);
                    } else if (substring2.endsWith(".anr.xcrash")) {
                        if (TextUtils.isEmpty(str6)) {
                            map.put(keyCrashType, "anr");
                        }
                        str2 = substring2.substring(0, substring2.length() - 11);
                    } else {
                        return;
                    }
                    if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str5)) {
                        Matcher matcher = e.matcher(str2);
                        if (matcher.find() && matcher.groupCount() == 3) {
                            if (TextUtils.isEmpty(str3)) {
                                map.put(keyStartTime, new SimpleDateFormat(StdDateFormat.DATE_FORMAT_STR_ISO8601, Locale.US).format(new Date(Long.parseLong(matcher.group(1), 10) / 1000)));
                            }
                            if (TextUtils.isEmpty(str4)) {
                                map.put(keyAppVersion, matcher.group(2));
                            }
                            if (TextUtils.isEmpty(str5)) {
                                map.put(keyProcessName, matcher.group(3));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void a(Map<String, String> map) {
        if (TextUtils.isEmpty(map.get(keyAppId))) {
            map.put(keyAppId, XCrash.a());
        }
        if (TextUtils.isEmpty(map.get(keyTombstoneMaker))) {
            map.put(keyTombstoneMaker, "xCrash 2.4.9");
        }
        if (TextUtils.isEmpty(map.get(keyRooted))) {
            map.put(keyRooted, f.a() ? "Yes" : "No");
        }
        if (TextUtils.isEmpty(map.get(keyApiLevel))) {
            map.put(keyApiLevel, String.valueOf(Build.VERSION.SDK_INT));
        }
        if (TextUtils.isEmpty(map.get(keyOsVersion))) {
            map.put(keyOsVersion, Build.VERSION.RELEASE);
        }
        if (TextUtils.isEmpty(map.get(keyBuildFingerprint))) {
            map.put("Model", Build.FINGERPRINT);
        }
        if (TextUtils.isEmpty(map.get(keyManufacturer))) {
            map.put(keyManufacturer, Build.MANUFACTURER);
        }
        if (TextUtils.isEmpty(map.get(keyBrand))) {
            map.put(keyBrand, Build.BRAND);
        }
        if (TextUtils.isEmpty(map.get("Model"))) {
            map.put("Model", Build.MODEL);
        }
        if (TextUtils.isEmpty(map.get(keyAbiList))) {
            map.put(keyAbiList, f.b());
        }
    }

    private static String a(BufferedReader bufferedReader) throws IOException {
        try {
            bufferedReader.mark(2);
            for (int i = 0; i < 2; i++) {
                try {
                    int read = bufferedReader.read();
                    if (read == -1) {
                        bufferedReader.reset();
                        return null;
                    } else if (read > 0) {
                        bufferedReader.reset();
                        return bufferedReader.readLine();
                    }
                } catch (Exception unused) {
                    bufferedReader.reset();
                    return bufferedReader.readLine();
                }
            }
            bufferedReader.reset();
            return null;
        } catch (Exception unused2) {
            return bufferedReader.readLine();
        }
    }

    private static void a(Map<String, String> map, BufferedReader bufferedReader, boolean z) throws IOException {
        StringBuilder sb = new StringBuilder();
        String str = "";
        a aVar = a.UNKNOWN;
        String a2 = z ? a(bufferedReader) : bufferedReader.readLine();
        int i = 0;
        int i2 = a2 == null ? 1 : 0;
        String str2 = null;
        boolean z2 = false;
        boolean z3 = false;
        while (i2 == 0) {
            String a3 = z ? a(bufferedReader) : bufferedReader.readLine();
            int i3 = a3 == null ? 1 : i;
            switch (aVar) {
                case UNKNOWN:
                    if (!a2.equals(b.d)) {
                        if (!a2.equals(b.e)) {
                            if (a2.length() > 1 && a2.endsWith(Constants.COLON_SEPARATOR)) {
                                a aVar2 = a.SECTION;
                                i = 0;
                                String substring = a2.substring(0, a2.length() - 1);
                                if (!g.contains(substring)) {
                                    if (!substring.equals(keyMemoryInfo)) {
                                        if (!substring.startsWith("memory near ")) {
                                            str2 = substring;
                                            z2 = false;
                                            aVar = aVar2;
                                            str = "";
                                            z3 = false;
                                            break;
                                        } else {
                                            str2 = keyMemoryNear;
                                            sb.append(a2);
                                            sb.append('\n');
                                            z2 = true;
                                            aVar = aVar2;
                                            str = "";
                                            z3 = false;
                                            break;
                                        }
                                    } else {
                                        str2 = substring;
                                        z2 = true;
                                        aVar = aVar2;
                                        str = "";
                                        z3 = false;
                                        break;
                                    }
                                } else {
                                    z3 = substring.equals(keyBacktrace) || substring.equals(keyBuildId) || substring.equals(keyStack) || substring.equals(keyMemoryMap) || substring.equals(keyOpenFiles) || substring.equals(keyJavaStacktrace) || substring.equals(keyXCrashErrorDebug);
                                    z2 = substring.equals(keyXCrashError);
                                    str2 = substring;
                                    aVar = aVar2;
                                    str = "";
                                    break;
                                }
                            } else {
                                i = 0;
                                break;
                            }
                        } else {
                            a aVar3 = a.SECTION;
                            str2 = keyOtherThreads;
                            str = b.f;
                            sb.append(a2);
                            sb.append('\n');
                            z3 = false;
                            z2 = false;
                            aVar = aVar3;
                            i = 0;
                            break;
                        }
                    } else {
                        aVar = a.HEAD;
                        i = 0;
                        break;
                    }
                    break;
                case HEAD:
                    if (a2.startsWith("pid: ")) {
                        Matcher matcher = b.matcher(a2);
                        if (!matcher.find() || matcher.groupCount() != 4) {
                            Matcher matcher2 = c.matcher(a2);
                            if (matcher2.find() && matcher2.groupCount() == 2) {
                                a(map, keyProcessId, matcher2.group(1));
                                a(map, keyProcessName, matcher2.group(2));
                            }
                        } else {
                            a(map, keyProcessId, matcher.group(1));
                            a(map, keyThreadId, matcher.group(2));
                            a(map, keyThreadName, matcher.group(3));
                            a(map, keyProcessName, matcher.group(4));
                        }
                    } else if (a2.startsWith("signal ")) {
                        Matcher matcher3 = d.matcher(a2);
                        if (matcher3.find() && matcher3.groupCount() == 3) {
                            a(map, keySignal, matcher3.group(1));
                            a(map, "code", matcher3.group(2));
                            a(map, keyFaultAddr, matcher3.group(3));
                        }
                    } else {
                        Matcher matcher4 = a.matcher(a2);
                        if (matcher4.find() && matcher4.groupCount() == 2 && f.contains(matcher4.group(1))) {
                            a(map, matcher4.group(1), matcher4.group(2));
                        }
                    }
                    if (a3 != null && (a3.startsWith("    r0 ") || a3.startsWith("    x0 ") || a3.startsWith("    eax ") || a3.startsWith("    rax "))) {
                        aVar = a.SECTION;
                        str2 = keyRegisters;
                        str = "";
                        z3 = true;
                        z2 = false;
                    }
                    if (a3 != null && !a3.isEmpty()) {
                        i = 0;
                        break;
                    } else {
                        aVar = a.UNKNOWN;
                        i = 0;
                        break;
                    }
                    break;
                case SECTION:
                    if (!a2.equals(str) && i3 == 0) {
                        if (z3) {
                            if (str2.equals(keyJavaStacktrace) && a2.startsWith(StringUtils.SPACE)) {
                                a2 = a2.trim();
                            } else if (a2.startsWith("    ")) {
                                a2 = a2.substring(4);
                            }
                        }
                        sb.append(a2);
                        sb.append('\n');
                        break;
                    } else {
                        if (h.contains(str2) && sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        a(map, str2, sb.toString(), z2);
                        sb.setLength(i);
                        aVar = a.UNKNOWN;
                        break;
                    }
            }
            a2 = a3;
            i2 = i3;
        }
    }

    private static void a(Map<String, String> map, String str, String str2) {
        a(map, str, str2, false);
    }

    private static void a(Map<String, String> map, String str, String str2, boolean z) {
        if (str != null && !str.isEmpty() && str2 != null) {
            String str3 = map.get(str);
            if (z) {
                if (str3 != null) {
                    str2 = str3 + str2;
                }
                map.put(str, str2);
            } else if (str3 == null || (str3.isEmpty() && !str2.isEmpty())) {
                map.put(str, str2);
            }
        }
    }
}
