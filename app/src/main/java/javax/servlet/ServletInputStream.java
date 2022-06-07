package javax.servlet;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public abstract class ServletInputStream extends InputStream {
    public int readLine(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        if (i2 <= 0) {
            return 0;
        }
        while (true) {
            int read = read();
            if (read == -1) {
                break;
            }
            i++;
            bArr[i] = (byte) read;
            i3++;
            if (read == 10 || i3 == i2) {
                break;
            }
        }
        if (i3 > 0) {
            return i3;
        }
        return -1;
    }
}
