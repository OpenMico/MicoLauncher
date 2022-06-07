package com.xiaomi.passport.ui.internal;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.accountsdk.utils.AccountLog;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Source.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "it", "", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class Source$getSuccess$1 extends Lambda implements Function1<Throwable, Unit> {
    public static final Source$getSuccess$1 INSTANCE = new Source$getSuccess$1();

    Source$getSuccess$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull Throwable it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        if (SourceTool.Companion.getENABLE_TEST()) {
            it.printStackTrace();
        } else {
            AccountLog.e("Source", "request fail", it);
        }
    }
}
