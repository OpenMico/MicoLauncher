package com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3;

import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;

/* compiled from: ConstantBitrateSeeker.java */
/* loaded from: classes3.dex */
final class a extends ConstantBitrateSeekMap implements Seeker {
    @Override // com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return -1L;
    }

    public a(long j, long j2, MpegAudioUtil.Header header) {
        super(j, j2, header.bitrate, header.frameSize);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.Seeker
    public long getTimeUs(long j) {
        return getTimeUsAtPosition(j);
    }
}
