package com.xiaomi.phonenum.phone;

import android.content.Context;
import android.os.Build;

/* loaded from: classes4.dex */
public class PhoneInfoManager {
    private static PhoneUtil a;

    public static synchronized PhoneUtil getDefaultPhoneUtil(Context context) {
        synchronized (PhoneInfoManager.class) {
            Context applicationContext = context.getApplicationContext();
            if (a != null) {
                return a;
            } else if (Build.VERSION.SDK_INT <= 20) {
                a = new KKPhoneInfo(applicationContext);
                return a;
            } else {
                if (Build.VERSION.SDK_INT >= 26) {
                    a = new OPhoneInfo(applicationContext);
                } else if (Build.VERSION.SDK_INT >= 24) {
                    a = new NPhoneInfo(applicationContext);
                } else if (Build.VERSION.SDK_INT >= 23) {
                    a = new MPhoneInfo(applicationContext);
                } else if (Build.VERSION.SDK_INT >= 22) {
                    a = new LSPhoneInfo(applicationContext);
                } else if (Build.VERSION.SDK_INT >= 21) {
                    a = new LPhoneInfo(applicationContext);
                }
                return a;
            }
        }
    }

    public static void setPhoneUtil(PhoneUtil phoneUtil) {
        a = phoneUtil;
    }
}
