package io.netty.handler.codec;

import io.netty.handler.codec.Headers;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class EmptyHeaders<K, V, T extends Headers<K, V, T>> implements Headers<K, V, T> {
    private T a() {
        return this;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean contains(K k) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean contains(K k, V v) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsBoolean(K k, boolean z) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsByte(K k, byte b) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsChar(K k, char c) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsDouble(K k, double d) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsFloat(K k, float f) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsInt(K k, int i) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsLong(K k, long j) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsObject(K k, Object obj) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsShort(K k, short s) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean containsTimeMillis(K k, long j) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public V get(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public V get(K k, V v) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public V getAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public V getAndRemove(K k, V v) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public Boolean getBoolean(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean getBoolean(K k, boolean z) {
        return z;
    }

    @Override // io.netty.handler.codec.Headers
    public Boolean getBooleanAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean getBooleanAndRemove(K k, boolean z) {
        return z;
    }

    @Override // io.netty.handler.codec.Headers
    public byte getByte(K k, byte b) {
        return b;
    }

    @Override // io.netty.handler.codec.Headers
    public Byte getByte(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public byte getByteAndRemove(K k, byte b) {
        return b;
    }

    @Override // io.netty.handler.codec.Headers
    public Byte getByteAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public char getChar(K k, char c) {
        return c;
    }

    @Override // io.netty.handler.codec.Headers
    public Character getChar(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public char getCharAndRemove(K k, char c) {
        return c;
    }

    @Override // io.netty.handler.codec.Headers
    public Character getCharAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public double getDouble(K k, double d) {
        return d;
    }

    @Override // io.netty.handler.codec.Headers
    public Double getDouble(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public double getDoubleAndRemove(K k, double d) {
        return d;
    }

    @Override // io.netty.handler.codec.Headers
    public Double getDoubleAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public float getFloat(K k, float f) {
        return f;
    }

    @Override // io.netty.handler.codec.Headers
    public Float getFloat(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public float getFloatAndRemove(K k, float f) {
        return f;
    }

    @Override // io.netty.handler.codec.Headers
    public Float getFloatAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public int getInt(K k, int i) {
        return i;
    }

    @Override // io.netty.handler.codec.Headers
    public Integer getInt(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public int getIntAndRemove(K k, int i) {
        return i;
    }

    @Override // io.netty.handler.codec.Headers
    public Integer getIntAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public long getLong(K k, long j) {
        return j;
    }

    @Override // io.netty.handler.codec.Headers
    public Long getLong(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public long getLongAndRemove(K k, long j) {
        return j;
    }

    @Override // io.netty.handler.codec.Headers
    public Long getLongAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public Short getShort(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public short getShort(K k, short s) {
        return s;
    }

    @Override // io.netty.handler.codec.Headers
    public Short getShortAndRemove(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public short getShortAndRemove(K k, short s) {
        return s;
    }

    @Override // io.netty.handler.codec.Headers
    public long getTimeMillis(K k, long j) {
        return j;
    }

    @Override // io.netty.handler.codec.Headers
    public Long getTimeMillis(K k) {
        return null;
    }

    @Override // io.netty.handler.codec.Headers
    public long getTimeMillisAndRemove(K k, long j) {
        return j;
    }

    @Override // io.netty.handler.codec.Headers
    public Long getTimeMillisAndRemove(K k) {
        return null;
    }

    public int hashCode() {
        return -1028477387;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean isEmpty() {
        return true;
    }

    @Override // io.netty.handler.codec.Headers
    public boolean remove(K k) {
        return false;
    }

    @Override // io.netty.handler.codec.Headers
    public int size() {
        return 0;
    }

    @Override // io.netty.handler.codec.Headers
    public List<V> getAll(K k) {
        return Collections.emptyList();
    }

    @Override // io.netty.handler.codec.Headers
    public List<V> getAllAndRemove(K k) {
        return Collections.emptyList();
    }

    @Override // io.netty.handler.codec.Headers
    public Set<K> names() {
        return Collections.emptySet();
    }

    @Override // io.netty.handler.codec.Headers
    public T add(K k, V v) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T add(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T add(K k, V... vArr) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addObject(K k, Object obj) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addObject(K k, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addObject(K k, Object... objArr) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addBoolean(K k, boolean z) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addByte(K k, byte b) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addChar(K k, char c) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addShort(K k, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addInt(K k, int i) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addLong(K k, long j) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addFloat(K k, float f) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addDouble(K k, double d) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T addTimeMillis(K k, long j) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T add(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T set(K k, V v) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T set(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T set(K k, V... vArr) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setObject(K k, Object obj) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setObject(K k, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setObject(K k, Object... objArr) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setBoolean(K k, boolean z) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setByte(K k, byte b) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setChar(K k, char c) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setShort(K k, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setInt(K k, int i) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setLong(K k, long j) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setFloat(K k, float f) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setDouble(K k, double d) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setTimeMillis(K k, long j) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T set(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T setAll(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override // io.netty.handler.codec.Headers
    public T clear() {
        return a();
    }

    @Override // io.netty.handler.codec.Headers, java.lang.Iterable
    public Iterator<Map.Entry<K, V>> iterator() {
        return Collections.emptyList().iterator();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Headers)) {
            return false;
        }
        return isEmpty() && ((Headers) obj).isEmpty();
    }

    public String toString() {
        return getClass().getSimpleName() + "[]";
    }
}
