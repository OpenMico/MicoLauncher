package org.eclipse.jetty.server;

import java.io.IOException;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.component.LifeCycle;

/* loaded from: classes5.dex */
public interface Connector extends LifeCycle {
    void close() throws IOException;

    void customize(EndPoint endPoint, Request request) throws IOException;

    int getConfidentialPort();

    String getConfidentialScheme();

    Object getConnection();

    int getConnections();

    long getConnectionsDurationMax();

    double getConnectionsDurationMean();

    double getConnectionsDurationStdDev();

    long getConnectionsDurationTotal();

    int getConnectionsOpen();

    int getConnectionsOpenMax();

    int getConnectionsRequestsMax();

    double getConnectionsRequestsMean();

    double getConnectionsRequestsStdDev();

    String getHost();

    int getIntegralPort();

    String getIntegralScheme();

    int getLocalPort();

    int getLowResourceMaxIdleTime();

    int getMaxIdleTime();

    String getName();

    int getPort();

    int getRequestBufferSize();

    Buffers getRequestBuffers();

    int getRequestHeaderSize();

    int getRequests();

    boolean getResolveNames();

    int getResponseBufferSize();

    Buffers getResponseBuffers();

    int getResponseHeaderSize();

    Server getServer();

    boolean getStatsOn();

    long getStatsOnMs();

    boolean isConfidential(Request request);

    boolean isIntegral(Request request);

    boolean isLowResources();

    void open() throws IOException;

    void persist(EndPoint endPoint) throws IOException;

    void setHost(String str);

    void setLowResourceMaxIdleTime(int i);

    void setMaxIdleTime(int i);

    void setPort(int i);

    void setRequestBufferSize(int i);

    void setRequestHeaderSize(int i);

    void setResponseBufferSize(int i);

    void setResponseHeaderSize(int i);

    void setServer(Server server);

    void setStatsOn(boolean z);

    void statsReset();
}
