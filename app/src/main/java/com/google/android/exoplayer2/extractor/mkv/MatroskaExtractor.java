package com.google.android.exoplayer2.extractor.mkv;

import android.util.Pair;
import android.util.SparseArray;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.renderscript.ScriptIntrinsicBLAS;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.constant.CacheConstants;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.AvcConfig;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.android.exoplayer2.video.DolbyVisionConfig;
import com.google.android.exoplayer2.video.HevcConfig;
import com.google.common.collect.ImmutableList;
import io.netty.handler.codec.http.HttpConstants;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public class MatroskaExtractor implements Extractor {
    public static final int FLAG_DISABLE_SEEK_FOR_CUES = 1;
    private static final Map<String, Integer> e;
    private boolean A;
    private int B;
    private long C;
    private boolean D;
    private long E;
    private long F;
    private long G;
    @Nullable
    private LongArray H;
    @Nullable
    private LongArray I;
    private boolean J;
    private boolean K;
    private int L;
    private long M;
    private long N;
    private int O;
    private int P;
    private int[] Q;
    private int R;
    private int S;
    private int T;
    private int U;
    private boolean V;
    private int W;
    private int X;
    private int Y;
    private boolean Z;
    private boolean aa;
    private boolean ab;
    private int ac;
    private byte ad;
    private boolean ae;
    private ExtractorOutput af;
    private final b f;
    private final d g;
    private final SparseArray<b> h;
    private final boolean i;
    private final ParsableByteArray j;
    private final ParsableByteArray k;
    private final ParsableByteArray l;
    private final ParsableByteArray m;
    private final ParsableByteArray n;
    private final ParsableByteArray o;
    private final ParsableByteArray p;
    private final ParsableByteArray q;
    private final ParsableByteArray r;
    private final ParsableByteArray s;
    private ByteBuffer t;
    private long u;
    private long v;
    private long w;
    private long x;
    private long y;
    @Nullable
    private b z;
    public static final ExtractorsFactory FACTORY = $$Lambda$MatroskaExtractor$gk9aWFYBdOUkXzWsSo1efS7jAFw.INSTANCE;
    private static final byte[] a = {49, 10, 48, 48, 58, 48, 48, 58, 48, 48, HttpConstants.COMMA, 48, 48, 48, 32, 45, 45, 62, 32, 48, 48, 58, 48, 48, 58, 48, 48, HttpConstants.COMMA, 48, 48, 48, 10};
    private static final byte[] b = Util.getUtf8Bytes("Format: Start, End, ReadOrder, Layer, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
    private static final byte[] c = {68, 105, 97, 108, 111, 103, 117, 101, 58, 32, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, HttpConstants.COMMA, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, HttpConstants.COMMA};
    private static final UUID d = new UUID(72057594037932032L, -9223371306706625679L);

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    @CallSuper
    protected int getElementType(int i) {
        switch (i) {
            case ScriptIntrinsicBLAS.NON_UNIT /* 131 */:
            case 136:
            case 155:
            case Opcodes.IF_ICMPEQ /* 159 */:
            case Opcodes.ARETURN /* 176 */:
            case 179:
            case 186:
            case 215:
            case 231:
            case 238:
            case 241:
            case 251:
            case 16871:
            case 16980:
            case 17029:
            case 17143:
            case 18401:
            case 18408:
            case 20529:
            case 20530:
            case 21420:
            case 21432:
            case 21680:
            case 21682:
            case 21690:
            case 21930:
            case 21945:
            case 21946:
            case 21947:
            case 21948:
            case 21949:
            case 21998:
            case 22186:
            case 22203:
            case 25188:
            case 30321:
            case 2352003:
            case 2807729:
                return 2;
            case 134:
            case 17026:
            case 21358:
            case 2274716:
                return 3;
            case Opcodes.IF_ICMPNE /* 160 */:
            case Opcodes.IF_ACMPNE /* 166 */:
            case 174:
            case Opcodes.INVOKESPECIAL /* 183 */:
            case Opcodes.NEW /* 187 */:
            case 224:
            case 225:
            case 16868:
            case 18407:
            case 19899:
            case 20532:
            case 20533:
            case 21936:
            case 21968:
            case 25152:
            case 28032:
            case 30113:
            case 30320:
            case 290298740:
            case 357149030:
            case 374648427:
            case 408125543:
            case 440786851:
            case 475249515:
            case 524531317:
                return 1;
            case Opcodes.IF_ICMPLT /* 161 */:
            case Opcodes.IF_ICMPGT /* 163 */:
            case Opcodes.IF_ACMPEQ /* 165 */:
            case 16877:
            case 16981:
            case 18402:
            case 21419:
            case 25506:
            case 30322:
                return 4;
            case Opcodes.PUTFIELD /* 181 */:
            case 17545:
            case 21969:
            case 21970:
            case 21971:
            case 21972:
            case 21973:
            case 21974:
            case 21975:
            case 21976:
            case 21977:
            case 21978:
            case 30323:
            case 30324:
            case 30325:
                return 5;
            default:
                return 0;
        }
    }

    @CallSuper
    protected boolean isLevel1Element(int i) {
        return i == 357149030 || i == 524531317 || i == 475249515 || i == 374648427;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public final void release() {
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("htc_video_rotA-000", 0);
        hashMap.put("htc_video_rotA-090", 90);
        hashMap.put("htc_video_rotA-180", Integer.valueOf((int) Opcodes.GETFIELD));
        hashMap.put("htc_video_rotA-270", 270);
        e = Collections.unmodifiableMap(hashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] g() {
        return new Extractor[]{new MatroskaExtractor()};
    }

    public MatroskaExtractor() {
        this(0);
    }

    public MatroskaExtractor(int i) {
        this(new a(), i);
    }

    MatroskaExtractor(b bVar, int i) {
        this.v = -1L;
        this.w = C.TIME_UNSET;
        this.x = C.TIME_UNSET;
        this.y = C.TIME_UNSET;
        this.E = -1L;
        this.F = -1L;
        this.G = C.TIME_UNSET;
        this.f = bVar;
        this.f.a(new a());
        this.i = (i & 1) == 0;
        this.g = new d();
        this.h = new SparseArray<>();
        this.l = new ParsableByteArray(4);
        this.m = new ParsableByteArray(ByteBuffer.allocate(4).putInt(-1).array());
        this.n = new ParsableByteArray(4);
        this.j = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.k = new ParsableByteArray(4);
        this.o = new ParsableByteArray();
        this.p = new ParsableByteArray();
        this.q = new ParsableByteArray(8);
        this.r = new ParsableByteArray();
        this.s = new ParsableByteArray();
        this.Q = new int[1];
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public final boolean sniff(ExtractorInput extractorInput) throws IOException {
        return new c().a(extractorInput);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public final void init(ExtractorOutput extractorOutput) {
        this.af = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    @CallSuper
    public void seek(long j, long j2) {
        this.G = C.TIME_UNSET;
        this.L = 0;
        this.f.a();
        this.g.a();
        e();
        for (int i = 0; i < this.h.size(); i++) {
            this.h.valueAt(i).b();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public final int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        this.K = false;
        boolean z = true;
        while (z && !this.K) {
            z = this.f.a(extractorInput);
            if (z && a(positionHolder, extractorInput.getPosition())) {
                return 1;
            }
        }
        if (z) {
            return 0;
        }
        for (int i = 0; i < this.h.size(); i++) {
            b valueAt = this.h.valueAt(i);
            valueAt.d();
            valueAt.a();
        }
        return -1;
    }

    @CallSuper
    protected void startMasterElement(int i, long j, long j2) throws ParserException {
        f();
        if (i == 160) {
            this.V = false;
        } else if (i == 174) {
            this.z = new b();
        } else if (i == 187) {
            this.J = false;
        } else if (i == 19899) {
            this.B = -1;
            this.C = -1L;
        } else if (i == 20533) {
            c(i).g = true;
        } else if (i == 21968) {
            c(i).w = true;
        } else if (i == 25152) {
        } else {
            if (i == 408125543) {
                long j3 = this.v;
                if (j3 == -1 || j3 == j) {
                    this.v = j;
                    this.u = j2;
                    return;
                }
                throw ParserException.createForMalformedContainer("Multiple Segment elements not supported", null);
            } else if (i == 475249515) {
                this.H = new LongArray();
                this.I = new LongArray();
            } else if (i != 524531317 || this.A) {
            } else {
                if (!this.i || this.E == -1) {
                    this.af.seekMap(new SeekMap.Unseekable(this.y));
                    this.A = true;
                    return;
                }
                this.D = true;
            }
        }
    }

    @CallSuper
    protected void endMasterElement(int i) throws ParserException {
        f();
        if (i != 160) {
            if (i == 174) {
                b bVar = (b) Assertions.checkStateNotNull(this.z);
                if (bVar.b != null) {
                    if (a(bVar.b)) {
                        bVar.a(this.af, bVar.c);
                        this.h.put(bVar.c, bVar);
                    }
                    this.z = null;
                    return;
                }
                throw ParserException.createForMalformedContainer("CodecId is missing in TrackEntry element", null);
            } else if (i == 19899) {
                int i2 = this.B;
                if (i2 != -1) {
                    long j = this.C;
                    if (j != -1) {
                        if (i2 == 475249515) {
                            this.E = j;
                            return;
                        }
                        return;
                    }
                }
                throw ParserException.createForMalformedContainer("Mandatory element SeekID or SeekPosition not found", null);
            } else if (i == 25152) {
                a(i);
                if (!this.z.g) {
                    return;
                }
                if (this.z.i != null) {
                    this.z.k = new DrmInitData(new DrmInitData.SchemeData(C.UUID_NIL, MimeTypes.VIDEO_WEBM, this.z.i.encryptionKey));
                    return;
                }
                throw ParserException.createForMalformedContainer("Encrypted Track found but ContentEncKeyID was not found", null);
            } else if (i == 28032) {
                a(i);
                if (this.z.g && this.z.h != null) {
                    throw ParserException.createForMalformedContainer("Combining encryption and compression is not supported", null);
                }
            } else if (i == 357149030) {
                if (this.w == C.TIME_UNSET) {
                    this.w = 1000000L;
                }
                long j2 = this.x;
                if (j2 != C.TIME_UNSET) {
                    this.y = a(j2);
                }
            } else if (i != 374648427) {
                if (i == 475249515) {
                    if (!this.A) {
                        this.af.seekMap(a(this.H, this.I));
                        this.A = true;
                    }
                    this.H = null;
                    this.I = null;
                }
            } else if (this.h.size() != 0) {
                this.af.endTracks();
            } else {
                throw ParserException.createForMalformedContainer("No valid tracks were found", null);
            }
        } else if (this.L == 2) {
            int i3 = 0;
            for (int i4 = 0; i4 < this.P; i4++) {
                i3 += this.Q[i4];
            }
            b bVar2 = this.h.get(this.R);
            bVar2.d();
            int i5 = i3;
            int i6 = 0;
            while (i6 < this.P) {
                long j3 = this.M + ((bVar2.e * i6) / 1000);
                int i7 = this.T;
                if (i6 == 0 && !this.V) {
                    i7 |= 1;
                }
                int i8 = this.Q[i6];
                int i9 = i5 - i8;
                a(bVar2, j3, i7, i8, i9);
                i6++;
                i5 = i9;
            }
            this.L = 0;
        }
    }

    @CallSuper
    protected void integerElement(int i, long j) throws ParserException {
        boolean z = false;
        switch (i) {
            case ScriptIntrinsicBLAS.NON_UNIT /* 131 */:
                c(i).d = (int) j;
                return;
            case 136:
                b c2 = c(i);
                if (j == 1) {
                    z = true;
                }
                c2.U = z;
                return;
            case 155:
                this.N = a(j);
                return;
            case Opcodes.IF_ICMPEQ /* 159 */:
                c(i).N = (int) j;
                return;
            case Opcodes.ARETURN /* 176 */:
                c(i).l = (int) j;
                return;
            case 179:
                b(i);
                this.H.add(a(j));
                return;
            case 186:
                c(i).m = (int) j;
                return;
            case 215:
                c(i).c = (int) j;
                return;
            case 231:
                this.G = a(j);
                return;
            case 238:
                this.U = (int) j;
                return;
            case 241:
                if (!this.J) {
                    b(i);
                    this.I.add(j);
                    this.J = true;
                    return;
                }
                return;
            case 251:
                this.V = true;
                return;
            case 16871:
                c(i).X = (int) j;
                return;
            case 16980:
                if (j != 3) {
                    StringBuilder sb = new StringBuilder(50);
                    sb.append("ContentCompAlgo ");
                    sb.append(j);
                    sb.append(" not supported");
                    throw ParserException.createForMalformedContainer(sb.toString(), null);
                }
                return;
            case 17029:
                if (j < 1 || j > 2) {
                    StringBuilder sb2 = new StringBuilder(53);
                    sb2.append("DocTypeReadVersion ");
                    sb2.append(j);
                    sb2.append(" not supported");
                    throw ParserException.createForMalformedContainer(sb2.toString(), null);
                }
                return;
            case 17143:
                if (j != 1) {
                    StringBuilder sb3 = new StringBuilder(50);
                    sb3.append("EBMLReadVersion ");
                    sb3.append(j);
                    sb3.append(" not supported");
                    throw ParserException.createForMalformedContainer(sb3.toString(), null);
                }
                return;
            case 18401:
                if (j != 5) {
                    StringBuilder sb4 = new StringBuilder(49);
                    sb4.append("ContentEncAlgo ");
                    sb4.append(j);
                    sb4.append(" not supported");
                    throw ParserException.createForMalformedContainer(sb4.toString(), null);
                }
                return;
            case 18408:
                if (j != 1) {
                    StringBuilder sb5 = new StringBuilder(56);
                    sb5.append("AESSettingsCipherMode ");
                    sb5.append(j);
                    sb5.append(" not supported");
                    throw ParserException.createForMalformedContainer(sb5.toString(), null);
                }
                return;
            case 20529:
                if (j != 0) {
                    StringBuilder sb6 = new StringBuilder(55);
                    sb6.append("ContentEncodingOrder ");
                    sb6.append(j);
                    sb6.append(" not supported");
                    throw ParserException.createForMalformedContainer(sb6.toString(), null);
                }
                return;
            case 20530:
                if (j != 1) {
                    StringBuilder sb7 = new StringBuilder(55);
                    sb7.append("ContentEncodingScope ");
                    sb7.append(j);
                    sb7.append(" not supported");
                    throw ParserException.createForMalformedContainer(sb7.toString(), null);
                }
                return;
            case 21420:
                this.C = j + this.v;
                return;
            case 21432:
                int i2 = (int) j;
                a(i);
                if (i2 == 3) {
                    this.z.v = 1;
                    return;
                } else if (i2 != 15) {
                    switch (i2) {
                        case 0:
                            this.z.v = 0;
                            return;
                        case 1:
                            this.z.v = 2;
                            return;
                        default:
                            return;
                    }
                } else {
                    this.z.v = 3;
                    return;
                }
            case 21680:
                c(i).n = (int) j;
                return;
            case 21682:
                c(i).p = (int) j;
                return;
            case 21690:
                c(i).o = (int) j;
                return;
            case 21930:
                b c3 = c(i);
                if (j == 1) {
                    z = true;
                }
                c3.T = z;
                return;
            case 21945:
                a(i);
                switch ((int) j) {
                    case 1:
                        this.z.z = 2;
                        return;
                    case 2:
                        this.z.z = 1;
                        return;
                    default:
                        return;
                }
            case 21946:
                a(i);
                int isoTransferCharacteristicsToColorTransfer = ColorInfo.isoTransferCharacteristicsToColorTransfer((int) j);
                if (isoTransferCharacteristicsToColorTransfer != -1) {
                    this.z.y = isoTransferCharacteristicsToColorTransfer;
                    return;
                }
                return;
            case 21947:
                a(i);
                this.z.w = true;
                int isoColorPrimariesToColorSpace = ColorInfo.isoColorPrimariesToColorSpace((int) j);
                if (isoColorPrimariesToColorSpace != -1) {
                    this.z.x = isoColorPrimariesToColorSpace;
                    return;
                }
                return;
            case 21948:
                c(i).A = (int) j;
                return;
            case 21949:
                c(i).B = (int) j;
                return;
            case 21998:
                c(i).f = (int) j;
                return;
            case 22186:
                c(i).Q = j;
                return;
            case 22203:
                c(i).R = j;
                return;
            case 25188:
                c(i).O = (int) j;
                return;
            case 30321:
                a(i);
                switch ((int) j) {
                    case 0:
                        this.z.q = 0;
                        return;
                    case 1:
                        this.z.q = 1;
                        return;
                    case 2:
                        this.z.q = 2;
                        return;
                    case 3:
                        this.z.q = 3;
                        return;
                    default:
                        return;
                }
            case 2352003:
                c(i).e = (int) j;
                return;
            case 2807729:
                this.w = j;
                return;
            default:
                return;
        }
    }

    @CallSuper
    protected void floatElement(int i, double d2) throws ParserException {
        if (i == 181) {
            c(i).P = (int) d2;
        } else if (i != 17545) {
            switch (i) {
                case 21969:
                    c(i).C = (float) d2;
                    return;
                case 21970:
                    c(i).D = (float) d2;
                    return;
                case 21971:
                    c(i).E = (float) d2;
                    return;
                case 21972:
                    c(i).F = (float) d2;
                    return;
                case 21973:
                    c(i).G = (float) d2;
                    return;
                case 21974:
                    c(i).H = (float) d2;
                    return;
                case 21975:
                    c(i).I = (float) d2;
                    return;
                case 21976:
                    c(i).J = (float) d2;
                    return;
                case 21977:
                    c(i).K = (float) d2;
                    return;
                case 21978:
                    c(i).L = (float) d2;
                    return;
                default:
                    switch (i) {
                        case 30323:
                            c(i).r = (float) d2;
                            return;
                        case 30324:
                            c(i).s = (float) d2;
                            return;
                        case 30325:
                            c(i).t = (float) d2;
                            return;
                        default:
                            return;
                    }
            }
        } else {
            this.x = (long) d2;
        }
    }

    @CallSuper
    protected void stringElement(int i, String str) throws ParserException {
        if (i == 134) {
            c(i).b = str;
        } else if (i != 17026) {
            if (i == 21358) {
                c(i).a = str;
            } else if (i == 2274716) {
                c(i).Y = str;
            }
        } else if (!"webm".equals(str) && !"matroska".equals(str)) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 22);
            sb.append("DocType ");
            sb.append(str);
            sb.append(" not supported");
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:83:0x025f, code lost:
        throw com.google.android.exoplayer2.ParserException.createForMalformedContainer("EBML lacing sample size out of range.", null);
     */
    @androidx.annotation.CallSuper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void binaryElement(int r21, int r22, com.google.android.exoplayer2.extractor.ExtractorInput r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 795
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.binaryElement(int, int, com.google.android.exoplayer2.extractor.ExtractorInput):void");
    }

    protected void handleBlockAddIDExtraData(b bVar, ExtractorInput extractorInput, int i) throws IOException {
        if (bVar.X == 1685485123 || bVar.X == 1685480259) {
            bVar.M = new byte[i];
            extractorInput.readFully(bVar.M, 0, i);
            return;
        }
        extractorInput.skipFully(i);
    }

    protected void handleBlockAdditionalData(b bVar, int i, ExtractorInput extractorInput, int i2) throws IOException {
        if (i != 4 || !"V_VP9".equals(bVar.b)) {
            extractorInput.skipFully(i2);
            return;
        }
        this.s.reset(i2);
        extractorInput.readFully(this.s.getData(), 0, i2);
    }

    @EnsuresNonNull({"currentTrack"})
    private void a(int i) throws ParserException {
        if (this.z == null) {
            StringBuilder sb = new StringBuilder(43);
            sb.append("Element ");
            sb.append(i);
            sb.append(" must be in a TrackEntry");
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }
    }

    @EnsuresNonNull({"cueTimesUs", "cueClusterPositions"})
    private void b(int i) throws ParserException {
        if (this.H == null || this.I == null) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Element ");
            sb.append(i);
            sb.append(" must be in a Cues");
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }
    }

    private b c(int i) throws ParserException {
        a(i);
        return this.z;
    }

    @RequiresNonNull({"#1.output"})
    private void a(b bVar, long j, int i, int i2, int i3) {
        int i4;
        int i5;
        if (bVar.S != null) {
            bVar.S.a(bVar, j, i, i2, i3);
        } else {
            if ("S_TEXT/UTF8".equals(bVar.b) || "S_TEXT/ASS".equals(bVar.b)) {
                if (this.P > 1) {
                    Log.w("MatroskaExtractor", "Skipping subtitle sample in laced block.");
                } else if (this.N == C.TIME_UNSET) {
                    Log.w("MatroskaExtractor", "Skipping subtitle sample with no duration.");
                } else {
                    a(bVar.b, this.N, this.p.getData());
                    int position = this.p.getPosition();
                    while (true) {
                        if (position >= this.p.limit()) {
                            break;
                        } else if (this.p.getData()[position] == 0) {
                            this.p.setLimit(position);
                            break;
                        } else {
                            position++;
                        }
                    }
                    TrackOutput trackOutput = bVar.V;
                    ParsableByteArray parsableByteArray = this.p;
                    trackOutput.sampleData(parsableByteArray, parsableByteArray.limit());
                    i2 += this.p.limit();
                }
            }
            if ((268435456 & i) == 0) {
                i5 = i;
                i4 = i2;
            } else if (this.P > 1) {
                i5 = i & (-268435457);
                i4 = i2;
            } else {
                int limit = this.s.limit();
                bVar.V.sampleData(this.s, limit, 2);
                i4 = i2 + limit;
                i5 = i;
            }
            bVar.V.sampleMetadata(j, i5, i4, i3, bVar.i);
        }
        this.K = true;
    }

    private void a(ExtractorInput extractorInput, int i) throws IOException {
        if (this.l.limit() < i) {
            if (this.l.capacity() < i) {
                ParsableByteArray parsableByteArray = this.l;
                parsableByteArray.ensureCapacity(Math.max(parsableByteArray.capacity() * 2, i));
            }
            extractorInput.readFully(this.l.getData(), this.l.limit(), i - this.l.limit());
            this.l.setLimit(i);
        }
    }

    @RequiresNonNull({"#2.output"})
    private int a(ExtractorInput extractorInput, b bVar, int i) throws IOException {
        int i2;
        if ("S_TEXT/UTF8".equals(bVar.b)) {
            a(extractorInput, a, i);
            return d();
        } else if ("S_TEXT/ASS".equals(bVar.b)) {
            a(extractorInput, c, i);
            return d();
        } else {
            TrackOutput trackOutput = bVar.V;
            boolean z = true;
            if (!this.Z) {
                if (bVar.g) {
                    this.T &= -1073741825;
                    int i3 = 128;
                    if (!this.aa) {
                        extractorInput.readFully(this.l.getData(), 0, 1);
                        this.W++;
                        if ((this.l.getData()[0] & 128) != 128) {
                            this.ad = this.l.getData()[0];
                            this.aa = true;
                        } else {
                            throw ParserException.createForMalformedContainer("Extension bit is set in signal byte", null);
                        }
                    }
                    if ((this.ad & 1) == 1) {
                        boolean z2 = (this.ad & 2) == 2;
                        this.T |= 1073741824;
                        if (!this.ae) {
                            extractorInput.readFully(this.q.getData(), 0, 8);
                            this.W += 8;
                            this.ae = true;
                            byte[] data = this.l.getData();
                            if (!z2) {
                                i3 = 0;
                            }
                            data[0] = (byte) (i3 | 8);
                            this.l.setPosition(0);
                            trackOutput.sampleData(this.l, 1, 1);
                            this.X++;
                            this.q.setPosition(0);
                            trackOutput.sampleData(this.q, 8, 1);
                            this.X += 8;
                        }
                        if (z2) {
                            if (!this.ab) {
                                extractorInput.readFully(this.l.getData(), 0, 1);
                                this.W++;
                                this.l.setPosition(0);
                                this.ac = this.l.readUnsignedByte();
                                this.ab = true;
                            }
                            int i4 = this.ac * 4;
                            this.l.reset(i4);
                            extractorInput.readFully(this.l.getData(), 0, i4);
                            this.W += i4;
                            short s = (short) ((this.ac / 2) + 1);
                            int i5 = (s * 6) + 2;
                            ByteBuffer byteBuffer = this.t;
                            if (byteBuffer == null || byteBuffer.capacity() < i5) {
                                this.t = ByteBuffer.allocate(i5);
                            }
                            this.t.position(0);
                            this.t.putShort(s);
                            int i6 = 0;
                            int i7 = 0;
                            while (true) {
                                i2 = this.ac;
                                if (i6 >= i2) {
                                    break;
                                }
                                int readUnsignedIntToInt = this.l.readUnsignedIntToInt();
                                if (i6 % 2 == 0) {
                                    this.t.putShort((short) (readUnsignedIntToInt - i7));
                                } else {
                                    this.t.putInt(readUnsignedIntToInt - i7);
                                }
                                i6++;
                                i7 = readUnsignedIntToInt;
                            }
                            int i8 = (i - this.W) - i7;
                            if (i2 % 2 == 1) {
                                this.t.putInt(i8);
                            } else {
                                this.t.putShort((short) i8);
                                this.t.putInt(0);
                            }
                            this.r.reset(this.t.array(), i5);
                            trackOutput.sampleData(this.r, i5, 1);
                            this.X += i5;
                        }
                    }
                } else if (bVar.h != null) {
                    this.o.reset(bVar.h, bVar.h.length);
                }
                if (bVar.f > 0) {
                    this.T |= 268435456;
                    this.s.reset(0);
                    this.l.reset(4);
                    this.l.getData()[0] = (byte) ((i >> 24) & 255);
                    this.l.getData()[1] = (byte) ((i >> 16) & 255);
                    this.l.getData()[2] = (byte) ((i >> 8) & 255);
                    this.l.getData()[3] = (byte) (i & 255);
                    trackOutput.sampleData(this.l, 4, 2);
                    this.X += 4;
                }
                this.Z = true;
            }
            int limit = i + this.o.limit();
            if (!"V_MPEG4/ISO/AVC".equals(bVar.b) && !"V_MPEGH/ISO/HEVC".equals(bVar.b)) {
                if (bVar.S != null) {
                    if (this.o.limit() != 0) {
                        z = false;
                    }
                    Assertions.checkState(z);
                    bVar.S.a(extractorInput);
                }
                while (true) {
                    int i9 = this.W;
                    if (i9 >= limit) {
                        break;
                    }
                    int a2 = a(extractorInput, trackOutput, limit - i9);
                    this.W += a2;
                    this.X += a2;
                }
            } else {
                byte[] data2 = this.k.getData();
                data2[0] = 0;
                data2[1] = 0;
                data2[2] = 0;
                int i10 = bVar.W;
                int i11 = 4 - bVar.W;
                while (this.W < limit) {
                    int i12 = this.Y;
                    if (i12 == 0) {
                        a(extractorInput, data2, i11, i10);
                        this.W += i10;
                        this.k.setPosition(0);
                        this.Y = this.k.readUnsignedIntToInt();
                        this.j.setPosition(0);
                        trackOutput.sampleData(this.j, 4);
                        this.X += 4;
                    } else {
                        int a3 = a(extractorInput, trackOutput, i12);
                        this.W += a3;
                        this.X += a3;
                        this.Y -= a3;
                    }
                }
            }
            if ("A_VORBIS".equals(bVar.b)) {
                this.m.setPosition(0);
                trackOutput.sampleData(this.m, 4);
                this.X += 4;
            }
            return d();
        }
    }

    private int d() {
        int i = this.X;
        e();
        return i;
    }

    private void e() {
        this.W = 0;
        this.X = 0;
        this.Y = 0;
        this.Z = false;
        this.aa = false;
        this.ab = false;
        this.ac = 0;
        this.ad = (byte) 0;
        this.ae = false;
        this.o.reset(0);
    }

    private void a(ExtractorInput extractorInput, byte[] bArr, int i) throws IOException {
        int length = bArr.length + i;
        if (this.p.capacity() < length) {
            this.p.reset(Arrays.copyOf(bArr, length + i));
        } else {
            System.arraycopy(bArr, 0, this.p.getData(), 0, bArr.length);
        }
        extractorInput.readFully(this.p.getData(), bArr.length, i);
        this.p.setPosition(0);
        this.p.setLimit(length);
    }

    private static void a(String str, long j, byte[] bArr) {
        char c2;
        int i;
        byte[] bArr2;
        int hashCode = str.hashCode();
        if (hashCode != 738597099) {
            if (hashCode == 1422270023 && str.equals("S_TEXT/UTF8")) {
                c2 = 0;
            }
            c2 = 65535;
        } else {
            if (str.equals("S_TEXT/ASS")) {
                c2 = 1;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                bArr2 = a(j, "%02d:%02d:%02d,%03d", 1000L);
                i = 19;
                break;
            case 1:
                bArr2 = a(j, "%01d:%02d:%02d:%02d", 10000L);
                i = 21;
                break;
            default:
                throw new IllegalArgumentException();
        }
        System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
    }

    private static byte[] a(long j, String str, long j2) {
        Assertions.checkArgument(j != C.TIME_UNSET);
        int i = (int) (j / 3600000000L);
        long j3 = j - ((i * CacheConstants.HOUR) * 1000000);
        int i2 = (int) (j3 / 60000000);
        long j4 = j3 - ((i2 * 60) * 1000000);
        int i3 = (int) (j4 / 1000000);
        return Util.getUtf8Bytes(String.format(Locale.US, str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf((int) ((j4 - (i3 * 1000000)) / j2))));
    }

    private void a(ExtractorInput extractorInput, byte[] bArr, int i, int i2) throws IOException {
        int min = Math.min(i2, this.o.bytesLeft());
        extractorInput.readFully(bArr, i + min, i2 - min);
        if (min > 0) {
            this.o.readBytes(bArr, i, min);
        }
    }

    private int a(ExtractorInput extractorInput, TrackOutput trackOutput, int i) throws IOException {
        int bytesLeft = this.o.bytesLeft();
        if (bytesLeft <= 0) {
            return trackOutput.sampleData((DataReader) extractorInput, i, false);
        }
        int min = Math.min(i, bytesLeft);
        trackOutput.sampleData(this.o, min);
        return min;
    }

    private SeekMap a(@Nullable LongArray longArray, @Nullable LongArray longArray2) {
        int i;
        if (this.v == -1 || this.y == C.TIME_UNSET || longArray == null || longArray.size() == 0 || longArray2 == null || longArray2.size() != longArray.size()) {
            return new SeekMap.Unseekable(this.y);
        }
        int size = longArray.size();
        int[] iArr = new int[size];
        long[] jArr = new long[size];
        long[] jArr2 = new long[size];
        long[] jArr3 = new long[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            jArr3[i3] = longArray.get(i3);
            jArr[i3] = this.v + longArray2.get(i3);
        }
        while (true) {
            i = size - 1;
            if (i2 >= i) {
                break;
            }
            int i4 = i2 + 1;
            iArr[i2] = (int) (jArr[i4] - jArr[i2]);
            jArr2[i2] = jArr3[i4] - jArr3[i2];
            i2 = i4;
        }
        iArr[i] = (int) ((this.v + this.u) - jArr[i]);
        jArr2[i] = this.y - jArr3[i];
        long j = jArr2[i];
        if (j <= 0) {
            StringBuilder sb = new StringBuilder(72);
            sb.append("Discarding last cue point with unexpected duration: ");
            sb.append(j);
            Log.w("MatroskaExtractor", sb.toString());
            iArr = Arrays.copyOf(iArr, iArr.length - 1);
            jArr = Arrays.copyOf(jArr, jArr.length - 1);
            jArr2 = Arrays.copyOf(jArr2, jArr2.length - 1);
            jArr3 = Arrays.copyOf(jArr3, jArr3.length - 1);
        }
        return new ChunkIndex(iArr, jArr, jArr2, jArr3);
    }

    private boolean a(PositionHolder positionHolder, long j) {
        if (this.D) {
            this.F = j;
            positionHolder.position = this.E;
            this.D = false;
            return true;
        }
        if (this.A) {
            long j2 = this.F;
            if (j2 != -1) {
                positionHolder.position = j2;
                this.F = -1L;
                return true;
            }
        }
        return false;
    }

    private long a(long j) throws ParserException {
        long j2 = this.w;
        if (j2 != C.TIME_UNSET) {
            return Util.scaleLargeTimestamp(j, j2, 1000L);
        }
        throw ParserException.createForMalformedContainer("Can't scale timecode prior to timecodeScale being set.", null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static boolean a(String str) {
        char c2;
        switch (str.hashCode()) {
            case -2095576542:
                if (str.equals("V_MPEG4/ISO/AP")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -2095575984:
                if (str.equals("V_MPEG4/ISO/SP")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1985379776:
                if (str.equals("A_MS/ACM")) {
                    c2 = 23;
                    break;
                }
                c2 = 65535;
                break;
            case -1784763192:
                if (str.equals("A_TRUEHD")) {
                    c2 = 18;
                    break;
                }
                c2 = 65535;
                break;
            case -1730367663:
                if (str.equals("A_VORBIS")) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case -1482641358:
                if (str.equals("A_MPEG/L2")) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case -1482641357:
                if (str.equals("A_MPEG/L3")) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case -1373388978:
                if (str.equals("V_MS/VFW/FOURCC")) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -933872740:
                if (str.equals("S_DVBSUB")) {
                    c2 = 31;
                    break;
                }
                c2 = 65535;
                break;
            case -538363189:
                if (str.equals("V_MPEG4/ISO/ASP")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -538363109:
                if (str.equals("V_MPEG4/ISO/AVC")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -425012669:
                if (str.equals("S_VOBSUB")) {
                    c2 = 29;
                    break;
                }
                c2 = 65535;
                break;
            case -356037306:
                if (str.equals("A_DTS/LOSSLESS")) {
                    c2 = 21;
                    break;
                }
                c2 = 65535;
                break;
            case 62923557:
                if (str.equals("A_AAC")) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case 62923603:
                if (str.equals("A_AC3")) {
                    c2 = 16;
                    break;
                }
                c2 = 65535;
                break;
            case 62927045:
                if (str.equals("A_DTS")) {
                    c2 = 19;
                    break;
                }
                c2 = 65535;
                break;
            case 82318131:
                if (str.equals("V_AV1")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 82338133:
                if (str.equals("V_VP8")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 82338134:
                if (str.equals("V_VP9")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 99146302:
                if (str.equals("S_HDMV/PGS")) {
                    c2 = 30;
                    break;
                }
                c2 = 65535;
                break;
            case 444813526:
                if (str.equals("V_THEORA")) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case 542569478:
                if (str.equals("A_DTS/EXPRESS")) {
                    c2 = 20;
                    break;
                }
                c2 = 65535;
                break;
            case 635596514:
                if (str.equals("A_PCM/FLOAT/IEEE")) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                c2 = 65535;
                break;
            case 725948237:
                if (str.equals("A_PCM/INT/BIG")) {
                    c2 = 25;
                    break;
                }
                c2 = 65535;
                break;
            case 725957860:
                if (str.equals("A_PCM/INT/LIT")) {
                    c2 = 24;
                    break;
                }
                c2 = 65535;
                break;
            case 738597099:
                if (str.equals("S_TEXT/ASS")) {
                    c2 = 28;
                    break;
                }
                c2 = 65535;
                break;
            case 855502857:
                if (str.equals("V_MPEGH/ISO/HEVC")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 1422270023:
                if (str.equals("S_TEXT/UTF8")) {
                    c2 = 27;
                    break;
                }
                c2 = 65535;
                break;
            case 1809237540:
                if (str.equals("V_MPEG2")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 1950749482:
                if (str.equals("A_EAC3")) {
                    c2 = 17;
                    break;
                }
                c2 = 65535;
                break;
            case 1950789798:
                if (str.equals("A_FLAC")) {
                    c2 = 22;
                    break;
                }
                c2 = 65535;
                break;
            case 1951062397:
                if (str.equals("A_OPUS")) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                return true;
            default:
                return false;
        }
    }

    private static int[] a(@Nullable int[] iArr, int i) {
        if (iArr == null) {
            return new int[i];
        }
        return iArr.length >= i ? iArr : new int[Math.max(iArr.length * 2, i)];
    }

    @EnsuresNonNull({"extractorOutput"})
    private void f() {
        Assertions.checkStateNotNull(this.af);
    }

    /* loaded from: classes2.dex */
    private final class a implements EbmlProcessor {
        private a() {
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public int getElementType(int i) {
            return MatroskaExtractor.this.getElementType(i);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public boolean isLevel1Element(int i) {
            return MatroskaExtractor.this.isLevel1Element(i);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void startMasterElement(int i, long j, long j2) throws ParserException {
            MatroskaExtractor.this.startMasterElement(i, j, j2);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void endMasterElement(int i) throws ParserException {
            MatroskaExtractor.this.endMasterElement(i);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void integerElement(int i, long j) throws ParserException {
            MatroskaExtractor.this.integerElement(i, j);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void floatElement(int i, double d) throws ParserException {
            MatroskaExtractor.this.floatElement(i, d);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void stringElement(int i, String str) throws ParserException {
            MatroskaExtractor.this.stringElement(i, str);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void binaryElement(int i, int i2, ExtractorInput extractorInput) throws IOException {
            MatroskaExtractor.this.binaryElement(i, i2, extractorInput);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c {
        private final byte[] a = new byte[10];
        private boolean b;
        private int c;
        private long d;
        private int e;
        private int f;
        private int g;

        public void a() {
            this.b = false;
            this.c = 0;
        }

        public void a(ExtractorInput extractorInput) throws IOException {
            if (!this.b) {
                extractorInput.peekFully(this.a, 0, 10);
                extractorInput.resetPeekPosition();
                if (Ac3Util.parseTrueHdSyncframeAudioSampleCount(this.a) != 0) {
                    this.b = true;
                }
            }
        }

        @RequiresNonNull({"#1.output"})
        public void a(b bVar, long j, int i, int i2, int i3) {
            if (this.b) {
                int i4 = this.c;
                this.c = i4 + 1;
                if (i4 == 0) {
                    this.d = j;
                    this.e = i;
                    this.f = 0;
                }
                this.f += i2;
                this.g = i3;
                if (this.c >= 16) {
                    a(bVar);
                }
            }
        }

        @RequiresNonNull({"#1.output"})
        public void a(b bVar) {
            if (this.c > 0) {
                bVar.V.sampleMetadata(this.d, this.e, this.f, this.g, bVar.i);
                this.c = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b {
        public int A;
        public int B;
        public float C;
        public float D;
        public float E;
        public float F;
        public float G;
        public float H;
        public float I;
        public float J;
        public float K;
        public float L;
        public byte[] M;
        public int N;
        public int O;
        public int P;
        public long Q;
        public long R;
        public c S;
        public boolean T;
        public boolean U;
        public TrackOutput V;
        public int W;
        private int X;
        private String Y;
        public String a;
        public String b;
        public int c;
        public int d;
        public int e;
        public int f;
        public boolean g;
        public byte[] h;
        public TrackOutput.CryptoData i;
        public byte[] j;
        public DrmInitData k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        public float r;
        public float s;
        public float t;
        public byte[] u;
        public int v;
        public boolean w;
        public int x;
        public int y;
        public int z;

        private b() {
            this.l = -1;
            this.m = -1;
            this.n = -1;
            this.o = -1;
            this.p = 0;
            this.q = -1;
            this.r = 0.0f;
            this.s = 0.0f;
            this.t = 0.0f;
            this.u = null;
            this.v = -1;
            this.w = false;
            this.x = -1;
            this.y = -1;
            this.z = -1;
            this.A = 1000;
            this.B = 200;
            this.C = -1.0f;
            this.D = -1.0f;
            this.E = -1.0f;
            this.F = -1.0f;
            this.G = -1.0f;
            this.H = -1.0f;
            this.I = -1.0f;
            this.J = -1.0f;
            this.K = -1.0f;
            this.L = -1.0f;
            this.N = 1;
            this.O = -1;
            this.P = 8000;
            this.Q = 0L;
            this.R = 0L;
            this.U = true;
            this.Y = "eng";
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @EnsuresNonNull({"this.output"})
        @RequiresNonNull({"codecId"})
        public void a(ExtractorOutput extractorOutput, int i) throws ParserException {
            char c;
            String str;
            List<byte[]> list;
            String str2;
            int i2;
            int i3;
            DolbyVisionConfig parse;
            String str3 = this.b;
            int i4 = 1;
            int i5 = 4;
            int i6 = 0;
            switch (str3.hashCode()) {
                case -2095576542:
                    if (str3.equals("V_MPEG4/ISO/AP")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -2095575984:
                    if (str3.equals("V_MPEG4/ISO/SP")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1985379776:
                    if (str3.equals("A_MS/ACM")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -1784763192:
                    if (str3.equals("A_TRUEHD")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -1730367663:
                    if (str3.equals("A_VORBIS")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1482641358:
                    if (str3.equals("A_MPEG/L2")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1482641357:
                    if (str3.equals("A_MPEG/L3")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -1373388978:
                    if (str3.equals("V_MS/VFW/FOURCC")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -933872740:
                    if (str3.equals("S_DVBSUB")) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case -538363189:
                    if (str3.equals("V_MPEG4/ISO/ASP")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -538363109:
                    if (str3.equals("V_MPEG4/ISO/AVC")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -425012669:
                    if (str3.equals("S_VOBSUB")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -356037306:
                    if (str3.equals("A_DTS/LOSSLESS")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 62923557:
                    if (str3.equals("A_AAC")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 62923603:
                    if (str3.equals("A_AC3")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 62927045:
                    if (str3.equals("A_DTS")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 82318131:
                    if (str3.equals("V_AV1")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 82338133:
                    if (str3.equals("V_VP8")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 82338134:
                    if (str3.equals("V_VP9")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 99146302:
                    if (str3.equals("S_HDMV/PGS")) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case 444813526:
                    if (str3.equals("V_THEORA")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 542569478:
                    if (str3.equals("A_DTS/EXPRESS")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 635596514:
                    if (str3.equals("A_PCM/FLOAT/IEEE")) {
                        c = JSONLexer.EOI;
                        break;
                    }
                    c = 65535;
                    break;
                case 725948237:
                    if (str3.equals("A_PCM/INT/BIG")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 725957860:
                    if (str3.equals("A_PCM/INT/LIT")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 738597099:
                    if (str3.equals("S_TEXT/ASS")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case 855502857:
                    if (str3.equals("V_MPEGH/ISO/HEVC")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1422270023:
                    if (str3.equals("S_TEXT/UTF8")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 1809237540:
                    if (str3.equals("V_MPEG2")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1950749482:
                    if (str3.equals("A_EAC3")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1950789798:
                    if (str3.equals("A_FLAC")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 1951062397:
                    if (str3.equals("A_OPUS")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            int i7 = 4096;
            ColorInfo colorInfo = null;
            switch (c) {
                case 0:
                    str2 = MimeTypes.VIDEO_VP8;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 1:
                    str2 = MimeTypes.VIDEO_VP9;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 2:
                    str2 = MimeTypes.VIDEO_AV1;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 3:
                    str2 = MimeTypes.VIDEO_MPEG2;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 4:
                case 5:
                case 6:
                    str2 = MimeTypes.VIDEO_MP4V;
                    byte[] bArr = this.j;
                    list = bArr == null ? null : Collections.singletonList(bArr);
                    i7 = -1;
                    i5 = -1;
                    str = null;
                    break;
                case 7:
                    str2 = MimeTypes.VIDEO_H264;
                    AvcConfig parse2 = AvcConfig.parse(new ParsableByteArray(a(this.b)));
                    list = parse2.initializationData;
                    this.W = parse2.nalUnitLengthFieldLength;
                    str = parse2.codecs;
                    i7 = -1;
                    i5 = -1;
                    break;
                case '\b':
                    str2 = MimeTypes.VIDEO_H265;
                    HevcConfig parse3 = HevcConfig.parse(new ParsableByteArray(a(this.b)));
                    list = parse3.initializationData;
                    this.W = parse3.nalUnitLengthFieldLength;
                    str = parse3.codecs;
                    i7 = -1;
                    i5 = -1;
                    break;
                case '\t':
                    Pair<String, List<byte[]>> a = a(new ParsableByteArray(a(this.b)));
                    str2 = (String) a.first;
                    list = (List) a.second;
                    i7 = -1;
                    i5 = -1;
                    str = null;
                    break;
                case '\n':
                    str2 = MimeTypes.VIDEO_UNKNOWN;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 11:
                    str2 = MimeTypes.AUDIO_VORBIS;
                    i7 = 8192;
                    list = a(a(this.b));
                    i5 = -1;
                    str = null;
                    break;
                case '\f':
                    str2 = MimeTypes.AUDIO_OPUS;
                    i7 = 5760;
                    list = new ArrayList<>(3);
                    list.add(a(this.b));
                    list.add(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(this.Q).array());
                    list.add(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(this.R).array());
                    i5 = -1;
                    str = null;
                    break;
                case '\r':
                    str2 = MimeTypes.AUDIO_AAC;
                    list = Collections.singletonList(a(this.b));
                    AacUtil.Config parseAudioSpecificConfig = AacUtil.parseAudioSpecificConfig(this.j);
                    this.P = parseAudioSpecificConfig.sampleRateHz;
                    this.N = parseAudioSpecificConfig.channelCount;
                    str = parseAudioSpecificConfig.codecs;
                    i5 = -1;
                    i7 = -1;
                    break;
                case 14:
                    str2 = MimeTypes.AUDIO_MPEG_L2;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 15:
                    str2 = "audio/mpeg";
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 16:
                    str2 = MimeTypes.AUDIO_AC3;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 17:
                    str2 = MimeTypes.AUDIO_E_AC3;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 18:
                    str2 = MimeTypes.AUDIO_TRUEHD;
                    this.S = new c();
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 19:
                case 20:
                    str2 = MimeTypes.AUDIO_DTS;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 21:
                    str2 = MimeTypes.AUDIO_DTS_HD;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 22:
                    str2 = MimeTypes.AUDIO_FLAC;
                    list = Collections.singletonList(a(this.b));
                    i7 = -1;
                    i5 = -1;
                    str = null;
                    break;
                case 23:
                    str2 = MimeTypes.AUDIO_RAW;
                    if (!b(new ParsableByteArray(a(this.b)))) {
                        str2 = MimeTypes.AUDIO_UNKNOWN;
                        String valueOf = String.valueOf(str2);
                        Log.w("MatroskaExtractor", valueOf.length() != 0 ? "Non-PCM MS/ACM is unsupported. Setting mimeType to ".concat(valueOf) : new String("Non-PCM MS/ACM is unsupported. Setting mimeType to "));
                        i7 = -1;
                        i5 = -1;
                        list = null;
                        str = null;
                        break;
                    } else {
                        i5 = Util.getPcmEncoding(this.O);
                        if (i5 != 0) {
                            i7 = -1;
                            list = null;
                            str = null;
                            break;
                        } else {
                            str2 = MimeTypes.AUDIO_UNKNOWN;
                            int i8 = this.O;
                            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 60);
                            sb.append("Unsupported PCM bit depth: ");
                            sb.append(i8);
                            sb.append(". Setting mimeType to ");
                            sb.append(str2);
                            Log.w("MatroskaExtractor", sb.toString());
                            i7 = -1;
                            i5 = -1;
                            list = null;
                            str = null;
                            break;
                        }
                    }
                case 24:
                    str2 = MimeTypes.AUDIO_RAW;
                    i5 = Util.getPcmEncoding(this.O);
                    if (i5 != 0) {
                        i7 = -1;
                        list = null;
                        str = null;
                        break;
                    } else {
                        str2 = MimeTypes.AUDIO_UNKNOWN;
                        int i9 = this.O;
                        StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 74);
                        sb2.append("Unsupported little endian PCM bit depth: ");
                        sb2.append(i9);
                        sb2.append(". Setting mimeType to ");
                        sb2.append(str2);
                        Log.w("MatroskaExtractor", sb2.toString());
                        i7 = -1;
                        i5 = -1;
                        list = null;
                        str = null;
                        break;
                    }
                case 25:
                    str2 = MimeTypes.AUDIO_RAW;
                    int i10 = this.O;
                    if (i10 != 8) {
                        if (i10 != 16) {
                            str2 = MimeTypes.AUDIO_UNKNOWN;
                            StringBuilder sb3 = new StringBuilder(String.valueOf(str2).length() + 71);
                            sb3.append("Unsupported big endian PCM bit depth: ");
                            sb3.append(i10);
                            sb3.append(". Setting mimeType to ");
                            sb3.append(str2);
                            Log.w("MatroskaExtractor", sb3.toString());
                            i7 = -1;
                            i5 = -1;
                            list = null;
                            str = null;
                            break;
                        } else {
                            i5 = 268435456;
                            i7 = -1;
                            list = null;
                            str = null;
                            break;
                        }
                    } else {
                        i5 = 3;
                        i7 = -1;
                        list = null;
                        str = null;
                        break;
                    }
                case 26:
                    str2 = MimeTypes.AUDIO_RAW;
                    int i11 = this.O;
                    if (i11 != 32) {
                        str2 = MimeTypes.AUDIO_UNKNOWN;
                        StringBuilder sb4 = new StringBuilder(String.valueOf(str2).length() + 75);
                        sb4.append("Unsupported floating point PCM bit depth: ");
                        sb4.append(i11);
                        sb4.append(". Setting mimeType to ");
                        sb4.append(str2);
                        Log.w("MatroskaExtractor", sb4.toString());
                        i7 = -1;
                        i5 = -1;
                        list = null;
                        str = null;
                        break;
                    } else {
                        i7 = -1;
                        list = null;
                        str = null;
                        break;
                    }
                case 27:
                    str2 = MimeTypes.APPLICATION_SUBRIP;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 28:
                    str2 = MimeTypes.TEXT_SSA;
                    list = ImmutableList.of(MatroskaExtractor.b, a(this.b));
                    i7 = -1;
                    i5 = -1;
                    str = null;
                    break;
                case 29:
                    str2 = MimeTypes.APPLICATION_VOBSUB;
                    list = ImmutableList.of(a(this.b));
                    i7 = -1;
                    i5 = -1;
                    str = null;
                    break;
                case 30:
                    str2 = MimeTypes.APPLICATION_PGS;
                    i7 = -1;
                    i5 = -1;
                    list = null;
                    str = null;
                    break;
                case 31:
                    str2 = MimeTypes.APPLICATION_DVBSUBS;
                    byte[] bArr2 = new byte[4];
                    System.arraycopy(a(this.b), 0, bArr2, 0, 4);
                    list = ImmutableList.of(bArr2);
                    i7 = -1;
                    i5 = -1;
                    str = null;
                    break;
                default:
                    throw ParserException.createForMalformedContainer("Unrecognized codec identifier.", null);
            }
            byte[] bArr3 = this.M;
            if (!(bArr3 == null || (parse = DolbyVisionConfig.parse(new ParsableByteArray(bArr3))) == null)) {
                str = parse.codecs;
                str2 = MimeTypes.VIDEO_DOLBY_VISION;
            }
            int i12 = (this.U ? 1 : 0) | 0 | (this.T ? 2 : 0);
            Format.Builder builder = new Format.Builder();
            if (MimeTypes.isAudio(str2)) {
                builder.setChannelCount(this.N).setSampleRate(this.P).setPcmEncoding(i5);
            } else if (MimeTypes.isVideo(str2)) {
                if (this.p == 0) {
                    int i13 = this.n;
                    if (i13 == -1) {
                        i13 = this.l;
                    }
                    this.n = i13;
                    int i14 = this.o;
                    if (i14 == -1) {
                        i14 = this.m;
                    }
                    this.o = i14;
                }
                float f = -1.0f;
                int i15 = this.n;
                if (!(i15 == -1 || (i3 = this.o) == -1)) {
                    f = (this.m * i15) / (this.l * i3);
                }
                if (this.w) {
                    colorInfo = new ColorInfo(this.x, this.z, this.y, c());
                }
                i6 = (this.a == null || !MatroskaExtractor.e.containsKey(this.a)) ? -1 : ((Integer) MatroskaExtractor.e.get(this.a)).intValue();
                if (this.q == 0 && Float.compare(this.r, 0.0f) == 0 && Float.compare(this.s, 0.0f) == 0) {
                    if (Float.compare(this.t, 0.0f) != 0) {
                        if (Float.compare(this.s, 90.0f) == 0) {
                            i6 = 90;
                        } else if (Float.compare(this.s, -180.0f) == 0 || Float.compare(this.s, 180.0f) == 0) {
                            i6 = Opcodes.GETFIELD;
                        } else if (Float.compare(this.s, -90.0f) == 0) {
                            i6 = 270;
                        }
                    }
                    builder.setWidth(this.l).setHeight(this.m).setPixelWidthHeightRatio(f).setRotationDegrees(i6).setProjectionData(this.u).setStereoMode(this.v).setColorInfo(colorInfo);
                    i4 = 2;
                }
                builder.setWidth(this.l).setHeight(this.m).setPixelWidthHeightRatio(f).setRotationDegrees(i6).setProjectionData(this.u).setStereoMode(this.v).setColorInfo(colorInfo);
                i4 = 2;
            } else if (MimeTypes.APPLICATION_SUBRIP.equals(str2) || MimeTypes.TEXT_SSA.equals(str2) || MimeTypes.APPLICATION_VOBSUB.equals(str2) || MimeTypes.APPLICATION_PGS.equals(str2) || MimeTypes.APPLICATION_DVBSUBS.equals(str2)) {
                i4 = 3;
            } else {
                throw ParserException.createForMalformedContainer("Unexpected MIME type.", null);
            }
            if (this.a == null || MatroskaExtractor.e.containsKey(this.a)) {
                i2 = i;
            } else {
                builder.setLabel(this.a);
                i2 = i;
            }
            Format build = builder.setId(i2).setSampleMimeType(str2).setMaxInputSize(i7).setLanguage(this.Y).setSelectionFlags(i12).setInitializationData(list).setCodecs(str).setDrmInitData(this.k).build();
            this.V = extractorOutput.track(this.c, i4);
            this.V.format(build);
        }

        @RequiresNonNull({"output"})
        public void a() {
            c cVar = this.S;
            if (cVar != null) {
                cVar.a(this);
            }
        }

        public void b() {
            c cVar = this.S;
            if (cVar != null) {
                cVar.a();
            }
        }

        @Nullable
        private byte[] c() {
            if (this.C == -1.0f || this.D == -1.0f || this.E == -1.0f || this.F == -1.0f || this.G == -1.0f || this.H == -1.0f || this.I == -1.0f || this.J == -1.0f || this.K == -1.0f || this.L == -1.0f) {
                return null;
            }
            byte[] bArr = new byte[25];
            ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            order.put((byte) 0);
            order.putShort((short) ((this.C * 50000.0f) + 0.5f));
            order.putShort((short) ((this.D * 50000.0f) + 0.5f));
            order.putShort((short) ((this.E * 50000.0f) + 0.5f));
            order.putShort((short) ((this.F * 50000.0f) + 0.5f));
            order.putShort((short) ((this.G * 50000.0f) + 0.5f));
            order.putShort((short) ((this.H * 50000.0f) + 0.5f));
            order.putShort((short) ((this.I * 50000.0f) + 0.5f));
            order.putShort((short) ((this.J * 50000.0f) + 0.5f));
            order.putShort((short) (this.K + 0.5f));
            order.putShort((short) (this.L + 0.5f));
            order.putShort((short) this.A);
            order.putShort((short) this.B);
            return bArr;
        }

        private static Pair<String, List<byte[]>> a(ParsableByteArray parsableByteArray) throws ParserException {
            try {
                parsableByteArray.skipBytes(16);
                long readLittleEndianUnsignedInt = parsableByteArray.readLittleEndianUnsignedInt();
                if (readLittleEndianUnsignedInt == 1482049860) {
                    return new Pair<>("video/divx", null);
                }
                if (readLittleEndianUnsignedInt == 859189832) {
                    return new Pair<>("video/3gpp", null);
                }
                if (readLittleEndianUnsignedInt == 826496599) {
                    byte[] data = parsableByteArray.getData();
                    for (int position = parsableByteArray.getPosition() + 20; position < data.length - 4; position++) {
                        if (data[position] == 0 && data[position + 1] == 0 && data[position + 2] == 1 && data[position + 3] == 15) {
                            return new Pair<>(MimeTypes.VIDEO_VC1, Collections.singletonList(Arrays.copyOfRange(data, position, data.length)));
                        }
                    }
                    throw ParserException.createForMalformedContainer("Failed to find FourCC VC1 initialization data", null);
                }
                Log.w("MatroskaExtractor", "Unknown FourCC. Setting mimeType to video/x-unknown");
                return new Pair<>(MimeTypes.VIDEO_UNKNOWN, null);
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw ParserException.createForMalformedContainer("Error parsing FourCC private data", null);
            }
        }

        private static List<byte[]> a(byte[] bArr) throws ParserException {
            try {
                if (bArr[0] == 2) {
                    int i = 0;
                    int i2 = 1;
                    while ((bArr[i2] & 255) == 255) {
                        i += 255;
                        i2++;
                    }
                    int i3 = i2 + 1;
                    int i4 = i + (bArr[i2] & 255);
                    int i5 = 0;
                    while ((bArr[i3] & 255) == 255) {
                        i5 += 255;
                        i3++;
                    }
                    int i6 = i3 + 1;
                    int i7 = i5 + (bArr[i3] & 255);
                    if (bArr[i6] == 1) {
                        byte[] bArr2 = new byte[i4];
                        System.arraycopy(bArr, i6, bArr2, 0, i4);
                        int i8 = i6 + i4;
                        if (bArr[i8] == 3) {
                            int i9 = i8 + i7;
                            if (bArr[i9] == 5) {
                                byte[] bArr3 = new byte[bArr.length - i9];
                                System.arraycopy(bArr, i9, bArr3, 0, bArr.length - i9);
                                ArrayList arrayList = new ArrayList(2);
                                arrayList.add(bArr2);
                                arrayList.add(bArr3);
                                return arrayList;
                            }
                            throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
                        }
                        throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
                    }
                    throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
                }
                throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
            }
        }

        private static boolean b(ParsableByteArray parsableByteArray) throws ParserException {
            try {
                int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
                if (readLittleEndianUnsignedShort == 1) {
                    return true;
                }
                if (readLittleEndianUnsignedShort != 65534) {
                    return false;
                }
                parsableByteArray.setPosition(24);
                if (parsableByteArray.readLong() == MatroskaExtractor.d.getMostSignificantBits()) {
                    if (parsableByteArray.readLong() == MatroskaExtractor.d.getLeastSignificantBits()) {
                        return true;
                    }
                }
                return false;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw ParserException.createForMalformedContainer("Error parsing MS/ACM codec private", null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @EnsuresNonNull({"output"})
        public void d() {
            Assertions.checkNotNull(this.V);
        }

        @EnsuresNonNull({"codecPrivate"})
        private byte[] a(String str) throws ParserException {
            byte[] bArr = this.j;
            if (bArr != null) {
                return bArr;
            }
            String valueOf = String.valueOf(str);
            throw ParserException.createForMalformedContainer(valueOf.length() != 0 ? "Missing CodecPrivate for codec ".concat(valueOf) : new String("Missing CodecPrivate for codec "), null);
        }
    }
}
