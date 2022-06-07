package com.xiaomi.passport.utils;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.CloudCoder;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class AMPassTokenUpdateUtil {
    private static final int MAX_TIMES_PER_DAY = 100;
    private static final long MS_PER_DAY = TimeUnit.DAYS.toMillis(1);
    private static final String SP_KEY_DATE = "date";
    private static final String SP_KEY_TIMES = "frequency";
    private static final String TAG = "AMPassTokenUpdateUtil";
    private final SharedPreferencesUtil spUtil;

    public AMPassTokenUpdateUtil(Context context) {
        this.spUtil = new SharedPreferencesUtil(context, "passport_passtoken_update_util");
    }

    public boolean needUpdatePassToken(String str, AccountInfo accountInfo) {
        if (accountInfo == null) {
            return false;
        }
        String rePassToken = accountInfo.getRePassToken();
        if (TextUtils.isEmpty(rePassToken)) {
            return false;
        }
        synchronized (AMPassTokenUpdateUtil.class) {
            String md5DigestUpperCase = CloudCoder.getMd5DigestUpperCase(str);
            String passToken = accountInfo.getPassToken();
            String upperCase = rePassToken.toUpperCase();
            if (TextUtils.equals(passToken, str) || !TextUtils.equals(upperCase, md5DigestUpperCase) || !checkFrequency()) {
                return false;
            }
            incrementFrequency();
            AccountLog.d(TAG, "need to update password in AM");
            return true;
        }
    }

    private boolean checkFrequency() {
        return getSpDate() != nowDate() || getSpTimes() < 100;
    }

    private void incrementFrequency() {
        if (getSpDate() == nowDate()) {
            saveSpTimes(getSpTimes() + 1);
            return;
        }
        saveSpDate(nowDate());
        saveSpTimes(1);
    }

    private long nowDate() {
        return System.currentTimeMillis() / MS_PER_DAY;
    }

    private long getSpDate() {
        return this.spUtil.getLong("date", 0L);
    }

    private void saveSpDate(long j) {
        this.spUtil.saveLong("date", j);
    }

    private int getSpTimes() {
        return this.spUtil.getInt(SP_KEY_TIMES, 0);
    }

    private void saveSpTimes(int i) {
        this.spUtil.saveInt(SP_KEY_TIMES, i);
    }
}
