package androidx.fragment.app;

import android.os.Bundle;
import androidx.annotation.NonNull;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Fragment.kt */
@Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 1})
/* loaded from: classes.dex */
final class e implements FragmentResultListener {
    private final /* synthetic */ Function2 a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Function2 function2) {
        this.a = function2;
    }

    @Override // androidx.fragment.app.FragmentResultListener
    public final /* synthetic */ void onFragmentResult(@NonNull String p0, @NonNull Bundle p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullExpressionValue(this.a.invoke(p0, p1), "invoke(...)");
    }
}
