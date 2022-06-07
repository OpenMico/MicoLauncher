package com.xiaomi.mico.base.exts;

import android.os.Build;
import android.view.View;
import androidx.annotation.UiThread;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewExt.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0004H\u0007¨\u0006\u0007"}, d2 = {"focusable", "", "Landroid/view/View;", "f", "", "visibility", "visible", "share-lib_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes3.dex */
public final class ViewExtKt {
    @UiThread
    public static final void visibility(@NotNull View visibility, boolean z) {
        Intrinsics.checkNotNullParameter(visibility, "$this$visibility");
        visibility.setVisibility(z ? 0 : 8);
    }

    public static final void focusable(@NotNull View focusable, boolean z) {
        Intrinsics.checkNotNullParameter(focusable, "$this$focusable");
        if (Build.VERSION.SDK_INT >= 26) {
            focusable.setFocusable(z ? 1 : 0);
        } else {
            focusable.setFocusable(z);
        }
    }
}
