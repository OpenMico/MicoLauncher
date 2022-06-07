package com.xiaomi.passport.ui.settings.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class UserDataLocalStorage {
    private static final String USER_DATA_PREFERENCE_PREFIX = "local_user_data_";
    private final Context mContext;
    private final String mHashedUserId;

    /* JADX INFO: Access modifiers changed from: package-private */
    public UserDataLocalStorage(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("context or userId cannot be null");
        } else if (MiAccountManager.get(context).isUseSystem() || !PackageUtils.isDebuggable(context)) {
            this.mContext = context;
            this.mHashedUserId = getHashedUserId(str);
        } else {
            throw new IllegalStateException("cannot use this class when am.isUseSystem() is false");
        }
    }

    private static String getHashedUserId(String str) {
        try {
            return Base64Utils.encodeToString(MessageDigest.getInstance("MD5").digest(str.getBytes()));
        } catch (NoSuchAlgorithmException unused) {
            return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getUserData(String str) {
        String string = getSharedPreference().getString(Base64Utils.encodeToString(str), null);
        if (string == null) {
            return null;
        }
        return Base64Utils.decodeToString(string);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean setUserData(String str, String str2) {
        return getSharedPreference().edit().putString(Base64Utils.encodeToString(str), str2 == null ? null : Base64Utils.encodeToString(str2)).commit();
    }

    private SharedPreferences getSharedPreference() {
        Context context = this.mContext;
        return context.getSharedPreferences(USER_DATA_PREFERENCE_PREFIX + this.mHashedUserId, 0);
    }
}
