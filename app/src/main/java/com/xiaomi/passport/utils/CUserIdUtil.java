package com.xiaomi.passport.utils;

import android.accounts.Account;
import android.content.Context;
import android.os.Looper;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.MiuiCUserIdUtil;
import com.xiaomi.passport.accountmanager.MiAccountManager;

/* loaded from: classes4.dex */
public class CUserIdUtil {
    private static final String TAG = "CUserIdUtil";
    private final Context mContext;

    public CUserIdUtil(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
            return;
        }
        throw new IllegalArgumentException("context == null");
    }

    public final String getCUserId() {
        if (!isInMainThread()) {
            MiAccountManager am = getAM();
            Account[] accountsByType = am.getAccountsByType("com.xiaomi");
            if (accountsByType.length == 0) {
                return null;
            }
            try {
                return am.getUserData(accountsByType[0], "encrypted_user_id");
            } catch (SecurityException unused) {
                AccountLog.d(TAG, "failed to getUserData");
                if (am.isUseSystem()) {
                    return getFromXiaomiAccountApp(accountsByType[0]);
                }
                throw new IllegalStateException("not supposed to be here");
            }
        } else {
            throw new IllegalStateException("CUserIdUtil#getCUserId() should NOT be called on main thread!");
        }
    }

    boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    String getFromXiaomiAccountApp(Account account) {
        return new MiuiCUserIdUtil(this.mContext, account).getCUserId();
    }

    MiAccountManager getAM() {
        return MiAccountManager.get(this.mContext);
    }
}
