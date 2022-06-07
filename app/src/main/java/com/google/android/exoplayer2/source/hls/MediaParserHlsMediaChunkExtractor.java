package com.google.android.exoplayer2.source.hls;

import android.annotation.SuppressLint;
import android.media.MediaFormat;
import android.media.MediaParser;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.source.mediaparser.InputReaderAdapterV30;
import com.google.android.exoplayer2.source.mediaparser.MediaParserUtil;
import com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.FileTypes;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiresApi(30)
/* loaded from: classes2.dex */
public final class MediaParserHlsMediaChunkExtractor implements HlsMediaChunkExtractor {
    public static final HlsExtractorFactory FACTORY = $$Lambda$MediaParserHlsMediaChunkExtractor$jKHnkoWqUTKIiYyO2aKSP2oQA.INSTANCE;
    private final OutputConsumerAdapterV30 a;
    private final InputReaderAdapterV30 b = new InputReaderAdapterV30();
    private final MediaParser c;
    private final Format d;
    private final boolean e;
    private final ImmutableList<MediaFormat> f;
    private int g;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ HlsMediaChunkExtractor a(Uri uri, Format format, List list, TimestampAdjuster timestampAdjuster, Map map, ExtractorInput extractorInput) throws IOException {
        List list2 = list;
        if (FileTypes.inferFileTypeFromMimeType(format.sampleMimeType) == 13) {
            return new BundledHlsMediaChunkExtractor(new WebvttExtractor(format.language, timestampAdjuster), format, timestampAdjuster);
        }
        boolean z = list2 != null;
        ImmutableList.Builder builder = ImmutableList.builder();
        if (list2 != null) {
            for (int i = 0; i < list.size(); i++) {
                builder.add((ImmutableList.Builder) MediaParserUtil.toCaptionsMediaFormat((Format) list.get(i)));
            }
        } else {
            builder.add((ImmutableList.Builder) MediaParserUtil.toCaptionsMediaFormat(new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA608).build()));
        }
        ImmutableList build = builder.build();
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = new OutputConsumerAdapterV30();
        if (list2 == null) {
            list2 = ImmutableList.of();
        }
        outputConsumerAdapterV30.setMuxedCaptionFormats(list2);
        outputConsumerAdapterV30.setTimestampAdjuster(timestampAdjuster);
        MediaParser a2 = a(outputConsumerAdapterV30, format, z, build, "android.media.mediaparser.FragmentedMp4Parser", "android.media.mediaparser.Ac3Parser", "android.media.mediaparser.Ac4Parser", "android.media.mediaparser.AdtsParser", "android.media.mediaparser.Mp3Parser", "android.media.mediaparser.TsParser");
        a aVar = new a(extractorInput);
        a2.advance(aVar);
        outputConsumerAdapterV30.setSelectedParserName(a2.getParserName());
        return new MediaParserHlsMediaChunkExtractor(a2, outputConsumerAdapterV30, format, z, build, aVar.b);
    }

    public MediaParserHlsMediaChunkExtractor(MediaParser mediaParser, OutputConsumerAdapterV30 outputConsumerAdapterV30, Format format, boolean z, ImmutableList<MediaFormat> immutableList, int i) {
        this.c = mediaParser;
        this.a = outputConsumerAdapterV30;
        this.e = z;
        this.f = immutableList;
        this.d = format;
        this.g = i;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public void init(ExtractorOutput extractorOutput) {
        this.a.setExtractorOutput(extractorOutput);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public boolean read(ExtractorInput extractorInput) throws IOException {
        extractorInput.skipFully(this.g);
        this.g = 0;
        this.b.setDataReader(extractorInput, extractorInput.getLength());
        return this.c.advance(this.b);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public boolean isPackedAudioExtractor() {
        String parserName = this.c.getParserName();
        return "android.media.mediaparser.Ac3Parser".equals(parserName) || "android.media.mediaparser.Ac4Parser".equals(parserName) || "android.media.mediaparser.AdtsParser".equals(parserName) || "android.media.mediaparser.Mp3Parser".equals(parserName);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public boolean isReusable() {
        String parserName = this.c.getParserName();
        return "android.media.mediaparser.FragmentedMp4Parser".equals(parserName) || "android.media.mediaparser.TsParser".equals(parserName);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public HlsMediaChunkExtractor recreate() {
        Assertions.checkState(!isReusable());
        return new MediaParserHlsMediaChunkExtractor(a(this.a, this.d, this.e, this.f, this.c.getParserName()), this.a, this.d, this.e, this.f, 0);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor
    public void onTruncatedSegmentParsed() {
        this.c.seek(MediaParser.SeekPoint.START);
    }

    @SuppressLint({"WrongConstant"})
    private static MediaParser a(MediaParser.OutputConsumer outputConsumer, Format format, boolean z, ImmutableList<MediaFormat> immutableList, String... strArr) {
        MediaParser mediaParser;
        if (strArr.length == 1) {
            mediaParser = MediaParser.createByName(strArr[0], outputConsumer);
        } else {
            mediaParser = MediaParser.create(outputConsumer, strArr);
        }
        mediaParser.setParameter(MediaParserUtil.PARAMETER_EXPOSE_CAPTION_FORMATS, immutableList);
        mediaParser.setParameter(MediaParserUtil.PARAMETER_OVERRIDE_IN_BAND_CAPTION_DECLARATIONS, Boolean.valueOf(z));
        mediaParser.setParameter(MediaParserUtil.PARAMETER_IN_BAND_CRYPTO_INFO, true);
        mediaParser.setParameter(MediaParserUtil.PARAMETER_EAGERLY_EXPOSE_TRACK_TYPE, true);
        mediaParser.setParameter(MediaParserUtil.PARAMETER_IGNORE_TIMESTAMP_OFFSET, true);
        mediaParser.setParameter("android.media.mediaparser.ts.ignoreSpliceInfoStream", true);
        mediaParser.setParameter("android.media.mediaparser.ts.mode", "hls");
        String str = format.codecs;
        if (!TextUtils.isEmpty(str)) {
            if (!MimeTypes.AUDIO_AAC.equals(MimeTypes.getAudioMediaMimeType(str))) {
                mediaParser.setParameter("android.media.mediaparser.ts.ignoreAacStream", true);
            }
            if (!MimeTypes.VIDEO_H264.equals(MimeTypes.getVideoMediaMimeType(str))) {
                mediaParser.setParameter("android.media.mediaparser.ts.ignoreAvcStream", true);
            }
        }
        return mediaParser;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a implements MediaParser.SeekableInputReader {
        private final ExtractorInput a;
        private int b;

        private a(ExtractorInput extractorInput) {
            this.a = extractorInput;
        }
    }
}
