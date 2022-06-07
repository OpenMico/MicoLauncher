package com.xiaomi.passport.data;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.utils.CUserIdUtil;

/* loaded from: classes4.dex */
public class XMPassportInfo extends PassportInfo {
    private static final String TAG = "XMPassportInfo";

    private XMPassportInfo(String str, String str2, String str3, String str4, String str5) {
        super(str, str2, str3, str4, str5);
    }

    public static XMPassportInfo build(Context context, String str) {
        String str2 = TextUtils.isEmpty(str) ? "passportapi" : str;
        Context applicationContext = context.getApplicationContext();
        MiAccountManager miAccountManager = MiAccountManager.get(applicationContext);
        Account xiaomiAccount = miAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLog.i(TAG, "no xiaomi account");
            return null;
        }
        ServiceTokenResult serviceTokenResult = miAccountManager.getServiceToken(applicationContext, str2).get();
        if (serviceTokenResult == null) {
            AccountLog.e(TAG, "service token result is null");
            return null;
        } else if (serviceTokenResult.errorCode != ServiceTokenResult.ErrorCode.ERROR_NONE) {
            AccountLog.e(TAG, "service token result error code = " + serviceTokenResult.errorCode + " error msg: " + serviceTokenResult.errorMessage);
            return null;
        } else {
            String str3 = serviceTokenResult.cUserId;
            return new XMPassportInfo(xiaomiAccount.name, TextUtils.isEmpty(str3) ? new CUserIdUtil(applicationContext).getCUserId() : str3, str2, serviceTokenResult.serviceToken, serviceTokenResult.security);
        }
    }

    public void refreshAuthToken(Context context) {
        Context applicationContext = context.getApplicationContext();
        MiAccountManager miAccountManager = MiAccountManager.get(applicationContext);
        if (miAccountManager.getXiaomiAccount() == null) {
            AccountLog.i(TAG, "no xiaomi account");
            return;
        }
        miAccountManager.invalidateServiceToken(context, new ServiceTokenResult.Builder(getServiceId()).serviceToken(getServiceToken()).security(getSecurity()).build()).get();
        ServiceTokenResult serviceTokenResult = miAccountManager.getServiceToken(applicationContext, getServiceId()).get();
        if (serviceTokenResult == null) {
            AccountLog.e(TAG, "service token result is null");
        } else if (serviceTokenResult.errorCode != ServiceTokenResult.ErrorCode.ERROR_NONE) {
            AccountLog.e(TAG, "service token result error code = " + serviceTokenResult.errorCode);
        } else {
            setServiceToken(serviceTokenResult.serviceToken);
            setSecurity(serviceTokenResult.security);
        }
    }
}
