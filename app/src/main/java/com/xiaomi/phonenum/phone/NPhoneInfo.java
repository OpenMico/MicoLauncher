package com.xiaomi.phonenum.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(api = 24)
/* loaded from: classes4.dex */
public class NPhoneInfo extends PhoneInfo {
    private SubscriptionManager a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public NPhoneInfo(Context context) {
        super(context);
        this.a = SubscriptionManager.from(context);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneCount() {
        return this.mTm.getPhoneCount();
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
        Object a;
        return i != -1 && (a = a("getDataEnabled", i)) != null && ((Boolean) a).booleanValue() && SubscriptionManager.getDefaultDataSubscriptionId() == i;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean isNetworkRoamingForSubId(int i) {
        if (i == -1) {
            return false;
        }
        return this.a.isNetworkRoaming(i);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneTypeForSubId(int i) {
        if (i == -1) {
            return 0;
        }
        return this.mTm.createForSubscriptionId(i).getPhoneType();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    @SuppressLint({"HardwareIds"})
    public String getIccid(int i) {
        if (i == -1) {
            return null;
        }
        return this.mTm.createForSubscriptionId(i).getSimSerialNumber();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    @SuppressLint({"HardwareIds"})
    protected String getImsi(int i) {
        if (i == -1) {
            return null;
        }
        return this.mTm.createForSubscriptionId(i).getSubscriberId();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    @SuppressLint({"HardwareIds"})
    protected String getLine1Number(int i) {
        if (i == -1) {
            return null;
        }
        return this.mTm.createForSubscriptionId(i).getLine1Number();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    protected String getMccMnc(int i) {
        if (i == -1) {
            return null;
        }
        return this.mTm.createForSubscriptionId(i).getSimOperator();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public String getNetworkMccMncForSubId(int i) {
        if (i == -1) {
            return null;
        }
        return this.mTm.createForSubscriptionId(i).getNetworkOperator();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean waitForServiceForSubId(int i, long j) throws InterruptedException {
        if (i == -1) {
            return false;
        }
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
