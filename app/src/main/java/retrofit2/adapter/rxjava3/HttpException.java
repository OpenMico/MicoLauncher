package retrofit2.adapter.rxjava3;

import retrofit2.Response;

@Deprecated
/* loaded from: classes6.dex */
public final class HttpException extends retrofit2.HttpException {
    public HttpException(Response<?> response) {
        super(response);
    }
}
