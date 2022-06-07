package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.uc.crashsdk.export.LogType;
import java.util.Arrays;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class AdtsReader implements ElementaryStreamReader {
    private static final byte[] a = {73, 68, 51};
    private final boolean b;
    private final ParsableBitArray c;
    private final ParsableByteArray d;
    @Nullable
    private final String e;
    private String f;
    private TrackOutput g;
    private TrackOutput h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private boolean m;
    private int n;
    private int o;
    private int p;
    private boolean q;
    private long r;
    private int s;
    private long t;
    private TrackOutput u;
    private long v;

    public static boolean isAdtsSyncWord(int i) {
        return (i & 65526) == 65520;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public AdtsReader(boolean z) {
        this(z, null);
    }

    public AdtsReader(boolean z, @Nullable String str) {
        this.c = new ParsableBitArray(new byte[7]);
        this.d = new ParsableByteArray(Arrays.copyOf(a, 10));
        b();
        this.n = -1;
        this.o = -1;
        this.r = C.TIME_UNSET;
        this.t = C.TIME_UNSET;
        this.b = z;
        this.e = str;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.t = C.TIME_UNSET;
        a();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.f = trackIdGenerator.getFormatId();
        this.g = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
        this.u = this.g;
        if (this.b) {
            trackIdGenerator.generateNewId();
            this.h = extractorOutput.track(trackIdGenerator.getTrackId(), 5);
            this.h.format(new Format.Builder().setId(trackIdGenerator.getFormatId()).setSampleMimeType(MimeTypes.APPLICATION_ID3).build());
            return;
        }
        this.h = new DummyTrackOutput();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j, int i) {
        if (j != C.TIME_UNSET) {
            this.t = j;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) throws ParserException {
        h();
        while (parsableByteArray.bytesLeft() > 0) {
            switch (this.i) {
                case 0:
                    a(parsableByteArray);
                    break;
                case 1:
                    b(parsableByteArray);
                    break;
                case 2:
                    if (!a(parsableByteArray, this.d.getData(), 10)) {
                        break;
                    } else {
                        f();
                        break;
                    }
                case 3:
                    if (!a(parsableByteArray, this.c.data, this.l ? 7 : 5)) {
                        break;
                    } else {
                        g();
                        break;
                    }
                case 4:
                    c(parsableByteArray);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    public long getSampleDurationUs() {
        return this.r;
    }

    private void a() {
        this.m = false;
        b();
    }

    private boolean a(ParsableByteArray parsableByteArray, byte[] bArr, int i) {
        int min = Math.min(parsableByteArray.bytesLeft(), i - this.j);
        parsableByteArray.readBytes(bArr, this.j, min);
        this.j += min;
        return this.j == i;
    }

    private void b() {
        this.i = 0;
        this.j = 0;
        this.k = 256;
    }

    private void c() {
        this.i = 2;
        this.j = a.length;
        this.s = 0;
        this.d.setPosition(0);
    }

    private void a(TrackOutput trackOutput, long j, int i, int i2) {
        this.i = 4;
        this.j = i;
        this.u = trackOutput;
        this.v = j;
        this.s = i2;
    }

    private void d() {
        this.i = 3;
        this.j = 0;
    }

    private void e() {
        this.i = 1;
        this.j = 0;
    }

    private void a(ParsableByteArray parsableByteArray) {
        byte[] data = parsableByteArray.getData();
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit) {
            int i = position + 1;
            int i2 = data[position] & 255;
            if (this.k != 512 || !a((byte) -1, (byte) i2) || (!this.m && !a(parsableByteArray, i - 2))) {
                int i3 = this.k;
                int i4 = i2 | i3;
                if (i4 == 329) {
                    this.k = LogType.UNEXP_OTHER;
                } else if (i4 == 511) {
                    this.k = 512;
                } else if (i4 == 836) {
                    this.k = 1024;
                } else if (i4 == 1075) {
                    c();
                    parsableByteArray.setPosition(i);
                    return;
                } else if (i3 != 256) {
                    this.k = 256;
                    position = i - 1;
                }
                position = i;
            } else {
                this.p = (i2 & 8) >> 3;
                boolean z = true;
                if ((i2 & 1) != 0) {
                    z = false;
                }
                this.l = z;
                if (!this.m) {
                    e();
                } else {
                    d();
                }
                parsableByteArray.setPosition(i);
                return;
            }
        }
        parsableByteArray.setPosition(position);
    }

    private void b(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() != 0) {
            this.c.data[0] = parsableByteArray.getData()[parsableByteArray.getPosition()];
            this.c.setPosition(2);
            int readBits = this.c.readBits(4);
            int i = this.o;
            if (i == -1 || readBits == i) {
                if (!this.m) {
                    this.m = true;
                    this.n = this.p;
                    this.o = readBits;
                }
                d();
                return;
            }
            a();
        }
    }

    private boolean a(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 1);
        if (!b(parsableByteArray, this.c.data, 1)) {
            return false;
        }
        this.c.setPosition(4);
        int readBits = this.c.readBits(1);
        int i2 = this.n;
        if (i2 != -1 && readBits != i2) {
            return false;
        }
        if (this.o != -1) {
            if (!b(parsableByteArray, this.c.data, 1)) {
                return true;
            }
            this.c.setPosition(2);
            if (this.c.readBits(4) != this.o) {
                return false;
            }
            parsableByteArray.setPosition(i + 2);
        }
        if (!b(parsableByteArray, this.c.data, 4)) {
            return true;
        }
        this.c.setPosition(14);
        int readBits2 = this.c.readBits(13);
        if (readBits2 < 7) {
            return false;
        }
        byte[] data = parsableByteArray.getData();
        int limit = parsableByteArray.limit();
        int i3 = i + readBits2;
        if (i3 >= limit) {
            return true;
        }
        if (data[i3] == -1) {
            int i4 = i3 + 1;
            if (i4 == limit) {
                return true;
            }
            return a((byte) -1, data[i4]) && ((data[i4] & 8) >> 3) == readBits;
        } else if (data[i3] != 73) {
            return false;
        } else {
            int i5 = i3 + 1;
            if (i5 == limit) {
                return true;
            }
            if (data[i5] != 68) {
                return false;
            }
            int i6 = i3 + 2;
            return i6 == limit || data[i6] == 51;
        }
    }

    private boolean a(byte b, byte b2) {
        return isAdtsSyncWord(((b & 255) << 8) | (b2 & 255));
    }

    private boolean b(ParsableByteArray parsableByteArray, byte[] bArr, int i) {
        if (parsableByteArray.bytesLeft() < i) {
            return false;
        }
        parsableByteArray.readBytes(bArr, 0, i);
        return true;
    }

    @RequiresNonNull({"id3Output"})
    private void f() {
        this.h.sampleData(this.d, 10);
        this.d.setPosition(6);
        a(this.h, 0L, 10, this.d.readSynchSafeInt() + 10);
    }

    @RequiresNonNull({"output"})
    private void g() throws ParserException {
        this.c.setPosition(0);
        if (!this.q) {
            int readBits = this.c.readBits(2) + 1;
            if (readBits != 2) {
                StringBuilder sb = new StringBuilder(61);
                sb.append("Detected audio object type: ");
                sb.append(readBits);
                sb.append(", but assuming AAC LC.");
                Log.w("AdtsReader", sb.toString());
                readBits = 2;
            }
            this.c.skipBits(5);
            byte[] buildAudioSpecificConfig = AacUtil.buildAudioSpecificConfig(readBits, this.o, this.c.readBits(3));
            AacUtil.Config parseAudioSpecificConfig = AacUtil.parseAudioSpecificConfig(buildAudioSpecificConfig);
            Format build = new Format.Builder().setId(this.f).setSampleMimeType(MimeTypes.AUDIO_AAC).setCodecs(parseAudioSpecificConfig.codecs).setChannelCount(parseAudioSpecificConfig.channelCount).setSampleRate(parseAudioSpecificConfig.sampleRateHz).setInitializationData(Collections.singletonList(buildAudioSpecificConfig)).setLanguage(this.e).build();
            this.r = 1024000000 / build.sampleRate;
            this.g.format(build);
            this.q = true;
        } else {
            this.c.skipBits(10);
        }
        this.c.skipBits(4);
        int readBits2 = (this.c.readBits(13) - 2) - 5;
        a(this.g, this.r, 0, this.l ? readBits2 - 2 : readBits2);
    }

    @RequiresNonNull({"currentOutput"})
    private void c(ParsableByteArray parsableByteArray) {
        int min = Math.min(parsableByteArray.bytesLeft(), this.s - this.j);
        this.u.sampleData(parsableByteArray, min);
        this.j += min;
        int i = this.j;
        int i2 = this.s;
        if (i == i2) {
            long j = this.t;
            if (j != C.TIME_UNSET) {
                this.u.sampleMetadata(j, 1, i2, 0, null);
                this.t += this.v;
            }
            b();
        }
    }

    @EnsuresNonNull({"output", "currentOutput", "id3Output"})
    private void h() {
        Assertions.checkNotNull(this.g);
        Util.castNonNull(this.u);
        Util.castNonNull(this.h);
    }
}
