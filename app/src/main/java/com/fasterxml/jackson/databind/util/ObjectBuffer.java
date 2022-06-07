package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;
import java.util.List;

/* loaded from: classes.dex */
public final class ObjectBuffer {
    private LinkedNode<Object[]> a;
    private LinkedNode<Object[]> b;
    private int c;
    private Object[] d;

    public Object[] resetAndStart() {
        _reset();
        Object[] objArr = this.d;
        if (objArr != null) {
            return objArr;
        }
        Object[] objArr2 = new Object[12];
        this.d = objArr2;
        return objArr2;
    }

    public Object[] resetAndStart(Object[] objArr, int i) {
        _reset();
        Object[] objArr2 = this.d;
        if (objArr2 == null || objArr2.length < i) {
            this.d = new Object[Math.max(12, i)];
        }
        System.arraycopy(objArr, 0, this.d, 0, i);
        return this.d;
    }

    public Object[] appendCompletedChunk(Object[] objArr) {
        LinkedNode<Object[]> linkedNode = new LinkedNode<>(objArr, null);
        if (this.a == null) {
            this.b = linkedNode;
            this.a = linkedNode;
        } else {
            this.b.linkNext(linkedNode);
            this.b = linkedNode;
        }
        int length = objArr.length;
        this.c += length;
        if (length < 16384) {
            length += length;
        } else if (length < 262144) {
            length += length >> 2;
        }
        return new Object[length];
    }

    public Object[] completeAndClearBuffer(Object[] objArr, int i) {
        int i2 = this.c + i;
        Object[] objArr2 = new Object[i2];
        _copyTo(objArr2, i2, objArr, i);
        _reset();
        return objArr2;
    }

    public <T> T[] completeAndClearBuffer(Object[] objArr, int i, Class<T> cls) {
        int i2 = this.c + i;
        T[] tArr = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i2));
        _copyTo(tArr, i2, objArr, i);
        _reset();
        return tArr;
    }

    public void completeAndClearBuffer(Object[] objArr, int i, List<Object> list) {
        int i2;
        LinkedNode<Object[]> linkedNode = this.a;
        while (true) {
            i2 = 0;
            if (linkedNode != null) {
                Object[] value = linkedNode.value();
                int length = value.length;
                while (i2 < length) {
                    list.add(value[i2]);
                    i2++;
                }
                linkedNode = linkedNode.next();
            }
        }
        while (i2 < i) {
            list.add(objArr[i2]);
            i2++;
        }
        _reset();
    }

    public int initialCapacity() {
        Object[] objArr = this.d;
        if (objArr == null) {
            return 0;
        }
        return objArr.length;
    }

    public int bufferedSize() {
        return this.c;
    }

    protected void _reset() {
        LinkedNode<Object[]> linkedNode = this.b;
        if (linkedNode != null) {
            this.d = linkedNode.value();
        }
        this.b = null;
        this.a = null;
        this.c = 0;
    }

    protected final void _copyTo(Object obj, int i, Object[] objArr, int i2) {
        int i3 = 0;
        for (LinkedNode<Object[]> linkedNode = this.a; linkedNode != null; linkedNode = linkedNode.next()) {
            Object[] value = linkedNode.value();
            int length = value.length;
            System.arraycopy(value, 0, obj, i3, length);
            i3 += length;
        }
        System.arraycopy(objArr, 0, obj, i3, i2);
        int i4 = i3 + i2;
        if (i4 != i) {
            throw new IllegalStateException("Should have gotten " + i + " entries, got " + i4);
        }
    }
}
