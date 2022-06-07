package com.xiaomi.miot.host.lan.impl.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes2.dex */
public class OTKeyExceptionMonitor {
    private static final long KEY_EXCEPTION_CONTINUED_TIME = 28800000;
    private static final String KEY_START_EXCEPTION = "key_start_exception";
    private static final String PREF_NAME = "ot_key_exception_monitor";
    private static final String TAG = "OTKeyExceptionMonitor";
    private static volatile OTKeyExceptionMonitor sInstance;
    private Context mContext;
    private SharedPreferences mSharedPrefs;

    private OTKeyExceptionMonitor(Context context) {
        this.mContext = context.getApplicationContext();
        this.mSharedPrefs = this.mContext.getSharedPreferences(PREF_NAME, 0);
    }

    public static OTKeyExceptionMonitor getInstance(Context context) {
        if (sInstance == null) {
            synchronized (OTKeyExceptionMonitor.class) {
                if (sInstance == null) {
                    sInstance = new OTKeyExceptionMonitor(context);
                }
            }
        }
        return sInstance;
    }

    public synchronized void abnormalDisconnection() {
        long j = this.mSharedPrefs.getLong(KEY_START_EXCEPTION, 0L);
        long currentTimeMillis = System.currentTimeMillis();
        if (j == 0 || j > currentTimeMillis) {
            SharedPreferences.Editor edit = this.mSharedPrefs.edit();
            edit.putLong(KEY_START_EXCEPTION, currentTimeMillis);
            edit.apply();
        }
    }

    public synchronized void clearException() {
        SharedPreferences.Editor edit = this.mSharedPrefs.edit();
        edit.putLong(KEY_START_EXCEPTION, 0L);
        edit.apply();
    }

    public synchronized boolean isKeyInvalid() {
        long currentTimeMillis;
        long j;
        int i;
        currentTimeMillis = System.currentTimeMillis();
        j = this.mSharedPrefs.getLong(KEY_START_EXCEPTION, 0L);
        i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i != 0) {
            Log.d(TAG, "isKeyInvalid, exception continued time = " + getTimeString(currentTimeMillis - j));
        }
        return i != 0 && currentTimeMillis - j > KEY_EXCEPTION_CONTINUED_TIME;
    }

    private String getTimeString(long j) {
        long j2 = j / 1000;
        long j3 = j2 % 3600;
        return ((int) (j2 / 3600)) + Constants.COLON_SEPARATOR + ((int) (j3 / 60)) + Constants.COLON_SEPARATOR + ((int) (j3 % 60));
    }
}
