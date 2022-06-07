package io.netty.util.internal.logging;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;

/* loaded from: classes4.dex */
public class Slf4JLoggerFactory extends InternalLoggerFactory {
    static final /* synthetic */ boolean a = !Slf4JLoggerFactory.class.desiredAssertionStatus();
    public static final InternalLoggerFactory INSTANCE = new Slf4JLoggerFactory();

    @Deprecated
    public Slf4JLoggerFactory() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Slf4JLoggerFactory(boolean z) {
        if (a || z) {
            final StringBuffer stringBuffer = new StringBuffer();
            PrintStream printStream = System.err;
            try {
                printStream = new PrintStream(new OutputStream() { // from class: io.netty.util.internal.logging.Slf4JLoggerFactory.1
                    @Override // java.io.OutputStream
                    public void write(int i) {
                        stringBuffer.append((char) i);
                    }
                }, true, "US-ASCII");
                try {
                    if (!(LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory)) {
                        printStream.print(stringBuffer);
                        printStream.flush();
                        return;
                    }
                    throw new NoClassDefFoundError(stringBuffer.toString());
                } finally {
                    System.setErr(printStream);
                }
            } catch (UnsupportedEncodingException e) {
                throw new Error(e);
            }
        } else {
            throw new AssertionError();
        }
    }

    @Override // io.netty.util.internal.logging.InternalLoggerFactory
    public InternalLogger newInstance(String str) {
        return new g(LoggerFactory.getLogger(str));
    }
}
