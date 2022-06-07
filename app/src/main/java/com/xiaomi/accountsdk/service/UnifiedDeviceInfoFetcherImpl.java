package com.xiaomi.accountsdk.service;

import android.content.Context;
import com.xiaomi.accountsdk.hasheddeviceidlib.IUnifiedDeviceIdFetcher;

/* loaded from: classes2.dex */
public class UnifiedDeviceInfoFetcherImpl implements IUnifiedDeviceIdFetcher {
    @Override // com.xiaomi.accountsdk.hasheddeviceidlib.IUnifiedDeviceIdFetcher
    public String getHashedDeviceId(Context context) {
        DeviceInfoResult deviceInfoRpc = PassportCommonServiceClient.getDeviceInfoRpc(context, "passport", 1, 5000);
        if (deviceInfoRpc == null || deviceInfoRpc.deviceInfo == null) {
            return null;
        }
        return deviceInfoRpc.deviceInfo.getString(DeviceInfoResult.BUNDLE_KEY_HASHED_DEVICE_ID);
    }
}
