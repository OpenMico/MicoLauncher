package com.xiaomi.passport.ui.internal;

import android.content.Context;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class SNSAuthProvider$signInWithAuthCredential$1 extends Lambda implements Function0<AccountInfo> {
    final /* synthetic */ Context $context;
    final /* synthetic */ AuthCredential $credential;
    final /* synthetic */ SNSAuthProvider this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SNSAuthProvider$signInWithAuthCredential$1(SNSAuthProvider sNSAuthProvider, AuthCredential authCredential, Context context) {
        super(0);
        this.this$0 = sNSAuthProvider;
        this.$credential = authCredential;
        this.$context = context;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final AccountInfo invoke() {
        AccountInfo signInWithSnsTokenAuthCredential;
        AccountInfo signInWithSnsCodeAuthCredential;
        try {
            AuthCredential authCredential = this.$credential;
            if (authCredential instanceof SnsCodeAuthCredential) {
                signInWithSnsCodeAuthCredential = this.this$0.signInWithSnsCodeAuthCredential(this.$context, (SnsCodeAuthCredential) this.$credential);
                return signInWithSnsCodeAuthCredential;
            } else if (authCredential instanceof SnsTokenAuthCredential) {
                signInWithSnsTokenAuthCredential = this.this$0.signInWithSnsTokenAuthCredential(this.$context, (SnsTokenAuthCredential) this.$credential);
                return signInWithSnsTokenAuthCredential;
            } else {
                throw new IllegalStateException("not support originAuthCredential:" + this.$credential);
            }
        } catch (SNSRequest.NeedLoginForBindException e) {
            SNSAuthProvider sNSAuthProvider = this.this$0;
            SNSBindParameter sNSBindParameter = e.getSNSBindParameter();
            Intrinsics.checkExpressionValueIsNotNull(sNSBindParameter, "e.snsBindParameter");
            sNSAuthProvider.storeBindParameter(sNSBindParameter);
            throw e;
        }
    }
}
