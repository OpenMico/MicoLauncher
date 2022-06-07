package com.xiaomi.account.http;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/* loaded from: classes2.dex */
public class Request {
    public final boolean followRedirects;
    public final Map<String, String> formBody;
    public final Map<String, String> headers;
    public final URI uri;
    public final String url;

    private Request(Builder builder) {
        this.uri = builder.uri;
        this.url = builder.uri.toString();
        this.headers = builder.headers;
        this.formBody = builder.formBody;
        this.followRedirects = builder.followRedirects;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        boolean followRedirects = true;
        Map<String, String> formBody;
        Map<String, String> headers;
        URI uri;

        public Builder url(String str) {
            try {
                this.uri = new URI(str);
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
            URI uri = this.uri;
            String query = uri.getQuery();
            if (query == null) {
                str2 = str;
            } else {
                str2 = query + MusicGroupListActivity.SPECIAL_SYMBOL + str;
            }
            try {
                this.uri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), str2, uri.getFragment());
                return this;
            } catch (URISyntaxException unused) {
                throw new IllegalArgumentException("unexpected newQuery: " + str);
            }
        }

        public Builder headers(@Nullable Map<String, String> map) {
            this.headers = map;
            return this;
        }

        public Builder formBody(@Nullable Map<String, String> map) {
            this.formBody = map;
            return this;
        }

        public Builder followRedirects(boolean z) {
            this.followRedirects = z;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
