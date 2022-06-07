package com.xiaomi.passport.ui.internal;

import android.graphics.Bitmap;
import android.util.Pair;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/passport/ui/internal/Captcha;", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportRepoImpl$getCaptchaImage$1 extends Lambda implements Function0<Captcha> {
    final /* synthetic */ String $url;
    final /* synthetic */ PassportRepoImpl this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PassportRepoImpl$getCaptchaImage$1(PassportRepoImpl passportRepoImpl, String str) {
        super(0);
        this.this$0 = passportRepoImpl;
        this.$url = str;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final Captcha invoke() {
        Pair captchaImageNullSafe;
        captchaImageNullSafe = this.this$0.getCaptchaImageNullSafe(this.$url);
        Object obj = captchaImageNullSafe.second;
        Intrinsics.checkExpressionValueIsNotNull(obj, "captcha.second");
        return new Captcha((Bitmap) captchaImageNullSafe.first, (String) obj, this.$url);
    }
}
