package javax.servlet.http;

import java.util.EventObject;

/* loaded from: classes5.dex */
public class HttpSessionEvent extends EventObject {
    public HttpSessionEvent(HttpSession httpSession) {
        super(httpSession);
    }

    public HttpSession getSession() {
        return (HttpSession) super.getSource();
    }
}
