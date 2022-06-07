package com.google.android.exoplayer2.drm;

import android.os.Handler;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public interface DrmSessionEventListener {
    default void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
    }

    default void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
    }

    default void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
    }

    @Deprecated
    default void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
    }

    default void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i2) {
    }

    default void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
    }

    default void onDrmSessionReleased(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
    }

    /* loaded from: classes2.dex */
    public static class EventDispatcher {
        private final CopyOnWriteArrayList<a> a;
        @Nullable
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final int windowIndex;

        public EventDispatcher() {
            this(new CopyOnWriteArrayList(), 0, null);
        }

        private EventDispatcher(CopyOnWriteArrayList<a> copyOnWriteArrayList, int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            this.a = copyOnWriteArrayList;
            this.windowIndex = i;
            this.mediaPeriodId = mediaPeriodId;
        }

        @CheckResult
        public EventDispatcher withParameters(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            return new EventDispatcher(this.a, i, mediaPeriodId);
        }

        public void addEventListener(Handler handler, DrmSessionEventListener drmSessionEventListener) {
            Assertions.checkNotNull(handler);
            Assertions.checkNotNull(drmSessionEventListener);
            this.a.add(new a(handler, drmSessionEventListener));
        }

        public void removeEventListener(DrmSessionEventListener drmSessionEventListener) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next.b == drmSessionEventListener) {
                    this.a.remove(next);
                }
            }
        }

        public void drmSessionAcquired(final int i) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DrmSessionEventListener$EventDispatcher$0fXtU5oV7UcGaQxDc4Xx-SRHQ7E
                    @Override // java.lang.Runnable
                    public final void run() {
                        DrmSessionEventListener.EventDispatcher.this.a(drmSessionEventListener, i);
                    }
                });
            }
        }

        public /* synthetic */ void a(DrmSessionEventListener drmSessionEventListener, int i) {
            drmSessionEventListener.onDrmSessionAcquired(this.windowIndex, this.mediaPeriodId);
            drmSessionEventListener.onDrmSessionAcquired(this.windowIndex, this.mediaPeriodId, i);
        }

        public void drmKeysLoaded() {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DrmSessionEventListener$EventDispatcher$-tbROPFXuCOQT59fV8nGSryAuGE
                    @Override // java.lang.Runnable
                    public final void run() {
                        DrmSessionEventListener.EventDispatcher.this.d(drmSessionEventListener);
                    }
                });
            }
        }

        public /* synthetic */ void d(DrmSessionEventListener drmSessionEventListener) {
            drmSessionEventListener.onDrmKeysLoaded(this.windowIndex, this.mediaPeriodId);
        }

        public void drmSessionManagerError(final Exception exc) {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DrmSessionEventListener$EventDispatcher$5-j6MRjav6dbwhlNV0G3apGFQ_k
                    @Override // java.lang.Runnable
                    public final void run() {
                        DrmSessionEventListener.EventDispatcher.this.a(drmSessionEventListener, exc);
                    }
                });
            }
        }

        public /* synthetic */ void a(DrmSessionEventListener drmSessionEventListener, Exception exc) {
            drmSessionEventListener.onDrmSessionManagerError(this.windowIndex, this.mediaPeriodId, exc);
        }

        public void drmKeysRestored() {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DrmSessionEventListener$EventDispatcher$3zx5IYgMYSmrlu1C3mtDrN4onmM
                    @Override // java.lang.Runnable
                    public final void run() {
                        DrmSessionEventListener.EventDispatcher.this.c(drmSessionEventListener);
                    }
                });
            }
        }

        public /* synthetic */ void c(DrmSessionEventListener drmSessionEventListener) {
            drmSessionEventListener.onDrmKeysRestored(this.windowIndex, this.mediaPeriodId);
        }

        public void drmKeysRemoved() {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DrmSessionEventListener$EventDispatcher$9yUsrFSok7seygjfRDUyaS6VkAs
                    @Override // java.lang.Runnable
                    public final void run() {
                        DrmSessionEventListener.EventDispatcher.this.b(drmSessionEventListener);
                    }
                });
            }
        }

        public /* synthetic */ void b(DrmSessionEventListener drmSessionEventListener) {
            drmSessionEventListener.onDrmKeysRemoved(this.windowIndex, this.mediaPeriodId);
        }

        public void drmSessionReleased() {
            Iterator<a> it = this.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                final DrmSessionEventListener drmSessionEventListener = next.b;
                Util.postOrRun(next.a, new Runnable() { // from class: com.google.android.exoplayer2.drm.-$$Lambda$DrmSessionEventListener$EventDispatcher$gPtChHwUnrGIDiDvLAhcCPKhKIE
                    @Override // java.lang.Runnable
                    public final void run() {
                        DrmSessionEventListener.EventDispatcher.this.a(drmSessionEventListener);
                    }
                });
            }
        }

        public /* synthetic */ void a(DrmSessionEventListener drmSessionEventListener) {
            drmSessionEventListener.onDrmSessionReleased(this.windowIndex, this.mediaPeriodId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class a {
            public Handler a;
            public DrmSessionEventListener b;

            public a(Handler handler, DrmSessionEventListener drmSessionEventListener) {
                this.a = handler;
                this.b = drmSessionEventListener;
            }
        }
    }
}
