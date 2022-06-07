package com.xiaomi.mico.account.sdk;

import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public final class ExtendedAuthToken {
    public final String authToken;
    public final String security;

    private ExtendedAuthToken(String str, String str2) {
        this.authToken = str;
        this.security = str2;
    }

    public static ExtendedAuthToken build(String str, String str2) {
        return new ExtendedAuthToken(str, str2);
    }

    public static ExtendedAuthToken parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length != 2 || TextUtils.isEmpty(split[0]) || TextUtils.isEmpty(split[1])) {
            return null;
        }
        return new ExtendedAuthToken(split[0], split[1]);
    }

    public String toPlain() {
        return this.authToken + Constants.ACCEPT_TIME_SEPARATOR_SP + this.security;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ExtendedAuthToken extendedAuthToken = (ExtendedAuthToken) obj;
        String str = this.authToken;
        if (str != null) {
            if (!str.equals(extendedAuthToken.authToken)) {
                return false;
            }
        } else if (extendedAuthToken.authToken != null) {
            return false;
        }
        String str2 = this.security;
        if (str2 != null) {
            return str2.equals(extendedAuthToken.security);
        }
        return extendedAuthToken.security == null;
    }

    public int hashCode() {
        String str = this.authToken;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.security;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }
}
