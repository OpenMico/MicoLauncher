package org.fourthline.cling.transport.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.fourthline.cling.model.message.Connection;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.UpnpMessage;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.transport.spi.UpnpStream;
import org.seamless.util.io.IO;

/* loaded from: classes5.dex */
public abstract class AsyncServletUpnpStream extends UpnpStream implements AsyncListener {
    private static final Logger log = Logger.getLogger(UpnpStream.class.getName());
    protected final AsyncContext asyncContext;
    protected final HttpServletRequest request;
    protected StreamResponseMessage responseMessage;

    protected abstract Connection createConnection();

    @Override // javax.servlet.AsyncListener
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
    }

    public AsyncServletUpnpStream(ProtocolFactory protocolFactory, AsyncContext asyncContext, HttpServletRequest httpServletRequest) {
        super(protocolFactory);
        this.asyncContext = asyncContext;
        this.request = httpServletRequest;
        asyncContext.addListener(this);
    }

    protected HttpServletRequest getRequest() {
        return this.request;
    }

    protected HttpServletResponse getResponse() {
        ServletResponse response = this.asyncContext.getResponse();
        if (response != null) {
            return (HttpServletResponse) response;
        }
        throw new IllegalStateException("Couldn't get response from asynchronous context, already timed out");
    }

    protected void complete() {
        try {
            this.asyncContext.complete();
        } catch (IllegalStateException e) {
            Logger logger = log;
            logger.info("Error calling servlet container's AsyncContext#complete() method: " + e);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            StreamRequestMessage readRequestMessage = readRequestMessage();
            if (log.isLoggable(Level.FINER)) {
                Logger logger = log;
                logger.finer("Processing new request message: " + readRequestMessage);
            }
            this.responseMessage = process(readRequestMessage);
            if (this.responseMessage != null) {
                if (log.isLoggable(Level.FINER)) {
                    Logger logger2 = log;
                    logger2.finer("Preparing HTTP response message: " + this.responseMessage);
                }
                writeResponseMessage(this.responseMessage);
            } else {
                if (log.isLoggable(Level.FINER)) {
                    log.finer("Sending HTTP response status: 404");
                }
                getResponse().setStatus(404);
            }
        } finally {
            complete();
        }
    }

    @Override // javax.servlet.AsyncListener
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        if (log.isLoggable(Level.FINER)) {
            Logger logger = log;
            logger.finer("Completed asynchronous processing of HTTP request: " + asyncEvent.getSuppliedRequest());
        }
        responseSent(this.responseMessage);
    }

    @Override // javax.servlet.AsyncListener
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        if (log.isLoggable(Level.FINER)) {
            Logger logger = log;
            logger.finer("Asynchronous processing of HTTP request timed out: " + asyncEvent.getSuppliedRequest());
        }
        responseException(new Exception("Asynchronous request timed out"));
    }

    @Override // javax.servlet.AsyncListener
    public void onError(AsyncEvent asyncEvent) throws IOException {
        if (log.isLoggable(Level.FINER)) {
            Logger logger = log;
            logger.finer("Asynchronous processing of HTTP request error: " + asyncEvent.getThrowable());
        }
        responseException(asyncEvent.getThrowable());
    }

    protected StreamRequestMessage readRequestMessage() throws IOException {
        String method = getRequest().getMethod();
        String requestURI = getRequest().getRequestURI();
        if (log.isLoggable(Level.FINER)) {
            Logger logger = log;
            logger.finer("Processing HTTP request: " + method + StringUtils.SPACE + requestURI);
        }
        try {
            StreamRequestMessage streamRequestMessage = new StreamRequestMessage(UpnpRequest.Method.getByHttpName(method), URI.create(requestURI));
            if (!((UpnpRequest) streamRequestMessage.getOperation()).getMethod().equals(UpnpRequest.Method.UNKNOWN)) {
                streamRequestMessage.setConnection(createConnection());
                UpnpHeaders upnpHeaders = new UpnpHeaders();
                Enumeration<String> headerNames = getRequest().getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String nextElement = headerNames.nextElement();
                    Enumeration<String> headers = getRequest().getHeaders(nextElement);
                    while (headers.hasMoreElements()) {
                        upnpHeaders.add(nextElement, headers.nextElement());
                    }
                }
                streamRequestMessage.setHeaders(upnpHeaders);
                ServletInputStream servletInputStream = null;
                try {
                    servletInputStream = getRequest().getInputStream();
                    byte[] readBytes = IO.readBytes(servletInputStream);
                    if (log.isLoggable(Level.FINER)) {
                        Logger logger2 = log;
                        logger2.finer("Reading request body bytes: " + readBytes.length);
                    }
                    if (readBytes.length > 0 && streamRequestMessage.isContentTypeMissingOrText()) {
                        if (log.isLoggable(Level.FINER)) {
                            log.finer("Request contains textual entity body, converting then setting string on message");
                        }
                        streamRequestMessage.setBodyCharacters(readBytes);
                    } else if (readBytes.length > 0) {
                        if (log.isLoggable(Level.FINER)) {
                            log.finer("Request contains binary entity body, setting bytes on message");
                        }
                        streamRequestMessage.setBody(UpnpMessage.BodyType.BYTES, readBytes);
                    } else if (log.isLoggable(Level.FINER)) {
                        log.finer("Request did not contain entity body");
                    }
                    return streamRequestMessage;
                } finally {
                    if (servletInputStream != null) {
                        servletInputStream.close();
                    }
                }
            } else {
                throw new RuntimeException("Method not supported: " + method);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid request URI: " + requestURI, e);
        }
    }

    protected void writeResponseMessage(StreamResponseMessage streamResponseMessage) throws IOException {
        if (log.isLoggable(Level.FINER)) {
            Logger logger = log;
            logger.finer("Sending HTTP response status: " + streamResponseMessage.getOperation().getStatusCode());
        }
        getResponse().setStatus(streamResponseMessage.getOperation().getStatusCode());
        for (Map.Entry<String, List<String>> entry : streamResponseMessage.getHeaders().entrySet()) {
            for (String str : entry.getValue()) {
                getResponse().addHeader(entry.getKey(), str);
            }
        }
        getResponse().setDateHeader("Date", System.currentTimeMillis());
        byte[] bodyBytes = streamResponseMessage.hasBody() ? streamResponseMessage.getBodyBytes() : null;
        int length = bodyBytes != null ? bodyBytes.length : -1;
        if (length > 0) {
            getResponse().setContentLength(length);
            log.finer("Response message has body, writing bytes to stream...");
            IO.writeBytes(getResponse().getOutputStream(), bodyBytes);
        }
    }
}
