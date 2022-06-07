package com.xiaomi.miplay.mylibrary.mirror;

import android.media.MediaFormat;

/* compiled from: AudioEncoder.java */
/* loaded from: classes4.dex */
class a extends b {
    private final AudioEncodeConfig a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(AudioEncodeConfig audioEncodeConfig) {
        super(audioEncodeConfig.getCodecName());
        this.a = audioEncodeConfig;
    }

    @Override // com.xiaomi.miplay.mylibrary.mirror.b
    protected MediaFormat a() {
        return this.a.a();
    }
}
