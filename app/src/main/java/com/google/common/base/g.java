package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: JdkPattern.java */
@GwtIncompatible
/* loaded from: classes2.dex */
final class g extends d implements Serializable {
    private static final long serialVersionUID = 0;
    private final Pattern pattern;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(Pattern pattern) {
        this.pattern = (Pattern) Preconditions.checkNotNull(pattern);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.base.d
    public c a(CharSequence charSequence) {
        return new a(this.pattern.matcher(charSequence));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.base.d
    public String a() {
        return this.pattern.pattern();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.base.d
    public int b() {
        return this.pattern.flags();
    }

    public String toString() {
        return this.pattern.toString();
    }

    public int hashCode() {
        return this.pattern.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof g)) {
            return false;
        }
        return this.pattern.equals(((g) obj).pattern);
    }

    /* compiled from: JdkPattern.java */
    /* loaded from: classes2.dex */
    private static final class a extends c {
        final Matcher a;

        a(Matcher matcher) {
            this.a = (Matcher) Preconditions.checkNotNull(matcher);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.base.c
        public boolean a() {
            return this.a.matches();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.base.c
        public boolean b() {
            return this.a.find();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.base.c
        public boolean a(int i) {
            return this.a.find(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.base.c
        public int c() {
            return this.a.end();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.base.c
        public int d() {
            return this.a.start();
        }
    }
}
