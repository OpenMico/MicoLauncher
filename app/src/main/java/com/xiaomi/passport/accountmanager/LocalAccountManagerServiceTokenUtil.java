package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.xiaomi.accounts.AccountManager;
import com.xiaomi.accountsdk.futureservice.MiAccountManagerFuture;
import com.xiaomi.passport.servicetoken.AMKeys;
import com.xiaomi.passport.servicetoken.IAMUtil;
import com.xiaomi.passport.servicetoken.IServiceTokenUtil;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.ServiceTokenUtilFacade;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;
import com.xiaomi.passport.utils.AuthenticatorUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class LocalAccountManagerServiceTokenUtil implements IAMUtil, IServiceTokenUtil {
    private final AMKeys amKeys = new AMKeys();
    private final IServiceTokenUtil delegate = ServiceTokenUtilFacade.getInstance().buildAMServiceTokenUtil(this);

    @Override // com.xiaomi.passport.servicetoken.IAMUtil
    public AccountManagerFuture<Bundle> getAuthToken(Context context, String str, Account account) {
        return AccountManager.get(context).getAuthToken(account, str, (Bundle) null, (Activity) null, (AccountManagerCallback<Bundle>) null, (Handler) null);
    }

    @Override // com.xiaomi.passport.servicetoken.IAMUtil
    public String peekAuthToken(Context context, String str, Account account) {
        return AccountManager.get(context).peekAuthToken(account, str);
    }

    @Override // com.xiaomi.passport.servicetoken.IAMUtil
    public Account getXiaomiAccount(Context context) {
        return AuthenticatorUtil.getXiaomiAccount(context);
    }

    @Override // com.xiaomi.passport.servicetoken.IAMUtil
    public void invalidateAuthToken(Context context, String str) {
        AccountManager.get(context).invalidateAuthToken(this.amKeys.getType(), str);
    }

    @Override // com.xiaomi.passport.servicetoken.IAMUtil
    public String getCUserId(Context context, Account account) {
        return AccountManager.get(context).getUserData(account, this.amKeys.getAmUserDataKeyCUserId());
    }

    @Override // com.xiaomi.passport.servicetoken.IAMUtil
    public String getSlh(Context context, String str, Account account) {
        return AccountManager.get(context).getUserData(account, this.amKeys.getAmUserDataKeySlh(str));
    }

    @Override // com.xiaomi.passport.servicetoken.IAMUtil
    public String getPh(Context context, String str, Account account) {
        return AccountManager.get(context).getUserData(account, this.amKeys.getAmUserDataKeyPh(str));
    }

    @Override // com.xiaomi.passport.servicetoken.IServiceTokenUtil
    public ServiceTokenFuture getServiceToken(Context context, String str) {
        return this.delegate.getServiceToken(context, str);
    }

    @Override // com.xiaomi.passport.servicetoken.IServiceTokenUtil
    public ServiceTokenFuture invalidateServiceToken(Context context, ServiceTokenResult serviceTokenResult) {
        return this.delegate.invalidateServiceToken(context, serviceTokenResult);
    }

    @Override // com.xiaomi.passport.servicetoken.IServiceTokenUtil
    public boolean fastCheckSlhPhCompatibility(Context context) {
        return this.delegate.fastCheckSlhPhCompatibility(context);
    }

    @Override // com.xiaomi.passport.servicetoken.IServiceTokenUtil
    public MiAccountManagerFuture<XmAccountVisibility> canAccessAccount(Context context) {
        return this.delegate.canAccessAccount(context);
    }
}
