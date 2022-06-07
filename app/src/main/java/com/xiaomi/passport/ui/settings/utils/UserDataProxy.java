package com.xiaomi.passport.ui.settings.utils;

import android.accounts.Account;
import android.content.Context;
import com.xiaomi.passport.accountmanager.MiAccountManager;

/* loaded from: classes4.dex */
public class UserDataProxy {
    private final Context mContext;
    private final MiAccountManager mMiAccountManager;

    public UserDataProxy(Context context) {
        if (context != null) {
            this.mContext = context;
            this.mMiAccountManager = MiAccountManager.get(context);
            return;
        }
        throw new IllegalArgumentException("context is null or userId is null");
    }

    public void setUserData(Account account, String str, String str2) {
        if (account == null) {
            throw new IllegalArgumentException("account cannot be null");
        } else if (this.mMiAccountManager.isUseLocal()) {
            this.mMiAccountManager.setUserData(account, str, str2);
        } else {
            new UserDataLocalStorage(this.mContext, account.name).setUserData(str, str2);
        }
    }

    public String getUserData(Account account, String str) {
        if (account == null) {
            throw new IllegalArgumentException("account cannot be null");
        } else if (this.mMiAccountManager.isUseLocal()) {
            return this.mMiAccountManager.getUserData(account, str);
        } else {
            return new UserDataLocalStorage(this.mContext, account.name).getUserData(str);
        }
    }
}
