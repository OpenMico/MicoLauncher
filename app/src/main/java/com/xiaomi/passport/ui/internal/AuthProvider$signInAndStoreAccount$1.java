package com.xiaomi.passport.ui.internal;

import android.content.Context;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AuthProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "it", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AuthProvider$signInAndStoreAccount$1 extends Lambda implements Function1<AccountInfo, AccountInfo> {
    final /* synthetic */ Context $context;
    final /* synthetic */ AuthProvider this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthProvider$signInAndStoreAccount$1(AuthProvider authProvider, Context context) {
        super(1);
        this.this$0 = authProvider;
        this.$context = context;
    }

    @NotNull
    public final AccountInfo invoke(@NotNull AccountInfo it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        this.this$0.storePassToken(this.$context, it);
        return it;
    }
}
