package com.google.android.exoplayer2.drm;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Map;
import java.util.UUID;

@RequiresApi(18)
/* loaded from: classes2.dex */
public final class OfflineLicenseHelper {
    private static final Format a = new Format.Builder().setDrmInitData(new DrmInitData(new DrmInitData.SchemeData[0])).build();
    private final ConditionVariable b;
    private final DefaultDrmSessionManager c;
    private final HandlerThread d;
    private final DrmSessionEventListener.EventDispatcher e;

    public static OfflineLicenseHelper newWidevineInstance(String str, HttpDataSource.Factory factory, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return newWidevineInstance(str, false, factory, eventDispatcher);
    }

    public static OfflineLicenseHelper newWidevineInstance(String str, boolean z, HttpDataSource.Factory factory, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return newWidevineInstance(str, z, factory, null, eventDispatcher);
    }

    public static OfflineLicenseHelper newWidevineInstance(String str, boolean z, HttpDataSource.Factory factory, @Nullable Map<String, String> map, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return new OfflineLicenseHelper(new DefaultDrmSessionManager.Builder().setKeyRequestParameters(map).build(new HttpMediaDrmCallback(str, z, factory)), eventDispatcher);
    }

    @Deprecated
    public OfflineLicenseHelper(UUID uuid, ExoMediaDrm.Provider provider, MediaDrmCallback mediaDrmCallback, @Nullable Map<String, String> map, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this(new DefaultDrmSessionManager.Builder().setUuidAndExoMediaDrmProvider(uuid, provider).setKeyRequestParameters(map).build(mediaDrmCallback), eventDispatcher);
    }

    public OfflineLicenseHelper(DefaultDrmSessionManager defaultDrmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this.c = defaultDrmSessionManager;
        this.e = eventDispatcher;
        this.d = new HandlerThread("ExoPlayer:OfflineLicenseHelper");
        this.d.start();
        this.b = new ConditionVariable();
        eventDispatcher.addEventListener(new Handler(this.d.getLooper()), new DrmSessionEventListener() { // from class: com.google.android.exoplayer2.drm.OfflineLicenseHelper.1
            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.b.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
                OfflineLicenseHelper.this.b.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.b.open();
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
            public void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                OfflineLicenseHelper.this.b.open();
            }
        });
    }

    public synchronized byte[] downloadLicense(Format format) throws DrmSession.DrmSessionException {
        Assertions.checkArgument(format.drmInitData != null);
        return a(2, null, format);
    }

    public synchronized byte[] renewLicense(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        return a(2, bArr, a);
    }

    public synchronized void releaseLicense(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        a(3, bArr, a);
    }

    public synchronized Pair<Long, Long> getLicenseDurationRemainingSec(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        this.c.prepare();
        DrmSession b = b(1, bArr, a);
        DrmSession.DrmSessionException error = b.getError();
        Pair<Long, Long> licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(b);
        b.release(this.e);
        this.c.release();
        if (error == null) {
            return (Pair) Assertions.checkNotNull(licenseDurationRemainingSec);
        } else if (error.getCause() instanceof KeysExpiredException) {
            return Pair.create(0L, 0L);
        } else {
            throw error;
        }
    }

    public void release() {
        this.d.quit();
    }

    private byte[] a(int i, @Nullable byte[] bArr, Format format) throws DrmSession.DrmSessionException {
        this.c.prepare();
        DrmSession b = b(i, bArr, format);
        DrmSession.DrmSessionException error = b.getError();
        byte[] offlineLicenseKeySetId = b.getOfflineLicenseKeySetId();
        b.release(this.e);
        this.c.release();
        if (error == null) {
            return (byte[]) Assertions.checkNotNull(offlineLicenseKeySetId);
        }
        throw error;
    }

    private DrmSession b(int i, @Nullable byte[] bArr, Format format) {
        Assertions.checkNotNull(format.drmInitData);
        this.c.setMode(i, bArr);
        this.b.close();
        DrmSession acquireSession = this.c.acquireSession(this.d.getLooper(), this.e, format);
        this.b.block();
        return (DrmSession) Assertions.checkNotNull(acquireSession);
    }
}
