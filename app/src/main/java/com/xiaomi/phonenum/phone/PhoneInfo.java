package com.xiaomi.phonenum.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xiaomi.phonenum.bean.Sim;

/* loaded from: classes4.dex */
public abstract class PhoneInfo implements PhoneUtil {
    private ConnectivityManager a;
    protected Context mContext;
    protected TelephonyManager mTm;

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public abstract boolean getDataEnabledForSubId(int i);

    protected abstract String getIccid(int i);

    protected abstract String getImsi(int i);

    protected abstract String getLine1Number(int i);

    protected abstract String getMccMnc(int i);

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public abstract String getNetworkMccMncForSubId(int i);

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public abstract int getPhoneCount();

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public abstract int getPhoneTypeForSubId(int i);

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public abstract int getSubIdForSlotId(int i);

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public abstract boolean isNetworkRoamingForSubId(int i);

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public abstract boolean waitForServiceForSubId(int i, long j) throws InterruptedException;

    public PhoneInfo(Context context) {
        this.mContext = context;
        this.mTm = (TelephonyManager) context.getSystemService("phone");
        this.a = (ConnectivityManager) context.getSystemService("connectivity");
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public boolean isNetWorkTypeMobile() {
        return this.a.getActiveNetworkInfo().getType() == 0;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    @SuppressLint({"HardwareIds"})
    public String getImei() {
        try {
            return this.mTm.getDeviceId();
        } catch (SecurityException e) {
            Log.e("PhoneInfo", "cannot get IMEI", e);
            return null;
        }
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public String getDeviceId() {
        return getImei();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public Sim getSimForSubId(int i) {
        String iccid = getIccid(i);
        String imsi = getImsi(i);
        String mccMnc = getMccMnc(i);
        String line1Number = getLine1Number(i);
        if (iccid == null || imsi == null) {
            return null;
        }
        return new Sim(iccid, imsi, mccMnc, line1Number);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public boolean isSimStateReadyForSubId(int i) {
        return getSimForSubId(i) != null;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneUtil
    public boolean checkPermission(@NonNull String str) {
        return this.mContext.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }
}
