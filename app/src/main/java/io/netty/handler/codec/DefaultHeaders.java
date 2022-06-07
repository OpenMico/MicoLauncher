package io.netty.handler.codec;

import com.xiaomi.onetrack.api.b;
import io.netty.handler.codec.Headers;
import io.netty.util.HashingStrategy;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SystemPropertyUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class DefaultHeaders<K, V, T extends Headers<K, V, T>> implements Headers<K, V, T> {
    private static final int b = Math.min(128, Math.max(1, SystemPropertyUtil.getInt("io.netty.DefaultHeaders.arraySizeHintMax", 16)));
    int a;
    private final HeaderEntry<K, V>[] c;
    private final byte d;
    private final ValueConverter<V> e;
    private final NameValidator<K> f;
    private final HashingStrategy<K> g;
    protected final HeaderEntry<K, V> head;

    /* loaded from: classes4.dex */
    public interface NameValidator<K> {
        public static final NameValidator NOT_NULL = new NameValidator() { // from class: io.netty.handler.codec.DefaultHeaders.NameValidator.1
            @Override // io.netty.handler.codec.DefaultHeaders.NameValidator
            public void validateName(Object obj) {
                ObjectUtil.checkNotNull(obj, "name");
            }
        };

        void validateName(K k);
    }

    private T a() {
        return this;
    }

    public DefaultHeaders(ValueConverter<V> valueConverter) {
        this(HashingStrategy.JAVA_HASHER, valueConverter);
    }

    public DefaultHeaders(ValueConverter<V> valueConverter, NameValidator<K> nameValidator) {
        this(HashingStrategy.JAVA_HASHER, valueConverter, nameValidator);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy, ValueConverter<V> valueConverter) {
        this(hashingStrategy, valueConverter, NameValidator.NOT_NULL);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy, ValueConverter<V> valueConverter, NameValidator<K> nameValidator) {
        this(hashingStrategy, valueConverter, nameValidator, 16);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy, ValueConverter<V> valueConverter, NameValidator<K> nameValidator, int i) {
        this.e = (ValueConverter) ObjectUtil.checkNotNull(valueConverter, "valueConverter");
        this.f = (NameValidator) ObjectUtil.checkNotNull(nameValidator, "nameValidator");
        this.g = (HashingStrategy) ObjectUtil.checkNotNull(hashingStrategy, "nameHashingStrategy");
        this.c = new HeaderEntry[MathUtil.findNextPositivePowerOfTwo(Math.min(i, b))];
        this.d = (byte) (this.c.length - 1);
        this.head = new HeaderEntry<>();
    }

    @Override // io.netty.handler.codec.Headers
    public V get(K k) {
        ObjectUtil.checkNotNull(k, "name");
        int hashCode = this.g.hashCode(k);
        V v = null;
        for (HeaderEntry<K, V> headerEntry = this.c[a(hashCode)]; headerEntry != null; headerEntry = headerEntry.next) {
            if (headerEntry.hash == hashCode && this.g.equals(k, headerEntry.key)) {
                v = headerEntry.value;
            }
        }
        return v;
    }

    @Override // io.netty.handler.codec.Headers
    public V get(K k, V v) {
        V v2 = get(k);
        return v2 == null ? v : v2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.Headers
    public V getAndRemove(K k) {
        int hashCode = this.g.hashCode(k);
        return (V) a(hashCode, a(hashCode), ObjectUtil.checkNotNull(k, "name"));
    }

    @Override // io.netty.handler.codec.Headers
    public V getAndRemove(K k, V v) {
        V andRemove = getAndRemove(k);
        return andRemove == null ? v : andRemove;
    }

    @Override // io.netty.handler.codec.Headers
    public List<V> getAll(K k) {
        ObjectUtil.checkNotNull(k, "name");
        LinkedList linkedList = new LinkedList();
        int hashCode = this.g.hashCode(k);
        for (HeaderEntry<K, V> headerEntry = this.c[a(hashCode)]; headerEntry != null; headerEntry = headerEntry.next) {
            if (headerEntry.hash == hashCode && this.g.equals(k, headerEntry.key)) {
                linkedList.addFirst(headerEntry.getValue());
            }
        }
        return linkedList;
    }

    @Override // io.netty.handler.codec.Headers
    public List<V> getAllAndRemove(K k) {
        List<V> all = getAll(k);
        remove(k);
        return all;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean contains(K k) {
        return get(k) != null;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsObject(K k, Object obj) {
        return contains(k, this.e.convertObject(ObjectUtil.checkNotNull(obj, b.p)));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsBoolean(K k, boolean z) {
        return contains(k, this.e.convertBoolean(z));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsByte(K k, byte b2) {
        return contains(k, this.e.convertByte(b2));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsChar(K k, char c) {
        return contains(k, this.e.convertChar(c));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsShort(K k, short s) {
        return contains(k, this.e.convertShort(s));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsInt(K k, int i) {
        return contains(k, this.e.convertInt(i));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsLong(K k, long j) {
        return contains(k, this.e.convertLong(j));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsFloat(K k, float f) {
        return contains(k, this.e.convertFloat(f));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsDouble(K k, double d) {
        return contains(k, this.e.convertDouble(d));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsTimeMillis(K k, long j) {
        return contains(k, this.e.convertTimeMillis(j));
    }

    @Override // io.netty.handler.codec.Headers
    public boolean contains(K k, V v) {
        return contains(k, v, HashingStrategy.JAVA_HASHER);
    }

    public final boolean contains(K k, V v, HashingStrategy<? super V> hashingStrategy) {
        ObjectUtil.checkNotNull(k, "name");
        int hashCode = this.g.hashCode(k);
        for (HeaderEntry<K, V> headerEntry = this.c[a(hashCode)]; headerEntry != null; headerEntry = headerEntry.next) {
            if (headerEntry.hash == hashCode && this.g.equals(k, headerEntry.key) && hashingStrategy.equals(v, (V) headerEntry.value)) {
                return true;
            }
        }
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public int size() {
        return this.a;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean isEmpty() {
        HeaderEntry<K, V> headerEntry = this.head;
        return headerEntry == headerEntry.after;
    }

    @Override // io.netty.handler.codec.Headers
    public Set<K> names() {
        if (isEmpty()) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(size());
        for (HeaderEntry<K, V> headerEntry = this.head.after; headerEntry != this.head; headerEntry = headerEntry.after) {
            linkedHashSet.add(headerEntry.getKey());
        }
        return linkedHashSet;
    }

    @Override // io.netty.handler.codec.Headers
    public T add(K k, V v) {
        this.f.validateName(k);
        ObjectUtil.checkNotNull(v, b.p);
        int hashCode = this.g.hashCode(k);
        a(hashCode, a(hashCode), k, v);
        return a();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.Headers
    public T add(K k, Iterable<? extends V> iterable) {
        this.f.validateName(k);
        int hashCode = this.g.hashCode(k);
        int a2 = a(hashCode);
        Iterator<? extends V> it = iterable.iterator();
        while (it.hasNext()) {
            a(hashCode, a2, k, it.next());
        }
        return (T) a();
    }

    @Override // io.netty.handler.codec.Headers
    public T add(K k, V... vArr) {
        this.f.validateName(k);
        int hashCode = this.g.hashCode(k);
        int a2 = a(hashCode);
        for (V v : vArr) {
            a(hashCode, a2, k, v);
        }
        return a();
    }

    @Override // io.netty.handler.codec.Headers
    public T addObject(K k, Object obj) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertObject(ObjectUtil.checkNotNull(obj, b.p)));
    }

    @Override // io.netty.handler.codec.Headers
    public T addObject(K k, Iterable<?> iterable) {
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            addObject((DefaultHeaders<K, V, T>) k, it.next());
        }
        return a();
    }

    @Override // io.netty.handler.codec.Headers
    public T addObject(K k, Object... objArr) {
        for (Object obj : objArr) {
            addObject((DefaultHeaders<K, V, T>) k, obj);
        }
        return a();
    }

    @Override // io.netty.handler.codec.Headers
    public T addInt(K k, int i) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertInt(i));
    }

    @Override // io.netty.handler.codec.Headers
    public T addLong(K k, long j) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertLong(j));
    }

    @Override // io.netty.handler.codec.Headers
    public T addDouble(K k, double d) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertDouble(d));
    }

    @Override // io.netty.handler.codec.Headers
    public T addTimeMillis(K k, long j) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertTimeMillis(j));
    }

    @Override // io.netty.handler.codec.Headers
    public T addChar(K k, char c) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertChar(c));
    }

    @Override // io.netty.handler.codec.Headers
    public T addBoolean(K k, boolean z) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertBoolean(z));
    }

    @Override // io.netty.handler.codec.Headers
    public T addFloat(K k, float f) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertFloat(f));
    }

    @Override // io.netty.handler.codec.Headers
    public T addByte(K k, byte b2) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertByte(b2));
    }

    @Override // io.netty.handler.codec.Headers
    public T addShort(K k, short s) {
        return add((DefaultHeaders<K, V, T>) k, (K) this.e.convertShort(s));
    }

    @Override // io.netty.handler.codec.Headers
    public T add(Headers<? extends K, ? extends V, ?> headers) {
        if (headers != this) {
            addImpl(headers);
            return a();
        }
        throw new IllegalArgumentException("can't add to itself.");
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void addImpl(Headers<? extends K, ? extends V, ?> headers) {
        if (headers instanceof DefaultHeaders) {
            DefaultHeaders defaultHeaders = (DefaultHeaders) headers;
            HeaderEntry<K, V> headerEntry = defaultHeaders.head.after;
            if (defaultHeaders.g == this.g && defaultHeaders.f == this.f) {
                while (headerEntry != defaultHeaders.head) {
                    a(headerEntry.hash, a(headerEntry.hash), headerEntry.key, headerEntry.value);
                    headerEntry = headerEntry.after;
                }
                return;
            }
            while (headerEntry != defaultHeaders.head) {
                add((DefaultHeaders<K, V, T>) headerEntry.key, (K) headerEntry.value);
                headerEntry = headerEntry.after;
            }
            return;
        }
        for (Map.Entry<? extends K, ? extends V> entry : headers) {
            add((DefaultHeaders<K, V, T>) entry.getKey(), entry.getValue());
        }
    }

    @Override // io.netty.handler.codec.Headers
    public T set(K k, V v) {
        this.f.validateName(k);
        ObjectUtil.checkNotNull(v, b.p);
        int hashCode = this.g.hashCode(k);
        int a2 = a(hashCode);
        a(hashCode, a2, k);
        a(hashCode, a2, k, v);
        return a();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.Headers
    public T set(K k, Iterable<? extends V> iterable) {
        Object next;
        this.f.validateName(k);
        ObjectUtil.checkNotNull(iterable, "values");
        int hashCode = this.g.hashCode(k);
        int a2 = a(hashCode);
        a(hashCode, a2, k);
        Iterator<? extends V> it = iterable.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            a(hashCode, a2, k, next);
        }
        return (T) a();
    }

    @Override // io.netty.handler.codec.Headers
    public T set(K k, V... vArr) {
        this.f.validateName(k);
        ObjectUtil.checkNotNull(vArr, "values");
        int hashCode = this.g.hashCode(k);
        int a2 = a(hashCode);
        a(hashCode, a2, k);
        for (V v : vArr) {
            if (v == null) {
                break;
            }
            a(hashCode, a2, k, v);
        }
        return a();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.Headers
    public T setObject(K k, Object obj) {
        ObjectUtil.checkNotNull(obj, b.p);
        return (T) set((DefaultHeaders<K, V, T>) k, (K) ObjectUtil.checkNotNull(this.e.convertObject(obj), "convertedValue"));
    }

    @Override // io.netty.handler.codec.Headers
    public T setObject(K k, Iterable<?> iterable) {
        Object next;
        this.f.validateName(k);
        int hashCode = this.g.hashCode(k);
        int a2 = a(hashCode);
        a(hashCode, a2, k);
        Iterator<?> it = iterable.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            a(hashCode, a2, k, this.e.convertObject(next));
        }
        return a();
    }

    @Override // io.netty.handler.codec.Headers
    public T setObject(K k, Object... objArr) {
        this.f.validateName(k);
        int hashCode = this.g.hashCode(k);
        int a2 = a(hashCode);
        a(hashCode, a2, k);
        for (Object obj : objArr) {
            if (obj == null) {
                break;
            }
            a(hashCode, a2, k, this.e.convertObject(obj));
        }
        return a();
    }

    @Override // io.netty.handler.codec.Headers
    public T setInt(K k, int i) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertInt(i));
    }

    @Override // io.netty.handler.codec.Headers
    public T setLong(K k, long j) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertLong(j));
    }

    @Override // io.netty.handler.codec.Headers
    public T setDouble(K k, double d) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertDouble(d));
    }

    @Override // io.netty.handler.codec.Headers
    public T setTimeMillis(K k, long j) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertTimeMillis(j));
    }

    @Override // io.netty.handler.codec.Headers
    public T setFloat(K k, float f) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertFloat(f));
    }

    @Override // io.netty.handler.codec.Headers
    public T setChar(K k, char c) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertChar(c));
    }

    @Override // io.netty.handler.codec.Headers
    public T setBoolean(K k, boolean z) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertBoolean(z));
    }

    @Override // io.netty.handler.codec.Headers
    public T setByte(K k, byte b2) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertByte(b2));
    }

    @Override // io.netty.handler.codec.Headers
    public T setShort(K k, short s) {
        return set((DefaultHeaders<K, V, T>) k, (K) this.e.convertShort(s));
    }

    @Override // io.netty.handler.codec.Headers
    public T set(Headers<? extends K, ? extends V, ?> headers) {
        if (headers != this) {
            clear();
            addImpl(headers);
        }
        return a();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.Headers
    public T setAll(Headers<? extends K, ? extends V, ?> headers) {
        if (headers != this) {
            Iterator<? extends K> it = headers.names().iterator();
            while (it.hasNext()) {
                remove(it.next());
            }
            addImpl(headers);
        }
        return (T) a();
    }

    @Override // io.netty.handler.codec.Headers
    public boolean remove(K k) {
        return getAndRemove(k) != null;
    }

    @Override // io.netty.handler.codec.Headers
    public T clear() {
        Arrays.fill(this.c, (Object) null);
        HeaderEntry<K, V> headerEntry = this.head;
        headerEntry.after = headerEntry;
        headerEntry.before = headerEntry;
        this.a = 0;
        return a();
    }

    @Override // io.netty.handler.codec.Headers, java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return new a();
    }

    @Override // io.netty.handler.codec.Headers
    public Boolean getBoolean(K k) {
        V v = get(k);
        if (v != null) {
            return Boolean.valueOf(this.e.convertToBoolean(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean getBoolean(K k, boolean z) {
        Boolean bool = getBoolean(k);
        return bool != null ? bool.booleanValue() : z;
    }

    @Override // io.netty.handler.codec.Headers
    public Byte getByte(K k) {
        V v = get(k);
        if (v != null) {
            return Byte.valueOf(this.e.convertToByte(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public byte getByte(K k, byte b2) {
        Byte b3 = getByte(k);
        return b3 != null ? b3.byteValue() : b2;
    }

    @Override // io.netty.handler.codec.Headers
    public Character getChar(K k) {
        V v = get(k);
        if (v != null) {
            return Character.valueOf(this.e.convertToChar(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public char getChar(K k, char c) {
        Character ch = getChar(k);
        return ch != null ? ch.charValue() : c;
    }

    @Override // io.netty.handler.codec.Headers
    public Short getShort(K k) {
        V v = get(k);
        if (v != null) {
            return Short.valueOf(this.e.convertToShort(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public short getShort(K k, short s) {
        Short sh = getShort(k);
        return sh != null ? sh.shortValue() : s;
    }

    @Override // io.netty.handler.codec.Headers
    public Integer getInt(K k) {
        V v = get(k);
        if (v != null) {
            return Integer.valueOf(this.e.convertToInt(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public int getInt(K k, int i) {
        Integer num = getInt(k);
        return num != null ? num.intValue() : i;
    }

    @Override // io.netty.handler.codec.Headers
    public Long getLong(K k) {
        V v = get(k);
        if (v != null) {
            return Long.valueOf(this.e.convertToLong(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public long getLong(K k, long j) {
        Long l = getLong(k);
        return l != null ? l.longValue() : j;
    }

    @Override // io.netty.handler.codec.Headers
    public Float getFloat(K k) {
        V v = get(k);
        if (v != null) {
            return Float.valueOf(this.e.convertToFloat(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public float getFloat(K k, float f) {
        Float f2 = getFloat(k);
        return f2 != null ? f2.floatValue() : f;
    }

    @Override // io.netty.handler.codec.Headers
    public Double getDouble(K k) {
        V v = get(k);
        if (v != null) {
            return Double.valueOf(this.e.convertToDouble(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public double getDouble(K k, double d) {
        Double d2 = getDouble(k);
        return d2 != null ? d2.doubleValue() : d;
    }

    @Override // io.netty.handler.codec.Headers
    public Long getTimeMillis(K k) {
        V v = get(k);
        if (v != null) {
            return Long.valueOf(this.e.convertToTimeMillis(v));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public long getTimeMillis(K k, long j) {
        Long timeMillis = getTimeMillis(k);
        return timeMillis != null ? timeMillis.longValue() : j;
    }

    @Override // io.netty.handler.codec.Headers
    public Boolean getBooleanAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Boolean.valueOf(this.e.convertToBoolean(andRemove));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean getBooleanAndRemove(K k, boolean z) {
        Boolean booleanAndRemove = getBooleanAndRemove(k);
        return booleanAndRemove != null ? booleanAndRemove.booleanValue() : z;
    }

    @Override // io.netty.handler.codec.Headers
    public Byte getByteAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Byte.valueOf(this.e.convertToByte(andRemove));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public byte getByteAndRemove(K k, byte b2) {
        Byte byteAndRemove = getByteAndRemove(k);
        return byteAndRemove != null ? byteAndRemove.byteValue() : b2;
    }

    @Override // io.netty.handler.codec.Headers
    public Character getCharAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove == null) {
            return null;
        }
        try {
            return Character.valueOf(this.e.convertToChar(andRemove));
        } catch (Throwable unused) {
            return null;
        }
    }

    @Override // io.netty.handler.codec.Headers
    public char getCharAndRemove(K k, char c) {
        Character charAndRemove = getCharAndRemove(k);
        return charAndRemove != null ? charAndRemove.charValue() : c;
    }

    @Override // io.netty.handler.codec.Headers
    public Short getShortAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Short.valueOf(this.e.convertToShort(andRemove));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public short getShortAndRemove(K k, short s) {
        Short shortAndRemove = getShortAndRemove(k);
        return shortAndRemove != null ? shortAndRemove.shortValue() : s;
    }

    @Override // io.netty.handler.codec.Headers
    public Integer getIntAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Integer.valueOf(this.e.convertToInt(andRemove));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public int getIntAndRemove(K k, int i) {
        Integer intAndRemove = getIntAndRemove(k);
        return intAndRemove != null ? intAndRemove.intValue() : i;
    }

    @Override // io.netty.handler.codec.Headers
    public Long getLongAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Long.valueOf(this.e.convertToLong(andRemove));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public long getLongAndRemove(K k, long j) {
        Long longAndRemove = getLongAndRemove(k);
        return longAndRemove != null ? longAndRemove.longValue() : j;
    }

    @Override // io.netty.handler.codec.Headers
    public Float getFloatAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Float.valueOf(this.e.convertToFloat(andRemove));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public float getFloatAndRemove(K k, float f) {
        Float floatAndRemove = getFloatAndRemove(k);
        return floatAndRemove != null ? floatAndRemove.floatValue() : f;
    }

    @Override // io.netty.handler.codec.Headers
    public Double getDoubleAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Double.valueOf(this.e.convertToDouble(andRemove));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public double getDoubleAndRemove(K k, double d) {
        Double doubleAndRemove = getDoubleAndRemove(k);
        return doubleAndRemove != null ? doubleAndRemove.doubleValue() : d;
    }

    @Override // io.netty.handler.codec.Headers
    public Long getTimeMillisAndRemove(K k) {
        V andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Long.valueOf(this.e.convertToTimeMillis(andRemove));
        }
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public long getTimeMillisAndRemove(K k, long j) {
        Long timeMillisAndRemove = getTimeMillisAndRemove(k);
        return timeMillisAndRemove != null ? timeMillisAndRemove.longValue() : j;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Headers)) {
            return false;
        }
        return equals((Headers) obj, HashingStrategy.JAVA_HASHER);
    }

    public int hashCode() {
        return hashCode(HashingStrategy.JAVA_HASHER);
    }

    public final boolean equals(Headers<K, V, ?> headers, HashingStrategy<V> hashingStrategy) {
        if (headers.size() != size()) {
            return false;
        }
        if (this == headers) {
            return true;
        }
        for (K k : names()) {
            List<V> all = headers.getAll(k);
            List<V> all2 = getAll(k);
            if (all.size() != all2.size()) {
                return false;
            }
            for (int i = 0; i < all.size(); i++) {
                if (!hashingStrategy.equals(all.get(i), all2.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public final int hashCode(HashingStrategy<V> hashingStrategy) {
        int i = -1028477387;
        for (K k : names()) {
            i = (i * 31) + this.g.hashCode(k);
            List<V> all = getAll(k);
            for (int i2 = 0; i2 < all.size(); i2++) {
                i = (i * 31) + hashingStrategy.hashCode(all.get(i2));
            }
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        String str = "";
        for (K k : names()) {
            List<V> all = getAll(k);
            for (int i = 0; i < all.size(); i++) {
                sb.append(str);
                sb.append(k);
                sb.append(": ");
                sb.append(all.get(i));
                str = ", ";
            }
        }
        sb.append(']');
        return sb.toString();
    }

    protected HeaderEntry<K, V> newHeaderEntry(int i, K k, V v, HeaderEntry<K, V> headerEntry) {
        return new HeaderEntry<>(i, k, v, headerEntry, this.head);
    }

    protected ValueConverter<V> valueConverter() {
        return this.e;
    }

    private int a(int i) {
        return i & this.d;
    }

    private void a(int i, int i2, K k, V v) {
        HeaderEntry<K, V>[] headerEntryArr = this.c;
        headerEntryArr[i2] = newHeaderEntry(i, k, v, headerEntryArr[i2]);
        this.a++;
    }

    private V a(int i, int i2, K k) {
        HeaderEntry<K, V> headerEntry = this.c[i2];
        V v = null;
        if (headerEntry == null) {
            return null;
        }
        for (HeaderEntry<K, V> headerEntry2 = headerEntry.next; headerEntry2 != null; headerEntry2 = headerEntry.next) {
            if (headerEntry2.hash != i || !this.g.equals(k, headerEntry2.key)) {
                headerEntry = headerEntry2;
            } else {
                v = headerEntry2.value;
                headerEntry.next = headerEntry2.next;
                headerEntry2.remove();
                this.a--;
            }
        }
        HeaderEntry<K, V> headerEntry3 = this.c[i2];
        if (headerEntry3.hash == i && this.g.equals(k, headerEntry3.key)) {
            if (v == null) {
                v = headerEntry3.value;
            }
            this.c[i2] = headerEntry3.next;
            headerEntry3.remove();
            this.a--;
        }
        return v;
    }

    /* loaded from: classes4.dex */
    public static final class HeaderDateFormat {
        private static final FastThreadLocal<HeaderDateFormat> a = new FastThreadLocal<HeaderDateFormat>() { // from class: io.netty.handler.codec.DefaultHeaders.HeaderDateFormat.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public HeaderDateFormat initialValue() {
                return new HeaderDateFormat();
            }
        };
        private final DateFormat b;
        private final DateFormat c;
        private final DateFormat d;

        /* JADX INFO: Access modifiers changed from: package-private */
        public static HeaderDateFormat a() {
            return a.get();
        }

        private HeaderDateFormat() {
            this.b = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            this.c = new SimpleDateFormat("E, dd-MMM-yy HH:mm:ss z", Locale.ENGLISH);
            this.d = new SimpleDateFormat("E MMM d HH:mm:ss yyyy", Locale.ENGLISH);
            TimeZone timeZone = TimeZone.getTimeZone("GMT");
            this.b.setTimeZone(timeZone);
            this.c.setTimeZone(timeZone);
            this.d.setTimeZone(timeZone);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public long a(String str) throws ParseException {
            Date parse = this.b.parse(str);
            if (parse == null) {
                parse = this.c.parse(str);
            }
            if (parse == null) {
                parse = this.d.parse(str);
            }
            if (parse != null) {
                return parse.getTime();
            }
            throw new ParseException(str, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements Iterator<Map.Entry<K, V>> {
        private HeaderEntry<K, V> b;

        private a() {
            this.b = DefaultHeaders.this.head;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b.after != DefaultHeaders.this.head;
        }

        /* renamed from: a */
        public Map.Entry<K, V> next() {
            this.b = this.b.after;
            if (this.b != DefaultHeaders.this.head) {
                return this.b;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("read-only iterator");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes4.dex */
    public static class HeaderEntry<K, V> implements Map.Entry<K, V> {
        protected HeaderEntry<K, V> after;
        protected HeaderEntry<K, V> before;
        protected final int hash;
        protected final K key;
        protected HeaderEntry<K, V> next;
        protected V value;

        /* JADX INFO: Access modifiers changed from: protected */
        public HeaderEntry(int i, K k) {
            this.hash = i;
            this.key = k;
        }

        HeaderEntry(int i, K k, V v, HeaderEntry<K, V> headerEntry, HeaderEntry<K, V> headerEntry2) {
            this.hash = i;
            this.key = k;
            this.value = v;
            this.next = headerEntry;
            this.after = headerEntry2;
            this.before = headerEntry2.before;
            pointNeighborsToThis();
        }

        HeaderEntry() {
            this.hash = -1;
            this.key = null;
            this.after = this;
            this.before = this;
        }

        protected final void pointNeighborsToThis() {
            this.before.after = this;
            this.after.before = this;
        }

        public final HeaderEntry<K, V> before() {
            return this.before;
        }

        public final HeaderEntry<K, V> after() {
            return this.after;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void remove() {
            HeaderEntry<K, V> headerEntry = this.before;
            headerEntry.after = this.after;
            this.after.before = headerEntry;
        }

        @Override // java.util.Map.Entry
        public final K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public final V setValue(V v) {
            ObjectUtil.checkNotNull(v, b.p);
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public final String toString() {
            return this.key.toString() + '=' + this.value.toString();
        }
    }
}
