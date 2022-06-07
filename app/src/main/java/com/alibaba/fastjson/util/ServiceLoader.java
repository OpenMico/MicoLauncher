package com.alibaba.fastjson.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class ServiceLoader {
    private static final String PREFIX = "META-INF/services/";
    private static final Set<String> loadedUrls = new HashSet();

    public static <T> Set<T> load(Class<T> cls, ClassLoader classLoader) {
        if (classLoader == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        String str = PREFIX + cls.getName();
        HashSet<String> hashSet2 = new HashSet();
        try {
            Enumeration<URL> resources = classLoader.getResources(str);
            while (resources.hasMoreElements()) {
                URL nextElement = resources.nextElement();
                if (!loadedUrls.contains(nextElement.toString())) {
                    load(nextElement, hashSet2);
                    loadedUrls.add(nextElement.toString());
                }
            }
        } catch (Throwable unused) {
        }
        for (String str2 : hashSet2) {
            try {
                hashSet.add(classLoader.loadClass(str2).newInstance());
            } catch (Exception unused2) {
            }
        }
        return hashSet;
    }

    public static void load(URL url, Set<String> set) throws IOException {
        Throwable th;
        InputStream inputStream;
        BufferedReader bufferedReader = null;
        try {
            inputStream = url.openStream();
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine == null) {
                            IOUtils.close(bufferedReader2);
                            IOUtils.close(inputStream);
                            return;
                        }
                        int indexOf = readLine.indexOf(35);
                        if (indexOf >= 0) {
                            readLine = readLine.substring(0, indexOf);
                        }
                        String trim = readLine.trim();
                        if (trim.length() != 0) {
                            set.add(trim);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader = bufferedReader2;
                        IOUtils.close(bufferedReader);
                        IOUtils.close(inputStream);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
        }
    }
}
