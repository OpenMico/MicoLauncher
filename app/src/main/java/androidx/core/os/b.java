package androidx.core.os;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;

/* compiled from: LocaleListInterface.java */
/* loaded from: classes.dex */
interface b {
    @IntRange(from = -1)
    int a(Locale locale);

    Object a();

    Locale a(int i);

    @Nullable
    Locale a(@NonNull String[] strArr);

    boolean b();

    @IntRange(from = 0)
    int c();

    String d();
}
