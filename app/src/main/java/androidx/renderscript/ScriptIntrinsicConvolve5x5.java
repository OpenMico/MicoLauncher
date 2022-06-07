package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsicConvolve5x5 extends ScriptIntrinsic {
    private final float[] a = new float[25];
    private Allocation b;

    ScriptIntrinsicConvolve5x5(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicConvolve5x5 create(RenderScript renderScript, Element element) {
        if (element.isCompatible(Element.U8(renderScript)) || element.isCompatible(Element.U8_2(renderScript)) || element.isCompatible(Element.U8_3(renderScript)) || element.isCompatible(Element.U8_4(renderScript)) || element.isCompatible(Element.F32(renderScript)) || element.isCompatible(Element.F32_2(renderScript)) || element.isCompatible(Element.F32_3(renderScript)) || element.isCompatible(Element.F32_4(renderScript))) {
            boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
            ScriptIntrinsicConvolve5x5 scriptIntrinsicConvolve5x5 = new ScriptIntrinsicConvolve5x5(renderScript.a(4, element.a(renderScript), z), renderScript);
            scriptIntrinsicConvolve5x5.setIncSupp(z);
            return scriptIntrinsicConvolve5x5;
        }
        throw new RSIllegalArgumentException("Unsupported element type.");
    }

    public void setInput(Allocation allocation) {
        this.b = allocation;
        setVar(1, allocation);
    }

    public void setCoefficients(float[] fArr) {
        FieldPacker fieldPacker = new FieldPacker(100);
        int i = 0;
        while (true) {
            float[] fArr2 = this.a;
            if (i < fArr2.length) {
                fArr2[i] = fArr[i];
                fieldPacker.addF32(fArr2[i]);
                i++;
            } else {
                setVar(0, fieldPacker);
                return;
            }
        }
    }

    public void forEach(Allocation allocation) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null);
    }

    public void forEach(Allocation allocation, Script.LaunchOptions launchOptions) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null, launchOptions);
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
