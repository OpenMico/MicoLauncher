package org.eclipse.jetty.continuation;

import com.xiaomi.mipush.sdk.Constants;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;

/* loaded from: classes5.dex */
public class ContinuationFilter implements Filter {
    static boolean __debug;
    static boolean _initialized;
    ServletContext _context;
    private boolean _debug;
    private boolean _faux;
    private boolean _filtered;
    private boolean _jetty6;

    /* loaded from: classes5.dex */
    public interface FilteredContinuation extends Continuation {
        boolean enter(ServletResponse servletResponse);

        boolean exit();
    }

    @Override // javax.servlet.Filter
    public void destroy() {
    }

    @Override // javax.servlet.Filter
    public void init(FilterConfig filterConfig) throws ServletException {
        boolean equals = "org.eclipse.jetty.servlet".equals(filterConfig.getClass().getPackage().getName());
        this._context = filterConfig.getServletContext();
        String initParameter = filterConfig.getInitParameter("debug");
        boolean z = false;
        this._debug = initParameter != null && Boolean.parseBoolean(initParameter);
        if (this._debug) {
            __debug = true;
        }
        String initParameter2 = filterConfig.getInitParameter("jetty6");
        if (initParameter2 == null) {
            initParameter2 = filterConfig.getInitParameter("partial");
        }
        if (initParameter2 != null) {
            this._jetty6 = Boolean.parseBoolean(initParameter2);
        } else {
            this._jetty6 = ContinuationSupport.__jetty6 && !equals;
        }
        String initParameter3 = filterConfig.getInitParameter("faux");
        if (initParameter3 != null) {
            this._faux = Boolean.parseBoolean(initParameter3);
        } else {
            this._faux = !equals && !this._jetty6 && this._context.getMajorVersion() < 3;
        }
        if (this._faux || this._jetty6) {
            z = true;
        }
        this._filtered = z;
        if (this._debug) {
            this._context.log("ContinuationFilter  jetty=" + equals + " jetty6=" + this._jetty6 + " faux=" + this._faux + " filtered=" + this._filtered + " servlet3=" + ContinuationSupport.__servlet3);
        }
        _initialized = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
        if (r2.enter(r7) != false) goto L_0x0031;
     */
    @Override // javax.servlet.Filter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doFilter(javax.servlet.ServletRequest r6, javax.servlet.ServletResponse r7, javax.servlet.FilterChain r8) throws java.io.IOException, javax.servlet.ServletException {
        /*
            r5 = this;
            boolean r0 = r5._filtered
            if (r0 == 0) goto L_0x0076
            java.lang.String r0 = "org.eclipse.jetty.continuation"
            java.lang.Object r0 = r6.getAttribute(r0)
            org.eclipse.jetty.continuation.Continuation r0 = (org.eclipse.jetty.continuation.Continuation) r0
            boolean r1 = r5._faux
            if (r1 == 0) goto L_0x0021
            if (r0 == 0) goto L_0x0016
            boolean r1 = r0 instanceof org.eclipse.jetty.continuation.FauxContinuation
            if (r1 != 0) goto L_0x0021
        L_0x0016:
            org.eclipse.jetty.continuation.FauxContinuation r0 = new org.eclipse.jetty.continuation.FauxContinuation
            r0.<init>(r6)
            java.lang.String r1 = "org.eclipse.jetty.continuation"
            r6.setAttribute(r1, r0)
            goto L_0x0023
        L_0x0021:
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r0 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r0
        L_0x0023:
            r1 = 0
            r2 = r0
            r0 = r1
        L_0x0026:
            if (r0 != 0) goto L_0x0080
            r0 = 1
            if (r2 == 0) goto L_0x0031
            boolean r3 = r2.enter(r7)     // Catch: ContinuationThrowable -> 0x004b, all -> 0x0049
            if (r3 == 0) goto L_0x0034
        L_0x0031:
            r8.doFilter(r6, r7)     // Catch: ContinuationThrowable -> 0x004b, all -> 0x0049
        L_0x0034:
            if (r2 != 0) goto L_0x003e
            java.lang.String r2 = "org.eclipse.jetty.continuation"
            java.lang.Object r2 = r6.getAttribute(r2)
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r2 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r2
        L_0x003e:
            if (r2 == 0) goto L_0x0026
            boolean r3 = r2.exit()
            if (r3 == 0) goto L_0x0047
            goto L_0x0026
        L_0x0047:
            r0 = r1
            goto L_0x0026
        L_0x0049:
            r7 = move-exception
            goto L_0x0064
        L_0x004b:
            r3 = move-exception
            java.lang.String r4 = "faux"
            r5.debug(r4, r3)     // Catch: all -> 0x0049
            if (r2 != 0) goto L_0x005b
            java.lang.String r2 = "org.eclipse.jetty.continuation"
            java.lang.Object r2 = r6.getAttribute(r2)
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r2 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r2
        L_0x005b:
            if (r2 == 0) goto L_0x0026
            boolean r3 = r2.exit()
            if (r3 == 0) goto L_0x0047
            goto L_0x0026
        L_0x0064:
            if (r2 != 0) goto L_0x006f
            java.lang.String r8 = "org.eclipse.jetty.continuation"
            java.lang.Object r6 = r6.getAttribute(r8)
            r2 = r6
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r2 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r2
        L_0x006f:
            if (r2 == 0) goto L_0x0075
            boolean r6 = r2.exit()
        L_0x0075:
            throw r7
        L_0x0076:
            r8.doFilter(r6, r7)     // Catch: ContinuationThrowable -> 0x007a
            goto L_0x0080
        L_0x007a:
            r6 = move-exception
            java.lang.String r7 = "caught"
            r5.debug(r7, r6)
        L_0x0080:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.continuation.ContinuationFilter.doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain):void");
    }

    private void debug(String str) {
        if (this._debug) {
            this._context.log(str);
        }
    }

    private void debug(String str, Throwable th) {
        if (!this._debug) {
            return;
        }
        if (th instanceof ContinuationThrowable) {
            ServletContext servletContext = this._context;
            servletContext.log(str + Constants.COLON_SEPARATOR + th);
            return;
        }
        this._context.log(str, th);
    }
}
