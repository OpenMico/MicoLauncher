package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsicResize extends ScriptIntrinsic {
    private Allocation a;

    protected ScriptIntrinsicResize(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicResize create(RenderScript renderScript) {
        boolean z = renderScript.b() && Build.VERSION.SDK_INT < 21;
        ScriptIntrinsicResize scriptIntrinsicResize = new ScriptIntrinsicResize(renderScript.a(12, 0L, z), renderScript);
        scriptIntrinsicResize.setIncSupp(z);
        return scriptIntrinsicResize;
    }

    public void setInput(Allocation allocation) {
        Element element = allocation.getElement();
        if (element.isCompatible(Element.U8(this.t)) || element.isCompatible(Element.U8_2(this.t)) || element.isCompatible(Element.U8_3(this.t)) || element.isCompatible(Element.U8_4(this.t)) || element.isCompatible(Element.F32(this.t)) || element.isCompatible(Element.F32_2(this.t)) || element.isCompatible(Element.F32_3(this.t)) || element.isCompatible(Element.F32_4(this.t))) {
            this.a = allocation;
            setVar(0, allocation);
            return;
        }
        throw new RSIllegalArgumentException("Unsupported element type.");
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(0, null);
    }

    public void forEach_bicubic(Allocation allocation) {
        if (allocation != this.a) {
            forEach_bicubic(allocation, null);
            return;
        }
        throw new RSIllegalArgumentException("Output cannot be same as Input.");
    }

    public void forEach_bicubic(Allocation allocation, Script.LaunchOptions launchOptions) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null, launchOptions);
    }

    public Script.KernelID getKernelID_bicubic() {
        return createKernelID(0, 2, null, null);
    }
}
