package io.realm.internal;

import android.content.Context;
import com.getkeepsafe.relinker.ReLinker;
import io.realm.BuildConfig;
import java.io.File;
import java.util.Locale;

/* loaded from: classes5.dex */
public class RealmCore {
    private static final String a = File.separator;
    private static final String b = File.pathSeparator;
    private static final String c = "lib" + b + ".." + a + "lib";
    private static boolean d = false;

    public static boolean osIsWindows() {
        return System.getProperty("os.name").toLowerCase(Locale.getDefault()).contains("win");
    }

    public static synchronized void loadLibrary(Context context) {
        synchronized (RealmCore.class) {
            if (!d) {
                ReLinker.loadLibrary(context, "realm-jni", BuildConfig.VERSION_NAME);
                d = true;
            }
        }
    }

    public static void addNativeLibraryPath(String str) {
        try {
            System.setProperty("java.library.path", System.getProperty("java.library.path") + b + str + b);
        } catch (Exception e) {
            throw new RuntimeException("Cannot set the library path!", e);
        }
    }
}
