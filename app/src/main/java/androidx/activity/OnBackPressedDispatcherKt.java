package androidx.activity;

import androidx.lifecycle.LifecycleOwner;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OnBackPressedDispatcher.kt */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a9\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\b\n¨\u0006\u000b"}, d2 = {"addCallback", "Landroidx/activity/OnBackPressedCallback;", "Landroidx/activity/OnBackPressedDispatcher;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "enabled", "", "onBackPressed", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "activity-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class OnBackPressedDispatcherKt {
    public static /* synthetic */ OnBackPressedCallback addCallback$default(OnBackPressedDispatcher onBackPressedDispatcher, LifecycleOwner lifecycleOwner, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            lifecycleOwner = null;
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return addCallback(onBackPressedDispatcher, lifecycleOwner, z, function1);
    }

    @NotNull
    public static final OnBackPressedCallback addCallback(@NotNull OnBackPressedDispatcher onBackPressedDispatcher, @Nullable LifecycleOwner lifecycleOwner, final boolean z, @NotNull final Function1<? super OnBackPressedCallback, Unit> onBackPressed) {
        Intrinsics.checkNotNullParameter(onBackPressedDispatcher, "<this>");
        Intrinsics.checkNotNullParameter(onBackPressed, "onBackPressed");
        OnBackPressedCallback onBackPressedDispatcherKt$addCallback$callback$1 = new OnBackPressedCallback(onBackPressed, z) { // from class: androidx.activity.OnBackPressedDispatcherKt$addCallback$callback$1
            final /* synthetic */ Function1<OnBackPressedCallback, Unit> a;
            final /* synthetic */ boolean b;

            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(z);
                this.b = z;
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                this.a.invoke(this);
            }
        };
        if (lifecycleOwner != null) {
            onBackPressedDispatcher.addCallback(lifecycleOwner, onBackPressedDispatcherKt$addCallback$callback$1);
        } else {
            onBackPressedDispatcher.addCallback(onBackPressedDispatcherKt$addCallback$callback$1);
        }
        return onBackPressedDispatcherKt$addCallback$callback$1;
    }
}
