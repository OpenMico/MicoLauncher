package com.xiaomi.phonenum.phone;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(api = 23)
/* loaded from: classes4.dex */
public class MPhoneInfo extends PhoneInfo {
    private SubscriptionManager a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MPhoneInfo(Context context) {
        super(context);
        this.a = SubscriptionManager.from(context);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneCount() {
        return this.mTm.getPhoneCount();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean getDataEnabledForSubId(int i) {
        Object a = a("getDataEnabled", i);
        if (a == null) {
            return false;
        }
        return ((Boolean) a).booleanValue();
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
}
