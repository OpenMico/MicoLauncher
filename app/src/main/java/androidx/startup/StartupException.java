package androidx.startup;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class StartupException extends RuntimeException {
    public StartupException(@NonNull String str) {
        super(str);
    }

    public StartupException(@NonNull Throwable th) {
        super(th);
    }

    public StartupException(@NonNull String str, @NonNull Throwable th) {
        super(str, th);
    }
}
