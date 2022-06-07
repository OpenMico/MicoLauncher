package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Splitter {
    private final CharMatcher a;
    private final boolean b;
    private final b c;
    private final int d;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface b {
        Iterator<String> b(Splitter splitter, CharSequence charSequence);
    }

    private Splitter(b bVar) {
        this(bVar, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    private Splitter(b bVar, boolean z, CharMatcher charMatcher, int i) {
        this.c = bVar;
        this.b = z;
        this.a = charMatcher;
        this.d = i;
    }

    public static Splitter on(char c) {
        return on(CharMatcher.is(c));
    }

    public static Splitter on(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new b() { // from class: com.google.common.base.Splitter.1
            /* renamed from: a */
            public a b(Splitter splitter, CharSequence charSequence) {
                return new a(splitter, charSequence) { // from class: com.google.common.base.Splitter.1.1
                    @Override // com.google.common.base.Splitter.a
                    int b(int i) {
                        return i + 1;
                    }

                    @Override // com.google.common.base.Splitter.a
                    int a(int i) {
                        return CharMatcher.this.indexIn(this.c, i);
                    }
                };
            }
        });
    }

    public static Splitter on(final String str) {
        Preconditions.checkArgument(str.length() != 0, "The separator may not be the empty string.");
        if (str.length() == 1) {
            return on(str.charAt(0));
        }
        return new Splitter(new b() { // from class: com.google.common.base.Splitter.2
            /* renamed from: a */
            public a b(Splitter splitter, CharSequence charSequence) {
                return new a(splitter, charSequence) { // from class: com.google.common.base.Splitter.2.1
                    /* JADX WARN: Code restructure failed: missing block: B:8:0x0026, code lost:
                        r6 = r6 + 1;
                     */
                    @Override // com.google.common.base.Splitter.a
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public int a(int r6) {
                        /*
                            r5 = this;
                            com.google.common.base.Splitter$2 r0 = com.google.common.base.Splitter.AnonymousClass2.this
                            java.lang.String r0 = r1
                            int r0 = r0.length()
                            java.lang.CharSequence r1 = r5.c
                            int r1 = r1.length()
                            int r1 = r1 - r0
                        L_0x000f:
                            if (r6 > r1) goto L_0x002d
                            r2 = 0
                        L_0x0012:
                            if (r2 >= r0) goto L_0x002c
                            java.lang.CharSequence r3 = r5.c
                            int r4 = r2 + r6
                            char r3 = r3.charAt(r4)
                            com.google.common.base.Splitter$2 r4 = com.google.common.base.Splitter.AnonymousClass2.this
                            java.lang.String r4 = r1
                            char r4 = r4.charAt(r2)
                            if (r3 == r4) goto L_0x0029
                            int r6 = r6 + 1
                            goto L_0x000f
                        L_0x0029:
                            int r2 = r2 + 1
                            goto L_0x0012
                        L_0x002c:
                            return r6
                        L_0x002d:
                            r6 = -1
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Splitter.AnonymousClass2.AnonymousClass1.a(int):int");
                    }

                    @Override // com.google.common.base.Splitter.a
                    public int b(int i) {
                        return i + str.length();
                    }
                };
            }
        });
    }

    @GwtIncompatible
    public static Splitter on(Pattern pattern) {
        return a(new g(pattern));
    }

    private static Splitter a(final d dVar) {
        Preconditions.checkArgument(!dVar.a("").a(), "The pattern may not match the empty string: %s", dVar);
        return new Splitter(new b() { // from class: com.google.common.base.Splitter.3
            /* renamed from: a */
            public a b(Splitter splitter, CharSequence charSequence) {
                final c a2 = d.this.a(charSequence);
                return new a(splitter, charSequence) { // from class: com.google.common.base.Splitter.3.1
                    @Override // com.google.common.base.Splitter.a
                    public int a(int i) {
                        if (a2.a(i)) {
                            return a2.d();
                        }
                        return -1;
                    }

                    @Override // com.google.common.base.Splitter.a
                    public int b(int i) {
                        return a2.c();
                    }
                };
            }
        });
    }

    @GwtIncompatible
    public static Splitter onPattern(String str) {
        return a(j.d(str));
    }

    public static Splitter fixedLength(final int i) {
        Preconditions.checkArgument(i > 0, "The length may not be less than 1");
        return new Splitter(new b() { // from class: com.google.common.base.Splitter.4
            /* renamed from: a */
            public a b(Splitter splitter, CharSequence charSequence) {
                return new a(splitter, charSequence) { // from class: com.google.common.base.Splitter.4.1
                    @Override // com.google.common.base.Splitter.a
                    public int b(int i2) {
                        return i2;
                    }

                    @Override // com.google.common.base.Splitter.a
                    public int a(int i2) {
                        int i3 = i2 + i;
                        if (i3 < this.c.length()) {
                            return i3;
                        }
                        return -1;
                    }
                };
            }
        });
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(this.c, true, this.a, this.d);
    }

    public Splitter limit(int i) {
        Preconditions.checkArgument(i > 0, "must be greater than zero: %s", i);
        return new Splitter(this.c, this.b, this.a, i);
    }

    public Splitter trimResults() {
        return trimResults(CharMatcher.whitespace());
    }

    public Splitter trimResults(CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(this.c, this.b, charMatcher, this.d);
    }

    public Iterable<String> split(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable<String>() { // from class: com.google.common.base.Splitter.5
            @Override // java.lang.Iterable
            public Iterator<String> iterator() {
                return Splitter.this.a(charSequence);
            }

            public String toString() {
                Joiner on = Joiner.on(", ");
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                StringBuilder appendTo = on.appendTo(sb, (Iterable<?>) this);
                appendTo.append(']');
                return appendTo.toString();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Iterator<String> a(CharSequence charSequence) {
        return this.c.b(this, charSequence);
    }

    @Beta
    public List<String> splitToList(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        Iterator<String> a2 = a(charSequence);
        ArrayList arrayList = new ArrayList();
        while (a2.hasNext()) {
            arrayList.add(a2.next());
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Beta
    public MapSplitter withKeyValueSeparator(String str) {
        return withKeyValueSeparator(on(str));
    }

    @Beta
    public MapSplitter withKeyValueSeparator(char c) {
        return withKeyValueSeparator(on(c));
    }

    @Beta
    public MapSplitter withKeyValueSeparator(Splitter splitter) {
        return new MapSplitter(splitter);
    }

    @Beta
    /* loaded from: classes2.dex */
    public static final class MapSplitter {
        private final Splitter a;
        private final Splitter b;

        private MapSplitter(Splitter splitter, Splitter splitter2) {
            this.a = splitter;
            this.b = (Splitter) Preconditions.checkNotNull(splitter2);
        }

        public Map<String, String> split(CharSequence charSequence) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (String str : this.a.split(charSequence)) {
                Iterator a = this.b.a(str);
                Preconditions.checkArgument(a.hasNext(), "Chunk [%s] is not a valid entry", str);
                String str2 = (String) a.next();
                Preconditions.checkArgument(!linkedHashMap.containsKey(str2), "Duplicate key [%s] found.", str2);
                Preconditions.checkArgument(a.hasNext(), "Chunk [%s] is not a valid entry", str);
                linkedHashMap.put(str2, (String) a.next());
                Preconditions.checkArgument(!a.hasNext(), "Chunk [%s] is not a valid entry", str);
            }
            return Collections.unmodifiableMap(linkedHashMap);
        }
    }

    /* loaded from: classes2.dex */
    private static abstract class a extends b<String> {
        final CharSequence c;
        final CharMatcher d;
        final boolean e;
        int f = 0;
        int g;

        abstract int a(int i);

        abstract int b(int i);

        protected a(Splitter splitter, CharSequence charSequence) {
            this.d = splitter.a;
            this.e = splitter.b;
            this.g = splitter.d;
            this.c = charSequence;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: c */
        public String a() {
            int a;
            int i = this.f;
            while (true) {
                int i2 = this.f;
                if (i2 == -1) {
                    return b();
                }
                a = a(i2);
                if (a == -1) {
                    a = this.c.length();
                    this.f = -1;
                } else {
                    this.f = b(a);
                }
                int i3 = this.f;
                if (i3 == i) {
                    this.f = i3 + 1;
                    if (this.f > this.c.length()) {
                        this.f = -1;
                    }
                } else {
                    while (i < a && this.d.matches(this.c.charAt(i))) {
                        i++;
                    }
                    while (a > i && this.d.matches(this.c.charAt(a - 1))) {
                        a--;
                    }
                    if (!this.e || i != a) {
                        break;
                    }
                    i = this.f;
                }
            }
            int i4 = this.g;
            if (i4 == 1) {
                a = this.c.length();
                this.f = -1;
                while (a > i && this.d.matches(this.c.charAt(a - 1))) {
                    a--;
                }
            } else {
                this.g = i4 - 1;
            }
            return this.c.subSequence(i, a).toString();
        }
    }
}
