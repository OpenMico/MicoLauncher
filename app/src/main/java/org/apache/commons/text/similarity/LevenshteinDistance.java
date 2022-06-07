package org.apache.commons.text.similarity;

import java.util.Arrays;

/* loaded from: classes5.dex */
public class LevenshteinDistance implements EditDistance<Integer> {
    private static final LevenshteinDistance a = new LevenshteinDistance();
    private final Integer b;

    public LevenshteinDistance() {
        this(null);
    }

    public LevenshteinDistance(Integer num) {
        if (num == null || num.intValue() >= 0) {
            this.b = num;
            return;
        }
        throw new IllegalArgumentException("Threshold must not be negative");
    }

    @Override // org.apache.commons.text.similarity.EditDistance, org.apache.commons.text.similarity.SimilarityScore
    public Integer apply(CharSequence charSequence, CharSequence charSequence2) {
        Integer num = this.b;
        if (num != null) {
            return Integer.valueOf(a(charSequence, charSequence2, num.intValue()));
        }
        return Integer.valueOf(a(charSequence, charSequence2));
    }

    public static LevenshteinDistance getDefaultInstance() {
        return a;
    }

    public Integer getThreshold() {
        return this.b;
    }

    private static int a(CharSequence charSequence, CharSequence charSequence2, int i) {
        CharSequence charSequence3;
        int i2;
        CharSequence charSequence4;
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        } else if (i >= 0) {
            int length = charSequence.length();
            int length2 = charSequence2.length();
            if (length == 0) {
                if (length2 <= i) {
                    return length2;
                }
                return -1;
            } else if (length2 != 0) {
                if (length > length2) {
                    charSequence3 = charSequence2;
                    i2 = charSequence.length();
                    length = length2;
                    charSequence4 = charSequence;
                } else {
                    charSequence3 = charSequence;
                    i2 = length2;
                    charSequence4 = charSequence2;
                }
                int i3 = length + 1;
                int[] iArr = new int[i3];
                int[] iArr2 = new int[i3];
                int min = Math.min(length, i) + 1;
                char c = 0;
                for (int i4 = 0; i4 < min; i4++) {
                    iArr[i4] = i4;
                }
                int i5 = Integer.MAX_VALUE;
                Arrays.fill(iArr, min, iArr.length, Integer.MAX_VALUE);
                Arrays.fill(iArr2, Integer.MAX_VALUE);
                int[] iArr3 = iArr2;
                int i6 = 1;
                while (i6 <= i2) {
                    char charAt = charSequence4.charAt(i6 - 1);
                    iArr3[c] = i6;
                    int max = Math.max(1, i6 - i);
                    int min2 = i6 > i5 - i ? length : Math.min(length, i6 + i);
                    if (max > min2) {
                        return -1;
                    }
                    if (max > 1) {
                        iArr3[max - 1] = i5;
                    }
                    while (max <= min2) {
                        int i7 = max - 1;
                        if (charSequence3.charAt(i7) == charAt) {
                            iArr3[max] = iArr[i7];
                        } else {
                            iArr3[max] = Math.min(Math.min(iArr3[i7], iArr[max]), iArr[i7]) + 1;
                        }
                        max++;
                    }
                    i6++;
                    c = 0;
                    i5 = Integer.MAX_VALUE;
                    iArr3 = iArr;
                    iArr = iArr3;
                }
                if (iArr[length] <= i) {
                    return iArr[length];
                }
                return -1;
            } else if (length <= i) {
                return length;
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
    }

    private static int a(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        if (length == 0) {
            return length2;
        }
        if (length2 == 0) {
            return length;
        }
        if (length > length2) {
            length2 = charSequence.length();
            length = length2;
        } else {
            charSequence2 = charSequence;
            charSequence = charSequence2;
        }
        int[] iArr = new int[length + 1];
        for (int i = 0; i <= length; i++) {
            iArr[i] = i;
        }
        for (int i2 = 1; i2 <= length2; i2++) {
            int i3 = iArr[0];
            char charAt = charSequence.charAt(i2 - 1);
            iArr[0] = i2;
            int i4 = i3;
            int i5 = 1;
            while (i5 <= length) {
                int i6 = iArr[i5];
                int i7 = i5 - 1;
                iArr[i5] = Math.min(Math.min(iArr[i7] + 1, iArr[i5] + 1), i4 + (charSequence2.charAt(i7) == charAt ? 0 : 1));
                i5++;
                i4 = i6;
            }
        }
        return iArr[length];
    }
}
