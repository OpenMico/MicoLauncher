package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public final class DefaultContentMetadata implements ContentMetadata {
    public static final DefaultContentMetadata EMPTY = new DefaultContentMetadata(Collections.emptyMap());
    private int a;
    private final Map<String, byte[]> b;

    public DefaultContentMetadata() {
        this(Collections.emptyMap());
    }

    public DefaultContentMetadata(Map<String, byte[]> map) {
        this.b = Collections.unmodifiableMap(map);
    }

    public DefaultContentMetadata copyWithMutationsApplied(ContentMetadataMutations contentMetadataMutations) {
        Map<String, byte[]> a = a(this.b, contentMetadataMutations);
        return a(this.b, a) ? this : new DefaultContentMetadata(a);
    }

    public Set<Map.Entry<String, byte[]>> entrySet() {
        return this.b.entrySet();
    }

    @Override // com.google.android.exoplayer2.upstream.cache.ContentMetadata
    @Nullable
    public final byte[] get(String str, @Nullable byte[] bArr) {
        byte[] bArr2 = this.b.get(str);
        return bArr2 != null ? Arrays.copyOf(bArr2, bArr2.length) : bArr;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.ContentMetadata
    @Nullable
    public final String get(String str, @Nullable String str2) {
        byte[] bArr = this.b.get(str);
        return bArr != null ? new String(bArr, Charsets.UTF_8) : str2;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.ContentMetadata
    public final long get(String str, long j) {
        byte[] bArr = this.b.get(str);
        return bArr != null ? ByteBuffer.wrap(bArr).getLong() : j;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.ContentMetadata
    public final boolean contains(String str) {
        return this.b.containsKey(str);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return a(this.b, ((DefaultContentMetadata) obj).b);
    }

    public int hashCode() {
        if (this.a == 0) {
            int i = 0;
            for (Map.Entry<String, byte[]> entry : this.b.entrySet()) {
                i += Arrays.hashCode(entry.getValue()) ^ entry.getKey().hashCode();
            }
            this.a = i;
        }
        return this.a;
    }

    private static boolean a(Map<String, byte[]> map, Map<String, byte[]> map2) {
        if (map.size() != map2.size()) {
            return false;
        }
        for (Map.Entry<String, byte[]> entry : map.entrySet()) {
            if (!Arrays.equals(entry.getValue(), map2.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    private static Map<String, byte[]> a(Map<String, byte[]> map, ContentMetadataMutations contentMetadataMutations) {
        HashMap hashMap = new HashMap(map);
        a(hashMap, contentMetadataMutations.getRemovedValues());
        a(hashMap, contentMetadataMutations.getEditedValues());
        return hashMap;
    }

    private static void a(HashMap<String, byte[]> hashMap, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            hashMap.remove(list.get(i));
        }
    }

    private static void a(HashMap<String, byte[]> hashMap, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), a(entry.getValue()));
        }
    }

    private static byte[] a(Object obj) {
        if (obj instanceof Long) {
            return ByteBuffer.allocate(8).putLong(((Long) obj).longValue()).array();
        }
        if (obj instanceof String) {
            return ((String) obj).getBytes(Charsets.UTF_8);
        }
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        throw new IllegalArgumentException();
    }
}
