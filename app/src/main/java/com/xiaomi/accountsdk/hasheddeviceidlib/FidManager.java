package com.xiaomi.accountsdk.hasheddeviceidlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.FidSigningUtil;

/* loaded from: classes2.dex */
public class FidManager {
    private static final String FID_KEY = "fid";
    private static final String PREFERENCES_NAME = "passport_fid_manager";
    private static volatile FidManager instance;

    public static FidManager getInstance() {
        if (instance == null) {
            synchronized (FidManager.class) {
                if (instance == null) {
                    instance = new FidManager();
                }
            }
        }
        return instance;
    }

    public String getFid(Context context) throws FidSigningUtil.FidSignException {
        String fidFromCached = getFidFromCached(context);
        if (fidFromCached == null) {
            FidSigningUtil.IFidSigner fidSigner = FidSigningUtil.getFidSigner();
            if (fidSigner instanceof FidSigningUtil.IFidSignerExtension) {
                fidFromCached = ((FidSigningUtil.IFidSignerExtension) fidSigner).getFidDirect();
            } else {
                fidFromCached = fidSigner.getFid();
            }
            if (!TextUtils.isEmpty(fidFromCached)) {
                cacheFid(context, fidFromCached);
            }
        }
        return fidFromCached;
    }

    private FidManager() {
    }

    private String getFidFromCached(Context context) {
        return getSharedPreference(context).getString(FID_KEY, null);
    }

    private void cacheFid(Context context, String str) {
        getSharedPreference(context).edit().putString(FID_KEY, str).apply();
    }

    private static SharedPreferences getSharedPreference(Context context) {
        if (context != null) {
            return context.getSharedPreferences(PREFERENCES_NAME, 0);
        }
        throw new IllegalArgumentException("context is null");
    }
}
