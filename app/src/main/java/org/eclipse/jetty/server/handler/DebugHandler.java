package org.eclipse.jetty.server.handler;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.DateCache;
import org.eclipse.jetty.util.RolloverFileOutputStream;

/* loaded from: classes5.dex */
public class DebugHandler extends HandlerWrapper {
    private DateCache _date = new DateCache("HH:mm:ss", Locale.US);
    private OutputStream _out;
    private PrintStream _print;

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        boolean z;
        String str2;
        Response response = request.getResponse();
        Thread currentThread = Thread.currentThread();
        String name = currentThread.getName();
        String str3 = (String) httpServletRequest.getAttribute("org.eclipse.jetty.thread.name");
        if (str3 == null) {
            str3 = name + Constants.COLON_SEPARATOR + request.getScheme() + "://" + request.getLocalAddr() + Constants.COLON_SEPARATOR + request.getLocalPort() + request.getUri();
            z = false;
        } else {
            z = true;
        }
        try {
            try {
                try {
                    try {
                        try {
                            String now = this._date.now();
                            int lastMs = this._date.lastMs();
                            if (z) {
                                PrintStream printStream = this._print;
                                StringBuilder sb = new StringBuilder();
                                sb.append(now);
                                sb.append(lastMs > 99 ? "." : lastMs > 9 ? ".0" : ".00");
                                sb.append(lastMs);
                                sb.append(Constants.COLON_SEPARATOR);
                                sb.append(str3);
                                sb.append(" RETRY");
                                printStream.println(sb.toString());
                            } else {
                                PrintStream printStream2 = this._print;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(now);
                                sb2.append(lastMs > 99 ? "." : lastMs > 9 ? ".0" : ".00");
                                sb2.append(lastMs);
                                sb2.append(Constants.COLON_SEPARATOR);
                                sb2.append(str3);
                                sb2.append(StringUtils.SPACE);
                                sb2.append(request.getRemoteAddr());
                                sb2.append(StringUtils.SPACE);
                                sb2.append(httpServletRequest.getMethod());
                                sb2.append(StringUtils.SPACE);
                                sb2.append(request.getHeader("Cookie"));
                                sb2.append("; ");
                                sb2.append(request.getHeader("User-Agent"));
                                printStream2.println(sb2.toString());
                            }
                            currentThread.setName(str3);
                            getHandler().handle(str, request, httpServletRequest, httpServletResponse);
                            currentThread.setName(name);
                            String now2 = this._date.now();
                            int lastMs2 = this._date.lastMs();
                            if (request.getAsyncContinuation().isSuspended()) {
                                httpServletRequest.setAttribute("org.eclipse.jetty.thread.name", str3);
                                PrintStream printStream3 = this._print;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(now2);
                                sb3.append(lastMs2 > 99 ? "." : lastMs2 > 9 ? ".0" : ".00");
                                sb3.append(lastMs2);
                                sb3.append(Constants.COLON_SEPARATOR);
                                sb3.append(str3);
                                sb3.append(" SUSPEND");
                                printStream3.println(sb3.toString());
                                return;
                            }
                            PrintStream printStream4 = this._print;
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(now2);
                            sb4.append(lastMs2 > 99 ? "." : lastMs2 > 9 ? ".0" : ".00");
                            sb4.append(lastMs2);
                            sb4.append(Constants.COLON_SEPARATOR);
                            sb4.append(str3);
                            sb4.append(StringUtils.SPACE);
                            sb4.append(response.getStatus());
                            sb4.append("");
                            sb4.append(StringUtils.SPACE);
                            sb4.append(response.getContentType());
                            sb4.append(StringUtils.SPACE);
                            sb4.append(response.getContentCount());
                            printStream4.println(sb4.toString());
                        } catch (ServletException e) {
                            String str4 = e.toString() + Constants.COLON_SEPARATOR + e.getCause();
                            throw e;
                        }
                    } catch (IOException e2) {
                        e2.toString();
                        throw e2;
                    }
                } catch (RuntimeException e3) {
                    e3.toString();
                    throw e3;
                }
            } catch (Error e4) {
                e4.toString();
                throw e4;
            }
        } catch (Throwable th) {
            currentThread.setName(name);
            String now3 = this._date.now();
            int lastMs3 = this._date.lastMs();
            if (request.getAsyncContinuation().isSuspended()) {
                httpServletRequest.setAttribute("org.eclipse.jetty.thread.name", str3);
                PrintStream printStream5 = this._print;
                StringBuilder sb5 = new StringBuilder();
                sb5.append(now3);
                sb5.append(lastMs3 <= 99 ? lastMs3 > 9 ? ".0" : ".00" : ".");
                sb5.append(lastMs3);
                sb5.append(Constants.COLON_SEPARATOR);
                sb5.append(str3);
                sb5.append(" SUSPEND");
                printStream5.println(sb5.toString());
            } else {
                PrintStream printStream6 = this._print;
                StringBuilder sb6 = new StringBuilder();
                sb6.append(now3);
                sb6.append(lastMs3 > 99 ? "." : lastMs3 > 9 ? ".0" : ".00");
                sb6.append(lastMs3);
                sb6.append(Constants.COLON_SEPARATOR);
                sb6.append(str3);
                sb6.append(StringUtils.SPACE);
                sb6.append(response.getStatus());
                if (0 == 0) {
                    str2 = "";
                } else {
                    str2 = "/" + ((String) null);
                }
                sb6.append(str2);
                sb6.append(StringUtils.SPACE);
                sb6.append(response.getContentType());
                sb6.append(StringUtils.SPACE);
                sb6.append(response.getContentCount());
                printStream6.println(sb6.toString());
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        if (this._out == null) {
            this._out = new RolloverFileOutputStream("./logs/yyyy_mm_dd.debug.log", true);
        }
        this._print = new PrintStream(this._out);
        super.doStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        this._print.close();
    }

    public OutputStream getOutputStream() {
        return this._out;
    }

    public void setOutputStream(OutputStream outputStream) {
        this._out = outputStream;
    }
}
