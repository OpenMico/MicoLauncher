package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.URIUtil;

/* loaded from: classes5.dex */
public class MovedContextHandler extends ContextHandler {
    boolean _discardPathInfo;
    boolean _discardQuery;
    String _expires;
    String _newContextURL;
    boolean _permanent;
    final Redirector _redirector = new Redirector();

    public MovedContextHandler() {
        setHandler(this._redirector);
        setAllowNullPathInfo(true);
    }

    public MovedContextHandler(HandlerContainer handlerContainer, String str, String str2) {
        super(handlerContainer, str);
        this._newContextURL = str2;
        setHandler(this._redirector);
    }

    public boolean isDiscardPathInfo() {
        return this._discardPathInfo;
    }

    public void setDiscardPathInfo(boolean z) {
        this._discardPathInfo = z;
    }

    public String getNewContextURL() {
        return this._newContextURL;
    }

    public void setNewContextURL(String str) {
        this._newContextURL = str;
    }

    public boolean isPermanent() {
        return this._permanent;
    }

    public void setPermanent(boolean z) {
        this._permanent = z;
    }

    public boolean isDiscardQuery() {
        return this._discardQuery;
    }

    public void setDiscardQuery(boolean z) {
        this._discardQuery = z;
    }

    /* loaded from: classes5.dex */
    private class Redirector extends AbstractHandler {
        private Redirector() {
        }

        @Override // org.eclipse.jetty.server.Handler
        public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
            if (MovedContextHandler.this._newContextURL != null) {
                String str2 = MovedContextHandler.this._newContextURL;
                if (!MovedContextHandler.this._discardPathInfo && httpServletRequest.getPathInfo() != null) {
                    str2 = URIUtil.addPaths(str2, httpServletRequest.getPathInfo());
                }
                StringBuilder sb = URIUtil.hasScheme(str2) ? new StringBuilder() : request.getRootURL();
                sb.append(str2);
                if (!MovedContextHandler.this._discardQuery && httpServletRequest.getQueryString() != null) {
                    sb.append('?');
                    sb.append(httpServletRequest.getQueryString().replaceAll("\r\n?&=", "!"));
                }
                httpServletResponse.setHeader("Location", sb.toString());
                if (MovedContextHandler.this._expires != null) {
                    httpServletResponse.setHeader("Expires", MovedContextHandler.this._expires);
                }
                httpServletResponse.setStatus(MovedContextHandler.this._permanent ? 301 : 302);
                httpServletResponse.setContentLength(0);
                request.setHandled(true);
            }
        }
    }

    public String getExpires() {
        return this._expires;
    }

    public void setExpires(String str) {
        this._expires = str;
    }
}
