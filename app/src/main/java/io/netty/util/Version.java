package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public final class Version {
    private final String a;
    private final String b;
    private final long c;
    private final long d;
    private final String e;
    private final String f;
    private final String g;

    public static Map<String, Version> identify() {
        return identify(null);
    }

    public static Map<String, Version> identify(ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = PlatformDependent.getContextClassLoader();
        }
        Properties properties = new Properties();
        try {
            Enumeration<URL> resources = classLoader.getResources("META-INF/io.netty.versions.properties");
            while (resources.hasMoreElements()) {
                InputStream openStream = resources.nextElement().openStream();
                properties.load(openStream);
                try {
                    openStream.close();
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
        }
        HashSet<String> hashSet = new HashSet();
        for (String str : properties.keySet()) {
            int indexOf = str.indexOf(46);
            if (indexOf > 0) {
                String substring = str.substring(0, indexOf);
                if (properties.containsKey(substring + ".version")) {
                    if (properties.containsKey(substring + ".buildDate")) {
                        if (properties.containsKey(substring + ".commitDate")) {
                            if (properties.containsKey(substring + ".shortCommitHash")) {
                                if (properties.containsKey(substring + ".longCommitHash")) {
                                    if (properties.containsKey(substring + ".repoStatus")) {
                                        hashSet.add(substring);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TreeMap treeMap = new TreeMap();
        for (String str2 : hashSet) {
            String property = properties.getProperty(str2 + ".version");
            long a = a(properties.getProperty(str2 + ".buildDate"));
            long a2 = a(properties.getProperty(str2 + ".commitDate"));
            String property2 = properties.getProperty(str2 + ".shortCommitHash");
            String property3 = properties.getProperty(str2 + ".longCommitHash");
            treeMap.put(str2, new Version(str2, property, a, a2, property2, property3, properties.getProperty(str2 + ".repoStatus")));
        }
        return treeMap;
    }

    private static long a(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(str).getTime();
        } catch (ParseException unused) {
            return 0L;
        }
    }

    public static void main(String[] strArr) {
        for (Version version : identify().values()) {
            System.err.println(version);
        }
    }

    private Version(String str, String str2, long j, long j2, String str3, String str4, String str5) {
        this.a = str;
        this.b = str2;
        this.c = j;
        this.d = j2;
        this.e = str3;
        this.f = str4;
        this.g = str5;
    }

    public String artifactId() {
        return this.a;
    }

    public String artifactVersion() {
        return this.b;
    }

    public long buildTimeMillis() {
        return this.c;
    }

    public long commitTimeMillis() {
        return this.d;
    }

    public String shortCommitHash() {
        return this.e;
    }

    public String longCommitHash() {
        return this.f;
    }

    public String repositoryStatus() {
        return this.g;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append('-');
        sb.append(this.b);
        sb.append('.');
        sb.append(this.e);
        if ("clean".equals(this.g)) {
            str = "";
        } else {
            str = " (repository: " + this.g + ')';
        }
        sb.append(str);
        return sb.toString();
    }
}
