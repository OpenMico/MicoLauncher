package com.xiaomi.passport.servicetoken;

import android.accounts.Account;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Bundle;

/* loaded from: classes4.dex */
public interface IAMUtil {
    AccountManagerFuture<Bundle> getAuthToken(Context context, String str, Account account);

    String getCUserId(Context context, Account account);

    String getPh(Context context, String str, Account account);

    String getSlh(Context context, String str, Account account);

    Account getXiaomiAccount(Context context);

    void invalidateAuthToken(Context context, String str);

    String peekAuthToken(Context context, String str, Account account);
}
