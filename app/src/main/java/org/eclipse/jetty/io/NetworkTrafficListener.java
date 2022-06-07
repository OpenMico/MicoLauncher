package org.eclipse.jetty.io;

import java.net.Socket;

/* loaded from: classes5.dex */
public interface NetworkTrafficListener {

    /* loaded from: classes5.dex */
    public static class Empty implements NetworkTrafficListener {
        @Override // org.eclipse.jetty.io.NetworkTrafficListener
        public void closed(Socket socket) {
        }

        @Override // org.eclipse.jetty.io.NetworkTrafficListener
        public void incoming(Socket socket, Buffer buffer) {
        }

        @Override // org.eclipse.jetty.io.NetworkTrafficListener
        public void opened(Socket socket) {
        }

        @Override // org.eclipse.jetty.io.NetworkTrafficListener
        public void outgoing(Socket socket, Buffer buffer) {
        }
    }

    void closed(Socket socket);

    void incoming(Socket socket, Buffer buffer);

    void opened(Socket socket);

    void outgoing(Socket socket, Buffer buffer);
}
