package com.google.android.exoplayer2.audio;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.audio.-$$Lambda$AudioAttributes$jKKY4R_5rh4LBh2lb2xoG1XFAfQ  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$AudioAttributes$jKKY4R_5rh4LBh2lb2xoG1XFAfQ implements Bundleable.Creator {
    public static final /* synthetic */ $$Lambda$AudioAttributes$jKKY4R_5rh4LBh2lb2xoG1XFAfQ INSTANCE = new $$Lambda$AudioAttributes$jKKY4R_5rh4LBh2lb2xoG1XFAfQ();

    private /* synthetic */ $$Lambda$AudioAttributes$jKKY4R_5rh4LBh2lb2xoG1XFAfQ() {
    }

    @Override // com.google.android.exoplayer2.Bundleable.Creator
    public final Bundleable fromBundle(Bundle bundle) {
        AudioAttributes a;
        a = AudioAttributes.a(bundle);
        return a;
    }
}
