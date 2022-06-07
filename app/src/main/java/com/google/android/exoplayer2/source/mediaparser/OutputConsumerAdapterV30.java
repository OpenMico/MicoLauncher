package com.google.android.exoplayer2.source.mediaparser;

import android.annotation.SuppressLint;
import android.media.DrmInitData;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaParser;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.collect.ImmutableList;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiresApi(30)
@SuppressLint({"Override"})
/* loaded from: classes2.dex */
public final class OutputConsumerAdapterV30 implements MediaParser.OutputConsumer {
    private static final Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> a = Pair.create(MediaParser.SeekPoint.START, MediaParser.SeekPoint.START);
    private static final Pattern b = Pattern.compile("pattern \\(encrypt: (\\d+), skip: (\\d+)\\)");
    private final ArrayList<TrackOutput> c;
    private final ArrayList<Format> d;
    private final ArrayList<MediaCodec.CryptoInfo> e;
    private final ArrayList<TrackOutput.CryptoData> f;
    private final a g;
    private final boolean h;
    private final int i;
    @Nullable
    private final Format j;
    private ExtractorOutput k;
    @Nullable
    private MediaParser.SeekMap l;
    @Nullable
    private MediaParser.SeekMap m;
    @Nullable
    private String n;
    @Nullable
    private ChunkIndex o;
    @Nullable
    private TimestampAdjuster p;
    private List<Format> q;
    private int r;
    private long s;
    private boolean t;
    private boolean u;
    private boolean v;

    public OutputConsumerAdapterV30() {
        this(null, 7, false);
    }

    public OutputConsumerAdapterV30(@Nullable Format format, int i, boolean z) {
        this.h = z;
        this.j = format;
        this.i = i;
        this.c = new ArrayList<>();
        this.d = new ArrayList<>();
        this.e = new ArrayList<>();
        this.f = new ArrayList<>();
        this.g = new a();
        this.k = new DummyExtractorOutput();
        this.s = C.TIME_UNSET;
        this.q = ImmutableList.of();
    }

    public void setSampleTimestampUpperLimitFilterUs(long j) {
        this.s = j;
    }

    public void setTimestampAdjuster(TimestampAdjuster timestampAdjuster) {
        this.p = timestampAdjuster;
    }

    public void setExtractorOutput(ExtractorOutput extractorOutput) {
        this.k = extractorOutput;
    }

    public void setMuxedCaptionFormats(List<Format> list) {
        this.q = list;
    }

    public void disableSeeking() {
        this.v = true;
    }

    @Nullable
    public MediaParser.SeekMap getDummySeekMap() {
        return this.l;
    }

    @Nullable
    public ChunkIndex getChunkIndex() {
        return this.o;
    }

    public Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> getSeekPoints(long j) {
        MediaParser.SeekMap seekMap = this.m;
        return seekMap != null ? seekMap.getSeekPoints(j) : a;
    }

    public void setSelectedParserName(String str) {
        this.n = b(str);
    }

    @Nullable
    public Format[] getSampleFormats() {
        if (!this.t) {
            return null;
        }
        Format[] formatArr = new Format[this.d.size()];
        for (int i = 0; i < this.d.size(); i++) {
            formatArr[i] = (Format) Assertions.checkNotNull(this.d.get(i));
        }
        return formatArr;
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onTrackCountFound(int i) {
        this.t = true;
        a();
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSeekMapFound(MediaParser.SeekMap seekMap) {
        SeekMap seekMap2;
        if (!this.h || this.l != null) {
            this.m = seekMap;
            long durationMicros = seekMap.getDurationMicros();
            ExtractorOutput extractorOutput = this.k;
            if (this.v) {
                if (durationMicros == -2147483648L) {
                    durationMicros = C.TIME_UNSET;
                }
                seekMap2 = new SeekMap.Unseekable(durationMicros);
            } else {
                seekMap2 = new b(seekMap);
            }
            extractorOutput.seekMap(seekMap2);
            return;
        }
        this.l = seekMap;
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onTrackDataFound(int i, MediaParser.TrackData trackData) {
        if (!a(trackData.mediaFormat)) {
            a(i);
            TrackOutput trackOutput = this.c.get(i);
            if (trackOutput == null) {
                String string = trackData.mediaFormat.getString("track-type-string");
                int a2 = a(string != null ? string : trackData.mediaFormat.getString("mime"));
                if (a2 == this.i) {
                    this.r = i;
                }
                TrackOutput track = this.k.track(i, a2);
                this.c.set(i, track);
                if (string == null) {
                    trackOutput = track;
                } else {
                    return;
                }
            }
            Format a3 = a(trackData);
            Format format = this.j;
            trackOutput.format((format == null || i != this.r) ? a3 : a3.withManifestFormatInfo(format));
            this.d.set(i, a3);
            a();
        }
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSampleDataFound(int i, MediaParser.InputReader inputReader) throws IOException {
        a(i);
        this.g.a = inputReader;
        TrackOutput trackOutput = this.c.get(i);
        if (trackOutput == null) {
            trackOutput = this.k.track(i, -1);
            this.c.set(i, trackOutput);
        }
        trackOutput.sampleData((DataReader) this.g, (int) inputReader.getLength(), true);
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSampleCompleted(int i, long j, int i2, int i3, int i4, @Nullable MediaCodec.CryptoInfo cryptoInfo) {
        long j2 = this.s;
        if (j2 == C.TIME_UNSET || j < j2) {
            TimestampAdjuster timestampAdjuster = this.p;
            ((TrackOutput) Assertions.checkNotNull(this.c.get(i))).sampleMetadata(timestampAdjuster != null ? timestampAdjuster.adjustSampleTimestamp(j) : j, i2, i3, i4, a(i, cryptoInfo));
        }
    }

    private boolean a(MediaFormat mediaFormat) {
        ByteBuffer byteBuffer = mediaFormat.getByteBuffer("chunk-index-int-sizes");
        if (byteBuffer == null) {
            return false;
        }
        IntBuffer asIntBuffer = byteBuffer.asIntBuffer();
        LongBuffer asLongBuffer = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer("chunk-index-long-offsets"))).asLongBuffer();
        LongBuffer asLongBuffer2 = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer("chunk-index-long-us-durations"))).asLongBuffer();
        LongBuffer asLongBuffer3 = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer("chunk-index-long-us-times"))).asLongBuffer();
        int[] iArr = new int[asIntBuffer.remaining()];
        long[] jArr = new long[asLongBuffer.remaining()];
        long[] jArr2 = new long[asLongBuffer2.remaining()];
        long[] jArr3 = new long[asLongBuffer3.remaining()];
        asIntBuffer.get(iArr);
        asLongBuffer.get(jArr);
        asLongBuffer2.get(jArr2);
        asLongBuffer3.get(jArr3);
        this.o = new ChunkIndex(iArr, jArr, jArr2, jArr3);
        this.k.seekMap(this.o);
        return true;
    }

    private void a(int i) {
        for (int size = this.c.size(); size <= i; size++) {
            this.c.add(null);
            this.d.add(null);
            this.e.add(null);
            this.f.add(null);
        }
    }

    @Nullable
    private TrackOutput.CryptoData a(int i, @Nullable MediaCodec.CryptoInfo cryptoInfo) {
        int i2;
        if (cryptoInfo == null) {
            return null;
        }
        if (this.e.get(i) == cryptoInfo) {
            return (TrackOutput.CryptoData) Assertions.checkNotNull(this.f.get(i));
        }
        int i3 = 0;
        try {
            Matcher matcher = b.matcher(cryptoInfo.toString());
            matcher.find();
            i3 = Integer.parseInt((String) Util.castNonNull(matcher.group(1)));
            i2 = Integer.parseInt((String) Util.castNonNull(matcher.group(2)));
        } catch (RuntimeException e) {
            String valueOf = String.valueOf(cryptoInfo);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 43);
            sb.append("Unexpected error while parsing CryptoInfo: ");
            sb.append(valueOf);
            Log.e("OutputConsumerAdapterV30", sb.toString(), e);
            i2 = 0;
        }
        TrackOutput.CryptoData cryptoData = new TrackOutput.CryptoData(cryptoInfo.mode, cryptoInfo.key, i3, i2);
        this.e.set(i, cryptoInfo);
        this.f.set(i, cryptoData);
        return cryptoData;
    }

    private void a() {
        if (this.t && !this.u) {
            int size = this.c.size();
            for (int i = 0; i < size; i++) {
                if (this.c.get(i) == null) {
                    return;
                }
            }
            this.k.endTracks();
            this.u = true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int a(@Nullable String str) {
        char c;
        if (str == null) {
            return -1;
        }
        switch (str.hashCode()) {
            case -450004177:
                if (str.equals(PlayerEvent.METADATA)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -284840886:
                if (str.equals("unknown")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3556653:
                if (str.equals("text")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 93166550:
                if (str.equals("audio")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 112202875:
                if (str.equals("video")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 5;
            case 4:
                return -1;
            default:
                return MimeTypes.getTrackType(str);
        }
    }

    private Format a(MediaParser.TrackData trackData) {
        MediaFormat mediaFormat = trackData.mediaFormat;
        String string = mediaFormat.getString("mime");
        int integer = mediaFormat.getInteger("caption-service-number", -1);
        int i = 0;
        Format.Builder accessibilityChannel = new Format.Builder().setDrmInitData(a(mediaFormat.getString("crypto-mode-fourcc"), trackData.drmInitData)).setContainerMimeType(this.n).setPeakBitrate(mediaFormat.getInteger("bitrate", -1)).setChannelCount(mediaFormat.getInteger("channel-count", -1)).setColorInfo(d(mediaFormat)).setSampleMimeType(string).setCodecs(mediaFormat.getString("codecs-string")).setFrameRate(mediaFormat.getFloat("frame-rate", -1.0f)).setWidth(mediaFormat.getInteger("width", -1)).setHeight(mediaFormat.getInteger("height", -1)).setInitializationData(c(mediaFormat)).setLanguage(mediaFormat.getString(ai.N)).setMaxInputSize(mediaFormat.getInteger("max-input-size", -1)).setPcmEncoding(mediaFormat.getInteger("exo-pcm-encoding", -1)).setRotationDegrees(mediaFormat.getInteger("rotation-degrees", 0)).setSampleRate(mediaFormat.getInteger("sample-rate", -1)).setSelectionFlags(b(mediaFormat)).setEncoderDelay(mediaFormat.getInteger("encoder-delay", 0)).setEncoderPadding(mediaFormat.getInteger("encoder-padding", 0)).setPixelWidthHeightRatio(mediaFormat.getFloat("pixel-width-height-ratio-float", 1.0f)).setSubsampleOffsetUs(mediaFormat.getLong("subsample-offset-us-long", Long.MAX_VALUE)).setAccessibilityChannel(integer);
        while (true) {
            if (i >= this.q.size()) {
                break;
            }
            Format format = this.q.get(i);
            if (Util.areEqual(format.sampleMimeType, string) && format.accessibilityChannel == integer) {
                accessibilityChannel.setLanguage(format.language).setRoleFlags(format.roleFlags).setSelectionFlags(format.selectionFlags).setLabel(format.label).setMetadata(format.metadata);
                break;
            }
            i++;
        }
        return accessibilityChannel.build();
    }

    @Nullable
    private static DrmInitData a(@Nullable String str, @Nullable android.media.DrmInitData drmInitData) {
        if (drmInitData == null) {
            return null;
        }
        DrmInitData.SchemeData[] schemeDataArr = new DrmInitData.SchemeData[drmInitData.getSchemeInitDataCount()];
        for (int i = 0; i < schemeDataArr.length; i++) {
            DrmInitData.SchemeInitData schemeInitDataAt = drmInitData.getSchemeInitDataAt(i);
            schemeDataArr[i] = new DrmInitData.SchemeData(schemeInitDataAt.uuid, schemeInitDataAt.mimeType, schemeInitDataAt.data);
        }
        return new com.google.android.exoplayer2.drm.DrmInitData(str, schemeDataArr);
    }

    private static int b(MediaFormat mediaFormat) {
        return a(mediaFormat, "is-forced-subtitle", 2) | a(mediaFormat, "is-autoselect", 4) | 0 | a(mediaFormat, "is-default", 1);
    }

    private static int a(MediaFormat mediaFormat, String str, int i) {
        if (mediaFormat.getInteger(str, 0) != 0) {
            return i;
        }
        return 0;
    }

    private static List<byte[]> c(MediaFormat mediaFormat) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            i++;
            StringBuilder sb = new StringBuilder(15);
            sb.append("csd-");
            sb.append(i);
            ByteBuffer byteBuffer = mediaFormat.getByteBuffer(sb.toString());
            if (byteBuffer == null) {
                return arrayList;
            }
            arrayList.add(a(byteBuffer));
        }
    }

    @Nullable
    private static ColorInfo d(MediaFormat mediaFormat) {
        ByteBuffer byteBuffer = mediaFormat.getByteBuffer("hdr-static-info");
        byte[] a2 = byteBuffer != null ? a(byteBuffer) : null;
        int integer = mediaFormat.getInteger("color-transfer", -1);
        int integer2 = mediaFormat.getInteger("color-range", -1);
        int integer3 = mediaFormat.getInteger("color-standard", -1);
        if (a2 == null && integer == -1 && integer2 == -1 && integer3 == -1) {
            return null;
        }
        return new ColorInfo(integer3, integer2, integer, a2);
    }

    private static byte[] a(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.get(bArr);
        return bArr;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static String b(String str) {
        char c;
        switch (str.hashCode()) {
            case -2063506020:
                if (str.equals("android.media.mediaparser.Mp4Parser")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1870824006:
                if (str.equals("android.media.mediaparser.OggParser")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1566427438:
                if (str.equals("android.media.mediaparser.TsParser")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -900207883:
                if (str.equals("android.media.mediaparser.AdtsParser")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -589864617:
                if (str.equals("android.media.mediaparser.WavParser")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 52265814:
                if (str.equals("android.media.mediaparser.PsParser")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 116768877:
                if (str.equals("android.media.mediaparser.FragmentedMp4Parser")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 376876796:
                if (str.equals("android.media.mediaparser.Ac3Parser")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 703268017:
                if (str.equals("android.media.mediaparser.AmrParser")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 768643067:
                if (str.equals("android.media.mediaparser.FlacParser")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 965962719:
                if (str.equals("android.media.mediaparser.MatroskaParser")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1264380477:
                if (str.equals("android.media.mediaparser.Ac4Parser")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1343957595:
                if (str.equals("android.media.mediaparser.Mp3Parser")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2063134683:
                if (str.equals("android.media.mediaparser.FlvParser")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return MimeTypes.VIDEO_WEBM;
            case 1:
            case 2:
                return "video/mp4";
            case 3:
                return "audio/mpeg";
            case 4:
                return MimeTypes.AUDIO_AAC;
            case 5:
                return MimeTypes.AUDIO_AC3;
            case 6:
                return MimeTypes.VIDEO_MP2T;
            case 7:
                return MimeTypes.VIDEO_FLV;
            case '\b':
                return MimeTypes.AUDIO_OGG;
            case '\t':
                return MimeTypes.VIDEO_PS;
            case '\n':
                return MimeTypes.AUDIO_RAW;
            case 11:
                return MimeTypes.AUDIO_AMR;
            case '\f':
                return MimeTypes.AUDIO_AC4;
            case '\r':
                return MimeTypes.AUDIO_FLAC;
            default:
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "Illegal parser name: ".concat(valueOf) : new String("Illegal parser name: "));
        }
    }

    /* loaded from: classes2.dex */
    private static final class b implements SeekMap {
        private final MediaParser.SeekMap a;

        public b(MediaParser.SeekMap seekMap) {
            this.a = seekMap;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return this.a.isSeekable();
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            long durationMicros = this.a.getDurationMicros();
            return durationMicros != -2147483648L ? durationMicros : C.TIME_UNSET;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long j) {
            Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> seekPoints = this.a.getSeekPoints(j);
            if (seekPoints.first == seekPoints.second) {
                return new SeekMap.SeekPoints(a((MediaParser.SeekPoint) seekPoints.first));
            }
            return new SeekMap.SeekPoints(a((MediaParser.SeekPoint) seekPoints.first), a((MediaParser.SeekPoint) seekPoints.second));
        }

        private static SeekPoint a(MediaParser.SeekPoint seekPoint) {
            return new SeekPoint(seekPoint.timeMicros, seekPoint.position);
        }
    }

    /* loaded from: classes2.dex */
    private static final class a implements DataReader {
        @Nullable
        public MediaParser.InputReader a;

        private a() {
        }

        @Override // com.google.android.exoplayer2.upstream.DataReader
        public int read(byte[] bArr, int i, int i2) throws IOException {
            return ((MediaParser.InputReader) Util.castNonNull(this.a)).read(bArr, i, i2);
        }
    }
}
