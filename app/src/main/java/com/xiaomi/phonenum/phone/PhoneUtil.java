package com.xiaomi.phonenum.phone;

import androidx.annotation.NonNull;
import com.xiaomi.phonenum.bean.Sim;

/* loaded from: classes4.dex */
public interface PhoneUtil {
    boolean checkPermission(@NonNull String str);

    boolean getDataEnabledForSubId(int i);

    String getDeviceId();

    @Deprecated
    String getImei();

    String getNetworkMccMncForSubId(int i);

    int getPhoneCount();

    int getPhoneTypeForSubId(int i);

    Sim getSimForSubId(int i);

    int getSubIdForSlotId(int i);

    boolean isNetWorkTypeMobile();

    boolean isNetworkRoamingForSubId(int i);

    boolean isSimStateReadyForSubId(int i);

    boolean waitForServiceForSubId(int i, long j) throws InterruptedException;
}
