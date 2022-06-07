package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/* compiled from: TrieParser.java */
@GwtCompatible
/* loaded from: classes2.dex */
final class a {
    private static final Joiner a = Joiner.on("");

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ImmutableMap<String, PublicSuffixType> a(CharSequence charSequence) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            i += a(Lists.newLinkedList(), charSequence, i, builder);
        }
        return builder.build();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int a(java.util.List<java.lang.CharSequence> r9, java.lang.CharSequence r10, int r11, com.google.common.collect.ImmutableMap.Builder<java.lang.String, com.google.thirdparty.publicsuffix.PublicSuffixType> r12) {
        /*
            int r0 = r10.length()
            r1 = 0
            r2 = r11
            r3 = r1
        L_0x0007:
            r4 = 58
            r5 = 33
            r6 = 44
            r7 = 63
            if (r2 >= r0) goto L_0x0025
            char r3 = r10.charAt(r2)
            r8 = 38
            if (r3 == r8) goto L_0x0025
            if (r3 == r7) goto L_0x0025
            if (r3 == r5) goto L_0x0025
            if (r3 == r4) goto L_0x0025
            if (r3 != r6) goto L_0x0022
            goto L_0x0025
        L_0x0022:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0025:
            java.lang.CharSequence r8 = r10.subSequence(r11, r2)
            java.lang.CharSequence r8 = b(r8)
            r9.add(r1, r8)
            if (r3 == r5) goto L_0x0038
            if (r3 == r7) goto L_0x0038
            if (r3 == r4) goto L_0x0038
            if (r3 != r6) goto L_0x004b
        L_0x0038:
            com.google.common.base.Joiner r4 = com.google.thirdparty.publicsuffix.a.a
            java.lang.String r4 = r4.join(r9)
            int r5 = r4.length()
            if (r5 <= 0) goto L_0x004b
            com.google.thirdparty.publicsuffix.PublicSuffixType r5 = com.google.thirdparty.publicsuffix.PublicSuffixType.a(r3)
            r12.put(r4, r5)
        L_0x004b:
            int r2 = r2 + 1
            if (r3 == r7) goto L_0x0066
            if (r3 == r6) goto L_0x0066
        L_0x0051:
            if (r2 >= r0) goto L_0x0066
            int r3 = a(r9, r10, r2, r12)
            int r2 = r2 + r3
            char r3 = r10.charAt(r2)
            if (r3 == r7) goto L_0x0064
            char r3 = r10.charAt(r2)
            if (r3 != r6) goto L_0x0051
        L_0x0064:
            int r2 = r2 + 1
        L_0x0066:
            r9.remove(r1)
            int r2 = r2 - r11
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.thirdparty.publicsuffix.a.a(java.util.List, java.lang.CharSequence, int, com.google.common.collect.ImmutableMap$Builder):int");
    }

    private static CharSequence b(CharSequence charSequence) {
        return new StringBuilder(charSequence).reverse();
    }
}
