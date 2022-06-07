package org.eclipse.jetty.server;

import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import io.netty.handler.codec.http.HttpHeaders;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.nio.DirectNIOBuffer;
import org.eclipse.jetty.io.nio.IndirectNIOBuffer;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.MultiPartInputStream;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class Request implements HttpServletRequest {
    private static final int _STREAM = 1;
    private static final String __ASYNC_FWD = "org.eclipse.asyncfwd";
    public static final String __MULTIPART_CONFIG_ELEMENT = "org.eclipse.multipartConfig";
    public static final String __MULTIPART_CONTEXT = "org.eclipse.multiPartContext";
    public static final String __MULTIPART_INPUT_STREAM = "org.eclipse.multiPartInputStream";
    private static final int __NONE = 0;
    private static final int __READER = 2;
    private volatile Attributes _attributes;
    private Authentication _authentication;
    private MultiMap<String> _baseParameters;
    private String _characterEncoding;
    protected AbstractHttpConnection _connection;
    private ContextHandler.Context _context;
    private String _contextPath;
    private CookieCutter _cookies;
    private long _dispatchTime;
    private DispatcherType _dispatcherType;
    private EndPoint _endp;
    private String _method;
    private MultiPartInputStream _multiPartInputStream;
    private boolean _newContext;
    private MultiMap<String> _parameters;
    private boolean _paramsExtracted;
    private String _pathInfo;
    private int _port;
    private String _queryEncoding;
    private String _queryString;
    private BufferedReader _reader;
    private String _readerEncoding;
    private String _remoteAddr;
    private String _remoteHost;
    private Object _requestAttributeListeners;
    private String _requestURI;
    private String _requestedSessionId;
    private Map<Object, HttpSession> _savedNewSessions;
    private UserIdentity.Scope _scope;
    private String _serverName;
    private String _servletPath;
    private HttpSession _session;
    private SessionManager _sessionManager;
    private long _timeStamp;
    private Buffer _timeStampBuffer;
    private HttpURI _uri;
    private static final Logger LOG = Log.getLogger(Request.class);
    private static final Collection __defaultLocale = Collections.singleton(Locale.getDefault());
    protected final AsyncContinuation _async = new AsyncContinuation();
    private boolean _asyncSupported = true;
    private boolean _cookiesExtracted = false;
    private boolean _dns = false;
    private boolean _handled = false;
    private int _inputState = 0;
    private String _protocol = HttpVersions.HTTP_1_1;
    private boolean _requestedSessionIdFromCookie = false;
    private String _scheme = "http";

    /* loaded from: classes5.dex */
    public static class MultiPartCleanerListener implements ServletRequestListener {
        @Override // javax.servlet.ServletRequestListener
        public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        }

        @Override // javax.servlet.ServletRequestListener
        public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
            MultiPartInputStream multiPartInputStream = (MultiPartInputStream) servletRequestEvent.getServletRequest().getAttribute(Request.__MULTIPART_INPUT_STREAM);
            if (multiPartInputStream != null && ((ContextHandler.Context) servletRequestEvent.getServletRequest().getAttribute(Request.__MULTIPART_CONTEXT)) == servletRequestEvent.getServletContext()) {
                try {
                    multiPartInputStream.deleteParts();
                } catch (MultiException e) {
                    servletRequestEvent.getServletContext().log("Errors deleting multipart tmp files", e);
                }
            }
        }
    }

    public static Request getRequest(HttpServletRequest httpServletRequest) {
        if (httpServletRequest instanceof Request) {
            return (Request) httpServletRequest;
        }
        return AbstractHttpConnection.getCurrentConnection().getRequest();
    }

    public Request() {
    }

    public Request(AbstractHttpConnection abstractHttpConnection) {
        setConnection(abstractHttpConnection);
    }

    public void addEventListener(EventListener eventListener) {
        if (eventListener instanceof ServletRequestAttributeListener) {
            this._requestAttributeListeners = LazyList.add(this._requestAttributeListeners, eventListener);
        }
        if (eventListener instanceof ContinuationListener) {
            throw new IllegalArgumentException(eventListener.getClass().toString());
        } else if (eventListener instanceof AsyncListener) {
            throw new IllegalArgumentException(eventListener.getClass().toString());
        }
    }

    public void extractParameters() {
        int contentLength;
        int i;
        int i2;
        if (this._baseParameters == null) {
            this._baseParameters = new MultiMap<>(16);
        }
        if (!this._paramsExtracted) {
            this._paramsExtracted = true;
            try {
                if (this._uri != null && this._uri.hasQuery()) {
                    if (this._queryEncoding == null) {
                        this._uri.decodeQueryTo(this._baseParameters);
                    } else {
                        try {
                            this._uri.decodeQueryTo(this._baseParameters, this._queryEncoding);
                        } catch (UnsupportedEncodingException e) {
                            if (LOG.isDebugEnabled()) {
                                LOG.warn(e);
                            } else {
                                LOG.warn(e.toString(), new Object[0]);
                            }
                        }
                    }
                }
                String characterEncoding = getCharacterEncoding();
                String contentType = getContentType();
                if (contentType != null && contentType.length() > 0 && "application/x-www-form-urlencoded".equalsIgnoreCase(HttpFields.valueParameters(contentType, null)) && this._inputState == 0 && (("POST".equals(getMethod()) || "PUT".equals(getMethod())) && (contentLength = getContentLength()) != 0)) {
                    try {
                        if (this._context != null) {
                            i2 = this._context.getContextHandler().getMaxFormContentSize();
                            i = this._context.getContextHandler().getMaxFormKeys();
                        } else {
                            Number number = (Number) this._connection.getConnector().getServer().getAttribute("org.eclipse.jetty.server.Request.maxFormContentSize");
                            i2 = number == null ? 200000 : number.intValue();
                            Number number2 = (Number) this._connection.getConnector().getServer().getAttribute("org.eclipse.jetty.server.Request.maxFormKeys");
                            i = number2 == null ? 1000 : number2.intValue();
                        }
                        if (contentLength > i2 && i2 > 0) {
                            throw new IllegalStateException("Form too large" + contentLength + ">" + i2);
                        }
                        ServletInputStream inputStream = getInputStream();
                        MultiMap<String> multiMap = this._baseParameters;
                        if (contentLength >= 0) {
                            i2 = -1;
                        }
                        UrlEncoded.decodeTo(inputStream, multiMap, characterEncoding, i2, i);
                    } catch (IOException e2) {
                        if (LOG.isDebugEnabled()) {
                            LOG.warn(e2);
                        } else {
                            LOG.warn(e2.toString(), new Object[0]);
                        }
                    }
                }
                if (this._parameters == null) {
                    this._parameters = this._baseParameters;
                } else if (this._parameters != this._baseParameters) {
                    for (Map.Entry<String, Object> entry : this._baseParameters.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        for (int i3 = 0; i3 < LazyList.size(value); i3++) {
                            this._parameters.add(key, LazyList.get(value, i3));
                        }
                    }
                }
                if (this._parameters == null) {
                    this._parameters = this._baseParameters;
                }
            } finally {
                if (this._parameters == null) {
                    this._parameters = this._baseParameters;
                }
            }
        }
    }

    @Override // javax.servlet.ServletRequest
    public AsyncContext getAsyncContext() {
        if (!this._async.isInitial() || this._async.isAsyncStarted()) {
            return this._async;
        }
        throw new IllegalStateException(this._async.getStatusString());
    }

    public AsyncContinuation getAsyncContinuation() {
        return this._async;
    }

    @Override // javax.servlet.ServletRequest
    public Object getAttribute(String str) {
        if ("org.eclipse.jetty.io.EndPoint.maxIdleTime".equalsIgnoreCase(str)) {
            return new Long(getConnection().getEndPoint().getMaxIdleTime());
        }
        Object attribute = this._attributes == null ? null : this._attributes.getAttribute(str);
        return (attribute != null || !Continuation.ATTRIBUTE.equals(str)) ? attribute : this._async;
    }

    @Override // javax.servlet.ServletRequest
    public Enumeration getAttributeNames() {
        if (this._attributes == null) {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }
        return AttributesMap.getAttributeNamesCopy(this._attributes);
    }

    public Attributes getAttributes() {
        if (this._attributes == null) {
            this._attributes = new AttributesMap();
        }
        return this._attributes;
    }

    public Authentication getAuthentication() {
        return this._authentication;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getAuthType() {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this));
        }
        Authentication authentication2 = this._authentication;
        if (authentication2 instanceof Authentication.User) {
            return ((Authentication.User) authentication2).getAuthMethod();
        }
        return null;
    }

    @Override // javax.servlet.ServletRequest
    public String getCharacterEncoding() {
        return this._characterEncoding;
    }

    public AbstractHttpConnection getConnection() {
        return this._connection;
    }

    @Override // javax.servlet.ServletRequest
    public int getContentLength() {
        return (int) this._connection.getRequestFields().getLongField(HttpHeaders.CONTENT_LENGTH_BUFFER);
    }

    public long getContentRead() {
        AbstractHttpConnection abstractHttpConnection = this._connection;
        if (abstractHttpConnection == null || abstractHttpConnection.getParser() == null) {
            return -1L;
        }
        return ((HttpParser) this._connection.getParser()).getContentRead();
    }

    @Override // javax.servlet.ServletRequest
    public String getContentType() {
        return this._connection.getRequestFields().getStringField(HttpHeaders.CONTENT_TYPE_BUFFER);
    }

    public ContextHandler.Context getContext() {
        return this._context;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getContextPath() {
        return this._contextPath;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Cookie[] getCookies() {
        if (this._cookiesExtracted) {
            CookieCutter cookieCutter = this._cookies;
            if (cookieCutter == null) {
                return null;
            }
            return cookieCutter.getCookies();
        }
        this._cookiesExtracted = true;
        Enumeration<String> values = this._connection.getRequestFields().getValues(HttpHeaders.COOKIE_BUFFER);
        if (values != null) {
            if (this._cookies == null) {
                this._cookies = new CookieCutter();
            }
            while (values.hasMoreElements()) {
                this._cookies.addCookieField(values.nextElement());
            }
        }
        CookieCutter cookieCutter2 = this._cookies;
        if (cookieCutter2 == null) {
            return null;
        }
        return cookieCutter2.getCookies();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public long getDateHeader(String str) {
        return this._connection.getRequestFields().getDateField(str);
    }

    @Override // javax.servlet.ServletRequest
    public DispatcherType getDispatcherType() {
        return this._dispatcherType;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getHeader(String str) {
        return this._connection.getRequestFields().getStringField(str);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Enumeration getHeaderNames() {
        return this._connection.getRequestFields().getFieldNames();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Enumeration getHeaders(String str) {
        Enumeration<String> values = this._connection.getRequestFields().getValues(str);
        return values == null ? Collections.enumeration(Collections.EMPTY_LIST) : values;
    }

    public int getInputState() {
        return this._inputState;
    }

    @Override // javax.servlet.ServletRequest
    public ServletInputStream getInputStream() throws IOException {
        int i = this._inputState;
        if (i == 0 || i == 1) {
            this._inputState = 1;
            return this._connection.getInputStream();
        }
        throw new IllegalStateException("READER");
    }

    @Override // javax.servlet.http.HttpServletRequest
    public int getIntHeader(String str) {
        return (int) this._connection.getRequestFields().getLongField(str);
    }

    @Override // javax.servlet.ServletRequest
    public String getLocalAddr() {
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return null;
        }
        return endPoint.getLocalAddr();
    }

    @Override // javax.servlet.ServletRequest
    public Locale getLocale() {
        Enumeration<String> values = this._connection.getRequestFields().getValues("Accept-Language", HttpFields.__separators);
        if (values == null || !values.hasMoreElements()) {
            return Locale.getDefault();
        }
        List qualityList = HttpFields.qualityList(values);
        if (qualityList.size() == 0) {
            return Locale.getDefault();
        }
        if (qualityList.size() <= 0) {
            return Locale.getDefault();
        }
        String valueParameters = HttpFields.valueParameters((String) qualityList.get(0), null);
        String str = "";
        int indexOf = valueParameters.indexOf(45);
        if (indexOf > -1) {
            str = valueParameters.substring(indexOf + 1).trim();
            valueParameters = valueParameters.substring(0, indexOf).trim();
        }
        return new Locale(valueParameters, str);
    }

    @Override // javax.servlet.ServletRequest
    public Enumeration getLocales() {
        Enumeration<String> values = this._connection.getRequestFields().getValues("Accept-Language", HttpFields.__separators);
        if (values == null || !values.hasMoreElements()) {
            return Collections.enumeration(__defaultLocale);
        }
        List qualityList = HttpFields.qualityList(values);
        if (qualityList.size() == 0) {
            return Collections.enumeration(__defaultLocale);
        }
        int size = qualityList.size();
        Object obj = null;
        for (int i = 0; i < size; i++) {
            String valueParameters = HttpFields.valueParameters((String) qualityList.get(i), null);
            String str = "";
            int indexOf = valueParameters.indexOf(45);
            if (indexOf > -1) {
                str = valueParameters.substring(indexOf + 1).trim();
                valueParameters = valueParameters.substring(0, indexOf).trim();
            }
            obj = LazyList.add(LazyList.ensureSize(obj, size), new Locale(valueParameters, str));
        }
        if (LazyList.size(obj) == 0) {
            return Collections.enumeration(__defaultLocale);
        }
        return Collections.enumeration(LazyList.getList(obj));
    }

    @Override // javax.servlet.ServletRequest
    public String getLocalName() {
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return null;
        }
        if (this._dns) {
            return endPoint.getLocalHost();
        }
        String localAddr = endPoint.getLocalAddr();
        if (localAddr == null || localAddr.indexOf(58) < 0) {
            return localAddr;
        }
        return "[" + localAddr + "]";
    }

    @Override // javax.servlet.ServletRequest
    public int getLocalPort() {
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return 0;
        }
        return endPoint.getLocalPort();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getMethod() {
        return this._method;
    }

    @Override // javax.servlet.ServletRequest
    public String getParameter(String str) {
        if (!this._paramsExtracted) {
            extractParameters();
        }
        return (String) this._parameters.getValue(str, 0);
    }

    @Override // javax.servlet.ServletRequest
    public Map getParameterMap() {
        if (!this._paramsExtracted) {
            extractParameters();
        }
        return Collections.unmodifiableMap(this._parameters.toStringArrayMap());
    }

    @Override // javax.servlet.ServletRequest
    public Enumeration getParameterNames() {
        if (!this._paramsExtracted) {
            extractParameters();
        }
        return Collections.enumeration(this._parameters.keySet());
    }

    public MultiMap<String> getParameters() {
        return this._parameters;
    }

    @Override // javax.servlet.ServletRequest
    public String[] getParameterValues(String str) {
        if (!this._paramsExtracted) {
            extractParameters();
        }
        List values = this._parameters.getValues(str);
        if (values == null) {
            return null;
        }
        return (String[]) values.toArray(new String[values.size()]);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getPathInfo() {
        return this._pathInfo;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getPathTranslated() {
        ContextHandler.Context context;
        String str = this._pathInfo;
        if (str == null || (context = this._context) == null) {
            return null;
        }
        return context.getRealPath(str);
    }

    @Override // javax.servlet.ServletRequest
    public String getProtocol() {
        return this._protocol;
    }

    public String getQueryEncoding() {
        return this._queryEncoding;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getQueryString() {
        HttpURI httpURI;
        if (this._queryString == null && (httpURI = this._uri) != null) {
            String str = this._queryEncoding;
            if (str == null) {
                this._queryString = httpURI.getQuery();
            } else {
                this._queryString = httpURI.getQuery(str);
            }
        }
        return this._queryString;
    }

    @Override // javax.servlet.ServletRequest
    public BufferedReader getReader() throws IOException {
        int i = this._inputState;
        if (i != 0 && i != 2) {
            throw new IllegalStateException("STREAMED");
        } else if (this._inputState == 2) {
            return this._reader;
        } else {
            String characterEncoding = getCharacterEncoding();
            if (characterEncoding == null) {
                characterEncoding = "ISO-8859-1";
            }
            if (this._reader == null || !characterEncoding.equalsIgnoreCase(this._readerEncoding)) {
                final ServletInputStream inputStream = getInputStream();
                this._readerEncoding = characterEncoding;
                this._reader = new BufferedReader(new InputStreamReader(inputStream, characterEncoding)) { // from class: org.eclipse.jetty.server.Request.1
                    @Override // java.io.BufferedReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws IOException {
                        inputStream.close();
                    }
                };
            }
            this._inputState = 2;
            return this._reader;
        }
    }

    @Override // javax.servlet.ServletRequest
    public String getRealPath(String str) {
        ContextHandler.Context context = this._context;
        if (context == null) {
            return null;
        }
        return context.getRealPath(str);
    }

    @Override // javax.servlet.ServletRequest
    public String getRemoteAddr() {
        String str = this._remoteAddr;
        if (str != null) {
            return str;
        }
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return null;
        }
        return endPoint.getRemoteAddr();
    }

    @Override // javax.servlet.ServletRequest
    public String getRemoteHost() {
        if (!this._dns) {
            return getRemoteAddr();
        }
        String str = this._remoteHost;
        if (str != null) {
            return str;
        }
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return null;
        }
        return endPoint.getRemoteHost();
    }

    @Override // javax.servlet.ServletRequest
    public int getRemotePort() {
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return 0;
        }
        return endPoint.getRemotePort();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getRemoteUser() {
        Principal userPrincipal = getUserPrincipal();
        if (userPrincipal == null) {
            return null;
        }
        return userPrincipal.getName();
    }

    @Override // javax.servlet.ServletRequest
    public RequestDispatcher getRequestDispatcher(String str) {
        if (str == null || this._context == null) {
            return null;
        }
        if (!str.startsWith("/")) {
            String addPaths = URIUtil.addPaths(this._servletPath, this._pathInfo);
            int lastIndexOf = addPaths.lastIndexOf("/");
            str = URIUtil.addPaths(lastIndexOf > 1 ? addPaths.substring(0, lastIndexOf + 1) : "/", str);
        }
        return this._context.getRequestDispatcher(str);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getRequestedSessionId() {
        return this._requestedSessionId;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getRequestURI() {
        HttpURI httpURI;
        if (this._requestURI == null && (httpURI = this._uri) != null) {
            this._requestURI = httpURI.getPathAndParam();
        }
        return this._requestURI;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public StringBuffer getRequestURL() {
        StringBuffer stringBuffer = new StringBuffer(48);
        synchronized (stringBuffer) {
            String scheme = getScheme();
            int serverPort = getServerPort();
            stringBuffer.append(scheme);
            stringBuffer.append("://");
            stringBuffer.append(getServerName());
            if (this._port > 0 && ((scheme.equalsIgnoreCase("http") && serverPort != 80) || (scheme.equalsIgnoreCase("https") && serverPort != 443))) {
                stringBuffer.append(':');
                stringBuffer.append(this._port);
            }
            stringBuffer.append(getRequestURI());
        }
        return stringBuffer;
    }

    public Response getResponse() {
        return this._connection._response;
    }

    public StringBuilder getRootURL() {
        StringBuilder sb = new StringBuilder(48);
        String scheme = getScheme();
        int serverPort = getServerPort();
        sb.append(scheme);
        sb.append("://");
        sb.append(getServerName());
        if (serverPort > 0 && ((scheme.equalsIgnoreCase("http") && serverPort != 80) || (scheme.equalsIgnoreCase("https") && serverPort != 443))) {
            sb.append(':');
            sb.append(serverPort);
        }
        return sb;
    }

    @Override // javax.servlet.ServletRequest
    public String getScheme() {
        return this._scheme;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x008b, code lost:
        if (r5._serverName == null) goto L_0x0091;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008f, code lost:
        if (r5._port >= 0) goto L_0x009a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0091, code lost:
        r5._serverName = org.eclipse.jetty.io.BufferUtil.to8859_1_String(r0);
        r5._port = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009c, code lost:
        return r5._serverName;
     */
    @Override // javax.servlet.ServletRequest
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getServerName() {
        /*
            r5 = this;
            java.lang.String r0 = r5._serverName
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            org.eclipse.jetty.http.HttpURI r0 = r5._uri
            if (r0 == 0) goto L_0x00d0
            java.lang.String r0 = r0.getHost()
            r5._serverName = r0
            org.eclipse.jetty.http.HttpURI r0 = r5._uri
            int r0 = r0.getPort()
            r5._port = r0
            java.lang.String r0 = r5._serverName
            if (r0 == 0) goto L_0x001c
            return r0
        L_0x001c:
            org.eclipse.jetty.server.AbstractHttpConnection r0 = r5._connection
            org.eclipse.jetty.http.HttpFields r0 = r0.getRequestFields()
            org.eclipse.jetty.io.Buffer r1 = org.eclipse.jetty.http.HttpHeaders.HOST_BUFFER
            org.eclipse.jetty.io.Buffer r0 = r0.get(r1)
            if (r0 == 0) goto L_0x009d
            int r1 = r0.putIndex()
        L_0x002e:
            int r2 = r1 + (-1)
            int r3 = r0.getIndex()
            if (r1 <= r3) goto L_0x0089
            byte r1 = r0.peek(r2)
            r1 = r1 & 255(0xff, float:3.57E-43)
            char r1 = (char) r1
            r3 = 58
            if (r1 == r3) goto L_0x0047
            r3 = 93
            if (r1 == r3) goto L_0x0089
            r1 = r2
            goto L_0x002e
        L_0x0047:
            int r1 = r0.getIndex()
            int r3 = r0.getIndex()
            int r3 = r2 - r3
            org.eclipse.jetty.io.Buffer r1 = r0.peek(r1, r3)
            java.lang.String r1 = org.eclipse.jetty.io.BufferUtil.to8859_1_String(r1)
            r5._serverName = r1
            int r1 = r2 + 1
            r3 = 1
            int r4 = r0.putIndex()     // Catch: NumberFormatException -> 0x006f
            int r4 = r4 - r2
            int r4 = r4 - r3
            org.eclipse.jetty.io.Buffer r0 = r0.peek(r1, r4)     // Catch: NumberFormatException -> 0x006f
            int r0 = org.eclipse.jetty.io.BufferUtil.toInt(r0)     // Catch: NumberFormatException -> 0x006f
            r5._port = r0     // Catch: NumberFormatException -> 0x006f
            goto L_0x007f
        L_0x006f:
            org.eclipse.jetty.server.AbstractHttpConnection r0 = r5._connection     // Catch: IOException -> 0x0082
            if (r0 == 0) goto L_0x007f
            org.eclipse.jetty.server.AbstractHttpConnection r0 = r5._connection     // Catch: IOException -> 0x0082
            org.eclipse.jetty.http.Generator r0 = r0._generator     // Catch: IOException -> 0x0082
            r1 = 400(0x190, float:5.6E-43)
            java.lang.String r2 = "Bad Host header"
            r4 = 0
            r0.sendError(r1, r2, r4, r3)     // Catch: IOException -> 0x0082
        L_0x007f:
            java.lang.String r0 = r5._serverName
            return r0
        L_0x0082:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            r1.<init>(r0)
            throw r1
        L_0x0089:
            java.lang.String r1 = r5._serverName
            if (r1 == 0) goto L_0x0091
            int r1 = r5._port
            if (r1 >= 0) goto L_0x009a
        L_0x0091:
            java.lang.String r0 = org.eclipse.jetty.io.BufferUtil.to8859_1_String(r0)
            r5._serverName = r0
            r0 = 0
            r5._port = r0
        L_0x009a:
            java.lang.String r0 = r5._serverName
            return r0
        L_0x009d:
            org.eclipse.jetty.server.AbstractHttpConnection r0 = r5._connection
            if (r0 == 0) goto L_0x00bc
            java.lang.String r0 = r5.getLocalName()
            r5._serverName = r0
            int r0 = r5.getLocalPort()
            r5._port = r0
            java.lang.String r0 = r5._serverName
            if (r0 == 0) goto L_0x00bc
            java.lang.String r1 = "0.0.0.0"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x00bc
            java.lang.String r0 = r5._serverName
            return r0
        L_0x00bc:
            java.net.InetAddress r0 = java.net.InetAddress.getLocalHost()     // Catch: UnknownHostException -> 0x00c7
            java.lang.String r0 = r0.getHostAddress()     // Catch: UnknownHostException -> 0x00c7
            r5._serverName = r0     // Catch: UnknownHostException -> 0x00c7
            goto L_0x00cd
        L_0x00c7:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.Request.LOG
            r1.ignore(r0)
        L_0x00cd:
            java.lang.String r0 = r5._serverName
            return r0
        L_0x00d0:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "No uri"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.Request.getServerName():java.lang.String");
    }

    @Override // javax.servlet.ServletRequest
    public int getServerPort() {
        HttpURI httpURI;
        if (this._port <= 0) {
            if (this._serverName == null) {
                getServerName();
            }
            if (this._port <= 0) {
                if (this._serverName == null || (httpURI = this._uri) == null) {
                    EndPoint endPoint = this._endp;
                    this._port = endPoint == null ? 0 : endPoint.getLocalPort();
                } else {
                    this._port = httpURI.getPort();
                }
            }
        }
        int i = this._port;
        return i <= 0 ? getScheme().equalsIgnoreCase("https") ? 443 : 80 : i;
    }

    @Override // javax.servlet.ServletRequest
    public ServletContext getServletContext() {
        return this._context;
    }

    public String getServletName() {
        UserIdentity.Scope scope = this._scope;
        if (scope != null) {
            return scope.getName();
        }
        return null;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public String getServletPath() {
        if (this._servletPath == null) {
            this._servletPath = "";
        }
        return this._servletPath;
    }

    public ServletResponse getServletResponse() {
        return this._connection.getResponse();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public HttpSession getSession() {
        return getSession(true);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public HttpSession getSession(boolean z) {
        HttpSession httpSession = this._session;
        if (httpSession != null) {
            SessionManager sessionManager = this._sessionManager;
            if (sessionManager == null || sessionManager.isValid(httpSession)) {
                return this._session;
            }
            this._session = null;
        }
        if (!z) {
            return null;
        }
        SessionManager sessionManager2 = this._sessionManager;
        if (sessionManager2 != null) {
            this._session = sessionManager2.newHttpSession(this);
            HttpCookie sessionCookie = this._sessionManager.getSessionCookie(this._session, getContextPath(), isSecure());
            if (sessionCookie != null) {
                this._connection.getResponse().addCookie(sessionCookie);
            }
            return this._session;
        }
        throw new IllegalStateException("No SessionManager");
    }

    public SessionManager getSessionManager() {
        return this._sessionManager;
    }

    public long getTimeStamp() {
        return this._timeStamp;
    }

    public Buffer getTimeStampBuffer() {
        if (this._timeStampBuffer == null && this._timeStamp > 0) {
            this._timeStampBuffer = HttpFields.__dateCache.formatBuffer(this._timeStamp);
        }
        return this._timeStampBuffer;
    }

    public HttpURI getUri() {
        return this._uri;
    }

    public UserIdentity getUserIdentity() {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this));
        }
        Authentication authentication2 = this._authentication;
        if (authentication2 instanceof Authentication.User) {
            return ((Authentication.User) authentication2).getUserIdentity();
        }
        return null;
    }

    public UserIdentity getResolvedUserIdentity() {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.User) {
            return ((Authentication.User) authentication).getUserIdentity();
        }
        return null;
    }

    public UserIdentity.Scope getUserIdentityScope() {
        return this._scope;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Principal getUserPrincipal() {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this));
        }
        Authentication authentication2 = this._authentication;
        if (authentication2 instanceof Authentication.User) {
            return ((Authentication.User) authentication2).getUserIdentity().getUserPrincipal();
        }
        return null;
    }

    public long getDispatchTime() {
        return this._dispatchTime;
    }

    public boolean isHandled() {
        return this._handled;
    }

    @Override // javax.servlet.ServletRequest
    public boolean isAsyncStarted() {
        return this._async.isAsyncStarted();
    }

    @Override // javax.servlet.ServletRequest
    public boolean isAsyncSupported() {
        return this._asyncSupported;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isRequestedSessionIdFromCookie() {
        return this._requestedSessionId != null && this._requestedSessionIdFromCookie;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isRequestedSessionIdFromUrl() {
        return this._requestedSessionId != null && !this._requestedSessionIdFromCookie;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isRequestedSessionIdFromURL() {
        return this._requestedSessionId != null && !this._requestedSessionIdFromCookie;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isRequestedSessionIdValid() {
        HttpSession session;
        return (this._requestedSessionId == null || (session = getSession(false)) == null || !this._sessionManager.getSessionIdManager().getClusterId(this._requestedSessionId).equals(this._sessionManager.getClusterId(session))) ? false : true;
    }

    @Override // javax.servlet.ServletRequest
    public boolean isSecure() {
        return this._connection.isConfidential(this);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean isUserInRole(String str) {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this));
        }
        Authentication authentication2 = this._authentication;
        if (authentication2 instanceof Authentication.User) {
            return ((Authentication.User) authentication2).isUserInRole(this._scope, str);
        }
        return false;
    }

    public HttpSession recoverNewSession(Object obj) {
        Map<Object, HttpSession> map = this._savedNewSessions;
        if (map == null) {
            return null;
        }
        return map.get(obj);
    }

    public void recycle() {
        if (this._inputState == 2) {
            try {
                int read = this._reader.read();
                while (read != -1) {
                    read = this._reader.read();
                }
            } catch (Exception e) {
                LOG.ignore(e);
                this._reader = null;
            }
        }
        setAuthentication(Authentication.NOT_CHECKED);
        this._async.recycle();
        this._asyncSupported = true;
        this._handled = false;
        if (this._context == null) {
            if (this._attributes != null) {
                this._attributes.clearAttributes();
            }
            this._characterEncoding = null;
            this._contextPath = null;
            CookieCutter cookieCutter = this._cookies;
            if (cookieCutter != null) {
                cookieCutter.reset();
            }
            this._cookiesExtracted = false;
            this._context = null;
            this._serverName = null;
            this._method = null;
            this._pathInfo = null;
            this._port = 0;
            this._protocol = HttpVersions.HTTP_1_1;
            this._queryEncoding = null;
            this._queryString = null;
            this._requestedSessionId = null;
            this._requestedSessionIdFromCookie = false;
            this._session = null;
            this._sessionManager = null;
            this._requestURI = null;
            this._scope = null;
            this._scheme = "http";
            this._servletPath = null;
            this._timeStamp = 0L;
            this._timeStampBuffer = null;
            this._uri = null;
            MultiMap<String> multiMap = this._baseParameters;
            if (multiMap != null) {
                multiMap.clear();
            }
            this._parameters = null;
            this._paramsExtracted = false;
            this._inputState = 0;
            Map<Object, HttpSession> map = this._savedNewSessions;
            if (map != null) {
                map.clear();
            }
            this._savedNewSessions = null;
            this._multiPartInputStream = null;
            return;
        }
        throw new IllegalStateException("Request in context!");
    }

    @Override // javax.servlet.ServletRequest
    public void removeAttribute(String str) {
        Object attribute = this._attributes == null ? null : this._attributes.getAttribute(str);
        if (this._attributes != null) {
            this._attributes.removeAttribute(str);
        }
        if (!(attribute == null || this._requestAttributeListeners == null)) {
            ServletRequestAttributeEvent servletRequestAttributeEvent = new ServletRequestAttributeEvent(this._context, this, str, attribute);
            int size = LazyList.size(this._requestAttributeListeners);
            for (int i = 0; i < size; i++) {
                ServletRequestAttributeListener servletRequestAttributeListener = (ServletRequestAttributeListener) LazyList.get(this._requestAttributeListeners, i);
                if (servletRequestAttributeListener instanceof ServletRequestAttributeListener) {
                    servletRequestAttributeListener.attributeRemoved(servletRequestAttributeEvent);
                }
            }
        }
    }

    public void removeEventListener(EventListener eventListener) {
        this._requestAttributeListeners = LazyList.remove(this._requestAttributeListeners, eventListener);
    }

    public void saveNewSession(Object obj, HttpSession httpSession) {
        if (this._savedNewSessions == null) {
            this._savedNewSessions = new HashMap();
        }
        this._savedNewSessions.put(obj, httpSession);
    }

    public void setAsyncSupported(boolean z) {
        this._asyncSupported = z;
    }

    @Override // javax.servlet.ServletRequest
    public void setAttribute(String str, Object obj) {
        String str2 = null;
        Object attribute = this._attributes == null ? null : this._attributes.getAttribute(str);
        if (str.startsWith("org.eclipse.jetty.")) {
            if ("org.eclipse.jetty.server.Request.queryEncoding".equals(str)) {
                if (obj != null) {
                    str2 = obj.toString();
                }
                setQueryEncoding(str2);
            } else if ("org.eclipse.jetty.server.sendContent".equals(str)) {
                try {
                    ((AbstractHttpConnection.Output) getServletResponse().getOutputStream()).sendContent(obj);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if ("org.eclipse.jetty.server.ResponseBuffer".equals(str)) {
                try {
                    ByteBuffer byteBuffer = (ByteBuffer) obj;
                    synchronized (byteBuffer) {
                        ((AbstractHttpConnection.Output) getServletResponse().getOutputStream()).sendResponse(byteBuffer.isDirect() ? new DirectNIOBuffer(byteBuffer, true) : new IndirectNIOBuffer(byteBuffer, true));
                    }
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            } else if ("org.eclipse.jetty.io.EndPoint.maxIdleTime".equalsIgnoreCase(str)) {
                try {
                    getConnection().getEndPoint().setMaxIdleTime(Integer.valueOf(obj.toString()).intValue());
                } catch (IOException e3) {
                    throw new RuntimeException(e3);
                }
            }
        }
        if (this._attributes == null) {
            this._attributes = new AttributesMap();
        }
        this._attributes.setAttribute(str, obj);
        if (this._requestAttributeListeners != null) {
            ServletRequestAttributeEvent servletRequestAttributeEvent = new ServletRequestAttributeEvent(this._context, this, str, attribute == null ? obj : attribute);
            int size = LazyList.size(this._requestAttributeListeners);
            for (int i = 0; i < size; i++) {
                ServletRequestAttributeListener servletRequestAttributeListener = (ServletRequestAttributeListener) LazyList.get(this._requestAttributeListeners, i);
                if (servletRequestAttributeListener instanceof ServletRequestAttributeListener) {
                    ServletRequestAttributeListener servletRequestAttributeListener2 = servletRequestAttributeListener;
                    if (attribute == null) {
                        servletRequestAttributeListener2.attributeAdded(servletRequestAttributeEvent);
                    } else if (obj == null) {
                        servletRequestAttributeListener2.attributeRemoved(servletRequestAttributeEvent);
                    } else {
                        servletRequestAttributeListener2.attributeReplaced(servletRequestAttributeEvent);
                    }
                }
            }
        }
    }

    public void setAttributes(Attributes attributes) {
        this._attributes = attributes;
    }

    public void setAuthentication(Authentication authentication) {
        this._authentication = authentication;
    }

    @Override // javax.servlet.ServletRequest
    public void setCharacterEncoding(String str) throws UnsupportedEncodingException {
        if (this._inputState == 0) {
            this._characterEncoding = str;
            if (!StringUtil.isUTF8(str)) {
                "".getBytes(str);
            }
        }
    }

    public void setCharacterEncodingUnchecked(String str) {
        this._characterEncoding = str;
    }

    protected final void setConnection(AbstractHttpConnection abstractHttpConnection) {
        this._connection = abstractHttpConnection;
        this._async.setConnection(abstractHttpConnection);
        this._endp = abstractHttpConnection.getEndPoint();
        this._dns = abstractHttpConnection.getResolveNames();
    }

    public void setContentType(String str) {
        this._connection.getRequestFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, str);
    }

    public void setContext(ContextHandler.Context context) {
        this._newContext = this._context != context;
        this._context = context;
    }

    public boolean takeNewContext() {
        boolean z = this._newContext;
        this._newContext = false;
        return z;
    }

    public void setContextPath(String str) {
        this._contextPath = str;
    }

    public void setCookies(Cookie[] cookieArr) {
        if (this._cookies == null) {
            this._cookies = new CookieCutter();
        }
        this._cookies.setCookies(cookieArr);
    }

    public void setDispatcherType(DispatcherType dispatcherType) {
        this._dispatcherType = dispatcherType;
    }

    public void setHandled(boolean z) {
        this._handled = z;
    }

    public void setMethod(String str) {
        this._method = str;
    }

    public void setParameters(MultiMap<String> multiMap) {
        if (multiMap == null) {
            multiMap = this._baseParameters;
        }
        this._parameters = multiMap;
        if (this._paramsExtracted && this._parameters == null) {
            throw new IllegalStateException();
        }
    }

    public void setPathInfo(String str) {
        this._pathInfo = str;
    }

    public void setProtocol(String str) {
        this._protocol = str;
    }

    public void setQueryEncoding(String str) {
        this._queryEncoding = str;
        this._queryString = null;
    }

    public void setQueryString(String str) {
        this._queryString = str;
        this._queryEncoding = null;
    }

    public void setRemoteAddr(String str) {
        this._remoteAddr = str;
    }

    public void setRemoteHost(String str) {
        this._remoteHost = str;
    }

    public void setRequestedSessionId(String str) {
        this._requestedSessionId = str;
    }

    public void setRequestedSessionIdFromCookie(boolean z) {
        this._requestedSessionIdFromCookie = z;
    }

    public void setRequestURI(String str) {
        this._requestURI = str;
    }

    public void setScheme(String str) {
        this._scheme = str;
    }

    public void setServerName(String str) {
        this._serverName = str;
    }

    public void setServerPort(int i) {
        this._port = i;
    }

    public void setServletPath(String str) {
        this._servletPath = str;
    }

    public void setSession(HttpSession httpSession) {
        this._session = httpSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this._sessionManager = sessionManager;
    }

    public void setTimeStamp(long j) {
        this._timeStamp = j;
    }

    public void setUri(HttpURI httpURI) {
        this._uri = httpURI;
    }

    public void setUserIdentityScope(UserIdentity.Scope scope) {
        this._scope = scope;
    }

    public void setDispatchTime(long j) {
        this._dispatchTime = j;
    }

    @Override // javax.servlet.ServletRequest
    public AsyncContext startAsync() throws IllegalStateException {
        if (this._asyncSupported) {
            this._async.suspend();
            return this._async;
        }
        throw new IllegalStateException("!asyncSupported");
    }

    @Override // javax.servlet.ServletRequest
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        if (this._asyncSupported) {
            this._async.suspend(this._context, servletRequest, servletResponse);
            return this._async;
        }
        throw new IllegalStateException("!asyncSupported");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._handled ? "[" : "(");
        sb.append(getMethod());
        sb.append(StringUtils.SPACE);
        sb.append(this._uri);
        sb.append(this._handled ? "]@" : ")@");
        sb.append(hashCode());
        sb.append(StringUtils.SPACE);
        sb.append(super.toString());
        return sb.toString();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this, httpServletResponse));
            return !(this._authentication instanceof Authentication.ResponseSent);
        }
        httpServletResponse.sendError(401);
        return false;
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Part getPart(String str) throws IOException, ServletException {
        if (getContentType() == null || !getContentType().startsWith(HttpHeaders.Values.MULTIPART_FORM_DATA)) {
            throw new ServletException("Content-Type != multipart/form-data");
        }
        if (this._multiPartInputStream == null) {
            MultipartConfigElement multipartConfigElement = (MultipartConfigElement) getAttribute(__MULTIPART_CONFIG_ELEMENT);
            if (multipartConfigElement != null) {
                ServletInputStream inputStream = getInputStream();
                String contentType = getContentType();
                ContextHandler.Context context = this._context;
                this._multiPartInputStream = new MultiPartInputStream(inputStream, contentType, multipartConfigElement, context != null ? (File) context.getAttribute(ServletContext.TEMPDIR) : null);
                setAttribute(__MULTIPART_INPUT_STREAM, this._multiPartInputStream);
                setAttribute(__MULTIPART_CONTEXT, this._context);
                Iterator<Part> it = this._multiPartInputStream.getParts().iterator();
                while (it.hasNext()) {
                    MultiPartInputStream.MultiPart multiPart = (MultiPartInputStream.MultiPart) it.next();
                    if (multiPart.getContentDispositionFilename() == null && multiPart.getFile() == null) {
                        String charsetFromContentType = multiPart.getContentType() != null ? MimeTypes.getCharsetFromContentType(new ByteArrayBuffer(multiPart.getContentType())) : null;
                        byte[] bytes = multiPart.getBytes();
                        if (charsetFromContentType == null) {
                            charsetFromContentType = "UTF-8";
                        }
                        String str2 = new String(bytes, charsetFromContentType);
                        getParameter("");
                        getParameters().add(multiPart.getName(), str2);
                    }
                }
            } else {
                throw new IllegalStateException("No multipart config for servlet");
            }
        }
        return this._multiPartInputStream.getPart(str);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public Collection<Part> getParts() throws IOException, ServletException {
        if (getContentType() == null || !getContentType().startsWith(HttpHeaders.Values.MULTIPART_FORM_DATA)) {
            throw new ServletException("Content-Type != multipart/form-data");
        }
        if (this._multiPartInputStream == null) {
            MultipartConfigElement multipartConfigElement = (MultipartConfigElement) getAttribute(__MULTIPART_CONFIG_ELEMENT);
            if (multipartConfigElement != null) {
                ServletInputStream inputStream = getInputStream();
                String contentType = getContentType();
                ContextHandler.Context context = this._context;
                this._multiPartInputStream = new MultiPartInputStream(inputStream, contentType, multipartConfigElement, context != null ? (File) context.getAttribute(ServletContext.TEMPDIR) : null);
                setAttribute(__MULTIPART_INPUT_STREAM, this._multiPartInputStream);
                setAttribute(__MULTIPART_CONTEXT, this._context);
                Iterator<Part> it = this._multiPartInputStream.getParts().iterator();
                while (it.hasNext()) {
                    MultiPartInputStream.MultiPart multiPart = (MultiPartInputStream.MultiPart) it.next();
                    if (multiPart.getContentDispositionFilename() == null && multiPart.getFile() == null) {
                        String charsetFromContentType = multiPart.getContentType() != null ? MimeTypes.getCharsetFromContentType(new ByteArrayBuffer(multiPart.getContentType())) : null;
                        byte[] bytes = multiPart.getBytes();
                        if (charsetFromContentType == null) {
                            charsetFromContentType = "UTF-8";
                        }
                        String str = new String(bytes, charsetFromContentType);
                        getParameter("");
                        getParameters().add(multiPart.getName(), str);
                    }
                }
            } else {
                throw new IllegalStateException("No multipart config for servlet");
            }
        }
        return this._multiPartInputStream.getParts();
    }

    @Override // javax.servlet.http.HttpServletRequest
    public void login(String str, String str2) throws ServletException {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            this._authentication = ((Authentication.Deferred) authentication).login(str, str2, this);
            if (this._authentication == null) {
                throw new ServletException();
            }
            return;
        }
        throw new ServletException("Authenticated as " + this._authentication);
    }

    @Override // javax.servlet.http.HttpServletRequest
    public void logout() throws ServletException {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.User) {
            ((Authentication.User) authentication).logout();
        }
        this._authentication = Authentication.UNAUTHENTICATED;
    }

    public void mergeQueryString(String str) {
        boolean z;
        MultiMap<String> multiMap = new MultiMap<>();
        UrlEncoded.decodeTo(str, multiMap, "UTF-8");
        if (!this._paramsExtracted) {
            extractParameters();
        }
        MultiMap<String> multiMap2 = this._parameters;
        if (multiMap2 == null || multiMap2.size() <= 0) {
            z = false;
        } else {
            z = false;
            for (Map.Entry<String, Object> entry : this._parameters.entrySet()) {
                String key = entry.getKey();
                if (multiMap.containsKey(key)) {
                    z = true;
                }
                Object value = entry.getValue();
                for (int i = 0; i < LazyList.size(value); i++) {
                    multiMap.add(key, LazyList.get(value, i));
                }
            }
        }
        String str2 = this._queryString;
        if (str2 != null && str2.length() > 0) {
            if (z) {
                StringBuilder sb = new StringBuilder();
                MultiMap multiMap3 = new MultiMap();
                UrlEncoded.decodeTo(this._queryString, multiMap3, getQueryEncoding());
                MultiMap multiMap4 = new MultiMap();
                UrlEncoded.decodeTo(str, multiMap4, "UTF-8");
                for (Map.Entry entry2 : multiMap3.entrySet()) {
                    String str3 = (String) entry2.getKey();
                    if (!multiMap4.containsKey(str3)) {
                        Object value2 = entry2.getValue();
                        for (int i2 = 0; i2 < LazyList.size(value2); i2++) {
                            sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                            sb.append(str3);
                            sb.append("=");
                            sb.append(LazyList.get(value2, i2));
                        }
                    }
                }
                str = str + ((Object) sb);
            } else {
                str = str + MusicGroupListActivity.SPECIAL_SYMBOL + this._queryString;
            }
        }
        setParameters(multiMap);
        setQueryString(str);
    }
}
