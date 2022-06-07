package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsicConvolve3x3 extends ScriptIntrinsic {
    private final float[] a = new float[9];
    private Allocation b;

    ScriptIntrinsicConvolve3x3(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicConvolve3x3 create(RenderScript renderScript, Element element) {
        float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        if (element.isCompatible(Element.U8(renderScript)) || element.isCompatible(Element.U8_2(renderScript)) || element.isCompatible(Element.U8_3(renderScript)) || element.isCompatible(Element.U8_4(renderScript)) || element.isCompatible(Element.F32(renderScript)) || element.isCompatible(Element.F32_2(renderScript)) || element.isCompatible(Element.F32_3(renderScript)) || element.isCompatible(Element.F32_4(renderScript))) {
            boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
            ScriptIntrinsicConvolve3x3 scriptIntrinsicConvolve3x3 = new ScriptIntrinsicConvolve3x3(renderScript.a(1, element.a(renderScript), z), renderScript);
            scriptIntrinsicConvolve3x3.setIncSupp(z);
            scriptIntrinsicConvolve3x3.setCoefficients(fArr);
            return scriptIntrinsicConvolve3x3;
        }
        throw new RSIllegalArgumentException("Unsupported element type.");
    }

    public void setInput(Allocation allocation) {
        this.b = allocation;
        setVar(1, allocation);
    }

    public void setCoefficients(float[] fArr) {
        FieldPacker fieldPacker = new FieldPacker(36);
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
