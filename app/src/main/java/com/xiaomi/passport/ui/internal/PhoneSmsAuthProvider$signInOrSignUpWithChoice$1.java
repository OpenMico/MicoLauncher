package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.AccountInfo;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AuthBaseProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhoneSmsAuthProvider$signInOrSignUpWithChoice$1 extends Lambda implements Function0<AccountInfo> {
    final /* synthetic */ ChoosePhoneSmsAuthCredential $credential;
    final /* synthetic */ PhoneSmsAuthProvider this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhoneSmsAuthProvider$signInOrSignUpWithChoice$1(PhoneSmsAuthProvider phoneSmsAuthProvider, ChoosePhoneSmsAuthCredential choosePhoneSmsAuthCredential) {
        super(0);
        this.this$0 = phoneSmsAuthProvider;
        this.$credential = choosePhoneSmsAuthCredential;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final AccountInfo invoke() {
        return this.this$0.getPassportRepo().signInWithPhone(this.$credential);
    }
}
