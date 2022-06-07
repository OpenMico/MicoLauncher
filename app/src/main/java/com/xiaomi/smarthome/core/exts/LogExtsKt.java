package com.xiaomi.smarthome.core.exts;

import com.elvishew.xlog.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: LogExts.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0002"}, d2 = {"appendLine", "Lcom/elvishew/xlog/Logger;", "smarthome_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class LogExtsKt {
    @NotNull
    public static final Logger appendLine(@NotNull Logger appendLine) {
        Intrinsics.checkNotNullParameter(appendLine, "$this$appendLine");
        appendLine.d("=================================================================================");
        return appendLine;
    }
}
