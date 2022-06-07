package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker;
import com.google.android.exoplayer2.extractor.mp4.a;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.AvcConfig;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.android.exoplayer2.video.DolbyVisionConfig;
import com.google.android.exoplayer2.video.HevcConfig;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: AtomParsers.java */
/* loaded from: classes2.dex */
public final class b {
    private static final byte[] a = Util.getUtf8Bytes("OpusHead");

    /* compiled from: AtomParsers.java */
    /* renamed from: com.google.android.exoplayer2.extractor.mp4.b$b */
    /* loaded from: classes2.dex */
    public interface AbstractC0061b {
        int a();

        int b();

        int c();
    }

    private static int a(int i) {
        if (i == 1936684398) {
            return 1;
        }
        if (i == 1986618469) {
            return 2;
        }
        if (i == 1952807028 || i == 1935832172 || i == 1937072756 || i == 1668047728) {
            return 3;
        }
        return i == 1835365473 ? 5 : -1;
    }

    public static List<h> a(a.C0060a aVar, GaplessInfoHolder gaplessInfoHolder, long j, @Nullable DrmInitData drmInitData, boolean z, boolean z2, Function<Track, Track> function) throws ParserException {
        Track apply;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < aVar.d.size(); i++) {
            a.C0060a aVar2 = aVar.d.get(i);
            if (aVar2.a == 1953653099 && (apply = function.apply(a(aVar2, (a.b) Assertions.checkNotNull(aVar.d(1836476516)), j, drmInitData, z, z2))) != null) {
                arrayList.add(a(apply, (a.C0060a) Assertions.checkNotNull(((a.C0060a) Assertions.checkNotNull(((a.C0060a) Assertions.checkNotNull(aVar2.e(1835297121))).e(1835626086))).e(1937007212)), gaplessInfoHolder));
            }
        }
        return arrayList;
    }

    public static Pair<Metadata, Metadata> a(a.b bVar) {
        ParsableByteArray parsableByteArray = bVar.b;
        parsableByteArray.setPosition(8);
        Metadata metadata = null;
        Metadata metadata2 = null;
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1835365473) {
                parsableByteArray.setPosition(position);
                metadata = a(parsableByteArray, position + readInt);
            } else if (readInt2 == 1936553057) {
                parsableByteArray.setPosition(position);
                metadata2 = c(parsableByteArray, position + readInt);
            }
            parsableByteArray.setPosition(position + readInt);
        }
        return Pair.create(metadata, metadata2);
    }

    @Nullable
    public static Metadata a(a.C0060a aVar) {
        a.b d2 = aVar.d(1751411826);
        a.b d3 = aVar.d(1801812339);
        a.b d4 = aVar.d(1768715124);
        if (d2 == null || d3 == null || d4 == null || d(d2.b) != 1835299937) {
            return null;
        }
        ParsableByteArray parsableByteArray = d3.b;
        parsableByteArray.setPosition(12);
        int readInt = parsableByteArray.readInt();
        String[] strArr = new String[readInt];
        for (int i = 0; i < readInt; i++) {
            int readInt2 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            strArr[i] = parsableByteArray.readString(readInt2 - 8);
        }
        ParsableByteArray parsableByteArray2 = d4.b;
        parsableByteArray2.setPosition(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray2.bytesLeft() > 8) {
            int position = parsableByteArray2.getPosition();
            int readInt3 = parsableByteArray2.readInt();
            int readInt4 = parsableByteArray2.readInt() - 1;
            if (readInt4 < 0 || readInt4 >= strArr.length) {
                StringBuilder sb = new StringBuilder(52);
                sb.append("Skipped metadata with unknown key index: ");
                sb.append(readInt4);
                Log.w("AtomParsers", sb.toString());
            } else {
                MdtaMetadataEntry a2 = d.a(parsableByteArray2, position + readInt3, strArr[readInt4]);
                if (a2 != null) {
                    arrayList.add(a2);
                }
            }
            parsableByteArray2.setPosition(position + readInt3);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    public static void a(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() != 1751411826) {
            position += 4;
        }
        parsableByteArray.setPosition(position);
    }

    @Nullable
    private static Track a(a.C0060a aVar, a.b bVar, long j, @Nullable DrmInitData drmInitData, boolean z, boolean z2) throws ParserException {
        long j2;
        a.b bVar2;
        long[] jArr;
        long[] jArr2;
        a.C0060a e2;
        Pair<long[], long[]> b;
        a.C0060a aVar2 = (a.C0060a) Assertions.checkNotNull(aVar.e(1835297121));
        int a2 = a(d(((a.b) Assertions.checkNotNull(aVar2.d(1751411826))).b));
        if (a2 == -1) {
            return null;
        }
        f c2 = c(((a.b) Assertions.checkNotNull(aVar.d(1953196132))).b);
        if (j == C.TIME_UNSET) {
            j2 = c2.b;
            bVar2 = bVar;
        } else {
            bVar2 = bVar;
            j2 = j;
        }
        long b2 = b(bVar2.b);
        long scaleLargeTimestamp = j2 == C.TIME_UNSET ? -9223372036854775807L : Util.scaleLargeTimestamp(j2, 1000000L, b2);
        Pair<Long, String> e3 = e(((a.b) Assertions.checkNotNull(aVar2.d(1835296868))).b);
        c a3 = a(((a.b) Assertions.checkNotNull(((a.C0060a) Assertions.checkNotNull(((a.C0060a) Assertions.checkNotNull(aVar2.e(1835626086))).e(1937007212))).d(1937011556))).b, c2.a, c2.c, (String) e3.second, drmInitData, z2);
        if (z || (e2 = aVar.e(1701082227)) == null || (b = b(e2)) == null) {
            jArr2 = null;
            jArr = null;
        } else {
            jArr2 = (long[]) b.first;
            jArr = (long[]) b.second;
        }
        if (a3.b == null) {
            return null;
        }
        return new Track(c2.a, a2, ((Long) e3.first).longValue(), b2, scaleLargeTimestamp, a3.b, a3.d, a3.a, a3.c, jArr2, jArr);
    }

    private static h a(Track track, a.C0060a aVar, GaplessInfoHolder gaplessInfoHolder) throws ParserException {
        AbstractC0061b bVar;
        boolean z;
        int i;
        int i2;
        boolean z2;
        long j;
        long[] jArr;
        int[] iArr;
        Track track2;
        int i3;
        int[] iArr2;
        long[] jArr2;
        int i4;
        int i5;
        int[] iArr3;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z3;
        a.b d2 = aVar.d(1937011578);
        if (d2 != null) {
            bVar = new d(d2, track.format);
        } else {
            a.b d3 = aVar.d(1937013298);
            if (d3 != null) {
                bVar = new e(d3);
            } else {
                throw ParserException.createForMalformedContainer("Track has no sample table size information", null);
            }
        }
        int a2 = bVar.a();
        if (a2 == 0) {
            return new h(track, new long[0], new int[0], 0, new long[0], new int[0], 0L);
        }
        a.b d4 = aVar.d(1937007471);
        if (d4 == null) {
            d4 = (a.b) Assertions.checkNotNull(aVar.d(1668232756));
            z = true;
        } else {
            z = false;
        }
        ParsableByteArray parsableByteArray = d4.b;
        ParsableByteArray parsableByteArray2 = ((a.b) Assertions.checkNotNull(aVar.d(1937011555))).b;
        ParsableByteArray parsableByteArray3 = ((a.b) Assertions.checkNotNull(aVar.d(1937011827))).b;
        a.b d5 = aVar.d(1937011571);
        ParsableByteArray parsableByteArray4 = d5 != null ? d5.b : null;
        a.b d6 = aVar.d(1668576371);
        ParsableByteArray parsableByteArray5 = d6 != null ? d6.b : null;
        a aVar2 = new a(parsableByteArray2, parsableByteArray, z);
        parsableByteArray3.setPosition(12);
        int readUnsignedIntToInt = parsableByteArray3.readUnsignedIntToInt() - 1;
        int readUnsignedIntToInt2 = parsableByteArray3.readUnsignedIntToInt();
        int readUnsignedIntToInt3 = parsableByteArray3.readUnsignedIntToInt();
        if (parsableByteArray5 != null) {
            parsableByteArray5.setPosition(12);
            i = parsableByteArray5.readUnsignedIntToInt();
        } else {
            i = 0;
        }
        if (parsableByteArray4 != null) {
            parsableByteArray4.setPosition(12);
            i2 = parsableByteArray4.readUnsignedIntToInt();
            if (i2 > 0) {
                i6 = parsableByteArray4.readUnsignedIntToInt() - 1;
            } else {
                i6 = -1;
                parsableByteArray4 = null;
            }
        } else {
            i6 = -1;
            i2 = 0;
        }
        int b = bVar.b();
        String str = track.format.sampleMimeType;
        if (b == -1 || !((MimeTypes.AUDIO_RAW.equals(str) || MimeTypes.AUDIO_MLAW.equals(str) || MimeTypes.AUDIO_ALAW.equals(str)) && readUnsignedIntToInt == 0 && i == 0 && i2 == 0)) {
            i7 = i2;
            z2 = false;
        } else {
            i7 = i2;
            z2 = true;
        }
        if (z2) {
            long[] jArr3 = new long[aVar2.a];
            int[] iArr4 = new int[aVar2.a];
            while (aVar2.a()) {
                jArr3[aVar2.b] = aVar2.d;
                iArr4[aVar2.b] = aVar2.c;
            }
            FixedSampleSizeRechunker.Results a3 = FixedSampleSizeRechunker.a(b, jArr3, iArr4, readUnsignedIntToInt3);
            long[] jArr4 = a3.offsets;
            int[] iArr5 = a3.sizes;
            int i10 = a3.maximumSize;
            long[] jArr5 = a3.timestamps;
            int[] iArr6 = a3.flags;
            j = a3.duration;
            i4 = a2;
            jArr2 = jArr4;
            iArr2 = iArr5;
            i3 = i10;
            jArr = jArr5;
            iArr = iArr6;
            track2 = track;
        } else {
            jArr2 = new long[a2];
            iArr2 = new int[a2];
            jArr = new long[a2];
            iArr = new int[a2];
            int i11 = readUnsignedIntToInt;
            int i12 = readUnsignedIntToInt3;
            int i13 = 0;
            int i14 = 0;
            int i15 = 0;
            long j2 = 0;
            long j3 = 0;
            int i16 = 0;
            int i17 = 0;
            int i18 = i;
            while (true) {
                if (i14 >= a2) {
                    i11 = i11;
                    i4 = a2;
                    i8 = i15;
                    i9 = i17;
                    break;
                }
                boolean z4 = true;
                while (i15 == 0) {
                    z4 = aVar2.a();
                    if (!z4) {
                        break;
                    }
                    j3 = aVar2.d;
                    i15 = aVar2.c;
                    a2 = a2;
                    i11 = i11;
                }
                i11 = i11;
                if (!z4) {
                    Log.w("AtomParsers", "Unexpected end of chunk data");
                    jArr2 = Arrays.copyOf(jArr2, i14);
                    iArr2 = Arrays.copyOf(iArr2, i14);
                    jArr = Arrays.copyOf(jArr, i14);
                    iArr = Arrays.copyOf(iArr, i14);
                    i4 = i14;
                    i8 = i15;
                    i9 = i17;
                    break;
                }
                if (parsableByteArray5 != null) {
                    int i19 = i18;
                    while (i16 == 0 && i19 > 0) {
                        i16 = parsableByteArray5.readUnsignedIntToInt();
                        i17 = parsableByteArray5.readInt();
                        i19--;
                    }
                    i16--;
                    i18 = i19;
                } else {
                    i18 = i18;
                    i17 = i17;
                }
                jArr2[i14] = j3;
                iArr2[i14] = bVar.c();
                if (iArr2[i14] > i13) {
                    i13 = iArr2[i14];
                    jArr2 = jArr2;
                } else {
                    jArr2 = jArr2;
                }
                jArr[i14] = j2 + i17;
                iArr[i14] = parsableByteArray4 == null ? 1 : 0;
                if (i14 == i6) {
                    iArr[i14] = 1;
                    i7--;
                    if (i7 > 0) {
                        i6 = ((ParsableByteArray) Assertions.checkNotNull(parsableByteArray4)).readUnsignedIntToInt() - 1;
                    }
                }
                j2 += i12;
                readUnsignedIntToInt2--;
                if (readUnsignedIntToInt2 == 0 && i11 > 0) {
                    int readUnsignedIntToInt4 = parsableByteArray3.readUnsignedIntToInt();
                    i11--;
                    i12 = parsableByteArray3.readInt();
                    readUnsignedIntToInt2 = readUnsignedIntToInt4;
                }
                j3 += iArr2[i14];
                i15--;
                i14++;
                i17 = i17;
                a2 = a2;
            }
            j = j2 + i9;
            if (parsableByteArray5 != null) {
                for (int i20 = i18; i20 > 0; i20--) {
                    if (parsableByteArray5.readUnsignedIntToInt() != 0) {
                        z3 = false;
                        break;
                    }
                    parsableByteArray5.readInt();
                }
            }
            z3 = true;
            if (i7 == 0 && readUnsignedIntToInt2 == 0 && i8 == 0 && i11 == 0 && i16 == 0 && z3) {
                i3 = i13;
                track2 = track;
            } else {
                track2 = track;
                int i21 = track2.id;
                String str2 = !z3 ? ", ctts invalid" : "";
                i3 = i13;
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 262);
                sb.append("Inconsistent stbl box for track ");
                sb.append(i21);
                sb.append(": remainingSynchronizationSamples ");
                sb.append(i7);
                sb.append(", remainingSamplesAtTimestampDelta ");
                sb.append(readUnsignedIntToInt2);
                sb.append(", remainingSamplesInChunk ");
                sb.append(i8);
                sb.append(", remainingTimestampDeltaChanges ");
                sb.append(i11);
                sb.append(", remainingSamplesAtTimestampOffset ");
                sb.append(i16);
                sb.append(str2);
                Log.w("AtomParsers", sb.toString());
            }
        }
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(j, 1000000L, track2.timescale);
        if (track2.editListDurations == null) {
            Util.scaleLargeTimestampsInPlace(jArr, 1000000L, track2.timescale);
            return new h(track, jArr2, iArr2, i3, jArr, iArr, scaleLargeTimestamp);
        }
        if (track2.editListDurations.length == 1 && track2.type == 1 && jArr.length >= 2) {
            long j4 = ((long[]) Assertions.checkNotNull(track2.editListMediaTimes))[0];
            i5 = i4;
            long scaleLargeTimestamp2 = Util.scaleLargeTimestamp(track2.editListDurations[0], track2.timescale, track2.movieTimescale) + j4;
            if (a(jArr, j, j4, scaleLargeTimestamp2)) {
                long j5 = j - scaleLargeTimestamp2;
                long scaleLargeTimestamp3 = Util.scaleLargeTimestamp(j4 - jArr[0], track2.format.sampleRate, track2.timescale);
                long scaleLargeTimestamp4 = Util.scaleLargeTimestamp(j5, track2.format.sampleRate, track2.timescale);
                if (!(scaleLargeTimestamp3 == 0 && scaleLargeTimestamp4 == 0) && scaleLargeTimestamp3 <= 2147483647L && scaleLargeTimestamp4 <= 2147483647L) {
                    gaplessInfoHolder.encoderDelay = (int) scaleLargeTimestamp3;
                    gaplessInfoHolder.encoderPadding = (int) scaleLargeTimestamp4;
                    Util.scaleLargeTimestampsInPlace(jArr, 1000000L, track2.timescale);
                    return new h(track, jArr2, iArr2, i3, jArr, iArr, Util.scaleLargeTimestamp(track2.editListDurations[0], 1000000L, track2.movieTimescale));
                }
            }
        } else {
            i5 = i4;
        }
        if (track2.editListDurations.length == 1 && track2.editListDurations[0] == 0) {
            long j6 = ((long[]) Assertions.checkNotNull(track2.editListMediaTimes))[0];
            for (int i22 = 0; i22 < jArr.length; i22++) {
                jArr[i22] = Util.scaleLargeTimestamp(jArr[i22] - j6, 1000000L, track2.timescale);
            }
            return new h(track, jArr2, iArr2, i3, jArr, iArr, Util.scaleLargeTimestamp(j - j6, 1000000L, track2.timescale));
        }
        boolean z5 = track2.type == 1;
        int[] iArr7 = new int[track2.editListDurations.length];
        int[] iArr8 = new int[track2.editListDurations.length];
        long[] jArr6 = (long[]) Assertions.checkNotNull(track2.editListMediaTimes);
        int i23 = 0;
        boolean z6 = false;
        int i24 = 0;
        int i25 = 0;
        while (i23 < track2.editListDurations.length) {
            long j7 = jArr6[i23];
            if (j7 != -1) {
                jArr6 = jArr6;
                i3 = i3;
                long scaleLargeTimestamp5 = Util.scaleLargeTimestamp(track2.editListDurations[i23], track2.timescale, track2.movieTimescale);
                iArr7[i23] = Util.binarySearchFloor(jArr, j7, true, true);
                iArr8[i23] = Util.binarySearchCeil(jArr, j7 + scaleLargeTimestamp5, z5, false);
                while (iArr7[i23] < iArr8[i23] && (iArr[iArr7[i23]] & 1) == 0) {
                    iArr7[i23] = iArr7[i23] + 1;
                }
                i24 += iArr8[i23] - iArr7[i23];
                z6 |= i25 != iArr7[i23];
                i25 = iArr8[i23];
            } else {
                jArr6 = jArr6;
                i3 = i3;
                i25 = i25;
                z6 = z6;
            }
            i23++;
            iArr2 = iArr2;
        }
        int[] iArr9 = iArr2;
        int i26 = i3;
        int i27 = 0;
        boolean z7 = true;
        if (i24 == i5) {
            z7 = false;
        }
        boolean z8 = z6 | z7;
        long[] jArr7 = z8 ? new long[i24] : jArr2;
        int[] iArr10 = z8 ? new int[i24] : iArr9;
        if (z8) {
            i26 = 0;
        }
        int[] iArr11 = z8 ? new int[i24] : iArr;
        long[] jArr8 = new long[i24];
        int i28 = 0;
        long j8 = 0;
        while (i27 < track2.editListDurations.length) {
            long j9 = track2.editListMediaTimes[i27];
            int i29 = iArr7[i27];
            int i30 = iArr8[i27];
            if (z8) {
                iArr7 = iArr7;
                int i31 = i30 - i29;
                System.arraycopy(jArr2, i29, jArr7, i28, i31);
                jArr2 = jArr2;
                iArr3 = iArr9;
                System.arraycopy(iArr3, i29, iArr10, i28, i31);
                System.arraycopy(iArr, i29, iArr11, i28, i31);
            } else {
                iArr7 = iArr7;
                jArr2 = jArr2;
                iArr3 = iArr9;
            }
            int i32 = i28;
            int i33 = i29;
            while (i33 < i30) {
                jArr8[i32] = Util.scaleLargeTimestamp(j8, 1000000L, track2.movieTimescale) + Util.scaleLargeTimestamp(Math.max(0L, jArr[i33] - j9), 1000000L, track2.timescale);
                if (z8 && iArr10[i32] > i26) {
                    i26 = iArr3[i33];
                }
                i32++;
                i33++;
                iArr = iArr;
                iArr8 = iArr8;
                jArr = jArr;
                iArr11 = iArr11;
                i30 = i30;
            }
            j8 += track2.editListDurations[i27];
            i27++;
            iArr = iArr;
            iArr9 = iArr3;
            iArr8 = iArr8;
            jArr = jArr;
            i28 = i32;
            iArr11 = iArr11;
        }
        return new h(track, jArr7, iArr10, i26, jArr8, iArr11, Util.scaleLargeTimestamp(j8, 1000000L, track2.movieTimescale));
    }

    @Nullable
    private static Metadata a(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        a(parsableByteArray);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1768715124) {
                parsableByteArray.setPosition(position);
                return b(parsableByteArray, position + readInt);
            }
            parsableByteArray.setPosition(position + readInt);
        }
        return null;
    }

    @Nullable
    private static Metadata b(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i) {
            Metadata.Entry a2 = d.a(parsableByteArray);
            if (a2 != null) {
                arrayList.add(a2);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    @Nullable
    private static Metadata c(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() != 1935766900) {
                parsableByteArray.setPosition(position + readInt);
            } else if (readInt < 14) {
                return null;
            } else {
                parsableByteArray.skipBytes(5);
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                if (readUnsignedByte != 12 && readUnsignedByte != 13) {
                    return null;
                }
                float f2 = readUnsignedByte == 12 ? 240.0f : 120.0f;
                parsableByteArray.skipBytes(1);
                return new Metadata(new SmtaMetadataEntry(f2, parsableByteArray.readUnsignedByte()));
            }
        }
        return null;
    }

    private static long b(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        if (a.a(parsableByteArray.readInt()) != 0) {
            i = 16;
        }
        parsableByteArray.skipBytes(i);
        return parsableByteArray.readUnsignedInt();
    }

    private static f c(ParsableByteArray parsableByteArray) {
        boolean z;
        int i = 8;
        parsableByteArray.setPosition(8);
        int a2 = a.a(parsableByteArray.readInt());
        parsableByteArray.skipBytes(a2 == 0 ? 8 : 16);
        int readInt = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int position = parsableByteArray.getPosition();
        if (a2 == 0) {
            i = 4;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= i) {
                z = true;
                break;
            } else if (parsableByteArray.getData()[position + i3] != -1) {
                z = false;
                break;
            } else {
                i3++;
            }
        }
        long j = C.TIME_UNSET;
        if (z) {
            parsableByteArray.skipBytes(i);
        } else {
            long readUnsignedInt = a2 == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            if (readUnsignedInt != 0) {
                j = readUnsignedInt;
            }
        }
        parsableByteArray.skipBytes(16);
        int readInt2 = parsableByteArray.readInt();
        int readInt3 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int readInt4 = parsableByteArray.readInt();
        int readInt5 = parsableByteArray.readInt();
        if (readInt2 == 0 && readInt3 == 65536 && readInt4 == -65536 && readInt5 == 0) {
            i2 = 90;
        } else if (readInt2 == 0 && readInt3 == -65536 && readInt4 == 65536 && readInt5 == 0) {
            i2 = 270;
        } else if (readInt2 == -65536 && readInt3 == 0 && readInt4 == 0 && readInt5 == -65536) {
            i2 = Opcodes.GETFIELD;
        }
        return new f(readInt, j, i2);
    }

    private static int d(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        return parsableByteArray.readInt();
    }

    private static Pair<Long, String> e(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        int a2 = a.a(parsableByteArray.readInt());
        parsableByteArray.skipBytes(a2 == 0 ? 8 : 16);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (a2 == 0) {
            i = 4;
        }
        parsableByteArray.skipBytes(i);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        StringBuilder sb = new StringBuilder(3);
        sb.append((char) (((readUnsignedShort >> 10) & 31) + 96));
        sb.append((char) (((readUnsignedShort >> 5) & 31) + 96));
        sb.append((char) ((readUnsignedShort & 31) + 96));
        return Pair.create(Long.valueOf(readUnsignedInt), sb.toString());
    }

    private static c a(ParsableByteArray parsableByteArray, int i, int i2, String str, @Nullable DrmInitData drmInitData, boolean z) throws ParserException {
        int i3;
        parsableByteArray.setPosition(12);
        int readInt = parsableByteArray.readInt();
        c cVar = new c(readInt);
        for (int i4 = 0; i4 < readInt; i4++) {
            int position = parsableByteArray.getPosition();
            int readInt2 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(readInt2 > 0, "childAtomSize must be positive");
            int readInt3 = parsableByteArray.readInt();
            if (readInt3 == 1635148593 || readInt3 == 1635148595 || readInt3 == 1701733238 || readInt3 == 1831958048 || readInt3 == 1836070006 || readInt3 == 1752589105 || readInt3 == 1751479857 || readInt3 == 1932670515 || readInt3 == 1211250227 || readInt3 == 1987063864 || readInt3 == 1987063865 || readInt3 == 1635135537 || readInt3 == 1685479798 || readInt3 == 1685479729 || readInt3 == 1685481573 || readInt3 == 1685481521) {
                i3 = position;
                a(parsableByteArray, readInt3, i3, readInt2, i, i2, drmInitData, cVar, i4);
            } else if (readInt3 == 1836069985 || readInt3 == 1701733217 || readInt3 == 1633889587 || readInt3 == 1700998451 || readInt3 == 1633889588 || readInt3 == 1685353315 || readInt3 == 1685353317 || readInt3 == 1685353320 || readInt3 == 1685353324 || readInt3 == 1685353336 || readInt3 == 1935764850 || readInt3 == 1935767394 || readInt3 == 1819304813 || readInt3 == 1936684916 || readInt3 == 1953984371 || readInt3 == 778924082 || readInt3 == 778924083 || readInt3 == 1835557169 || readInt3 == 1835560241 || readInt3 == 1634492771 || readInt3 == 1634492791 || readInt3 == 1970037111 || readInt3 == 1332770163 || readInt3 == 1716281667) {
                i3 = position;
                a(parsableByteArray, readInt3, position, readInt2, i, str, z, drmInitData, cVar, i4);
            } else if (readInt3 == 1414810956 || readInt3 == 1954034535 || readInt3 == 2004251764 || readInt3 == 1937010800 || readInt3 == 1664495672) {
                a(parsableByteArray, readInt3, position, readInt2, i, str, cVar);
                i3 = position;
            } else if (readInt3 == 1835365492) {
                a(parsableByteArray, readInt3, position, i, cVar);
                i3 = position;
            } else if (readInt3 == 1667329389) {
                cVar.b = new Format.Builder().setId(i).setSampleMimeType(MimeTypes.APPLICATION_CAMERA_MOTION).build();
                i3 = position;
            } else {
                i3 = position;
            }
            parsableByteArray.setPosition(i3 + readInt2);
        }
        return cVar;
    }

    private static void a(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, c cVar) {
        String str2;
        parsableByteArray.setPosition(i2 + 8 + 8);
        ImmutableList immutableList = null;
        long j = Long.MAX_VALUE;
        if (i == 1414810956) {
            str2 = MimeTypes.APPLICATION_TTML;
        } else if (i == 1954034535) {
            str2 = MimeTypes.APPLICATION_TX3G;
            int i5 = (i3 - 8) - 8;
            byte[] bArr = new byte[i5];
            parsableByteArray.readBytes(bArr, 0, i5);
            immutableList = ImmutableList.of(bArr);
        } else if (i == 2004251764) {
            str2 = MimeTypes.APPLICATION_MP4VTT;
        } else if (i == 1937010800) {
            str2 = MimeTypes.APPLICATION_TTML;
            j = 0;
        } else if (i == 1664495672) {
            str2 = MimeTypes.APPLICATION_MP4CEA608;
            cVar.d = 1;
        } else {
            throw new IllegalStateException();
        }
        cVar.b = new Format.Builder().setId(i4).setSampleMimeType(str2).setLanguage(str).setSubsampleOffsetUs(j).setInitializationData(immutableList).build();
    }

    private static void a(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, int i5, @Nullable DrmInitData drmInitData, c cVar, int i6) throws ParserException {
        DrmInitData drmInitData2;
        List<byte[]> list;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int i7 = i2;
        int i8 = i3;
        DrmInitData drmInitData3 = drmInitData;
        parsableByteArray2.setPosition(i7 + 8 + 8);
        parsableByteArray2.skipBytes(16);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray2.skipBytes(50);
        int position = parsableByteArray.getPosition();
        int i9 = i;
        if (i9 == 1701733238) {
            Pair<Integer, TrackEncryptionBox> c2 = c(parsableByteArray2, i7, i8);
            if (c2 != null) {
                i9 = ((Integer) c2.first).intValue();
                drmInitData3 = drmInitData3 == null ? null : drmInitData3.copyWithSchemeType(((TrackEncryptionBox) c2.second).schemeType);
                cVar.a[i6] = (TrackEncryptionBox) c2.second;
            }
            parsableByteArray2.setPosition(position);
        }
        String str = i9 == 1831958048 ? "video/mpeg" : i9 == 1211250227 ? "video/3gpp" : null;
        int i10 = -1;
        float f2 = 1.0f;
        byte[] bArr = null;
        List<byte[]> list2 = null;
        String str2 = null;
        ColorInfo colorInfo = null;
        boolean z = false;
        while (true) {
            if (position - i7 < i8) {
                parsableByteArray2.setPosition(position);
                int position2 = parsableByteArray.getPosition();
                drmInitData2 = drmInitData3;
                int readInt = parsableByteArray.readInt();
                if (readInt == 0) {
                    list = list2;
                    if (parsableByteArray.getPosition() - i7 == i8) {
                    }
                } else {
                    list = list2;
                }
                ExtractorUtil.checkContainerInput(readInt > 0, "childAtomSize must be positive");
                int readInt2 = parsableByteArray.readInt();
                if (readInt2 == 1635148611) {
                    ExtractorUtil.checkContainerInput(str == null, null);
                    str = MimeTypes.VIDEO_H264;
                    parsableByteArray2.setPosition(position2 + 8);
                    AvcConfig parse = AvcConfig.parse(parsableByteArray);
                    List<byte[]> list3 = parse.initializationData;
                    cVar.c = parse.nalUnitLengthFieldLength;
                    if (!z) {
                        f2 = parse.pixelWidthAspectRatio;
                    }
                    str2 = parse.codecs;
                    list2 = list3;
                } else if (readInt2 == 1752589123) {
                    ExtractorUtil.checkContainerInput(str == null, null);
                    str = MimeTypes.VIDEO_H265;
                    parsableByteArray2.setPosition(position2 + 8);
                    HevcConfig parse2 = HevcConfig.parse(parsableByteArray);
                    List<byte[]> list4 = parse2.initializationData;
                    cVar.c = parse2.nalUnitLengthFieldLength;
                    str2 = parse2.codecs;
                    list2 = list4;
                } else if (readInt2 == 1685480259 || readInt2 == 1685485123) {
                    DolbyVisionConfig parse3 = DolbyVisionConfig.parse(parsableByteArray);
                    if (parse3 != null) {
                        str2 = parse3.codecs;
                        str = MimeTypes.VIDEO_DOLBY_VISION;
                    }
                    list2 = list;
                } else if (readInt2 == 1987076931) {
                    ExtractorUtil.checkContainerInput(str == null, null);
                    str = i9 == 1987063864 ? MimeTypes.VIDEO_VP8 : MimeTypes.VIDEO_VP9;
                    list2 = list;
                } else if (readInt2 == 1635135811) {
                    ExtractorUtil.checkContainerInput(str == null, null);
                    str = MimeTypes.VIDEO_AV1;
                    list2 = list;
                } else if (readInt2 == 1681012275) {
                    ExtractorUtil.checkContainerInput(str == null, null);
                    str = "video/3gpp";
                    list2 = list;
                } else if (readInt2 == 1702061171) {
                    ExtractorUtil.checkContainerInput(str == null, null);
                    Pair<String, byte[]> e2 = e(parsableByteArray2, position2);
                    str = (String) e2.first;
                    byte[] bArr2 = (byte[]) e2.second;
                    list2 = bArr2 != null ? ImmutableList.of(bArr2) : list;
                } else if (readInt2 == 1885434736) {
                    list2 = list;
                    f2 = d(parsableByteArray2, position2);
                    z = true;
                } else if (readInt2 == 1937126244) {
                    list2 = list;
                    bArr = d(parsableByteArray2, position2, readInt);
                } else if (readInt2 == 1936995172) {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    i10 = 3;
                    parsableByteArray2.skipBytes(3);
                    if (readUnsignedByte == 0) {
                        switch (parsableByteArray.readUnsignedByte()) {
                            case 0:
                                i10 = 0;
                                break;
                            case 1:
                                i10 = 1;
                                break;
                            case 2:
                                i10 = 2;
                                break;
                        }
                        list2 = list;
                    }
                    i10 = i10;
                    list2 = list;
                } else {
                    if (readInt2 == 1668246642) {
                        int readInt3 = parsableByteArray.readInt();
                        boolean z2 = readInt3 == 1852009592;
                        if (z2 || readInt3 == 1852009571) {
                            int readUnsignedShort3 = parsableByteArray.readUnsignedShort();
                            int readUnsignedShort4 = parsableByteArray.readUnsignedShort();
                            parsableByteArray2.skipBytes(2);
                            ColorInfo colorInfo2 = new ColorInfo(ColorInfo.isoColorPrimariesToColorSpace(readUnsignedShort3), z2 && (parsableByteArray.readUnsignedByte() & 128) != 0 ? 1 : 2, ColorInfo.isoTransferCharacteristicsToColorTransfer(readUnsignedShort4), null);
                            list2 = list;
                            colorInfo = colorInfo2;
                        } else {
                            String valueOf = String.valueOf(a.c(readInt3));
                            Log.w("AtomParsers", valueOf.length() != 0 ? "Unsupported color type: ".concat(valueOf) : new String("Unsupported color type: "));
                        }
                    }
                    list2 = list;
                }
                position += readInt;
                drmInitData3 = drmInitData2;
                parsableByteArray2 = parsableByteArray;
                i7 = i2;
                i8 = i3;
            } else {
                drmInitData2 = drmInitData3;
                list = list2;
            }
        }
        if (str != null) {
            cVar.b = new Format.Builder().setId(i4).setSampleMimeType(str).setCodecs(str2).setWidth(readUnsignedShort).setHeight(readUnsignedShort2).setPixelWidthHeightRatio(f2).setRotationDegrees(i5).setProjectionData(bArr).setStereoMode(i10).setInitializationData(list).setDrmInitData(drmInitData2).setColorInfo(colorInfo).build();
        }
    }

    private static void a(ParsableByteArray parsableByteArray, int i, int i2, int i3, c cVar) {
        parsableByteArray.setPosition(i2 + 8 + 8);
        if (i == 1835365492) {
            parsableByteArray.readNullTerminatedString();
            String readNullTerminatedString = parsableByteArray.readNullTerminatedString();
            if (readNullTerminatedString != null) {
                cVar.b = new Format.Builder().setId(i3).setSampleMimeType(readNullTerminatedString).build();
            }
        }
    }

    @Nullable
    private static Pair<long[], long[]> b(a.C0060a aVar) {
        a.b d2 = aVar.d(1701606260);
        if (d2 == null) {
            return null;
        }
        ParsableByteArray parsableByteArray = d2.b;
        parsableByteArray.setPosition(8);
        int a2 = a.a(parsableByteArray.readInt());
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        long[] jArr = new long[readUnsignedIntToInt];
        long[] jArr2 = new long[readUnsignedIntToInt];
        for (int i = 0; i < readUnsignedIntToInt; i++) {
            jArr[i] = a2 == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
            jArr2[i] = a2 == 1 ? parsableByteArray.readLong() : parsableByteArray.readInt();
            if (parsableByteArray.readShort() == 1) {
                parsableByteArray.skipBytes(2);
            } else {
                throw new IllegalArgumentException("Unsupported media rate.");
            }
        }
        return Pair.create(jArr, jArr2);
    }

    private static float d(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8);
        return parsableByteArray.readUnsignedIntToInt() / parsableByteArray.readUnsignedIntToInt();
    }

    private static void a(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, boolean z, @Nullable DrmInitData drmInitData, c cVar, int i5) throws ParserException {
        int i6;
        int i7;
        DrmInitData drmInitData2;
        int i8;
        String str2;
        int i9;
        int i10;
        int i11;
        boolean z2;
        int i12 = i2;
        parsableByteArray.setPosition(i12 + 8 + 8);
        if (z) {
            i6 = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
        } else {
            parsableByteArray.skipBytes(8);
            i6 = 0;
        }
        boolean z3 = true;
        if (i6 == 0 || i6 == 1) {
            int readUnsignedShort = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
            i9 = parsableByteArray.readUnsignedFixedPoint1616();
            if (i6 == 1) {
                parsableByteArray.skipBytes(16);
            }
            i7 = readUnsignedShort;
        } else if (i6 == 2) {
            parsableByteArray.skipBytes(16);
            i9 = (int) Math.round(parsableByteArray.readDouble());
            i7 = parsableByteArray.readUnsignedIntToInt();
            parsableByteArray.skipBytes(20);
        } else {
            return;
        }
        int position = parsableByteArray.getPosition();
        int i13 = i;
        if (i13 == 1701733217) {
            Pair<Integer, TrackEncryptionBox> c2 = c(parsableByteArray, i12, i3);
            if (c2 != null) {
                i13 = ((Integer) c2.first).intValue();
                drmInitData2 = drmInitData == null ? null : drmInitData.copyWithSchemeType(((TrackEncryptionBox) c2.second).schemeType);
                cVar.a[i5] = (TrackEncryptionBox) c2.second;
            } else {
                drmInitData2 = drmInitData;
            }
            parsableByteArray.setPosition(position);
        } else {
            drmInitData2 = drmInitData;
        }
        if (i13 == 1633889587) {
            str2 = MimeTypes.AUDIO_AC3;
            i8 = -1;
        } else if (i13 == 1700998451) {
            str2 = MimeTypes.AUDIO_E_AC3;
            i8 = -1;
        } else if (i13 == 1633889588) {
            str2 = MimeTypes.AUDIO_AC4;
            i8 = -1;
        } else if (i13 == 1685353315) {
            str2 = MimeTypes.AUDIO_DTS;
            i8 = -1;
        } else if (i13 == 1685353320 || i13 == 1685353324) {
            str2 = MimeTypes.AUDIO_DTS_HD;
            i8 = -1;
        } else if (i13 == 1685353317) {
            str2 = MimeTypes.AUDIO_DTS_EXPRESS;
            i8 = -1;
        } else if (i13 == 1685353336) {
            str2 = MimeTypes.AUDIO_DTS_UHD;
            i8 = -1;
        } else if (i13 == 1935764850) {
            str2 = "audio/3gpp";
            i8 = -1;
        } else if (i13 == 1935767394) {
            str2 = MimeTypes.AUDIO_AMR_WB;
            i8 = -1;
        } else if (i13 == 1819304813 || i13 == 1936684916) {
            str2 = MimeTypes.AUDIO_RAW;
            i8 = 2;
        } else if (i13 == 1953984371) {
            str2 = MimeTypes.AUDIO_RAW;
            i8 = 268435456;
        } else if (i13 == 778924082 || i13 == 778924083) {
            str2 = "audio/mpeg";
            i8 = -1;
        } else if (i13 == 1835557169) {
            str2 = MimeTypes.AUDIO_MPEGH_MHA1;
            i8 = -1;
        } else if (i13 == 1835560241) {
            str2 = MimeTypes.AUDIO_MPEGH_MHM1;
            i8 = -1;
        } else if (i13 == 1634492771) {
            str2 = MimeTypes.AUDIO_ALAC;
            i8 = -1;
        } else if (i13 == 1634492791) {
            str2 = MimeTypes.AUDIO_ALAW;
            i8 = -1;
        } else if (i13 == 1970037111) {
            str2 = MimeTypes.AUDIO_MLAW;
            i8 = -1;
        } else if (i13 == 1332770163) {
            str2 = MimeTypes.AUDIO_OPUS;
            i8 = -1;
        } else if (i13 == 1716281667) {
            str2 = MimeTypes.AUDIO_FLAC;
            i8 = -1;
        } else {
            str2 = null;
            i8 = -1;
        }
        String str3 = null;
        ImmutableList immutableList = null;
        while (position - i12 < i3) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            if (readInt <= 0) {
                z3 = false;
            }
            ExtractorUtil.checkContainerInput(z3, "childAtomSize must be positive");
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1835557187) {
                int i14 = readInt - 13;
                byte[] bArr = new byte[i14];
                parsableByteArray.setPosition(position + 13);
                parsableByteArray.readBytes(bArr, 0, i14);
                immutableList = ImmutableList.of(bArr);
                z3 = true;
            } else if (readInt2 == 1702061171 || (z && readInt2 == 2002876005)) {
                z3 = true;
                if (readInt2 == 1702061171) {
                    i11 = position;
                    i10 = -1;
                } else {
                    i11 = b(parsableByteArray, position, readInt);
                    i10 = -1;
                }
                if (i11 != i10) {
                    Pair<String, byte[]> e2 = e(parsableByteArray, i11);
                    str2 = (String) e2.first;
                    byte[] bArr2 = (byte[]) e2.second;
                    if (bArr2 != null) {
                        if (MimeTypes.AUDIO_AAC.equals(str2)) {
                            AacUtil.Config parseAudioSpecificConfig = AacUtil.parseAudioSpecificConfig(bArr2);
                            i9 = parseAudioSpecificConfig.sampleRateHz;
                            i7 = parseAudioSpecificConfig.channelCount;
                            str3 = parseAudioSpecificConfig.codecs;
                        }
                        immutableList = ImmutableList.of(bArr2);
                    }
                }
            } else if (readInt2 == 1684103987) {
                parsableByteArray.setPosition(position + 8);
                cVar.b = Ac3Util.parseAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitData2);
                z2 = false;
                z3 = true;
            } else if (readInt2 == 1684366131) {
                parsableByteArray.setPosition(position + 8);
                cVar.b = Ac3Util.parseEAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitData2);
                z2 = false;
                z3 = true;
            } else if (readInt2 == 1684103988) {
                parsableByteArray.setPosition(position + 8);
                cVar.b = Ac4Util.parseAc4AnnexEFormat(parsableByteArray, Integer.toString(i4), str, drmInitData2);
                z2 = false;
                z3 = true;
            } else if (readInt2 == 1684305011) {
                cVar.b = new Format.Builder().setId(i4).setSampleMimeType(str2).setChannelCount(i7).setSampleRate(i9).setDrmInitData(drmInitData2).setLanguage(str).build();
                z2 = false;
                z3 = true;
            } else if (readInt2 == 1682927731) {
                int i15 = readInt - 8;
                byte[] bArr3 = a;
                byte[] copyOf = Arrays.copyOf(bArr3, bArr3.length + i15);
                parsableByteArray.setPosition(position + 8);
                parsableByteArray.readBytes(copyOf, a.length, i15);
                immutableList = OpusUtil.buildInitializationData(copyOf);
                z3 = true;
            } else if (readInt2 == 1684425825) {
                int i16 = readInt - 12;
                byte[] bArr4 = new byte[i16 + 4];
                bArr4[0] = 102;
                z3 = true;
                bArr4[1] = 76;
                bArr4[2] = 97;
                bArr4[3] = 67;
                parsableByteArray.setPosition(position + 12);
                parsableByteArray.readBytes(bArr4, 4, i16);
                immutableList = ImmutableList.of(bArr4);
            } else {
                z3 = true;
                if (readInt2 == 1634492771) {
                    int i17 = readInt - 12;
                    byte[] bArr5 = new byte[i17];
                    parsableByteArray.setPosition(position + 12);
                    parsableByteArray.readBytes(bArr5, 0, i17);
                    Pair<Integer, Integer> parseAlacAudioSpecificConfig = CodecSpecificDataUtil.parseAlacAudioSpecificConfig(bArr5);
                    i9 = ((Integer) parseAlacAudioSpecificConfig.first).intValue();
                    i7 = ((Integer) parseAlacAudioSpecificConfig.second).intValue();
                    immutableList = ImmutableList.of(bArr5);
                } else {
                    z2 = false;
                }
            }
            position += readInt;
            i12 = i2;
        }
        if (cVar.b == null && str2 != null) {
            cVar.b = new Format.Builder().setId(i4).setSampleMimeType(str2).setCodecs(str3).setChannelCount(i7).setSampleRate(i9).setPcmEncoding(i8).setInitializationData(immutableList).setDrmInitData(drmInitData2).setLanguage(str).build();
        }
    }

    private static int b(ParsableByteArray parsableByteArray, int i, int i2) throws ParserException {
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(readInt > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == 1702061171) {
                return position;
            }
            position += readInt;
        }
        return -1;
    }

    private static Pair<String, byte[]> e(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8 + 4);
        parsableByteArray.skipBytes(1);
        f(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((readUnsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort());
        }
        if ((readUnsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        f(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if ("audio/mpeg".equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS_HD.equals(mimeTypeFromMp4ObjectType)) {
            return Pair.create(mimeTypeFromMp4ObjectType, null);
        }
        parsableByteArray.skipBytes(12);
        parsableByteArray.skipBytes(1);
        int f2 = f(parsableByteArray);
        byte[] bArr = new byte[f2];
        parsableByteArray.readBytes(bArr, 0, f2);
        return Pair.create(mimeTypeFromMp4ObjectType, bArr);
    }

    @Nullable
    private static Pair<Integer, TrackEncryptionBox> c(ParsableByteArray parsableByteArray, int i, int i2) throws ParserException {
        Pair<Integer, TrackEncryptionBox> a2;
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(readInt > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == 1936289382 && (a2 = a(parsableByteArray, position, readInt)) != null) {
                return a2;
            }
            position += readInt;
        }
        return null;
    }

    @Nullable
    static Pair<Integer, TrackEncryptionBox> a(ParsableByteArray parsableByteArray, int i, int i2) throws ParserException {
        int i3 = i + 8;
        int i4 = -1;
        String str = null;
        Integer num = null;
        int i5 = 0;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1718775137) {
                num = Integer.valueOf(parsableByteArray.readInt());
            } else if (readInt2 == 1935894637) {
                parsableByteArray.skipBytes(4);
                str = parsableByteArray.readString(4);
            } else if (readInt2 == 1935894633) {
                i4 = i3;
                i5 = readInt;
            }
            i3 += readInt;
        }
        if (!C.CENC_TYPE_cenc.equals(str) && !C.CENC_TYPE_cbc1.equals(str) && !C.CENC_TYPE_cens.equals(str) && !C.CENC_TYPE_cbcs.equals(str)) {
            return null;
        }
        boolean z = true;
        ExtractorUtil.checkContainerInput(num != null, "frma atom is mandatory");
        ExtractorUtil.checkContainerInput(i4 != -1, "schi atom is mandatory");
        TrackEncryptionBox a2 = a(parsableByteArray, i4, i5, str);
        if (a2 == null) {
            z = false;
        }
        ExtractorUtil.checkContainerInput(z, "tenc atom is mandatory");
        return Pair.create(num, (TrackEncryptionBox) Util.castNonNull(a2));
    }

    @Nullable
    private static TrackEncryptionBox a(ParsableByteArray parsableByteArray, int i, int i2, String str) {
        int i3;
        int i4;
        byte[] bArr;
        int i5 = i + 8;
        while (i5 - i < i2) {
            parsableByteArray.setPosition(i5);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1952804451) {
                int a2 = a.a(parsableByteArray.readInt());
                parsableByteArray.skipBytes(1);
                if (a2 == 0) {
                    parsableByteArray.skipBytes(1);
                    i4 = 0;
                    i3 = 0;
                } else {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    i4 = (readUnsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                    i3 = readUnsignedByte & 15;
                }
                boolean z = parsableByteArray.readUnsignedByte() == 1;
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                byte[] bArr2 = new byte[16];
                parsableByteArray.readBytes(bArr2, 0, bArr2.length);
                if (!z || readUnsignedByte2 != 0) {
                    bArr = null;
                } else {
                    int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                    byte[] bArr3 = new byte[readUnsignedByte3];
                    parsableByteArray.readBytes(bArr3, 0, readUnsignedByte3);
                    bArr = bArr3;
                }
                return new TrackEncryptionBox(z, str, readUnsignedByte2, bArr2, i4, i3, bArr);
            }
            i5 += readInt;
        }
        return null;
    }

    @Nullable
    private static byte[] d(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1886547818) {
                return Arrays.copyOfRange(parsableByteArray.getData(), i3, readInt + i3);
            }
            i3 += readInt;
        }
        return null;
    }

    private static int f(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = readUnsignedByte & 127;
        while ((readUnsignedByte & 128) == 128) {
            readUnsignedByte = parsableByteArray.readUnsignedByte();
            i = (i << 7) | (readUnsignedByte & 127);
        }
        return i;
    }

    private static boolean a(long[] jArr, long j, long j2, long j3) {
        int length = jArr.length - 1;
        return jArr[0] <= j2 && j2 < jArr[Util.constrainValue(4, 0, length)] && jArr[Util.constrainValue(jArr.length - 4, 0, length)] < j3 && j3 <= j;
    }

    /* compiled from: AtomParsers.java */
    /* loaded from: classes2.dex */
    public static final class a {
        public final int a;
        public int b;
        public int c;
        public long d;
        private final boolean e;
        private final ParsableByteArray f;
        private final ParsableByteArray g;
        private int h;
        private int i;

        public a(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z) throws ParserException {
            this.g = parsableByteArray;
            this.f = parsableByteArray2;
            this.e = z;
            parsableByteArray2.setPosition(12);
            this.a = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.i = parsableByteArray.readUnsignedIntToInt();
            ExtractorUtil.checkContainerInput(parsableByteArray.readInt() != 1 ? false : true, "first_chunk must be 1");
            this.b = -1;
        }

        public boolean a() {
            long j;
            int i = this.b + 1;
            this.b = i;
            if (i == this.a) {
                return false;
            }
            if (this.e) {
                j = this.f.readUnsignedLongToLong();
            } else {
                j = this.f.readUnsignedInt();
            }
            this.d = j;
            if (this.b == this.h) {
                this.c = this.g.readUnsignedIntToInt();
                this.g.skipBytes(4);
                int i2 = this.i - 1;
                this.i = i2;
                this.h = i2 > 0 ? this.g.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    /* compiled from: AtomParsers.java */
    /* loaded from: classes2.dex */
    public static final class f {
        private final int a;
        private final long b;
        private final int c;

        public f(int i, long j, int i2) {
            this.a = i;
            this.b = j;
            this.c = i2;
        }
    }

    /* compiled from: AtomParsers.java */
    /* loaded from: classes2.dex */
    public static final class c {
        public final TrackEncryptionBox[] a;
        @Nullable
        public Format b;
        public int c;
        public int d = 0;

        public c(int i) {
            this.a = new TrackEncryptionBox[i];
        }
    }

    /* compiled from: AtomParsers.java */
    /* loaded from: classes2.dex */
    public static final class d implements AbstractC0061b {
        private final int a;
        private final int b;
        private final ParsableByteArray c;

        public d(a.b bVar, Format format) {
            this.c = bVar.b;
            this.c.setPosition(12);
            int readUnsignedIntToInt = this.c.readUnsignedIntToInt();
            if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
                int pcmFrameSize = Util.getPcmFrameSize(format.pcmEncoding, format.channelCount);
                if (readUnsignedIntToInt == 0 || readUnsignedIntToInt % pcmFrameSize != 0) {
                    StringBuilder sb = new StringBuilder(88);
                    sb.append("Audio sample size mismatch. stsd sample size: ");
                    sb.append(pcmFrameSize);
                    sb.append(", stsz sample size: ");
                    sb.append(readUnsignedIntToInt);
                    Log.w("AtomParsers", sb.toString());
                    readUnsignedIntToInt = pcmFrameSize;
                }
            }
            this.a = readUnsignedIntToInt == 0 ? -1 : readUnsignedIntToInt;
            this.b = this.c.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.b.AbstractC0061b
        public int a() {
            return this.b;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.b.AbstractC0061b
        public int b() {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.b.AbstractC0061b
        public int c() {
            int i = this.a;
            return i == -1 ? this.c.readUnsignedIntToInt() : i;
        }
    }

    /* compiled from: AtomParsers.java */
    /* loaded from: classes2.dex */
    public static final class e implements AbstractC0061b {
        private final ParsableByteArray a;
        private final int b;
        private final int c;
        private int d;
        private int e;

        @Override // com.google.android.exoplayer2.extractor.mp4.b.AbstractC0061b
        public int b() {
            return -1;
        }

        public e(a.b bVar) {
            this.a = bVar.b;
            this.a.setPosition(12);
            this.c = this.a.readUnsignedIntToInt() & 255;
            this.b = this.a.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.b.AbstractC0061b
        public int a() {
            return this.b;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.b.AbstractC0061b
        public int c() {
            int i = this.c;
            if (i == 8) {
                return this.a.readUnsignedByte();
            }
            if (i == 16) {
                return this.a.readUnsignedShort();
            }
            int i2 = this.d;
            this.d = i2 + 1;
            if (i2 % 2 != 0) {
                return this.e & 15;
            }
            this.e = this.a.readUnsignedByte();
            return (this.e & PsExtractor.VIDEO_STREAM_MASK) >> 4;
        }
    }
}
