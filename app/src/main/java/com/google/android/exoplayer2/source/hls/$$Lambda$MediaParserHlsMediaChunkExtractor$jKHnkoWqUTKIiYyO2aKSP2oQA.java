package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.util.List;
import java.util.Map;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.source.hls.-$$Lambda$MediaParserHlsMediaChunkExtractor$jKHnkoWqUTKI-iYyO2a-KSP2oQA  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$MediaParserHlsMediaChunkExtractor$jKHnkoWqUTKIiYyO2aKSP2oQA implements HlsExtractorFactory {
    public static final /* synthetic */ $$Lambda$MediaParserHlsMediaChunkExtractor$jKHnkoWqUTKIiYyO2aKSP2oQA INSTANCE = new $$Lambda$MediaParserHlsMediaChunkExtractor$jKHnkoWqUTKIiYyO2aKSP2oQA();

    private /* synthetic */ $$Lambda$MediaParserHlsMediaChunkExtractor$jKHnkoWqUTKIiYyO2aKSP2oQA() {
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public final HlsMediaChunkExtractor createExtractor(Uri uri, Format format, List list, TimestampAdjuster timestampAdjuster, Map map, ExtractorInput extractorInput) {
        HlsMediaChunkExtractor a;
        a = MediaParserHlsMediaChunkExtractor.a(uri, format, list, timestampAdjuster, map, extractorInput);
        return a;
    }
}
