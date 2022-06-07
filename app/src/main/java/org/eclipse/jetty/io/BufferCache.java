package org.eclipse.jetty.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.StringMap;

/* loaded from: classes5.dex */
public class BufferCache {
    private final HashMap _bufferMap = new HashMap();
    private final StringMap _stringMap = new StringMap(true);
    private final ArrayList _index = new ArrayList();

    public CachedBuffer add(String str, int i) {
        CachedBuffer cachedBuffer = new CachedBuffer(str, i);
        this._bufferMap.put(cachedBuffer, cachedBuffer);
        this._stringMap.put(str, (Object) cachedBuffer);
        while (i - this._index.size() >= 0) {
            this._index.add(null);
        }
        if (this._index.get(i) == null) {
            this._index.add(i, cachedBuffer);
        }
        return cachedBuffer;
    }

    public CachedBuffer get(int i) {
        if (i < 0 || i >= this._index.size()) {
            return null;
        }
        return (CachedBuffer) this._index.get(i);
    }

    public CachedBuffer get(Buffer buffer) {
        return (CachedBuffer) this._bufferMap.get(buffer);
    }

    public CachedBuffer get(String str) {
        return (CachedBuffer) this._stringMap.get(str);
    }

    public Buffer lookup(Buffer buffer) {
        if (buffer instanceof CachedBuffer) {
            return buffer;
        }
        CachedBuffer cachedBuffer = get(buffer);
        return cachedBuffer == null ? buffer instanceof Buffer.CaseInsensitve ? buffer : new ByteArrayBuffer.CaseInsensitive(buffer.asArray(), 0, buffer.length(), 0) : cachedBuffer;
    }

    public CachedBuffer getBest(byte[] bArr, int i, int i2) {
        Map.Entry bestEntry = this._stringMap.getBestEntry(bArr, i, i2);
        if (bestEntry != null) {
            return (CachedBuffer) bestEntry.getValue();
        }
        return null;
    }

    public Buffer lookup(String str) {
        CachedBuffer cachedBuffer = get(str);
        return cachedBuffer == null ? new CachedBuffer(str, -1) : cachedBuffer;
    }

    public String toString(Buffer buffer) {
        return lookup(buffer).toString();
    }

    public int getOrdinal(String str) {
        CachedBuffer cachedBuffer = (CachedBuffer) this._stringMap.get(str);
        if (cachedBuffer == null) {
            return -1;
        }
        return cachedBuffer.getOrdinal();
    }

    public int getOrdinal(Buffer buffer) {
        if (buffer instanceof CachedBuffer) {
            return ((CachedBuffer) buffer).getOrdinal();
        }
        Buffer lookup = lookup(buffer);
        if (lookup == null || !(lookup instanceof CachedBuffer)) {
            return -1;
        }
        return ((CachedBuffer) lookup).getOrdinal();
    }

    /* loaded from: classes5.dex */
    public static class CachedBuffer extends ByteArrayBuffer.CaseInsensitive {
        private HashMap _associateMap = null;
        private final int _ordinal;

        public CachedBuffer(String str, int i) {
            super(str);
            this._ordinal = i;
        }

        public int getOrdinal() {
            return this._ordinal;
        }

        public CachedBuffer getAssociate(Object obj) {
            HashMap hashMap = this._associateMap;
            if (hashMap == null) {
                return null;
            }
            return (CachedBuffer) hashMap.get(obj);
        }

        public void setAssociate(Object obj, CachedBuffer cachedBuffer) {
            if (this._associateMap == null) {
                this._associateMap = new HashMap();
            }
            this._associateMap.put(obj, cachedBuffer);
        }
    }

    public String toString() {
        return "CACHE[bufferMap=" + this._bufferMap + ",stringMap=" + this._stringMap + ",index=" + this._index + "]";
    }
}
