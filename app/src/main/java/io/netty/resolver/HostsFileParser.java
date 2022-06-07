package io.netty.resolver;

import io.netty.util.NetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class HostsFileParser {
    private static final Pattern a = Pattern.compile("[ \t]+");
    private static final InternalLogger b = InternalLoggerFactory.getInstance(HostsFileParser.class);

    private static File a() {
        if (!PlatformDependent.isWindows()) {
            return new File("/etc/hosts");
        }
        File file = new File(System.getenv("SystemRoot") + "\\system32\\drivers\\etc\\hosts");
        return !file.exists() ? new File("C:\\Windows\\system32\\drivers\\etc\\hosts") : file;
    }

    public static Map<String, InetAddress> parseSilently() {
        File a2 = a();
        try {
            return parse(a2);
        } catch (IOException e) {
            InternalLogger internalLogger = b;
            internalLogger.warn("Failed to load and parse hosts file at " + a2.getPath(), (Throwable) e);
            return Collections.emptyMap();
        }
    }

    public static Map<String, InetAddress> parse() throws IOException {
        return parse(a());
    }

    public static Map<String, InetAddress> parse(File file) throws IOException {
        ObjectUtil.checkNotNull(file, "file");
        if (!file.exists() || !file.isFile()) {
            return Collections.emptyMap();
        }
        return parse(new BufferedReader(new FileReader(file)));
    }

    public static Map<String, InetAddress> parse(Reader reader) throws IOException {
        byte[] createByteArrayFromIpAddressString;
        ObjectUtil.checkNotNull(reader, "reader");
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            HashMap hashMap = new HashMap();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    int indexOf = readLine.indexOf(35);
                    if (indexOf != -1) {
                        readLine = readLine.substring(0, indexOf);
                    }
                    String trim = readLine.trim();
                    if (!trim.isEmpty()) {
                        ArrayList arrayList = new ArrayList();
                        String[] split = a.split(trim);
                        for (String str : split) {
                            if (!str.isEmpty()) {
                                arrayList.add(str);
                            }
                        }
                        if (arrayList.size() >= 2 && (createByteArrayFromIpAddressString = NetUtil.createByteArrayFromIpAddressString((String) arrayList.get(0))) != null) {
                            for (int i = 1; i < arrayList.size(); i++) {
                                String str2 = (String) arrayList.get(i);
                                String lowerCase = str2.toLowerCase(Locale.ENGLISH);
                                if (!hashMap.containsKey(lowerCase)) {
                                    hashMap.put(lowerCase, InetAddress.getByAddress(str2, createByteArrayFromIpAddressString));
                                }
                            }
                        }
                    }
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                    }
                }
            }
            return hashMap;
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e2) {
                b.warn("Failed to close a reader", (Throwable) e2);
            }
        }
    }

    private HostsFileParser() {
    }
}
