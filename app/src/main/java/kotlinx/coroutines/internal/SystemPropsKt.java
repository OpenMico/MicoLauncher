package kotlinx.coroutines.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"kotlinx/coroutines/internal/SystemPropsKt__SystemPropsKt", "kotlinx/coroutines/internal/SystemPropsKt__SystemProps_commonKt"}, k = 4, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class SystemPropsKt {
    public static final int getAVAILABLE_PROCESSORS() {
        return c.a();
    }

    public static final int systemProp(@NotNull String str, int i, int i2, int i3) {
        return d.a(str, i, i2, i3);
    }

    public static final long systemProp(@NotNull String str, long j, long j2, long j3) {
        return d.a(str, j, j2, j3);
    }

    @Nullable
    public static final String systemProp(@NotNull String str) {
        return c.a(str);
    }

    public static final boolean systemProp(@NotNull String str, boolean z) {
        return d.a(str, z);
    }
}
