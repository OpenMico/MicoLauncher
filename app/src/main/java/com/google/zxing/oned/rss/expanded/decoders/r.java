package com.google.zxing.oned.rss.expanded.decoders;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import io.netty.util.internal.StringUtil;
import kotlin.text.Typography;

/* compiled from: GeneralAppIdDecoder.java */
/* loaded from: classes2.dex */
public final class r {
    private final BitArray a;
    private final l b = new l();
    private final StringBuilder c = new StringBuilder();

    public r(BitArray bitArray) {
        this.a = bitArray;
    }

    public String a(StringBuilder sb, int i) throws NotFoundException, FormatException {
        String str = null;
        while (true) {
            n a = a(i, str);
            String a2 = q.a(a.a());
            if (a2 != null) {
                sb.append(a2);
            }
            if (a.b()) {
                str = String.valueOf(a.c());
            } else {
                str = null;
            }
            if (i == a.e()) {
                return sb.toString();
            }
            i = a.e();
        }
    }

    private boolean a(int i) {
        if (i + 7 > this.a.getSize()) {
            return i + 4 <= this.a.getSize();
        }
        int i2 = i;
        while (true) {
            int i3 = i + 3;
            if (i2 >= i3) {
                return this.a.get(i3);
            }
            if (this.a.get(i2)) {
                return true;
            }
            i2++;
        }
    }

    private o b(int i) throws FormatException {
        int i2 = i + 7;
        if (i2 > this.a.getSize()) {
            int a = a(i, 4);
            if (a == 0) {
                return new o(this.a.getSize(), 10, 10);
            }
            return new o(this.a.getSize(), a - 1, 10);
        }
        int a2 = a(i, 7) - 8;
        return new o(i2, a2 / 11, a2 % 11);
    }

    public int a(int i, int i2) {
        return a(this.a, i, i2);
    }

    public static int a(BitArray bitArray, int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (bitArray.get(i + i4)) {
                i3 |= 1 << ((i2 - i4) - 1);
            }
        }
        return i3;
    }

    public n a(int i, String str) throws FormatException {
        this.c.setLength(0);
        if (str != null) {
            this.c.append(str);
        }
        this.b.a(i);
        n a = a();
        if (a == null || !a.b()) {
            return new n(this.b.a(), this.c.toString());
        }
        return new n(this.b.a(), this.c.toString(), a.c());
    }

    private n a() throws FormatException {
        boolean z;
        k kVar;
        do {
            int a = this.b.a();
            if (this.b.b()) {
                kVar = d();
                z = kVar.b();
            } else if (this.b.c()) {
                kVar = c();
                z = kVar.b();
            } else {
                kVar = b();
                z = kVar.b();
            }
            if (!(a != this.b.a()) && !z) {
                break;
            }
        } while (!z);
        return kVar.a();
    }

    private k b() throws FormatException {
        n nVar;
        while (a(this.b.a())) {
            o b = b(this.b.a());
            this.b.a(b.e());
            if (b.c()) {
                if (b.d()) {
                    nVar = new n(this.b.a(), this.c.toString());
                } else {
                    nVar = new n(this.b.a(), this.c.toString(), b.b());
                }
                return new k(nVar, true);
            }
            this.c.append(b.a());
            if (b.d()) {
                return new k(new n(this.b.a(), this.c.toString()), true);
            }
            this.c.append(b.b());
        }
        if (i(this.b.a())) {
            this.b.e();
            this.b.b(4);
        }
        return new k(false);
    }

    private k c() throws FormatException {
        while (c(this.b.a())) {
            m d = d(this.b.a());
            this.b.a(d.e());
            if (d.b()) {
                return new k(new n(this.b.a(), this.c.toString()), true);
            }
            this.c.append(d.a());
        }
        if (h(this.b.a())) {
            this.b.b(3);
            this.b.d();
        } else if (g(this.b.a())) {
            if (this.b.a() + 5 < this.a.getSize()) {
                this.b.b(5);
            } else {
                this.b.a(this.a.getSize());
            }
            this.b.e();
        }
        return new k(false);
    }

    private k d() {
        while (e(this.b.a())) {
            m f = f(this.b.a());
            this.b.a(f.e());
            if (f.b()) {
                return new k(new n(this.b.a(), this.c.toString()), true);
            }
            this.c.append(f.a());
        }
        if (h(this.b.a())) {
            this.b.b(3);
            this.b.d();
        } else if (g(this.b.a())) {
            if (this.b.a() + 5 < this.a.getSize()) {
                this.b.b(5);
            } else {
                this.b.a(this.a.getSize());
            }
            this.b.f();
        }
        return new k(false);
    }

    private boolean c(int i) {
        int a;
        if (i + 5 > this.a.getSize()) {
            return false;
        }
        int a2 = a(i, 5);
        if (a2 >= 5 && a2 < 16) {
            return true;
        }
        if (i + 7 > this.a.getSize()) {
            return false;
        }
        int a3 = a(i, 7);
        if (a3 < 64 || a3 >= 116) {
            return i + 8 <= this.a.getSize() && (a = a(i, 8)) >= 232 && a < 253;
        }
        return true;
    }

    private m d(int i) throws FormatException {
        char c;
        int a = a(i, 5);
        if (a == 15) {
            return new m(i + 5, '$');
        }
        if (a >= 5 && a < 15) {
            return new m(i + 5, (char) ((a + 48) - 5));
        }
        int a2 = a(i, 7);
        if (a2 >= 64 && a2 < 90) {
            return new m(i + 7, (char) (a2 + 1));
        }
        if (a2 >= 90 && a2 < 116) {
            return new m(i + 7, (char) (a2 + 7));
        }
        switch (a(i, 8)) {
            case 232:
                c = '!';
                break;
            case 233:
                c = '\"';
                break;
            case 234:
                c = '%';
                break;
            case 235:
                c = Typography.amp;
                break;
            case 236:
                c = '\'';
                break;
            case 237:
                c = '(';
                break;
            case 238:
                c = ')';
                break;
            case 239:
                c = '*';
                break;
            case PsExtractor.VIDEO_STREAM_MASK /* 240 */:
                c = '+';
                break;
            case 241:
                c = StringUtil.COMMA;
                break;
            case 242:
                c = '-';
                break;
            case 243:
                c = '.';
                break;
            case 244:
                c = JsonPointer.SEPARATOR;
                break;
            case 245:
                c = ':';
                break;
            case 246:
                c = ';';
                break;
            case 247:
                c = Typography.less;
                break;
            case 248:
                c = '=';
                break;
            case 249:
                c = Typography.greater;
                break;
            case 250:
                c = '?';
                break;
            case 251:
                c = '_';
                break;
            case 252:
                c = ' ';
                break;
            default:
                throw FormatException.getFormatInstance();
        }
        return new m(i + 8, c);
    }

    private boolean e(int i) {
        int a;
        if (i + 5 > this.a.getSize()) {
            return false;
        }
        int a2 = a(i, 5);
        if (a2 < 5 || a2 >= 16) {
            return i + 6 <= this.a.getSize() && (a = a(i, 6)) >= 16 && a < 63;
        }
        return true;
    }

    private m f(int i) {
        char c;
        int a = a(i, 5);
        if (a == 15) {
            return new m(i + 5, '$');
        }
        if (a >= 5 && a < 15) {
            return new m(i + 5, (char) ((a + 48) - 5));
        }
        int a2 = a(i, 6);
        if (a2 >= 32 && a2 < 58) {
            return new m(i + 6, (char) (a2 + 33));
        }
        switch (a2) {
            case 58:
                c = '*';
                break;
            case 59:
                c = StringUtil.COMMA;
                break;
            case 60:
                c = '-';
                break;
            case 61:
                c = '.';
                break;
            case 62:
                c = JsonPointer.SEPARATOR;
                break;
            default:
                throw new IllegalStateException("Decoding invalid alphanumeric value: " + a2);
        }
        return new m(i + 6, c);
    }

    private boolean g(int i) {
        int i2;
        if (i + 1 > this.a.getSize()) {
            return false;
        }
        for (int i3 = 0; i3 < 5 && (i2 = i3 + i) < this.a.getSize(); i3++) {
            if (i3 == 2) {
                if (!this.a.get(i + 2)) {
                    return false;
                }
            } else if (this.a.get(i2)) {
                return false;
            }
        }
        return true;
    }

    private boolean h(int i) {
        int i2 = i + 3;
        if (i2 > this.a.getSize()) {
            return false;
        }
        while (i < i2) {
            if (this.a.get(i)) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean i(int i) {
        int i2;
        if (i + 1 > this.a.getSize()) {
            return false;
        }
        for (int i3 = 0; i3 < 4 && (i2 = i3 + i) < this.a.getSize(); i3++) {
            if (this.a.get(i2)) {
                return false;
            }
        }
        return true;
    }
}
