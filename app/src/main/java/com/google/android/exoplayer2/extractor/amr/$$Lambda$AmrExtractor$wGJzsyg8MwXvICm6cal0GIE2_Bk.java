package com.google.android.exoplayer2.extractor.amr;

import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.extractor.amr.-$$Lambda$AmrExtractor$wGJzsyg8MwXvICm6cal0GIE2_Bk  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$AmrExtractor$wGJzsyg8MwXvICm6cal0GIE2_Bk implements ExtractorsFactory {
    public static final /* synthetic */ $$Lambda$AmrExtractor$wGJzsyg8MwXvICm6cal0GIE2_Bk INSTANCE = new $$Lambda$AmrExtractor$wGJzsyg8MwXvICm6cal0GIE2_Bk();

    private /* synthetic */ $$Lambda$AmrExtractor$wGJzsyg8MwXvICm6cal0GIE2_Bk() {
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final Extractor[] createExtractors() {
        Extractor[] c;
        c = AmrExtractor.c();
        return c;
    }
}
