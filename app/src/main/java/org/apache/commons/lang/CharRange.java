package org.apache.commons.lang;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public final class CharRange implements Serializable {
    private static final long serialVersionUID = 8270183163158333422L;
    private transient String a;
    private final char end;
    private final boolean negated;
    private final char start;

    /* renamed from: org.apache.commons.lang.CharRange$1  reason: invalid class name */
    /* loaded from: classes5.dex */
    class AnonymousClass1 {
    }

    static boolean a(CharRange charRange) {
        return charRange.negated;
    }

    static char b(CharRange charRange) {
        return charRange.start;
    }

    static char c(CharRange charRange) {
        return charRange.end;
    }

    public static CharRange is(char c) {
        return new CharRange(c, c, false);
    }

    public static CharRange isNot(char c) {
        return new CharRange(c, c, true);
    }

    public static CharRange isIn(char c, char c2) {
        return new CharRange(c, c2, false);
    }

    public static CharRange isNotIn(char c, char c2) {
        return new CharRange(c, c2, true);
    }

    public CharRange(char c) {
        this(c, c, false);
    }

    public CharRange(char c, boolean z) {
        this(c, c, z);
    }

    public CharRange(char c, char c2) {
        this(c, c2, false);
    }

    public CharRange(char c, char c2, boolean z) {
        if (c > c2) {
            c2 = c;
            c = c2;
        }
        this.start = c;
        this.end = c2;
        this.negated = z;
    }

    public char getStart() {
        return this.start;
    }

    public char getEnd() {
        return this.end;
    }

    public boolean isNegated() {
        return this.negated;
    }

    public boolean contains(char c) {
        return (c >= this.start && c <= this.end) != this.negated;
    }

    public boolean contains(CharRange charRange) {
        if (charRange != null) {
            return this.negated ? charRange.negated ? this.start >= charRange.start && this.end <= charRange.end : charRange.end < this.start || charRange.start > this.end : charRange.negated ? this.start == 0 && this.end == 65535 : this.start <= charRange.start && this.end >= charRange.end;
        }
        throw new IllegalArgumentException("The Range must not be null");
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CharRange)) {
            return false;
        }
        CharRange charRange = (CharRange) obj;
        return this.start == charRange.start && this.end == charRange.end && this.negated == charRange.negated;
    }

    public int hashCode() {
        return this.start + 'S' + (this.end * 7) + (this.negated ? 1 : 0);
    }

    public String toString() {
        if (this.a == null) {
            StrBuilder strBuilder = new StrBuilder(4);
            if (isNegated()) {
                strBuilder.append('^');
            }
            strBuilder.append(this.start);
            if (this.start != this.end) {
                strBuilder.append('-');
                strBuilder.append(this.end);
            }
            this.a = strBuilder.toString();
        }
        return this.a;
    }

    public Iterator iterator() {
        return new a(this, null);
    }

    /* loaded from: classes5.dex */
    private static class a implements Iterator {
        private char a;
        private final CharRange b;
        private boolean c;

        a(CharRange charRange, AnonymousClass1 r2) {
            this(charRange);
        }

        private a(CharRange charRange) {
            this.b = charRange;
            this.c = true;
            if (!CharRange.a(this.b)) {
                this.a = CharRange.b(this.b);
            } else if (CharRange.b(this.b) != 0) {
                this.a = (char) 0;
            } else if (CharRange.c(this.b) == 65535) {
                this.c = false;
            } else {
                this.a = (char) (CharRange.c(this.b) + 1);
            }
        }

        private void a() {
            if (CharRange.a(this.b)) {
                char c = this.a;
                if (c == 65535) {
                    this.c = false;
                } else if (c + 1 != CharRange.b(this.b)) {
                    this.a = (char) (this.a + 1);
                } else if (CharRange.c(this.b) == 65535) {
                    this.c = false;
                } else {
                    this.a = (char) (CharRange.c(this.b) + 1);
                }
            } else if (this.a < CharRange.c(this.b)) {
                this.a = (char) (this.a + 1);
            } else {
                this.c = false;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.c) {
                char c = this.a;
                a();
                return new Character(c);
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
