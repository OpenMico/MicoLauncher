package androidx.core.transition;

import android.transition.Transition;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: Transition.kt */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "it", "Landroid/transition/Transition;"}, k = 3, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class TransitionKt$addListener$3 extends Lambda implements Function1<Transition, Unit> {
    public static final TransitionKt$addListener$3 INSTANCE = new TransitionKt$addListener$3();

    public TransitionKt$addListener$3() {
        super(1);
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull Transition it) {
        Intrinsics.checkNotNullParameter(it, "it");
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Transition transition) {
        invoke2(transition);
        return Unit.INSTANCE;
    }
}
