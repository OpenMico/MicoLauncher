package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public class Joiner {
    private final String a;

    public static Joiner on(String str) {
        return new Joiner(str);
    }

    public static Joiner on(char c) {
        return new Joiner(String.valueOf(c));
    }

    private Joiner(String str) {
        this.a = (String) Preconditions.checkNotNull(str);
    }

    private Joiner(Joiner joiner) {
        this.a = joiner.a;
    }

    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A a, Iterable<?> iterable) throws IOException {
        return (A) appendTo((Joiner) a, iterable.iterator());
    }

    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A a, Iterator<?> it) throws IOException {
        Preconditions.checkNotNull(a);
        if (it.hasNext()) {
            a.append(a(it.next()));
            while (it.hasNext()) {
                a.append(this.a);
                a.append(a(it.next()));
            }
        }
        return a;
    }

    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A a, Object[] objArr) throws IOException {
        return (A) appendTo((Joiner) a, (Iterable<?>) Arrays.asList(objArr));
    }

    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A a, @NullableDecl Object obj, @NullableDecl Object obj2, Object... objArr) throws IOException {
        return (A) appendTo((Joiner) a, a(obj, obj2, objArr));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder sb, Iterable<?> iterable) {
        return appendTo(sb, iterable.iterator());
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder sb, Iterator<?> it) {
        try {
            appendTo((Joiner) sb, it);
            return sb;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder sb, Object[] objArr) {
        return appendTo(sb, (Iterable<?>) Arrays.asList(objArr));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder sb, @NullableDecl Object obj, @NullableDecl Object obj2, Object... objArr) {
        return appendTo(sb, a(obj, obj2, objArr));
    }

    public final String join(Iterable<?> iterable) {
        return join(iterable.iterator());
    }

    public final String join(Iterator<?> it) {
        return appendTo(new StringBuilder(), it).toString();
    }

    public final String join(Object[] objArr) {
        return join(Arrays.asList(objArr));
    }

    public final String join(@NullableDecl Object obj, @NullableDecl Object obj2, Object... objArr) {
        return join(a(obj, obj2, objArr));
    }

    public Joiner useForNull(final String str) {
        Preconditions.checkNotNull(str);
        return new Joiner(this) { // from class: com.google.common.base.Joiner.1
            @Override // com.google.common.base.Joiner
            CharSequence a(@NullableDecl Object obj) {
                return obj == null ? str : Joiner.this.a(obj);
            }

            @Override // com.google.common.base.Joiner
            public Joiner useForNull(String str2) {
                throw new UnsupportedOperationException("already specified useForNull");
            }

            @Override // com.google.common.base.Joiner
            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    public Joiner skipNulls() {
        return new Joiner(this) { // from class: com.google.common.base.Joiner.2
            @Override // com.google.common.base.Joiner
            public <A extends Appendable> A appendTo(A a, Iterator<?> it) throws IOException {
                Preconditions.checkNotNull(a, "appendable");
                Preconditions.checkNotNull(it, "parts");
                while (true) {
                    if (it.hasNext()) {
                        Object next = it.next();
                        if (next != null) {
                            a.append(Joiner.this.a(next));
                            break;
                        }
                    } else {
                        break;
                    }
                }
                while (it.hasNext()) {
                    Object next2 = it.next();
                    if (next2 != null) {
                        a.append(Joiner.this.a);
                        a.append(Joiner.this.a(next2));
                    }
                }
                return a;
            }

            @Override // com.google.common.base.Joiner
            public Joiner useForNull(String str) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }

            @Override // com.google.common.base.Joiner
            public MapJoiner withKeyValueSeparator(String str) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }

    public MapJoiner withKeyValueSeparator(char c) {
        return withKeyValueSeparator(String.valueOf(c));
    }

    public MapJoiner withKeyValueSeparator(String str) {
        return new MapJoiner(str);
    }

    /* loaded from: classes2.dex */
    public static final class MapJoiner {
        private final Joiner a;
        private final String b;

        private MapJoiner(Joiner joiner, String str) {
            this.a = joiner;
            this.b = (String) Preconditions.checkNotNull(str);
        }

        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A a, Map<?, ?> map) throws IOException {
            return (A) appendTo((MapJoiner) a, (Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder sb, Map<?, ?> map) {
            return appendTo(sb, (Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        @CanIgnoreReturnValue
        @Beta
        public <A extends Appendable> A appendTo(A a, Iterable<? extends Map.Entry<?, ?>> iterable) throws IOException {
            return (A) appendTo((MapJoiner) a, iterable.iterator());
        }

        @CanIgnoreReturnValue
        @Beta
        public <A extends Appendable> A appendTo(A a, Iterator<? extends Map.Entry<?, ?>> it) throws IOException {
            Preconditions.checkNotNull(a);
            if (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                a.append(this.a.a(entry.getKey()));
                a.append(this.b);
                a.append(this.a.a(entry.getValue()));
                while (it.hasNext()) {
                    a.append(this.a.a);
                    Map.Entry entry2 = (Map.Entry) it.next();
                    a.append(this.a.a(entry2.getKey()));
                    a.append(this.b);
                    a.append(this.a.a(entry2.getValue()));
                }
            }
            return a;
        }

        @CanIgnoreReturnValue
        @Beta
        public StringBuilder appendTo(StringBuilder sb, Iterable<? extends Map.Entry<?, ?>> iterable) {
            return appendTo(sb, iterable.iterator());
        }

        @CanIgnoreReturnValue
        @Beta
        public StringBuilder appendTo(StringBuilder sb, Iterator<? extends Map.Entry<?, ?>> it) {
            try {
                appendTo((MapJoiner) sb, it);
                return sb;
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }

        public String join(Map<?, ?> map) {
            return join(map.entrySet());
        }

        @Beta
        public String join(Iterable<? extends Map.Entry<?, ?>> iterable) {
            return join(iterable.iterator());
        }

        @Beta
        public String join(Iterator<? extends Map.Entry<?, ?>> it) {
            return appendTo(new StringBuilder(), it).toString();
        }

        public MapJoiner useForNull(String str) {
            return new MapJoiner(this.a.useForNull(str), this.b);
        }
    }

    CharSequence a(Object obj) {
        Preconditions.checkNotNull(obj);
        return obj instanceof CharSequence ? (CharSequence) obj : obj.toString();
    }

    private static Iterable<Object> a(final Object obj, final Object obj2, final Object[] objArr) {
        Preconditions.checkNotNull(objArr);
        return new AbstractList<Object>() { // from class: com.google.common.base.Joiner.3
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return objArr.length + 2;
            }

            @Override // java.util.AbstractList, java.util.List
            public Object get(int i) {
                switch (i) {
                    case 0:
                        return obj;
                    case 1:
                        return obj2;
                    default:
                        return objArr[i - 2];
                }
            }
        };
    }
}
