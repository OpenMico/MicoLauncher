package com.xiaomi.mico.base.exts;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ListExt.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"validateIndex", "", ExifInterface.GPS_DIRECTION_TRUE, "", MiSoundBoxCommandExtras.INDEX, "", "share-lib_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes3.dex */
public final class ListExtKt {
    public static final <T> boolean validateIndex(@NotNull List<? extends T> validateIndex, int i) {
        Intrinsics.checkNotNullParameter(validateIndex, "$this$validateIndex");
        return !validateIndex.isEmpty() && i >= 0 && i < validateIndex.size();
    }
}
