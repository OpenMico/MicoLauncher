package io.netty.handler.codec;

import com.xiaomi.passport.StatConstants;
import io.netty.util.Signal;

/* loaded from: classes4.dex */
public class DecoderResult {
    private final Throwable a;
    protected static final Signal SIGNAL_UNFINISHED = Signal.valueOf(DecoderResult.class, "UNFINISHED");
    protected static final Signal SIGNAL_SUCCESS = Signal.valueOf(DecoderResult.class, "SUCCESS");
    public static final DecoderResult UNFINISHED = new DecoderResult(SIGNAL_UNFINISHED);
    public static final DecoderResult SUCCESS = new DecoderResult(SIGNAL_SUCCESS);

    public static DecoderResult failure(Throwable th) {
        if (th != null) {
            return new DecoderResult(th);
        }
        throw new NullPointerException("cause");
    }

    protected DecoderResult(Throwable th) {
        if (th != null) {
            this.a = th;
            return;
        }
        throw new NullPointerException("cause");
    }

    public boolean isFinished() {
        return this.a != SIGNAL_UNFINISHED;
    }

    public boolean isSuccess() {
        return this.a == SIGNAL_SUCCESS;
    }

    public boolean isFailure() {
        Throwable th = this.a;
        return (th == SIGNAL_SUCCESS || th == SIGNAL_UNFINISHED) ? false : true;
    }

    public Throwable cause() {
        if (isFailure()) {
            return this.a;
        }
        return null;
    }

    public String toString() {
        if (!isFinished()) {
            return "unfinished";
        }
        if (isSuccess()) {
            return StatConstants.BIND_SUCCESS;
        }
        String th = cause().toString();
        StringBuilder sb = new StringBuilder(th.length() + 17);
        sb.append("failure(");
        sb.append(th);
        sb.append(')');
        return sb.toString();
    }
}
