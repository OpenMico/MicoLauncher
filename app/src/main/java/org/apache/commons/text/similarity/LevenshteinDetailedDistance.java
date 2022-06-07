package org.apache.commons.text.similarity;

import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class LevenshteinDetailedDistance implements EditDistance<LevenshteinResults> {
    private static final LevenshteinDetailedDistance a = new LevenshteinDetailedDistance();
    private final Integer b;

    public LevenshteinDetailedDistance() {
        this(null);
    }

    public LevenshteinDetailedDistance(Integer num) {
        if (num == null || num.intValue() >= 0) {
            this.b = num;
            return;
        }
        throw new IllegalArgumentException("Threshold must not be negative");
    }

    @Override // org.apache.commons.text.similarity.EditDistance, org.apache.commons.text.similarity.SimilarityScore
    public LevenshteinResults apply(CharSequence charSequence, CharSequence charSequence2) {
        Integer num = this.b;
        if (num != null) {
            return a(charSequence, charSequence2, num.intValue());
        }
        return a(charSequence, charSequence2);
    }

    public static LevenshteinDetailedDistance getDefaultInstance() {
        return a;
    }

    public Integer getThreshold() {
        return this.b;
    }

    private static LevenshteinResults a(CharSequence charSequence, CharSequence charSequence2, int i) {
        boolean z;
        CharSequence charSequence3;
        CharSequence charSequence4;
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        } else if (i >= 0) {
            int length = charSequence.length();
            int length2 = charSequence2.length();
            int i2 = -1;
            int i3 = 0;
            if (length == 0) {
                return length2 <= i ? new LevenshteinResults(Integer.valueOf(length2), Integer.valueOf(length2), 0, 0) : new LevenshteinResults(-1, 0, 0, 0);
            }
            if (length2 == 0) {
                return length <= i ? new LevenshteinResults(Integer.valueOf(length), 0, Integer.valueOf(length), 0) : new LevenshteinResults(-1, 0, 0, 0);
            }
            int i4 = 1;
            if (length > length2) {
                charSequence3 = charSequence;
                charSequence4 = charSequence2;
                z = true;
                length2 = charSequence.length();
                length = length2;
            } else {
                charSequence4 = charSequence;
                charSequence3 = charSequence2;
                z = false;
            }
            int i5 = length + 1;
            int[] iArr = new int[i5];
            int[] iArr2 = new int[i5];
            int[][] iArr3 = (int[][]) Array.newInstance(int.class, length2 + 1, i5);
            for (int i6 = 0; i6 <= length; i6++) {
                iArr3[0][i6] = i6;
            }
            for (int i7 = 0; i7 <= length2; i7++) {
                iArr3[i7][0] = i7;
            }
            int min = Math.min(length, i) + 1;
            for (int i8 = 0; i8 < min; i8++) {
                iArr[i8] = i8;
            }
            int i9 = Integer.MAX_VALUE;
            Arrays.fill(iArr, min, iArr.length, Integer.MAX_VALUE);
            Arrays.fill(iArr2, Integer.MAX_VALUE);
            int[] iArr4 = iArr2;
            int[] iArr5 = iArr;
            int i10 = 1;
            while (i10 <= length2) {
                char charAt = charSequence3.charAt(i10 - 1);
                iArr4[i3] = i10;
                int max = Math.max(i4, i10 - i);
                int min2 = i10 > i9 - i ? length : Math.min(length, i10 + i);
                if (max > min2) {
                    return new LevenshteinResults(Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i3), Integer.valueOf(i3));
                }
                if (max > 1) {
                    iArr4[max - 1] = i9;
                }
                while (max <= min2) {
                    int i11 = max - 1;
                    if (charSequence4.charAt(i11) == charAt) {
                        iArr4[max] = iArr5[i11];
                    } else {
                        iArr4[max] = Math.min(Math.min(iArr4[i11], iArr5[max]), iArr5[i11]) + 1;
                    }
                    iArr3[i10][max] = iArr4[max];
                    max++;
                }
                i4 = 1;
                i10++;
                i2 = -1;
                i3 = 0;
                i9 = Integer.MAX_VALUE;
                iArr4 = iArr5;
                iArr5 = iArr4;
            }
            if (iArr5[length] <= i) {
                return a(charSequence4, charSequence3, iArr3, z);
            }
            return new LevenshteinResults(-1, 0, 0, 0);
        } else {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
    }

    private static LevenshteinResults a(CharSequence charSequence, CharSequence charSequence2) {
        boolean z;
        CharSequence charSequence3;
        CharSequence charSequence4;
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i = 0;
        if (length == 0) {
            return new LevenshteinResults(Integer.valueOf(length2), Integer.valueOf(length2), 0, 0);
        }
        if (length2 == 0) {
            return new LevenshteinResults(Integer.valueOf(length), 0, Integer.valueOf(length), 0);
        }
        if (length > length2) {
            charSequence3 = charSequence;
            charSequence4 = charSequence2;
            z = true;
            length2 = charSequence.length();
            length = length2;
        } else {
            charSequence4 = charSequence;
            charSequence3 = charSequence2;
            z = false;
        }
        int i2 = length + 1;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        int[][] iArr3 = (int[][]) Array.newInstance(int.class, length2 + 1, i2);
        for (int i3 = 0; i3 <= length; i3++) {
            iArr3[0][i3] = i3;
        }
        for (int i4 = 0; i4 <= length2; i4++) {
            iArr3[i4][0] = i4;
        }
        for (int i5 = 0; i5 <= length; i5++) {
            iArr[i5] = i5;
        }
        int[] iArr4 = iArr;
        int i6 = 1;
        while (i6 <= length2) {
            char charAt = charSequence3.charAt(i6 - 1);
            iArr2[i] = i6;
            int i7 = 1;
            while (i7 <= length) {
                int i8 = i7 - 1;
                if (charSequence4.charAt(i8) != charAt) {
                    i = 1;
                }
                iArr2[i7] = Math.min(Math.min(iArr2[i8] + 1, iArr4[i7] + 1), iArr4[i8] + i);
                iArr3[i6][i7] = iArr2[i7];
                i7++;
                i = 0;
            }
            i6++;
            i = 0;
            iArr4 = iArr2;
            iArr2 = iArr4;
        }
        return a(charSequence4, charSequence3, iArr3, z);
    }

    private static LevenshteinResults a(CharSequence charSequence, CharSequence charSequence2, int[][] iArr, boolean z) {
        int i;
        int i2;
        int i3;
        boolean z2;
        int length = charSequence2.length();
        int length2 = charSequence.length();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (length >= 0 && length2 >= 0) {
            int i7 = length2 == 0 ? -1 : iArr[length][length2 - 1];
            int i8 = length == 0 ? -1 : iArr[length - 1][length2];
            int i9 = (length <= 0 || length2 <= 0) ? -1 : iArr[length - 1][length2 - 1];
            if (i7 == -1 && i8 == -1 && i9 == -1) {
                break;
            }
            int i10 = iArr[length][length2];
            if (length2 > 0 && length > 0) {
                if (charSequence.charAt(length2 - 1) == charSequence2.charAt(length - 1)) {
                    length2--;
                    length--;
                }
            }
            int i11 = i10 - 1;
            boolean z3 = true;
            if ((i11 == i7 && i10 <= i9 && i10 <= i8) || (i9 == -1 && i8 == -1)) {
                length2--;
                if (z) {
                    i = i5 + 1;
                    i2 = i4;
                    i3 = length;
                    z2 = false;
                } else {
                    i2 = i4 + 1;
                    i = i5;
                    i3 = length;
                    z2 = true;
                    z3 = false;
                }
            } else if ((i11 != i8 || i10 > i9 || i10 > i7) && !(i9 == -1 && i7 == -1)) {
                z3 = false;
                i = i5;
                i2 = i4;
                i3 = length;
                z2 = false;
            } else {
                int i12 = length - 1;
                if (z) {
                    i2 = i4 + 1;
                    i = i5;
                    i3 = i12;
                    z2 = true;
                    z3 = false;
                } else {
                    i = i5 + 1;
                    i2 = i4;
                    i3 = i12;
                    z2 = false;
                }
            }
            if (z3 || z2) {
                length = i3;
                i4 = i2;
                i5 = i;
            } else {
                i6++;
                length2--;
                length = i3 - 1;
                i4 = i2;
                i5 = i;
            }
        }
        return new LevenshteinResults(Integer.valueOf(i5 + i4 + i6), Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i6));
    }
}
