package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ContentMetadataMutations {
    private final Map<String, Object> a = new HashMap();
    private final List<String> b = new ArrayList();

    public static ContentMetadataMutations setContentLength(ContentMetadataMutations contentMetadataMutations, long j) {
        return contentMetadataMutations.set(ContentMetadata.KEY_CONTENT_LENGTH, j);
    }

    public static ContentMetadataMutations setRedirectedUri(ContentMetadataMutations contentMetadataMutations, @Nullable Uri uri) {
        if (uri == null) {
            return contentMetadataMutations.remove(ContentMetadata.KEY_REDIRECTED_URI);
        }
        return contentMetadataMutations.set(ContentMetadata.KEY_REDIRECTED_URI, uri.toString());
    }

    public ContentMetadataMutations set(String str, String str2) {
        return a(str, str2);
    }

    public ContentMetadataMutations set(String str, long j) {
        return a(str, Long.valueOf(j));
    }

    public ContentMetadataMutations set(String str, byte[] bArr) {
        return a(str, Arrays.copyOf(bArr, bArr.length));
    }

    public ContentMetadataMutations remove(String str) {
        this.b.add(str);
        this.a.remove(str);
        return this;
    }

    public List<String> getRemovedValues() {
        return Collections.unmodifiableList(new ArrayList(this.b));
    }

    public Map<String, Object> getEditedValues() {
        HashMap hashMap = new HashMap(this.a);
        for (Map.Entry entry : hashMap.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof byte[]) {
                byte[] bArr = (byte[]) value;
                entry.setValue(Arrays.copyOf(bArr, bArr.length));
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    private ContentMetadataMutations a(String str, Object obj) {
        this.a.put((String) Assertions.checkNotNull(str), Assertions.checkNotNull(obj));
        this.b.remove(str);
        return this;
    }
}
