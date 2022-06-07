package retrofit2;

import java.util.Objects;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes6.dex */
public class HttpException extends RuntimeException {
    private final transient Response<?> a;
    private final int code;
    private final String message;

    private static String a(Response<?> response) {
        Objects.requireNonNull(response, "response == null");
        return "HTTP " + response.code() + StringUtils.SPACE + response.message();
    }

    public HttpException(Response<?> response) {
        super(a(response));
        this.code = response.code();
        this.message = response.message();
        this.a = response;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    @Nullable
    public Response<?> response() {
        return this.a;
    }
}
