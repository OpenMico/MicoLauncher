package okhttp3;

import androidx.exifinterface.media.ExifInterface;
import com.milink.base.contract.MiLinkKeys;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.api.b;
import com.zhy.http.okhttp.OkHttpUtils;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;
import org.eclipse.jetty.util.URIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Request.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001*BA\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0016\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\u00010\u000b¢\u0006\u0002\u0010\rJ\u000f\u0010\b\u001a\u0004\u0018\u00010\tH\u0007¢\u0006\u0002\b\u001bJ\r\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\b\u001cJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001e\u001a\u00020\u0005J\r\u0010\u0006\u001a\u00020\u0007H\u0007¢\u0006\u0002\b\u001fJ\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050 2\u0006\u0010\u001e\u001a\u00020\u0005J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b!J\u0006\u0010\"\u001a\u00020#J\b\u0010$\u001a\u0004\u0018\u00010\u0001J#\u0010$\u001a\u0004\u0018\u0001H%\"\u0004\b\u0000\u0010%2\u000e\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u0002H%0\f¢\u0006\u0002\u0010'J\b\u0010(\u001a\u00020\u0005H\u0016J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b)R\u0015\u0010\b\u001a\u0004\u0018\u00010\t8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0013\u0010\u0006\u001a\u00020\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0017R$\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\u00010\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001a¨\u0006+"}, d2 = {"Lokhttp3/Request;", "", "url", "Lokhttp3/HttpUrl;", SchemaActivity.KEY_METHOD, "", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/RequestBody;", "tags", "", "Ljava/lang/Class;", "(Lokhttp3/HttpUrl;Ljava/lang/String;Lokhttp3/Headers;Lokhttp3/RequestBody;Ljava/util/Map;)V", "()Lokhttp3/RequestBody;", "cacheControl", "Lokhttp3/CacheControl;", "()Lokhttp3/CacheControl;", "()Lokhttp3/Headers;", "isHttps", "", "()Z", "lazyCacheControl", "()Ljava/lang/String;", "getTags$okhttp", "()Ljava/util/Map;", "()Lokhttp3/HttpUrl;", "-deprecated_body", "-deprecated_cacheControl", "header", "name", "-deprecated_headers", "", "-deprecated_method", "newBuilder", "Lokhttp3/Request$Builder;", MiLinkKeys.PARAM_TAG, ExifInterface.GPS_DIRECTION_TRUE, "type", "(Ljava/lang/Class;)Ljava/lang/Object;", "toString", "-deprecated_url", "Builder", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class Request {
    @Nullable
    private final RequestBody body;
    @NotNull
    private final Headers headers;
    private CacheControl lazyCacheControl;
    @NotNull
    private final String method;
    @NotNull
    private final Map<Class<?>, Object> tags;
    @NotNull
    private final HttpUrl url;

    public Request(@NotNull HttpUrl url, @NotNull String method, @NotNull Headers headers, @Nullable RequestBody requestBody, @NotNull Map<Class<?>, ? extends Object> tags) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(headers, "headers");
        Intrinsics.checkParameterIsNotNull(tags, "tags");
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = requestBody;
        this.tags = tags;
    }

    @JvmName(name = "url")
    @NotNull
    public final HttpUrl url() {
        return this.url;
    }

    @JvmName(name = SchemaActivity.KEY_METHOD)
    @NotNull
    public final String method() {
        return this.method;
    }

    @JvmName(name = "headers")
    @NotNull
    public final Headers headers() {
        return this.headers;
    }

    @JvmName(name = "body")
    @Nullable
    public final RequestBody body() {
        return this.body;
    }

    @NotNull
    public final Map<Class<?>, Object> getTags$okhttp() {
        return this.tags;
    }

    public final boolean isHttps() {
        return this.url.isHttps();
    }

    @Nullable
    public final String header(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return this.headers.get(name);
    }

    @NotNull
    public final List<String> headers(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return this.headers.values(name);
    }

    @Nullable
    public final Object tag() {
        return tag(Object.class);
    }

    @Nullable
    public final <T> T tag(@NotNull Class<? extends T> type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return (T) type.cast(this.tags.get(type));
    }

    @NotNull
    public final Builder newBuilder() {
        return new Builder(this);
    }

    @JvmName(name = "cacheControl")
    @NotNull
    public final CacheControl cacheControl() {
        CacheControl cacheControl = this.lazyCacheControl;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl parse = CacheControl.Companion.parse(this.headers);
        this.lazyCacheControl = parse;
        return parse;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "url", imports = {}))
    @JvmName(name = "-deprecated_url")
    @NotNull
    /* renamed from: -deprecated_url */
    public final HttpUrl m1927deprecated_url() {
        return this.url;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "method", imports = {}))
    @JvmName(name = "-deprecated_method")
    @NotNull
    /* renamed from: -deprecated_method */
    public final String m1926deprecated_method() {
        return this.method;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "headers", imports = {}))
    @JvmName(name = "-deprecated_headers")
    @NotNull
    /* renamed from: -deprecated_headers */
    public final Headers m1925deprecated_headers() {
        return this.headers;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "body", imports = {}))
    @JvmName(name = "-deprecated_body")
    @Nullable
    /* renamed from: -deprecated_body */
    public final RequestBody m1923deprecated_body() {
        return this.body;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cacheControl", imports = {}))
    @JvmName(name = "-deprecated_cacheControl")
    @NotNull
    /* renamed from: -deprecated_cacheControl */
    public final CacheControl m1924deprecated_cacheControl() {
        return cacheControl();
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request{method=");
        sb.append(this.method);
        sb.append(", url=");
        sb.append(this.url);
        if (this.headers.size() != 0) {
            sb.append(", headers=[");
            int i = 0;
            for (Pair<? extends String, ? extends String> pair : this.headers) {
                i++;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Pair<? extends String, ? extends String> pair2 = pair;
                String str = (String) pair2.component1();
                String str2 = (String) pair2.component2();
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(str);
                sb.append(':');
                sb.append(str2);
            }
            sb.append(']');
        }
        if (!this.tags.isEmpty()) {
            sb.append(", tags=");
            sb.append(this.tags);
        }
        sb.append('}');
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* compiled from: Request.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u000f\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010%\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u0013H\u0016J\b\u0010(\u001a\u00020\u0004H\u0016J\u0010\u0010)\u001a\u00020\u00002\u0006\u0010)\u001a\u00020*H\u0016J\u0014\u0010+\u001a\u00020\u00002\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0017J\b\u0010,\u001a\u00020\u0000H\u0016J\b\u0010-\u001a\u00020\u0000H\u0016J\u0018\u0010.\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u0013H\u0016J\u0010\u0010\f\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020/H\u0016J\u001a\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u00100\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00101\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00102\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00103\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0013H\u0016J-\u00104\u001a\u00020\u0000\"\u0004\b\u0000\u001052\u000e\u00106\u001a\n\u0012\u0006\b\u0000\u0012\u0002H50\u001a2\b\u00104\u001a\u0004\u0018\u0001H5H\u0016¢\u0006\u0002\u00107J\u0012\u00104\u001a\u00020\u00002\b\u00104\u001a\u0004\u0018\u00010\u0001H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u000208H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\u0013H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R*\u0010\u0018\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a\u0012\u0004\u0012\u00020\u00010\u0019X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u00069"}, d2 = {"Lokhttp3/Request$Builder;", "", "()V", "request", "Lokhttp3/Request;", "(Lokhttp3/Request;)V", "body", "Lokhttp3/RequestBody;", "getBody$okhttp", "()Lokhttp3/RequestBody;", "setBody$okhttp", "(Lokhttp3/RequestBody;)V", "headers", "Lokhttp3/Headers$Builder;", "getHeaders$okhttp", "()Lokhttp3/Headers$Builder;", "setHeaders$okhttp", "(Lokhttp3/Headers$Builder;)V", SchemaActivity.KEY_METHOD, "", "getMethod$okhttp", "()Ljava/lang/String;", "setMethod$okhttp", "(Ljava/lang/String;)V", "tags", "", "Ljava/lang/Class;", "getTags$okhttp", "()Ljava/util/Map;", "setTags$okhttp", "(Ljava/util/Map;)V", "url", "Lokhttp3/HttpUrl;", "getUrl$okhttp", "()Lokhttp3/HttpUrl;", "setUrl$okhttp", "(Lokhttp3/HttpUrl;)V", "addHeader", "name", b.p, "build", "cacheControl", "Lokhttp3/CacheControl;", "delete", BluetoothConstants.GET, "head", "header", "Lokhttp3/Headers;", "patch", "post", "put", "removeHeader", MiLinkKeys.PARAM_TAG, ExifInterface.GPS_DIRECTION_TRUE, "type", "(Ljava/lang/Class;Ljava/lang/Object;)Lokhttp3/Request$Builder;", "Ljava/net/URL;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static class Builder {
        @Nullable
        private RequestBody body;
        @NotNull
        private Headers.Builder headers;
        @NotNull
        private String method;
        @NotNull
        private Map<Class<?>, Object> tags;
        @Nullable
        private HttpUrl url;

        @JvmOverloads
        @NotNull
        public Builder delete() {
            return delete$default(this, null, 1, null);
        }

        @Nullable
        public final HttpUrl getUrl$okhttp() {
            return this.url;
        }

        public final void setUrl$okhttp(@Nullable HttpUrl httpUrl) {
            this.url = httpUrl;
        }

        @NotNull
        public final String getMethod$okhttp() {
            return this.method;
        }

        public final void setMethod$okhttp(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.method = str;
        }

        @NotNull
        public final Headers.Builder getHeaders$okhttp() {
            return this.headers;
        }

        public final void setHeaders$okhttp(@NotNull Headers.Builder builder) {
            Intrinsics.checkParameterIsNotNull(builder, "<set-?>");
            this.headers = builder;
        }

        @Nullable
        public final RequestBody getBody$okhttp() {
            return this.body;
        }

        public final void setBody$okhttp(@Nullable RequestBody requestBody) {
            this.body = requestBody;
        }

        @NotNull
        public final Map<Class<?>, Object> getTags$okhttp() {
            return this.tags;
        }

        public final void setTags$okhttp(@NotNull Map<Class<?>, Object> map) {
            Intrinsics.checkParameterIsNotNull(map, "<set-?>");
            this.tags = map;
        }

        public Builder() {
            this.tags = new LinkedHashMap();
            this.method = "GET";
            this.headers = new Headers.Builder();
        }

        public Builder(@NotNull Request request) {
            LinkedHashMap linkedHashMap;
            Intrinsics.checkParameterIsNotNull(request, "request");
            this.tags = new LinkedHashMap();
            this.url = request.url();
            this.method = request.method();
            this.body = request.body();
            if (request.getTags$okhttp().isEmpty()) {
                linkedHashMap = new LinkedHashMap();
            } else {
                linkedHashMap = MapsKt.toMutableMap(request.getTags$okhttp());
            }
            this.tags = linkedHashMap;
            this.headers = request.headers().newBuilder();
        }

        @NotNull
        public Builder url(@NotNull HttpUrl url) {
            Intrinsics.checkParameterIsNotNull(url, "url");
            Builder builder = this;
            builder.url = url;
            return builder;
        }

        @NotNull
        public Builder url(@NotNull String url) {
            Intrinsics.checkParameterIsNotNull(url, "url");
            if (StringsKt.startsWith(url, "ws:", true)) {
                StringBuilder sb = new StringBuilder();
                sb.append(URIUtil.HTTP_COLON);
                String substring = url.substring(3);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
                sb.append(substring);
                url = sb.toString();
            } else if (StringsKt.startsWith(url, "wss:", true)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URIUtil.HTTPS_COLON);
                String substring2 = url.substring(4);
                Intrinsics.checkExpressionValueIsNotNull(substring2, "(this as java.lang.String).substring(startIndex)");
                sb2.append(substring2);
                url = sb2.toString();
            }
            return url(HttpUrl.Companion.get(url));
        }

        @NotNull
        public Builder url(@NotNull URL url) {
            Intrinsics.checkParameterIsNotNull(url, "url");
            HttpUrl.Companion companion = HttpUrl.Companion;
            String url2 = url.toString();
            Intrinsics.checkExpressionValueIsNotNull(url2, "url.toString()");
            return url(companion.get(url2));
        }

        @NotNull
        public Builder header(@NotNull String name, @NotNull String value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder builder = this;
            builder.headers.set(name, value);
            return builder;
        }

        @NotNull
        public Builder addHeader(@NotNull String name, @NotNull String value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(value, "value");
            Builder builder = this;
            builder.headers.add(name, value);
            return builder;
        }

        @NotNull
        public Builder removeHeader(@NotNull String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Builder builder = this;
            builder.headers.removeAll(name);
            return builder;
        }

        @NotNull
        public Builder headers(@NotNull Headers headers) {
            Intrinsics.checkParameterIsNotNull(headers, "headers");
            Builder builder = this;
            builder.headers = headers.newBuilder();
            return builder;
        }

        @NotNull
        public Builder cacheControl(@NotNull CacheControl cacheControl) {
            Intrinsics.checkParameterIsNotNull(cacheControl, "cacheControl");
            String cacheControl2 = cacheControl.toString();
            return cacheControl2.length() == 0 ? removeHeader("Cache-Control") : header("Cache-Control", cacheControl2);
        }

        @NotNull
        public Builder get() {
            return method("GET", null);
        }

        @NotNull
        public Builder head() {
            return method("HEAD", null);
        }

        @NotNull
        public Builder post(@NotNull RequestBody body) {
            Intrinsics.checkParameterIsNotNull(body, "body");
            return method("POST", body);
        }

        public static /* synthetic */ Builder delete$default(Builder builder, RequestBody requestBody, int i, Object obj) {
            if (obj == null) {
                if ((i & 1) != 0) {
                    requestBody = Util.EMPTY_REQUEST;
                }
                return builder.delete(requestBody);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: delete");
        }

        @JvmOverloads
        @NotNull
        public Builder delete(@Nullable RequestBody requestBody) {
            return method("DELETE", requestBody);
        }

        @NotNull
        public Builder put(@NotNull RequestBody body) {
            Intrinsics.checkParameterIsNotNull(body, "body");
            return method("PUT", body);
        }

        @NotNull
        public Builder patch(@NotNull RequestBody body) {
            Intrinsics.checkParameterIsNotNull(body, "body");
            return method(OkHttpUtils.METHOD.PATCH, body);
        }

        @NotNull
        public Builder method(@NotNull String method, @Nullable RequestBody requestBody) {
            Intrinsics.checkParameterIsNotNull(method, "method");
            Builder builder = this;
            if (method.length() > 0) {
                if (requestBody == null) {
                    if (!(!HttpMethod.requiresRequestBody(method))) {
                        throw new IllegalArgumentException(("method " + method + " must have a request body.").toString());
                    }
                } else if (!HttpMethod.permitsRequestBody(method)) {
                    throw new IllegalArgumentException(("method " + method + " must not have a request body.").toString());
                }
                builder.method = method;
                builder.body = requestBody;
                return builder;
            }
            throw new IllegalArgumentException("method.isEmpty() == true".toString());
        }

        @NotNull
        public Builder tag(@Nullable Object obj) {
            return tag(Object.class, obj);
        }

        @NotNull
        public <T> Builder tag(@NotNull Class<? super T> type, @Nullable T t) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            Builder builder = this;
            if (t == null) {
                builder.tags.remove(type);
            } else {
                if (builder.tags.isEmpty()) {
                    builder.tags = new LinkedHashMap();
                }
                Map<Class<?>, Object> map = builder.tags;
                Object cast = type.cast(t);
                if (cast == null) {
                    Intrinsics.throwNpe();
                }
                map.put(type, cast);
            }
            return builder;
        }

        @NotNull
        public Request build() {
            HttpUrl httpUrl = this.url;
            if (httpUrl != null) {
                return new Request(httpUrl, this.method, this.headers.build(), this.body, Util.toImmutableMap(this.tags));
            }
            throw new IllegalStateException("url == null".toString());
        }
    }
}
