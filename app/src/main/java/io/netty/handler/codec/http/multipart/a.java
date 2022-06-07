package io.netty.handler.codec.http.multipart;

import java.io.Serializable;
import java.util.Comparator;

/* compiled from: CaseIgnoringComparator.java */
/* loaded from: classes4.dex */
final class a implements Serializable, Comparator<CharSequence> {
    static final a a = new a();
    private static final long serialVersionUID = 4582133183775373862L;

    private a() {
    }

    /* renamed from: a */
    public int compare(CharSequence charSequence, CharSequence charSequence2) {
        char upperCase;
        char upperCase2;
        char lowerCase;
        char lowerCase2;
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int min = Math.min(length, length2);
        for (int i = 0; i < min; i++) {
            char charAt = charSequence.charAt(i);
            char charAt2 = charSequence2.charAt(i);
            if (!(charAt == charAt2 || (upperCase = Character.toUpperCase(charAt)) == (upperCase2 = Character.toUpperCase(charAt2)) || (lowerCase = Character.toLowerCase(upperCase)) == (lowerCase2 = Character.toLowerCase(upperCase2)))) {
                return lowerCase - lowerCase2;
            }
        }
        return length - length2;
    }

    private Object readResolve() {
        return a;
    }
}
