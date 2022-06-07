package com.xiaomi.phonenum.phone;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(api = 22)
/* loaded from: classes4.dex */
public class LSPhoneInfo extends PhoneInfo {
    private SubscriptionManager a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public LSPhoneInfo(Context context) {
        super(context);
        this.a = SubscriptionManager.from(context);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneCount() {
        Object a = a("getSimCount");
        if (a == null) {
            return 0;
        }
        return ((Integer) a).intValue();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getSubIdForSlotId(int i) {
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = this.a.getActiveSubscriptionInfoForSimSlotIndex(i);
        if (activeSubscriptionInfoForSimSlotIndex != null) {
            return activeSubscriptionInfoForSimSlotIndex.getSubscriptionId();
        }
        return -1;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean getDataEnabledForSubId(int i) {
        return i >= 0 && a() && i == b();
    }

    private boolean a() {
        Object a = a("getDataEnabled");
        if (a == null) {
            return false;
        }
        return ((Boolean) a).booleanValue();
    }

    public int getSlotIdForSubId(int i) {
        SubscriptionInfo activeSubscriptionInfo = this.a.getActiveSubscriptionInfo(i);
        if (activeSubscriptionInfo == null) {
            return -1;
        }
        return activeSubscriptionInfo.getSimSlotIndex();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean isNetworkRoamingForSubId(int i) {
        return this.a.isNetworkRoaming(i);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    public String getIccid(int i) {
        SubscriptionInfo activeSubscriptionInfo;
        if (i == -1 || (activeSubscriptionInfo = this.a.getActiveSubscriptionInfo(i)) == null) {
            return null;
        }
        return activeSubscriptionInfo.getIccId();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    protected String getImsi(int i) {
        return (String) a("getSubscriberId", i);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    protected String getMccMnc(int i) {
        return (String) a("getSimOperator", i);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    protected String getLine1Number(int i) {
        return (String) a("getLine1NumberForSubscriber", i);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public String getNetworkMccMncForSubId(int i) {
        return (String) a("getNetworkOperatorForSubscription", i);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneTypeForSubId(int i) {
        Object a = a("getCurrentPhoneType", i);
        if (a == null) {
            return 1;
        }
        return ((Integer) a).intValue();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean waitForServiceForSubId(int i, long j) throws InterruptedException {
        return a.a(this.mContext, i, j);
    }

    private int b() {
        try {
            Method method = Class.forName("android.telephony.SubscriptionManager").getMethod("getDefaultDataSubId", new Class[0]);
            method.setAccessible(true);
            Integer num = (Integer) method.invoke(null, new Object[0]);
            if (num != null) {
                return num.intValue();
            }
            return -1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return -1;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return -1;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return -1;
        }
    }

    private Object a(String str, int i) {
        try {
            Method method = this.mTm.getClass().getMethod(str, Integer.TYPE);
            method.setAccessible(true);
            return method.invoke(this.mTm, Integer.valueOf(i));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (SecurityException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private Object a(String str) {
        try {
            Method method = this.mTm.getClass().getMethod(str, new Class[0]);
            method.setAccessible(true);
            return method.invoke(this.mTm, new Object[0]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (SecurityException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }
}
