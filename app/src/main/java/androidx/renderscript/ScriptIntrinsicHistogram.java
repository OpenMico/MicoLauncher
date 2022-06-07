package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsicHistogram extends ScriptIntrinsic {
    private Allocation a;

    protected ScriptIntrinsicHistogram(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicHistogram create(RenderScript renderScript, Element element) {
        if (element.isCompatible(Element.U8_4(renderScript)) || element.isCompatible(Element.U8_3(renderScript)) || element.isCompatible(Element.U8_2(renderScript)) || element.isCompatible(Element.U8(renderScript))) {
            boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
            ScriptIntrinsicHistogram scriptIntrinsicHistogram = new ScriptIntrinsicHistogram(renderScript.a(9, element.a(renderScript), z), renderScript);
            scriptIntrinsicHistogram.setIncSupp(z);
            return scriptIntrinsicHistogram;
        }
        throw new RSIllegalArgumentException("Unsupported element type.");
    }

    public void forEach(Allocation allocation) {
        forEach(allocation, null);
    }

    public void forEach(Allocation allocation, Script.LaunchOptions launchOptions) {
        if (allocation.getType().getElement().getVectorSize() < this.a.getType().getElement().getVectorSize()) {
            throw new RSIllegalArgumentException("Input vector size must be >= output vector size.");
        } else if (allocation.getType().getElement().isCompatible(Element.U8(this.t)) || allocation.getType().getElement().isCompatible(Element.U8_2(this.t)) || allocation.getType().getElement().isCompatible(Element.U8_3(this.t)) || allocation.getType().getElement().isCompatible(Element.U8_4(this.t))) {
            forEach(0, allocation, (Allocation) null, (FieldPacker) null, launchOptions);
        } else {
            throw new RSIllegalArgumentException("Input type must be U8, U8_1, U8_2 or U8_4.");
        }
    }

    public void setDotCoefficients(float f, float f2, float f3, float f4) {
        if (f < 0.0f || f2 < 0.0f || f3 < 0.0f || f4 < 0.0f) {
            throw new RSIllegalArgumentException("Coefficient may not be negative.");
        } else if (f + f2 + f3 + f4 <= 1.0f) {
            FieldPacker fieldPacker = new FieldPacker(16);
            fieldPacker.addF32(f);
            fieldPacker.addF32(f2);
            fieldPacker.addF32(f3);
            fieldPacker.addF32(f4);
            setVar(0, fieldPacker);
        } else {
            throw new RSIllegalArgumentException("Sum of coefficients must be 1.0 or less.");
        }
    }

    public void setOutput(Allocation allocation) {
        this.a = allocation;
        if (this.a.getType().getElement() != Element.U32(this.t) && this.a.getType().getElement() != Element.U32_2(this.t) && this.a.getType().getElement() != Element.U32_3(this.t) && this.a.getType().getElement() != Element.U32_4(this.t) && this.a.getType().getElement() != Element.I32(this.t) && this.a.getType().getElement() != Element.I32_2(this.t) && this.a.getType().getElement() != Element.I32_3(this.t) && this.a.getType().getElement() != Element.I32_4(this.t)) {
            throw new RSIllegalArgumentException("Output type must be U32 or I32.");
        } else if (this.a.getType().getX() == 256 && this.a.getType().getY() == 0 && !this.a.getType().hasMipmaps() && this.a.getType().getYuv() == 0) {
            setVar(1, allocation);
        } else {
            throw new RSIllegalArgumentException("Output must be 1D, 256 elements.");
        }
    }

    public void forEach_Dot(Allocation allocation) {
        forEach_Dot(allocation, null);
    }

    public void forEach_Dot(Allocation allocation, Script.LaunchOptions launchOptions) {
        if (this.a.getType().getElement().getVectorSize() != 1) {
            throw new RSIllegalArgumentException("Output vector size must be one.");
        } else if (allocation.getType().getElement().isCompatible(Element.U8(this.t)) || allocation.getType().getElement().isCompatible(Element.U8_2(this.t)) || allocation.getType().getElement().isCompatible(Element.U8_3(this.t)) || allocation.getType().getElement().isCompatible(Element.U8_4(this.t))) {
            forEach(1, allocation, (Allocation) null, (FieldPacker) null, launchOptions);
        } else {
            throw new RSIllegalArgumentException("Input type must be U8, U8_1, U8_2 or U8_4.");
        }
    }

    public Script.KernelID getKernelID_Separate() {
        return createKernelID(0, 3, null, null);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
