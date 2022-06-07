package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExceptionsConstuctor.kt */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n"}, d2 = {"<anonymous>", "", "e"}, k = 3, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ExceptionsConstuctorKt$safeCtor$1 extends Lambda implements Function1<Throwable, Throwable> {
    final /* synthetic */ Function1<Throwable, Throwable> $block;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ExceptionsConstuctorKt$safeCtor$1(Function1<? super Throwable, ? extends Throwable> function1) {
        super(1);
        this.$block = function1;
    }

    @Nullable
    public final Throwable invoke(@NotNull Throwable th) {
        Object obj;
        Function1<Throwable, Throwable> function1 = this.$block;
        try {
            Result.Companion companion = Result.Companion;
            obj = Result.m1220constructorimpl(function1.invoke(th));
        } catch (Throwable th2) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m1220constructorimpl(ResultKt.createFailure(th2));
        }
        if (Result.m1225isFailureimpl(obj)) {
            obj = null;
        }
        return (Throwable) obj;
    }
}
