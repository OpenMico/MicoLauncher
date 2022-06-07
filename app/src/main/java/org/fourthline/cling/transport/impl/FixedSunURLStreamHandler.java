package org.fourthline.cling.transport.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.logging.Logger;
import org.eclipse.jetty.http.HttpMethods;
import sun.net.www.protocol.http.Handler;
import sun.net.www.protocol.http.HttpURLConnection;

/* loaded from: classes5.dex */
public class FixedSunURLStreamHandler implements URLStreamHandlerFactory {
    private static final Logger log = Logger.getLogger(FixedSunURLStreamHandler.class.getName());

    @Override // java.net.URLStreamHandlerFactory
    public URLStreamHandler createURLStreamHandler(String str) {
        Logger logger = log;
        logger.fine("Creating new URLStreamHandler for protocol: " + str);
        if ("http".equals(str)) {
            return new Handler() { // from class: org.fourthline.cling.transport.impl.FixedSunURLStreamHandler.1
                protected URLConnection openConnection(URL url) throws IOException {
                    return openConnection(url, null);
                }

                /* JADX WARN: Type inference failed for: r2v1, types: [org.fourthline.cling.transport.impl.FixedSunURLStreamHandler$UpnpURLConnection, java.net.URLConnection] */
                protected URLConnection openConnection(URL url, Proxy proxy) throws IOException {
                    return new UpnpURLConnection(url, this);
                }
            };
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class UpnpURLConnection extends HttpURLConnection {
        private static final String[] methods = {"GET", "POST", "HEAD", HttpMethods.OPTIONS, "PUT", "DELETE", "SUBSCRIBE", "UNSUBSCRIBE", "NOTIFY"};

        protected UpnpURLConnection(URL url, Handler handler) throws IOException {
            super(url, handler);
        }

        public UpnpURLConnection(URL url, String str, int i) throws IOException {
            super(url, str, i);
        }

        public synchronized OutputStream getOutputStream() throws IOException {
            OutputStream outputStream;
            String str = this.method;
            if (!this.method.equals("PUT") && !this.method.equals("POST") && !this.method.equals("NOTIFY")) {
                this.method = "GET";
                outputStream = FixedSunURLStreamHandler.super.getOutputStream();
                this.method = str;
            }
            this.method = "PUT";
            outputStream = FixedSunURLStreamHandler.super.getOutputStream();
            this.method = str;
            return outputStream;
        }

        public void setRequestMethod(String str) throws ProtocolException {
            if (!this.connected) {
                for (String str2 : methods) {
                    if (str2.equals(str)) {
                        this.method = str;
                        return;
                    }
                }
                throw new ProtocolException("Invalid UPnP HTTP method: " + str);
            }
            throw new ProtocolException("Cannot reset method once connected");
        }
    }
}
