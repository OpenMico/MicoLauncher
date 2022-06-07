package org.eclipse.jetty.servlet;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.WriterOutputStream;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.InclusiveByteRange;
import org.eclipse.jetty.server.ResourceCache;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.nio.NIOConnector;
import org.eclipse.jetty.server.ssl.SslConnector;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.MultiPartOutputStream;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.resource.ResourceFactory;

/* loaded from: classes5.dex */
public class DefaultServlet extends HttpServlet implements ResourceFactory {
    private static final Logger LOG = Log.getLogger(DefaultServlet.class);
    private static final long serialVersionUID = 4930458713846881193L;
    private ResourceCache _cache;
    private ByteArrayBuffer _cacheControl;
    private ContextHandler _contextHandler;
    private ServletHolder _defaultHolder;
    private MimeTypes _mimeTypes;
    private String _relativeResourceBase;
    private Resource _resourceBase;
    private ServletContext _servletContext;
    private ServletHandler _servletHandler;
    private Resource _stylesheet;
    private String[] _welcomes;
    private boolean _acceptRanges = true;
    private boolean _dirAllowed = true;
    private boolean _welcomeServlets = false;
    private boolean _welcomeExactServlets = false;
    private boolean _redirectWelcome = false;
    private boolean _gzip = true;
    private boolean _pathInfoOnly = false;
    private boolean _useFileMappedBuffer = false;

    @Override // javax.servlet.GenericServlet
    public void init() throws UnavailableException {
        this._servletContext = getServletContext();
        this._contextHandler = initContextHandler(this._servletContext);
        this._mimeTypes = this._contextHandler.getMimeTypes();
        this._welcomes = this._contextHandler.getWelcomeFiles();
        if (this._welcomes == null) {
            this._welcomes = new String[]{"index.html", "index.jsp"};
        }
        this._acceptRanges = getInitBoolean("acceptRanges", this._acceptRanges);
        this._dirAllowed = getInitBoolean("dirAllowed", this._dirAllowed);
        this._redirectWelcome = getInitBoolean("redirectWelcome", this._redirectWelcome);
        this._gzip = getInitBoolean("gzip", this._gzip);
        this._pathInfoOnly = getInitBoolean("pathInfoOnly", this._pathInfoOnly);
        if ("exact".equals(getInitParameter("welcomeServlets"))) {
            this._welcomeExactServlets = true;
            this._welcomeServlets = false;
        } else {
            this._welcomeServlets = getInitBoolean("welcomeServlets", this._welcomeServlets);
        }
        if (getInitParameter(Constants.EXTRA_KEY_ALIASES) != null) {
            this._contextHandler.setAliases(getInitBoolean(Constants.EXTRA_KEY_ALIASES, false));
        }
        boolean isAliases = this._contextHandler.isAliases();
        if (isAliases || FileResource.getCheckAliases()) {
            if (isAliases) {
                this._servletContext.log("Aliases are enabled");
            }
            this._useFileMappedBuffer = getInitBoolean("useFileMappedBuffer", this._useFileMappedBuffer);
            this._relativeResourceBase = getInitParameter("relativeResourceBase");
            String initParameter = getInitParameter("resourceBase");
            if (initParameter != null) {
                if (this._relativeResourceBase == null) {
                    try {
                        this._resourceBase = this._contextHandler.newResource(initParameter);
                    } catch (Exception e) {
                        LOG.warn(Log.EXCEPTION, e);
                        throw new UnavailableException(e.toString());
                    }
                } else {
                    throw new UnavailableException("resourceBase & relativeResourceBase");
                }
            }
            String initParameter2 = getInitParameter("stylesheet");
            if (initParameter2 != null) {
                try {
                    this._stylesheet = Resource.newResource(initParameter2);
                    if (!this._stylesheet.exists()) {
                        LOG.warn("!" + initParameter2, new Object[0]);
                        this._stylesheet = null;
                    }
                } catch (Exception e2) {
                    LOG.warn(e2.toString(), new Object[0]);
                    LOG.debug(e2);
                }
            }
            if (this._stylesheet == null) {
                this._stylesheet = Resource.newResource(getClass().getResource("/jetty-dir.css"));
            }
            String initParameter3 = getInitParameter("cacheControl");
            if (initParameter3 != null) {
                this._cacheControl = new ByteArrayBuffer(initParameter3);
            }
            String initParameter4 = getInitParameter("resourceCache");
            int initInt = getInitInt("maxCacheSize", -2);
            int initInt2 = getInitInt("maxCachedFileSize", -2);
            int initInt3 = getInitInt("maxCachedFiles", -2);
            if (initParameter4 != null) {
                if (!(initInt == -1 && initInt2 == -2 && initInt3 == -2)) {
                    LOG.debug("ignoring resource cache configuration, using resourceCache attribute", new Object[0]);
                }
                if (this._relativeResourceBase == null && this._resourceBase == null) {
                    this._cache = (ResourceCache) this._servletContext.getAttribute(initParameter4);
                    LOG.debug("Cache {}={}", initParameter4, this._cache);
                } else {
                    throw new UnavailableException("resourceCache specified with resource bases");
                }
            }
            try {
                if (this._cache == null && initInt3 > 0) {
                    this._cache = new ResourceCache(null, this, this._mimeTypes, this._useFileMappedBuffer);
                    if (initInt > 0) {
                        this._cache.setMaxCacheSize(initInt);
                    }
                    if (initInt2 >= -1) {
                        this._cache.setMaxCachedFileSize(initInt2);
                    }
                    if (initInt3 >= -1) {
                        this._cache.setMaxCachedFiles(initInt3);
                    }
                }
                this._servletHandler = (ServletHandler) this._contextHandler.getChildHandlerByClass(ServletHandler.class);
                ServletHolder[] servlets = this._servletHandler.getServlets();
                for (ServletHolder servletHolder : servlets) {
                    if (servletHolder.getServletInstance() == this) {
                        this._defaultHolder = servletHolder;
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("resource base = " + this._resourceBase, new Object[0]);
                }
            } catch (Exception e3) {
                LOG.warn(Log.EXCEPTION, e3);
                throw new UnavailableException(e3.toString());
            }
        } else {
            throw new IllegalStateException("Alias checking disabled");
        }
    }

    protected ContextHandler initContextHandler(ServletContext servletContext) {
        if (ContextHandler.getCurrentContext() != null) {
            return ContextHandler.getCurrentContext().getContextHandler();
        }
        if (servletContext instanceof ContextHandler.Context) {
            return ((ContextHandler.Context) servletContext).getContextHandler();
        }
        throw new IllegalArgumentException("The servletContext " + servletContext + StringUtils.SPACE + servletContext.getClass().getName() + " is not " + ContextHandler.Context.class.getName());
    }

    @Override // javax.servlet.GenericServlet, javax.servlet.ServletConfig
    public String getInitParameter(String str) {
        ServletContext servletContext = getServletContext();
        String initParameter = servletContext.getInitParameter("org.eclipse.jetty.servlet.Default." + str);
        return initParameter == null ? super.getInitParameter(str) : initParameter;
    }

    private boolean getInitBoolean(String str, boolean z) {
        String initParameter = getInitParameter(str);
        return (initParameter == null || initParameter.length() == 0) ? z : initParameter.startsWith(ai.aF) || initParameter.startsWith(ExifInterface.GPS_DIRECTION_TRUE) || initParameter.startsWith("y") || initParameter.startsWith("Y") || initParameter.startsWith("1");
    }

    private int getInitInt(String str, int i) {
        String initParameter = getInitParameter(str);
        if (initParameter == null) {
            initParameter = getInitParameter(str);
        }
        return (initParameter == null || initParameter.length() <= 0) ? i : Integer.parseInt(initParameter);
    }

    @Override // org.eclipse.jetty.util.resource.ResourceFactory
    public Resource getResource(String str) {
        String str2 = this._relativeResourceBase;
        if (str2 != null) {
            str = URIUtil.addPaths(str2, str);
        }
        Resource resource = null;
        try {
            if (this._resourceBase != null) {
                resource = this._resourceBase.addPath(str);
            } else {
                resource = this._contextHandler.newResource(this._servletContext.getResource(str));
            }
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Resource " + str + "=" + resource, new Object[0]);
            }
        } catch (IOException e) {
            LOG.ignore(e);
        }
        return ((resource == null || !resource.exists()) && str.endsWith("/jetty-dir.css")) ? this._stylesheet : resource;
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ce, code lost:
        if (r11.isDirectory() != false) goto L_0x00d0;
     */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x02dc A[Catch: IllegalArgumentException -> 0x0307, all -> 0x0305, TRY_LEAVE, TryCatch #9 {IllegalArgumentException -> 0x0307, all -> 0x0305, blocks: (B:73:0x010b, B:75:0x0113, B:79:0x0135, B:81:0x0143, B:84:0x014b, B:87:0x0153, B:89:0x015b, B:91:0x0161, B:93:0x0170, B:95:0x0176, B:96:0x018a, B:98:0x019f, B:112:0x01e9, B:114:0x01ef, B:117:0x01f9, B:119:0x01ff, B:121:0x020e, B:123:0x0217, B:125:0x021d, B:126:0x0244, B:127:0x0257, B:129:0x025d, B:131:0x0263, B:132:0x0268, B:133:0x0272, B:143:0x0299, B:144:0x029d, B:159:0x02d5, B:160:0x02d6, B:162:0x02dc, B:168:0x02ee, B:169:0x0304), top: B:199:0x010b }] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x02e4  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02ee A[Catch: IllegalArgumentException -> 0x0307, all -> 0x0305, TRY_ENTER, TryCatch #9 {IllegalArgumentException -> 0x0307, all -> 0x0305, blocks: (B:73:0x010b, B:75:0x0113, B:79:0x0135, B:81:0x0143, B:84:0x014b, B:87:0x0153, B:89:0x015b, B:91:0x0161, B:93:0x0170, B:95:0x0176, B:96:0x018a, B:98:0x019f, B:112:0x01e9, B:114:0x01ef, B:117:0x01f9, B:119:0x01ff, B:121:0x020e, B:123:0x0217, B:125:0x021d, B:126:0x0244, B:127:0x0257, B:129:0x025d, B:131:0x0263, B:132:0x0268, B:133:0x0272, B:143:0x0299, B:144:0x029d, B:159:0x02d5, B:160:0x02d6, B:162:0x02dc, B:168:0x02ee, B:169:0x0304), top: B:199:0x010b }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0317 A[Catch: all -> 0x032c, TRY_LEAVE, TryCatch #7 {all -> 0x032c, blocks: (B:174:0x030a, B:176:0x0317), top: B:191:0x030a }] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00e8 A[Catch: IllegalArgumentException -> 0x0107, all -> 0x0103, TryCatch #10 {IllegalArgumentException -> 0x0107, all -> 0x0103, blocks: (B:46:0x00c4, B:48:0x00ca, B:61:0x00e8, B:63:0x00ec, B:64:0x00f3, B:67:0x00fc, B:101:0x01b5, B:103:0x01bb, B:106:0x01c3, B:108:0x01d2, B:109:0x01d5), top: B:197:0x00c4 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0113 A[Catch: IllegalArgumentException -> 0x0307, all -> 0x0305, TryCatch #9 {IllegalArgumentException -> 0x0307, all -> 0x0305, blocks: (B:73:0x010b, B:75:0x0113, B:79:0x0135, B:81:0x0143, B:84:0x014b, B:87:0x0153, B:89:0x015b, B:91:0x0161, B:93:0x0170, B:95:0x0176, B:96:0x018a, B:98:0x019f, B:112:0x01e9, B:114:0x01ef, B:117:0x01f9, B:119:0x01ff, B:121:0x020e, B:123:0x0217, B:125:0x021d, B:126:0x0244, B:127:0x0257, B:129:0x025d, B:131:0x0263, B:132:0x0268, B:133:0x0272, B:143:0x0299, B:144:0x029d, B:159:0x02d5, B:160:0x02d6, B:162:0x02dc, B:168:0x02ee, B:169:0x0304), top: B:199:0x010b }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0151  */
    @Override // javax.servlet.http.HttpServlet
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void doGet(javax.servlet.http.HttpServletRequest r17, javax.servlet.http.HttpServletResponse r18) throws javax.servlet.ServletException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 828
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.DefaultServlet.doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    private boolean hasDefinedRange(Enumeration<String> enumeration) {
        return enumeration != null && enumeration.hasMoreElements();
    }

    @Override // javax.servlet.http.HttpServlet
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }

    @Override // javax.servlet.http.HttpServlet
    protected void doTrace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.sendError(405);
    }

    @Override // javax.servlet.http.HttpServlet
    protected void doOptions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.setHeader("Allow", "GET,HEAD,POST,OPTIONS");
    }

    private String getWelcomeFile(String str) throws MalformedURLException, IOException {
        PathMap.Entry holderEntry;
        String str2 = null;
        if (this._welcomes == null) {
            return null;
        }
        int i = 0;
        while (true) {
            String[] strArr = this._welcomes;
            if (i >= strArr.length) {
                return str2;
            }
            String addPaths = URIUtil.addPaths(str, strArr[i]);
            Resource resource = getResource(addPaths);
            if (resource != null && resource.exists()) {
                return this._welcomes[i];
            }
            if ((this._welcomeServlets || this._welcomeExactServlets) && str2 == null && (holderEntry = this._servletHandler.getHolderEntry(addPaths)) != null && holderEntry.getValue() != this._defaultHolder && (this._welcomeServlets || (this._welcomeExactServlets && holderEntry.getKey().equals(addPaths)))) {
                str2 = addPaths;
            }
            i++;
        }
    }

    protected boolean passConditionalHeaders(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Resource resource, HttpContent httpContent) throws IOException {
        Buffer lastModified;
        try {
            if (!httpServletRequest.getMethod().equals("HEAD")) {
                String header = httpServletRequest.getHeader("If-Modified-Since");
                if (header != null) {
                    Response response = Response.getResponse(httpServletResponse);
                    if (httpContent == null || (lastModified = httpContent.getLastModified()) == null || !header.equals(lastModified.toString())) {
                        long dateHeader = httpServletRequest.getDateHeader("If-Modified-Since");
                        if (dateHeader != -1 && resource.lastModified() / 1000 <= dateHeader / 1000) {
                            response.reset(true);
                            response.setStatus(304);
                            response.flushBuffer();
                            return false;
                        }
                    } else {
                        response.reset(true);
                        response.setStatus(304);
                        response.flushBuffer();
                        return false;
                    }
                }
                long dateHeader2 = httpServletRequest.getDateHeader("If-Unmodified-Since");
                if (dateHeader2 != -1 && resource.lastModified() / 1000 > dateHeader2 / 1000) {
                    httpServletResponse.sendError(412);
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            if (!httpServletResponse.isCommitted()) {
                httpServletResponse.sendError(400, e.getMessage());
            }
            throw e;
        }
    }

    protected void sendDirectory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Resource resource, String str) throws IOException {
        if (!this._dirAllowed) {
            httpServletResponse.sendError(403);
            return;
        }
        String addPaths = URIUtil.addPaths(httpServletRequest.getRequestURI(), "/");
        Resource resource2 = this._resourceBase;
        if (resource2 != null) {
            if (resource2 instanceof ResourceCollection) {
                resource = resource2.addPath(str);
            }
        } else if (this._contextHandler.getBaseResource() instanceof ResourceCollection) {
            resource = this._contextHandler.getBaseResource().addPath(str);
        }
        boolean z = true;
        if (str.length() <= 1) {
            z = false;
        }
        String listHTML = resource.getListHTML(addPaths, z);
        if (listHTML == null) {
            httpServletResponse.sendError(403, "No directory");
            return;
        }
        byte[] bytes = listHTML.getBytes("UTF-8");
        httpServletResponse.setContentType("text/html; charset=UTF-8");
        httpServletResponse.setContentLength(bytes.length);
        httpServletResponse.getOutputStream().write(bytes);
    }

    protected void sendData(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean z, Resource resource, HttpContent httpContent, Enumeration enumeration) throws IOException {
        boolean z2;
        long j;
        boolean z3;
        OutputStream outputStream;
        int i = 0;
        if (httpContent == null) {
            j = resource.length();
            z2 = false;
        } else {
            Connector connector = AbstractHttpConnection.getCurrentConnection().getConnector();
            z2 = (connector instanceof NIOConnector) && ((NIOConnector) connector).getUseDirectBuffers() && !(connector instanceof SslConnector);
            j = httpContent.getContentLength();
        }
        try {
            outputStream = httpServletResponse.getOutputStream();
            z3 = outputStream instanceof HttpOutput ? ((HttpOutput) outputStream).isWritten() : AbstractHttpConnection.getCurrentConnection().getGenerator().isWritten();
        } catch (IllegalStateException unused) {
            outputStream = new WriterOutputStream(httpServletResponse.getWriter());
            z3 = true;
        }
        long j2 = -1;
        if (enumeration != null && enumeration.hasMoreElements() && j >= 0) {
            List satisfiableRanges = InclusiveByteRange.satisfiableRanges(enumeration, j);
            if (satisfiableRanges == null || satisfiableRanges.size() == 0) {
                writeHeaders(httpServletResponse, httpContent, j);
                httpServletResponse.setStatus(416);
                httpServletResponse.setHeader("Content-Range", InclusiveByteRange.to416HeaderRangeString(j));
                resource.writeTo(outputStream, 0L, j);
            } else if (satisfiableRanges.size() == 1) {
                InclusiveByteRange inclusiveByteRange = (InclusiveByteRange) satisfiableRanges.get(0);
                long size = inclusiveByteRange.getSize(j);
                writeHeaders(httpServletResponse, httpContent, size);
                httpServletResponse.setStatus(206);
                httpServletResponse.setHeader("Content-Range", inclusiveByteRange.toHeaderRangeString(j));
                resource.writeTo(outputStream, inclusiveByteRange.getFirst(j), size);
            } else {
                writeHeaders(httpServletResponse, httpContent, -1L);
                String obj = httpContent.getContentType().toString();
                MultiPartOutputStream multiPartOutputStream = new MultiPartOutputStream(outputStream);
                httpServletResponse.setStatus(206);
                httpServletResponse.setContentType((httpServletRequest.getHeader(HttpHeaders.REQUEST_RANGE) != null ? "multipart/x-byteranges; boundary=" : "multipart/byteranges; boundary=") + multiPartOutputStream.getBoundary());
                InputStream inputStream = resource.getInputStream();
                String[] strArr = new String[satisfiableRanges.size()];
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    i = 2;
                    if (i2 >= satisfiableRanges.size()) {
                        break;
                    }
                    InclusiveByteRange inclusiveByteRange2 = (InclusiveByteRange) satisfiableRanges.get(i2);
                    strArr[i2] = inclusiveByteRange2.toHeaderRangeString(j);
                    long j3 = i3;
                    if (i2 > 0) {
                    }
                    i3 = (int) (j3 + i + 2 + multiPartOutputStream.getBoundary().length() + 2 + 12 + 2 + obj.length() + 2 + 13 + 2 + strArr[i2].length() + 2 + 2 + (inclusiveByteRange2.getLast(j) - inclusiveByteRange2.getFirst(j)) + 1);
                    i2++;
                    i = 0;
                }
                httpServletResponse.setContentLength(i3 + multiPartOutputStream.getBoundary().length() + 4 + 2 + 2);
                long j4 = 0;
                for (int i4 = 0; i4 < satisfiableRanges.size(); i4++) {
                    InclusiveByteRange inclusiveByteRange3 = (InclusiveByteRange) satisfiableRanges.get(i4);
                    multiPartOutputStream.startPart(obj, new String[]{"Content-Range: " + strArr[i4]});
                    long first = inclusiveByteRange3.getFirst(j);
                    long size2 = inclusiveByteRange3.getSize(j);
                    if (inputStream != null) {
                        if (first < j4) {
                            inputStream.close();
                            inputStream = resource.getInputStream();
                            j4 = 0;
                        }
                        if (j4 < first) {
                            inputStream.skip(first - j4);
                            j4 = first;
                        }
                        IO.copy(inputStream, multiPartOutputStream, size2);
                        j4 += size2;
                    } else {
                        resource.writeTo(multiPartOutputStream, first, size2);
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                multiPartOutputStream.close();
            }
        } else if (z) {
            resource.writeTo(outputStream, 0L, j);
        } else if (httpContent == null || z3 || !(outputStream instanceof HttpOutput)) {
            if (!z3) {
                j2 = j;
            }
            writeHeaders(httpServletResponse, httpContent, j2);
            Buffer indirectBuffer = httpContent == null ? null : httpContent.getIndirectBuffer();
            if (indirectBuffer != null) {
                indirectBuffer.writeTo(outputStream);
            } else {
                resource.writeTo(outputStream, 0L, j);
            }
        } else if (httpServletResponse instanceof Response) {
            writeOptionHeaders(((Response) httpServletResponse).getHttpFields());
            ((AbstractHttpConnection.Output) outputStream).sendContent(httpContent);
        } else {
            Buffer directBuffer = z2 ? httpContent.getDirectBuffer() : httpContent.getIndirectBuffer();
            if (directBuffer != null) {
                writeHeaders(httpServletResponse, httpContent, j);
                ((AbstractHttpConnection.Output) outputStream).sendContent(directBuffer);
                return;
            }
            writeHeaders(httpServletResponse, httpContent, j);
            resource.writeTo(outputStream, 0L, j);
        }
    }

    protected void writeHeaders(HttpServletResponse httpServletResponse, HttpContent httpContent, long j) throws IOException {
        if (httpContent.getContentType() != null && httpServletResponse.getContentType() == null) {
            httpServletResponse.setContentType(httpContent.getContentType().toString());
        }
        if (httpServletResponse instanceof Response) {
            Response response = (Response) httpServletResponse;
            HttpFields httpFields = response.getHttpFields();
            if (httpContent.getLastModified() != null) {
                httpFields.put(HttpHeaders.LAST_MODIFIED_BUFFER, httpContent.getLastModified());
            } else if (httpContent.getResource() != null) {
                long lastModified = httpContent.getResource().lastModified();
                if (lastModified != -1) {
                    httpFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, lastModified);
                }
            }
            if (j != -1) {
                response.setLongContentLength(j);
            }
            writeOptionHeaders(httpFields);
            return;
        }
        long lastModified2 = httpContent.getResource().lastModified();
        if (lastModified2 >= 0) {
            httpServletResponse.setDateHeader("Last-Modified", lastModified2);
        }
        if (j != -1) {
            if (j < 2147483647L) {
                httpServletResponse.setContentLength((int) j);
            } else {
                httpServletResponse.setHeader("Content-Length", Long.toString(j));
            }
        }
        writeOptionHeaders(httpServletResponse);
    }

    protected void writeOptionHeaders(HttpFields httpFields) throws IOException {
        if (this._acceptRanges) {
            httpFields.put(HttpHeaders.ACCEPT_RANGES_BUFFER, HttpHeaderValues.BYTES_BUFFER);
        }
        if (this._cacheControl != null) {
            httpFields.put(HttpHeaders.CACHE_CONTROL_BUFFER, this._cacheControl);
        }
    }

    protected void writeOptionHeaders(HttpServletResponse httpServletResponse) throws IOException {
        if (this._acceptRanges) {
            httpServletResponse.setHeader("Accept-Ranges", "bytes");
        }
        ByteArrayBuffer byteArrayBuffer = this._cacheControl;
        if (byteArrayBuffer != null) {
            httpServletResponse.setHeader("Cache-Control", byteArrayBuffer.toString());
        }
    }

    @Override // javax.servlet.GenericServlet, javax.servlet.Servlet
    public void destroy() {
        ResourceCache resourceCache = this._cache;
        if (resourceCache != null) {
            resourceCache.flushCache();
        }
        super.destroy();
    }
}
