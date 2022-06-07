package androidx.core.graphics;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import com.xiaomi.idm.service.iot.LightService;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: PorterDuff.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b\u001a\r\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0086\b¨\u0006\u0007"}, d2 = {"toColorFilter", "Landroid/graphics/PorterDuffColorFilter;", "Landroid/graphics/PorterDuff$Mode;", LightService.LightPropertyCommand.COLOR, "", "toXfermode", "Landroid/graphics/PorterDuffXfermode;", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PorterDuffKt {
    @NotNull
    public static final PorterDuffXfermode toXfermode(@NotNull PorterDuff.Mode mode) {
        Intrinsics.checkNotNullParameter(mode, "<this>");
        return new PorterDuffXfermode(mode);
    }

    @NotNull
    public static final PorterDuffColorFilter toColorFilter(@NotNull PorterDuff.Mode mode, int i) {
        Intrinsics.checkNotNullParameter(mode, "<this>");
        return new PorterDuffColorFilter(i, mode);
    }
}
