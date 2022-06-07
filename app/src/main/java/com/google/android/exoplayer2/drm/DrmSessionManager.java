package com.google.android.exoplayer2.drm;

import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;

/* loaded from: classes2.dex */
public interface DrmSessionManager {
    public static final DrmSessionManager DRM_UNSUPPORTED = new DrmSessionManager() { // from class: com.google.android.exoplayer2.drm.DrmSessionManager.1
        @Override // com.google.android.exoplayer2.drm.DrmSessionManager
        @Nullable
        public DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
            if (format.drmInitData == null) {
                return null;
            }
            return new ErrorStateDrmSession(new DrmSession.DrmSessionException(new UnsupportedDrmException(1), 6001));
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionManager
        @Nullable
        public Class<UnsupportedMediaCrypto> getExoMediaCryptoType(Format format) {
            if (format.drmInitData != null) {
                return UnsupportedMediaCrypto.class;
            }
            return null;
        }
    };
    @Deprecated
    public static final DrmSessionManager DUMMY = DRM_UNSUPPORTED;

    /* loaded from: classes2.dex */
    public interface DrmSessionReference {
        public static final DrmSessionReference EMPTY = $$Lambda$DrmSessionManager$DrmSessionReference$4zUSOJ06w1kBTfPARGykSaDUuyg.INSTANCE;

        static /* synthetic */ void a() {
        }

        void release();
    }

    @Nullable
    DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    @Nullable
    Class<? extends ExoMediaCrypto> getExoMediaCryptoType(Format format);

    default void prepare() {
    }

    default void release() {
    }

    @Deprecated
    static DrmSessionManager getDummyDrmSessionManager() {
        return DRM_UNSUPPORTED;
    }

    default DrmSessionReference preacquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        return DrmSessionReference.EMPTY;
    }
}
