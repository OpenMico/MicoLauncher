package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;
import com.uc.crashsdk.export.LogType;

/* loaded from: classes.dex */
public class ScriptIntrinsicLUT extends ScriptIntrinsic {
    private Allocation b;
    private final Matrix4f a = new Matrix4f();
    private final byte[] c = new byte[1024];
    private boolean d = true;

    protected ScriptIntrinsicLUT(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicLUT create(RenderScript renderScript, Element element) {
        boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
        ScriptIntrinsicLUT scriptIntrinsicLUT = new ScriptIntrinsicLUT(renderScript.a(3, element.a(renderScript), z), renderScript);
        scriptIntrinsicLUT.setIncSupp(z);
        scriptIntrinsicLUT.b = Allocation.createSized(renderScript, Element.U8(renderScript), 1024);
        for (int i = 0; i < 256; i++) {
            byte[] bArr = scriptIntrinsicLUT.c;
            byte b = (byte) i;
            bArr[i] = b;
            bArr[i + 256] = b;
            bArr[i + 512] = b;
            bArr[i + LogType.UNEXP_OTHER] = b;
        }
        scriptIntrinsicLUT.setVar(0, scriptIntrinsicLUT.b);
        return scriptIntrinsicLUT;
    }

    private void a(int i, int i2) {
        if (i < 0 || i > 255) {
            throw new RSIllegalArgumentException("Index out of range (0-255).");
        } else if (i2 < 0 || i2 > 255) {
            throw new RSIllegalArgumentException("Value out of range (0-255).");
        }
    }

    public void setRed(int i, int i2) {
        a(i, i2);
        this.c[i] = (byte) i2;
        this.d = true;
    }

    public void setGreen(int i, int i2) {
        a(i, i2);
        this.c[i + 256] = (byte) i2;
        this.d = true;
    }

    public void setBlue(int i, int i2) {
        a(i, i2);
        this.c[i + 512] = (byte) i2;
        this.d = true;
    }

    public void setAlpha(int i, int i2) {
        a(i, i2);
        this.c[i + LogType.UNEXP_OTHER] = (byte) i2;
        this.d = true;
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        if (this.d) {
            this.d = false;
            this.b.copyFromUnchecked(this.c);
        }
        forEach(0, allocation, allocation2, (FieldPacker) null);
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 3, null, null);
    }
}
