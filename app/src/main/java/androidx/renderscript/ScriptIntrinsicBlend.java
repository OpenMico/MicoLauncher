package androidx.renderscript;

import android.os.Build;
import androidx.renderscript.Script;

/* loaded from: classes.dex */
public class ScriptIntrinsicBlend extends ScriptIntrinsic {
    public void forEachDst(Allocation allocation, Allocation allocation2) {
    }

    ScriptIntrinsicBlend(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicBlend create(RenderScript renderScript, Element element) {
        boolean z = renderScript.b() && Build.VERSION.SDK_INT < 19;
        ScriptIntrinsicBlend scriptIntrinsicBlend = new ScriptIntrinsicBlend(renderScript.a(7, element.a(renderScript), z), renderScript);
        scriptIntrinsicBlend.setIncSupp(z);
        return scriptIntrinsicBlend;
    }

    private void a(int i, Allocation allocation, Allocation allocation2) {
        if (!allocation.getElement().isCompatible(Element.U8_4(this.t))) {
            throw new RSIllegalArgumentException("Input is not of expected format.");
        } else if (allocation2.getElement().isCompatible(Element.U8_4(this.t))) {
            forEach(i, allocation, allocation2, (FieldPacker) null);
        } else {
            throw new RSIllegalArgumentException("Output is not of expected format.");
        }
    }

    public void forEachClear(Allocation allocation, Allocation allocation2) {
        a(0, allocation, allocation2);
    }

    public Script.KernelID getKernelIDClear() {
        return createKernelID(0, 3, null, null);
    }

    public void forEachSrc(Allocation allocation, Allocation allocation2) {
        a(1, allocation, allocation2);
    }

    public Script.KernelID getKernelIDSrc() {
        return createKernelID(1, 3, null, null);
    }

    public Script.KernelID getKernelIDDst() {
        return createKernelID(2, 3, null, null);
    }

    public void forEachSrcOver(Allocation allocation, Allocation allocation2) {
        a(3, allocation, allocation2);
    }

    public Script.KernelID getKernelIDSrcOver() {
        return createKernelID(3, 3, null, null);
    }

    public void forEachDstOver(Allocation allocation, Allocation allocation2) {
        a(4, allocation, allocation2);
    }

    public Script.KernelID getKernelIDDstOver() {
        return createKernelID(4, 3, null, null);
    }

    public void forEachSrcIn(Allocation allocation, Allocation allocation2) {
        a(5, allocation, allocation2);
    }

    public Script.KernelID getKernelIDSrcIn() {
        return createKernelID(5, 3, null, null);
    }

    public void forEachDstIn(Allocation allocation, Allocation allocation2) {
        a(6, allocation, allocation2);
    }

    public Script.KernelID getKernelIDDstIn() {
        return createKernelID(6, 3, null, null);
    }

    public void forEachSrcOut(Allocation allocation, Allocation allocation2) {
        a(7, allocation, allocation2);
    }

    public Script.KernelID getKernelIDSrcOut() {
        return createKernelID(7, 3, null, null);
    }

    public void forEachDstOut(Allocation allocation, Allocation allocation2) {
        a(8, allocation, allocation2);
    }

    public Script.KernelID getKernelIDDstOut() {
        return createKernelID(8, 3, null, null);
    }

    public void forEachSrcAtop(Allocation allocation, Allocation allocation2) {
        a(9, allocation, allocation2);
    }

    public Script.KernelID getKernelIDSrcAtop() {
        return createKernelID(9, 3, null, null);
    }

    public void forEachDstAtop(Allocation allocation, Allocation allocation2) {
        a(10, allocation, allocation2);
    }

    public Script.KernelID getKernelIDDstAtop() {
        return createKernelID(10, 3, null, null);
    }

    public void forEachXor(Allocation allocation, Allocation allocation2) {
        a(11, allocation, allocation2);
    }

    public Script.KernelID getKernelIDXor() {
        return createKernelID(11, 3, null, null);
    }

    public void forEachMultiply(Allocation allocation, Allocation allocation2) {
        a(14, allocation, allocation2);
    }

    public Script.KernelID getKernelIDMultiply() {
        return createKernelID(14, 3, null, null);
    }

    public void forEachAdd(Allocation allocation, Allocation allocation2) {
        a(34, allocation, allocation2);
    }

    public Script.KernelID getKernelIDAdd() {
        return createKernelID(34, 3, null, null);
    }

    public void forEachSubtract(Allocation allocation, Allocation allocation2) {
        a(35, allocation, allocation2);
    }

    public Script.KernelID getKernelIDSubtract() {
        return createKernelID(35, 3, null, null);
    }
}
