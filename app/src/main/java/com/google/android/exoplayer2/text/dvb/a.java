package com.google.android.exoplayer2.text.dvb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: DvbParser.java */
/* loaded from: classes2.dex */
final class a {
    private static final byte[] a = {0, 7, 8, 15};
    private static final byte[] b = {0, 119, -120, -1};
    private static final byte[] c = {0, 17, 34, 51, 68, 85, 102, 119, -120, -103, -86, ByteSourceJsonBootstrapper.UTF8_BOM_2, -52, -35, -18, -1};
    private final Paint d = new Paint();
    private final Paint e = new Paint();
    private final Canvas f = new Canvas();
    private final b g = new b(719, 575, 0, 719, 0, 575);
    private final C0071a h = new C0071a(0, b(), c(), d());
    private final h i;
    private Bitmap j;

    private static int a(int i, int i2, int i3, int i4) {
        return (i << 24) | (i2 << 16) | (i3 << 8) | i4;
    }

    public a(int i, int i2) {
        this.d.setStyle(Paint.Style.FILL_AND_STROKE);
        this.d.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.d.setPathEffect(null);
        this.e.setStyle(Paint.Style.FILL);
        this.e.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        this.e.setPathEffect(null);
        this.i = new h(i, i2);
    }

    public void a() {
        this.i.a();
    }

    public List<Cue> a(byte[] bArr, int i) {
        b bVar;
        int i2;
        int i3;
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr, i);
        while (parsableBitArray.bitsLeft() >= 48 && parsableBitArray.readBits(8) == 15) {
            a(parsableBitArray, this.i);
        }
        d dVar = this.i.i;
        if (dVar == null) {
            return Collections.emptyList();
        }
        if (this.i.h != null) {
            bVar = this.i.h;
        } else {
            bVar = this.g;
        }
        if (!(this.j != null && bVar.a + 1 == this.j.getWidth() && bVar.b + 1 == this.j.getHeight())) {
            this.j = Bitmap.createBitmap(bVar.a + 1, bVar.b + 1, Bitmap.Config.ARGB_8888);
            this.f.setBitmap(this.j);
        }
        ArrayList arrayList = new ArrayList();
        SparseArray<e> sparseArray = dVar.d;
        for (int i4 = 0; i4 < sparseArray.size(); i4++) {
            this.f.save();
            e valueAt = sparseArray.valueAt(i4);
            f fVar = this.i.c.get(sparseArray.keyAt(i4));
            int i5 = valueAt.a + bVar.c;
            int i6 = valueAt.b + bVar.e;
            this.f.clipRect(i5, i6, Math.min(fVar.c + i5, bVar.d), Math.min(fVar.d + i6, bVar.f));
            C0071a aVar = this.i.d.get(fVar.g);
            if (aVar == null && (aVar = this.i.f.get(fVar.g)) == null) {
                aVar = this.h;
            }
            SparseArray<g> sparseArray2 = fVar.k;
            int i7 = 0;
            while (i7 < sparseArray2.size()) {
                int keyAt = sparseArray2.keyAt(i7);
                g valueAt2 = sparseArray2.valueAt(i7);
                c cVar = this.i.e.get(keyAt);
                c cVar2 = cVar == null ? this.i.g.get(keyAt) : cVar;
                if (cVar2 != null) {
                    i3 = i7;
                    sparseArray2 = sparseArray2;
                    a(cVar2, aVar, fVar.f, valueAt2.c + i5, i6 + valueAt2.d, cVar2.b ? null : this.d, this.f);
                } else {
                    i3 = i7;
                    sparseArray2 = sparseArray2;
                }
                i7 = i3 + 1;
            }
            if (fVar.b) {
                if (fVar.f == 3) {
                    i2 = aVar.d[fVar.h];
                } else if (fVar.f == 2) {
                    i2 = aVar.c[fVar.i];
                } else {
                    i2 = aVar.b[fVar.j];
                }
                this.e.setColor(i2);
                this.f.drawRect(i5, i6, fVar.c + i5, fVar.d + i6, this.e);
            }
            arrayList.add(new Cue.Builder().setBitmap(Bitmap.createBitmap(this.j, i5, i6, fVar.c, fVar.d)).setPosition(i5 / bVar.a).setPositionAnchor(0).setLine(i6 / bVar.b, 0).setLineAnchor(0).setSize(fVar.c / bVar.a).setBitmapHeight(fVar.d / bVar.b).build());
            this.f.drawColor(0, PorterDuff.Mode.CLEAR);
            this.f.restore();
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static void a(ParsableBitArray parsableBitArray, h hVar) {
        f fVar;
        int readBits = parsableBitArray.readBits(8);
        int readBits2 = parsableBitArray.readBits(16);
        int readBits3 = parsableBitArray.readBits(16);
        int bytePosition = parsableBitArray.getBytePosition() + readBits3;
        if (readBits3 * 8 > parsableBitArray.bitsLeft()) {
            Log.w("DvbParser", "Data field length exceeds limit");
            parsableBitArray.skipBits(parsableBitArray.bitsLeft());
            return;
        }
        switch (readBits) {
            case 16:
                if (readBits2 == hVar.a) {
                    d dVar = hVar.i;
                    d a2 = a(parsableBitArray, readBits3);
                    if (a2.c == 0) {
                        if (!(dVar == null || dVar.b == a2.b)) {
                            hVar.i = a2;
                            break;
                        }
                    } else {
                        hVar.i = a2;
                        hVar.c.clear();
                        hVar.d.clear();
                        hVar.e.clear();
                        break;
                    }
                }
                break;
            case 17:
                d dVar2 = hVar.i;
                if (readBits2 == hVar.a && dVar2 != null) {
                    f b2 = b(parsableBitArray, readBits3);
                    if (dVar2.c == 0 && (fVar = hVar.c.get(b2.a)) != null) {
                        b2.a(fVar);
                    }
                    hVar.c.put(b2.a, b2);
                    break;
                }
                break;
            case 18:
                if (readBits2 != hVar.a) {
                    if (readBits2 == hVar.b) {
                        C0071a c2 = c(parsableBitArray, readBits3);
                        hVar.f.put(c2.a, c2);
                        break;
                    }
                } else {
                    C0071a c3 = c(parsableBitArray, readBits3);
                    hVar.d.put(c3.a, c3);
                    break;
                }
                break;
            case 19:
                if (readBits2 != hVar.a) {
                    if (readBits2 == hVar.b) {
                        c b3 = b(parsableBitArray);
                        hVar.g.put(b3.a, b3);
                        break;
                    }
                } else {
                    c b4 = b(parsableBitArray);
                    hVar.e.put(b4.a, b4);
                    break;
                }
                break;
            case 20:
                if (readBits2 == hVar.a) {
                    hVar.h = a(parsableBitArray);
                    break;
                }
                break;
        }
        parsableBitArray.skipBytes(bytePosition - parsableBitArray.getBytePosition());
    }

    private static b a(ParsableBitArray parsableBitArray) {
        int i;
        int i2;
        int i3;
        parsableBitArray.skipBits(4);
        boolean readBit = parsableBitArray.readBit();
        parsableBitArray.skipBits(3);
        int readBits = parsableBitArray.readBits(16);
        int readBits2 = parsableBitArray.readBits(16);
        int i4 = 0;
        if (readBit) {
            int readBits3 = parsableBitArray.readBits(16);
            int readBits4 = parsableBitArray.readBits(16);
            int readBits5 = parsableBitArray.readBits(16);
            i = parsableBitArray.readBits(16);
            i3 = readBits4;
            i2 = readBits5;
            i4 = readBits3;
        } else {
            i2 = 0;
            i3 = readBits;
            i = readBits2;
        }
        return new b(readBits, readBits2, i4, i3, i2, i);
    }

    private static d a(ParsableBitArray parsableBitArray, int i) {
        int readBits = parsableBitArray.readBits(8);
        int readBits2 = parsableBitArray.readBits(4);
        int readBits3 = parsableBitArray.readBits(2);
        parsableBitArray.skipBits(2);
        int i2 = i - 2;
        SparseArray sparseArray = new SparseArray();
        while (i2 > 0) {
            int readBits4 = parsableBitArray.readBits(8);
            parsableBitArray.skipBits(8);
            i2 -= 6;
            sparseArray.put(readBits4, new e(parsableBitArray.readBits(16), parsableBitArray.readBits(16)));
        }
        return new d(readBits, readBits2, readBits3, sparseArray);
    }

    private static f b(ParsableBitArray parsableBitArray, int i) {
        int i2;
        int readBits = parsableBitArray.readBits(8);
        parsableBitArray.skipBits(4);
        boolean readBit = parsableBitArray.readBit();
        parsableBitArray.skipBits(3);
        int i3 = 16;
        int readBits2 = parsableBitArray.readBits(16);
        int readBits3 = parsableBitArray.readBits(16);
        int readBits4 = parsableBitArray.readBits(3);
        int readBits5 = parsableBitArray.readBits(3);
        int i4 = 2;
        parsableBitArray.skipBits(2);
        int readBits6 = parsableBitArray.readBits(8);
        int readBits7 = parsableBitArray.readBits(8);
        int readBits8 = parsableBitArray.readBits(4);
        int readBits9 = parsableBitArray.readBits(2);
        parsableBitArray.skipBits(2);
        int i5 = i - 10;
        SparseArray sparseArray = new SparseArray();
        while (i5 > 0) {
            int readBits10 = parsableBitArray.readBits(i3);
            int readBits11 = parsableBitArray.readBits(i4);
            int readBits12 = parsableBitArray.readBits(i4);
            int readBits13 = parsableBitArray.readBits(12);
            parsableBitArray.skipBits(4);
            int readBits14 = parsableBitArray.readBits(12);
            i5 -= 6;
            int i6 = 0;
            if (readBits11 == 1 || readBits11 == 2) {
                i5 -= 2;
                i6 = parsableBitArray.readBits(8);
                i2 = parsableBitArray.readBits(8);
            } else {
                i2 = 0;
            }
            sparseArray.put(readBits10, new g(readBits11, readBits12, readBits13, readBits14, i6, i2));
            readBits9 = readBits9;
            i4 = 2;
            i3 = 16;
        }
        return new f(readBits, readBit, readBits2, readBits3, readBits4, readBits5, readBits6, readBits7, readBits8, readBits9, sparseArray);
    }

    private static C0071a c(ParsableBitArray parsableBitArray, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = 8;
        int readBits = parsableBitArray.readBits(8);
        parsableBitArray.skipBits(8);
        int i7 = 2;
        int i8 = i - 2;
        int[] b2 = b();
        int[] c2 = c();
        int[] d2 = d();
        while (i8 > 0) {
            int readBits2 = parsableBitArray.readBits(i6);
            int readBits3 = parsableBitArray.readBits(i6);
            int i9 = i8 - 2;
            int[] iArr = (readBits3 & 128) != 0 ? b2 : (readBits3 & 64) != 0 ? c2 : d2;
            if ((readBits3 & 1) != 0) {
                i5 = parsableBitArray.readBits(i6);
                i4 = parsableBitArray.readBits(i6);
                i3 = parsableBitArray.readBits(i6);
                i2 = parsableBitArray.readBits(i6);
                i8 = i9 - 4;
            } else {
                i5 = parsableBitArray.readBits(6) << i7;
                i4 = parsableBitArray.readBits(4) << 4;
                i3 = parsableBitArray.readBits(4) << 4;
                i2 = parsableBitArray.readBits(i7) << 6;
                i8 = i9 - 2;
            }
            if (i5 == 0) {
                i2 = 255;
                i4 = 0;
                i3 = 0;
            }
            double d3 = i5;
            double d4 = i4 - 128;
            double d5 = i3 - 128;
            iArr[readBits2] = a((byte) (255 - (i2 & 255)), Util.constrainValue((int) (d3 + (1.402d * d4)), 0, 255), Util.constrainValue((int) ((d3 - (0.34414d * d5)) - (d4 * 0.71414d)), 0, 255), Util.constrainValue((int) (d3 + (d5 * 1.772d)), 0, 255));
            readBits = readBits;
            i6 = 8;
            i7 = 2;
        }
        return new C0071a(readBits, b2, c2, d2);
    }

    private static c b(ParsableBitArray parsableBitArray) {
        int readBits = parsableBitArray.readBits(16);
        parsableBitArray.skipBits(4);
        int readBits2 = parsableBitArray.readBits(2);
        boolean readBit = parsableBitArray.readBit();
        parsableBitArray.skipBits(1);
        byte[] bArr = Util.EMPTY_BYTE_ARRAY;
        byte[] bArr2 = Util.EMPTY_BYTE_ARRAY;
        if (readBits2 == 1) {
            parsableBitArray.skipBits(parsableBitArray.readBits(8) * 16);
        } else if (readBits2 == 0) {
            int readBits3 = parsableBitArray.readBits(16);
            int readBits4 = parsableBitArray.readBits(16);
            if (readBits3 > 0) {
                bArr = new byte[readBits3];
                parsableBitArray.readBytes(bArr, 0, readBits3);
            }
            if (readBits4 > 0) {
                bArr2 = new byte[readBits4];
                parsableBitArray.readBytes(bArr2, 0, readBits4);
            } else {
                bArr2 = bArr;
            }
        }
        return new c(readBits, readBit, bArr, bArr2);
    }

    private static int[] b() {
        return new int[]{0, -1, ViewCompat.MEASURED_STATE_MASK, -8421505};
    }

    private static int[] c() {
        int[] iArr = new int[16];
        iArr[0] = 0;
        for (int i = 1; i < iArr.length; i++) {
            if (i < 8) {
                iArr[i] = a(255, (i & 1) != 0 ? 255 : 0, (i & 2) != 0 ? 255 : 0, (i & 4) != 0 ? 255 : 0);
            } else {
                int i2 = 127;
                int i3 = (i & 1) != 0 ? 127 : 0;
                int i4 = (i & 2) != 0 ? 127 : 0;
                if ((i & 4) == 0) {
                    i2 = 0;
                }
                iArr[i] = a(255, i3, i4, i2);
            }
        }
        return iArr;
    }

    private static int[] d() {
        int[] iArr = new int[256];
        iArr[0] = 0;
        for (int i = 0; i < iArr.length; i++) {
            int i2 = 255;
            if (i < 8) {
                int i3 = (i & 1) != 0 ? 255 : 0;
                int i4 = (i & 2) != 0 ? 255 : 0;
                if ((i & 4) == 0) {
                    i2 = 0;
                }
                iArr[i] = a(63, i3, i4, i2);
            } else {
                int i5 = i & 136;
                int i6 = 170;
                int i7 = 85;
                if (i5 == 0) {
                    int i8 = ((i & 1) != 0 ? 85 : 0) + ((i & 16) != 0 ? 170 : 0);
                    int i9 = ((i & 2) != 0 ? 85 : 0) + ((i & 32) != 0 ? 170 : 0);
                    if ((i & 4) == 0) {
                        i7 = 0;
                    }
                    if ((i & 64) == 0) {
                        i6 = 0;
                    }
                    iArr[i] = a(255, i8, i9, i7 + i6);
                } else if (i5 != 8) {
                    int i10 = 43;
                    if (i5 == 128) {
                        int i11 = ((i & 1) != 0 ? 43 : 0) + 127 + ((i & 16) != 0 ? 85 : 0);
                        int i12 = ((i & 2) != 0 ? 43 : 0) + 127 + ((i & 32) != 0 ? 85 : 0);
                        if ((i & 4) == 0) {
                            i10 = 0;
                        }
                        int i13 = i10 + 127;
                        if ((i & 64) == 0) {
                            i7 = 0;
                        }
                        iArr[i] = a(255, i11, i12, i13 + i7);
                    } else if (i5 == 136) {
                        int i14 = ((i & 1) != 0 ? 43 : 0) + ((i & 16) != 0 ? 85 : 0);
                        int i15 = ((i & 2) != 0 ? 43 : 0) + ((i & 32) != 0 ? 85 : 0);
                        if ((i & 4) == 0) {
                            i10 = 0;
                        }
                        if ((i & 64) == 0) {
                            i7 = 0;
                        }
                        iArr[i] = a(255, i14, i15, i10 + i7);
                    }
                } else {
                    int i16 = ((i & 1) != 0 ? 85 : 0) + ((i & 16) != 0 ? 170 : 0);
                    int i17 = ((i & 2) != 0 ? 85 : 0) + ((i & 32) != 0 ? 170 : 0);
                    if ((i & 4) == 0) {
                        i7 = 0;
                    }
                    if ((i & 64) == 0) {
                        i6 = 0;
                    }
                    iArr[i] = a(127, i16, i17, i7 + i6);
                }
            }
        }
        return iArr;
    }

    private static void a(c cVar, C0071a aVar, int i, int i2, int i3, @Nullable Paint paint, Canvas canvas) {
        int[] iArr;
        if (i == 3) {
            iArr = aVar.d;
        } else if (i == 2) {
            iArr = aVar.c;
        } else {
            iArr = aVar.b;
        }
        a(cVar.c, iArr, i, i2, i3, paint, canvas);
        a(cVar.d, iArr, i, i2, i3 + 1, paint, canvas);
    }

    private static void a(byte[] bArr, int[] iArr, int i, int i2, int i3, @Nullable Paint paint, Canvas canvas) {
        byte[] bArr2;
        byte[] bArr3;
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr);
        int i4 = i2;
        int i5 = i3;
        byte[] bArr4 = null;
        byte[] bArr5 = null;
        byte[] bArr6 = null;
        while (parsableBitArray.bitsLeft() != 0) {
            int readBits = parsableBitArray.readBits(8);
            if (readBits != 240) {
                switch (readBits) {
                    case 16:
                        if (i == 3) {
                            bArr2 = bArr4 == null ? b : bArr4;
                        } else if (i == 2) {
                            bArr2 = bArr6 == null ? a : bArr6;
                        } else {
                            bArr2 = null;
                        }
                        int a2 = a(parsableBitArray, iArr, bArr2, i4, i5, paint, canvas);
                        parsableBitArray.byteAlign();
                        i4 = a2;
                        continue;
                    case 17:
                        if (i == 3) {
                            bArr3 = bArr5 == null ? c : bArr5;
                        } else {
                            bArr3 = null;
                        }
                        int b2 = b(parsableBitArray, iArr, bArr3, i4, i5, paint, canvas);
                        parsableBitArray.byteAlign();
                        i4 = b2;
                        continue;
                    case 18:
                        i4 = c(parsableBitArray, iArr, null, i4, i5, paint, canvas);
                        continue;
                    default:
                        switch (readBits) {
                            case 32:
                                bArr6 = a(4, 4, parsableBitArray);
                                continue;
                            case 33:
                                bArr4 = a(4, 8, parsableBitArray);
                                continue;
                            case 34:
                                bArr5 = a(16, 8, parsableBitArray);
                                continue;
                            default:
                                continue;
                        }
                }
            } else {
                i5 += 2;
                i4 = i2;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2 */
    private static int a(ParsableBitArray parsableBitArray, int[] iArr, @Nullable byte[] bArr, int i, int i2, @Nullable Paint paint, Canvas canvas) {
        boolean z;
        int i3;
        byte b2;
        int i4 = i;
        boolean z2 = false;
        while (true) {
            int readBits = parsableBitArray.readBits(2);
            if (readBits == 0) {
                if (!parsableBitArray.readBit()) {
                    if (!parsableBitArray.readBit()) {
                        switch (parsableBitArray.readBits(2)) {
                            case 0:
                                z = true;
                                b2 = 0;
                                i3 = 0;
                                break;
                            case 1:
                                z = z2;
                                i3 = 2;
                                b2 = 0;
                                break;
                            case 2:
                                i3 = parsableBitArray.readBits(4) + 12;
                                z = z2;
                                b2 = parsableBitArray.readBits(2);
                                break;
                            case 3:
                                i3 = parsableBitArray.readBits(8) + 29;
                                z = z2;
                                b2 = parsableBitArray.readBits(2);
                                break;
                            default:
                                z = z2;
                                b2 = 0;
                                i3 = 0;
                                break;
                        }
                    } else {
                        z = z2;
                        i3 = 1;
                        b2 = 0;
                    }
                } else {
                    i3 = 3 + parsableBitArray.readBits(3);
                    z = z2;
                    b2 = parsableBitArray.readBits(2);
                }
            } else {
                z = z2;
                b2 = readBits;
                i3 = 1;
            }
            if (!(i3 == 0 || paint == null)) {
                if (bArr != 0) {
                    b2 = bArr[b2];
                }
                paint.setColor(iArr[b2 == true ? 1 : 0]);
                canvas.drawRect(i4, i2, i4 + i3, i2 + 1, paint);
            }
            i4 += i3;
            if (z) {
                return i4;
            }
            z2 = z;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2 */
    private static int b(ParsableBitArray parsableBitArray, int[] iArr, @Nullable byte[] bArr, int i, int i2, @Nullable Paint paint, Canvas canvas) {
        boolean z;
        int i3;
        byte b2;
        int i4 = i;
        boolean z2 = false;
        while (true) {
            int readBits = parsableBitArray.readBits(4);
            if (readBits == 0) {
                if (parsableBitArray.readBit()) {
                    if (parsableBitArray.readBit()) {
                        switch (parsableBitArray.readBits(2)) {
                            case 0:
                                z = z2;
                                i3 = 1;
                                b2 = 0;
                                break;
                            case 1:
                                z = z2;
                                i3 = 2;
                                b2 = 0;
                                break;
                            case 2:
                                i3 = parsableBitArray.readBits(4) + 9;
                                z = z2;
                                b2 = parsableBitArray.readBits(4);
                                break;
                            case 3:
                                i3 = parsableBitArray.readBits(8) + 25;
                                z = z2;
                                b2 = parsableBitArray.readBits(4);
                                break;
                            default:
                                z = z2;
                                b2 = 0;
                                i3 = 0;
                                break;
                        }
                    } else {
                        i3 = parsableBitArray.readBits(2) + 4;
                        z = z2;
                        b2 = parsableBitArray.readBits(4);
                    }
                } else {
                    int readBits2 = parsableBitArray.readBits(3);
                    if (readBits2 != 0) {
                        i3 = readBits2 + 2;
                        z = z2;
                        b2 = 0;
                    } else {
                        z = true;
                        b2 = 0;
                        i3 = 0;
                    }
                }
            } else {
                z = z2;
                b2 = readBits;
                i3 = 1;
            }
            if (!(i3 == 0 || paint == null)) {
                if (bArr != 0) {
                    b2 = bArr[b2];
                }
                paint.setColor(iArr[b2 == true ? 1 : 0]);
                canvas.drawRect(i4, i2, i4 + i3, i2 + 1, paint);
            }
            i4 += i3;
            if (z) {
                return i4;
            }
            z2 = z;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2 */
    private static int c(ParsableBitArray parsableBitArray, int[] iArr, @Nullable byte[] bArr, int i, int i2, @Nullable Paint paint, Canvas canvas) {
        boolean z;
        int i3;
        byte b2;
        int i4 = i;
        boolean z2 = false;
        while (true) {
            int readBits = parsableBitArray.readBits(8);
            if (readBits != 0) {
                z = z2;
                b2 = readBits;
                i3 = 1;
            } else if (!parsableBitArray.readBit()) {
                int readBits2 = parsableBitArray.readBits(7);
                if (readBits2 != 0) {
                    z = z2;
                    i3 = readBits2;
                    b2 = 0;
                } else {
                    z = true;
                    b2 = 0;
                    i3 = 0;
                }
            } else {
                z = z2;
                i3 = parsableBitArray.readBits(7);
                b2 = parsableBitArray.readBits(8);
            }
            if (!(i3 == 0 || paint == null)) {
                if (bArr != 0) {
                    b2 = bArr[b2];
                }
                paint.setColor(iArr[b2 == true ? 1 : 0]);
                canvas.drawRect(i4, i2, i4 + i3, i2 + 1, paint);
            }
            i4 += i3;
            if (z) {
                return i4;
            }
            z2 = z;
        }
    }

    private static byte[] a(int i, int i2, ParsableBitArray parsableBitArray) {
        byte[] bArr = new byte[i];
        for (int i3 = 0; i3 < i; i3++) {
            bArr[i3] = (byte) parsableBitArray.readBits(i2);
        }
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DvbParser.java */
    /* loaded from: classes2.dex */
    public static final class h {
        public final int a;
        public final int b;
        public final SparseArray<f> c = new SparseArray<>();
        public final SparseArray<C0071a> d = new SparseArray<>();
        public final SparseArray<c> e = new SparseArray<>();
        public final SparseArray<C0071a> f = new SparseArray<>();
        public final SparseArray<c> g = new SparseArray<>();
        @Nullable
        public b h;
        @Nullable
        public d i;

        public h(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public void a() {
            this.c.clear();
            this.d.clear();
            this.e.clear();
            this.f.clear();
            this.g.clear();
            this.h = null;
            this.i = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DvbParser.java */
    /* loaded from: classes2.dex */
    public static final class b {
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;

        public b(int i, int i2, int i3, int i4, int i5, int i6) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DvbParser.java */
    /* loaded from: classes2.dex */
    public static final class d {
        public final int a;
        public final int b;
        public final int c;
        public final SparseArray<e> d;

        public d(int i, int i2, int i3, SparseArray<e> sparseArray) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = sparseArray;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DvbParser.java */
    /* loaded from: classes2.dex */
    public static final class e {
        public final int a;
        public final int b;

        public e(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DvbParser.java */
    /* loaded from: classes2.dex */
    public static final class f {
        public final int a;
        public final boolean b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final int g;
        public final int h;
        public final int i;
        public final int j;
        public final SparseArray<g> k;

        public f(int i, boolean z, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, SparseArray<g> sparseArray) {
            this.a = i;
            this.b = z;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
            this.g = i6;
            this.h = i7;
            this.i = i8;
            this.j = i9;
            this.k = sparseArray;
        }

        public void a(f fVar) {
            SparseArray<g> sparseArray = fVar.k;
            for (int i = 0; i < sparseArray.size(); i++) {
                this.k.put(sparseArray.keyAt(i), sparseArray.valueAt(i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DvbParser.java */
    /* loaded from: classes2.dex */
    public static final class g {
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;

        public g(int i, int i2, int i3, int i4, int i5, int i6) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DvbParser.java */
    /* renamed from: com.google.android.exoplayer2.text.dvb.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static final class C0071a {
        public final int a;
        public final int[] b;
        public final int[] c;
        public final int[] d;

        public C0071a(int i, int[] iArr, int[] iArr2, int[] iArr3) {
            this.a = i;
            this.b = iArr;
            this.c = iArr2;
            this.d = iArr3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DvbParser.java */
    /* loaded from: classes2.dex */
    public static final class c {
        public final int a;
        public final boolean b;
        public final byte[] c;
        public final byte[] d;

        public c(int i, boolean z, byte[] bArr, byte[] bArr2) {
            this.a = i;
            this.b = z;
            this.c = bArr;
            this.d = bArr2;
        }
    }
}
