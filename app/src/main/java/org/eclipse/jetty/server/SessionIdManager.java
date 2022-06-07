package org.eclipse.jetty.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.util.component.LifeCycle;

/* loaded from: classes5.dex */
public interface SessionIdManager extends LifeCycle {
    void addSession(HttpSession httpSession);

    String getClusterId(String str);

    String getNodeId(String str, HttpServletRequest httpServletRequest);

    String getWorkerName();

    boolean idInUse(String str);

    void invalidateAll(String str);

    String newSessionId(HttpServletRequest httpServletRequest, long j);

    void removeSession(HttpSession httpSession);
}
