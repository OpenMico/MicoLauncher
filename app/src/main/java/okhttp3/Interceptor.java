package okhttp3;

import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Interceptor.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u00072\u00020\u0001:\u0002\u0006\u0007J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lokhttp3/Interceptor;", "", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "Chain", "Companion", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public interface Interceptor {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: Interceptor.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\u0005H&J\b\u0010\n\u001a\u00020\u000bH&J\u0018\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0018\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0018\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0013\u001a\u00020\u0005H&¨\u0006\u0014"}, d2 = {"Lokhttp3/Interceptor$Chain;", "", "call", "Lokhttp3/Call;", "connectTimeoutMillis", "", "connection", "Lokhttp3/Connection;", "proceed", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "readTimeoutMillis", "withConnectTimeout", RtspHeaders.Values.TIMEOUT, "unit", "Ljava/util/concurrent/TimeUnit;", "withReadTimeout", "withWriteTimeout", "writeTimeoutMillis", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public interface Chain {
        @NotNull
        Call call();

        int connectTimeoutMillis();

        @Nullable
        Connection connection();

        @NotNull
        Response proceed(@NotNull Request request) throws IOException;

        int readTimeoutMillis();

        @NotNull
        Request request();

        @NotNull
        Chain withConnectTimeout(int i, @NotNull TimeUnit timeUnit);

        @NotNull
        Chain withReadTimeout(int i, @NotNull TimeUnit timeUnit);

        @NotNull
        Chain withWriteTimeout(int i, @NotNull TimeUnit timeUnit);

        int writeTimeoutMillis();
    }

    @NotNull
    Response intercept(@NotNull Chain chain) throws IOException;

    /* compiled from: Interceptor.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042#\b\u0004\u0010\u0005\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006H\u0086\n¨\u0006\f"}, d2 = {"Lokhttp3/Interceptor$Companion;", "", "()V", "invoke", "Lokhttp3/Interceptor;", "block", "Lkotlin/Function1;", "Lokhttp3/Interceptor$Chain;", "Lkotlin/ParameterName;", "name", "chain", "Lokhttp3/Response;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        @NotNull
        public final Interceptor invoke(@NotNull final Function1<? super Chain, Response> block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            return new Interceptor() { // from class: okhttp3.Interceptor$Companion$invoke$1
                @Override // okhttp3.Interceptor
                @NotNull
                public Response intercept(@NotNull Interceptor.Chain chain) {
                    Intrinsics.checkParameterIsNotNull(chain, "chain");
                    return (Response) Function1.this.invoke(chain);
                }
            };
        }
    }
}
