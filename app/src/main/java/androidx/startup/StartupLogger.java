package androidx.startup;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class StartupLogger {
    private StartupLogger() {
    }

    public static void i(@NonNull String str) {
        Log.i("StartupLogger", str);
    }

    public static void e(@NonNull String str, @Nullable Throwable th) {
        Log.e("StartupLogger", str, th);
    }
}
