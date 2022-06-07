package com.xiaomi.passport.ui.internal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import java.security.InvalidParameterException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportRepoImpl$loadImage$1 extends Lambda implements Function0<Bitmap> {
    final /* synthetic */ String $url;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PassportRepoImpl$loadImage$1(String str) {
        super(0);
        this.$url = str;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    public final Bitmap invoke() {
        if (!TextUtils.isEmpty(this.$url)) {
            SimpleRequest.StreamContent c = SimpleRequestForAccount.getAsStream(this.$url, null, null);
            try {
                Intrinsics.checkExpressionValueIsNotNull(c, "c");
                return BitmapFactory.decodeStream(c.getStream());
            } finally {
                c.closeStream();
            }
        } else {
            throw new InvalidParameterException();
        }
    }
}
