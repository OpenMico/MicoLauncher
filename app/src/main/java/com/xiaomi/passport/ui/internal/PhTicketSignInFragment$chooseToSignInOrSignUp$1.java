package com.xiaomi.passport.ui.internal;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.passport.ui.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: FragmentPhTicketAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Landroid/graphics/Bitmap;", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PhTicketSignInFragment$chooseToSignInOrSignUp$1 extends Lambda implements Function1<Bitmap, Unit> {
    final /* synthetic */ View $view;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhTicketSignInFragment$chooseToSignInOrSignUp$1(View view) {
        super(1);
        this.$view = view;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Bitmap bitmap) {
        invoke2(bitmap);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull Bitmap it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        View view = this.$view;
        ImageView imageView = (ImageView) (view != null ? view.findViewById(R.id.image_user_avatar) : null);
        if (imageView != null) {
            imageView.setImageBitmap(it);
        }
    }
}
