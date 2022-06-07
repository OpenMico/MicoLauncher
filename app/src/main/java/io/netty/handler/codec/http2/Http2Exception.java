package io.netty.handler.codec.http2;

import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class Http2Exception extends Exception {
    private static final long serialVersionUID = -6941186345430164209L;
    private final Http2Error error;
    private final ShutdownHint shutdownHint;

    /* loaded from: classes4.dex */
    public enum ShutdownHint {
        NO_SHUTDOWN,
        GRACEFUL_SHUTDOWN,
        HARD_SHUTDOWN
    }

    public Http2Exception(Http2Error http2Error) {
        this(http2Error, ShutdownHint.HARD_SHUTDOWN);
    }

    public Http2Exception(Http2Error http2Error, ShutdownHint shutdownHint) {
        this.error = (Http2Error) ObjectUtil.checkNotNull(http2Error, "error");
        this.shutdownHint = (ShutdownHint) ObjectUtil.checkNotNull(shutdownHint, "shutdownHint");
    }

    public Http2Exception(Http2Error http2Error, String str) {
        this(http2Error, str, ShutdownHint.HARD_SHUTDOWN);
    }

    public Http2Exception(Http2Error http2Error, String str, ShutdownHint shutdownHint) {
        super(str);
        this.error = (Http2Error) ObjectUtil.checkNotNull(http2Error, "error");
        this.shutdownHint = (ShutdownHint) ObjectUtil.checkNotNull(shutdownHint, "shutdownHint");
    }

    public Http2Exception(Http2Error http2Error, String str, Throwable th) {
        this(http2Error, str, th, ShutdownHint.HARD_SHUTDOWN);
    }

    public Http2Exception(Http2Error http2Error, String str, Throwable th, ShutdownHint shutdownHint) {
        super(str, th);
        this.error = (Http2Error) ObjectUtil.checkNotNull(http2Error, "error");
        this.shutdownHint = (ShutdownHint) ObjectUtil.checkNotNull(shutdownHint, "shutdownHint");
    }

    public Http2Error error() {
        return this.error;
    }

    public ShutdownHint shutdownHint() {
        return this.shutdownHint;
    }

    public static Http2Exception connectionError(Http2Error http2Error, String str, Object... objArr) {
        return new Http2Exception(http2Error, String.format(str, objArr));
    }

    public static Http2Exception connectionError(Http2Error http2Error, Throwable th, String str, Object... objArr) {
        return new Http2Exception(http2Error, String.format(str, objArr), th);
    }

    public static Http2Exception closedStreamError(Http2Error http2Error, String str, Object... objArr) {
        return new ClosedStreamCreationException(http2Error, String.format(str, objArr));
    }

    public static Http2Exception streamError(int i, Http2Error http2Error, String str, Object... objArr) {
        return i == 0 ? connectionError(http2Error, str, objArr) : new StreamException(i, http2Error, String.format(str, objArr));
    }

    public static Http2Exception streamError(int i, Http2Error http2Error, Throwable th, String str, Object... objArr) {
        return i == 0 ? connectionError(http2Error, th, str, objArr) : new StreamException(i, http2Error, String.format(str, objArr), th);
    }

    public static boolean isStreamError(Http2Exception http2Exception) {
        return http2Exception instanceof StreamException;
    }

    public static int streamId(Http2Exception http2Exception) {
        if (isStreamError(http2Exception)) {
            return ((StreamException) http2Exception).streamId();
        }
        return 0;
    }

    /* loaded from: classes4.dex */
    public static final class ClosedStreamCreationException extends Http2Exception {
        private static final long serialVersionUID = -6746542974372246206L;

        public ClosedStreamCreationException(Http2Error http2Error) {
            super(http2Error);
        }

        public ClosedStreamCreationException(Http2Error http2Error, String str) {
            super(http2Error, str);
        }

        public ClosedStreamCreationException(Http2Error http2Error, String str, Throwable th) {
            super(http2Error, str, th);
        }
    }

    /* loaded from: classes4.dex */
    public static final class StreamException extends Http2Exception {
        private static final long serialVersionUID = 602472544416984384L;
        private final int streamId;

        StreamException(int i, Http2Error http2Error, String str) {
            super(http2Error, str, ShutdownHint.NO_SHUTDOWN);
            this.streamId = i;
        }

        StreamException(int i, Http2Error http2Error, String str, Throwable th) {
            super(http2Error, str, th, ShutdownHint.NO_SHUTDOWN);
            this.streamId = i;
        }

        public int streamId() {
            return this.streamId;
        }
    }

    /* loaded from: classes4.dex */
    public static final class CompositeStreamException extends Http2Exception implements Iterable<StreamException> {
        private static final long serialVersionUID = 7091134858213711015L;
        private final List<StreamException> exceptions;

        public CompositeStreamException(Http2Error http2Error, int i) {
            super(http2Error, ShutdownHint.NO_SHUTDOWN);
            this.exceptions = new ArrayList(i);
        }

        public void add(StreamException streamException) {
            this.exceptions.add(streamException);
        }

        @Override // java.lang.Iterable
        public Iterator<StreamException> iterator() {
            return this.exceptions.iterator();
        }
    }
}
