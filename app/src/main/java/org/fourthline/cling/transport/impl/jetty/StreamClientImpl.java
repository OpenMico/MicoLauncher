package org.fourthline.cling.transport.impl.jetty;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.UpnpMessage;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.ContentTypeHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.transport.spi.AbstractStreamClient;
import org.fourthline.cling.transport.spi.InitializationException;
import org.fourthline.cling.transport.spi.StreamClient;
import org.seamless.util.Exceptions;
import org.seamless.util.MimeType;

/* loaded from: classes5.dex */
public class StreamClientImpl extends AbstractStreamClient<StreamClientConfigurationImpl, HttpContentExchange> {
    private static final Logger log = Logger.getLogger(StreamClient.class.getName());
    protected final HttpClient client = new HttpClient();
    protected final StreamClientConfigurationImpl configuration;

    @Override // org.fourthline.cling.transport.spi.AbstractStreamClient
    protected boolean logExecutionException(Throwable th) {
        return false;
    }

    public StreamClientImpl(StreamClientConfigurationImpl streamClientConfigurationImpl) throws InitializationException {
        this.configuration = streamClientConfigurationImpl;
        log.info("Starting Jetty HttpClient...");
        this.client.setThreadPool(new ExecutorThreadPool(getConfiguration().getRequestExecutorService()) { // from class: org.fourthline.cling.transport.impl.jetty.StreamClientImpl.1
            @Override // org.eclipse.jetty.util.thread.ExecutorThreadPool, org.eclipse.jetty.util.component.AbstractLifeCycle
            protected void doStop() throws Exception {
            }
        });
        this.client.setTimeout((streamClientConfigurationImpl.getTimeoutSeconds() + 5) * 1000);
        this.client.setConnectTimeout((streamClientConfigurationImpl.getTimeoutSeconds() + 5) * 1000);
        this.client.setMaxRetries(streamClientConfigurationImpl.getRequestRetryCount());
        try {
            this.client.start();
        } catch (Exception e) {
            throw new InitializationException("Could not start Jetty HTTP client: " + e, e);
        }
    }

    @Override // org.fourthline.cling.transport.spi.StreamClient
    public StreamClientConfigurationImpl getConfiguration() {
        return this.configuration;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.fourthline.cling.transport.spi.AbstractStreamClient
    public HttpContentExchange createRequest(StreamRequestMessage streamRequestMessage) {
        return new HttpContentExchange(getConfiguration(), this.client, streamRequestMessage);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Callable<StreamResponseMessage> createCallable(final StreamRequestMessage streamRequestMessage, final HttpContentExchange httpContentExchange) {
        return new Callable<StreamResponseMessage>() { // from class: org.fourthline.cling.transport.impl.jetty.StreamClientImpl.2
            @Override // java.util.concurrent.Callable
            public StreamResponseMessage call() throws Exception {
                if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                    Logger logger = StreamClientImpl.log;
                    logger.fine("Sending HTTP request: " + streamRequestMessage);
                }
                StreamClientImpl.this.client.send(httpContentExchange);
                int waitForDone = httpContentExchange.waitForDone();
                if (waitForDone == 7) {
                    try {
                        return httpContentExchange.createResponse();
                    } catch (Throwable th) {
                        Logger logger2 = StreamClientImpl.log;
                        Level level = Level.WARNING;
                        logger2.log(level, "Error reading response: " + streamRequestMessage, Exceptions.unwrap(th));
                        return null;
                    }
                } else if (waitForDone == 11 || waitForDone == 9) {
                    return null;
                } else {
                    Logger logger3 = StreamClientImpl.log;
                    logger3.warning("Unhandled HTTP exchange status: " + waitForDone);
                    return null;
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void abort(HttpContentExchange httpContentExchange) {
        httpContentExchange.cancel();
    }

    @Override // org.fourthline.cling.transport.spi.StreamClient
    public void stop() {
        try {
            this.client.stop();
        } catch (Exception e) {
            Logger logger = log;
            logger.info("Error stopping HTTP client: " + e);
        }
    }

    /* loaded from: classes5.dex */
    public static class HttpContentExchange extends ContentExchange {
        protected final HttpClient client;
        protected final StreamClientConfigurationImpl configuration;
        protected Throwable exception;
        protected final StreamRequestMessage requestMessage;

        public HttpContentExchange(StreamClientConfigurationImpl streamClientConfigurationImpl, HttpClient httpClient, StreamRequestMessage streamRequestMessage) {
            super(true);
            this.configuration = streamClientConfigurationImpl;
            this.client = httpClient;
            this.requestMessage = streamRequestMessage;
            applyRequestURLMethod();
            applyRequestHeaders();
            applyRequestBody();
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        protected void onConnectionFailed(Throwable th) {
            Logger logger = StreamClientImpl.log;
            Level level = Level.WARNING;
            logger.log(level, "HTTP connection failed: " + this.requestMessage, Exceptions.unwrap(th));
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        protected void onException(Throwable th) {
            Logger logger = StreamClientImpl.log;
            Level level = Level.WARNING;
            logger.log(level, "HTTP request failed: " + this.requestMessage, Exceptions.unwrap(th));
        }

        public StreamClientConfigurationImpl getConfiguration() {
            return this.configuration;
        }

        public StreamRequestMessage getRequestMessage() {
            return this.requestMessage;
        }

        protected void applyRequestURLMethod() {
            UpnpRequest operation = getRequestMessage().getOperation();
            if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                Logger logger = StreamClientImpl.log;
                logger.fine("Preparing HTTP request message with method '" + operation.getHttpMethodName() + "': " + getRequestMessage());
            }
            setURL(operation.getURI().toString());
            setMethod(operation.getHttpMethodName());
        }

        protected void applyRequestHeaders() {
            UpnpHeaders headers = getRequestMessage().getHeaders();
            if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                Logger logger = StreamClientImpl.log;
                logger.fine("Writing headers on HttpContentExchange: " + headers.size());
            }
            if (!headers.containsKey(UpnpHeader.Type.USER_AGENT)) {
                setRequestHeader(UpnpHeader.Type.USER_AGENT.getHttpName(), getConfiguration().getUserAgentValue(getRequestMessage().getUdaMajorVersion(), getRequestMessage().getUdaMinorVersion()));
            }
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                for (String str : entry.getValue()) {
                    String key = entry.getKey();
                    if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                        Logger logger2 = StreamClientImpl.log;
                        logger2.fine("Setting header '" + key + "': " + str);
                    }
                    addRequestHeader(key, str);
                }
            }
        }

        protected void applyRequestBody() {
            if (!getRequestMessage().hasBody()) {
                return;
            }
            if (getRequestMessage().getBodyType() == UpnpMessage.BodyType.STRING) {
                if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                    Logger logger = StreamClientImpl.log;
                    logger.fine("Writing textual request body: " + getRequestMessage());
                }
                MimeType value = getRequestMessage().getContentTypeHeader() != null ? getRequestMessage().getContentTypeHeader().getValue() : ContentTypeHeader.DEFAULT_CONTENT_TYPE_UTF8;
                String contentTypeCharset = getRequestMessage().getContentTypeCharset() != null ? getRequestMessage().getContentTypeCharset() : "UTF-8";
                setRequestContentType(value.toString());
                try {
                    ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(getRequestMessage().getBodyString(), contentTypeCharset);
                    setRequestHeader("Content-Length", String.valueOf(byteArrayBuffer.length()));
                    setRequestContent(byteArrayBuffer);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Unsupported character encoding: " + contentTypeCharset, e);
                }
            } else {
                if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                    Logger logger2 = StreamClientImpl.log;
                    logger2.fine("Writing binary request body: " + getRequestMessage());
                }
                if (getRequestMessage().getContentTypeHeader() != null) {
                    setRequestContentType(getRequestMessage().getContentTypeHeader().getValue().toString());
                    ByteArrayBuffer byteArrayBuffer2 = new ByteArrayBuffer(getRequestMessage().getBodyBytes());
                    setRequestHeader("Content-Length", String.valueOf(byteArrayBuffer2.length()));
                    setRequestContent(byteArrayBuffer2);
                    return;
                }
                throw new RuntimeException("Missing content type header in request message: " + this.requestMessage);
            }
        }

        protected StreamResponseMessage createResponse() {
            UpnpResponse upnpResponse = new UpnpResponse(getResponseStatus(), UpnpResponse.Status.getByStatusCode(getResponseStatus()).getStatusMsg());
            if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                Logger logger = StreamClientImpl.log;
                logger.fine("Received response: " + upnpResponse);
            }
            StreamResponseMessage streamResponseMessage = new StreamResponseMessage(upnpResponse);
            UpnpHeaders upnpHeaders = new UpnpHeaders();
            HttpFields responseFields = getResponseFields();
            for (String str : responseFields.getFieldNamesCollection()) {
                for (String str2 : responseFields.getValuesCollection(str)) {
                    upnpHeaders.add(str, str2);
                }
            }
            streamResponseMessage.setHeaders(upnpHeaders);
            byte[] responseContentBytes = getResponseContentBytes();
            if (responseContentBytes != null && responseContentBytes.length > 0 && streamResponseMessage.isContentTypeMissingOrText()) {
                if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                    StreamClientImpl.log.fine("Response contains textual entity body, converting then setting string on message");
                }
                try {
                    streamResponseMessage.setBodyCharacters(responseContentBytes);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Unsupported character encoding: " + e, e);
                }
            } else if (responseContentBytes != null && responseContentBytes.length > 0) {
                if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                    StreamClientImpl.log.fine("Response contains binary entity body, setting bytes on message");
                }
                streamResponseMessage.setBody(UpnpMessage.BodyType.BYTES, responseContentBytes);
            } else if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                StreamClientImpl.log.fine("Response did not contain entity body");
            }
            if (StreamClientImpl.log.isLoggable(Level.FINE)) {
                Logger logger2 = StreamClientImpl.log;
                logger2.fine("Response message complete: " + streamResponseMessage);
            }
            return streamResponseMessage;
        }
    }
}
