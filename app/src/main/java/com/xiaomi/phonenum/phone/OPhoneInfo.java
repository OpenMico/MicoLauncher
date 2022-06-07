package com.xiaomi.phonenum.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 26)
/* loaded from: classes4.dex */
public class OPhoneInfo extends PhoneInfo {
    private SubscriptionManager a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public OPhoneInfo(Context context) {
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
        if (i == -1) {
            return false;
        }
        return this.mTm.createForSubscriptionId(i).isDataEnabled();
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
}
