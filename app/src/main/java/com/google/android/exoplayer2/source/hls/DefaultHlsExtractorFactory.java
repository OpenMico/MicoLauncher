package com.google.android.exoplayer2.source.hls;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.FileTypes;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.common.primitives.Ints;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class DefaultHlsExtractorFactory implements HlsExtractorFactory {
    private static final int[] a = {8, 13, 11, 2, 0, 1, 7};
    private final int b;
    private final boolean c;

    public DefaultHlsExtractorFactory() {
        this(0, true);
    }

    public DefaultHlsExtractorFactory(int i, boolean z) {
        this.b = i;
        this.c = z;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public BundledHlsMediaChunkExtractor createExtractor(Uri uri, Format format, @Nullable List<Format> list, TimestampAdjuster timestampAdjuster, Map<String, List<String>> map, ExtractorInput extractorInput) throws IOException {
        int inferFileTypeFromMimeType = FileTypes.inferFileTypeFromMimeType(format.sampleMimeType);
        int inferFileTypeFromResponseHeaders = FileTypes.inferFileTypeFromResponseHeaders(map);
        int inferFileTypeFromUri = FileTypes.inferFileTypeFromUri(uri);
        ArrayList arrayList = new ArrayList(a.length);
        a(inferFileTypeFromMimeType, arrayList);
        a(inferFileTypeFromResponseHeaders, arrayList);
        a(inferFileTypeFromUri, arrayList);
        for (int i : a) {
            a(i, arrayList);
        }
        Extractor extractor = null;
        extractorInput.resetPeekPosition();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            int intValue = ((Integer) arrayList.get(i2)).intValue();
            Extractor extractor2 = (Extractor) Assertions.checkNotNull(a(intValue, format, list, timestampAdjuster));
            if (a(extractor2, extractorInput)) {
                return new BundledHlsMediaChunkExtractor(extractor2, format, timestampAdjuster);
            }
            if (extractor == null && (intValue == inferFileTypeFromMimeType || intValue == inferFileTypeFromResponseHeaders || intValue == inferFileTypeFromUri || intValue == 11)) {
                extractor = extractor2;
            }
        }
        return new BundledHlsMediaChunkExtractor((Extractor) Assertions.checkNotNull(extractor), format, timestampAdjuster);
    }

    private static void a(int i, List<Integer> list) {
        if (Ints.indexOf(a, i) != -1 && !list.contains(Integer.valueOf(i))) {
            list.add(Integer.valueOf(i));
        }
    }

    @Nullable
    @SuppressLint({"SwitchIntDef"})
    private Extractor a(int i, Format format, @Nullable List<Format> list, TimestampAdjuster timestampAdjuster) {
        if (i == 11) {
            return a(this.b, this.c, format, list, timestampAdjuster);
        }
        if (i == 13) {
            return new WebvttExtractor(format.language, timestampAdjuster);
        }
        switch (i) {
            case 0:
                return new Ac3Extractor();
            case 1:
                return new Ac4Extractor();
            case 2:
                return new AdtsExtractor();
            default:
                switch (i) {
                    case 7:
                        return new Mp3Extractor(0, 0L);
                    case 8:
                        return a(timestampAdjuster, format, list);
                    default:
                        return null;
                }
        }
    }

    private static TsExtractor a(int i, boolean z, Format format, @Nullable List<Format> list, TimestampAdjuster timestampAdjuster) {
        int i2 = i | 16;
        if (list != null) {
            i2 |= 32;
        } else if (z) {
            list = Collections.singletonList(new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA608).build());
        } else {
            list = Collections.emptyList();
        }
        String str = format.codecs;
        if (!TextUtils.isEmpty(str)) {
            if (!MimeTypes.containsCodecsCorrespondingToMimeType(str, MimeTypes.AUDIO_AAC)) {
                i2 |= 2;
            }
            if (!MimeTypes.containsCodecsCorrespondingToMimeType(str, MimeTypes.VIDEO_H264)) {
                i2 |= 4;
            }
        }
        return new TsExtractor(2, timestampAdjuster, new DefaultTsPayloadReaderFactory(i2, list));
    }

    private static FragmentedMp4Extractor a(TimestampAdjuster timestampAdjuster, Format format, @Nullable List<Format> list) {
        int i = a(format) ? 4 : 0;
        if (list == null) {
            list = Collections.emptyList();
        }
        return new FragmentedMp4Extractor(i, timestampAdjuster, null, list);
    }

    private static boolean a(Format format) {
        Metadata metadata = format.metadata;
        if (metadata == null) {
            return false;
        }
        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof HlsTrackMetadataEntry) {
                return !((HlsTrackMetadataEntry) entry).variantInfos.isEmpty();
            }
        }
        return false;
    }

    /* JADX WARN: Finally extract failed */
    private static boolean a(Extractor extractor, ExtractorInput extractorInput) throws IOException {
        try {
            boolean sniff = extractor.sniff(extractorInput);
            extractorInput.resetPeekPosition();
            return sniff;
        } catch (EOFException unused) {
            extractorInput.resetPeekPosition();
            return false;
        } catch (Throwable th) {
            extractorInput.resetPeekPosition();
            throw th;
        }
    }
}
