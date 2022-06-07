package com.xiaomi.micolauncher.common.player.color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class MMCQ {
    private static final Comparator<VBox> a = new Comparator<VBox>() { // from class: com.xiaomi.micolauncher.common.player.color.MMCQ.1
        /* renamed from: a */
        public int compare(VBox vBox, VBox vBox2) {
            return vBox.count(false) - vBox2.count(false);
        }
    };
    private static final Comparator<VBox> b = new Comparator<VBox>() { // from class: com.xiaomi.micolauncher.common.player.color.MMCQ.2
        /* renamed from: a */
        public int compare(VBox vBox, VBox vBox2) {
            int count = vBox.count(false);
            int count2 = vBox2.count(false);
            int volume = vBox.volume(false);
            int volume2 = vBox2.volume(false);
            return count == count2 ? volume - volume2 : Long.compare(count * volume, count2 * volume2);
        }
    };

    static int a(int i, int i2, int i3) {
        return (i << 10) + (i2 << 5) + i3;
    }

    /* loaded from: classes3.dex */
    public static class VBox {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        private final int[] g;
        private int[] h;
        private Integer i;
        private Integer j;

        public VBox(int i, int i2, int i3, int i4, int i5, int i6, int[] iArr) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
            this.g = iArr;
        }

        public String toString() {
            return "r1: " + this.a + " / r2: " + this.b + " / g1: " + this.c + " / g2: " + this.d + " / b1: " + this.e + " / b2: " + this.f;
        }

        public int volume(boolean z) {
            if (this.i == null || z) {
                this.i = Integer.valueOf(((this.b - this.a) + 1) * ((this.d - this.c) + 1) * ((this.f - this.e) + 1));
            }
            return this.i.intValue();
        }

        public int count(boolean z) {
            if (this.j == null || z) {
                int i = 0;
                for (int i2 = this.a; i2 <= this.b; i2++) {
                    for (int i3 = this.c; i3 <= this.d; i3++) {
                        for (int i4 = this.e; i4 <= this.f; i4++) {
                            i += this.g[MMCQ.a(i2, i3, i4)];
                        }
                    }
                }
                this.j = Integer.valueOf(i);
            }
            return this.j.intValue();
        }

        public VBox clone() {
            return new VBox(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
        }

        public int[] avg(boolean z) {
            int i;
            if (this.h == null || z) {
                int i2 = this.a;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                while (true) {
                    i = this.b;
                    if (i2 > i) {
                        break;
                    }
                    for (int i7 = this.c; i7 <= this.d; i7++) {
                        for (int i8 = this.e; i8 <= this.f; i8++) {
                            int i9 = this.g[MMCQ.a(i2, i7, i8)];
                            i3 += i9;
                            double d = i9;
                            i4 = (int) (i4 + ((i2 + 0.5d) * d * 8.0d));
                            i5 = (int) (i5 + ((i7 + 0.5d) * d * 8.0d));
                            i6 = (int) (i6 + (d * (i8 + 0.5d) * 8.0d));
                        }
                    }
                    i2++;
                }
                if (i3 > 0) {
                    this.h = new int[]{~(~(i4 / i3)), ~(~(i5 / i3)), ~(~(i6 / i3))};
                } else {
                    this.h = new int[]{~(~((((this.a + i) + 1) * 8) / 2)), ~(~((((this.c + this.d) + 1) * 8) / 2)), ~(~((((this.e + this.f) + 1) * 8) / 2))};
                }
            }
            return this.h;
        }

        public boolean contains(int[] iArr) {
            int i = iArr[0] >> 3;
            int i2 = iArr[1] >> 3;
            int i3 = iArr[2] >> 3;
            return i >= this.a && i <= this.b && i2 >= this.c && i2 <= this.d && i3 >= this.e && i3 <= this.f;
        }
    }

    /* loaded from: classes3.dex */
    public static class CMap {
        public final ArrayList<VBox> vboxes = new ArrayList<>();

        public void push(VBox vBox) {
            this.vboxes.add(vBox);
        }

        public int[][] palette() {
            int size = this.vboxes.size();
            int[][] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = this.vboxes.get(i).avg(false);
            }
            return iArr;
        }

        public int size() {
            return this.vboxes.size();
        }

        public int[] map(int[] iArr) {
            int size = this.vboxes.size();
            for (int i = 0; i < size; i++) {
                VBox vBox = this.vboxes.get(i);
                if (vBox.contains(iArr)) {
                    return vBox.avg(false);
                }
            }
            return nearest(iArr);
        }

        public int[] nearest(int[] iArr) {
            int size = this.vboxes.size();
            double d = Double.MAX_VALUE;
            int[] iArr2 = null;
            for (int i = 0; i < size; i++) {
                int[] avg = this.vboxes.get(i).avg(false);
                double sqrt = Math.sqrt(Math.pow(iArr[0] - avg[0], 2.0d) + Math.pow(iArr[1] - avg[1], 2.0d) + Math.pow(iArr[2] - avg[2], 2.0d));
                if (sqrt < d) {
                    iArr2 = avg;
                    d = sqrt;
                }
            }
            return iArr2;
        }
    }

    private static int[] a(int[][] iArr) {
        int[] iArr2 = new int[32768];
        for (int[] iArr3 : iArr) {
            int a2 = a(iArr3[0] >> 3, iArr3[1] >> 3, iArr3[2] >> 3);
            iArr2[a2] = iArr2[a2] + 1;
        }
        return iArr2;
    }

    private static VBox a(int[][] iArr, int[] iArr2) {
        int i = 1000000;
        int i2 = 1000000;
        int i3 = 1000000;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int[] iArr3 : iArr) {
            int i7 = iArr3[0] >> 3;
            int i8 = iArr3[1] >> 3;
            int i9 = iArr3[2] >> 3;
            if (i7 < i) {
                i = i7;
            } else if (i7 > i4) {
                i4 = i7;
            }
            if (i8 < i2) {
                i2 = i8;
            } else if (i8 > i5) {
                i5 = i8;
            }
            if (i9 < i3) {
                i3 = i9;
            } else if (i9 > i6) {
                i6 = i9;
            }
        }
        return new VBox(i, i4, i2, i5, i3, i6, iArr2);
    }

    private static VBox[] a(int[] iArr, VBox vBox) {
        int i;
        if (vBox.count(false) == 0) {
            return null;
        }
        if (vBox.count(false) == 1) {
            return new VBox[]{vBox.clone(), null};
        }
        int i2 = (vBox.b - vBox.a) + 1;
        int i3 = (vBox.d - vBox.c) + 1;
        int max = Math.max(Math.max(i2, i3), (vBox.f - vBox.e) + 1);
        int[] iArr2 = new int[32];
        Arrays.fill(iArr2, -1);
        int[] iArr3 = new int[32];
        Arrays.fill(iArr3, -1);
        if (max == i2) {
            i = 0;
            for (int i4 = vBox.a; i4 <= vBox.b; i4++) {
                int i5 = 0;
                for (int i6 = vBox.c; i6 <= vBox.d; i6++) {
                    for (int i7 = vBox.e; i7 <= vBox.f; i7++) {
                        i5 += iArr[a(i4, i6, i7)];
                    }
                }
                i += i5;
                iArr2[i4] = i;
            }
        } else if (max == i3) {
            i = 0;
            for (int i8 = vBox.c; i8 <= vBox.d; i8++) {
                int i9 = 0;
                for (int i10 = vBox.a; i10 <= vBox.b; i10++) {
                    for (int i11 = vBox.e; i11 <= vBox.f; i11++) {
                        i9 += iArr[a(i10, i8, i11)];
                    }
                }
                i += i9;
                iArr2[i8] = i;
            }
        } else {
            i = 0;
            for (int i12 = vBox.e; i12 <= vBox.f; i12++) {
                int i13 = 0;
                for (int i14 = vBox.a; i14 <= vBox.b; i14++) {
                    for (int i15 = vBox.c; i15 <= vBox.d; i15++) {
                        i13 += iArr[a(i14, i15, i12)];
                    }
                }
                i += i13;
                iArr2[i12] = i;
            }
        }
        for (int i16 = 0; i16 < 32; i16++) {
            if (iArr2[i16] != -1) {
                iArr3[i16] = i - iArr2[i16];
            }
        }
        if (max == i2) {
            return a('r', vBox, iArr2, iArr3, i);
        }
        if (max == i3) {
            return a('g', vBox, iArr2, iArr3, i);
        }
        return a('b', vBox, iArr2, iArr3, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0065, code lost:
        r7.b = r1;
        r9.a = r1 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.xiaomi.micolauncher.common.player.color.MMCQ.VBox[] a(char r16, com.xiaomi.micolauncher.common.player.color.MMCQ.VBox r17, int[] r18, int[] r19, int r20) {
        /*
            r0 = r16
            r1 = r17
            r2 = 103(0x67, float:1.44E-43)
            r3 = 114(0x72, float:1.6E-43)
            if (r0 != r3) goto L_0x000f
            int r4 = r1.a
            int r5 = r1.b
            goto L_0x001a
        L_0x000f:
            if (r0 != r2) goto L_0x0016
            int r4 = r1.c
            int r5 = r1.d
            goto L_0x001a
        L_0x0016:
            int r4 = r1.e
            int r5 = r1.f
        L_0x001a:
            r6 = r4
        L_0x001b:
            if (r6 > r5) goto L_0x0086
            r7 = r18[r6]
            r8 = 2
            int r9 = r20 / 2
            if (r7 <= r9) goto L_0x0083
            com.xiaomi.micolauncher.common.player.color.MMCQ$VBox r7 = r17.clone()
            com.xiaomi.micolauncher.common.player.color.MMCQ$VBox r9 = r17.clone()
            int r1 = r6 - r4
            int r10 = r5 - r6
            r11 = 1
            if (r1 > r10) goto L_0x003d
            int r5 = r5 - r11
            int r10 = r10 / r8
            int r6 = r6 + r10
            int r1 = ~r6
            int r1 = ~r1
            int r1 = java.lang.Math.min(r5, r1)
            goto L_0x004b
        L_0x003d:
            int r6 = r6 - r11
            double r5 = (double) r6
            double r12 = (double) r1
            r14 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r12 = r12 / r14
            double r5 = r5 - r12
            int r1 = (int) r5
            int r1 = ~r1
            int r1 = ~r1
            int r1 = java.lang.Math.max(r4, r1)
        L_0x004b:
            if (r1 < 0) goto L_0x0080
            r4 = r18[r1]
            if (r4 > 0) goto L_0x0052
            goto L_0x0080
        L_0x0052:
            r4 = r19[r1]
        L_0x0054:
            if (r4 != 0) goto L_0x0063
            if (r1 <= 0) goto L_0x0063
            int r4 = r1 + (-1)
            r4 = r18[r4]
            if (r4 <= 0) goto L_0x0063
            int r1 = r1 + (-1)
            r4 = r19[r1]
            goto L_0x0054
        L_0x0063:
            if (r0 != r3) goto L_0x006b
            r7.b = r1
            int r1 = r1 + r11
            r9.a = r1
            goto L_0x0078
        L_0x006b:
            if (r0 != r2) goto L_0x0073
            r7.d = r1
            int r1 = r1 + r11
            r9.c = r1
            goto L_0x0078
        L_0x0073:
            r7.f = r1
            int r1 = r1 + r11
            r9.e = r1
        L_0x0078:
            com.xiaomi.micolauncher.common.player.color.MMCQ$VBox[] r0 = new com.xiaomi.micolauncher.common.player.color.MMCQ.VBox[r8]
            r1 = 0
            r0[r1] = r7
            r0[r11] = r9
            return r0
        L_0x0080:
            int r1 = r1 + 1
            goto L_0x004b
        L_0x0083:
            int r6 = r6 + 1
            goto L_0x001b
        L_0x0086:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "VBox can't be cut"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.player.color.MMCQ.a(char, com.xiaomi.micolauncher.common.player.color.MMCQ$VBox, int[], int[], int):com.xiaomi.micolauncher.common.player.color.MMCQ$VBox[]");
    }

    public static CMap quantize(int[][] iArr, int i) {
        if (iArr.length == 0 || i < 2 || i > 256) {
            return null;
        }
        int[] a2 = a(iArr);
        VBox a3 = a(iArr, a2);
        ArrayList arrayList = new ArrayList();
        arrayList.add(a3);
        a(arrayList, a, (int) Math.ceil(i * 0.75d), a2);
        Collections.sort(arrayList, b);
        if (i > arrayList.size()) {
            a(arrayList, b, i, a2);
        }
        Collections.reverse(arrayList);
        CMap cMap = new CMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            cMap.push((VBox) it.next());
        }
        return cMap;
    }

    private static void a(List<VBox> list, Comparator<VBox> comparator, int i, int[] iArr) {
        int i2 = 0;
        while (i2 < 1000) {
            VBox vBox = list.get(list.size() - 1);
            if (vBox.count(false) == 0) {
                Collections.sort(list, comparator);
                i2++;
            } else {
                list.remove(list.size() - 1);
                VBox[] a2 = a(iArr, vBox);
                VBox vBox2 = a2[0];
                VBox vBox3 = a2[1];
                if (vBox2 != null) {
                    list.add(vBox2);
                    if (vBox3 != null) {
                        list.add(vBox3);
                    }
                    Collections.sort(list, comparator);
                    if (list.size() < i) {
                        i2++;
                        if (i2 > 1000) {
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    throw new RuntimeException("vbox1 not defined; shouldn't happen!");
                }
            }
        }
    }
}
