package com.xiaomi.phonenum.phone;

import android.annotation.SuppressLint;
import android.content.Context;

/* loaded from: classes4.dex */
public class KKPhoneInfo extends PhoneInfo {
    private final int a = -1;

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneCount() {
        return 1;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getSubIdForSlotId(int i) {
        return -1;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean waitForServiceForSubId(int i, long j) throws InterruptedException {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public KKPhoneInfo(Context context) {
        super(context);
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean getDataEnabledForSubId(int i) {
        return this.mTm.getDataState() == 2;
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public boolean isNetworkRoamingForSubId(int i) {
        return this.mTm.isNetworkRoaming();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public String getNetworkMccMncForSubId(int i) {
        return this.mTm.getNetworkOperator();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo, com.xiaomi.phonenum.phone.PhoneUtil
    public int getPhoneTypeForSubId(int i) {
        return this.mTm.getPhoneType();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    @SuppressLint({"HardwareIds"})
    protected String getIccid(int i) {
        return this.mTm.getSimSerialNumber();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    @SuppressLint({"HardwareIds"})
    protected String getImsi(int i) {
        return this.mTm.getSubscriberId();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    protected String getMccMnc(int i) {
        return this.mTm.getSimOperator();
    }

    @Override // com.xiaomi.phonenum.phone.PhoneInfo
    @SuppressLint({"HardwareIds"})
    protected String getLine1Number(int i) {
        return this.mTm.getLine1Number();
    }
}
