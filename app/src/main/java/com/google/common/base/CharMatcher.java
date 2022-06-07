package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;
import java.util.BitSet;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class CharMatcher implements Predicate<Character> {
    @Beta
    @Deprecated
    public static final CharMatcher WHITESPACE = whitespace();
    @Beta
    @Deprecated
    public static final CharMatcher BREAKING_WHITESPACE = breakingWhitespace();
    @Beta
    @Deprecated
    public static final CharMatcher ASCII = ascii();
    @Beta
    @Deprecated
    public static final CharMatcher DIGIT = digit();
    @Beta
    @Deprecated
    public static final CharMatcher JAVA_DIGIT = javaDigit();
    @Beta
    @Deprecated
    public static final CharMatcher JAVA_LETTER = javaLetter();
    @Beta
    @Deprecated
    public static final CharMatcher JAVA_LETTER_OR_DIGIT = javaLetterOrDigit();
    @Beta
    @Deprecated
    public static final CharMatcher JAVA_UPPER_CASE = javaUpperCase();
    @Beta
    @Deprecated
    public static final CharMatcher JAVA_LOWER_CASE = javaLowerCase();
    @Beta
    @Deprecated
    public static final CharMatcher JAVA_ISO_CONTROL = javaIsoControl();
    @Beta
    @Deprecated
    public static final CharMatcher INVISIBLE = invisible();
    @Beta
    @Deprecated
    public static final CharMatcher SINGLE_WIDTH = singleWidth();
    @Beta
    @Deprecated
    public static final CharMatcher ANY = any();
    @Beta
    @Deprecated
    public static final CharMatcher NONE = none();

    @GwtIncompatible
    private static boolean a(int i2, int i3) {
        return i2 <= 1023 && i3 > (i2 * 4) * 16;
    }

    public abstract boolean matches(char c2);

    public static CharMatcher any() {
        return b.a;
    }

    public static CharMatcher none() {
        return x.a;
    }

    public static CharMatcher whitespace() {
        return ab.b;
    }

    public static CharMatcher breakingWhitespace() {
        return f.a;
    }

    public static CharMatcher ascii() {
        return d.a;
    }

    @Deprecated
    public static CharMatcher digit() {
        return g.a;
    }

    @Deprecated
    public static CharMatcher javaDigit() {
        return o.a;
    }

    @Deprecated
    public static CharMatcher javaLetter() {
        return q.a;
    }

    @Deprecated
    public static CharMatcher javaLetterOrDigit() {
        return r.a;
    }

    @Deprecated
    public static CharMatcher javaUpperCase() {
        return t.a;
    }

    @Deprecated
    public static CharMatcher javaLowerCase() {
        return s.a;
    }

    public static CharMatcher javaIsoControl() {
        return p.a;
    }

    @Deprecated
    public static CharMatcher invisible() {
        return k.a;
    }

    @Deprecated
    public static CharMatcher singleWidth() {
        return aa.a;
    }

    public static CharMatcher is(char c2) {
        return new l(c2);
    }

    public static CharMatcher isNot(char c2) {
        return new n(c2);
    }

    public static CharMatcher anyOf(CharSequence charSequence) {
        switch (charSequence.length()) {
            case 0:
                return none();
            case 1:
                return is(charSequence.charAt(0));
            case 2:
                return a(charSequence.charAt(0), charSequence.charAt(1));
            default:
                return new c(charSequence);
        }
    }

    public static CharMatcher noneOf(CharSequence charSequence) {
        return anyOf(charSequence).negate();
    }

    public static CharMatcher inRange(char c2, char c3) {
        return new j(c2, c3);
    }

    public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
        return predicate instanceof CharMatcher ? (CharMatcher) predicate : new i(predicate);
    }

    protected CharMatcher() {
    }

    public CharMatcher negate() {
        return new v(this);
    }

    public CharMatcher and(CharMatcher charMatcher) {
        return new a(this, charMatcher);
    }

    public CharMatcher or(CharMatcher charMatcher) {
        return new y(this, charMatcher);
    }

    public CharMatcher precomputed() {
        return j.a(this);
    }

    @GwtIncompatible
    public CharMatcher a() {
        String str;
        BitSet bitSet = new BitSet();
        a(bitSet);
        int cardinality = bitSet.cardinality();
        if (cardinality * 2 <= 65536) {
            return a(cardinality, bitSet, toString());
        }
        bitSet.flip(0, 65536);
        int i2 = 65536 - cardinality;
        final String charMatcher = toString();
        if (charMatcher.endsWith(".negate()")) {
            str = charMatcher.substring(0, charMatcher.length() - 9);
        } else {
            str = charMatcher + ".negate()";
        }
        return new w(a(i2, bitSet, str)) { // from class: com.google.common.base.CharMatcher.1
            @Override // com.google.common.base.CharMatcher.v, com.google.common.base.CharMatcher
            public String toString() {
                return charMatcher;
            }
        };
    }

    @GwtIncompatible
    private static CharMatcher a(int i2, BitSet bitSet, String str) {
        switch (i2) {
            case 0:
                return none();
            case 1:
                return is((char) bitSet.nextSetBit(0));
            case 2:
                char nextSetBit = (char) bitSet.nextSetBit(0);
                return a(nextSetBit, (char) bitSet.nextSetBit(nextSetBit + 1));
            default:
                return a(i2, bitSet.length()) ? l.a(bitSet, str) : new e(bitSet, str);
        }
    }

    @GwtIncompatible
    void a(BitSet bitSet) {
        for (int i2 = 65535; i2 >= 0; i2--) {
            if (matches((char) i2)) {
                bitSet.set(i2);
            }
        }
    }

    public boolean matchesAnyOf(CharSequence charSequence) {
        return !matchesNoneOf(charSequence);
    }

    public boolean matchesAllOf(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!matches(charSequence.charAt(length))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence charSequence) {
        return indexIn(charSequence) == -1;
    }

    public int indexIn(CharSequence charSequence) {
        return indexIn(charSequence, 0);
    }

    public int indexIn(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        Preconditions.checkPositionIndex(i2, length);
        while (i2 < length) {
            if (matches(charSequence.charAt(i2))) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public int lastIndexIn(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (matches(charSequence.charAt(length))) {
                return length;
            }
        }
        return -1;
    }

    public int countIn(CharSequence charSequence) {
        int i2 = 0;
        for (int i3 = 0; i3 < charSequence.length(); i3++) {
            if (matches(charSequence.charAt(i3))) {
                i2++;
            }
        }
        return i2;
    }

    public String removeFrom(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        int indexIn = indexIn(charSequence2);
        if (indexIn == -1) {
            return charSequence2;
        }
        char[] charArray = charSequence2.toCharArray();
        int i2 = 1;
        while (true) {
            indexIn++;
            while (indexIn != charArray.length) {
                if (matches(charArray[indexIn])) {
                    break;
                }
                charArray[indexIn - i2] = charArray[indexIn];
                indexIn++;
            }
            return new String(charArray, 0, indexIn - i2);
            i2++;
        }
    }

    public String retainFrom(CharSequence charSequence) {
        return negate().removeFrom(charSequence);
    }

    public String replaceFrom(CharSequence charSequence, char c2) {
        String charSequence2 = charSequence.toString();
        int indexIn = indexIn(charSequence2);
        if (indexIn == -1) {
            return charSequence2;
        }
        char[] charArray = charSequence2.toCharArray();
        charArray[indexIn] = c2;
        while (true) {
            indexIn++;
            if (indexIn >= charArray.length) {
                return new String(charArray);
            }
            if (matches(charArray[indexIn])) {
                charArray[indexIn] = c2;
            }
        }
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence2.length();
        if (length == 0) {
            return removeFrom(charSequence);
        }
        int i2 = 0;
        if (length == 1) {
            return replaceFrom(charSequence, charSequence2.charAt(0));
        }
        String charSequence3 = charSequence.toString();
        int indexIn = indexIn(charSequence3);
        if (indexIn == -1) {
            return charSequence3;
        }
        int length2 = charSequence3.length();
        StringBuilder sb = new StringBuilder(((length2 * 3) / 2) + 16);
        do {
            sb.append((CharSequence) charSequence3, i2, indexIn);
            sb.append(charSequence2);
            i2 = indexIn + 1;
            indexIn = indexIn(charSequence3, i2);
        } while (indexIn != -1);
        sb.append((CharSequence) charSequence3, i2, length2);
        return sb.toString();
    }

    public String trimFrom(CharSequence charSequence) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length && matches(charSequence.charAt(i2))) {
            i2++;
        }
        int i3 = length - 1;
        while (i3 > i2 && matches(charSequence.charAt(i3))) {
            i3--;
        }
        return charSequence.subSequence(i2, i3 + 1).toString();
    }

    public String trimLeadingFrom(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!matches(charSequence.charAt(i2))) {
                return charSequence.subSequence(i2, length).toString();
            }
        }
        return "";
    }

    public String trimTrailingFrom(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!matches(charSequence.charAt(length))) {
                return charSequence.subSequence(0, length + 1).toString();
            }
        }
        return "";
    }

    public String collapseFrom(CharSequence charSequence, char c2) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            if (matches(charAt)) {
                if (charAt != c2 || (i2 != length - 1 && matches(charSequence.charAt(i2 + 1)))) {
                    StringBuilder sb = new StringBuilder(length);
                    sb.append(charSequence, 0, i2);
                    sb.append(c2);
                    return a(charSequence, i2 + 1, length, c2, sb, true);
                }
                i2++;
            }
            i2++;
        }
        return charSequence.toString();
    }

    public String trimAndCollapseFrom(CharSequence charSequence, char c2) {
        int length = charSequence.length();
        int i2 = length - 1;
        int i3 = 0;
        while (i3 < length && matches(charSequence.charAt(i3))) {
            i3++;
        }
        int i4 = i2;
        while (i4 > i3 && matches(charSequence.charAt(i4))) {
            i4--;
        }
        if (i3 == 0 && i4 == i2) {
            return collapseFrom(charSequence, c2);
        }
        int i5 = i4 + 1;
        return a(charSequence, i3, i5, c2, new StringBuilder(i5 - i3), false);
    }

    private String a(CharSequence charSequence, int i2, int i3, char c2, StringBuilder sb, boolean z2) {
        while (i2 < i3) {
            char charAt = charSequence.charAt(i2);
            if (!matches(charAt)) {
                sb.append(charAt);
                z2 = false;
            } else if (!z2) {
                sb.append(c2);
                z2 = true;
            }
            i2++;
        }
        return sb.toString();
    }

    @Deprecated
    public boolean apply(Character ch) {
        return matches(ch.charValue());
    }

    public String toString() {
        return super.toString();
    }

    public static String b(char c2) {
        char[] cArr = {'\\', 'u', 0, 0, 0, 0};
        for (int i2 = 0; i2 < 4; i2++) {
            cArr[5 - i2] = "0123456789ABCDEF".charAt(c2 & 15);
            c2 = (char) (c2 >> 4);
        }
        return String.copyValueOf(cArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class h extends CharMatcher {
        @Override // com.google.common.base.CharMatcher
        public final CharMatcher precomputed() {
            return this;
        }

        h() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return new w(this);
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class u extends h {
        private final String a;

        public u(String str) {
            this.a = (String) Preconditions.checkNotNull(str);
        }

        @Override // com.google.common.base.CharMatcher
        public final String toString() {
            return this.a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class w extends v {
        @Override // com.google.common.base.CharMatcher
        public final CharMatcher precomputed() {
            return this;
        }

        w(CharMatcher charMatcher) {
            super(charMatcher);
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static final class e extends u {
        private final BitSet a;

        private e(BitSet bitSet, String str) {
            super(str);
            this.a = bitSet.length() + 64 < bitSet.size() ? (BitSet) bitSet.clone() : bitSet;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return this.a.get(c);
        }

        @Override // com.google.common.base.CharMatcher
        void a(BitSet bitSet) {
            bitSet.or(this.a);
        }
    }

    /* loaded from: classes2.dex */
    public static final class b extends u {
        static final b a = new b();

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return true;
        }

        private b() {
            super("CharMatcher.any()");
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence) {
            return charSequence.length() == 0 ? -1 : 0;
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence, int i) {
            int length = charSequence.length();
            Preconditions.checkPositionIndex(i, length);
            if (i == length) {
                return -1;
            }
            return i;
        }

        @Override // com.google.common.base.CharMatcher
        public int lastIndexIn(CharSequence charSequence) {
            return charSequence.length() - 1;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesAllOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesNoneOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        @Override // com.google.common.base.CharMatcher
        public String removeFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, char c) {
            char[] cArr = new char[charSequence.length()];
            Arrays.fill(cArr, c);
            return new String(cArr);
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            StringBuilder sb = new StringBuilder(charSequence.length() * charSequence2.length());
            for (int i = 0; i < charSequence.length(); i++) {
                sb.append(charSequence2);
            }
            return sb.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.length() == 0 ? "" : String.valueOf(c);
        }

        @Override // com.google.common.base.CharMatcher
        public String trimFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        @Override // com.google.common.base.CharMatcher
        public int countIn(CharSequence charSequence) {
            return charSequence.length();
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher and(CharMatcher charMatcher) {
            return (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher or(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        @Override // com.google.common.base.CharMatcher.h, com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return none();
        }
    }

    /* loaded from: classes2.dex */
    public static final class x extends u {
        static final x a = new x();

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return false;
        }

        private x() {
            super("CharMatcher.none()");
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence, int i) {
            Preconditions.checkPositionIndex(i, charSequence.length());
            return -1;
        }

        @Override // com.google.common.base.CharMatcher
        public int lastIndexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesAllOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesNoneOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        @Override // com.google.common.base.CharMatcher
        public String removeFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            Preconditions.checkNotNull(charSequence2);
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String trimFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String trimLeadingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String trimTrailingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public int countIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return 0;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher and(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher or(CharMatcher charMatcher) {
            return (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher.h, com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return any();
        }
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static final class ab extends u {
        static final int a = Integer.numberOfLeadingZeros(31);
        static final ab b = new ab();

        ab() {
            super("CharMatcher.whitespace()");
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001  \f\u2009\u3000\u2004\u3000\u3000\u2028\n \u3000".charAt((48906 * c) >>> a) == c;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            for (int i = 0; i < 32; i++) {
                bitSet.set("\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001  \f\u2009\u3000\u2004\u3000\u3000\u2028\n \u3000".charAt(i));
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class f extends CharMatcher {
        static final CharMatcher a = new f();

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            if (!(c == ' ' || c == 133 || c == 5760)) {
                if (c == 8199) {
                    return false;
                }
                if (!(c == 8287 || c == 12288)) {
                    switch (c) {
                        case '\t':
                        case '\n':
                        case 11:
                        case '\f':
                        case '\r':
                            break;
                        default:
                            switch (c) {
                                case 8232:
                                case 8233:
                                    break;
                                default:
                                    return c >= 8192 && c <= 8202;
                            }
                    }
                }
            }
            return true;
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.breakingWhitespace()";
        }

        private f() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }
    }

    /* loaded from: classes2.dex */
    public static final class d extends u {
        static final d a = new d();

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return c <= 127;
        }

        d() {
            super("CharMatcher.ascii()");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class z extends CharMatcher {
        private final String a;
        private final char[] b;
        private final char[] c;

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        z(String str, char[] cArr, char[] cArr2) {
            this.a = str;
            this.b = cArr;
            this.c = cArr2;
            Preconditions.checkArgument(cArr.length == cArr2.length);
            int i = 0;
            while (i < cArr.length) {
                Preconditions.checkArgument(cArr[i] <= cArr2[i]);
                int i2 = i + 1;
                if (i2 < cArr.length) {
                    Preconditions.checkArgument(cArr2[i] < cArr[i2]);
                }
                i = i2;
            }
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            int binarySearch = Arrays.binarySearch(this.b, c);
            if (binarySearch >= 0) {
                return true;
            }
            int i = (~binarySearch) - 1;
            return i >= 0 && c <= this.c[i];
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return this.a;
        }
    }

    /* loaded from: classes2.dex */
    public static final class g extends z {
        static final g a = new g();

        private static char[] b() {
            return "0٠۰߀०০੦૦୦௦౦೦൦෦๐໐༠၀႐០᠐᥆᧐᪀᪐᭐᮰᱀᱐꘠꣐꤀꧐꧰꩐꯰０".toCharArray();
        }

        private static char[] c() {
            char[] cArr = new char[37];
            for (int i = 0; i < 37; i++) {
                cArr[i] = (char) ("0٠۰߀०০੦૦୦௦౦೦൦෦๐໐༠၀႐០᠐᥆᧐᪀᪐᭐᮰᱀᱐꘠꣐꤀꧐꧰꩐꯰０".charAt(i) + '\t');
            }
            return cArr;
        }

        private g() {
            super("CharMatcher.digit()", b(), c());
        }
    }

    /* loaded from: classes2.dex */
    public static final class o extends CharMatcher {
        static final o a = new o();

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaDigit()";
        }

        private o() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return Character.isDigit(c);
        }
    }

    /* loaded from: classes2.dex */
    public static final class q extends CharMatcher {
        static final q a = new q();

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaLetter()";
        }

        private q() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return Character.isLetter(c);
        }
    }

    /* loaded from: classes2.dex */
    public static final class r extends CharMatcher {
        static final r a = new r();

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }

        private r() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
        }
    }

    /* loaded from: classes2.dex */
    public static final class t extends CharMatcher {
        static final t a = new t();

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }

        private t() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return Character.isUpperCase(c);
        }
    }

    /* loaded from: classes2.dex */
    public static final class s extends CharMatcher {
        static final s a = new s();

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }

        private s() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return Character.isLowerCase(c);
        }
    }

    /* loaded from: classes2.dex */
    public static final class p extends u {
        static final p a = new p();

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return c <= 31 || (c >= 127 && c <= 159);
        }

        private p() {
            super("CharMatcher.javaIsoControl()");
        }
    }

    /* loaded from: classes2.dex */
    public static final class k extends z {
        static final k a = new k();

        private k() {
            super("CharMatcher.invisible()", "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u08e2\u1680\u180e\u2000\u2028\u205f\u2066\u3000\ud800\ufeff\ufff9".toCharArray(), "  \u00ad\u0605\u061c\u06dd\u070f\u08e2\u1680\u180e\u200f \u2064\u206f\u3000\uf8ff\ufeff\ufffb".toCharArray());
        }
    }

    /* loaded from: classes2.dex */
    public static final class aa extends z {
        static final aa a = new aa();

        private aa() {
            super("CharMatcher.singleWidth()", "\u0000־א׳\u0600ݐ\u0e00Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ\u0e7f₯℺\ufdff\ufeffￜ".toCharArray());
        }
    }

    /* loaded from: classes2.dex */
    public static class v extends CharMatcher {
        final CharMatcher c;

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        v(CharMatcher charMatcher) {
            this.c = (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return !this.c.matches(c);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesAllOf(CharSequence charSequence) {
            return this.c.matchesNoneOf(charSequence);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesNoneOf(CharSequence charSequence) {
            return this.c.matchesAllOf(charSequence);
        }

        @Override // com.google.common.base.CharMatcher
        public int countIn(CharSequence charSequence) {
            return charSequence.length() - this.c.countIn(charSequence);
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.c.a(bitSet2);
            bitSet2.flip(0, 65536);
            bitSet.or(bitSet2);
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return this.c;
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return this.c + ".negate()";
        }
    }

    /* loaded from: classes2.dex */
    public static final class a extends CharMatcher {
        final CharMatcher a;
        final CharMatcher b;

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        a(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.a = (CharMatcher) Preconditions.checkNotNull(charMatcher);
            this.b = (CharMatcher) Preconditions.checkNotNull(charMatcher2);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return this.a.matches(c) && this.b.matches(c);
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.a.a(bitSet2);
            BitSet bitSet3 = new BitSet();
            this.b.a(bitSet3);
            bitSet2.and(bitSet3);
            bitSet.or(bitSet2);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.and(" + this.a + ", " + this.b + ")";
        }
    }

    /* loaded from: classes2.dex */
    public static final class y extends CharMatcher {
        final CharMatcher a;
        final CharMatcher b;

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        y(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.a = (CharMatcher) Preconditions.checkNotNull(charMatcher);
            this.b = (CharMatcher) Preconditions.checkNotNull(charMatcher2);
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            this.a.a(bitSet);
            this.b.a(bitSet);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return this.a.matches(c) || this.b.matches(c);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.or(" + this.a + ", " + this.b + ")";
        }
    }

    /* loaded from: classes2.dex */
    public static final class l extends h {
        private final char a;

        l(char c) {
            this.a = c;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return c == this.a;
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString().replace(this.a, c);
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.a) ? this : none();
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.a) ? charMatcher : super.or(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher.h, com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return isNot(this.a);
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            bitSet.set(this.a);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.is('" + CharMatcher.b(this.a) + "')";
        }
    }

    /* loaded from: classes2.dex */
    public static final class n extends h {
        private final char a;

        n(char c) {
            this.a = c;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return c != this.a;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.a) ? super.and(charMatcher) : charMatcher;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.a) ? any() : this;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            bitSet.set(0, this.a);
            bitSet.set(this.a + 1, 65536);
        }

        @Override // com.google.common.base.CharMatcher.h, com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return is(this.a);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.isNot('" + CharMatcher.b(this.a) + "')";
        }
    }

    private static m a(char c2, char c3) {
        return new m(c2, c3);
    }

    /* loaded from: classes2.dex */
    public static final class m extends h {
        private final char a;
        private final char b;

        m(char c, char c2) {
            this.a = c;
            this.b = c2;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return c == this.a || c == this.b;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            bitSet.set(this.a);
            bitSet.set(this.b);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.anyOf(\"" + CharMatcher.b(this.a) + CharMatcher.b(this.b) + "\")";
        }
    }

    /* loaded from: classes2.dex */
    public static final class c extends CharMatcher {
        private final char[] a;

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return CharMatcher.super.apply(ch);
        }

        public c(CharSequence charSequence) {
            this.a = charSequence.toString().toCharArray();
            Arrays.sort(this.a);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return Arrays.binarySearch(this.a, c) >= 0;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            for (char c : this.a) {
                bitSet.set(c);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            StringBuilder sb = new StringBuilder("CharMatcher.anyOf(\"");
            for (char c : this.a) {
                sb.append(CharMatcher.b(c));
            }
            sb.append("\")");
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class j extends h {
        private final char a;
        private final char b;

        j(char c, char c2) {
            Preconditions.checkArgument(c2 >= c);
            this.a = c;
            this.b = c2;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return this.a <= c && c <= this.b;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        void a(BitSet bitSet) {
            bitSet.set(this.a, this.b + 1);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.inRange('" + CharMatcher.b(this.a) + "', '" + CharMatcher.b(this.b) + "')";
        }
    }

    /* loaded from: classes2.dex */
    private static final class i extends CharMatcher {
        private final Predicate<? super Character> a;

        i(Predicate<? super Character> predicate) {
            this.a = (Predicate) Preconditions.checkNotNull(predicate);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return this.a.apply(Character.valueOf(c));
        }

        @Override // com.google.common.base.CharMatcher
        public boolean apply(Character ch) {
            return this.a.apply(Preconditions.checkNotNull(ch));
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.forPredicate(" + this.a + ")";
        }
    }
}
