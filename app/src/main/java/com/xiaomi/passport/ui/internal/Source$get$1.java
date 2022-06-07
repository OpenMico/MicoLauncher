package com.xiaomi.passport.ui.internal;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.passport.ui.internal.Result;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Source.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "it", "Lcom/xiaomi/passport/ui/internal/Result;", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class Source$get$1 extends Lambda implements Function1<Result<V>, Unit> {
    final /* synthetic */ Function1 $fail;
    final /* synthetic */ Function1 $success;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Source$get$1(Function1 function1, Function1 function12) {
        super(1);
        this.$success = function1;
        this.$fail = function12;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((Result) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(@NotNull Result<V> it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        if (it instanceof Result.Success) {
            Function1 function1 = this.$success;
            Object value = it.getValue();
            if (value == null) {
                Intrinsics.throwNpe();
            }
            function1.invoke(value);
        } else if (it instanceof Result.Failure) {
            Function1 function12 = this.$fail;
            Throwable tr = it.getTr();
            if (tr == null) {
                Intrinsics.throwNpe();
            }
            function12.invoke(tr);
        }
    }
}
