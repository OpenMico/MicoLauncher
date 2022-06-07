package io.netty.handler.codec.http;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DefaultHttpRequest extends DefaultHttpMessage implements HttpRequest {
    private HttpMethod a;
    private String b;

    public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String str) {
        this(httpVersion, httpMethod, str, true);
    }

    public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String str, boolean z) {
        super(httpVersion, z, false);
        this.a = (HttpMethod) ObjectUtil.checkNotNull(httpMethod, SchemaActivity.KEY_METHOD);
        this.b = (String) ObjectUtil.checkNotNull(str, MiSoundBoxCommandExtras.URI);
    }

    public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String str, HttpHeaders httpHeaders) {
        super(httpVersion, httpHeaders);
        this.a = (HttpMethod) ObjectUtil.checkNotNull(httpMethod, SchemaActivity.KEY_METHOD);
        this.b = (String) ObjectUtil.checkNotNull(str, MiSoundBoxCommandExtras.URI);
    }

    @Override // io.netty.handler.codec.http.HttpRequest
    @Deprecated
    public HttpMethod getMethod() {
        return method();
    }

    @Override // io.netty.handler.codec.http.HttpRequest
    public HttpMethod method() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.HttpRequest
    @Deprecated
    public String getUri() {
        return uri();
    }

    @Override // io.netty.handler.codec.http.HttpRequest
    public String uri() {
        return this.b;
    }

    public HttpRequest setMethod(HttpMethod httpMethod) {
        if (httpMethod != null) {
            this.a = httpMethod;
            return this;
        }
        throw new NullPointerException(SchemaActivity.KEY_METHOD);
    }

    public HttpRequest setUri(String str) {
        if (str != null) {
            this.b = str;
            return this;
        }
        throw new NullPointerException(MiSoundBoxCommandExtras.URI);
    }

    @Override // io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.HttpMessage, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
    public HttpRequest setProtocolVersion(HttpVersion httpVersion) {
        super.setProtocolVersion(httpVersion);
        return this;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.DefaultHttpObject
    public int hashCode() {
        return ((((this.a.hashCode() + 31) * 31) + this.b.hashCode()) * 31) + super.hashCode();
    }

    @Override // io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.DefaultHttpObject
    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultHttpRequest)) {
            return false;
        }
        DefaultHttpRequest defaultHttpRequest = (DefaultHttpRequest) obj;
        return method().equals(defaultHttpRequest.method()) && uri().equalsIgnoreCase(defaultHttpRequest.uri()) && super.equals(obj);
    }

    public String toString() {
        return d.a(new StringBuilder(256), (HttpRequest) this).toString();
    }
}
