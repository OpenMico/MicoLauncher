package autodispose2.android;

import androidx.annotation.Nullable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BooleanSupplier;

/* loaded from: classes.dex */
public final class AutoDisposeAndroidPlugins {
    static volatile boolean a;
    @Nullable
    private static volatile BooleanSupplier b;

    private AutoDisposeAndroidPlugins() {
    }

    public static void lockdown() {
        a = true;
    }

    public static boolean isLockdown() {
        return a;
    }

    public static void setOnCheckMainThread(@Nullable BooleanSupplier booleanSupplier) {
        if (!a) {
            b = booleanSupplier;
            return;
        }
        throw new IllegalStateException("Plugins can't be changed anymore");
    }

    public static boolean onCheckMainThread(BooleanSupplier booleanSupplier) {
        if (booleanSupplier != null) {
            BooleanSupplier booleanSupplier2 = b;
            try {
                if (booleanSupplier2 == null) {
                    return booleanSupplier.getAsBoolean();
                }
                return booleanSupplier2.getAsBoolean();
            } catch (Throwable th) {
                throw Exceptions.propagate(th);
            }
        } else {
            throw new NullPointerException("defaultChecker == null");
        }
    }

    public static void reset() {
        setOnCheckMainThread(null);
    }
}
