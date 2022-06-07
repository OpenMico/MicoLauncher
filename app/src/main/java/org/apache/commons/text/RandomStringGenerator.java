package org.apache.commons.text;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public final class RandomStringGenerator {
    private final int a;
    private final int b;
    private final Set<CharacterPredicate> c;
    private final TextRandomProvider d;

    private RandomStringGenerator(int i, int i2, Set<CharacterPredicate> set, TextRandomProvider textRandomProvider) {
        this.a = i;
        this.b = i2;
        this.c = set;
        this.d = textRandomProvider;
    }

    private int a(int i, int i2) {
        TextRandomProvider textRandomProvider = this.d;
        if (textRandomProvider != null) {
            return textRandomProvider.nextInt((i2 - i) + 1) + i;
        }
        return ThreadLocalRandom.current().nextInt(i, i2 + 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0045, code lost:
        if (r3 == false) goto L_0x004e;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String generate(int r9) {
        /*
            r8 = this;
            if (r9 != 0) goto L_0x0005
            java.lang.String r9 = ""
            return r9
        L_0x0005:
            r0 = 1
            r1 = 0
            if (r9 <= 0) goto L_0x000b
            r2 = r0
            goto L_0x000c
        L_0x000b:
            r2 = r1
        L_0x000c:
            java.lang.String r3 = "Length %d is smaller than zero."
            long r4 = (long) r9
            org.apache.commons.lang3.Validate.isTrue(r2, r3, r4)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r9)
        L_0x0017:
            int r9 = r8.a
            int r3 = r8.b
            int r9 = r8.a(r9, r3)
            int r3 = java.lang.Character.getType(r9)
            if (r3 == 0) goto L_0x004e
            switch(r3) {
                case 18: goto L_0x004e;
                case 19: goto L_0x004e;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.util.Set<org.apache.commons.text.CharacterPredicate> r3 = r8.c
            if (r3 == 0) goto L_0x0048
            java.util.Iterator r3 = r3.iterator()
        L_0x0030:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x0044
            java.lang.Object r6 = r3.next()
            org.apache.commons.text.CharacterPredicate r6 = (org.apache.commons.text.CharacterPredicate) r6
            boolean r6 = r6.test(r9)
            if (r6 == 0) goto L_0x0030
            r3 = r0
            goto L_0x0045
        L_0x0044:
            r3 = r1
        L_0x0045:
            if (r3 != 0) goto L_0x0048
            goto L_0x004e
        L_0x0048:
            r2.appendCodePoint(r9)
            r6 = 1
            long r4 = r4 - r6
        L_0x004e:
            r6 = 0
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 != 0) goto L_0x0017
            java.lang.String r9 = r2.toString()
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.text.RandomStringGenerator.generate(int):java.lang.String");
    }

    /* loaded from: classes5.dex */
    public static class Builder implements Builder<RandomStringGenerator> {
        public static final int DEFAULT_LENGTH = 0;
        public static final int DEFAULT_MAXIMUM_CODE_POINT = 1114111;
        public static final int DEFAULT_MINIMUM_CODE_POINT = 0;
        private int a = 0;
        private int b = DEFAULT_MAXIMUM_CODE_POINT;
        private Set<CharacterPredicate> c;
        private TextRandomProvider d;

        public Builder withinRange(int i, int i2) {
            boolean z = true;
            Validate.isTrue(i <= i2, "Minimum code point %d is larger than maximum code point %d", Integer.valueOf(i), Integer.valueOf(i2));
            Validate.isTrue(i >= 0, "Minimum code point %d is negative", i);
            if (i2 > 1114111) {
                z = false;
            }
            Validate.isTrue(z, "Value %d is larger than Character.MAX_CODE_POINT.", i2);
            this.a = i;
            this.b = i2;
            return this;
        }

        public Builder filteredBy(CharacterPredicate... characterPredicateArr) {
            if (characterPredicateArr == null || characterPredicateArr.length == 0) {
                this.c = null;
                return this;
            }
            Set<CharacterPredicate> set = this.c;
            if (set == null) {
                this.c = new HashSet();
            } else {
                set.clear();
            }
            for (CharacterPredicate characterPredicate : characterPredicateArr) {
                this.c.add(characterPredicate);
            }
            return this;
        }

        public Builder usingRandom(TextRandomProvider textRandomProvider) {
            this.d = textRandomProvider;
            return this;
        }

        @Override // org.apache.commons.text.Builder
        public RandomStringGenerator build() {
            return new RandomStringGenerator(this.a, this.b, this.c, this.d);
        }
    }
}
