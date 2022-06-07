package com.xiaomi.passport.servicetoken;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.Coder;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;

/* loaded from: classes4.dex */
class ServiceTokenUtilAM extends ServiceTokenUtilImplBase {
    private final IAMUtil amUtil;

    @Override // com.xiaomi.passport.servicetoken.IServiceTokenUtil
    public boolean fastCheckSlhPhCompatibility(Context context) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ServiceTokenUtilAM(IAMUtil iAMUtil) {
        if (iAMUtil != null) {
            this.amUtil = iAMUtil;
            return;
        }
        throw new IllegalArgumentException("amUtil == null");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.passport.servicetoken.ServiceTokenUtilImplBase
    public final ServiceTokenResult invalidateServiceTokenImpl(Context context, ServiceTokenResult serviceTokenResult) {
        if (this.amUtil.getXiaomiAccount(context) == null) {
            return noAccountErrorResult(serviceTokenResult.sid);
        }
        this.amUtil.invalidateAuthToken(context, AMAuthTokenConverter.buildAMAuthToken(serviceTokenResult));
        return new ServiceTokenResult.Builder(serviceTokenResult.sid).build();
    }

    @Override // com.xiaomi.passport.servicetoken.ServiceTokenUtilImplBase
    public final ServiceTokenResult getServiceTokenImpl(Context context, String str) {
        Account xiaomiAccount = this.amUtil.getXiaomiAccount(context);
        if (xiaomiAccount == null) {
            return noAccountErrorResult(str);
        }
        String peekAuthToken = this.amUtil.peekAuthToken(context, str, xiaomiAccount);
        if (!TextUtils.isEmpty(peekAuthToken)) {
            return addAccountAdditionalInfo(context, xiaomiAccount, AMAuthTokenConverter.parseAMAuthToken(str, peekAuthToken, true));
        }
        try {
            return addAccountAdditionalInfo(context, xiaomiAccount, AMAuthTokenConverter.fromAMBundle(this.amUtil.getAuthToken(context, str, xiaomiAccount).getResult(), str));
        } catch (Exception e) {
            return AMAuthTokenConverter.fromAMException(str, e);
        }
    }

    @Override // com.xiaomi.passport.servicetoken.ServiceTokenUtilImplBase
    protected XmAccountVisibility canAccessAccountImpl(Context context) {
        Account xiaomiAccount = this.amUtil.getXiaomiAccount(context);
        if (xiaomiAccount == null) {
            return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_NO_ACCOUNT, null).build();
        }
        return new XmAccountVisibility.Builder(XmAccountVisibility.ErrorCode.ERROR_NONE, null).accountVisible(true, xiaomiAccount).build();
    }

    private ServiceTokenResult noAccountErrorResult(String str) {
        return new ServiceTokenResult.Builder(str).errorCode(ServiceTokenResult.ErrorCode.ERROR_NO_ACCOUNT).build();
    }

    final ServiceTokenResult addAccountAdditionalInfo(Context context, Account account, ServiceTokenResult serviceTokenResult) {
        if (serviceTokenResult.errorCode != ServiceTokenResult.ErrorCode.ERROR_NONE || TextUtils.isEmpty(serviceTokenResult.sid) || TextUtils.isEmpty(serviceTokenResult.serviceToken)) {
            return serviceTokenResult;
        }
        String md5DigestUpperCase = Coder.getMd5DigestUpperCase(serviceTokenResult.serviceToken);
        String cUserId = this.amUtil.getCUserId(context, account);
        String checkAndGet = checkAndGet(md5DigestUpperCase, this.amUtil.getSlh(context, serviceTokenResult.sid, account));
        return new ServiceTokenResult.Builder(serviceTokenResult.sid).serviceToken(serviceTokenResult.serviceToken).security(serviceTokenResult.security).errorCode(serviceTokenResult.errorCode).errorMessage(serviceTokenResult.errorMessage).errorStackTrace(serviceTokenResult.errorStackTrace).peeked(serviceTokenResult.peeked).cUserId(cUserId).slh(checkAndGet).ph(checkAndGet(md5DigestUpperCase, this.amUtil.getPh(context, serviceTokenResult.sid, account))).userId(account.name).build();
    }

    static String checkAndGet(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        String[] split = str2.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length != 2 || !str.equalsIgnoreCase(split[0])) {
            return null;
        }
        return split[1];
    }
}
