package javax.servlet.http;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletOutputStream;

/* compiled from: HttpServlet.java */
/* loaded from: classes5.dex */
class a extends ServletOutputStream {
    private static ResourceBundle a = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");
    private int b = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.b;
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        this.b++;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i2 >= 0) {
            this.b += i2;
            return;
        }
        throw new IOException(a.getString("err.io.negativelength"));
    }
}
