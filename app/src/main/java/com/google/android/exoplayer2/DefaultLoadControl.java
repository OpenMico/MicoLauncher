package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public class DefaultLoadControl implements LoadControl {
    public static final int DEFAULT_AUDIO_BUFFER_SIZE = 13107200;
    public static final int DEFAULT_BACK_BUFFER_DURATION_MS = 0;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 5000;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_MS = 2500;
    public static final int DEFAULT_CAMERA_MOTION_BUFFER_SIZE = 131072;
    public static final int DEFAULT_MAX_BUFFER_MS = 50000;
    public static final int DEFAULT_METADATA_BUFFER_SIZE = 131072;
    public static final int DEFAULT_MIN_BUFFER_MS = 50000;
    public static final int DEFAULT_MIN_BUFFER_SIZE = 13107200;
    public static final int DEFAULT_MUXED_BUFFER_SIZE = 144310272;
    public static final boolean DEFAULT_PRIORITIZE_TIME_OVER_SIZE_THRESHOLDS = false;
    public static final boolean DEFAULT_RETAIN_BACK_BUFFER_FROM_KEYFRAME = false;
    public static final int DEFAULT_TARGET_BUFFER_BYTES = -1;
    public static final int DEFAULT_TEXT_BUFFER_SIZE = 131072;
    public static final int DEFAULT_VIDEO_BUFFER_SIZE = 131072000;
    private final DefaultAllocator a;
    private final long b;
    private final long c;
    private final long d;
    private final long e;
    private final int f;
    private final boolean g;
    private final long h;
    private final boolean i;
    private int j;
    private boolean k;

    /* loaded from: classes.dex */
    public static final class Builder {
        @Nullable
        private DefaultAllocator a;
        private int b = 50000;
        private int c = 50000;
        private int d = DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS;
        private int e = 5000;
        private int f = -1;
        private boolean g = false;
        private int h = 0;
        private boolean i = false;
        private boolean j;

        public Builder setAllocator(DefaultAllocator defaultAllocator) {
            Assertions.checkState(!this.j);
            this.a = defaultAllocator;
            return this;
        }

        public Builder setBufferDurationsMs(int i, int i2, int i3, int i4) {
            Assertions.checkState(!this.j);
            DefaultLoadControl.b(i3, 0, "bufferForPlaybackMs", "0");
            DefaultLoadControl.b(i4, 0, "bufferForPlaybackAfterRebufferMs", "0");
            DefaultLoadControl.b(i, i3, "minBufferMs", "bufferForPlaybackMs");
            DefaultLoadControl.b(i, i4, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
            DefaultLoadControl.b(i2, i, "maxBufferMs", "minBufferMs");
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            return this;
        }

        public Builder setTargetBufferBytes(int i) {
            Assertions.checkState(!this.j);
            this.f = i;
            return this;
        }

        public Builder setPrioritizeTimeOverSizeThresholds(boolean z) {
            Assertions.checkState(!this.j);
            this.g = z;
            return this;
        }

        public Builder setBackBuffer(int i, boolean z) {
            Assertions.checkState(!this.j);
            DefaultLoadControl.b(i, 0, "backBufferDurationMs", "0");
            this.h = i;
            this.i = z;
            return this;
        }

        @Deprecated
        public DefaultLoadControl createDefaultLoadControl() {
            return build();
        }

        public DefaultLoadControl build() {
            Assertions.checkState(!this.j);
            this.j = true;
            if (this.a == null) {
                this.a = new DefaultAllocator(true, 65536);
            }
            return new DefaultLoadControl(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i);
        }
    }

    public DefaultLoadControl() {
        this(new DefaultAllocator(true, 65536), 50000, 50000, DEFAULT_BUFFER_FOR_PLAYBACK_MS, 5000, -1, false, 0, false);
    }

    protected DefaultLoadControl(DefaultAllocator defaultAllocator, int i, int i2, int i3, int i4, int i5, boolean z, int i6, boolean z2) {
        b(i3, 0, "bufferForPlaybackMs", "0");
        b(i4, 0, "bufferForPlaybackAfterRebufferMs", "0");
        b(i, i3, "minBufferMs", "bufferForPlaybackMs");
        b(i, i4, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
        b(i2, i, "maxBufferMs", "minBufferMs");
        b(i6, 0, "backBufferDurationMs", "0");
        this.a = defaultAllocator;
        this.b = C.msToUs(i);
        this.c = C.msToUs(i2);
        this.d = C.msToUs(i3);
        this.e = C.msToUs(i4);
        this.f = i5;
        int i7 = this.f;
        this.j = i7 == -1 ? 13107200 : i7;
        this.g = z;
        this.h = C.msToUs(i6);
        this.i = z2;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onPrepared() {
        a(false);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onTracksSelected(Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        int i = this.f;
        if (i == -1) {
            i = calculateTargetBufferBytes(rendererArr, exoTrackSelectionArr);
        }
        this.j = i;
        this.a.setTargetBufferSize(this.j);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onStopped() {
        a(true);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onReleased() {
        a(true);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public Allocator getAllocator() {
        return this.a;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public long getBackBufferDurationUs() {
        return this.h;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean retainBackBufferFromKeyframe() {
        return this.i;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean shouldContinueLoading(long j, long j2, float f) {
        boolean z = true;
        boolean z2 = this.a.getTotalBytesAllocated() >= this.j;
        long j3 = this.b;
        if (f > 1.0f) {
            j3 = Math.min(Util.getMediaDurationForPlayoutDuration(j3, f), this.c);
        }
        if (j2 < Math.max(j3, 500000L)) {
            if (!this.g && z2) {
                z = false;
            }
            this.k = z;
            if (!this.k && j2 < 500000) {
                Log.w("DefaultLoadControl", "Target buffer size reached with less than 500ms of buffered media data.");
            }
        } else if (j2 >= this.c || z2) {
            this.k = false;
        }
        return this.k;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean shouldStartPlayback(long j, float f, boolean z, long j2) {
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(j, f);
        long j3 = z ? this.e : this.d;
        if (j2 != C.TIME_UNSET) {
            j3 = Math.min(j2 / 2, j3);
        }
        return j3 <= 0 || playoutDurationForMediaDuration >= j3 || (!this.g && this.a.getTotalBytesAllocated() >= this.j);
    }

    protected int calculateTargetBufferBytes(Renderer[] rendererArr, ExoTrackSelection[] exoTrackSelectionArr) {
        int i = 0;
        for (int i2 = 0; i2 < rendererArr.length; i2++) {
            if (exoTrackSelectionArr[i2] != null) {
                i += a(rendererArr[i2].getTrackType());
            }
        }
        return Math.max(13107200, i);
    }

    private void a(boolean z) {
        int i = this.f;
        if (i == -1) {
            i = 13107200;
        }
        this.j = i;
        this.k = false;
        if (z) {
            this.a.reset();
        }
    }

    private static int a(int i) {
        switch (i) {
            case 0:
                return DEFAULT_MUXED_BUFFER_SIZE;
            case 1:
                return 13107200;
            case 2:
                return DEFAULT_VIDEO_BUFFER_SIZE;
            case 3:
                return 131072;
            case 4:
            default:
                throw new IllegalArgumentException();
            case 5:
                return 131072;
            case 6:
                return 131072;
            case 7:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(int i, int i2, String str, String str2) {
        boolean z = i >= i2;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(" cannot be less than ");
        sb.append(str2);
        Assertions.checkArgument(z, sb.toString());
    }
}
