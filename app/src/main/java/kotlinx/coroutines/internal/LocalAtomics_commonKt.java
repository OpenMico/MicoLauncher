package kotlinx.coroutines.internal;

import com.xiaomi.onetrack.api.b;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: LocalAtomics.common.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\".\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0000\u001a\u00020\u00018À\u0002@À\u0002X\u0080\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {b.p, "", "Ljava/util/concurrent/atomic/AtomicInteger;", "Lkotlinx/coroutines/internal/LocalAtomicInt;", "getValue", "(Ljava/util/concurrent/atomic/AtomicInteger;)I", "setValue", "(Ljava/util/concurrent/atomic/AtomicInteger;I)V", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class LocalAtomics_commonKt {
    public static final int getValue(@NotNull AtomicInteger atomicInteger) {
        return atomicInteger.get();
    }

    public static final void setValue(@NotNull AtomicInteger atomicInteger, int i) {
        atomicInteger.set(i);
    }
}
