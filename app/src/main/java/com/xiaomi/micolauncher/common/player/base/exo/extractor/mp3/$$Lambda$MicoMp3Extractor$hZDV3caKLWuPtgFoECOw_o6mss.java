package com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3;

import com.google.android.exoplayer2.metadata.id3.Id3Decoder;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.-$$Lambda$MicoMp3Extractor$hZ-DV3caKLWuPtgFoECOw_o6mss  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$MicoMp3Extractor$hZDV3caKLWuPtgFoECOw_o6mss implements Id3Decoder.FramePredicate {
    public static final /* synthetic */ $$Lambda$MicoMp3Extractor$hZDV3caKLWuPtgFoECOw_o6mss INSTANCE = new $$Lambda$MicoMp3Extractor$hZDV3caKLWuPtgFoECOw_o6mss();

    private /* synthetic */ $$Lambda$MicoMp3Extractor$hZDV3caKLWuPtgFoECOw_o6mss() {
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate
    public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
        boolean a;
        a = MicoMp3Extractor.a(i, i2, i3, i4, i5);
        return a;
    }
}
