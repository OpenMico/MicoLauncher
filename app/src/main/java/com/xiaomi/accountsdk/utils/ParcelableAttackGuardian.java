package com.xiaomi.accountsdk.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.BadParcelableException;

/* loaded from: classes2.dex */
public class ParcelableAttackGuardian {
    private static final String TAG = "ParcelableAttackGuardia";

    public boolean safeCheck(Activity activity) {
        if (activity == null || activity.getIntent() == null) {
            return true;
        }
        try {
            unparcelIntent(new Intent(activity.getIntent()));
            return true;
        } catch (RuntimeException e) {
            if (e instanceof BadParcelableException) {
                AccountLog.w(TAG, "fail checking ParcelableAttack for Activity " + activity.getClass().getName());
                return false;
            } else if (e.getCause() instanceof ClassNotFoundException) {
                AccountLog.w(TAG, "fail checking SerializableAttack for Activity " + activity.getClass().getName());
                return false;
            } else {
                AccountLog.w(TAG, "error", e);
                return true;
            }
        }
    }

    void unparcelIntent(Intent intent) throws BadParcelableException {
        intent.getStringExtra("");
    }
}
