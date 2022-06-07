package autodispose2;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.functions.Consumer;

/* loaded from: classes.dex */
public final class AutoDisposePlugins {
    static volatile boolean a = false;
    static volatile boolean b = true;
    static volatile boolean c;
    @Nullable
    private static volatile Consumer<? super OutsideScopeException> d;

    private AutoDisposePlugins() {
    }

    public static void lockdown() {
        c = true;
    }

    public static boolean isLockdown() {
        return c;
    }

    public static boolean getHideProxies() {
        return b;
    }

    public static boolean getFillInOutsideScopeExceptionStacktraces() {
        return a;
    }

    @Nullable
    public static Consumer<? super OutsideScopeException> getOutsideScopeHandler() {
        return d;
    }

    public static void setOutsideScopeHandler(@Nullable Consumer<? super OutsideScopeException> consumer) {
        if (!c) {
            d = consumer;
            return;
        }
        throw new IllegalStateException("Plugins can't be changed anymore");
    }

    public static void setFillInOutsideScopeExceptionStacktraces(boolean z) {
        if (!c) {
            a = z;
            return;
        }
        throw new IllegalStateException("Plugins can't be changed anymore");
    }

    public static void setHideProxies(boolean z) {
        if (!c) {
            b = z;
            return;
        }
        throw new IllegalStateException("Plugins can't be changed anymore");
    }

    public static void reset() {
        setOutsideScopeHandler(null);
    }
}
