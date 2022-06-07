package androidx.core.provider;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

/* compiled from: CalleeHandler.java */
/* loaded from: classes.dex */
class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static Handler a() {
        if (Looper.myLooper() == null) {
            return new Handler(Looper.getMainLooper());
        }
        return new Handler();
    }
}
