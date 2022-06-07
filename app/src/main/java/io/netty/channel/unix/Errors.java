package io.netty.channel.unix;

import io.netty.util.internal.EmptyArrays;
import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.ClosedChannelException;

/* loaded from: classes4.dex */
public final class Errors {
    static final ClosedChannelException a;
    static final NativeIoException b;
    static final NativeIoException c;
    static final NativeIoException d;
    static final NativeIoException e;
    static final NativeIoException f;
    static final NativeIoException g;
    public static final int ERRNO_ENOTCONN_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoENOTCONN();
    public static final int ERRNO_EBADF_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEBADF();
    public static final int ERRNO_EPIPE_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEPIPE();
    public static final int ERRNO_ECONNRESET_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoECONNRESET();
    public static final int ERRNO_EAGAIN_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEAGAIN();
    public static final int ERRNO_EWOULDBLOCK_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEWOULDBLOCK();
    public static final int ERRNO_EINPROGRESS_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEINPROGRESS();
    private static final String[] h = new String[512];

    static {
        int i = 0;
        while (true) {
            String[] strArr = h;
            if (i < strArr.length) {
                strArr[i] = ErrorsStaticallyReferencedJniMethods.strError(i);
                i++;
            } else {
                e = newConnectionResetException("syscall:read(...)", ERRNO_ECONNRESET_NEGATIVE);
                c = newConnectionResetException("syscall:write(...)", ERRNO_EPIPE_NEGATIVE);
                d = newConnectionResetException("syscall:writev(...)", ERRNO_EPIPE_NEGATIVE);
                f = newConnectionResetException("syscall:sendto(...)", ERRNO_EPIPE_NEGATIVE);
                g = newConnectionResetException("syscall:sendmsg(...)", ERRNO_EPIPE_NEGATIVE);
                b = newConnectionResetException("syscall:shutdown(...)", ERRNO_ENOTCONN_NEGATIVE);
                a = new ClosedChannelException();
                a.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
                return;
            }
        }
    }

    /* loaded from: classes4.dex */
    public static final class NativeIoException extends IOException {
        private static final long serialVersionUID = 8222160204268655526L;
        private final int expectedErr;

        public NativeIoException(String str, int i) {
            super(str);
            this.expectedErr = i;
        }

        public int expectedErr() {
            return this.expectedErr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ConnectException a(String str, int i) {
        return new ConnectException(str + "() failed: " + h[-i]);
    }

    public static NativeIoException newConnectionResetException(String str, int i) {
        NativeIoException newIOException = newIOException(str, i);
        newIOException.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        return newIOException;
    }

    public static NativeIoException newIOException(String str, int i) {
        return new NativeIoException(str + "() failed: " + h[-i], i);
    }

    public static int ioResult(String str, int i, NativeIoException nativeIoException) throws IOException {
        if (i == ERRNO_EAGAIN_NEGATIVE || i == ERRNO_EWOULDBLOCK_NEGATIVE) {
            return 0;
        }
        if (i == nativeIoException.expectedErr()) {
            throw nativeIoException;
        } else if (i == ERRNO_EBADF_NEGATIVE || i == ERRNO_ENOTCONN_NEGATIVE) {
            throw a;
        } else {
            throw newIOException(str, i);
        }
    }

    private Errors() {
    }
}
