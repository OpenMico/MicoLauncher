package com.google.android.exoplayer2.extractor.jpeg;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class JpegExtractor implements Extractor {
    private ExtractorOutput b;
    private int c;
    private int d;
    private int e;
    @Nullable
    private MotionPhotoMetadata g;
    private ExtractorInput h;
    private a i;
    @Nullable
    private Mp4Extractor j;
    private final ParsableByteArray a = new ParsableByteArray(6);
    private long f = -1;

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        if (a(extractorInput) != 65496) {
            return false;
        }
        this.d = a(extractorInput);
        if (this.d == 65504) {
            b(extractorInput);
            this.d = a(extractorInput);
        }
        if (this.d != 65505) {
            return false;
        }
        extractorInput.advancePeekPosition(2);
        this.a.reset(6);
        extractorInput.peekFully(this.a.getData(), 0, 6);
        return this.a.readUnsignedInt() == 1165519206 && this.a.readUnsignedShort() == 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.b = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        switch (this.c) {
            case 0:
                c(extractorInput);
                return 0;
            case 1:
                d(extractorInput);
                return 0;
            case 2:
                e(extractorInput);
                return 0;
            case 3:
            default:
                throw new IllegalStateException();
            case 4:
                long position = extractorInput.getPosition();
                long j = this.f;
                if (position != j) {
                    positionHolder.position = j;
                    return 1;
                }
                f(extractorInput);
                return 0;
            case 5:
                if (this.i == null || extractorInput != this.h) {
                    this.h = extractorInput;
                    this.i = new a(extractorInput, this.f);
                }
                int read = ((Mp4Extractor) Assertions.checkNotNull(this.j)).read(this.i, positionHolder);
                if (read == 1) {
                    positionHolder.position += this.f;
                }
                return read;
            case 6:
                return -1;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        if (j == 0) {
            this.c = 0;
            this.j = null;
        } else if (this.c == 5) {
            ((Mp4Extractor) Assertions.checkNotNull(this.j)).seek(j, j2);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
        Mp4Extractor mp4Extractor = this.j;
        if (mp4Extractor != null) {
            mp4Extractor.release();
        }
    }

    private int a(ExtractorInput extractorInput) throws IOException {
        this.a.reset(2);
        extractorInput.peekFully(this.a.getData(), 0, 2);
        return this.a.readUnsignedShort();
    }

    private void b(ExtractorInput extractorInput) throws IOException {
        this.a.reset(2);
        extractorInput.peekFully(this.a.getData(), 0, 2);
        extractorInput.advancePeekPosition(this.a.readUnsignedShort() - 2);
    }

    private void c(ExtractorInput extractorInput) throws IOException {
        this.a.reset(2);
        extractorInput.readFully(this.a.getData(), 0, 2);
        this.d = this.a.readUnsignedShort();
        int i = this.d;
        if (i == 65498) {
            if (this.f != -1) {
                this.c = 4;
            } else {
                b();
            }
        } else if ((i < 65488 || i > 65497) && this.d != 65281) {
            this.c = 1;
        }
    }

    private void d(ExtractorInput extractorInput) throws IOException {
        this.a.reset(2);
        extractorInput.readFully(this.a.getData(), 0, 2);
        this.e = this.a.readUnsignedShort() - 2;
        this.c = 2;
    }

    private void e(ExtractorInput extractorInput) throws IOException {
        String readNullTerminatedString;
        if (this.d == 65505) {
            ParsableByteArray parsableByteArray = new ParsableByteArray(this.e);
            extractorInput.readFully(parsableByteArray.getData(), 0, this.e);
            if (this.g == null && "http://ns.adobe.com/xap/1.0/".equals(parsableByteArray.readNullTerminatedString()) && (readNullTerminatedString = parsableByteArray.readNullTerminatedString()) != null) {
                this.g = a(readNullTerminatedString, extractorInput.getLength());
                MotionPhotoMetadata motionPhotoMetadata = this.g;
                if (motionPhotoMetadata != null) {
                    this.f = motionPhotoMetadata.videoStartPosition;
                }
            }
        } else {
            extractorInput.skipFully(this.e);
        }
        this.c = 0;
    }

    private void f(ExtractorInput extractorInput) throws IOException {
        if (!extractorInput.peekFully(this.a.getData(), 0, 1, true)) {
            b();
            return;
        }
        extractorInput.resetPeekPosition();
        if (this.j == null) {
            this.j = new Mp4Extractor();
        }
        this.i = new a(extractorInput, this.f);
        if (this.j.sniff(this.i)) {
            this.j.init(new StartOffsetExtractorOutput(this.f, (ExtractorOutput) Assertions.checkNotNull(this.b)));
            a();
            return;
        }
        b();
    }

    private void a() {
        a((Metadata.Entry) Assertions.checkNotNull(this.g));
        this.c = 5;
    }

    private void b() {
        a(new Metadata.Entry[0]);
        ((ExtractorOutput) Assertions.checkNotNull(this.b)).endTracks();
        this.b.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
        this.c = 6;
    }

    private void a(Metadata.Entry... entryArr) {
        ((ExtractorOutput) Assertions.checkNotNull(this.b)).track(1024, 4).format(new Format.Builder().setContainerMimeType("image/jpeg").setMetadata(new Metadata(entryArr)).build());
    }

    @Nullable
    private static MotionPhotoMetadata a(String str, long j) throws IOException {
        MotionPhotoDescription a;
        if (j == -1 || (a = b.a(str)) == null) {
            return null;
        }
        return a.a(j);
    }
}
