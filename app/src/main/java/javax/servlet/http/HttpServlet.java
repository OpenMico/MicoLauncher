package javax.servlet.http;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.MimeTypes;

/* loaded from: classes5.dex */
public abstract class HttpServlet extends GenericServlet implements Serializable {
    private static final String HEADER_IFMODSINCE = "If-Modified-Since";
    private static final String HEADER_LASTMOD = "Last-Modified";
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";
    private static final String LSTRING_FILE = "javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    protected long getLastModified(HttpServletRequest httpServletRequest) {
        return -1L;
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String protocol = httpServletRequest.getProtocol();
        String string = lStrings.getString("http.method_get_not_supported");
        if (protocol.endsWith("1.1")) {
            httpServletResponse.sendError(405, string);
        } else {
            httpServletResponse.sendError(400, string);
        }
    }

    protected void doHead(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        b bVar = new b(httpServletResponse);
        doGet(httpServletRequest, bVar);
        bVar.a();
    }

    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String protocol = httpServletRequest.getProtocol();
        String string = lStrings.getString("http.method_post_not_supported");
        if (protocol.endsWith("1.1")) {
            httpServletResponse.sendError(405, string);
        } else {
            httpServletResponse.sendError(400, string);
        }
    }

    protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String protocol = httpServletRequest.getProtocol();
        String string = lStrings.getString("http.method_put_not_supported");
        if (protocol.endsWith("1.1")) {
            httpServletResponse.sendError(405, string);
        } else {
            httpServletResponse.sendError(400, string);
        }
    }

    protected void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String protocol = httpServletRequest.getProtocol();
        String string = lStrings.getString("http.method_delete_not_supported");
        if (protocol.endsWith("1.1")) {
            httpServletResponse.sendError(405, string);
        } else {
            httpServletResponse.sendError(400, string);
        }
    }

    private Method[] getAllDeclaredMethods(Class<?> cls) {
        if (cls.equals(HttpServlet.class)) {
            return null;
        }
        Method[] allDeclaredMethods = getAllDeclaredMethods(cls.getSuperclass());
        Method[] declaredMethods = cls.getDeclaredMethods();
        if (allDeclaredMethods == null || allDeclaredMethods.length <= 0) {
            return declaredMethods;
        }
        Method[] methodArr = new Method[allDeclaredMethods.length + declaredMethods.length];
        System.arraycopy(allDeclaredMethods, 0, methodArr, 0, allDeclaredMethods.length);
        System.arraycopy(declaredMethods, 0, methodArr, allDeclaredMethods.length, declaredMethods.length);
        return methodArr;
    }

    protected void doOptions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Method[] allDeclaredMethods = getAllDeclaredMethods(getClass());
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        for (Method method : allDeclaredMethods) {
            if (method.getName().equals("doGet")) {
                z = true;
                z2 = true;
            }
            if (method.getName().equals("doPost")) {
                z3 = true;
            }
            if (method.getName().equals("doPut")) {
                z4 = true;
            }
            if (method.getName().equals("doDelete")) {
                z5 = true;
            }
        }
        String str = null;
        if (z) {
            str = "GET";
        }
        if (z2) {
            str = str == null ? "HEAD" : str + ", HEAD";
        }
        if (z3) {
            str = str == null ? "POST" : str + ", POST";
        }
        if (z4) {
            str = str == null ? "PUT" : str + ", PUT";
        }
        if (z5) {
            str = str == null ? "DELETE" : str + ", DELETE";
        }
        String str2 = str == null ? "TRACE" : str + ", TRACE";
        httpServletResponse.setHeader("Allow", str2 == null ? "OPTIONS" : str2 + ", OPTIONS");
    }

    protected void doTrace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder("TRACE ");
        sb.append(httpServletRequest.getRequestURI());
        sb.append(StringUtils.SPACE);
        sb.append(httpServletRequest.getProtocol());
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String nextElement = headerNames.nextElement();
            sb.append("\r\n");
            sb.append(nextElement);
            sb.append(": ");
            sb.append(httpServletRequest.getHeader(nextElement));
        }
        sb.append("\r\n");
        int length = sb.length();
        httpServletResponse.setContentType(MimeTypes.MESSAGE_HTTP);
        httpServletResponse.setContentLength(length);
        httpServletResponse.getOutputStream().print(sb.toString());
    }

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String method = httpServletRequest.getMethod();
        if (method.equals("GET")) {
            long lastModified = getLastModified(httpServletRequest);
            if (lastModified == -1) {
                doGet(httpServletRequest, httpServletResponse);
            } else if (httpServletRequest.getDateHeader("If-Modified-Since") < lastModified) {
                maybeSetLastModified(httpServletResponse, lastModified);
                doGet(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.setStatus(304);
            }
        } else if (method.equals("HEAD")) {
            maybeSetLastModified(httpServletResponse, getLastModified(httpServletRequest));
            doHead(httpServletRequest, httpServletResponse);
        } else if (method.equals("POST")) {
            doPost(httpServletRequest, httpServletResponse);
        } else if (method.equals("PUT")) {
            doPut(httpServletRequest, httpServletResponse);
        } else if (method.equals("DELETE")) {
            doDelete(httpServletRequest, httpServletResponse);
        } else if (method.equals("OPTIONS")) {
            doOptions(httpServletRequest, httpServletResponse);
        } else if (method.equals("TRACE")) {
            doTrace(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendError(501, MessageFormat.format(lStrings.getString("http.method_not_implemented"), method));
        }
    }

    private void maybeSetLastModified(HttpServletResponse httpServletResponse, long j) {
        if (!httpServletResponse.containsHeader("Last-Modified") && j >= 0) {
            httpServletResponse.setDateHeader("Last-Modified", j);
        }
    }

    @Override // javax.servlet.GenericServlet, javax.servlet.Servlet
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        try {
            service((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
        } catch (ClassCastException unused) {
            throw new ServletException("non-HTTP request or response");
        }
    }
}
