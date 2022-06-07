package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.B64Code;

/* loaded from: classes5.dex */
public class BasicAuthenticator extends LoginAuthenticator {
    @Override // org.eclipse.jetty.security.Authenticator
    public String getAuthMethod() {
        return "BASIC";
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z, Authentication.User user) throws ServerAuthException {
        return true;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public Authentication validateRequest(ServletRequest servletRequest, ServletResponse servletResponse, boolean z) throws ServerAuthException {
        int indexOf;
        String decode;
        int indexOf2;
        UserIdentity login;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String header = httpServletRequest.getHeader("Authorization");
        try {
            if (!z) {
                return new DeferredAuthentication(this);
            }
            if (header != null && (indexOf = header.indexOf(32)) > 0 && "basic".equalsIgnoreCase(header.substring(0, indexOf)) && (indexOf2 = (decode = B64Code.decode(header.substring(indexOf + 1), "ISO-8859-1")).indexOf(58)) > 0 && (login = login(decode.substring(0, indexOf2), decode.substring(indexOf2 + 1), httpServletRequest)) != null) {
                return new UserAuthentication(getAuthMethod(), login);
            }
            if (DeferredAuthentication.isDeferred(httpServletResponse)) {
                return Authentication.UNAUTHENTICATED;
            }
            httpServletResponse.setHeader("WWW-Authenticate", "basic realm=\"" + this._loginService.getName() + '\"');
            httpServletResponse.sendError(401);
            return Authentication.SEND_CONTINUE;
        } catch (IOException e) {
            throw new ServerAuthException(e);
        }
    }
}
