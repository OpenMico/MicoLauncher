package javax.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/* loaded from: classes5.dex */
public interface ServletResponse {
    void flushBuffer() throws IOException;

    int getBufferSize();

    String getCharacterEncoding();

    String getContentType();

    Locale getLocale();

    ServletOutputStream getOutputStream() throws IOException;

    PrintWriter getWriter() throws IOException;

    boolean isCommitted();

    void reset();

    void resetBuffer();

    void setBufferSize(int i);

    void setCharacterEncoding(String str);

    void setContentLength(int i);

    void setContentType(String str);

    void setLocale(Locale locale);
}
