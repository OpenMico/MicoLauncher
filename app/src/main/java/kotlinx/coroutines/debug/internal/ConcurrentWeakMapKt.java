package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.Symbol;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConcurrentWeakMap.kt */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0000\n\u0000\u001a\b\u0010\b\u001a\u00020\tH\u0002\u001a\u000e\u0010\n\u001a\u00020\u0003*\u0004\u0018\u00010\u000bH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"MAGIC", "", "MARKED_NULL", "Lkotlinx/coroutines/debug/internal/Marked;", "MARKED_TRUE", "MIN_CAPACITY", "REHASH", "Lkotlinx/coroutines/internal/Symbol;", "noImpl", "", "mark", "", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ConcurrentWeakMapKt {
    @NotNull
    private static final Symbol a = new Symbol("REHASH");
    @NotNull
    private static final b b = new b(null);
    @NotNull
    private static final b c = new b(true);

    public static final /* synthetic */ Symbol access$getREHASH$p() {
        return a;
    }

    public static final /* synthetic */ b access$mark(Object obj) {
        return a(obj);
    }

    public static final /* synthetic */ Void access$noImpl() {
        return a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final b a(Object obj) {
        if (obj == null) {
            return b;
        }
        return Intrinsics.areEqual(obj, (Object) true) ? c : new b(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void a() {
        throw new UnsupportedOperationException("not implemented");
    }
}
