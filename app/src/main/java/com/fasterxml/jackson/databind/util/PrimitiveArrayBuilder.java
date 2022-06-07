package com.fasterxml.jackson.databind.util;

/* loaded from: classes.dex */
public abstract class PrimitiveArrayBuilder<T> {
    protected a<T> _bufferHead;
    protected a<T> _bufferTail;
    protected int _bufferedEntryCount;
    protected T _freeBuffer;

    protected abstract T _constructArray(int i);

    public int bufferedSize() {
        return this._bufferedEntryCount;
    }

    public T resetAndStart() {
        _reset();
        T t = this._freeBuffer;
        return t == null ? _constructArray(12) : t;
    }

    public final T appendCompletedChunk(T t, int i) {
        a<T> aVar = new a<>(t, i);
        if (this._bufferHead == null) {
            this._bufferTail = aVar;
            this._bufferHead = aVar;
        } else {
            this._bufferTail.a(aVar);
            this._bufferTail = aVar;
        }
        this._bufferedEntryCount += i;
        return _constructArray(i < 16384 ? i + i : i + (i >> 2));
    }

    public T completeAndClearBuffer(T t, int i) {
        int i2 = this._bufferedEntryCount + i;
        T _constructArray = _constructArray(i2);
        int i3 = 0;
        for (a<T> aVar = this._bufferHead; aVar != null; aVar = aVar.b()) {
            i3 = aVar.a(_constructArray, i3);
        }
        System.arraycopy(t, 0, _constructArray, i3, i);
        int i4 = i3 + i;
        if (i4 == i2) {
            return _constructArray;
        }
        throw new IllegalStateException("Should have gotten " + i2 + " entries, got " + i4);
    }

    protected void _reset() {
        a<T> aVar = this._bufferTail;
        if (aVar != null) {
            this._freeBuffer = aVar.a();
        }
        this._bufferTail = null;
        this._bufferHead = null;
        this._bufferedEntryCount = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class a<T> {
        final T a;
        final int b;
        a<T> c;

        public a(T t, int i) {
            this.a = t;
            this.b = i;
        }

        public T a() {
            return this.a;
        }

        public int a(T t, int i) {
            System.arraycopy(this.a, 0, t, i, this.b);
            return i + this.b;
        }

        public a<T> b() {
            return this.c;
        }

        public void a(a<T> aVar) {
            if (this.c == null) {
                this.c = aVar;
                return;
            }
            throw new IllegalStateException();
        }
    }
}
