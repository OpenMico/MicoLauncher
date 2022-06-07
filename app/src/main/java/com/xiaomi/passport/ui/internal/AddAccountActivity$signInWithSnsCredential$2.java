package com.xiaomi.passport.ui.internal;

import android.widget.Toast;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.AddAccountListener;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ActivityAddAccount.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AddAccountActivity$signInWithSnsCredential$2 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ AddAccountActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AddAccountActivity$signInWithSnsCredential$2(AddAccountActivity addAccountActivity) {
        super(1);
        this.this$0 = addAccountActivity;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull Throwable it) {
        CommonErrorHandler commonErrorHandler;
        WebAuth webAuth;
        BaseAuthProvider baseAuthProvider;
        WebAuth webAuth2;
        Intrinsics.checkParameterIsNotNull(it, "it");
        this.this$0.dismissProgress();
        if (it instanceof IOException) {
            this.this$0.showNetworkError((IOException) it);
        } else if (it instanceof NeedNotificationException) {
            AddAccountActivity addAccountActivity = this.this$0;
            webAuth2 = addAccountActivity.mWebAuth;
            String notificationUrl = ((NeedNotificationException) it).getNotificationUrl();
            Intrinsics.checkExpressionValueIsNotNull(notificationUrl, "it.notificationUrl");
            addAccountActivity.gotoFragment(webAuth2.getNotificationFragment(notificationUrl), true);
        } else if (it instanceof SNSRequest.NeedLoginForBindException) {
            AddAccountActivity addAccountActivity2 = this.this$0;
            baseAuthProvider = addAccountActivity2.defaultAuthProvider;
            AddAccountListener.DefaultImpls.gotoFragment$default(addAccountActivity2, baseAuthProvider.getFragment(AddAccountActivity.access$getMSid$p(this.this$0), null), false, 2, null);
        } else if (it instanceof SNSRequest.BindLimitException) {
            Toast.makeText(this.this$0, R.string.sns_bind_limit, 0).show();
        } else if (it instanceof SNSRequest.RedirectToWebLoginException) {
            AddAccountActivity addAccountActivity3 = this.this$0;
            webAuth = addAccountActivity3.mWebAuth;
            addAccountActivity3.gotoFragment(webAuth.getSnsWebLoginFragment((SNSRequest.RedirectToWebLoginException) it), true);
        } else {
            commonErrorHandler = this.this$0.mCommonErrorHandler;
            commonErrorHandler.onUnKnowError(it, this.this$0);
        }
    }
}
