package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.eclipse.jetty.http.AbstractGenerator;
import org.eclipse.jetty.util.ByteArrayOutputStream2;

/* loaded from: classes5.dex */
public class HttpWriter extends Writer {
    public static final int MAX_OUTPUT_CHARS = 512;
    private static final int WRITE_CONV = 0;
    private static final int WRITE_ISO1 = 1;
    private static final int WRITE_UTF8 = 2;
    final AbstractGenerator _generator;
    final HttpOutput _out;
    int _surrogate = 0;
    int _writeMode;

    public HttpWriter(HttpOutput httpOutput) {
        this._out = httpOutput;
        this._generator = this._out._generator;
    }

    public void setCharacterEncoding(String str) {
        if (str == null || "ISO-8859-1".equalsIgnoreCase(str)) {
            this._writeMode = 1;
        } else if ("UTF-8".equalsIgnoreCase(str)) {
            this._writeMode = 2;
        } else {
            this._writeMode = 0;
            if (this._out._characterEncoding == null || !this._out._characterEncoding.equalsIgnoreCase(str)) {
                this._out._converter = null;
            }
        }
        HttpOutput httpOutput = this._out;
        httpOutput._characterEncoding = str;
        if (httpOutput._bytes == null) {
            this._out._bytes = new ByteArrayOutputStream2(512);
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this._out.close();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        this._out.flush();
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
        while (i2 > 512) {
            write(str, i, 512);
            i += 512;
            i2 -= 512;
        }
        if (this._out._chars == null) {
            this._out._chars = new char[512];
        }
        char[] cArr = this._out._chars;
        str.getChars(i, i + i2, cArr, 0);
        write(cArr, 0, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0178 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x017c A[SYNTHETIC] */
    @Override // java.io.Writer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(char[] r11, int r12, int r13) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.HttpWriter.write(char[], int, int):void");
    }

    private Writer getConverter() throws IOException {
        if (this._out._converter == null) {
            HttpOutput httpOutput = this._out;
            httpOutput._converter = new OutputStreamWriter(httpOutput._bytes, this._out._characterEncoding);
        }
        return this._out._converter;
    }
}
