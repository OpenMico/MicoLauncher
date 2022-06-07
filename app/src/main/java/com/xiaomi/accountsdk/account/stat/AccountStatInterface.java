package com.xiaomi.accountsdk.account.stat;

import android.text.TextUtils;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class AccountStatInterface {
    private static AccountStatInterface sInstance = new EmptyAccountStatImplementation();

    public abstract void statCountEvent(String str, String str2);

    public abstract void statCountEvent(String str, String str2, Map<String, String> map);

    public abstract void statHttpEvent(String str, long j);

    public abstract void statHttpEvent(String str, Exception exc);

    public abstract void statPageEnd(String str);

    public abstract void statPageStart(String str);

    public static AccountStatInterface getInstance() {
        return sInstance;
    }

    public static void setInstance(AccountStatInterface accountStatInterface) {
        sInstance = accountStatInterface;
    }

    public static String dropUnusedUrlParams(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("http://dummyurl/")) {
            return str;
        }
        String[] split = str.split("\\?");
        return split.length >= 1 ? split[0] : str;
    }

    /* loaded from: classes2.dex */
    private static class EmptyAccountStatImplementation extends AccountStatInterface {
        @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
        public void statCountEvent(String str, String str2) {
        }

        @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
        public void statCountEvent(String str, String str2, Map<String, String> map) {
        }

        @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
        public void statHttpEvent(String str, long j) {
        }

        @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
        public void statHttpEvent(String str, Exception exc) {
        }

        @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
        public void statPageEnd(String str) {
        }

        @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
        public void statPageStart(String str) {
        }

        private EmptyAccountStatImplementation() {
        }
    }
}
