package com.xiaomi.accountsdk.hasheddeviceidlib;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class PlainDeviceIdUtil {

    /* loaded from: classes2.dex */
    public interface IPlainDeviceIdFetcher {
        String getPlainDeviceId(Context context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class FetcherHolder {
        private static volatile IPlainDeviceIdFetcher sInstance = new PlainDeviceIdUtilImplDefault();

        private FetcherHolder() {
        }
    }

    public static IPlainDeviceIdFetcher getFetcherInstance() {
        return FetcherHolder.sInstance;
    }

    public static void setFetcherInstance(IPlainDeviceIdFetcher iPlainDeviceIdFetcher) {
        IPlainDeviceIdFetcher unused = FetcherHolder.sInstance = iPlainDeviceIdFetcher;
    }

    /* loaded from: classes2.dex */
    public static final class PlainDeviceIdUtilImplDefault implements IPlainDeviceIdFetcher {
        @Override // com.xiaomi.accountsdk.hasheddeviceidlib.PlainDeviceIdUtil.IPlainDeviceIdFetcher
        public String getPlainDeviceId(Context context) {
            if (context == null) {
                return null;
            }
            String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            return TextUtils.isEmpty(deviceId) ? MacAddressUtil.getMacAddress(context) : deviceId;
        }
    }
}
