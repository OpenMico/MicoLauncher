package com.google.android.exoplayer2.source.hls;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.text.webvtt.WebvttParserUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class WebvttExtractor implements Extractor {
    private static final Pattern a = Pattern.compile("LOCAL:([^,]+)");
    private static final Pattern b = Pattern.compile("MPEGTS:(-?\\d+)");
    @Nullable
    private final String c;
    private final TimestampAdjuster d;
    private ExtractorOutput f;
    private int h;
    private final ParsableByteArray e = new ParsableByteArray();
    private byte[] g = new byte[1024];

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    public WebvttExtractor(@Nullable String str, TimestampAdjuster timestampAdjuster) {
        this.c = str;
        this.d = timestampAdjuster;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        extractorInput.peekFully(this.g, 0, 6, false);
        this.e.reset(this.g, 6);
        if (WebvttParserUtil.isWebvttHeaderLine(this.e)) {
            return true;
        }
        extractorInput.peekFully(this.g, 6, 3, false);
        this.e.reset(this.g, 9);
        return WebvttParserUtil.isWebvttHeaderLine(this.e);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.f = extractorOutput;
        extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        throw new IllegalStateException();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        Assertions.checkNotNull(this.f);
        int length = (int) extractorInput.getLength();
        int i = this.h;
        byte[] bArr = this.g;
        if (i == bArr.length) {
            this.g = Arrays.copyOf(bArr, ((length != -1 ? length : bArr.length) * 3) / 2);
        }
        byte[] bArr2 = this.g;
        int i2 = this.h;
        int read = extractorInput.read(bArr2, i2, bArr2.length - i2);
        if (read != -1) {
            this.h += read;
            if (length == -1 || this.h != length) {
                return 0;
            }
        }
        a();
        return -1;
    }

    @RequiresNonNull({"output"})
    private void a() throws ParserException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(this.g);
        WebvttParserUtil.validateWebvttHeaderLine(parsableByteArray);
        long j = 0;
        long j2 = 0;
        for (String readLine = parsableByteArray.readLine(); !TextUtils.isEmpty(readLine); readLine = parsableByteArray.readLine()) {
            if (readLine.startsWith("X-TIMESTAMP-MAP")) {
                Matcher matcher = a.matcher(readLine);
                if (!matcher.find()) {
                    String valueOf = String.valueOf(readLine);
                    throw ParserException.createForMalformedContainer(valueOf.length() != 0 ? "X-TIMESTAMP-MAP doesn't contain local timestamp: ".concat(valueOf) : new String("X-TIMESTAMP-MAP doesn't contain local timestamp: "), null);
                }
                Matcher matcher2 = b.matcher(readLine);
                if (!matcher2.find()) {
                    String valueOf2 = String.valueOf(readLine);
                    throw ParserException.createForMalformedContainer(valueOf2.length() != 0 ? "X-TIMESTAMP-MAP doesn't contain media timestamp: ".concat(valueOf2) : new String("X-TIMESTAMP-MAP doesn't contain media timestamp: "), null);
                } else {
                    j2 = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(matcher.group(1)));
                    j = TimestampAdjuster.ptsToUs(Long.parseLong((String) Assertions.checkNotNull(matcher2.group(1))));
                }
            }
        }
        Matcher findNextCueHeader = WebvttParserUtil.findNextCueHeader(parsableByteArray);
        if (findNextCueHeader == null) {
            a(0L);
            return;
        }
        long parseTimestampUs = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(findNextCueHeader.group(1)));
        long adjustTsTimestamp = this.d.adjustTsTimestamp(TimestampAdjuster.usToWrappedPts((j + parseTimestampUs) - j2));
        TrackOutput a2 = a(adjustTsTimestamp - parseTimestampUs);
        this.e.reset(this.g, this.h);
        a2.sampleData(this.e, this.h);
        a2.sampleMetadata(adjustTsTimestamp, 1, this.h, 0, null);
    }

    @RequiresNonNull({"output"})
    private TrackOutput a(long j) {
        TrackOutput track = this.f.track(0, 3);
        track.format(new Format.Builder().setSampleMimeType(MimeTypes.TEXT_VTT).setLanguage(this.c).setSubsampleOffsetUs(j).build());
        this.f.endTracks();
        return track;
    }
}
