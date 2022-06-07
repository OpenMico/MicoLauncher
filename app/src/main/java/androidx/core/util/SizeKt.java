package androidx.core.util;

import android.util.Size;
import android.util.SizeF;
import androidx.annotation.RequiresApi;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Size.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\n\u001a\r\u0010\u0000\u001a\u00020\u0003*\u00020\u0004H\u0087\n\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0002H\u0087\n\u001a\r\u0010\u0005\u001a\u00020\u0003*\u00020\u0004H\u0087\n¨\u0006\u0006"}, d2 = {"component1", "", "Landroid/util/Size;", "", "Landroid/util/SizeF;", "component2", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class SizeKt {
    @RequiresApi(21)
    public static final int component1(@NotNull Size size) {
        Intrinsics.checkNotNullParameter(size, "<this>");
        return size.getWidth();
    }

    @RequiresApi(21)
    public static final int component2(@NotNull Size size) {
        Intrinsics.checkNotNullParameter(size, "<this>");
        return size.getHeight();
    }

    @RequiresApi(21)
    public static final float component1(@NotNull SizeF sizeF) {
        Intrinsics.checkNotNullParameter(sizeF, "<this>");
        return sizeF.getWidth();
    }

    @RequiresApi(21)
    public static final float component2(@NotNull SizeF sizeF) {
        Intrinsics.checkNotNullParameter(sizeF, "<this>");
        return sizeF.getHeight();
    }
}
