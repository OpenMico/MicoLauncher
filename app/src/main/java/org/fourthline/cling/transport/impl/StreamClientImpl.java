package org.fourthline.cling.transport.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.UpnpMessage;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.transport.spi.InitializationException;
import org.fourthline.cling.transport.spi.StreamClient;
import org.seamless.http.Headers;
import org.seamless.util.Exceptions;
import org.seamless.util.URIUtil;
import org.seamless.util.io.IO;

/* loaded from: classes5.dex */
public class StreamClientImpl implements StreamClient {
    static final String HACK_STREAM_HANDLER_SYSTEM_PROPERTY = "hackStreamHandlerProperty";
    private static final Logger log = Logger.getLogger(StreamClient.class.getName());
    protected final StreamClientConfigurationImpl configuration;

    @Override // org.fourthline.cling.transport.spi.StreamClient
    public void stop() {
    }

    public StreamClientImpl(StreamClientConfigurationImpl streamClientConfigurationImpl) throws InitializationException {
        this.configuration = streamClientConfigurationImpl;
        if (!ModelUtil.ANDROID_RUNTIME) {
            Logger logger = log;
            logger.fine("Using persistent HTTP stream client connections: " + streamClientConfigurationImpl.isUsePersistentConnections());
            System.setProperty("http.keepAlive", Boolean.toString(streamClientConfigurationImpl.isUsePersistentConnections()));
            if (System.getProperty(HACK_STREAM_HANDLER_SYSTEM_PROPERTY) == null) {
                log.fine("Setting custom static URLStreamHandlerFactory to work around bad JDK defaults");
                URL.setURLStreamHandlerFactory((URLStreamHandlerFactory) Class.forName("org.fourthline.cling.transport.impl.FixedSunURLStreamHandler").newInstance());
                System.setProperty(HACK_STREAM_HANDLER_SYSTEM_PROPERTY, "alreadyWorkedAroundTheEvilJDK");
                return;
            }
            return;
        }
        throw new InitializationException("This client does not work on Android. The design of HttpURLConnection is broken, we can not add additional 'permitted' HTTP methods. Read the Cling manual.");
    }

    @Override // org.fourthline.cling.transport.spi.StreamClient
    public StreamClientConfigurationImpl getConfiguration() {
        return this.configuration;
    }

    @Override // org.fourthline.cling.transport.spi.StreamClient
    public StreamResponseMessage sendRequest(StreamRequestMessage streamRequestMessage) {
        Throwable th;
        HttpURLConnection httpURLConnection;
        UpnpRequest operation;
        ProtocolException e;
        IOException e2;
        Exception e3;
        try {
            operation = streamRequestMessage.getOperation();
            log.fine("Preparing HTTP request message with method '" + operation.getHttpMethodName() + "': " + streamRequestMessage);
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            httpURLConnection = (HttpURLConnection) URIUtil.toURL(operation.getURI()).openConnection();
            try {
                httpURLConnection.setRequestMethod(operation.getHttpMethodName());
                httpURLConnection.setReadTimeout(this.configuration.getTimeoutSeconds() * 1000);
                httpURLConnection.setConnectTimeout(this.configuration.getTimeoutSeconds() * 1000);
                applyRequestProperties(httpURLConnection, streamRequestMessage);
                applyRequestBody(httpURLConnection, streamRequestMessage);
                log.fine("Sending HTTP request: " + streamRequestMessage);
                StreamResponseMessage createResponse = createResponse(httpURLConnection, httpURLConnection.getInputStream());
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return createResponse;
            } catch (ProtocolException e4) {
                e = e4;
                log.log(Level.WARNING, "HTTP request failed: " + streamRequestMessage, Exceptions.unwrap(e));
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            } catch (IOException e5) {
                e2 = e5;
                if (httpURLConnection == null) {
                    log.log(Level.WARNING, "HTTP request failed: " + streamRequestMessage, Exceptions.unwrap(e2));
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return null;
                } else if (e2 instanceof SocketTimeoutException) {
                    log.info("Timeout of " + getConfiguration().getTimeoutSeconds() + " seconds while waiting for HTTP request to complete, aborting: " + streamRequestMessage);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return null;
                } else {
                    if (log.isLoggable(Level.FINE)) {
                        log.fine("Exception occurred, trying to read the error stream: " + Exceptions.unwrap(e2));
                    }
                    try {
                        StreamResponseMessage createResponse2 = createResponse(httpURLConnection, httpURLConnection.getErrorStream());
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return createResponse2;
                    } catch (Exception e6) {
                        if (log.isLoggable(Level.FINE)) {
                            log.fine("Could not read error stream: " + e6);
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return null;
                    }
                }
            } catch (Exception e7) {
                e3 = e7;
                log.log(Level.WARNING, "HTTP request failed: " + streamRequestMessage, Exceptions.unwrap(e3));
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            }
        } catch (ProtocolException e8) {
            e = e8;
            httpURLConnection = null;
        } catch (IOException e9) {
            e2 = e9;
            httpURLConnection = null;
        } catch (Exception e10) {
            e3 = e10;
            httpURLConnection = null;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnection = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    protected void applyRequestProperties(HttpURLConnection httpURLConnection, StreamRequestMessage streamRequestMessage) {
        httpURLConnection.setInstanceFollowRedirects(false);
        if (!streamRequestMessage.getHeaders().containsKey(UpnpHeader.Type.USER_AGENT)) {
            httpURLConnection.setRequestProperty(UpnpHeader.Type.USER_AGENT.getHttpName(), getConfiguration().getUserAgentValue(streamRequestMessage.getUdaMajorVersion(), streamRequestMessage.getUdaMinorVersion()));
        }
        applyHeaders(httpURLConnection, streamRequestMessage.getHeaders());
    }

    protected void applyHeaders(HttpURLConnection httpURLConnection, Headers headers) {
        Logger logger = log;
        logger.fine("Writing headers on HttpURLConnection: " + headers.size());
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            for (String str : entry.getValue()) {
                String key = entry.getKey();
                Logger logger2 = log;
                logger2.fine("Setting header '" + key + "': " + str);
                httpURLConnection.setRequestProperty(key, str);
            }
        }
    }

    protected void applyRequestBody(HttpURLConnection httpURLConnection, StreamRequestMessage streamRequestMessage) throws IOException {
        if (streamRequestMessage.hasBody()) {
            httpURLConnection.setDoOutput(true);
            if (streamRequestMessage.getBodyType().equals(UpnpMessage.BodyType.STRING)) {
                IO.writeUTF8(httpURLConnection.getOutputStream(), streamRequestMessage.getBodyString());
            } else if (streamRequestMessage.getBodyType().equals(UpnpMessage.BodyType.BYTES)) {
                IO.writeBytes(httpURLConnection.getOutputStream(), streamRequestMessage.getBodyBytes());
            }
            httpURLConnection.getOutputStream().flush();
            return;
        }
        httpURLConnection.setDoOutput(false);
    }

    protected StreamResponseMessage createResponse(HttpURLConnection httpURLConnection, InputStream inputStream) throws Exception {
        byte[] bArr = null;
        if (httpURLConnection.getResponseCode() == -1) {
            Logger logger = log;
            logger.warning("Received an invalid HTTP response: " + httpURLConnection.getURL());
            log.warning("Is your Cling-based server sending connection heartbeats with RemoteClientInfo#isRequestCancelled? This client can't handle heartbeats, read the manual.");
            return null;
        }
        UpnpResponse upnpResponse = new UpnpResponse(httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
        Logger logger2 = log;
        logger2.fine("Received response: " + upnpResponse);
        StreamResponseMessage streamResponseMessage = new StreamResponseMessage(upnpResponse);
        streamResponseMessage.setHeaders(new UpnpHeaders(httpURLConnection.getHeaderFields()));
        if (inputStream != null) {
            try {
                bArr = IO.readBytes(inputStream);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        if (bArr != null && bArr.length > 0 && streamResponseMessage.isContentTypeMissingOrText()) {
            log.fine("Response contains textual entity body, converting then setting string on message");
            streamResponseMessage.setBodyCharacters(bArr);
        } else if (bArr == null || bArr.length <= 0) {
            log.fine("Response did not contain entity body");
        } else {
            log.fine("Response contains binary entity body, setting bytes on message");
            streamResponseMessage.setBody(UpnpMessage.BodyType.BYTES, bArr);
        }
        Logger logger3 = log;
        logger3.fine("Response message complete: " + streamResponseMessage);
        return streamResponseMessage;
    }
}
