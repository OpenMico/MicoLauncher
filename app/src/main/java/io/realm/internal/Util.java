package io.realm.internal;

import android.os.Build;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class Util {
    static native String nativeGetTablePrefix();

    public static String getTablePrefix() {
        return nativeGetTablePrefix();
    }

    public static Class<? extends RealmModel> getOriginalModelClass(Class<? extends RealmModel> cls) {
        if (cls.equals(RealmModel.class) || cls.equals(RealmObject.class)) {
            throw new IllegalArgumentException("RealmModel or RealmObject was passed as an argument. Only subclasses of these can be used as arguments to methods that accept a Realm model class.");
        }
        Class superclass = cls.getSuperclass();
        return (superclass.equals(Object.class) || superclass.equals(RealmObject.class)) ? cls : superclass;
    }

    public static String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter((Writer) stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT);
    }

    public static boolean isEmptyString(@Nullable String str) {
        return str == null || str.length() == 0;
    }

    public static boolean deleteRealm(String str, File file, String str2) {
        boolean z;
        File file2 = new File(file, str2 + ".management");
        File file3 = new File(str);
        File file4 = new File(str + ".note");
        File[] listFiles = file2.listFiles();
        if (listFiles != null) {
            for (File file5 : listFiles) {
                if (!file5.delete()) {
                    RealmLog.warn(String.format(Locale.ENGLISH, "Realm temporary file at %s cannot be deleted", file5.getAbsolutePath()), new Object[0]);
                }
            }
        }
        if (file2.exists() && !file2.delete()) {
            RealmLog.warn(String.format(Locale.ENGLISH, "Realm temporary folder at %s cannot be deleted", file2.getAbsolutePath()), new Object[0]);
        }
        if (file3.exists()) {
            z = file3.delete();
            if (!z) {
                RealmLog.warn(String.format(Locale.ENGLISH, "Realm file at %s cannot be deleted", file3.getAbsolutePath()), new Object[0]);
            }
        } else {
            z = true;
        }
        if (file4.exists() && !file4.delete()) {
            RealmLog.warn(String.format(Locale.ENGLISH, ".note file at %s cannot be deleted", file4.getAbsolutePath()), new Object[0]);
        }
        return z;
    }

    public static <T> Set<T> toSet(T... tArr) {
        if (tArr == null) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (T t : tArr) {
            if (t != null) {
                linkedHashSet.add(t);
            }
        }
        return linkedHashSet;
    }
}
