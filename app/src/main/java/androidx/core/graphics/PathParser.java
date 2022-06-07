package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PathParser {
    static float[] a(float[] fArr, int i, int i2) {
        if (i <= i2) {
            int length = fArr.length;
            if (i < 0 || i > length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            int i3 = i2 - i;
            int min = Math.min(i3, length - i);
            float[] fArr2 = new float[i3];
            System.arraycopy(fArr, i, fArr2, 0, min);
            return fArr2;
        }
        throw new IllegalArgumentException();
    }

    public static Path createPathFromPathData(String str) {
        Path path = new Path();
        PathDataNode[] createNodesFromPathData = createNodesFromPathData(str);
        if (createNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(createNodesFromPathData, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing " + str, e);
        }
    }

    public static PathDataNode[] createNodesFromPathData(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 1;
        int i2 = 0;
        while (i < str.length()) {
            int a2 = a(str, i);
            String trim = str.substring(i2, a2).trim();
            if (trim.length() > 0) {
                a(arrayList, trim.charAt(0), a(trim));
            }
            i = a2 + 1;
            i2 = a2;
        }
        if (i - i2 == 1 && i2 < str.length()) {
            a(arrayList, str.charAt(i2), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArr) {
        if (pathDataNodeArr == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            pathDataNodeArr2[i] = new PathDataNode(pathDataNodeArr[i]);
        }
        return pathDataNodeArr2;
    }

    public static boolean canMorph(@Nullable PathDataNode[] pathDataNodeArr, @Nullable PathDataNode[] pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false;
        }
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            if (!(pathDataNodeArr[i].mType == pathDataNodeArr2[i].mType && pathDataNodeArr[i].mParams.length == pathDataNodeArr2[i].mParams.length)) {
                return false;
            }
        }
        return true;
    }

    public static void updateNodes(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        for (int i = 0; i < pathDataNodeArr2.length; i++) {
            pathDataNodeArr[i].mType = pathDataNodeArr2[i].mType;
            for (int i2 = 0; i2 < pathDataNodeArr2[i].mParams.length; i2++) {
                pathDataNodeArr[i].mParams[i2] = pathDataNodeArr2[i].mParams[i2];
            }
        }
    }

    private static int a(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (((charAt - 'A') * (charAt - 'Z') <= 0 || (charAt - 'a') * (charAt - 'z') <= 0) && charAt != 'e' && charAt != 'E') {
                return i;
            }
            i++;
        }
        return i;
    }

    private static void a(ArrayList<PathDataNode> arrayList, char c, float[] fArr) {
        arrayList.add(new PathDataNode(c, fArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        int a;
        boolean b;

        a() {
        }
    }

    private static float[] a(String str) {
        if (str.charAt(0) == 'z' || str.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] fArr = new float[str.length()];
            a aVar = new a();
            int length = str.length();
            int i = 1;
            int i2 = 0;
            while (i < length) {
                a(str, i, aVar);
                int i3 = aVar.a;
                if (i < i3) {
                    i2++;
                    fArr[i2] = Float.parseFloat(str.substring(i, i3));
                }
                i = aVar.b ? i3 : i3 + 1;
            }
            return a(fArr, 0, i2);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + str + "\"", e);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:22:0x003e A[LOOP:0: B:3:0x0007->B:22:0x003e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0041 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(java.lang.String r8, int r9, androidx.core.graphics.PathParser.a r10) {
        /*
            r0 = 0
            r10.b = r0
            r1 = r9
            r2 = r0
            r3 = r2
            r4 = r3
        L_0x0007:
            int r5 = r8.length()
            if (r1 >= r5) goto L_0x0041
            char r5 = r8.charAt(r1)
            r6 = 32
            r7 = 1
            if (r5 == r6) goto L_0x0039
            r6 = 69
            if (r5 == r6) goto L_0x0037
            r6 = 101(0x65, float:1.42E-43)
            if (r5 == r6) goto L_0x0037
            switch(r5) {
                case 44: goto L_0x0039;
                case 45: goto L_0x002c;
                case 46: goto L_0x0022;
                default: goto L_0x0021;
            }
        L_0x0021:
            goto L_0x0035
        L_0x0022:
            if (r3 != 0) goto L_0x0027
            r2 = r0
            r3 = r7
            goto L_0x003b
        L_0x0027:
            r10.b = r7
            r2 = r0
            r4 = r7
            goto L_0x003b
        L_0x002c:
            if (r1 == r9) goto L_0x0035
            if (r2 != 0) goto L_0x0035
            r10.b = r7
            r2 = r0
            r4 = r7
            goto L_0x003b
        L_0x0035:
            r2 = r0
            goto L_0x003b
        L_0x0037:
            r2 = r7
            goto L_0x003b
        L_0x0039:
            r2 = r0
            r4 = r7
        L_0x003b:
            if (r4 == 0) goto L_0x003e
            goto L_0x0041
        L_0x003e:
            int r1 = r1 + 1
            goto L_0x0007
        L_0x0041:
            r10.a = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.PathParser.a(java.lang.String, int, androidx.core.graphics.PathParser$a):void");
    }

    public static boolean interpolatePathDataNodes(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2, PathDataNode[] pathDataNodeArr3, float f) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr3 == null) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
        } else if (pathDataNodeArr.length == pathDataNodeArr2.length && pathDataNodeArr2.length == pathDataNodeArr3.length) {
            if (!canMorph(pathDataNodeArr2, pathDataNodeArr3)) {
                return false;
            }
            for (int i = 0; i < pathDataNodeArr.length; i++) {
                pathDataNodeArr[i].interpolatePathDataNode(pathDataNodeArr2[i], pathDataNodeArr3[i], f);
            }
            return true;
        } else {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
        }
    }

    /* loaded from: classes.dex */
    public static class PathDataNode {
        public float[] mParams;
        public char mType;

        PathDataNode(char c, float[] fArr) {
            this.mType = c;
            this.mParams = fArr;
        }

        PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            float[] fArr = pathDataNode.mParams;
            this.mParams = PathParser.a(fArr, 0, fArr.length);
        }

        public static void nodesToPath(PathDataNode[] pathDataNodeArr, Path path) {
            float[] fArr = new float[6];
            char c = 'm';
            for (int i = 0; i < pathDataNodeArr.length; i++) {
                a(path, fArr, c, pathDataNodeArr[i].mType, pathDataNodeArr[i].mParams);
                c = pathDataNodeArr[i].mType;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f) {
            this.mType = pathDataNode.mType;
            int i = 0;
            while (true) {
                float[] fArr = pathDataNode.mParams;
                if (i < fArr.length) {
                    this.mParams[i] = (fArr[i] * (1.0f - f)) + (pathDataNode2.mParams[i] * f);
                    i++;
                } else {
                    return;
                }
            }
        }

        private static void a(Path path, float[] fArr, char c, char c2, float[] fArr2) {
            int i;
            int i2;
            float f;
            float f2;
            boolean z;
            float f3;
            float f4;
            float f5;
            Path path2 = path;
            boolean z2 = false;
            float f6 = fArr[0];
            float f7 = fArr[1];
            float f8 = fArr[2];
            float f9 = fArr[3];
            float f10 = fArr[4];
            float f11 = fArr[5];
            switch (c2) {
                case 'A':
                case 'a':
                    i = 7;
                    break;
                case 'C':
                case 'c':
                    i = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i = 1;
                    break;
                case 'L':
                case 'M':
                case 'T':
                case 'l':
                case 'm':
                case 't':
                    i = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path2.moveTo(f10, f11);
                    f6 = f10;
                    f8 = f6;
                    f7 = f11;
                    f9 = f7;
                    i = 2;
                    break;
                default:
                    i = 2;
                    break;
            }
            float f12 = f10;
            float f13 = f11;
            int i3 = 0;
            char c3 = c;
            while (i3 < fArr2.length) {
                float f14 = 0.0f;
                switch (c2) {
                    case 'A':
                        i2 = i3;
                        int i4 = i2 + 5;
                        int i5 = i2 + 6;
                        a(path, f6, f7, fArr2[i4], fArr2[i5], fArr2[i2 + 0], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f, fArr2[i2 + 4] != 0.0f);
                        f6 = fArr2[i4];
                        f7 = fArr2[i5];
                        f9 = f7;
                        f8 = f6;
                        break;
                    case 'C':
                        i2 = i3;
                        int i6 = i2 + 2;
                        int i7 = i2 + 3;
                        int i8 = i2 + 4;
                        int i9 = i2 + 5;
                        path.cubicTo(fArr2[i2 + 0], fArr2[i2 + 1], fArr2[i6], fArr2[i7], fArr2[i8], fArr2[i9]);
                        f6 = fArr2[i8];
                        float f15 = fArr2[i9];
                        float f16 = fArr2[i6];
                        float f17 = fArr2[i7];
                        f7 = f15;
                        f9 = f17;
                        f8 = f16;
                        break;
                    case 'H':
                        i2 = i3;
                        int i10 = i2 + 0;
                        path2.lineTo(fArr2[i10], f7);
                        f6 = fArr2[i10];
                        break;
                    case 'L':
                        i2 = i3;
                        int i11 = i2 + 0;
                        int i12 = i2 + 1;
                        path2.lineTo(fArr2[i11], fArr2[i12]);
                        f6 = fArr2[i11];
                        f7 = fArr2[i12];
                        break;
                    case 'M':
                        i2 = i3;
                        int i13 = i2 + 0;
                        f6 = fArr2[i13];
                        int i14 = i2 + 1;
                        f7 = fArr2[i14];
                        if (i2 <= 0) {
                            path2.moveTo(fArr2[i13], fArr2[i14]);
                            f13 = f7;
                            f12 = f6;
                            break;
                        } else {
                            path2.lineTo(fArr2[i13], fArr2[i14]);
                            break;
                        }
                    case 'Q':
                        i2 = i3;
                        int i15 = i2 + 0;
                        int i16 = i2 + 1;
                        int i17 = i2 + 2;
                        int i18 = i2 + 3;
                        path2.quadTo(fArr2[i15], fArr2[i16], fArr2[i17], fArr2[i18]);
                        float f18 = fArr2[i15];
                        float f19 = fArr2[i16];
                        f6 = fArr2[i17];
                        f7 = fArr2[i18];
                        f8 = f18;
                        f9 = f19;
                        break;
                    case 'S':
                        i2 = i3;
                        if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                            f2 = (f6 * 2.0f) - f8;
                            f = (f7 * 2.0f) - f9;
                        } else {
                            f2 = f6;
                            f = f7;
                        }
                        int i19 = i2 + 0;
                        int i20 = i2 + 1;
                        int i21 = i2 + 2;
                        int i22 = i2 + 3;
                        path.cubicTo(f2, f, fArr2[i19], fArr2[i20], fArr2[i21], fArr2[i22]);
                        float f20 = fArr2[i19];
                        float f21 = fArr2[i20];
                        f6 = fArr2[i21];
                        f7 = fArr2[i22];
                        f8 = f20;
                        f9 = f21;
                        break;
                    case 'T':
                        float f22 = f7;
                        float f23 = f6;
                        i2 = i3;
                        if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                            f23 = (f23 * 2.0f) - f8;
                            f22 = (f22 * 2.0f) - f9;
                        }
                        int i23 = i2 + 0;
                        int i24 = i2 + 1;
                        path2.quadTo(f23, f22, fArr2[i23], fArr2[i24]);
                        f6 = fArr2[i23];
                        f8 = f23;
                        f9 = f22;
                        f7 = fArr2[i24];
                        break;
                    case 'V':
                        i2 = i3;
                        int i25 = i2 + 0;
                        path2 = path;
                        path2.lineTo(f6, fArr2[i25]);
                        f6 = f6;
                        f7 = fArr2[i25];
                        break;
                    case 'a':
                        int i26 = i3 + 5;
                        float f24 = fArr2[i26] + f6;
                        int i27 = i3 + 6;
                        float f25 = fArr2[i27] + f7;
                        float f26 = fArr2[i3 + 0];
                        float f27 = fArr2[i3 + 1];
                        float f28 = fArr2[i3 + 2];
                        boolean z3 = fArr2[i3 + 3] != 0.0f ? true : z2;
                        if (fArr2[i3 + 4] != 0.0f) {
                            z = true;
                        } else {
                            boolean z4 = z2 ? 1 : 0;
                            Object[] objArr = z2 ? 1 : 0;
                            Object[] objArr2 = z2 ? 1 : 0;
                            z = z4;
                        }
                        i2 = i3;
                        a(path, f6, f7, f24, f25, f26, f27, f28, z3, z);
                        f6 += fArr2[i26];
                        f7 += fArr2[i27];
                        f9 = f7;
                        f8 = f6;
                        path2 = path;
                        break;
                    case 'c':
                        int i28 = i3 + 2;
                        int i29 = i3 + 3;
                        int i30 = i3 + 4;
                        int i31 = i3 + 5;
                        path.rCubicTo(fArr2[i3 + 0], fArr2[i3 + 1], fArr2[i28], fArr2[i29], fArr2[i30], fArr2[i31]);
                        f8 = fArr2[i28] + f6;
                        f9 = fArr2[i29] + f7;
                        f6 += fArr2[i30];
                        f7 += fArr2[i31];
                        i2 = i3;
                        break;
                    case 'h':
                        int i32 = i3 + 0;
                        path2.rLineTo(fArr2[i32], 0.0f);
                        f6 += fArr2[i32];
                        i2 = i3;
                        break;
                    case 'l':
                        int i33 = i3 + 0;
                        int i34 = i3 + 1;
                        path2.rLineTo(fArr2[i33], fArr2[i34]);
                        f6 += fArr2[i33];
                        f7 += fArr2[i34];
                        i2 = i3;
                        break;
                    case 'm':
                        int i35 = i3 + 0;
                        f6 += fArr2[i35];
                        int i36 = i3 + 1;
                        f7 += fArr2[i36];
                        if (i3 <= 0) {
                            path2.rMoveTo(fArr2[i35], fArr2[i36]);
                            f13 = f7;
                            f12 = f6;
                            i2 = i3;
                            break;
                        } else {
                            path2.rLineTo(fArr2[i35], fArr2[i36]);
                            i2 = i3;
                            break;
                        }
                    case 'q':
                        int i37 = i3 + 0;
                        int i38 = i3 + 1;
                        int i39 = i3 + 2;
                        int i40 = i3 + 3;
                        path2.rQuadTo(fArr2[i37], fArr2[i38], fArr2[i39], fArr2[i40]);
                        f8 = fArr2[i37] + f6;
                        f9 = fArr2[i38] + f7;
                        f6 += fArr2[i39];
                        f7 += fArr2[i40];
                        i2 = i3;
                        break;
                    case 's':
                        if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                            f4 = f6 - f8;
                            f3 = f7 - f9;
                        } else {
                            f4 = 0.0f;
                            f3 = 0.0f;
                        }
                        int i41 = i3 + 0;
                        int i42 = i3 + 1;
                        int i43 = i3 + 2;
                        int i44 = i3 + 3;
                        path.rCubicTo(f4, f3, fArr2[i41], fArr2[i42], fArr2[i43], fArr2[i44]);
                        f8 = fArr2[i41] + f6;
                        f9 = fArr2[i42] + f7;
                        f6 += fArr2[i43];
                        f7 += fArr2[i44];
                        i2 = i3;
                        break;
                    case 't':
                        if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                            f14 = f6 - f8;
                            f5 = f7 - f9;
                        } else {
                            f5 = 0.0f;
                        }
                        int i45 = i3 + 0;
                        int i46 = i3 + 1;
                        path2.rQuadTo(f14, f5, fArr2[i45], fArr2[i46]);
                        f8 = f14 + f6;
                        f9 = f5 + f7;
                        f6 += fArr2[i45];
                        f7 += fArr2[i46];
                        i2 = i3;
                        break;
                    case 'v':
                        int i47 = i3 + 0;
                        path2.rLineTo(0.0f, fArr2[i47]);
                        f7 += fArr2[i47];
                        i2 = i3;
                        break;
                    default:
                        i2 = i3;
                        f7 = f7;
                        break;
                }
                i3 = i2 + i;
                c3 = c2;
                z2 = false;
            }
            char c4 = z2 ? 1 : 0;
            char c5 = z2 ? 1 : 0;
            char c6 = z2 ? 1 : 0;
            fArr[c4] = f6;
            fArr[1] = f7;
            fArr[2] = f8;
            fArr[3] = f9;
            fArr[4] = f12;
            fArr[5] = f13;
        }

        private static void a(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
            double d;
            double d2;
            double radians = Math.toRadians(f7);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double d3 = f;
            double d4 = d3 * cos;
            double d5 = f2;
            double d6 = f5;
            double d7 = (d4 + (d5 * sin)) / d6;
            double d8 = f6;
            double d9 = (((-f) * sin) + (d5 * cos)) / d8;
            double d10 = f4;
            double d11 = ((f3 * cos) + (d10 * sin)) / d6;
            double d12 = (((-f3) * sin) + (d10 * cos)) / d8;
            double d13 = d7 - d11;
            double d14 = d9 - d12;
            double d15 = (d7 + d11) / 2.0d;
            double d16 = (d9 + d12) / 2.0d;
            double d17 = (d13 * d13) + (d14 * d14);
            if (d17 == 0.0d) {
                Log.w("PathParser", " Points are coincident");
                return;
            }
            double d18 = (1.0d / d17) - 0.25d;
            if (d18 < 0.0d) {
                Log.w("PathParser", "Points are too far apart " + d17);
                float sqrt = (float) (Math.sqrt(d17) / 1.99999d);
                a(path, f, f2, f3, f4, f5 * sqrt, f6 * sqrt, f7, z, z2);
                return;
            }
            double sqrt2 = Math.sqrt(d18);
            double d19 = d13 * sqrt2;
            double d20 = sqrt2 * d14;
            if (z == z2) {
                d2 = d15 - d20;
                d = d16 + d19;
            } else {
                d2 = d15 + d20;
                d = d16 - d19;
            }
            double atan2 = Math.atan2(d9 - d, d7 - d2);
            double atan22 = Math.atan2(d12 - d, d11 - d2) - atan2;
            int i = (atan22 > 0.0d ? 1 : (atan22 == 0.0d ? 0 : -1));
            if (z2 != (i >= 0)) {
                atan22 = i > 0 ? atan22 - 6.283185307179586d : atan22 + 6.283185307179586d;
            }
            double d21 = d2 * d6;
            double d22 = d * d8;
            a(path, (d21 * cos) - (d22 * sin), (d21 * sin) + (d22 * cos), d6, d8, d3, d5, radians, atan2, atan22);
        }

        private static void a(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
            double d10 = d3;
            int ceil = (int) Math.ceil(Math.abs((d9 * 4.0d) / 3.141592653589793d));
            double cos = Math.cos(d7);
            double sin = Math.sin(d7);
            double cos2 = Math.cos(d8);
            double sin2 = Math.sin(d8);
            double d11 = -d10;
            double d12 = d11 * cos;
            double d13 = d4 * sin;
            double d14 = (d12 * sin2) - (d13 * cos2);
            double d15 = d11 * sin;
            double d16 = d4 * cos;
            double d17 = (sin2 * d15) + (cos2 * d16);
            double d18 = d9 / ceil;
            int i = 0;
            double d19 = d6;
            double d20 = d5;
            double d21 = d8;
            while (i < ceil) {
                double d22 = d21 + d18;
                double sin3 = Math.sin(d22);
                double cos3 = Math.cos(d22);
                double d23 = (d + ((d10 * cos) * cos3)) - (d13 * sin3);
                double d24 = d2 + (d10 * sin * cos3) + (d16 * sin3);
                double d25 = (d12 * sin3) - (d13 * cos3);
                double d26 = (sin3 * d15) + (cos3 * d16);
                double d27 = d22 - d21;
                double tan = Math.tan(d27 / 2.0d);
                double sin4 = (Math.sin(d27) * (Math.sqrt(((tan * 3.0d) * tan) + 4.0d) - 1.0d)) / 3.0d;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float) (d20 + (d14 * sin4)), (float) (d19 + (d17 * sin4)), (float) (d23 - (sin4 * d25)), (float) (d24 - (sin4 * d26)), (float) d23, (float) d24);
                i++;
                d18 = d18;
                ceil = ceil;
                sin = sin;
                d19 = d24;
                d15 = d15;
                d21 = d22;
                d17 = d26;
                d14 = d25;
                cos = cos;
                d10 = d3;
                d20 = d23;
            }
        }
    }

    private PathParser() {
    }
}
