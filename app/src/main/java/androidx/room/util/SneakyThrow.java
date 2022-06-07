package androidx.room.util;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SneakyThrow {
    public static void reThrow(@NonNull Exception exc) {
        a(exc);
    }

    private static <E extends Throwable> void a(@NonNull Throwable th) throws Throwable {
        throw th;
    }

    private SneakyThrow() {
    }
}
