package org.fourthline.cling.transport.spi;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.transport.spi.StreamClientConfiguration;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class AbstractStreamClient<C extends StreamClientConfiguration, REQUEST> implements StreamClient<C> {
    private static final Logger log = Logger.getLogger(StreamClient.class.getName());

    protected abstract void abort(REQUEST request);

    protected abstract Callable<StreamResponseMessage> createCallable(StreamRequestMessage streamRequestMessage, REQUEST request);

    protected abstract REQUEST createRequest(StreamRequestMessage streamRequestMessage);

    protected abstract boolean logExecutionException(Throwable th);

    protected void onFinally(REQUEST request) {
    }

    @Override // org.fourthline.cling.transport.spi.StreamClient
    public StreamResponseMessage sendRequest(StreamRequestMessage streamRequestMessage) throws InterruptedException {
        if (log.isLoggable(Level.FINE)) {
            Logger logger = log;
            logger.fine("Preparing HTTP request: " + streamRequestMessage);
        }
        REQUEST createRequest = createRequest(streamRequestMessage);
        if (createRequest == null) {
            return null;
        }
        try {
            Callable<StreamResponseMessage> createCallable = createCallable(streamRequestMessage, createRequest);
            long currentTimeMillis = System.currentTimeMillis();
            Future submit = getConfiguration().getRequestExecutorService().submit(createCallable);
            try {
                try {
                    try {
                        if (log.isLoggable(Level.FINE)) {
                            Logger logger2 = log;
                            logger2.fine("Waiting " + getConfiguration().getTimeoutSeconds() + " seconds for HTTP request to complete: " + streamRequestMessage);
                        }
                        StreamResponseMessage streamResponseMessage = (StreamResponseMessage) submit.get(getConfiguration().getTimeoutSeconds(), TimeUnit.SECONDS);
                        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                        if (log.isLoggable(Level.FINEST)) {
                            Logger logger3 = log;
                            logger3.finest("Got HTTP response in " + currentTimeMillis2 + "ms: " + streamRequestMessage);
                        }
                        if (getConfiguration().getLogWarningSeconds() > 0 && currentTimeMillis2 > getConfiguration().getLogWarningSeconds() * 1000) {
                            Logger logger4 = log;
                            logger4.warning("HTTP request took a long time (" + currentTimeMillis2 + "ms): " + streamRequestMessage);
                        }
                        onFinally(createRequest);
                        return streamResponseMessage;
                    } catch (ExecutionException e) {
                        Throwable cause = e.getCause();
                        if (!logExecutionException(cause)) {
                            Logger logger5 = log;
                            Level level = Level.WARNING;
                            logger5.log(level, "HTTP request failed: " + streamRequestMessage, Exceptions.unwrap(cause));
                        }
                        onFinally(createRequest);
                        return null;
                    }
                } catch (InterruptedException unused) {
                    if (log.isLoggable(Level.FINE)) {
                        Logger logger6 = log;
                        logger6.fine("Interruption, aborting request: " + streamRequestMessage);
                    }
                    abort(createRequest);
                    throw new InterruptedException("HTTP request interrupted and aborted");
                }
            } catch (TimeoutException unused2) {
                Logger logger7 = log;
                logger7.info("Timeout of " + getConfiguration().getTimeoutSeconds() + " seconds while waiting for HTTP request to complete, aborting: " + streamRequestMessage);
                abort(createRequest);
                onFinally(createRequest);
                return null;
            }
        } catch (Throwable th) {
            onFinally(createRequest);
            throw th;
        }
    }
}
