package com.jakewharton.rxbinding4.widget;

import android.widget.RadioGroup;
import androidx.annotation.CheckResult;
import com.xiaomi.onetrack.api.b;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: RadioGroupToggleCheckedConsumer.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007¨\u0006\u0004"}, d2 = {"checked", "Lio/reactivex/rxjava3/functions/Consumer;", "", "Landroid/widget/RadioGroup;", "rxbinding_release"}, k = 5, mv = {1, 1, 16}, xs = "com/jakewharton/rxbinding4/widget/RxRadioGroup")
/* loaded from: classes2.dex */
final /* synthetic */ class ac {

    /* compiled from: RadioGroupToggleCheckedConsumer.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", b.p, "", "kotlin.jvm.PlatformType", "accept", "(Ljava/lang/Integer;)V"}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    static final class a<T> implements Consumer<Integer> {
        final /* synthetic */ RadioGroup a;

        a(RadioGroup radioGroup) {
            this.a = radioGroup;
        }

        /* renamed from: a */
        public final void accept(Integer num) {
            if (num != null && num.intValue() == -1) {
                this.a.clearCheck();
                return;
            }
            RadioGroup radioGroup = this.a;
            if (num == null) {
                Intrinsics.throwNpe();
            }
            radioGroup.check(num.intValue());
        }
    }

    @CheckResult
    @NotNull
    public static final Consumer<? super Integer> a(@NotNull RadioGroup checked) {
        Intrinsics.checkParameterIsNotNull(checked, "$this$checked");
        return new a(checked);
    }
}
