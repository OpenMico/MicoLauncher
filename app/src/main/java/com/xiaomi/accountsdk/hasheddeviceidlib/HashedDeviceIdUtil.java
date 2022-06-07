package com.xiaomi.accountsdk.hasheddeviceidlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.hasheddeviceidlib.PlainDeviceIdUtil;
import com.xiaomi.accountsdk.utils.AccountLog;
import java.util.UUID;

/* loaded from: classes2.dex */
public class HashedDeviceIdUtil {
    private static final String PSEUDO_DEVICE_ID_PREFIX = "android_pseudo_";
    private static final String SP_KEY_HASHED_DEVICE_ID = "hashedDeviceId";
    private static final String SP_NAME_DEVICE_ID = "deviceId";
    private static final String TAG = "HashedDeviceIdUtil";
    private final Context context;
    private final PlainDeviceIdUtil.IPlainDeviceIdFetcher plainDeviceIdFetcher;

    /* loaded from: classes2.dex */
    public enum DeviceIdPolicy {
        RUNTIME_DEVICE_ID_ONLY,
        CACHED_THEN_RUNTIME_THEN_PSEUDO
    }

    public HashedDeviceIdUtil(Context context) {
        this(context, PlainDeviceIdUtil.getFetcherInstance());
    }

    public HashedDeviceIdUtil(Context context, PlainDeviceIdUtil.IPlainDeviceIdFetcher iPlainDeviceIdFetcher) {
        if (iPlainDeviceIdFetcher != null) {
            this.context = context == null ? null : context.getApplicationContext();
            this.plainDeviceIdFetcher = iPlainDeviceIdFetcher;
            return;
        }
        throw new IllegalArgumentException("plainDeviceIdFetcher == null");
    }

    /* loaded from: classes2.dex */
    public static class GlobalConfig {
        public static DeviceIdPolicy DEFAULT_DEVICE_ID_POLICY = DeviceIdPolicy.RUNTIME_DEVICE_ID_ONLY;
        private static final GlobalConfig sInstance = new GlobalConfig();
        private DeviceIdPolicy policy = DEFAULT_DEVICE_ID_POLICY;
        private IUnifiedDeviceIdFetcher unifiedDeviceIdFetcher;

        private GlobalConfig() {
        }

        public static GlobalConfig getInstance() {
            return sInstance;
        }

        public void setPolicy(DeviceIdPolicy deviceIdPolicy) {
            this.policy = deviceIdPolicy;
        }

        public void setUnifiedDeviceIdFetcher(IUnifiedDeviceIdFetcher iUnifiedDeviceIdFetcher) {
            this.unifiedDeviceIdFetcher = iUnifiedDeviceIdFetcher;
        }

        public IUnifiedDeviceIdFetcher getUnifiedDeviceIdFetcher() {
            return this.unifiedDeviceIdFetcher;
        }
    }

    public boolean hasHistoricalHashedDeviceId() {
        return legal(loadHistoricalHashedDeviceId());
    }

    DeviceIdPolicy policy() {
        return GlobalConfig.getInstance().policy;
    }

    public String getHashedDeviceIdThrow() throws IllegalDeviceException {
        String hashedDeviceIdNoThrow = getHashedDeviceIdNoThrow();
        if (hashedDeviceIdNoThrow != null) {
            return hashedDeviceIdNoThrow;
        }
        throw new IllegalDeviceException("null device id");
    }

    @Deprecated
    public synchronized String getHashedDeviceIdNoThrow() {
        return getHashedDeviceId(true);
    }

    public synchronized String getHashedDeviceId(boolean z) {
        IUnifiedDeviceIdFetcher unifiedDeviceIdFetcher;
        DeviceIdPolicy policy = policy();
        if (policy == DeviceIdPolicy.RUNTIME_DEVICE_ID_ONLY) {
            return getRuntimeDeviceIdHashed();
        }
        if (policy == DeviceIdPolicy.CACHED_THEN_RUNTIME_THEN_PSEUDO) {
            String loadHistoricalHashedDeviceId = loadHistoricalHashedDeviceId();
            if (legal(loadHistoricalHashedDeviceId)) {
                return loadHistoricalHashedDeviceId;
            }
            String runtimeDeviceIdHashed = getRuntimeDeviceIdHashed();
            if (runtimeDeviceIdHashed != null) {
                saveHistoricalHashedDeviceId(runtimeDeviceIdHashed);
                return runtimeDeviceIdHashed;
            }
            if (z && !isMainThread() && (unifiedDeviceIdFetcher = GlobalConfig.getInstance().getUnifiedDeviceIdFetcher()) != null) {
                String hashedDeviceId = unifiedDeviceIdFetcher.getHashedDeviceId(this.context);
                if (!TextUtils.isEmpty(hashedDeviceId)) {
                    saveHistoricalHashedDeviceId(hashedDeviceId);
                    return hashedDeviceId;
                }
            }
            String createPseudoDeviceId = createPseudoDeviceId();
            saveHistoricalHashedDeviceId(createPseudoDeviceId);
            return createPseudoDeviceId;
        }
        throw new IllegalStateException("unknown policy " + policy);
    }

    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    String getRuntimeDeviceIdHashed() {
        try {
            String userEnvironmentPlainDeviceId = getUserEnvironmentPlainDeviceId();
            if (legal(userEnvironmentPlainDeviceId)) {
                return DeviceIdHasher.hashDeviceInfo(userEnvironmentPlainDeviceId);
            }
            return null;
        } catch (SecurityException e) {
            AccountLog.w(TAG, "can't get deviceid.", e);
            return null;
        }
    }

    String getUserEnvironmentPlainDeviceId() {
        return this.plainDeviceIdFetcher.getPlainDeviceId(this.context);
    }

    boolean legal(String str) {
        return !TextUtils.isEmpty(str);
    }

    String createPseudoDeviceId() {
        return String.format("%s%s", PSEUDO_DEVICE_ID_PREFIX, UUID.randomUUID().toString());
    }

    public String loadHistoricalHashedDeviceId() {
        SharedPreferences sp = getSP();
        if (sp == null) {
            return null;
        }
        return sp.getString(SP_KEY_HASHED_DEVICE_ID, null);
    }

    public void saveHistoricalHashedDeviceId(String str) {
        SharedPreferences sp = getSP();
        if (sp != null) {
            sp.edit().putString(SP_KEY_HASHED_DEVICE_ID, str).commit();
        }
    }

    SharedPreferences getSP() {
        Context context = this.context;
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences("deviceId", 0);
    }
}
