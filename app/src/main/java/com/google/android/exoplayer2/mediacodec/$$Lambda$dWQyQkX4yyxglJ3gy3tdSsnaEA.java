package com.google.android.exoplayer2.mediacodec;

import java.util.List;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.mediacodec.-$$Lambda$dWQyQkX4yyxglJ3gy3t-dSsnaEA  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$dWQyQkX4yyxglJ3gy3tdSsnaEA implements MediaCodecSelector {
    public static final /* synthetic */ $$Lambda$dWQyQkX4yyxglJ3gy3tdSsnaEA INSTANCE = new $$Lambda$dWQyQkX4yyxglJ3gy3tdSsnaEA();

    private /* synthetic */ $$Lambda$dWQyQkX4yyxglJ3gy3tdSsnaEA() {
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecSelector
    public final List getDecoderInfos(String str, boolean z, boolean z2) {
        return MediaCodecUtil.getDecoderInfos(str, z, z2);
    }
}
