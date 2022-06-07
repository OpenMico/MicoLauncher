package javax.servlet.http;

import java.util.EventListener;

/* loaded from: classes5.dex */
public interface HttpSessionActivationListener extends EventListener {
    void sessionDidActivate(HttpSessionEvent httpSessionEvent);

    void sessionWillPassivate(HttpSessionEvent httpSessionEvent);
}
