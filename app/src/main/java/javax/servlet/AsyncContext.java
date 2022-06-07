package javax.servlet;

/* loaded from: classes5.dex */
public interface AsyncContext {
    public static final String ASYNC_CONTEXT_PATH = "javax.servlet.async.context_path";
    public static final String ASYNC_PATH_INFO = "javax.servlet.async.path_info";
    public static final String ASYNC_QUERY_STRING = "javax.servlet.async.query_string";
    public static final String ASYNC_REQUEST_URI = "javax.servlet.async.request_uri";
    public static final String ASYNC_SERVLET_PATH = "javax.servlet.async.servlet_path";

    void addListener(AsyncListener asyncListener);

    void addListener(AsyncListener asyncListener, ServletRequest servletRequest, ServletResponse servletResponse);

    void complete();

    <T extends AsyncListener> T createListener(Class<T> cls) throws ServletException;

    void dispatch();

    void dispatch(String str);

    void dispatch(ServletContext servletContext, String str);

    ServletRequest getRequest();

    ServletResponse getResponse();

    long getTimeout();

    boolean hasOriginalRequestAndResponse();

    void setTimeout(long j);

    void start(Runnable runnable);
}
