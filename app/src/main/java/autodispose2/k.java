package autodispose2;

import io.reactivex.rxjava3.annotations.Nullable;

/* compiled from: AutoDisposeUtil.java */
/* loaded from: classes.dex */
final class k {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> T a(@Nullable T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }
}
