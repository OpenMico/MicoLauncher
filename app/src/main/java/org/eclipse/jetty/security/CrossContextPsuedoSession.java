package org.eclipse.jetty.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* loaded from: classes5.dex */
public interface CrossContextPsuedoSession<T> {
    void clear(HttpServletRequest httpServletRequest);

    T fetch(HttpServletRequest httpServletRequest);

    void store(T t, HttpServletResponse httpServletResponse);
}
