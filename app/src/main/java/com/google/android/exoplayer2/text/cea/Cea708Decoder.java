package com.google.android.exoplayer2.text.cea;

import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import androidx.renderscript.ScriptIntrinsicBLAS;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.xiaomi.mi_connect_sdk.BuildConfig;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.text.Typography;
import okio.Utf8;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class Cea708Decoder extends a {
    private final ParsableByteArray a = new ParsableByteArray();
    private final ParsableBitArray b = new ParsableBitArray();
    private int c = -1;
    private final boolean d;
    private final int e;
    private final b[] f;
    private b g;
    @Nullable
    private List<Cue> h;
    @Nullable
    private List<Cue> i;
    @Nullable
    private c j;
    private int k;

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    public String getName() {
        return "Cea708Decoder";
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public /* bridge */ /* synthetic */ SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        return super.dequeueOutputBuffer();
    }

    @Override // com.google.android.exoplayer2.text.cea.a
    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.text.SubtitleDecoder
    public /* bridge */ /* synthetic */ void setPositionUs(long j) {
        super.setPositionUs(j);
    }

    public Cea708Decoder(int i, @Nullable List<byte[]> list) {
        boolean z = true;
        this.e = i == -1 ? 1 : i;
        this.d = (list == null || !CodecSpecificDataUtil.parseCea708InitializationData(list)) ? false : z;
        this.f = new b[8];
        for (int i2 = 0; i2 < 8; i2++) {
            this.f[i2] = new b();
        }
        this.g = this.f[0];
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    public void flush() {
        super.flush();
        this.h = null;
        this.i = null;
        this.k = 0;
        this.g = this.f[this.k];
        h();
        this.j = null;
    }

    @Override // com.google.android.exoplayer2.text.cea.a
    protected boolean isNewSubtitleDataAvailable() {
        return this.h != this.i;
    }

    @Override // com.google.android.exoplayer2.text.cea.a
    protected Subtitle createSubtitle() {
        List<Cue> list = this.h;
        this.i = list;
        return new b((List) Assertions.checkNotNull(list));
    }

    @Override // com.google.android.exoplayer2.text.cea.a
    protected void decode(SubtitleInputBuffer subtitleInputBuffer) {
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(subtitleInputBuffer.data);
        this.a.reset(byteBuffer.array(), byteBuffer.limit());
        while (this.a.bytesLeft() >= 3) {
            int readUnsignedByte = this.a.readUnsignedByte() & 7;
            int i = readUnsignedByte & 3;
            boolean z = false;
            boolean z2 = (readUnsignedByte & 4) == 4;
            byte readUnsignedByte2 = (byte) this.a.readUnsignedByte();
            byte readUnsignedByte3 = (byte) this.a.readUnsignedByte();
            if (i == 2 || i == 3) {
                if (z2) {
                    if (i == 3) {
                        a();
                        int i2 = (readUnsignedByte2 & 192) >> 6;
                        int i3 = this.c;
                        if (!(i3 == -1 || i2 == (i3 + 1) % 4)) {
                            h();
                            int i4 = this.c;
                            StringBuilder sb = new StringBuilder(71);
                            sb.append("Sequence number discontinuity. previous=");
                            sb.append(i4);
                            sb.append(" current=");
                            sb.append(i2);
                            Log.w("Cea708Decoder", sb.toString());
                        }
                        this.c = i2;
                        int i5 = readUnsignedByte2 & Utf8.REPLACEMENT_BYTE;
                        if (i5 == 0) {
                            i5 = 64;
                        }
                        this.j = new c(i2, i5);
                        byte[] bArr = this.j.c;
                        c cVar = this.j;
                        int i6 = cVar.d;
                        cVar.d = i6 + 1;
                        bArr[i6] = readUnsignedByte3;
                    } else {
                        if (i == 2) {
                            z = true;
                        }
                        Assertions.checkArgument(z);
                        c cVar2 = this.j;
                        if (cVar2 == null) {
                            Log.e("Cea708Decoder", "Encountered DTVCC_PACKET_DATA before DTVCC_PACKET_START");
                        } else {
                            byte[] bArr2 = cVar2.c;
                            c cVar3 = this.j;
                            int i7 = cVar3.d;
                            cVar3.d = i7 + 1;
                            bArr2[i7] = readUnsignedByte2;
                            byte[] bArr3 = this.j.c;
                            c cVar4 = this.j;
                            int i8 = cVar4.d;
                            cVar4.d = i8 + 1;
                            bArr3[i8] = readUnsignedByte3;
                        }
                    }
                    if (this.j.d == (this.j.b * 2) - 1) {
                        a();
                    }
                }
            }
        }
    }

    private void a() {
        if (this.j != null) {
            b();
            this.j = null;
        }
    }

    @RequiresNonNull({"currentDtvCcPacket"})
    private void b() {
        if (this.j.d != (this.j.b * 2) - 1) {
            int i = this.j.d;
            int i2 = this.j.a;
            StringBuilder sb = new StringBuilder(115);
            sb.append("DtvCcPacket ended prematurely; size is ");
            sb.append((this.j.b * 2) - 1);
            sb.append(", but current index is ");
            sb.append(i);
            sb.append(" (sequence number ");
            sb.append(i2);
            sb.append(");");
            Log.d("Cea708Decoder", sb.toString());
        }
        this.b.reset(this.j.c, this.j.d);
        int readBits = this.b.readBits(3);
        int readBits2 = this.b.readBits(5);
        if (readBits == 7) {
            this.b.skipBits(2);
            readBits = this.b.readBits(6);
            if (readBits < 7) {
                StringBuilder sb2 = new StringBuilder(44);
                sb2.append("Invalid extended service number: ");
                sb2.append(readBits);
                Log.w("Cea708Decoder", sb2.toString());
            }
        }
        if (readBits2 == 0) {
            if (readBits != 0) {
                StringBuilder sb3 = new StringBuilder(59);
                sb3.append("serviceNumber is non-zero (");
                sb3.append(readBits);
                sb3.append(") when blockSize is 0");
                Log.w("Cea708Decoder", sb3.toString());
            }
        } else if (readBits == this.e) {
            boolean z = false;
            while (this.b.bitsLeft() > 0) {
                int readBits3 = this.b.readBits(8);
                if (readBits3 == 16) {
                    int readBits4 = this.b.readBits(8);
                    if (readBits4 <= 31) {
                        c(readBits4);
                    } else if (readBits4 <= 127) {
                        g(readBits4);
                        z = true;
                    } else if (readBits4 <= 159) {
                        d(readBits4);
                    } else if (readBits4 <= 255) {
                        h(readBits4);
                        z = true;
                    } else {
                        StringBuilder sb4 = new StringBuilder(37);
                        sb4.append("Invalid extended command: ");
                        sb4.append(readBits4);
                        Log.w("Cea708Decoder", sb4.toString());
                    }
                } else if (readBits3 <= 31) {
                    a(readBits3);
                } else if (readBits3 <= 127) {
                    e(readBits3);
                    z = true;
                } else if (readBits3 <= 159) {
                    b(readBits3);
                    z = true;
                } else if (readBits3 <= 255) {
                    f(readBits3);
                    z = true;
                } else {
                    StringBuilder sb5 = new StringBuilder(33);
                    sb5.append("Invalid base command: ");
                    sb5.append(readBits3);
                    Log.w("Cea708Decoder", sb5.toString());
                }
            }
            if (z) {
                this.h = g();
            }
        }
    }

    private void a(int i) {
        if (i == 0) {
            return;
        }
        if (i == 3) {
            this.h = g();
        } else if (i != 8) {
            switch (i) {
                case 12:
                    h();
                    return;
                case 13:
                    this.g.a('\n');
                    return;
                case 14:
                    return;
                default:
                    if (i >= 17 && i <= 23) {
                        StringBuilder sb = new StringBuilder(55);
                        sb.append("Currently unsupported COMMAND_EXT1 Command: ");
                        sb.append(i);
                        Log.w("Cea708Decoder", sb.toString());
                        this.b.skipBits(8);
                        return;
                    } else if (i < 24 || i > 31) {
                        StringBuilder sb2 = new StringBuilder(31);
                        sb2.append("Invalid C0 command: ");
                        sb2.append(i);
                        Log.w("Cea708Decoder", sb2.toString());
                        return;
                    } else {
                        StringBuilder sb3 = new StringBuilder(54);
                        sb3.append("Currently unsupported COMMAND_P16 Command: ");
                        sb3.append(i);
                        Log.w("Cea708Decoder", sb3.toString());
                        this.b.skipBits(16);
                        return;
                    }
            }
        } else {
            this.g.f();
        }
    }

    private void b(int i) {
        int i2 = 1;
        switch (i) {
            case 128:
            case 129:
            case 130:
            case ScriptIntrinsicBLAS.NON_UNIT /* 131 */:
            case 132:
            case 133:
            case 134:
            case 135:
                int i3 = i - 128;
                if (this.k != i3) {
                    this.k = i3;
                    this.g = this.f[i3];
                    return;
                }
                return;
            case 136:
                while (i2 <= 8) {
                    if (this.b.readBit()) {
                        this.f[8 - i2].c();
                    }
                    i2++;
                }
                return;
            case 137:
                for (int i4 = 1; i4 <= 8; i4++) {
                    if (this.b.readBit()) {
                        this.f[8 - i4].a(true);
                    }
                }
                return;
            case TsExtractor.TS_STREAM_TYPE_DTS /* 138 */:
                while (i2 <= 8) {
                    if (this.b.readBit()) {
                        this.f[8 - i2].a(false);
                    }
                    i2++;
                }
                return;
            case 139:
                for (int i5 = 1; i5 <= 8; i5++) {
                    if (this.b.readBit()) {
                        b bVar = this.f[8 - i5];
                        bVar.a(!bVar.e());
                    }
                }
                return;
            case 140:
                while (i2 <= 8) {
                    if (this.b.readBit()) {
                        this.f[8 - i2].b();
                    }
                    i2++;
                }
                return;
            case ScriptIntrinsicBLAS.LEFT /* 141 */:
                this.b.skipBits(8);
                return;
            case ScriptIntrinsicBLAS.RIGHT /* 142 */:
                return;
            case 143:
                h();
                return;
            case 144:
                if (!this.g.d()) {
                    this.b.skipBits(16);
                    return;
                } else {
                    c();
                    return;
                }
            case 145:
                if (!this.g.d()) {
                    this.b.skipBits(24);
                    return;
                } else {
                    d();
                    return;
                }
            case 146:
                if (!this.g.d()) {
                    this.b.skipBits(16);
                    return;
                } else {
                    e();
                    return;
                }
            default:
                switch (i) {
                    case Opcodes.DCMPL /* 151 */:
                        if (!this.g.d()) {
                            this.b.skipBits(32);
                            return;
                        } else {
                            f();
                            return;
                        }
                    case 152:
                    case Opcodes.IFEQ /* 153 */:
                    case Opcodes.IFNE /* 154 */:
                    case 155:
                    case BuildConfig.MiConnectVersionBuild /* 156 */:
                    case 157:
                    case Opcodes.IFLE /* 158 */:
                    case Opcodes.IF_ICMPEQ /* 159 */:
                        int i6 = i - 152;
                        i(i6);
                        if (this.k != i6) {
                            this.k = i6;
                            this.g = this.f[i6];
                            return;
                        }
                        return;
                    default:
                        StringBuilder sb = new StringBuilder(31);
                        sb.append("Invalid C1 command: ");
                        sb.append(i);
                        Log.w("Cea708Decoder", sb.toString());
                        return;
                }
        }
    }

    private void c(int i) {
        if (i > 7) {
            if (i <= 15) {
                this.b.skipBits(8);
            } else if (i <= 23) {
                this.b.skipBits(16);
            } else if (i <= 31) {
                this.b.skipBits(24);
            }
        }
    }

    private void d(int i) {
        if (i <= 135) {
            this.b.skipBits(32);
        } else if (i <= 143) {
            this.b.skipBits(40);
        } else if (i <= 159) {
            this.b.skipBits(2);
            this.b.skipBits(this.b.readBits(6) * 8);
        }
    }

    private void e(int i) {
        if (i == 127) {
            this.g.a((char) 9835);
        } else {
            this.g.a((char) (i & 255));
        }
    }

    private void f(int i) {
        this.g.a((char) (i & 255));
    }

    private void g(int i) {
        if (i == 37) {
            this.g.a(Typography.ellipsis);
        } else if (i == 42) {
            this.g.a((char) 352);
        } else if (i == 44) {
            this.g.a((char) 338);
        } else if (i != 63) {
            switch (i) {
                case 32:
                    this.g.a(' ');
                    return;
                case 33:
                    this.g.a(Typography.nbsp);
                    return;
                default:
                    switch (i) {
                        case 48:
                            this.g.a((char) 9608);
                            return;
                        case 49:
                            this.g.a(Typography.leftSingleQuote);
                            return;
                        case 50:
                            this.g.a(Typography.rightSingleQuote);
                            return;
                        case 51:
                            this.g.a(Typography.leftDoubleQuote);
                            return;
                        case 52:
                            this.g.a(Typography.rightDoubleQuote);
                            return;
                        case 53:
                            this.g.a(Typography.bullet);
                            return;
                        default:
                            switch (i) {
                                case 57:
                                    this.g.a(Typography.tm);
                                    return;
                                case 58:
                                    this.g.a((char) 353);
                                    return;
                                default:
                                    switch (i) {
                                        case 60:
                                            this.g.a((char) 339);
                                            return;
                                        case 61:
                                            this.g.a((char) 8480);
                                            return;
                                        default:
                                            switch (i) {
                                                case 118:
                                                    this.g.a((char) 8539);
                                                    return;
                                                case 119:
                                                    this.g.a((char) 8540);
                                                    return;
                                                case 120:
                                                    this.g.a((char) 8541);
                                                    return;
                                                case 121:
                                                    this.g.a((char) 8542);
                                                    return;
                                                case 122:
                                                    this.g.a((char) 9474);
                                                    return;
                                                case 123:
                                                    this.g.a((char) 9488);
                                                    return;
                                                case 124:
                                                    this.g.a((char) 9492);
                                                    return;
                                                case 125:
                                                    this.g.a((char) 9472);
                                                    return;
                                                case 126:
                                                    this.g.a((char) 9496);
                                                    return;
                                                case 127:
                                                    this.g.a((char) 9484);
                                                    return;
                                                default:
                                                    StringBuilder sb = new StringBuilder(33);
                                                    sb.append("Invalid G2 character: ");
                                                    sb.append(i);
                                                    Log.w("Cea708Decoder", sb.toString());
                                                    return;
                                            }
                                    }
                            }
                    }
            }
        } else {
            this.g.a((char) 376);
        }
    }

    private void h(int i) {
        if (i == 160) {
            this.g.a((char) 13252);
            return;
        }
        StringBuilder sb = new StringBuilder(33);
        sb.append("Invalid G3 character: ");
        sb.append(i);
        Log.w("Cea708Decoder", sb.toString());
        this.g.a('_');
    }

    private void c() {
        this.g.a(this.b.readBits(4), this.b.readBits(2), this.b.readBits(2), this.b.readBit(), this.b.readBit(), this.b.readBits(3), this.b.readBits(3));
    }

    private void d() {
        int a2 = b.a(this.b.readBits(2), this.b.readBits(2), this.b.readBits(2), this.b.readBits(2));
        int a3 = b.a(this.b.readBits(2), this.b.readBits(2), this.b.readBits(2), this.b.readBits(2));
        this.b.skipBits(2);
        this.g.a(a2, a3, b.b(this.b.readBits(2), this.b.readBits(2), this.b.readBits(2)));
    }

    private void e() {
        this.b.skipBits(4);
        int readBits = this.b.readBits(4);
        this.b.skipBits(2);
        this.g.a(readBits, this.b.readBits(6));
    }

    private void f() {
        int a2 = b.a(this.b.readBits(2), this.b.readBits(2), this.b.readBits(2), this.b.readBits(2));
        int readBits = this.b.readBits(2);
        int b2 = b.b(this.b.readBits(2), this.b.readBits(2), this.b.readBits(2));
        int i = this.b.readBit() ? readBits | 4 : readBits;
        boolean readBit = this.b.readBit();
        int readBits2 = this.b.readBits(2);
        int readBits3 = this.b.readBits(2);
        int readBits4 = this.b.readBits(2);
        this.b.skipBits(8);
        this.g.a(a2, b2, readBit, i, readBits2, readBits3, readBits4);
    }

    private void i(int i) {
        b bVar = this.f[i];
        this.b.skipBits(2);
        boolean readBit = this.b.readBit();
        boolean readBit2 = this.b.readBit();
        boolean readBit3 = this.b.readBit();
        int readBits = this.b.readBits(3);
        boolean readBit4 = this.b.readBit();
        int readBits2 = this.b.readBits(7);
        int readBits3 = this.b.readBits(8);
        int readBits4 = this.b.readBits(4);
        int readBits5 = this.b.readBits(4);
        this.b.skipBits(2);
        int readBits6 = this.b.readBits(6);
        this.b.skipBits(2);
        bVar.a(readBit, readBit2, readBit3, readBits, readBit4, readBits2, readBits3, readBits5, readBits6, readBits4, this.b.readBits(3), this.b.readBits(3));
    }

    private List<Cue> g() {
        a h;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 8; i++) {
            if (!this.f[i].a() && this.f[i].e() && (h = this.f[i].h()) != null) {
                arrayList.add(h);
            }
        }
        Collections.sort(arrayList, a.c);
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(((a) arrayList.get(i2)).a);
        }
        return Collections.unmodifiableList(arrayList2);
    }

    private void h() {
        for (int i = 0; i < 8; i++) {
            this.f[i].b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c {
        public final int a;
        public final int b;
        public final byte[] c;
        int d = 0;

        public c(int i, int i2) {
            this.a = i;
            this.b = i2;
            this.c = new byte[(i2 * 2) - 1];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b {
        private static final int[] h;
        private static final int[] k;
        private int A;
        private int B;
        private int C;
        private int D;
        private int E;
        private int F;
        private int G;
        private final List<SpannableString> l = new ArrayList();
        private final SpannableStringBuilder m = new SpannableStringBuilder();
        private boolean n;
        private boolean o;
        private int p;
        private boolean q;
        private int r;
        private int s;
        private int t;
        private int u;
        private boolean v;
        private int w;
        private int x;
        private int y;
        private int z;
        public static final int a = a(2, 2, 2, 0);
        public static final int b = a(0, 0, 0, 0);
        public static final int c = a(0, 0, 0, 3);
        private static final int[] d = {0, 0, 0, 0, 0, 2, 0};
        private static final int[] e = {0, 0, 0, 0, 0, 0, 2};
        private static final int[] f = {3, 3, 3, 3, 3, 3, 1};
        private static final boolean[] g = {false, false, false, true, true, true, false};
        private static final int[] i = {0, 1, 2, 3, 4, 3, 4};
        private static final int[] j = {0, 0, 0, 0, 0, 3, 3};

        static {
            int i2 = b;
            int i3 = c;
            h = new int[]{i2, i3, i2, i2, i3, i2, i2};
            k = new int[]{i2, i2, i2, i2, i2, i3, i3};
        }

        public b() {
            b();
        }

        public boolean a() {
            return !d() || (this.l.isEmpty() && this.m.length() == 0);
        }

        public void b() {
            c();
            this.n = false;
            this.o = false;
            this.p = 4;
            this.q = false;
            this.r = 0;
            this.s = 0;
            this.t = 0;
            this.u = 15;
            this.v = true;
            this.w = 0;
            this.x = 0;
            this.y = 0;
            int i2 = b;
            this.z = i2;
            this.D = a;
            this.F = i2;
        }

        public void c() {
            this.l.clear();
            this.m.clear();
            this.A = -1;
            this.B = -1;
            this.C = -1;
            this.E = -1;
            this.G = 0;
        }

        public boolean d() {
            return this.n;
        }

        public void a(boolean z) {
            this.o = z;
        }

        public boolean e() {
            return this.o;
        }

        public void a(boolean z, boolean z2, boolean z3, int i2, boolean z4, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.n = true;
            this.o = z;
            this.v = z2;
            this.p = i2;
            this.q = z4;
            this.r = i3;
            this.s = i4;
            this.t = i7;
            int i10 = i5 + 1;
            if (this.u != i10) {
                this.u = i10;
                while (true) {
                    if ((!z2 || this.l.size() < this.u) && this.l.size() < 15) {
                        break;
                    }
                    this.l.remove(0);
                }
            }
            if (!(i8 == 0 || this.x == i8)) {
                this.x = i8;
                int i11 = i8 - 1;
                a(h[i11], c, g[i11], 0, e[i11], f[i11], d[i11]);
            }
            if (i9 != 0 && this.y != i9) {
                this.y = i9;
                int i12 = i9 - 1;
                a(0, 1, 1, false, false, j[i12], i[i12]);
                a(a, k[i12], b);
            }
        }

        public void a(int i2, int i3, boolean z, int i4, int i5, int i6, int i7) {
            this.z = i2;
            this.w = i7;
        }

        public void a(int i2, int i3, int i4, boolean z, boolean z2, int i5, int i6) {
            if (this.A != -1) {
                if (!z) {
                    this.m.setSpan(new StyleSpan(2), this.A, this.m.length(), 33);
                    this.A = -1;
                }
            } else if (z) {
                this.A = this.m.length();
            }
            if (this.B != -1) {
                if (!z2) {
                    this.m.setSpan(new UnderlineSpan(), this.B, this.m.length(), 33);
                    this.B = -1;
                }
            } else if (z2) {
                this.B = this.m.length();
            }
        }

        public void a(int i2, int i3, int i4) {
            int i5;
            int i6;
            if (!(this.C == -1 || (i6 = this.D) == i2)) {
                this.m.setSpan(new ForegroundColorSpan(i6), this.C, this.m.length(), 33);
            }
            if (i2 != a) {
                this.C = this.m.length();
                this.D = i2;
            }
            if (!(this.E == -1 || (i5 = this.F) == i3)) {
                this.m.setSpan(new BackgroundColorSpan(i5), this.E, this.m.length(), 33);
            }
            if (i3 != b) {
                this.E = this.m.length();
                this.F = i3;
            }
        }

        public void a(int i2, int i3) {
            if (this.G != i2) {
                a('\n');
            }
            this.G = i2;
        }

        public void f() {
            int length = this.m.length();
            if (length > 0) {
                this.m.delete(length - 1, length);
            }
        }

        public void a(char c2) {
            if (c2 == '\n') {
                this.l.add(g());
                this.m.clear();
                if (this.A != -1) {
                    this.A = 0;
                }
                if (this.B != -1) {
                    this.B = 0;
                }
                if (this.C != -1) {
                    this.C = 0;
                }
                if (this.E != -1) {
                    this.E = 0;
                }
                while (true) {
                    if ((this.v && this.l.size() >= this.u) || this.l.size() >= 15) {
                        this.l.remove(0);
                    } else {
                        return;
                    }
                }
            } else {
                this.m.append(c2);
            }
        }

        public SpannableString g() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.m);
            int length = spannableStringBuilder.length();
            if (length > 0) {
                if (this.A != -1) {
                    spannableStringBuilder.setSpan(new StyleSpan(2), this.A, length, 33);
                }
                if (this.B != -1) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), this.B, length, 33);
                }
                if (this.C != -1) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(this.D), this.C, length, 33);
                }
                if (this.E != -1) {
                    spannableStringBuilder.setSpan(new BackgroundColorSpan(this.F), this.E, length, 33);
                }
            }
            return new SpannableString(spannableStringBuilder);
        }

        @Nullable
        public a h() {
            Layout.Alignment alignment;
            float f2;
            float f3;
            int i2;
            int i3;
            if (a()) {
                return null;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            boolean z = false;
            for (int i4 = 0; i4 < this.l.size(); i4++) {
                spannableStringBuilder.append((CharSequence) this.l.get(i4));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append((CharSequence) g());
            int i5 = this.w;
            switch (i5) {
                case 0:
                case 3:
                    alignment = Layout.Alignment.ALIGN_NORMAL;
                    break;
                case 1:
                    alignment = Layout.Alignment.ALIGN_OPPOSITE;
                    break;
                case 2:
                    alignment = Layout.Alignment.ALIGN_CENTER;
                    break;
                default:
                    StringBuilder sb = new StringBuilder(43);
                    sb.append("Unexpected justification value: ");
                    sb.append(i5);
                    throw new IllegalArgumentException(sb.toString());
            }
            if (this.q) {
                f3 = this.s / 99.0f;
                f2 = this.r / 99.0f;
            } else {
                f3 = this.s / 209.0f;
                f2 = this.r / 74.0f;
            }
            float f4 = (f3 * 0.9f) + 0.05f;
            float f5 = (f2 * 0.9f) + 0.05f;
            int i6 = this.t;
            if (i6 / 3 == 0) {
                i2 = 0;
            } else {
                i2 = i6 / 3 == 1 ? 1 : 2;
            }
            int i7 = this.t;
            if (i7 % 3 == 0) {
                i3 = 0;
            } else {
                i3 = i7 % 3 == 1 ? 1 : 2;
            }
            if (this.z != b) {
                z = true;
            }
            return new a(spannableStringBuilder, alignment, f5, 0, i2, f4, i3, -3.4028235E38f, z, this.z, this.p);
        }

        public static int b(int i2, int i3, int i4) {
            return a(i2, i3, i4, 0);
        }

        public static int a(int i2, int i3, int i4, int i5) {
            int i6;
            Assertions.checkIndex(i2, 0, 4);
            Assertions.checkIndex(i3, 0, 4);
            Assertions.checkIndex(i4, 0, 4);
            Assertions.checkIndex(i5, 0, 4);
            int i7 = 255;
            switch (i5) {
                case 0:
                case 1:
                    i6 = 255;
                    break;
                case 2:
                    i6 = 127;
                    break;
                case 3:
                    i6 = 0;
                    break;
                default:
                    i6 = 255;
                    break;
            }
            int i8 = i2 > 1 ? 255 : 0;
            int i9 = i3 > 1 ? 255 : 0;
            if (i4 <= 1) {
                i7 = 0;
            }
            return Color.argb(i6, i8, i9, i7);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private static final Comparator<a> c = $$Lambda$Cea708Decoder$a$bTWTJAa2oRZrpP1GK7T6b5_Aais.INSTANCE;
        public final Cue a;
        public final int b;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int a(a aVar, a aVar2) {
            return Integer.compare(aVar2.b, aVar.b);
        }

        public a(CharSequence charSequence, Layout.Alignment alignment, float f, int i, int i2, float f2, int i3, float f3, boolean z, int i4, int i5) {
            Cue.Builder size = new Cue.Builder().setText(charSequence).setTextAlignment(alignment).setLine(f, i).setLineAnchor(i2).setPosition(f2).setPositionAnchor(i3).setSize(f3);
            if (z) {
                size.setWindowColor(i4);
            }
            this.a = size.build();
            this.b = i5;
        }
    }
}
