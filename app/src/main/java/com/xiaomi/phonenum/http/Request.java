package com.xiaomi.phonenum.http;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.phonenum.utils.MapUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class Request {
    public final boolean followRedirects;
    public final Map<String, String> formBody;
    public final Map<String, String> headers;
    public final URI uri;
    public final String url;

    private Request(Builder builder) {
        this.uri = builder.a;
        this.url = builder.a.toString();
        this.headers = builder.b;
        this.formBody = builder.c;
        this.followRedirects = builder.d;
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        URI a;
        Map<String, String> b;
        Map<String, String> c;
        boolean d = true;

        public Builder url(String str) {
            try {
                this.a = new URI(str);
                return this;
            } catch (URISyntaxException unused) {
                throw new IllegalArgumentException("unexpected url: " + str);
            }
        }

        public Builder appendQuery(@Nullable String str) {
            String str2;
            if (TextUtils.isEmpty(str)) {
                return this;
            }
            URI uri = this.a;
            String query = uri.getQuery();
            if (query == null) {
                str2 = str;
            } else {
                str2 = query + MusicGroupListActivity.SPECIAL_SYMBOL + str;
            }
            try {
                this.a = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), str2, uri.getFragment());
                return this;
            } catch (URISyntaxException unused) {
                throw new IllegalArgumentException("unexpected newQuery: " + str);
            }
        }

        public Builder queryParams(@Nullable Map<String, String> map) {
            appendQuery(MapUtil.joinToQuery(map));
            return this;
        }

        public Builder ua(@Nullable String str) {
            if (this.b == null) {
                this.b = new HashMap();
            }
            this.b.put("User-Agent", str);
            return this;
        }

        public Builder headers(@Nullable Map<String, String> map) {
            this.b = map;
            return this;
        }

        public Builder formBody(@Nullable Map<String, String> map) {
            this.c = map;
            return this;
        }

        public Builder followRedirects(boolean z) {
            this.d = z;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
