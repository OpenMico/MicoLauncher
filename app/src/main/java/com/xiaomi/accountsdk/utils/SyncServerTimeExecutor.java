package com.xiaomi.accountsdk.utils;

import android.os.SystemClock;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class SyncServerTimeExecutor {
    private static final String TAG = "SyncServerTimeExecutor";
    private static final String URL = URLs.URL_ACOUNT_API_BASE_SECURE + "/configuration";
    private static final SyncServerTimeExecutor instance = new SyncServerTimeExecutor();
    private long timeCorrectionMillis;
    private final Executor syncExecutor = Executors.newSingleThreadExecutor();
    private final Object mLock = new Object();

    private SyncServerTimeExecutor() {
    }

    public static SyncServerTimeExecutor getInstance() {
        return instance;
    }

    public void runBackgroundSyncAndPostResult() {
        this.syncExecutor.execute(new Runnable() { // from class: com.xiaomi.accountsdk.utils.SyncServerTimeExecutor.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SimpleRequest.getAsString(SyncServerTimeExecutor.URL, null, null, true);
                } catch (AccessDeniedException e) {
                    AccountLog.e(SyncServerTimeExecutor.TAG, "syncServerTime", e);
                } catch (AuthenticationFailureException e2) {
                    AccountLog.e(SyncServerTimeExecutor.TAG, "syncServerTime", e2);
                } catch (IOException e3) {
                    AccountLog.e(SyncServerTimeExecutor.TAG, "syncServerTime", e3);
                }
            }
        });
    }

    public long getCurrentServerTimeMillis() {
        if (this.timeCorrectionMillis == 0) {
            return System.currentTimeMillis();
        }
        return SystemClock.elapsedRealtime() + this.timeCorrectionMillis;
    }

    public void syncServerTime(Date date) {
        if (date == null) {
            AccountLog.w(TAG, "server date is null");
            return;
        }
        long time = date.getTime() - SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            if (time != this.timeCorrectionMillis) {
                this.timeCorrectionMillis = time;
            }
        }
    }
}
