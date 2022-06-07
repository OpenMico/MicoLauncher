package kotlinx.coroutines.internal;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SystemProps.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0007"}, d2 = {"AVAILABLE_PROCESSORS", "", "getAVAILABLE_PROCESSORS", "()I", "systemProp", "", "propertyName", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/internal/SystemPropsKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class c {
    private static final int a = Runtime.getRuntime().availableProcessors();

    public static final int a() {
        return a;
    }

    @Nullable
    public static final String a(@NotNull String str) {
        try {
            return System.getProperty(str);
        } catch (SecurityException unused) {
            return null;
        }
    }
}
