package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsicBlur extends ScriptIntrinsic {
    private final float[] a = new float[9];
    private Allocation b;

    protected ScriptIntrinsicBlur(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicBlur create(RenderScript renderScript, Element element) {
        if (element.isCompatible(Element.U8_4(renderScript)) || element.isCompatible(Element.U8(renderScript))) {
            boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
            ScriptIntrinsicBlur scriptIntrinsicBlur = new ScriptIntrinsicBlur(renderScript.a(5, element.a(renderScript), z), renderScript);
            scriptIntrinsicBlur.setIncSupp(z);
            scriptIntrinsicBlur.setRadius(5.0f);
            return scriptIntrinsicBlur;
        }
        throw new RSIllegalArgumentException("Unsupported element type.");
    }

    public void setInput(Allocation allocation) {
        if (allocation.getType().getY() != 0) {
            this.b = allocation;
            setVar(1, allocation);
            return;
        }
        throw new RSIllegalArgumentException("Input set to a 1D Allocation");
    }

    public void setRadius(float f) {
        if (f <= 0.0f || f > 25.0f) {
            throw new RSIllegalArgumentException("Radius out of range (0 < r <= 25).");
        }
        setVar(0, f);
    }

    public void forEach(Allocation allocation) {
        if (allocation.getType().getY() != 0) {
            forEach(0, (Allocation) null, allocation, (FieldPacker) null);
            return;
        }
        throw new RSIllegalArgumentException("Output is a 1D Allocation");
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
