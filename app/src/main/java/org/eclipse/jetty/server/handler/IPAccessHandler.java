package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.IPAddressMap;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class IPAccessHandler extends HandlerWrapper {
    private static final Logger LOG = Log.getLogger(IPAccessHandler.class);
    IPAddressMap<PathMap> _white = new IPAddressMap<>();
    IPAddressMap<PathMap> _black = new IPAddressMap<>();

    public IPAccessHandler() {
    }

    public IPAccessHandler(String[] strArr, String[] strArr2) {
        if (strArr != null && strArr.length > 0) {
            setWhite(strArr);
        }
        if (strArr2 != null && strArr2.length > 0) {
            setBlack(strArr2);
        }
    }

    public void addWhite(String str) {
        add(str, this._white);
    }

    public void addBlack(String str) {
        add(str, this._black);
    }

    public void setWhite(String[] strArr) {
        set(strArr, this._white);
    }

    public void setBlack(String[] strArr) {
        set(strArr, this._black);
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        EndPoint endPoint;
        String remoteAddr;
        AbstractHttpConnection connection = request.getConnection();
        if (connection == null || (endPoint = connection.getEndPoint()) == null || (remoteAddr = endPoint.getRemoteAddr()) == null || isAddrUriAllowed(remoteAddr, request.getPathInfo())) {
            getHandler().handle(str, request, httpServletRequest, httpServletResponse);
            return;
        }
        httpServletResponse.sendError(403);
        request.setHandled(true);
    }

    protected void add(String str, IPAddressMap<PathMap> iPAddressMap) {
        boolean z;
        int i;
        if (str != null && str.length() > 0) {
            if (str.indexOf(124) > 0) {
                i = str.indexOf(124);
                z = false;
            } else {
                i = str.indexOf(47);
                z = i >= 0;
            }
            String substring = i > 0 ? str.substring(0, i) : str;
            String substring2 = i > 0 ? str.substring(i) : "/*";
            if (substring.endsWith(".")) {
                z = true;
            }
            if (substring2 != null && (substring2.startsWith("|") || substring2.startsWith("/*."))) {
                substring2 = substring2.substring(1);
            }
            PathMap pathMap = iPAddressMap.get(substring);
            if (pathMap == null) {
                pathMap = new PathMap(true);
                iPAddressMap.put(substring, (String) pathMap);
            }
            if (substring2 != null && !"".equals(substring2)) {
                pathMap.put(substring2, substring2);
            }
            if (z) {
                LOG.debug(toString() + " - deprecated specification syntax: " + str, new Object[0]);
            }
        }
    }

    protected void set(String[] strArr, IPAddressMap<PathMap> iPAddressMap) {
        iPAddressMap.clear();
        if (strArr != null && strArr.length > 0) {
            for (String str : strArr) {
                add(str, iPAddressMap);
            }
        }
    }

    protected boolean isAddrUriAllowed(String str, String str2) {
        Object lazyMatches;
        boolean z;
        if (this._white.size() > 0) {
            Object lazyMatches2 = this._white.getLazyMatches(str);
            if (lazyMatches2 != null) {
                z = false;
                for (Map.Entry entry : lazyMatches2 instanceof List ? (List) lazyMatches2 : Collections.singletonList(lazyMatches2)) {
                    PathMap pathMap = (PathMap) entry.getValue();
                    if (pathMap == null || (pathMap.size() != 0 && pathMap.match(str2) == null)) {
                        z = false;
                        continue;
                    } else {
                        z = true;
                        continue;
                    }
                    if (z) {
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        if (this._black.size() > 0 && (lazyMatches = this._black.getLazyMatches(str)) != null) {
            for (Map.Entry entry2 : lazyMatches instanceof List ? (List) lazyMatches : Collections.singletonList(lazyMatches)) {
                PathMap pathMap2 = (PathMap) entry2.getValue();
                if (pathMap2 != null && (pathMap2.size() == 0 || pathMap2.match(str2) != null)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        if (LOG.isDebugEnabled()) {
            System.err.println(dump());
        }
    }

    @Override // org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        sb.append(" WHITELIST:\n");
        dump(sb, this._white);
        sb.append(toString());
        sb.append(" BLACKLIST:\n");
        dump(sb, this._black);
        return sb.toString();
    }

    protected void dump(StringBuilder sb, IPAddressMap<PathMap> iPAddressMap) {
        for (String str : iPAddressMap.keySet()) {
            for (Object obj : iPAddressMap.get(str).values()) {
                sb.append("# ");
                sb.append(str);
                sb.append("|");
                sb.append(obj);
                sb.append("\n");
            }
        }
    }
}
