package com.bumptech.glide.load.model;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class LazyHeaders implements Headers {
    private final Map<String, List<LazyHeaderFactory>> a;
    private volatile Map<String, String> b;

    LazyHeaders(Map<String, List<LazyHeaderFactory>> map) {
        this.a = Collections.unmodifiableMap(map);
    }

    @Override // com.bumptech.glide.load.model.Headers
    public Map<String, String> getHeaders() {
        if (this.b == null) {
            synchronized (this) {
                if (this.b == null) {
                    this.b = Collections.unmodifiableMap(a());
                }
            }
        }
        return this.b;
    }

    private Map<String, String> a() {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, List<LazyHeaderFactory>> entry : this.a.entrySet()) {
            String a2 = a(entry.getValue());
            if (!TextUtils.isEmpty(a2)) {
                hashMap.put(entry.getKey(), a2);
            }
        }
        return hashMap;
    }

    @NonNull
    private String a(@NonNull List<LazyHeaderFactory> list) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String buildHeader = list.get(i).buildHeader();
            if (!TextUtils.isEmpty(buildHeader)) {
                sb.append(buildHeader);
                if (i != list.size() - 1) {
                    sb.append(StringUtil.COMMA);
                }
            }
        }
        return sb.toString();
    }

    public String toString() {
        return "LazyHeaders{headers=" + this.a + '}';
    }

    public boolean equals(Object obj) {
        if (obj instanceof LazyHeaders) {
            return this.a.equals(((LazyHeaders) obj).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private static final String a = a();
        private static final Map<String, List<LazyHeaderFactory>> b;
        private boolean c = true;
        private Map<String, List<LazyHeaderFactory>> d = b;
        private boolean e = true;

        static {
            HashMap hashMap = new HashMap(2);
            if (!TextUtils.isEmpty(a)) {
                hashMap.put("User-Agent", Collections.singletonList(new a(a)));
            }
            b = Collections.unmodifiableMap(hashMap);
        }

        public Builder addHeader(@NonNull String str, @NonNull String str2) {
            return addHeader(str, new a(str2));
        }

        public Builder addHeader(@NonNull String str, @NonNull LazyHeaderFactory lazyHeaderFactory) {
            if (this.e && "User-Agent".equalsIgnoreCase(str)) {
                return setHeader(str, lazyHeaderFactory);
            }
            b();
            a(str).add(lazyHeaderFactory);
            return this;
        }

        public Builder setHeader(@NonNull String str, @Nullable String str2) {
            return setHeader(str, str2 == null ? null : new a(str2));
        }

        public Builder setHeader(@NonNull String str, @Nullable LazyHeaderFactory lazyHeaderFactory) {
            b();
            if (lazyHeaderFactory == null) {
                this.d.remove(str);
            } else {
                List<LazyHeaderFactory> a2 = a(str);
                a2.clear();
                a2.add(lazyHeaderFactory);
            }
            if (this.e && "User-Agent".equalsIgnoreCase(str)) {
                this.e = false;
            }
            return this;
        }

        private List<LazyHeaderFactory> a(String str) {
            List<LazyHeaderFactory> list = this.d.get(str);
            if (list != null) {
                return list;
            }
            ArrayList arrayList = new ArrayList();
            this.d.put(str, arrayList);
            return arrayList;
        }

        private void b() {
            if (this.c) {
                this.c = false;
                this.d = c();
            }
        }

        public LazyHeaders build() {
            this.c = true;
            return new LazyHeaders(this.d);
        }

        private Map<String, List<LazyHeaderFactory>> c() {
            HashMap hashMap = new HashMap(this.d.size());
            for (Map.Entry<String, List<LazyHeaderFactory>> entry : this.d.entrySet()) {
                hashMap.put(entry.getKey(), new ArrayList(entry.getValue()));
            }
            return hashMap;
        }

        @VisibleForTesting
        static String a() {
            String property = System.getProperty("http.agent");
            if (TextUtils.isEmpty(property)) {
                return property;
            }
            int length = property.length();
            StringBuilder sb = new StringBuilder(property.length());
            for (int i = 0; i < length; i++) {
                char charAt = property.charAt(i);
                if ((charAt > 31 || charAt == '\t') && charAt < 127) {
                    sb.append(charAt);
                } else {
                    sb.append('?');
                }
            }
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    static final class a implements LazyHeaderFactory {
        @NonNull
        private final String a;

        a(@NonNull String str) {
            this.a = str;
        }

        @Override // com.bumptech.glide.load.model.LazyHeaderFactory
        public String buildHeader() {
            return this.a;
        }

        public String toString() {
            return "StringHeaderFactory{value='" + this.a + "'}";
        }

        public boolean equals(Object obj) {
            if (obj instanceof a) {
                return this.a.equals(((a) obj).a);
            }
            return false;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }
}
