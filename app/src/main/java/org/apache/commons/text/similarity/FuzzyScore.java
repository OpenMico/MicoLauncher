package org.apache.commons.text.similarity;

import java.util.Locale;

/* loaded from: classes5.dex */
public class FuzzyScore {
    private final Locale a;

    public FuzzyScore(Locale locale) {
        if (locale != null) {
            this.a = locale;
            return;
        }
        throw new IllegalArgumentException("Locale must not be null");
    }

    public Integer fuzzyScore(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        String lowerCase = charSequence.toString().toLowerCase(this.a);
        String lowerCase2 = charSequence2.toString().toLowerCase(this.a);
        int i = Integer.MIN_VALUE;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < lowerCase2.length()) {
            char charAt = lowerCase2.charAt(i2);
            int i5 = i3;
            boolean z = false;
            while (i4 < lowerCase.length() && !z) {
                if (charAt == lowerCase.charAt(i4)) {
                    i5++;
                    if (i + 1 == i4) {
                        i5 += 2;
                    }
                    z = true;
                    i = i4;
                }
                i4++;
            }
            i2++;
            i3 = i5;
            i = i;
        }
        return Integer.valueOf(i3);
    }

    public Locale getLocale() {
        return this.a;
    }
}
