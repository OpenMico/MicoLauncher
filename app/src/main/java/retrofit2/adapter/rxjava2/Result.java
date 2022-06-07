package retrofit2.adapter.rxjava2;

import javax.annotation.Nullable;
import retrofit2.Response;

/* loaded from: classes6.dex */
public final class Result<T> {
    @Nullable
    private final Response<T> a;
    @Nullable
    private final Throwable b;

    public static <T> Result<T> error(Throwable th) {
        if (th != null) {
            return new Result<>(null, th);
        }
        throw new NullPointerException("error == null");
    }

    public static <T> Result<T> response(Response<T> response) {
        if (response != null) {
            return new Result<>(response, null);
        }
        throw new NullPointerException("response == null");
    }

    private Result(@Nullable Response<T> response, @Nullable Throwable th) {
        this.a = response;
        this.b = th;
    }

    @Nullable
    public Response<T> response() {
        return this.a;
    }

    @Nullable
    public Throwable error() {
        return this.b;
    }

    public boolean isError() {
        return this.b != null;
    }
}
