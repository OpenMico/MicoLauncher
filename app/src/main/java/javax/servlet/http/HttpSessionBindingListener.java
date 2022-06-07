package javax.servlet.http;

import java.util.EventListener;

/* loaded from: classes5.dex */
public interface HttpSessionBindingListener extends EventListener {
    void valueBound(HttpSessionBindingEvent httpSessionBindingEvent);

    void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent);
}
