package retrofit2;

import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.e;

/* loaded from: classes6.dex */
public final class Response<T> {
    private final okhttp3.Response a;
    @Nullable
    private final T b;
    @Nullable
    private final ResponseBody c;

    public static <T> Response<T> success(@Nullable T t) {
        return success(t, new Response.Builder().code(200).message("OK").protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
    }

    public static <T> Response<T> success(int i, @Nullable T t) {
        if (i >= 200 && i < 300) {
            return success(t, new Response.Builder().code(i).message("Response.success()").protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
        }
        throw new IllegalArgumentException("code < 200 or >= 300: " + i);
    }

    public static <T> Response<T> success(@Nullable T t, Headers headers) {
        Objects.requireNonNull(headers, "headers == null");
        return success(t, new Response.Builder().code(200).message("OK").protocol(Protocol.HTTP_1_1).headers(headers).request(new Request.Builder().url("http://localhost/").build()).build());
    }

    public static <T> Response<T> success(@Nullable T t, okhttp3.Response response) {
        Objects.requireNonNull(response, "rawResponse == null");
        if (response.isSuccessful()) {
            return new Response<>(response, t, null);
        }
        throw new IllegalArgumentException("rawResponse must be successful response");
    }

    public static <T> Response<T> error(int i, ResponseBody responseBody) {
        Objects.requireNonNull(responseBody, "body == null");
        if (i >= 400) {
            return error(responseBody, new Response.Builder().body(new e.b(responseBody.contentType(), responseBody.contentLength())).code(i).message("Response.error()").protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
        }
        throw new IllegalArgumentException("code < 400: " + i);
    }

    public static <T> Response<T> error(ResponseBody responseBody, okhttp3.Response response) {
        Objects.requireNonNull(responseBody, "body == null");
        Objects.requireNonNull(response, "rawResponse == null");
        if (!response.isSuccessful()) {
            return new Response<>(response, null, responseBody);
        }
        throw new IllegalArgumentException("rawResponse should not be successful response");
    }

    private Response(okhttp3.Response response, @Nullable T t, @Nullable ResponseBody responseBody) {
        this.a = response;
        this.b = t;
        this.c = responseBody;
    }

    public okhttp3.Response raw() {
        return this.a;
    }

    public int code() {
        return this.a.code();
    }

    public String message() {
        return this.a.message();
    }

    public Headers headers() {
        return this.a.headers();
    }

    public boolean isSuccessful() {
        return this.a.isSuccessful();
    }

    @Nullable
    public T body() {
        return this.b;
    }

    @Nullable
    public ResponseBody errorBody() {
        return this.c;
    }

    public String toString() {
        return this.a.toString();
    }
}
