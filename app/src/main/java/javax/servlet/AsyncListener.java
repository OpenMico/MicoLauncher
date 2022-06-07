package javax.servlet;

import java.io.IOException;
import java.util.EventListener;

/* loaded from: classes5.dex */
public interface AsyncListener extends EventListener {
    void onComplete(AsyncEvent asyncEvent) throws IOException;

    void onError(AsyncEvent asyncEvent) throws IOException;

    void onStartAsync(AsyncEvent asyncEvent) throws IOException;

    void onTimeout(AsyncEvent asyncEvent) throws IOException;
}
