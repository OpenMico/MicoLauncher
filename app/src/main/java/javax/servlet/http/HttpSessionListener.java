package javax.servlet.http;

import java.util.EventListener;

/* loaded from: classes5.dex */
public interface HttpSessionListener extends EventListener {
    void sessionCreated(HttpSessionEvent httpSessionEvent);

    void sessionDestroyed(HttpSessionEvent httpSessionEvent);
}
