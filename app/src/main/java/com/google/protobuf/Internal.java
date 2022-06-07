package com.google.protobuf;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

/* loaded from: classes2.dex */
public final class Internal {
    static final Charset a = Charset.forName("UTF-8");
    static final Charset b = Charset.forName("ISO-8859-1");
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.wrap(EMPTY_BYTE_ARRAY);
    public static final CodedInputStream EMPTY_CODED_INPUT_STREAM = CodedInputStream.newInstance(EMPTY_BYTE_ARRAY);

    /* loaded from: classes2.dex */
    public interface BooleanList extends ProtobufList<Boolean> {
        void addBoolean(boolean z);

        boolean getBoolean(int i);

        @Override // com.google.protobuf.Internal.ProtobufList
        ProtobufList<Boolean> mutableCopyWithCapacity(int i);

        boolean setBoolean(int i, boolean z);
    }

    /* loaded from: classes2.dex */
    public interface DoubleList extends ProtobufList<Double> {
        void addDouble(double d);

        double getDouble(int i);

        @Override // com.google.protobuf.Internal.ProtobufList
        ProtobufList<Double> mutableCopyWithCapacity(int i);

        double setDouble(int i, double d);
    }

    /* loaded from: classes2.dex */
    public interface EnumLite {
        int getNumber();
    }

    /* loaded from: classes2.dex */
    public interface EnumLiteMap<T extends EnumLite> {
        T findValueByNumber(int i);
    }

    /* loaded from: classes2.dex */
    public interface EnumVerifier {
        boolean isInRange(int i);
    }

    /* loaded from: classes2.dex */
    public interface FloatList extends ProtobufList<Float> {
        void addFloat(float f);

        float getFloat(int i);

        @Override // com.google.protobuf.Internal.ProtobufList
        ProtobufList<Float> mutableCopyWithCapacity(int i);

        float setFloat(int i, float f);
    }

    /* loaded from: classes2.dex */
    public interface IntList extends ProtobufList<Integer> {
        void addInt(int i);

        int getInt(int i);

        @Override // com.google.protobuf.Internal.ProtobufList
        ProtobufList<Integer> mutableCopyWithCapacity(int i);

        int setInt(int i, int i2);
    }

    /* loaded from: classes2.dex */
    public interface LongList extends ProtobufList<Long> {
        void addLong(long j);

        long getLong(int i);

        @Override // com.google.protobuf.Internal.ProtobufList
        ProtobufList<Long> mutableCopyWithCapacity(int i);

        long setLong(int i, long j);
    }

    /* loaded from: classes2.dex */
    public interface ProtobufList<E> extends List<E>, RandomAccess {
        boolean isModifiable();

        void makeImmutable();

        ProtobufList<E> mutableCopyWithCapacity(int i);
    }

    public static int hashBoolean(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int hashLong(long j) {
        return (int) (j ^ (j >>> 32));
    }

    private Internal() {
    }

    public static <T> T a(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static <T> T a(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static String stringDefaultValue(String str) {
        return new String(str.getBytes(b), a);
    }

    public static ByteString bytesDefaultValue(String str) {
        return ByteString.copyFrom(str.getBytes(b));
    }

    public static byte[] byteArrayDefaultValue(String str) {
        return str.getBytes(b);
    }

    public static ByteBuffer byteBufferDefaultValue(String str) {
        return ByteBuffer.wrap(byteArrayDefaultValue(str));
    }

    public static ByteBuffer copyByteBuffer(ByteBuffer byteBuffer) {
        ByteBuffer duplicate = byteBuffer.duplicate();
        duplicate.clear();
        ByteBuffer allocate = ByteBuffer.allocate(duplicate.capacity());
        allocate.put(duplicate);
        allocate.clear();
        return allocate;
    }

    public static boolean isValidUtf8(ByteString byteString) {
        return byteString.isValidUtf8();
    }

    public static boolean isValidUtf8(byte[] bArr) {
        return au.a(bArr);
    }

    public static byte[] toByteArray(String str) {
        return str.getBytes(a);
    }

    public static String toStringUtf8(byte[] bArr) {
        return new String(bArr, a);
    }

    public static int hashEnum(EnumLite enumLite) {
        return enumLite.getNumber();
    }

    public static int hashEnumList(List<? extends EnumLite> list) {
        int i = 1;
        for (EnumLite enumLite : list) {
            i = (i * 31) + hashEnum(enumLite);
        }
        return i;
    }

    public static boolean equals(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(List<byte[]> list) {
        int i = 1;
        for (byte[] bArr : list) {
            i = (i * 31) + hashCode(bArr);
        }
        return i;
    }

    public static int hashCode(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    static int a(byte[] bArr, int i, int i2) {
        int a2 = a(i2, bArr, i, i2);
        if (a2 == 0) {
            return 1;
        }
        return a2;
    }

    public static int a(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        for (int i5 = i2; i5 < i2 + i3; i5++) {
            i4 = (i4 * 31) + bArr[i5];
        }
        return i4;
    }

    public static boolean equalsByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        if (byteBuffer.capacity() != byteBuffer2.capacity()) {
            return false;
        }
        return byteBuffer.duplicate().clear().equals(byteBuffer2.duplicate().clear());
    }

    public static boolean equalsByteBuffer(List<ByteBuffer> list, List<ByteBuffer> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!equalsByteBuffer(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static int hashCodeByteBuffer(List<ByteBuffer> list) {
        int i = 1;
        for (ByteBuffer byteBuffer : list) {
            i = (i * 31) + hashCodeByteBuffer(byteBuffer);
        }
        return i;
    }

    public static int hashCodeByteBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            int a2 = a(byteBuffer.capacity(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            if (a2 == 0) {
                return 1;
            }
            return a2;
        }
        int i = 4096;
        if (byteBuffer.capacity() <= 4096) {
            i = byteBuffer.capacity();
        }
        byte[] bArr = new byte[i];
        ByteBuffer duplicate = byteBuffer.duplicate();
        duplicate.clear();
        int capacity = byteBuffer.capacity();
        while (duplicate.remaining() > 0) {
            int remaining = duplicate.remaining() <= i ? duplicate.remaining() : i;
            duplicate.get(bArr, 0, remaining);
            capacity = a(capacity, bArr, 0, remaining);
        }
        if (capacity == 0) {
            return 1;
        }
        return capacity;
    }

    public static <T extends MessageLite> T getDefaultInstance(Class<T> cls) {
        try {
            Method method = cls.getMethod("getDefaultInstance", new Class[0]);
            return (T) ((MessageLite) method.invoke(method, new Object[0]));
        } catch (Exception e) {
            throw new RuntimeException("Failed to get default instance for " + cls, e);
        }
    }

    public static Object a(Object obj, Object obj2) {
        return ((MessageLite) obj).toBuilder().mergeFrom((MessageLite) obj2).buildPartial();
    }

    /* loaded from: classes2.dex */
    public static class ListAdapter<F, T> extends AbstractList<T> {
        private final List<F> a;
        private final Converter<F, T> b;

        /* loaded from: classes2.dex */
        public interface Converter<F, T> {
            T convert(F f);
        }

        public ListAdapter(List<F> list, Converter<F, T> converter) {
            this.a = list;
            this.b = converter;
        }

        @Override // java.util.AbstractList, java.util.List
        public T get(int i) {
            return this.b.convert(this.a.get(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.a.size();
        }
    }

    /* loaded from: classes2.dex */
    public static class MapAdapter<K, V, RealValue> extends AbstractMap<K, V> {
        private final Map<K, RealValue> a;
        private final Converter<RealValue, V> b;

        /* loaded from: classes2.dex */
        public interface Converter<A, B> {
            A doBackward(B b);

            B doForward(A a);
        }

        public static <T extends EnumLite> Converter<Integer, T> newEnumConverter(final EnumLiteMap<T> enumLiteMap, final T t) {
            return (Converter<Integer, T>) new Converter<Integer, T>() { // from class: com.google.protobuf.Internal.MapAdapter.1
                /* renamed from: a */
                public EnumLite doForward(Integer num) {
                    EnumLite findValueByNumber = enumLiteMap.findValueByNumber(num.intValue());
                    return findValueByNumber == null ? t : findValueByNumber;
                }

                /* renamed from: a */
                public Integer doBackward(EnumLite enumLite) {
                    return Integer.valueOf(enumLite.getNumber());
                }
            };
        }

        public MapAdapter(Map<K, RealValue> map, Converter<RealValue, V> converter) {
            this.a = map;
            this.b = converter;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            RealValue realvalue = this.a.get(obj);
            if (realvalue == null) {
                return null;
            }
            return this.b.doForward(realvalue);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k, V v) {
            RealValue put = this.a.put(k, this.b.doBackward(v));
            if (put == null) {
                return null;
            }
            return this.b.doForward(put);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return new c(this.a.entrySet());
        }

        /* loaded from: classes2.dex */
        private class c extends AbstractSet<Map.Entry<K, V>> {
            private final Set<Map.Entry<K, RealValue>> b;

            public c(Set<Map.Entry<K, RealValue>> set) {
                MapAdapter.this = r1;
                this.b = set;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return new b(this.b.iterator());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return this.b.size();
            }
        }

        /* loaded from: classes2.dex */
        private class b implements Iterator<Map.Entry<K, V>> {
            private final Iterator<Map.Entry<K, RealValue>> b;

            public b(Iterator<Map.Entry<K, RealValue>> it) {
                MapAdapter.this = r1;
                this.b = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b.hasNext();
            }

            /* renamed from: a */
            public Map.Entry<K, V> next() {
                return new a(this.b.next());
            }

            @Override // java.util.Iterator
            public void remove() {
                this.b.remove();
            }
        }

        /* loaded from: classes2.dex */
        public class a implements Map.Entry<K, V> {
            private final Map.Entry<K, RealValue> b;

            public a(Map.Entry<K, RealValue> entry) {
                MapAdapter.this = r1;
                this.b = entry;
            }

            @Override // java.util.Map.Entry
            public K getKey() {
                return this.b.getKey();
            }

            @Override // java.util.Map.Entry
            public V getValue() {
                return (V) MapAdapter.this.b.doForward(this.b.getValue());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Map.Entry
            public V setValue(V v) {
                Object value = this.b.setValue(MapAdapter.this.b.doBackward(v));
                if (value == null) {
                    return null;
                }
                return (V) MapAdapter.this.b.doForward(value);
            }

            @Override // java.util.Map.Entry
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                return getKey().equals(((Map.Entry) obj).getKey()) && getValue().equals(getValue());
            }

            @Override // java.util.Map.Entry
            public int hashCode() {
                return this.b.hashCode();
            }
        }
    }
}
