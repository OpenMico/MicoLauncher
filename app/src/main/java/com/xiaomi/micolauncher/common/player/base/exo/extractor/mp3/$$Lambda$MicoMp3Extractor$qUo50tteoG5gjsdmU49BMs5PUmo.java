package com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3;

import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.player.base.exo.extractor.mp3.-$$Lambda$MicoMp3Extractor$qUo50tteoG5gjsdmU49BMs5PUmo  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$MicoMp3Extractor$qUo50tteoG5gjsdmU49BMs5PUmo implements ExtractorsFactory {
    public static final /* synthetic */ $$Lambda$MicoMp3Extractor$qUo50tteoG5gjsdmU49BMs5PUmo INSTANCE = new $$Lambda$MicoMp3Extractor$qUo50tteoG5gjsdmU49BMs5PUmo();

    private /* synthetic */ $$Lambda$MicoMp3Extractor$qUo50tteoG5gjsdmU49BMs5PUmo() {
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final Extractor[] createExtractors() {
        Extractor[] b;
        b = MicoMp3Extractor.b();
        return b;
    }
}
