package com.google.android.exoplayer2.transformer;

import android.util.SparseLongArray;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.Util;

/* compiled from: TransformerMediaClock.java */
@RequiresApi(18)
/* loaded from: classes2.dex */
final class j implements MediaClock {
    private final SparseLongArray a = new SparseLongArray();
    private long b;

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
    }

    public void a(int i, long j) {
        long j2 = this.a.get(i, C.TIME_UNSET);
        int i2 = (j2 > C.TIME_UNSET ? 1 : (j2 == C.TIME_UNSET ? 0 : -1));
        if (i2 == 0 || j > j2) {
            this.a.put(i, j);
            if (i2 == 0 || j2 == this.b) {
                this.b = Util.minValue(this.a);
            }
        }
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        return this.b;
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        return PlaybackParameters.DEFAULT;
    }
}
