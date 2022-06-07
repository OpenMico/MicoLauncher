package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsicYuvToRGB extends ScriptIntrinsic {
    private Allocation a;

    ScriptIntrinsicYuvToRGB(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicYuvToRGB create(RenderScript renderScript, Element element) {
        boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
        ScriptIntrinsicYuvToRGB scriptIntrinsicYuvToRGB = new ScriptIntrinsicYuvToRGB(renderScript.a(6, element.a(renderScript), z), renderScript);
        scriptIntrinsicYuvToRGB.setIncSupp(z);
        return scriptIntrinsicYuvToRGB;
    }

    public void setInput(Allocation allocation) {
        this.a = allocation;
        setVar(0, allocation);
    }

    public void forEach(Allocation allocation) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null);
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(0, null);
    }
}
