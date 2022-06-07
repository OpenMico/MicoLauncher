package androidx.core.os;

import android.os.Build;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";

    @NonNull
    public static String getStorageState(@NonNull File file) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Environment.getExternalStorageState(file);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return Environment.getStorageState(file);
        }
        try {
            return file.getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath()) ? Environment.getExternalStorageState() : "unknown";
        } catch (IOException e) {
            Log.w("EnvironmentCompat", "Failed to resolve canonical path: " + e);
            return "unknown";
        }
    }

    private EnvironmentCompat() {
    }
}
