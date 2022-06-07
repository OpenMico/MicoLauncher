package autodispose2.android.internal;

import android.os.Looper;
import androidx.annotation.RestrictTo;
import autodispose2.android.AutoDisposeAndroidPlugins;
import io.reactivex.rxjava3.functions.BooleanSupplier;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class AutoDisposeAndroidUtil {
    private static final BooleanSupplier a = $$Lambda$AutoDisposeAndroidUtil$WQ9MMMz9wy4BZK5EgE1yj8_9MuE.INSTANCE;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a() throws Throwable {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private AutoDisposeAndroidUtil() {
    }

    public static boolean isMainThread() {
        return AutoDisposeAndroidPlugins.onCheckMainThread(a);
    }
}
