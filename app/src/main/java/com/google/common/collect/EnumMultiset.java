package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.Enum;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class EnumMultiset<E extends Enum<E>> extends h<E> implements Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient Class<E> a;
    private transient E[] b;
    private transient int[] c;
    private transient int d;
    private transient long e;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ int add(Object obj, int i) {
        return add((EnumMultiset<E>) ((Enum) obj), i);
    }

    @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ boolean contains(@NullableDecl Object obj) {
        return super.contains(obj);
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ int setCount(Object obj, int i) {
        return setCount((EnumMultiset<E>) ((Enum) obj), i);
    }

    static /* synthetic */ int c(EnumMultiset enumMultiset) {
        int i = enumMultiset.d;
        enumMultiset.d = i - 1;
        return i;
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> cls) {
        return new EnumMultiset<>(cls);
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable) {
        Iterator<E> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "EnumMultiset constructor passed empty Iterable");
        EnumMultiset<E> enumMultiset = new EnumMultiset<>(it.next().getDeclaringClass());
        Iterables.addAll(enumMultiset, iterable);
        return enumMultiset;
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable, Class<E> cls) {
        EnumMultiset<E> create = create(cls);
        Iterables.addAll(create, iterable);
        return create;
    }

    private EnumMultiset(Class<E> cls) {
        this.a = cls;
        Preconditions.checkArgument(cls.isEnum());
        this.b = cls.getEnumConstants();
        this.c = new int[this.b.length];
    }

    private boolean b(@NullableDecl Object obj) {
        if (!(obj instanceof Enum)) {
            return false;
        }
        Enum r5 = (Enum) obj;
        int ordinal = r5.ordinal();
        E[] eArr = this.b;
        return ordinal < eArr.length && eArr[ordinal] == r5;
    }

    void a(@NullableDecl Object obj) {
        Preconditions.checkNotNull(obj);
        if (!b(obj)) {
            throw new ClassCastException("Expected an " + this.a + " but got " + obj);
        }
    }

    @Override // com.google.common.collect.h
    int c() {
        return this.d;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        return Ints.saturatedCast(this.e);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@NullableDecl Object obj) {
        if (obj == null || !b(obj)) {
            return 0;
        }
        return this.c[((Enum) obj).ordinal()];
    }

    @CanIgnoreReturnValue
    public int add(E e, int i) {
        a(e);
        t.a(i, "occurrences");
        if (i == 0) {
            return count(e);
        }
        int ordinal = e.ordinal();
        int i2 = this.c[ordinal];
        long j = i;
        long j2 = i2 + j;
        Preconditions.checkArgument(j2 <= 2147483647L, "too many occurrences: %s", j2);
        this.c[ordinal] = (int) j2;
        if (i2 == 0) {
            this.d++;
        }
        this.e += j;
        return i2;
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(@NullableDecl Object obj, int i) {
        if (obj == null || !b(obj)) {
            return 0;
        }
        Enum r1 = (Enum) obj;
        t.a(i, "occurrences");
        if (i == 0) {
            return count(obj);
        }
        int ordinal = r1.ordinal();
        int[] iArr = this.c;
        int i2 = iArr[ordinal];
        if (i2 == 0) {
            return 0;
        }
        if (i2 <= i) {
            iArr[ordinal] = 0;
            this.d--;
            this.e -= i2;
        } else {
            iArr[ordinal] = i2 - i;
            this.e -= i;
        }
        return i2;
    }

    @CanIgnoreReturnValue
    public int setCount(E e, int i) {
        a(e);
        t.a(i, "count");
        int ordinal = e.ordinal();
        int[] iArr = this.c;
        int i2 = iArr[ordinal];
        iArr[ordinal] = i;
        this.e += i - i2;
        if (i2 == 0 && i > 0) {
            this.d++;
        } else if (i2 > 0 && i == 0) {
            this.d--;
        }
        return i2;
    }

    @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        Arrays.fill(this.c, 0);
        this.e = 0L;
        this.d = 0;
    }

    /* loaded from: classes2.dex */
    abstract class a<T> implements Iterator<T> {
        int b = 0;
        int c = -1;

        abstract T b(int i);

        a() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (this.b < EnumMultiset.this.b.length) {
                int[] iArr = EnumMultiset.this.c;
                int i = this.b;
                if (iArr[i] > 0) {
                    return true;
                }
                this.b = i + 1;
            }
            return false;
        }

        @Override // java.util.Iterator
        public T next() {
            if (hasNext()) {
                T b = b(this.b);
                int i = this.b;
                this.c = i;
                this.b = i + 1;
                return b;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            t.a(this.c >= 0);
            if (EnumMultiset.this.c[this.c] > 0) {
                EnumMultiset.c(EnumMultiset.this);
                EnumMultiset.this.e -= EnumMultiset.this.c[this.c];
                EnumMultiset.this.c[this.c] = 0;
            }
            this.c = -1;
        }
    }

    @Override // com.google.common.collect.h
    Iterator<E> a() {
        return new EnumMultiset<E>.a() { // from class: com.google.common.collect.EnumMultiset.1
            /* JADX INFO: Access modifiers changed from: package-private */
            /* renamed from: a */
            public E b(int i) {
                return (E) EnumMultiset.this.b[i];
            }
        };
    }

    @Override // com.google.common.collect.h
    Iterator<Multiset.Entry<E>> b() {
        return new EnumMultiset<E>.a() { // from class: com.google.common.collect.EnumMultiset.2
            /* JADX INFO: Access modifiers changed from: package-private */
            /* renamed from: a */
            public Multiset.Entry<E> b(final int i) {
                return new Multisets.a<E>() { // from class: com.google.common.collect.EnumMultiset.2.1
                    /* renamed from: a */
                    public E getElement() {
                        return (E) EnumMultiset.this.b[i];
                    }

                    @Override // com.google.common.collect.Multiset.Entry
                    public int getCount() {
                        return EnumMultiset.this.c[i];
                    }
                };
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    public Iterator<E> iterator() {
        return Multisets.a((Multiset) this);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.a);
        cc.a(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = (Class) objectInputStream.readObject();
        this.b = this.a.getEnumConstants();
        this.c = new int[this.b.length];
        cc.a(this, objectInputStream);
    }
}
