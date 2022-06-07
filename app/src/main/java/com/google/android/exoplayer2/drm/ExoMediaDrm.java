package com.google.android.exoplayer2.drm;

import android.media.DeniedByServerException;
import android.media.MediaCryptoException;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmInitData;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes2.dex */
public interface ExoMediaDrm {
    public static final int EVENT_KEY_EXPIRED = 3;
    public static final int EVENT_KEY_REQUIRED = 2;
    public static final int EVENT_PROVISION_REQUIRED = 1;
    public static final int KEY_TYPE_OFFLINE = 2;
    public static final int KEY_TYPE_RELEASE = 3;
    public static final int KEY_TYPE_STREAMING = 1;

    /* loaded from: classes2.dex */
    public interface OnEventListener {
        void onEvent(ExoMediaDrm exoMediaDrm, @Nullable byte[] bArr, int i, int i2, @Nullable byte[] bArr2);
    }

    /* loaded from: classes2.dex */
    public interface OnExpirationUpdateListener {
        void onExpirationUpdate(ExoMediaDrm exoMediaDrm, byte[] bArr, long j);
    }

    /* loaded from: classes2.dex */
    public interface OnKeyStatusChangeListener {
        void onKeyStatusChange(ExoMediaDrm exoMediaDrm, byte[] bArr, List<KeyStatus> list, boolean z);
    }

    /* loaded from: classes2.dex */
    public interface Provider {
        ExoMediaDrm acquireExoMediaDrm(UUID uuid);
    }

    void acquire();

    void closeSession(byte[] bArr);

    ExoMediaCrypto createMediaCrypto(byte[] bArr) throws MediaCryptoException;

    Class<? extends ExoMediaCrypto> getExoMediaCryptoType();

    KeyRequest getKeyRequest(byte[] bArr, @Nullable List<DrmInitData.SchemeData> list, int i, @Nullable HashMap<String, String> hashMap) throws NotProvisionedException;

    @Nullable
    PersistableBundle getMetrics();

    byte[] getPropertyByteArray(String str);

    String getPropertyString(String str);

    ProvisionRequest getProvisionRequest();

    byte[] openSession() throws MediaDrmException;

    @Nullable
    byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws NotProvisionedException, DeniedByServerException;

    void provideProvisionResponse(byte[] bArr) throws DeniedByServerException;

    Map<String, String> queryKeyStatus(byte[] bArr);

    void release();

    void restoreKeys(byte[] bArr, byte[] bArr2);

    void setOnEventListener(@Nullable OnEventListener onEventListener);

    void setOnExpirationUpdateListener(@Nullable OnExpirationUpdateListener onExpirationUpdateListener);

    void setOnKeyStatusChangeListener(@Nullable OnKeyStatusChangeListener onKeyStatusChangeListener);

    void setPropertyByteArray(String str, byte[] bArr);

    void setPropertyString(String str, String str2);

    /* loaded from: classes2.dex */
    public static final class AppManagedProvider implements Provider {
        private final ExoMediaDrm a;

        public AppManagedProvider(ExoMediaDrm exoMediaDrm) {
            this.a = exoMediaDrm;
        }

        @Override // com.google.android.exoplayer2.drm.ExoMediaDrm.Provider
        public ExoMediaDrm acquireExoMediaDrm(UUID uuid) {
            this.a.acquire();
            return this.a;
        }
    }

    /* loaded from: classes2.dex */
    public static final class KeyStatus {
        private final int a;
        private final byte[] b;

        public KeyStatus(int i, byte[] bArr) {
            this.a = i;
            this.b = bArr;
        }

        public int getStatusCode() {
            return this.a;
        }

        public byte[] getKeyId() {
            return this.b;
        }
    }

    /* loaded from: classes2.dex */
    public static final class KeyRequest {
        public static final int REQUEST_TYPE_INITIAL = 0;
        public static final int REQUEST_TYPE_NONE = 3;
        public static final int REQUEST_TYPE_RELEASE = 2;
        public static final int REQUEST_TYPE_RENEWAL = 1;
        public static final int REQUEST_TYPE_UNKNOWN = Integer.MIN_VALUE;
        public static final int REQUEST_TYPE_UPDATE = 4;
        private final byte[] a;
        private final String b;
        private final int c;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface RequestType {
        }

        public KeyRequest(byte[] bArr, String str) {
            this(bArr, str, Integer.MIN_VALUE);
        }

        public KeyRequest(byte[] bArr, String str, int i) {
            this.a = bArr;
            this.b = str;
            this.c = i;
        }

        public byte[] getData() {
            return this.a;
        }

        public String getLicenseServerUrl() {
            return this.b;
        }

        public int getRequestType() {
            return this.c;
        }
    }

    /* loaded from: classes2.dex */
    public static final class ProvisionRequest {
        private final byte[] a;
        private final String b;

        public ProvisionRequest(byte[] bArr, String str) {
            this.a = bArr;
            this.b = str;
        }

        public byte[] getData() {
            return this.a;
        }

        public String getDefaultUrl() {
            return this.b;
        }
    }
}
