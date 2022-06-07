package javax.servlet.http;

/* loaded from: classes5.dex */
public class HttpSessionBindingEvent extends HttpSessionEvent {
    private String name;
    private Object value;

    public HttpSessionBindingEvent(HttpSession httpSession, String str) {
        super(httpSession);
        this.name = str;
    }

    public HttpSessionBindingEvent(HttpSession httpSession, String str, Object obj) {
        super(httpSession);
        this.name = str;
        this.value = obj;
    }

    @Override // javax.servlet.http.HttpSessionEvent
    public HttpSession getSession() {
        return super.getSession();
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
}
