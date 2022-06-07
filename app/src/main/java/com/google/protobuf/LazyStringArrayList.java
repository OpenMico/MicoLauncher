package com.google.protobuf;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes2.dex */
public class LazyStringArrayList extends a<String> implements LazyStringList, RandomAccess {
    private final List<Object> b;
    private static final LazyStringArrayList a = new LazyStringArrayList();
    public static final LazyStringList EMPTY = a;

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.protobuf.a, com.google.protobuf.Internal.ProtobufList
    public /* bridge */ /* synthetic */ boolean isModifiable() {
        return super.isModifiable();
    }

    @Override // com.google.protobuf.a, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // com.google.protobuf.a, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // com.google.protobuf.a, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    static {
        a.makeImmutable();
    }

    public LazyStringArrayList() {
        this(10);
    }

    public LazyStringArrayList(int i) {
        this((ArrayList<Object>) new ArrayList(i));
    }

    public LazyStringArrayList(LazyStringList lazyStringList) {
        this.b = new ArrayList(lazyStringList.size());
        addAll(lazyStringList);
    }

    public LazyStringArrayList(List<String> list) {
        this((ArrayList<Object>) new ArrayList(list));
    }

    private LazyStringArrayList(ArrayList<Object> arrayList) {
        this.b = arrayList;
    }

    @Override // com.google.protobuf.Internal.ProtobufList
    public LazyStringArrayList mutableCopyWithCapacity(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.b);
            return new LazyStringArrayList((ArrayList<Object>) arrayList);
        }
        throw new IllegalArgumentException();
    }

    @Override // java.util.AbstractList, java.util.List
    public String get(int i) {
        Object obj = this.b.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.b.set(i, stringUtf8);
            }
            return stringUtf8;
        }
        byte[] bArr = (byte[]) obj;
        String stringUtf82 = Internal.toStringUtf8(bArr);
        if (Internal.isValidUtf8(bArr)) {
            this.b.set(i, stringUtf82);
        }
        return stringUtf82;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.b.size();
    }

    public String set(int i, String str) {
        ensureIsMutable();
        return c(this.b.set(i, str));
    }

    public void add(int i, String str) {
        ensureIsMutable();
        this.b.add(i, str);
        this.modCount++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, ByteString byteString) {
        ensureIsMutable();
        this.b.add(i, byteString);
        this.modCount++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, byte[] bArr) {
        ensureIsMutable();
        this.b.add(i, bArr);
        this.modCount++;
    }

    @Override // com.google.protobuf.a, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends String> collection) {
        ensureIsMutable();
        if (collection instanceof LazyStringList) {
            collection = ((LazyStringList) collection).getUnderlyingElements();
        }
        boolean addAll = this.b.addAll(i, collection);
        this.modCount++;
        return addAll;
    }

    @Override // com.google.protobuf.LazyStringList
    public boolean addAllByteString(Collection<? extends ByteString> collection) {
        ensureIsMutable();
        boolean addAll = this.b.addAll(collection);
        this.modCount++;
        return addAll;
    }

    @Override // com.google.protobuf.LazyStringList
    public boolean addAllByteArray(Collection<byte[]> collection) {
        ensureIsMutable();
        boolean addAll = this.b.addAll(collection);
        this.modCount++;
        return addAll;
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.List
    public String remove(int i) {
        ensureIsMutable();
        Object remove = this.b.remove(i);
        this.modCount++;
        return c(remove);
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        ensureIsMutable();
        this.b.clear();
        this.modCount++;
    }

    @Override // com.google.protobuf.LazyStringList
    public void add(ByteString byteString) {
        ensureIsMutable();
        this.b.add(byteString);
        this.modCount++;
    }

    @Override // com.google.protobuf.LazyStringList
    public void add(byte[] bArr) {
        ensureIsMutable();
        this.b.add(bArr);
        this.modCount++;
    }

    @Override // com.google.protobuf.LazyStringList
    public Object getRaw(int i) {
        return this.b.get(i);
    }

    @Override // com.google.protobuf.LazyStringList
    public ByteString getByteString(int i) {
        Object obj = this.b.get(i);
        ByteString d = d(obj);
        if (d != obj) {
            this.b.set(i, d);
        }
        return d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.protobuf.LazyStringList
    public byte[] getByteArray(int i) {
        Object obj = this.b.get(i);
        byte[] e = e(obj);
        if (e != obj) {
            this.b.set(i, e);
        }
        return e;
    }

    @Override // com.google.protobuf.LazyStringList
    public void set(int i, ByteString byteString) {
        b(i, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object b(int i, ByteString byteString) {
        ensureIsMutable();
        return this.b.set(i, byteString);
    }

    @Override // com.google.protobuf.LazyStringList
    public void set(int i, byte[] bArr) {
        b(i, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object b(int i, byte[] bArr) {
        ensureIsMutable();
        return this.b.set(i, bArr);
    }

    private static String c(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ByteString) {
            return ((ByteString) obj).toStringUtf8();
        }
        return Internal.toStringUtf8((byte[]) obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ByteString d(Object obj) {
        if (obj instanceof ByteString) {
            return (ByteString) obj;
        }
        if (obj instanceof String) {
            return ByteString.copyFromUtf8((String) obj);
        }
        return ByteString.copyFrom((byte[]) obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] e(Object obj) {
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        if (obj instanceof String) {
            return Internal.toByteArray((String) obj);
        }
        return ((ByteString) obj).toByteArray();
    }

    @Override // com.google.protobuf.LazyStringList
    public List<?> getUnderlyingElements() {
        return Collections.unmodifiableList(this.b);
    }

    @Override // com.google.protobuf.LazyStringList
    public void mergeFrom(LazyStringList lazyStringList) {
        ensureIsMutable();
        for (Object obj : lazyStringList.getUnderlyingElements()) {
            if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                this.b.add(Arrays.copyOf(bArr, bArr.length));
            } else {
                this.b.add(obj);
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class a extends AbstractList<byte[]> implements RandomAccess {
        private final LazyStringArrayList a;

        a(LazyStringArrayList lazyStringArrayList) {
            this.a = lazyStringArrayList;
        }

        /* renamed from: a */
        public byte[] get(int i) {
            return this.a.getByteArray(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.a.size();
        }

        /* renamed from: a */
        public byte[] set(int i, byte[] bArr) {
            Object b = this.a.b(i, bArr);
            this.modCount++;
            return LazyStringArrayList.e(b);
        }

        /* renamed from: b */
        public void add(int i, byte[] bArr) {
            this.a.a(i, bArr);
            this.modCount++;
        }

        /* renamed from: b */
        public byte[] remove(int i) {
            String remove = this.a.remove(i);
            this.modCount++;
            return LazyStringArrayList.e(remove);
        }
    }

    @Override // com.google.protobuf.LazyStringList
    public List<byte[]> asByteArrayList() {
        return new a(this);
    }

    /* loaded from: classes2.dex */
    private static class b extends AbstractList<ByteString> implements RandomAccess {
        private final LazyStringArrayList a;

        b(LazyStringArrayList lazyStringArrayList) {
            this.a = lazyStringArrayList;
        }

        /* renamed from: a */
        public ByteString get(int i) {
            return this.a.getByteString(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.a.size();
        }

        /* renamed from: a */
        public ByteString set(int i, ByteString byteString) {
            Object b = this.a.b(i, byteString);
            this.modCount++;
            return LazyStringArrayList.d(b);
        }

        /* renamed from: b */
        public void add(int i, ByteString byteString) {
            this.a.a(i, byteString);
            this.modCount++;
        }

        /* renamed from: b */
        public ByteString remove(int i) {
            String remove = this.a.remove(i);
            this.modCount++;
            return LazyStringArrayList.d(remove);
        }
    }

    @Override // com.google.protobuf.ProtocolStringList
    public List<ByteString> asByteStringList() {
        return new b(this);
    }

    @Override // com.google.protobuf.LazyStringList
    public LazyStringList getUnmodifiableView() {
        return isModifiable() ? new UnmodifiableLazyStringList(this) : this;
    }
}
