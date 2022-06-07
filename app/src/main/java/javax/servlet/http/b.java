package javax.servlet.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import javax.servlet.ServletOutputStream;

/* compiled from: HttpServlet.java */
/* loaded from: classes5.dex */
class b extends HttpServletResponseWrapper {
    private static final ResourceBundle a = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");
    private a b = new a();
    private PrintWriter c;
    private boolean d;
    private boolean e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        if (!this.d) {
            PrintWriter printWriter = this.c;
            if (printWriter != null) {
                printWriter.flush();
            }
            setContentLength(this.b.a());
        }
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public void setContentLength(int i) {
        super.setContentLength(i);
        this.d = true;
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.c == null) {
            this.e = true;
            return this.b;
        }
        throw new IllegalStateException(a.getString("err.ise.getOutputStream"));
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public PrintWriter getWriter() throws UnsupportedEncodingException {
        if (!this.e) {
            if (this.c == null) {
                this.c = new PrintWriter(new OutputStreamWriter(this.b, getCharacterEncoding()));
            }
            return this.c;
        }
        throw new IllegalStateException(a.getString("err.ise.getWriter"));
    }
}
