package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AuthProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "it", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AuthProvider$signInAndStoreAccount$2 extends Lambda implements Function1<AccountInfo, AccountInfo> {
    public static final AuthProvider$signInAndStoreAccount$2 INSTANCE = new AuthProvider$signInAndStoreAccount$2();

    AuthProvider$signInAndStoreAccount$2() {
        super(1);
    }

    @NotNull
    public final AccountInfo invoke(@NotNull AccountInfo it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        if (!SNSAuthProvider.Companion.isBindingSnsAccount()) {
            return it;
        }
        SNSBindParameter snsBindParameter = SNSAuthProvider.Companion.getSnsBindParameter();
        if (snsBindParameter == null) {
            Intrinsics.throwNpe();
        }
        NeedBindSnsException needBindSnsException = new NeedBindSnsException(snsBindParameter);
        SNSAuthProvider.Companion.invalidBindParameter();
        throw needBindSnsException;
    }
}
