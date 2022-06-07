package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExceptionsConstuctor.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¨\u0006\u0003"}, d2 = {"<anonymous>", "", "e", "kotlinx/coroutines/internal/ExceptionsConstuctorKt$safeCtor$1"}, k = 3, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3 extends Lambda implements Function1<Throwable, Throwable> {
    final /* synthetic */ Constructor $constructor$inlined;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3(Constructor constructor) {
        super(1);
        this.$constructor$inlined = constructor;
    }

    @Nullable
    public final Throwable invoke(@NotNull Throwable th) {
        Object obj;
        Object newInstance;
        try {
            Result.Companion companion = Result.Companion;
            newInstance = this.$constructor$inlined.newInstance(th.getMessage());
        } catch (Throwable th2) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m1220constructorimpl(ResultKt.createFailure(th2));
        }
        if (newInstance != null) {
            Throwable th3 = (Throwable) newInstance;
            th3.initCause(th);
            obj = Result.m1220constructorimpl(th3);
            if (Result.m1225isFailureimpl(obj)) {
                obj = null;
            }
            return (Throwable) obj;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
    }
}
