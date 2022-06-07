package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsicColorMatrix extends ScriptIntrinsic {
    private final Matrix4f a = new Matrix4f();
    private final Float4 b = new Float4();

    protected ScriptIntrinsicColorMatrix(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicColorMatrix create(RenderScript renderScript, Element element) {
        if (element.isCompatible(Element.U8_4(renderScript))) {
            boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
            ScriptIntrinsicColorMatrix scriptIntrinsicColorMatrix = new ScriptIntrinsicColorMatrix(renderScript.a(2, element.a(renderScript), z), renderScript);
            scriptIntrinsicColorMatrix.setIncSupp(z);
            return scriptIntrinsicColorMatrix;
        }
        throw new RSIllegalArgumentException("Unsupported element type.");
    }

    private void c() {
        FieldPacker fieldPacker = new FieldPacker(64);
        fieldPacker.addMatrix(this.a);
        setVar(0, fieldPacker);
    }

    public void setColorMatrix(Matrix4f matrix4f) {
        this.a.load(matrix4f);
        c();
    }

    public void setColorMatrix(Matrix3f matrix3f) {
        this.a.load(matrix3f);
        c();
    }

    public void setAdd(Float4 float4) {
        this.b.x = float4.x;
        this.b.y = float4.y;
        this.b.z = float4.z;
        this.b.w = float4.w;
        FieldPacker fieldPacker = new FieldPacker(16);
        fieldPacker.addF32(float4.x);
        fieldPacker.addF32(float4.y);
        fieldPacker.addF32(float4.z);
        fieldPacker.addF32(float4.w);
        setVar(1, fieldPacker);
    }

    public void setAdd(float f, float f2, float f3, float f4) {
        Float4 float4 = this.b;
        float4.x = f;
        float4.y = f2;
        float4.z = f3;
        float4.w = f4;
        FieldPacker fieldPacker = new FieldPacker(16);
        fieldPacker.addF32(this.b.x);
        fieldPacker.addF32(this.b.y);
        fieldPacker.addF32(this.b.z);
        fieldPacker.addF32(this.b.w);
        setVar(1, fieldPacker);
    }

    public void setGreyscale() {
        this.a.loadIdentity();
        this.a.set(0, 0, 0.299f);
        this.a.set(1, 0, 0.587f);
        this.a.set(2, 0, 0.114f);
        this.a.set(0, 1, 0.299f);
        this.a.set(1, 1, 0.587f);
        this.a.set(2, 1, 0.114f);
        this.a.set(0, 2, 0.299f);
        this.a.set(1, 2, 0.587f);
        this.a.set(2, 2, 0.114f);
        c();
    }

    public void setYUVtoRGB() {
        this.a.loadIdentity();
        this.a.set(0, 0, 1.0f);
        this.a.set(1, 0, 0.0f);
        this.a.set(2, 0, 1.13983f);
        this.a.set(0, 1, 1.0f);
        this.a.set(1, 1, -0.39465f);
        this.a.set(2, 1, -0.5806f);
        this.a.set(0, 2, 1.0f);
        this.a.set(1, 2, 2.03211f);
        this.a.set(2, 2, 0.0f);
        c();
    }

    public void setRGBtoYUV() {
        this.a.loadIdentity();
        this.a.set(0, 0, 0.299f);
        this.a.set(1, 0, 0.587f);
        this.a.set(2, 0, 0.114f);
        this.a.set(0, 1, -0.14713f);
        this.a.set(1, 1, -0.28886f);
        this.a.set(2, 1, 0.436f);
        this.a.set(0, 2, 0.615f);
        this.a.set(1, 2, -0.51499f);
        this.a.set(2, 2, -0.10001f);
        c();
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        forEach(0, allocation, allocation2, (FieldPacker) null);
    }

    public void forEach(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        if (!allocation.getElement().isCompatible(Element.U8(this.t)) && !allocation.getElement().isCompatible(Element.U8_2(this.t)) && !allocation.getElement().isCompatible(Element.U8_3(this.t)) && !allocation.getElement().isCompatible(Element.U8_4(this.t)) && !allocation.getElement().isCompatible(Element.F32(this.t)) && !allocation.getElement().isCompatible(Element.F32_2(this.t)) && !allocation.getElement().isCompatible(Element.F32_3(this.t)) && !allocation.getElement().isCompatible(Element.F32_4(this.t))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        } else if (allocation2.getElement().isCompatible(Element.U8(this.t)) || allocation2.getElement().isCompatible(Element.U8_2(this.t)) || allocation2.getElement().isCompatible(Element.U8_3(this.t)) || allocation2.getElement().isCompatible(Element.U8_4(this.t)) || allocation2.getElement().isCompatible(Element.F32(this.t)) || allocation2.getElement().isCompatible(Element.F32_2(this.t)) || allocation2.getElement().isCompatible(Element.F32_3(this.t)) || allocation2.getElement().isCompatible(Element.F32_4(this.t))) {
            forEach(0, allocation, allocation2, (FieldPacker) null, launchOptions);
        } else {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 3, null, null);
    }
}
