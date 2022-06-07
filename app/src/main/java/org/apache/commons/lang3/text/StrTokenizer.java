package org.apache.commons.lang3.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class StrTokenizer implements Cloneable, ListIterator<String> {
    private static final StrTokenizer a = new StrTokenizer();
    private static final StrTokenizer b = new StrTokenizer();
    private char[] c;
    private String[] d;
    private int e;
    private StrMatcher f;
    private StrMatcher g;
    private StrMatcher h;
    private StrMatcher i;
    private boolean j;
    private boolean k;

    static {
        a.setDelimiterMatcher(StrMatcher.commaMatcher());
        a.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        a.setIgnoredMatcher(StrMatcher.noneMatcher());
        a.setTrimmerMatcher(StrMatcher.trimMatcher());
        a.setEmptyTokenAsNull(false);
        a.setIgnoreEmptyTokens(false);
        b.setDelimiterMatcher(StrMatcher.tabMatcher());
        b.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        b.setIgnoredMatcher(StrMatcher.noneMatcher());
        b.setTrimmerMatcher(StrMatcher.trimMatcher());
        b.setEmptyTokenAsNull(false);
        b.setIgnoreEmptyTokens(false);
    }

    private static StrTokenizer b() {
        return (StrTokenizer) a.clone();
    }

    public static StrTokenizer getCSVInstance() {
        return b();
    }

    public static StrTokenizer getCSVInstance(String str) {
        StrTokenizer b2 = b();
        b2.reset(str);
        return b2;
    }

    public static StrTokenizer getCSVInstance(char[] cArr) {
        StrTokenizer b2 = b();
        b2.reset(cArr);
        return b2;
    }

    private static StrTokenizer c() {
        return (StrTokenizer) b.clone();
    }

    public static StrTokenizer getTSVInstance() {
        return c();
    }

    public static StrTokenizer getTSVInstance(String str) {
        StrTokenizer c = c();
        c.reset(str);
        return c;
    }

    public static StrTokenizer getTSVInstance(char[] cArr) {
        StrTokenizer c = c();
        c.reset(cArr);
        return c;
    }

    public StrTokenizer() {
        this.f = StrMatcher.splitMatcher();
        this.g = StrMatcher.noneMatcher();
        this.h = StrMatcher.noneMatcher();
        this.i = StrMatcher.noneMatcher();
        this.j = false;
        this.k = true;
        this.c = null;
    }

    public StrTokenizer(String str) {
        this.f = StrMatcher.splitMatcher();
        this.g = StrMatcher.noneMatcher();
        this.h = StrMatcher.noneMatcher();
        this.i = StrMatcher.noneMatcher();
        this.j = false;
        this.k = true;
        if (str != null) {
            this.c = str.toCharArray();
        } else {
            this.c = null;
        }
    }

    public StrTokenizer(String str, char c) {
        this(str);
        setDelimiterChar(c);
    }

    public StrTokenizer(String str, String str2) {
        this(str);
        setDelimiterString(str2);
    }

    public StrTokenizer(String str, StrMatcher strMatcher) {
        this(str);
        setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(String str, char c, char c2) {
        this(str, c);
        setQuoteChar(c2);
    }

    public StrTokenizer(String str, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(str, strMatcher);
        setQuoteMatcher(strMatcher2);
    }

    public StrTokenizer(char[] cArr) {
        this.f = StrMatcher.splitMatcher();
        this.g = StrMatcher.noneMatcher();
        this.h = StrMatcher.noneMatcher();
        this.i = StrMatcher.noneMatcher();
        this.j = false;
        this.k = true;
        this.c = ArrayUtils.clone(cArr);
    }

    public StrTokenizer(char[] cArr, char c) {
        this(cArr);
        setDelimiterChar(c);
    }

    public StrTokenizer(char[] cArr, String str) {
        this(cArr);
        setDelimiterString(str);
    }

    public StrTokenizer(char[] cArr, StrMatcher strMatcher) {
        this(cArr);
        setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(char[] cArr, char c, char c2) {
        this(cArr, c);
        setQuoteChar(c2);
    }

    public StrTokenizer(char[] cArr, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(cArr, strMatcher);
        setQuoteMatcher(strMatcher2);
    }

    public int size() {
        d();
        return this.d.length;
    }

    public String nextToken() {
        if (!hasNext()) {
            return null;
        }
        String[] strArr = this.d;
        int i = this.e;
        this.e = i + 1;
        return strArr[i];
    }

    public String previousToken() {
        if (!hasPrevious()) {
            return null;
        }
        String[] strArr = this.d;
        int i = this.e - 1;
        this.e = i;
        return strArr[i];
    }

    public String[] getTokenArray() {
        d();
        return (String[]) this.d.clone();
    }

    public List<String> getTokenList() {
        d();
        ArrayList arrayList = new ArrayList(this.d.length);
        for (String str : this.d) {
            arrayList.add(str);
        }
        return arrayList;
    }

    public StrTokenizer reset() {
        this.e = 0;
        this.d = null;
        return this;
    }

    public StrTokenizer reset(String str) {
        reset();
        if (str != null) {
            this.c = str.toCharArray();
        } else {
            this.c = null;
        }
        return this;
    }

    public StrTokenizer reset(char[] cArr) {
        reset();
        this.c = ArrayUtils.clone(cArr);
        return this;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public boolean hasNext() {
        d();
        return this.e < this.d.length;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public String next() {
        if (hasNext()) {
            String[] strArr = this.d;
            int i = this.e;
            this.e = i + 1;
            return strArr[i];
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return this.e;
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        d();
        return this.e > 0;
    }

    @Override // java.util.ListIterator
    public String previous() {
        if (hasPrevious()) {
            String[] strArr = this.d;
            int i = this.e - 1;
            this.e = i;
            return strArr[i];
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return this.e - 1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public void set(String str) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public void add(String str) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    private void d() {
        if (this.d == null) {
            char[] cArr = this.c;
            if (cArr == null) {
                List<String> list = tokenize(null, 0, 0);
                this.d = (String[]) list.toArray(new String[list.size()]);
                return;
            }
            List<String> list2 = tokenize(cArr, 0, cArr.length);
            this.d = (String[]) list2.toArray(new String[list2.size()]);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<String> tokenize(char[] cArr, int i, int i2) {
        if (cArr == null || i2 == 0) {
            return Collections.emptyList();
        }
        StrBuilder strBuilder = new StrBuilder();
        ArrayList arrayList = new ArrayList();
        int i3 = i;
        while (i3 >= 0 && i3 < i2) {
            i3 = a(cArr, i3, i2, strBuilder, arrayList);
            if (i3 >= i2) {
                a(arrayList, "");
            }
        }
        return arrayList;
    }

    private void a(List<String> list, String str) {
        if (StringUtils.isEmpty(str)) {
            if (!isIgnoreEmptyTokens()) {
                if (isEmptyTokenAsNull()) {
                    str = null;
                }
            } else {
                return;
            }
        }
        list.add(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0031, code lost:
        a(r13, "");
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0037, code lost:
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(char[] r9, int r10, int r11, org.apache.commons.lang3.text.StrBuilder r12, java.util.List<java.lang.String> r13) {
        /*
            r8 = this;
        L_0x0000:
            if (r10 >= r11) goto L_0x002f
            org.apache.commons.lang3.text.StrMatcher r0 = r8.getIgnoredMatcher()
            int r0 = r0.isMatch(r9, r10, r10, r11)
            org.apache.commons.lang3.text.StrMatcher r1 = r8.getTrimmerMatcher()
            int r1 = r1.isMatch(r9, r10, r10, r11)
            int r0 = java.lang.Math.max(r0, r1)
            if (r0 == 0) goto L_0x002f
            org.apache.commons.lang3.text.StrMatcher r1 = r8.getDelimiterMatcher()
            int r1 = r1.isMatch(r9, r10, r10, r11)
            if (r1 > 0) goto L_0x002f
            org.apache.commons.lang3.text.StrMatcher r1 = r8.getQuoteMatcher()
            int r1 = r1.isMatch(r9, r10, r10, r11)
            if (r1 <= 0) goto L_0x002d
            goto L_0x002f
        L_0x002d:
            int r10 = r10 + r0
            goto L_0x0000
        L_0x002f:
            if (r10 < r11) goto L_0x0038
            java.lang.String r9 = ""
            r8.a(r13, r9)
            r9 = -1
            return r9
        L_0x0038:
            org.apache.commons.lang3.text.StrMatcher r0 = r8.getDelimiterMatcher()
            int r0 = r0.isMatch(r9, r10, r10, r11)
            if (r0 <= 0) goto L_0x0049
            java.lang.String r9 = ""
            r8.a(r13, r9)
            int r10 = r10 + r0
            return r10
        L_0x0049:
            org.apache.commons.lang3.text.StrMatcher r0 = r8.getQuoteMatcher()
            int r7 = r0.isMatch(r9, r10, r10, r11)
            if (r7 <= 0) goto L_0x0060
            int r2 = r10 + r7
            r0 = r8
            r1 = r9
            r3 = r11
            r4 = r12
            r5 = r13
            r6 = r10
            int r9 = r0.a(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x0060:
            r6 = 0
            r7 = 0
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            int r9 = r0.a(r1, r2, r3, r4, r5, r6, r7)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.text.StrTokenizer.a(char[], int, int, org.apache.commons.lang3.text.StrBuilder, java.util.List):int");
    }

    private int a(char[] cArr, int i, int i2, StrBuilder strBuilder, List<String> list, int i3, int i4) {
        strBuilder.clear();
        boolean z = i4 > 0;
        int i5 = i;
        int i6 = 0;
        while (i5 < i2) {
            if (!z) {
                int isMatch = getDelimiterMatcher().isMatch(cArr, i5, i, i2);
                if (isMatch > 0) {
                    a(list, strBuilder.substring(0, i6));
                    return i5 + isMatch;
                } else if (i4 <= 0 || !a(cArr, i5, i2, i3, i4)) {
                    int isMatch2 = getIgnoredMatcher().isMatch(cArr, i5, i, i2);
                    if (isMatch2 > 0) {
                        i5 += isMatch2;
                        i6 = i6;
                    } else {
                        int isMatch3 = getTrimmerMatcher().isMatch(cArr, i5, i, i2);
                        if (isMatch3 > 0) {
                            strBuilder.append(cArr, i5, isMatch3);
                            i5 += isMatch3;
                            i6 = i6;
                        } else {
                            i5++;
                            strBuilder.append(cArr[i5]);
                            i6 = strBuilder.size();
                        }
                    }
                } else {
                    i5 += i4;
                    i6 = i6;
                    z = true;
                }
            } else if (a(cArr, i5, i2, i3, i4)) {
                int i7 = i5 + i4;
                if (a(cArr, i7, i2, i3, i4)) {
                    strBuilder.append(cArr, i5, i4);
                    i5 += i4 * 2;
                    i6 = strBuilder.size();
                } else {
                    i6 = i6;
                    z = false;
                    i5 = i7;
                }
            } else {
                i5++;
                strBuilder.append(cArr[i5]);
                i6 = strBuilder.size();
            }
        }
        a(list, strBuilder.substring(0, i6));
        return -1;
    }

    private boolean a(char[] cArr, int i, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i + i5;
            if (i6 >= i2 || cArr[i6] != cArr[i3 + i5]) {
                return false;
            }
        }
        return true;
    }

    public StrMatcher getDelimiterMatcher() {
        return this.f;
    }

    public StrTokenizer setDelimiterMatcher(StrMatcher strMatcher) {
        if (strMatcher == null) {
            this.f = StrMatcher.noneMatcher();
        } else {
            this.f = strMatcher;
        }
        return this;
    }

    public StrTokenizer setDelimiterChar(char c) {
        return setDelimiterMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setDelimiterString(String str) {
        return setDelimiterMatcher(StrMatcher.stringMatcher(str));
    }

    public StrMatcher getQuoteMatcher() {
        return this.g;
    }

    public StrTokenizer setQuoteMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.g = strMatcher;
        }
        return this;
    }

    public StrTokenizer setQuoteChar(char c) {
        return setQuoteMatcher(StrMatcher.charMatcher(c));
    }

    public StrMatcher getIgnoredMatcher() {
        return this.h;
    }

    public StrTokenizer setIgnoredMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.h = strMatcher;
        }
        return this;
    }

    public StrTokenizer setIgnoredChar(char c) {
        return setIgnoredMatcher(StrMatcher.charMatcher(c));
    }

    public StrMatcher getTrimmerMatcher() {
        return this.i;
    }

    public StrTokenizer setTrimmerMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.i = strMatcher;
        }
        return this;
    }

    public boolean isEmptyTokenAsNull() {
        return this.j;
    }

    public StrTokenizer setEmptyTokenAsNull(boolean z) {
        this.j = z;
        return this;
    }

    public boolean isIgnoreEmptyTokens() {
        return this.k;
    }

    public StrTokenizer setIgnoreEmptyTokens(boolean z) {
        this.k = z;
        return this;
    }

    public String getContent() {
        char[] cArr = this.c;
        if (cArr == null) {
            return null;
        }
        return new String(cArr);
    }

    public Object clone() {
        try {
            return a();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    Object a() throws CloneNotSupportedException {
        StrTokenizer strTokenizer = (StrTokenizer) super.clone();
        char[] cArr = strTokenizer.c;
        if (cArr != null) {
            strTokenizer.c = (char[]) cArr.clone();
        }
        strTokenizer.reset();
        return strTokenizer;
    }

    public String toString() {
        if (this.d == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        return "StrTokenizer" + getTokenList();
    }
}
