package org.eclipse.jetty.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class MultiPartOutputStream extends FilterOutputStream {
    private String boundary = "jetty" + System.identityHashCode(this) + Long.toString(System.currentTimeMillis(), 36);
    private byte[] boundaryBytes = this.boundary.getBytes("ISO-8859-1");
    private boolean inPart;
    private static final byte[] __CRLF = {13, 10};
    private static final byte[] __DASHDASH = {45, 45};
    public static String MULTIPART_MIXED = "multipart/mixed";
    public static String MULTIPART_X_MIXED_REPLACE = "multipart/x-mixed-replace";

    public MultiPartOutputStream(OutputStream outputStream) throws IOException {
        super(outputStream);
        this.inPart = false;
        this.inPart = false;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.inPart) {
            this.out.write(__CRLF);
        }
        this.out.write(__DASHDASH);
        this.out.write(this.boundaryBytes);
        this.out.write(__DASHDASH);
        this.out.write(__CRLF);
        this.inPart = false;
        super.close();
    }

    public String getBoundary() {
        return this.boundary;
    }

    public OutputStream getOut() {
        return this.out;
    }

    public void startPart(String str) throws IOException {
        if (this.inPart) {
            this.out.write(__CRLF);
        }
        this.inPart = true;
        this.out.write(__DASHDASH);
        this.out.write(this.boundaryBytes);
        this.out.write(__CRLF);
        OutputStream outputStream = this.out;
        outputStream.write(("Content-Type: " + str).getBytes("ISO-8859-1"));
        this.out.write(__CRLF);
        this.out.write(__CRLF);
    }

    public void startPart(String str, String[] strArr) throws IOException {
        if (this.inPart) {
            this.out.write(__CRLF);
        }
        this.inPart = true;
        this.out.write(__DASHDASH);
        this.out.write(this.boundaryBytes);
        this.out.write(__CRLF);
        OutputStream outputStream = this.out;
        outputStream.write(("Content-Type: " + str).getBytes("ISO-8859-1"));
        this.out.write(__CRLF);
        for (int i = 0; strArr != null && i < strArr.length; i++) {
            this.out.write(strArr[i].getBytes("ISO-8859-1"));
            this.out.write(__CRLF);
        }
        this.out.write(__CRLF);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
    }
}
