package org.apache.commons.lang3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CharRange.java */
/* loaded from: classes5.dex */
public final class a implements Serializable, Iterable<Character> {
    private static final long serialVersionUID = 8270183163158333422L;
    private transient String a;
    private final char end;
    private final boolean negated;
    private final char start;

    private a(char c, char c2, boolean z) {
        if (c > c2) {
            c2 = c;
            c = c2;
        }
        this.start = c;
        this.end = c2;
        this.negated = z;
    }

    public static a a(char c) {
        return new a(c, c, false);
    }

    public static a b(char c) {
        return new a(c, c, true);
    }

    public static a a(char c, char c2) {
        return new a(c, c2, false);
    }

    public static a b(char c, char c2) {
        return new a(c, c2, true);
    }

    public boolean a() {
        return this.negated;
    }

    public boolean c(char c) {
        return (c >= this.start && c <= this.end) != this.negated;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return this.start == aVar.start && this.end == aVar.end && this.negated == aVar.negated;
    }

    public int hashCode() {
        return this.start + 'S' + (this.end * 7) + (this.negated ? 1 : 0);
    }

    public String toString() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder(4);
            if (a()) {
                sb.append('^');
            }
            sb.append(this.start);
            if (this.start != this.end) {
                sb.append('-');
                sb.append(this.end);
            }
            this.a = sb.toString();
        }
        return this.a;
    }

    @Override // java.lang.Iterable
    public Iterator<Character> iterator() {
        return new C0380a();
    }

    /* compiled from: CharRange.java */
    /* renamed from: org.apache.commons.lang3.a$a  reason: collision with other inner class name */
    /* loaded from: classes5.dex */
    private static class C0380a implements Iterator<Character> {
        private char a;
        private final a b;
        private boolean c;

        private C0380a(a aVar) {
            this.b = aVar;
            this.c = true;
            if (!this.b.negated) {
                this.a = this.b.start;
            } else if (this.b.start != 0) {
                this.a = (char) 0;
            } else if (this.b.end == 65535) {
                this.c = false;
            } else {
                this.a = (char) (this.b.end + 1);
            }
        }

        private void b() {
            if (this.b.negated) {
                char c = this.a;
                if (c == 65535) {
                    this.c = false;
                } else if (c + 1 != this.b.start) {
                    this.a = (char) (this.a + 1);
                } else if (this.b.end == 65535) {
                    this.c = false;
                } else {
                    this.a = (char) (this.b.end + 1);
                }
            } else if (this.a < this.b.end) {
                this.a = (char) (this.a + 1);
            } else {
                this.c = false;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c;
        }

        /* renamed from: a */
        public Character next() {
            if (this.c) {
                char c = this.a;
                b();
                return Character.valueOf(c);
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
