package androidx.renderscript;

import android.content.res.Resources;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class ScriptC extends Script {
    protected ScriptC(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    protected ScriptC(RenderScript renderScript, Resources resources, int i) {
        super(0L, renderScript);
        long a = a(renderScript, resources, i);
        if (a != 0) {
            a(a);
            return;
        }
        throw new RSRuntimeException("Loading of ScriptC script failed.");
    }

    protected ScriptC(RenderScript renderScript, String str, byte[] bArr, byte[] bArr2) {
        super(0L, renderScript);
        long j;
        if (RenderScript.g == 4) {
            j = a(renderScript, str, bArr);
        } else {
            j = a(renderScript, str, bArr2);
        }
        if (j != 0) {
            a(j);
            return;
        }
        throw new RSRuntimeException("Loading of ScriptC script failed.");
    }

    private static synchronized long a(RenderScript renderScript, Resources resources, int i) {
        long a;
        synchronized (ScriptC.class) {
            InputStream openRawResource = resources.openRawResource(i);
            try {
                try {
                    byte[] bArr = new byte[1024];
                    int i2 = 0;
                    while (true) {
                        int length = bArr.length - i2;
                        if (length == 0) {
                            byte[] bArr2 = new byte[bArr.length * 2];
                            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                            length = bArr2.length - i2;
                            bArr = bArr2;
                        }
                        int read = openRawResource.read(bArr, i2, length);
                        if (read <= 0) {
                            a = renderScript.a(resources.getResourceEntryName(i), renderScript.getApplicationContext().getCacheDir().toString(), bArr, i2);
                        } else {
                            i2 += read;
                        }
                    }
                } finally {
                    openRawResource.close();
                }
            } catch (IOException unused) {
                throw new Resources.NotFoundException();
            }
        }
        return a;
    }

    private static synchronized long a(RenderScript renderScript, String str, byte[] bArr) {
        long a;
        synchronized (ScriptC.class) {
            a = renderScript.a(str, renderScript.getApplicationContext().getCacheDir().toString(), bArr, bArr.length);
        }
        return a;
    }
}
