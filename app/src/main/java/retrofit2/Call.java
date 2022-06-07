package retrofit2;

import java.io.IOException;
import okhttp3.Request;
import okio.Timeout;

/* loaded from: classes6.dex */
public interface Call<T> extends Cloneable {
    void cancel();

    Call<T> clone();

    void enqueue(Callback<T> callback);

    Response<T> execute() throws IOException;

    boolean isCanceled();

    boolean isExecuted();

    Request request();

    Timeout timeout();
}
