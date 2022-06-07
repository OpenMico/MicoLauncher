package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class VersionUtil {
    private static final Pattern a = Pattern.compile("[-_./;:]");

    protected VersionUtil() {
    }

    @Deprecated
    public Version version() {
        return Version.unknownVersion();
    }

    public static Version versionFor(Class<?> cls) {
        Version packageVersionFor = packageVersionFor(cls);
        return packageVersionFor == null ? Version.unknownVersion() : packageVersionFor;
    }

    public static Version packageVersionFor(Class<?> cls) {
        Version version;
        Object obj;
        try {
            try {
                version = ((Versioned) Class.forName(cls.getPackage().getName() + ".PackageVersion", true, cls.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0])).version();
            } catch (Exception unused) {
                throw new IllegalArgumentException("Failed to get Versioned out of " + obj);
            }
        } catch (Exception unused2) {
            version = null;
        }
        return version == null ? Version.unknownVersion() : version;
    }

    @Deprecated
    public static Version mavenVersionFor(ClassLoader classLoader, String str, String str2) {
        InputStream resourceAsStream = classLoader.getResourceAsStream("META-INF/maven/" + str.replaceAll("\\.", "/") + "/" + str2 + "/pom.properties");
        if (resourceAsStream != null) {
            try {
                Properties properties = new Properties();
                properties.load(resourceAsStream);
                return parseVersion(properties.getProperty("version"), properties.getProperty("groupId"), properties.getProperty("artifactId"));
            } catch (IOException unused) {
            } finally {
                a(resourceAsStream);
            }
        }
        return Version.unknownVersion();
    }

    public static Version parseVersion(String str, String str2, String str3) {
        if (str != null) {
            String trim = str.trim();
            if (trim.length() > 0) {
                String[] split = a.split(trim);
                int i = 0;
                int parseVersionPart = parseVersionPart(split[0]);
                int parseVersionPart2 = split.length > 1 ? parseVersionPart(split[1]) : 0;
                if (split.length > 2) {
                    i = parseVersionPart(split[2]);
                }
                return new Version(parseVersionPart, parseVersionPart2, i, split.length > 3 ? split[3] : null, str2, str3);
            }
        }
        return Version.unknownVersion();
    }

    protected static int parseVersionPart(String str) {
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt > '9' || charAt < '0') {
                break;
            }
            i = (i * 10) + (charAt - '0');
        }
        return i;
    }

    private static final void a(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    public static final void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }
}
