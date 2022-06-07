package com.google.android.exoplayer2.text.cea;

import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import androidx.core.view.InputDeviceCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.umeng.analytics.pro.n;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;

/* loaded from: classes2.dex */
public final class Cea608Decoder extends a {
    public static final long MIN_DATA_CHANNEL_TIMEOUT_MS = 16000;
    private static final int[] a = {11, 1, 3, 12, 14, 5, 7, 9};
    private static final int[] b = {0, 4, 8, 12, 16, 20, 24, 28};
    private static final int[] c = {-1, -16711936, -16776961, -16711681, -65536, InputDeviceCompat.SOURCE_ANY, -65281};
    private static final int[] d = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 225, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 233, 93, 237, 243, 250, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 231, 247, 209, 241, 9632};
    private static final int[] e = {174, Opcodes.ARETURN, PsExtractor.PRIVATE_STREAM_1, 191, 8482, Opcodes.IF_ICMPGE, Opcodes.IF_ICMPGT, 9834, 224, 32, 232, 226, 234, 238, 244, 251};
    private static final int[] f = {Opcodes.INSTANCEOF, 201, 211, 218, 220, 252, 8216, Opcodes.IF_ICMPLT, 42, 39, n.a.B, Opcodes.RET, 8480, 8226, 8220, 8221, 192, 194, Opcodes.IFNONNULL, 200, 202, 203, 235, 206, HttpStatus.MULTI_STATUS_207, 239, 212, 217, 249, 219, 171, Opcodes.NEW};
    private static final int[] g = {195, 227, 205, 204, 236, 210, 242, 213, 245, 123, 125, 92, 94, 95, 124, 126, 196, 228, 214, 246, 223, Opcodes.IF_ACMPEQ, 164, 9474, 197, 229, 216, 248, 9484, 9488, 9492, 9496};
    private static final boolean[] h = {false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
    private final int j;
    private final int k;
    private final int l;
    private final long m;
    @Nullable
    private List<Cue> p;
    @Nullable
    private List<Cue> q;
    private int r;
    private int s;
    private boolean t;
    private boolean u;
    private byte v;
    private byte w;
    private boolean y;
    private long z;
    private final ParsableByteArray i = new ParsableByteArray();
    private final ArrayList<a> n = new ArrayList<>();
    private a o = new a(0, 4);
    private int x = 0;

    private static boolean c(byte b2, byte b3) {
        return (b2 & 247) == 17 && (b3 & 240) == 48;
    }

    private static boolean d(byte b2, byte b3) {
        return (b2 & 246) == 18 && (b3 & 224) == 32;
    }

    private static boolean f(byte b2, byte b3) {
        return (b2 & 247) == 17 && (b3 & 240) == 32;
    }

    private static boolean g(byte b2, byte b3) {
        return (b2 & 240) == 16 && (b3 & 192) == 64;
    }

    private static boolean h(byte b2) {
        return (b2 & 224) == 0;
    }

    private static boolean h(byte b2, byte b3) {
        return (b2 & 247) == 23 && b3 >= 33 && b3 <= 35;
    }

    private static int i(byte b2) {
        return (b2 >> 3) & 1;
    }

    private static boolean i(byte b2, byte b3) {
        return (b2 & 246) == 20 && (b3 & 240) == 32;
    }

    private static boolean j(byte b2) {
        return (b2 & 240) == 16;
    }

    private static boolean k(byte b2) {
        return 1 <= b2 && b2 <= 15;
    }

    private static boolean l(byte b2) {
        return (b2 & 247) == 20;
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    public String getName() {
        return "Cea608Decoder";
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    public void release() {
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    @Override // com.google.android.exoplayer2.text.cea.a
    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.text.SubtitleDecoder
    public /* bridge */ /* synthetic */ void setPositionUs(long j) {
        super.setPositionUs(j);
    }

    public Cea608Decoder(String str, int i, long j) {
        this.m = j > 0 ? j * 1000 : -9223372036854775807L;
        this.j = MimeTypes.APPLICATION_MP4CEA608.equals(str) ? 2 : 3;
        switch (i) {
            case 1:
                this.l = 0;
                this.k = 0;
                break;
            case 2:
                this.l = 1;
                this.k = 0;
                break;
            case 3:
                this.l = 0;
                this.k = 1;
                break;
            case 4:
                this.l = 1;
                this.k = 1;
                break;
            default:
                Log.w("Cea608Decoder", "Invalid channel. Defaulting to CC1.");
                this.l = 0;
                this.k = 0;
                break;
        }
        a(0);
        c();
        this.y = true;
        this.z = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    public void flush() {
        super.flush();
        this.p = null;
        this.q = null;
        a(0);
        b(4);
        c();
        this.t = false;
        this.u = false;
        this.v = (byte) 0;
        this.w = (byte) 0;
        this.x = 0;
        this.y = true;
        this.z = C.TIME_UNSET;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.text.cea.a, com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        SubtitleOutputBuffer availableOutputBuffer;
        SubtitleOutputBuffer dequeueOutputBuffer = super.dequeueOutputBuffer();
        if (dequeueOutputBuffer != null) {
            return dequeueOutputBuffer;
        }
        if (!d() || (availableOutputBuffer = getAvailableOutputBuffer()) == null) {
            return null;
        }
        this.p = Collections.emptyList();
        this.z = C.TIME_UNSET;
        availableOutputBuffer.setContent(getPositionUs(), createSubtitle(), Long.MAX_VALUE);
        return availableOutputBuffer;
    }

    @Override // com.google.android.exoplayer2.text.cea.a
    protected boolean isNewSubtitleDataAvailable() {
        return this.p != this.q;
    }

    @Override // com.google.android.exoplayer2.text.cea.a
    protected Subtitle createSubtitle() {
        List<Cue> list = this.p;
        this.q = list;
        return new b((List) Assertions.checkNotNull(list));
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x0070 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0018 A[SYNTHETIC] */
    @Override // com.google.android.exoplayer2.text.cea.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(com.google.android.exoplayer2.text.SubtitleInputBuffer r10) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea608Decoder.decode(com.google.android.exoplayer2.text.SubtitleInputBuffer):void");
    }

    private boolean a(byte b2) {
        if (h(b2)) {
            this.x = i(b2);
        }
        return this.x == this.l;
    }

    private boolean a(boolean z, byte b2, byte b3) {
        if (!z || !j(b2)) {
            this.u = false;
        } else if (this.u && this.v == b2 && this.w == b3) {
            this.u = false;
            return true;
        } else {
            this.u = true;
            this.v = b2;
            this.w = b3;
        }
        return false;
    }

    private void b(byte b2) {
        this.o.a(' ');
        this.o.a((b2 >> 1) & 7, (b2 & 1) == 1);
    }

    private void a(byte b2, byte b3) {
        int i = a[b2 & 7];
        boolean z = false;
        if ((b3 & 32) != 0) {
            i++;
        }
        if (i != this.o.d) {
            if (this.r != 1 && !this.o.a()) {
                this.o = new a(this.r, this.s);
                this.n.add(this.o);
            }
            this.o.d = i;
        }
        boolean z2 = (b3 & 16) == 16;
        if ((b3 & 1) == 1) {
            z = true;
        }
        int i2 = (b3 >> 1) & 7;
        this.o.a(z2 ? 8 : i2, z);
        if (z2) {
            this.o.e = b[i2];
        }
    }

    private void c(byte b2) {
        if (b2 == 32) {
            a(2);
        } else if (b2 != 41) {
            switch (b2) {
                case 37:
                    a(1);
                    b(2);
                    return;
                case 38:
                    a(1);
                    b(3);
                    return;
                case 39:
                    a(1);
                    b(4);
                    return;
                default:
                    int i = this.r;
                    if (i != 0) {
                        if (b2 == 33) {
                            this.o.b();
                            return;
                        } else if (b2 != 36) {
                            switch (b2) {
                                case 44:
                                    this.p = Collections.emptyList();
                                    int i2 = this.r;
                                    if (i2 == 1 || i2 == 3) {
                                        c();
                                        return;
                                    }
                                    return;
                                case 45:
                                    if (i == 1 && !this.o.a()) {
                                        this.o.c();
                                        return;
                                    }
                                    return;
                                case 46:
                                    c();
                                    return;
                                case 47:
                                    this.p = b();
                                    c();
                                    return;
                                default:
                                    return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
            }
        } else {
            a(3);
        }
    }

    private List<Cue> b() {
        int size = this.n.size();
        ArrayList arrayList = new ArrayList(size);
        int i = 2;
        for (int i2 = 0; i2 < size; i2++) {
            Cue d2 = this.n.get(i2).d(Integer.MIN_VALUE);
            arrayList.add(d2);
            if (d2 != null) {
                i = Math.min(i, d2.positionAnchor);
            }
        }
        ArrayList arrayList2 = new ArrayList(size);
        for (int i3 = 0; i3 < size; i3++) {
            Cue cue = (Cue) arrayList.get(i3);
            if (cue != null) {
                if (cue.positionAnchor != i) {
                    cue = (Cue) Assertions.checkNotNull(this.n.get(i3).d(i));
                }
                arrayList2.add(cue);
            }
        }
        return arrayList2;
    }

    private void a(int i) {
        int i2 = this.r;
        if (i2 != i) {
            this.r = i;
            if (i == 3) {
                for (int i3 = 0; i3 < this.n.size(); i3++) {
                    this.n.get(i3).b(i);
                }
                return;
            }
            c();
            if (i2 == 3 || i == 1 || i == 0) {
                this.p = Collections.emptyList();
            }
        }
    }

    private void b(int i) {
        this.s = i;
        this.o.c(i);
    }

    private void c() {
        this.o.a(this.r);
        this.n.clear();
        this.n.add(this.o);
    }

    private void b(byte b2, byte b3) {
        if (k(b2)) {
            this.y = false;
        } else if (l(b2)) {
            if (!(b3 == 32 || b3 == 47)) {
                switch (b3) {
                    case 37:
                    case 38:
                    case 39:
                        break;
                    default:
                        switch (b3) {
                            case 41:
                                break;
                            case 42:
                            case 43:
                                this.y = false;
                                return;
                            default:
                                return;
                        }
                }
            }
            this.y = true;
        }
    }

    private static char d(byte b2) {
        return (char) d[(b2 & Byte.MAX_VALUE) - 32];
    }

    private static char e(byte b2) {
        return (char) e[b2 & 15];
    }

    private static char e(byte b2, byte b3) {
        if ((b2 & 1) == 0) {
            return f(b3);
        }
        return g(b3);
    }

    private static char f(byte b2) {
        return (char) f[b2 & Ascii.US];
    }

    private static char g(byte b2) {
        return (char) g[b2 & Ascii.US];
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private final List<C0069a> a = new ArrayList();
        private final List<SpannableString> b = new ArrayList();
        private final StringBuilder c = new StringBuilder();
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;

        public a(int i, int i2) {
            a(i);
            this.h = i2;
        }

        public void a(int i) {
            this.g = i;
            this.a.clear();
            this.b.clear();
            this.c.setLength(0);
            this.d = 15;
            this.e = 0;
            this.f = 0;
        }

        public boolean a() {
            return this.a.isEmpty() && this.b.isEmpty() && this.c.length() == 0;
        }

        public void b(int i) {
            this.g = i;
        }

        public void c(int i) {
            this.h = i;
        }

        public void a(int i, boolean z) {
            this.a.add(new C0069a(i, z, this.c.length()));
        }

        public void b() {
            int length = this.c.length();
            if (length > 0) {
                this.c.delete(length - 1, length);
                for (int size = this.a.size() - 1; size >= 0; size--) {
                    C0069a aVar = this.a.get(size);
                    if (aVar.c == length) {
                        aVar.c--;
                    } else {
                        return;
                    }
                }
            }
        }

        public void a(char c) {
            if (this.c.length() < 32) {
                this.c.append(c);
            }
        }

        public void c() {
            this.b.add(d());
            this.c.setLength(0);
            this.a.clear();
            int min = Math.min(this.h, this.d);
            while (this.b.size() >= min) {
                this.b.remove(0);
            }
        }

        @Nullable
        public Cue d(int i) {
            float f;
            int i2 = this.e + this.f;
            int i3 = 32 - i2;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i4 = 0; i4 < this.b.size(); i4++) {
                spannableStringBuilder.append(Util.truncateAscii(this.b.get(i4), i3));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append(Util.truncateAscii(d(), i3));
            if (spannableStringBuilder.length() == 0) {
                return null;
            }
            int length = i3 - spannableStringBuilder.length();
            int i5 = i2 - length;
            if (i == Integer.MIN_VALUE) {
                if (this.g != 2 || (Math.abs(i5) >= 3 && length >= 0)) {
                    i = (this.g != 2 || i5 <= 0) ? 0 : 2;
                } else {
                    i = 1;
                }
            }
            switch (i) {
                case 1:
                    f = 0.5f;
                    break;
                case 2:
                    f = (((32 - length) / 32.0f) * 0.8f) + 0.1f;
                    break;
                default:
                    f = ((i2 / 32.0f) * 0.8f) + 0.1f;
                    break;
            }
            int i6 = this.d;
            if (i6 > 7) {
                i6 = (i6 - 15) - 2;
            } else if (this.g == 1) {
                i6 -= this.h - 1;
            }
            return new Cue.Builder().setText(spannableStringBuilder).setTextAlignment(Layout.Alignment.ALIGN_NORMAL).setLine(i6, 1).setPosition(f).setPositionAnchor(i).build();
        }

        private SpannableString d() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.c);
            int length = spannableStringBuilder.length();
            int i = 0;
            int i2 = 0;
            boolean z = false;
            int i3 = -1;
            int i4 = -1;
            int i5 = -1;
            int i6 = -1;
            while (i < this.a.size()) {
                C0069a aVar = this.a.get(i);
                boolean z2 = aVar.b;
                int i7 = aVar.a;
                if (i7 != 8) {
                    z = i7 == 7;
                    if (i7 != 7) {
                        i6 = Cea608Decoder.c[i7];
                    }
                }
                int i8 = aVar.c;
                i++;
                if (i8 != (i < this.a.size() ? this.a.get(i).c : length)) {
                    if (i3 != -1 && !z2) {
                        a(spannableStringBuilder, i3, i8);
                        i3 = -1;
                    } else if (i3 == -1 && z2) {
                        i3 = i8;
                    }
                    if (i4 != -1 && !z) {
                        b(spannableStringBuilder, i4, i8);
                        i4 = -1;
                    } else if (i4 == -1 && z) {
                        i4 = i8;
                    }
                    if (i6 != i5) {
                        a(spannableStringBuilder, i2, i8, i5);
                        i5 = i6;
                        i2 = i8;
                    }
                }
            }
            if (!(i3 == -1 || i3 == length)) {
                a(spannableStringBuilder, i3, length);
            }
            if (!(i4 == -1 || i4 == length)) {
                b(spannableStringBuilder, i4, length);
            }
            if (i2 != length) {
                a(spannableStringBuilder, i2, length, i5);
            }
            return new SpannableString(spannableStringBuilder);
        }

        private static void a(SpannableStringBuilder spannableStringBuilder, int i, int i2) {
            spannableStringBuilder.setSpan(new UnderlineSpan(), i, i2, 33);
        }

        private static void b(SpannableStringBuilder spannableStringBuilder, int i, int i2) {
            spannableStringBuilder.setSpan(new StyleSpan(2), i, i2, 33);
        }

        private static void a(SpannableStringBuilder spannableStringBuilder, int i, int i2, int i3) {
            if (i3 != -1) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(i3), i, i2, 33);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: com.google.android.exoplayer2.text.cea.Cea608Decoder$a$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        public static class C0069a {
            public final int a;
            public final boolean b;
            public int c;

            public C0069a(int i, boolean z, int i2) {
                this.a = i;
                this.b = z;
                this.c = i2;
            }
        }
    }

    private boolean d() {
        return (this.m == C.TIME_UNSET || this.z == C.TIME_UNSET || getPositionUs() - this.z < this.m) ? false : true;
    }
}
