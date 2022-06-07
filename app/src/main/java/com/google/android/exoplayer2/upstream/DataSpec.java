package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class DataSpec {
    public static final int FLAG_ALLOW_CACHE_FRAGMENTATION = 4;
    public static final int FLAG_ALLOW_GZIP = 1;
    public static final int FLAG_DONT_CACHE_IF_LENGTH_UNKNOWN = 2;
    public static final int FLAG_MIGHT_NOT_USE_FULL_NETWORK_SPEED = 8;
    public static final int HTTP_METHOD_GET = 1;
    public static final int HTTP_METHOD_HEAD = 3;
    public static final int HTTP_METHOD_POST = 2;
    @Deprecated
    public final long absoluteStreamPosition;
    @Nullable
    public final Object customData;
    public final int flags;
    @Nullable
    public final byte[] httpBody;
    public final int httpMethod;
    public final Map<String, String> httpRequestHeaders;
    @Nullable
    public final String key;
    public final long length;
    public final long position;
    public final Uri uri;
    public final long uriPositionOffset;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Flags {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface HttpMethod {
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        @Nullable
        private Uri a;
        private long b;
        private int c;
        @Nullable
        private byte[] d;
        private Map<String, String> e;
        private long f;
        private long g;
        @Nullable
        private String h;
        private int i;
        @Nullable
        private Object j;

        public Builder() {
            this.c = 1;
            this.e = Collections.emptyMap();
            this.g = -1L;
        }

        private Builder(DataSpec dataSpec) {
            this.a = dataSpec.uri;
            this.b = dataSpec.uriPositionOffset;
            this.c = dataSpec.httpMethod;
            this.d = dataSpec.httpBody;
            this.e = dataSpec.httpRequestHeaders;
            this.f = dataSpec.position;
            this.g = dataSpec.length;
            this.h = dataSpec.key;
            this.i = dataSpec.flags;
            this.j = dataSpec.customData;
        }

        public Builder setUri(String str) {
            this.a = Uri.parse(str);
            return this;
        }

        public Builder setUri(Uri uri) {
            this.a = uri;
            return this;
        }

        public Builder setUriPositionOffset(long j) {
            this.b = j;
            return this;
        }

        public Builder setHttpMethod(int i) {
            this.c = i;
            return this;
        }

        public Builder setHttpBody(@Nullable byte[] bArr) {
            this.d = bArr;
            return this;
        }

        public Builder setHttpRequestHeaders(Map<String, String> map) {
            this.e = map;
            return this;
        }

        public Builder setPosition(long j) {
            this.f = j;
            return this;
        }

        public Builder setLength(long j) {
            this.g = j;
            return this;
        }

        public Builder setKey(@Nullable String str) {
            this.h = str;
            return this;
        }

        public Builder setFlags(int i) {
            this.i = i;
            return this;
        }

        public Builder setCustomData(@Nullable Object obj) {
            this.j = obj;
            return this;
        }

        public DataSpec build() {
            Assertions.checkStateNotNull(this.a, "The uri must be set.");
            return new DataSpec(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
        }
    }

    public static String getStringForHttpMethod(int i) {
        switch (i) {
            case 1:
                return "GET";
            case 2:
                return "POST";
            case 3:
                return "HEAD";
            default:
                throw new IllegalStateException();
        }
    }

    public DataSpec(Uri uri) {
        this(uri, 0L, -1L);
    }

    public DataSpec(Uri uri, long j, long j2) {
        this(uri, 0L, 1, null, Collections.emptyMap(), j, j2, null, 0, null);
    }

    @Deprecated
    public DataSpec(Uri uri, int i) {
        this(uri, 0L, -1L, null, i);
    }

    @Deprecated
    public DataSpec(Uri uri, long j, long j2, @Nullable String str) {
        this(uri, j, j, j2, str, 0);
    }

    @Deprecated
    public DataSpec(Uri uri, long j, long j2, @Nullable String str, int i) {
        this(uri, j, j, j2, str, i);
    }

    @Deprecated
    public DataSpec(Uri uri, long j, long j2, @Nullable String str, int i, Map<String, String> map) {
        this(uri, 1, null, j, j, j2, str, i, map);
    }

    @Deprecated
    public DataSpec(Uri uri, long j, long j2, long j3, @Nullable String str, int i) {
        this(uri, null, j, j2, j3, str, i);
    }

    @Deprecated
    public DataSpec(Uri uri, @Nullable byte[] bArr, long j, long j2, long j3, @Nullable String str, int i) {
        this(uri, bArr != null ? 2 : 1, bArr, j, j2, j3, str, i);
    }

    @Deprecated
    public DataSpec(Uri uri, int i, @Nullable byte[] bArr, long j, long j2, long j3, @Nullable String str, int i2) {
        this(uri, i, bArr, j, j2, j3, str, i2, Collections.emptyMap());
    }

    @Deprecated
    public DataSpec(Uri uri, int i, @Nullable byte[] bArr, long j, long j2, long j3, @Nullable String str, int i2, Map<String, String> map) {
        this(uri, j - j2, i, bArr, map, j2, j3, str, i2, null);
    }

    private DataSpec(Uri uri, long j, int i, @Nullable byte[] bArr, Map<String, String> map, long j2, long j3, @Nullable String str, int i2, @Nullable Object obj) {
        byte[] bArr2 = bArr;
        long j4 = j + j2;
        boolean z = true;
        Assertions.checkArgument(j4 >= 0);
        Assertions.checkArgument(j2 >= 0);
        if (j3 <= 0 && j3 != -1) {
            z = false;
        }
        Assertions.checkArgument(z);
        this.uri = uri;
        this.uriPositionOffset = j;
        this.httpMethod = i;
        this.httpBody = (bArr2 == null || bArr2.length == 0) ? null : bArr2;
        this.httpRequestHeaders = Collections.unmodifiableMap(new HashMap(map));
        this.position = j2;
        this.absoluteStreamPosition = j4;
        this.length = j3;
        this.key = str;
        this.flags = i2;
        this.customData = obj;
    }

    public boolean isFlagSet(int i) {
        return (this.flags & i) == i;
    }

    public final String getHttpMethodString() {
        return getStringForHttpMethod(this.httpMethod);
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public DataSpec subrange(long j) {
        long j2 = this.length;
        long j3 = -1;
        if (j2 != -1) {
            j3 = j2 - j;
        }
        return subrange(j, j3);
    }

    public DataSpec subrange(long j, long j2) {
        if (j == 0 && this.length == j2) {
            return this;
        }
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position + j, j2, this.key, this.flags, this.customData);
    }

    public DataSpec withUri(Uri uri) {
        return new DataSpec(uri, this.uriPositionOffset, this.httpMethod, this.httpBody, this.httpRequestHeaders, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec withRequestHeaders(Map<String, String> map) {
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, map, this.position, this.length, this.key, this.flags, this.customData);
    }

    public DataSpec withAdditionalHeaders(Map<String, String> map) {
        HashMap hashMap = new HashMap(this.httpRequestHeaders);
        hashMap.putAll(map);
        return new DataSpec(this.uri, this.uriPositionOffset, this.httpMethod, this.httpBody, hashMap, this.position, this.length, this.key, this.flags, this.customData);
    }

    public String toString() {
        String httpMethodString = getHttpMethodString();
        String valueOf = String.valueOf(this.uri);
        long j = this.position;
        long j2 = this.length;
        String str = this.key;
        int i = this.flags;
        StringBuilder sb = new StringBuilder(String.valueOf(httpMethodString).length() + 70 + String.valueOf(valueOf).length() + String.valueOf(str).length());
        sb.append("DataSpec[");
        sb.append(httpMethodString);
        sb.append(StringUtils.SPACE);
        sb.append(valueOf);
        sb.append(", ");
        sb.append(j);
        sb.append(", ");
        sb.append(j2);
        sb.append(", ");
        sb.append(str);
        sb.append(", ");
        sb.append(i);
        sb.append("]");
        return sb.toString();
    }
}
