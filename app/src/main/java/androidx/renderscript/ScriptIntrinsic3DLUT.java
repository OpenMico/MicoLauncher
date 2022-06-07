package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsic3DLUT extends ScriptIntrinsic {
    private Allocation a;
    private Element b;

    protected ScriptIntrinsic3DLUT(long j, RenderScript renderScript, Element element) {
        super(j, renderScript);
        this.b = element;
    }

    public static ScriptIntrinsic3DLUT create(RenderScript renderScript, Element element) {
        if (element.isCompatible(Element.U8_4(renderScript))) {
            boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
            ScriptIntrinsic3DLUT scriptIntrinsic3DLUT = new ScriptIntrinsic3DLUT(renderScript.a(8, element.a(renderScript), z), renderScript, element);
            scriptIntrinsic3DLUT.setIncSupp(z);
            return scriptIntrinsic3DLUT;
        }
        throw new RSIllegalArgumentException("Element must be compatible with uchar4.");
    }

    public void setLUT(Allocation allocation) {
        Type type = allocation.getType();
        if (type.getZ() == 0) {
            throw new RSIllegalArgumentException("LUT must be 3d.");
        } else if (type.getElement().isCompatible(this.b)) {
            this.a = allocation;
            setVar(0, this.a);
        } else {
            throw new RSIllegalArgumentException("LUT element type must match.");
        }
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        forEach(0, allocation, allocation2, (FieldPacker) null);
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 3, null, null);
    }
}
