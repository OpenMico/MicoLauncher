package com.google.android.exoplayer2.extractor.ts;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.core.view.InputDeviceCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class PsExtractor implements Extractor {
    public static final int AUDIO_STREAM = 192;
    public static final int AUDIO_STREAM_MASK = 224;
    public static final ExtractorsFactory FACTORY = $$Lambda$PsExtractor$Edqo88ec7fWrlSVr7jorjFbU9Y.INSTANCE;
    public static final int PRIVATE_STREAM_1 = 189;
    public static final int VIDEO_STREAM = 224;
    public static final int VIDEO_STREAM_MASK = 240;
    private final TimestampAdjuster a;
    private final SparseArray<a> b;
    private final ParsableByteArray c;
    private final c d;
    private boolean e;
    private boolean f;
    private boolean g;
    private long h;
    @Nullable
    private b i;
    private ExtractorOutput j;
    private boolean k;

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] a() {
        return new Extractor[]{new PsExtractor()};
    }

    public PsExtractor() {
        this(new TimestampAdjuster(0L));
    }

    public PsExtractor(TimestampAdjuster timestampAdjuster) {
        this.a = timestampAdjuster;
        this.c = new ParsableByteArray(4096);
        this.b = new SparseArray<>();
        this.d = new c();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        byte[] bArr = new byte[14];
        extractorInput.peekFully(bArr, 0, 14);
        if (442 != (((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255)) || (bArr[4] & 196) != 68 || (bArr[6] & 4) != 4 || (bArr[8] & 4) != 4 || (bArr[9] & 1) != 1 || (bArr[12] & 3) != 3) {
            return false;
        }
        extractorInput.advancePeekPosition(bArr[13] & 7);
        extractorInput.peekFully(bArr, 0, 3);
        return 1 == ((((bArr[0] & 255) << 16) | ((bArr[1] & 255) << 8)) | (bArr[2] & 255));
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.j = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        boolean z = this.a.getTimestampOffsetUs() == C.TIME_UNSET;
        if (!z) {
            long firstSampleTimestampUs = this.a.getFirstSampleTimestampUs();
            z = (firstSampleTimestampUs == C.TIME_UNSET || firstSampleTimestampUs == 0 || firstSampleTimestampUs == j2) ? false : true;
        }
        if (z) {
            this.a.reset(j2);
        }
        b bVar = this.i;
        if (bVar != null) {
            bVar.setSeekTargetUs(j2);
        }
        for (int i = 0; i < this.b.size(); i++) {
            this.b.valueAt(i).a();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        Assertions.checkStateNotNull(this.j);
        long length = extractorInput.getLength();
        int i = (length > (-1L) ? 1 : (length == (-1L) ? 0 : -1));
        if ((i != 0) && !this.d.a()) {
            return this.d.a(extractorInput, positionHolder);
        }
        a(length);
        b bVar = this.i;
        if (bVar != null && bVar.isSeeking()) {
            return this.i.handlePendingSeek(extractorInput, positionHolder);
        }
        extractorInput.resetPeekPosition();
        long peekPosition = i != 0 ? length - extractorInput.getPeekPosition() : -1L;
        if ((peekPosition != -1 && peekPosition < 4) || !extractorInput.peekFully(this.c.getData(), 0, 4, true)) {
            return -1;
        }
        this.c.setPosition(0);
        int readInt = this.c.readInt();
        if (readInt == 441) {
            return -1;
        }
        if (readInt == 442) {
            extractorInput.peekFully(this.c.getData(), 0, 10);
            this.c.setPosition(9);
            extractorInput.skipFully((this.c.readUnsignedByte() & 7) + 14);
            return 0;
        } else if (readInt == 443) {
            extractorInput.peekFully(this.c.getData(), 0, 2);
            this.c.setPosition(0);
            extractorInput.skipFully(this.c.readUnsignedShort() + 6);
            return 0;
        } else if (((readInt & InputDeviceCompat.SOURCE_ANY) >> 8) != 1) {
            extractorInput.skipFully(1);
            return 0;
        } else {
            int i2 = readInt & 255;
            a aVar = this.b.get(i2);
            if (!this.e) {
                if (aVar == null) {
                    ElementaryStreamReader elementaryStreamReader = null;
                    if (i2 == 189) {
                        elementaryStreamReader = new Ac3Reader();
                        this.f = true;
                        this.h = extractorInput.getPosition();
                    } else if ((i2 & 224) == 192) {
                        elementaryStreamReader = new MpegAudioReader();
                        this.f = true;
                        this.h = extractorInput.getPosition();
                    } else if ((i2 & VIDEO_STREAM_MASK) == 224) {
                        elementaryStreamReader = new H262Reader();
                        this.g = true;
                        this.h = extractorInput.getPosition();
                    }
                    if (elementaryStreamReader != null) {
                        elementaryStreamReader.createTracks(this.j, new TsPayloadReader.TrackIdGenerator(i2, 256));
                        aVar = new a(elementaryStreamReader, this.a);
                        this.b.put(i2, aVar);
                    }
                }
                if (extractorInput.getPosition() > ((!this.f || !this.g) ? 1048576L : this.h + PlaybackStateCompat.ACTION_PLAY_FROM_URI)) {
                    this.e = true;
                    this.j.endTracks();
                }
            }
            extractorInput.peekFully(this.c.getData(), 0, 2);
            this.c.setPosition(0);
            int readUnsignedShort = this.c.readUnsignedShort() + 6;
            if (aVar == null) {
                extractorInput.skipFully(readUnsignedShort);
            } else {
                this.c.reset(readUnsignedShort);
                extractorInput.readFully(this.c.getData(), 0, readUnsignedShort);
                this.c.setPosition(6);
                aVar.a(this.c);
                ParsableByteArray parsableByteArray = this.c;
                parsableByteArray.setLimit(parsableByteArray.capacity());
            }
            return 0;
        }
    }

    @RequiresNonNull({"output"})
    private void a(long j) {
        if (!this.k) {
            this.k = true;
            if (this.d.c() != C.TIME_UNSET) {
                this.i = new b(this.d.b(), this.d.c(), j);
                this.j.seekMap(this.i.getSeekMap());
                return;
            }
            this.j.seekMap(new SeekMap.Unseekable(this.d.c()));
        }
    }

    /* loaded from: classes2.dex */
    private static final class a {
        private final ElementaryStreamReader a;
        private final TimestampAdjuster b;
        private final ParsableBitArray c = new ParsableBitArray(new byte[64]);
        private boolean d;
        private boolean e;
        private boolean f;
        private int g;
        private long h;

        public a(ElementaryStreamReader elementaryStreamReader, TimestampAdjuster timestampAdjuster) {
            this.a = elementaryStreamReader;
            this.b = timestampAdjuster;
        }

        public void a() {
            this.f = false;
            this.a.seek();
        }

        public void a(ParsableByteArray parsableByteArray) throws ParserException {
            parsableByteArray.readBytes(this.c.data, 0, 3);
            this.c.setPosition(0);
            b();
            parsableByteArray.readBytes(this.c.data, 0, this.g);
            this.c.setPosition(0);
            c();
            this.a.packetStarted(this.h, 4);
            this.a.consume(parsableByteArray);
            this.a.packetFinished();
        }

        private void b() {
            this.c.skipBits(8);
            this.d = this.c.readBit();
            this.e = this.c.readBit();
            this.c.skipBits(6);
            this.g = this.c.readBits(8);
        }

        private void c() {
            this.h = 0L;
            if (this.d) {
                this.c.skipBits(4);
                this.c.skipBits(1);
                this.c.skipBits(1);
                long readBits = (this.c.readBits(3) << 30) | (this.c.readBits(15) << 15) | this.c.readBits(15);
                this.c.skipBits(1);
                if (!this.f && this.e) {
                    this.c.skipBits(4);
                    this.c.skipBits(1);
                    this.c.skipBits(1);
                    this.c.skipBits(1);
                    this.b.adjustTsTimestamp((this.c.readBits(3) << 30) | (this.c.readBits(15) << 15) | this.c.readBits(15));
                    this.f = true;
                }
                this.h = this.b.adjustTsTimestamp(readBits);
            }
        }
    }
}
