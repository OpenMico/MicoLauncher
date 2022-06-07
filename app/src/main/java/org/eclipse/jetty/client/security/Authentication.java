package org.eclipse.jetty.client.security;

import java.io.IOException;
import org.eclipse.jetty.client.HttpExchange;

/* loaded from: classes5.dex */
public interface Authentication {
    void setCredentials(HttpExchange httpExchange) throws IOException;
}
