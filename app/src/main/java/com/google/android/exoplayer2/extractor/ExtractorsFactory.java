package com.google.android.exoplayer2.extractor;

import android.net.Uri;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface ExtractorsFactory {
    public static final ExtractorsFactory EMPTY = $$Lambda$ExtractorsFactory$U_gkNSI1hfPdAFnZjPymt0t4zMs.INSTANCE;

    Extractor[] createExtractors();

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ Extractor[] a() {
        return new Extractor[0];
    }

    default Extractor[] createExtractors(Uri uri, Map<String, List<String>> map) {
        return createExtractors();
    }
}
