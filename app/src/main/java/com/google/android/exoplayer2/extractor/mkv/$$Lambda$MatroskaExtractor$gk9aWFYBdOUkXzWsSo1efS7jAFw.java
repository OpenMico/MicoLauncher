package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.extractor.mkv.-$$Lambda$MatroskaExtractor$gk9aWFYBdOUkXzWsSo1efS7jAFw  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$MatroskaExtractor$gk9aWFYBdOUkXzWsSo1efS7jAFw implements ExtractorsFactory {
    public static final /* synthetic */ $$Lambda$MatroskaExtractor$gk9aWFYBdOUkXzWsSo1efS7jAFw INSTANCE = new $$Lambda$MatroskaExtractor$gk9aWFYBdOUkXzWsSo1efS7jAFw();

    private /* synthetic */ $$Lambda$MatroskaExtractor$gk9aWFYBdOUkXzWsSo1efS7jAFw() {
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final Extractor[] createExtractors() {
        Extractor[] g;
        g = MatroskaExtractor.g();
        return g;
    }
}
