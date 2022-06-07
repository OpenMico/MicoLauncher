package androidx.renderscript;

import android.util.SparseArray;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class Script extends BaseObj {
    private final SparseArray<KernelID> b = new SparseArray<>();
    private final SparseArray<InvokeID> c = new SparseArray<>();
    private final SparseArray<FieldID> d = new SparseArray<>();
    private boolean a = false;

    /* loaded from: classes.dex */
    public static class Builder {
    }

    protected void setIncSupp(boolean z) {
        this.a = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isIncSupp() {
        return this.a;
    }

    long a(Allocation allocation) {
        if (allocation == null) {
            return 0L;
        }
        Type type = allocation.getType();
        long a = this.t.a(allocation.a(this.t), type.getDummyType(this.t, type.getElement().getDummyElement(this.t)), type.getX() * type.getElement().getBytesSize());
        allocation.setIncAllocID(a);
        return a;
    }

    /* loaded from: classes.dex */
    public static final class KernelID extends BaseObj {
        Script a;
        int b;
        int c;

        KernelID(long j, RenderScript renderScript, Script script, int i, int i2) {
            super(j, renderScript);
            this.a = script;
            this.b = i;
            this.c = i2;
        }
    }

    protected KernelID createKernelID(int i, int i2, Element element, Element element2) {
        KernelID kernelID = this.b.get(i);
        if (kernelID != null) {
            return kernelID;
        }
        long b = this.t.b(a(this.t), i, i2, this.a);
        if (b != 0) {
            KernelID kernelID2 = new KernelID(b, this.t, this, i, i2);
            this.b.put(i, kernelID2);
            return kernelID2;
        }
        throw new RSDriverException("Failed to create KernelID");
    }

    /* loaded from: classes.dex */
    public static final class InvokeID extends BaseObj {
        Script a;
        int b;

        InvokeID(long j, RenderScript renderScript, Script script, int i) {
            super(j, renderScript);
            this.a = script;
            this.b = i;
        }
    }

    protected InvokeID createInvokeID(int i) {
        InvokeID invokeID = this.c.get(i);
        if (invokeID != null) {
            return invokeID;
        }
        long b = this.t.b(a(this.t), i);
        if (b != 0) {
            InvokeID invokeID2 = new InvokeID(b, this.t, this, i);
            this.c.put(i, invokeID2);
            return invokeID2;
        }
        throw new RSDriverException("Failed to create KernelID");
    }

    /* loaded from: classes.dex */
    public static final class FieldID extends BaseObj {
        Script a;
        int b;

        FieldID(long j, RenderScript renderScript, Script script, int i) {
            super(j, renderScript);
            this.a = script;
            this.b = i;
        }
    }

    protected FieldID createFieldID(int i, Element element) {
        FieldID fieldID = this.d.get(i);
        if (fieldID != null) {
            return fieldID;
        }
        long b = this.t.b(a(this.t), i, this.a);
        if (b != 0) {
            FieldID fieldID2 = new FieldID(b, this.t, this, i);
            this.d.put(i, fieldID2);
            return fieldID2;
        }
        throw new RSDriverException("Failed to create FieldID");
    }

    protected void invoke(int i) {
        this.t.a(a(this.t), i, this.a);
    }

    protected void invoke(int i, FieldPacker fieldPacker) {
        if (fieldPacker != null) {
            this.t.a(a(this.t), i, fieldPacker.getData(), this.a);
        } else {
            this.t.a(a(this.t), i, this.a);
        }
    }

    public void bindAllocation(Allocation allocation, int i) {
        this.t.g();
        if (allocation != null) {
            this.t.a(a(this.t), allocation.a(this.t), i, this.a);
        } else {
            this.t.a(a(this.t), 0L, i, this.a);
        }
    }

    public void setTimeZone(String str) {
        this.t.g();
        try {
            this.t.a(a(this.t), str.getBytes("UTF-8"), this.a);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void forEach(int i, Allocation allocation, Allocation allocation2, FieldPacker fieldPacker) {
        if (allocation == null && allocation2 == null) {
            throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
        }
        long a = allocation != null ? allocation.a(this.t) : 0L;
        long a2 = allocation2 != null ? allocation2.a(this.t) : 0L;
        byte[] bArr = null;
        if (fieldPacker != null) {
            bArr = fieldPacker.getData();
        }
        if (this.a) {
            this.t.a(a(this.t), i, a(allocation), a(allocation2), bArr, this.a);
        } else {
            this.t.a(a(this.t), i, a, a2, bArr, this.a);
        }
    }

    protected void forEach(int i, Allocation allocation, Allocation allocation2, FieldPacker fieldPacker, LaunchOptions launchOptions) {
        if (allocation == null && allocation2 == null) {
            throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
        } else if (launchOptions == null) {
            forEach(i, allocation, allocation2, fieldPacker);
        } else {
            long a = allocation != null ? allocation.a(this.t) : 0L;
            long a2 = allocation2 != null ? allocation2.a(this.t) : 0L;
            byte[] bArr = null;
            if (fieldPacker != null) {
                bArr = fieldPacker.getData();
            }
            if (this.a) {
                this.t.a(a(this.t), i, a(allocation), a(allocation2), bArr, launchOptions.a, launchOptions.c, launchOptions.b, launchOptions.d, launchOptions.e, launchOptions.f, this.a);
            } else {
                this.t.a(a(this.t), i, a, a2, bArr, launchOptions.a, launchOptions.c, launchOptions.b, launchOptions.d, launchOptions.e, launchOptions.f, this.a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Script(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    protected void forEach(int i, Allocation[] allocationArr, Allocation allocation, FieldPacker fieldPacker) {
        forEach(i, allocationArr, allocation, fieldPacker, (LaunchOptions) null);
    }

    protected void forEach(int i, Allocation[] allocationArr, Allocation allocation, FieldPacker fieldPacker, LaunchOptions launchOptions) {
        long[] jArr;
        this.t.g();
        if (allocationArr != null) {
            for (Allocation allocation2 : allocationArr) {
                this.t.a(allocation2);
            }
        }
        this.t.a(allocation);
        if (allocationArr == null && allocation == null) {
            throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
        }
        if (allocationArr != null) {
            long[] jArr2 = new long[allocationArr.length];
            for (int i2 = 0; i2 < allocationArr.length; i2++) {
                jArr2[i2] = allocationArr[i2].a(this.t);
            }
            jArr = jArr2;
        } else {
            jArr = null;
        }
        long j = 0;
        if (allocation != null) {
            j = allocation.a(this.t);
        }
        this.t.a(a(this.t), i, jArr, j, fieldPacker != null ? fieldPacker.getData() : null, launchOptions != null ? new int[]{launchOptions.a, launchOptions.c, launchOptions.b, launchOptions.d, launchOptions.e, launchOptions.f} : null);
    }

    protected void reduce(int i, Allocation[] allocationArr, Allocation allocation, LaunchOptions launchOptions) {
        this.t.g();
        if (allocationArr == null || allocationArr.length < 1) {
            throw new RSIllegalArgumentException("At least one input is required.");
        } else if (allocation != null) {
            for (Allocation allocation2 : allocationArr) {
                this.t.a(allocation2);
            }
            long[] jArr = new long[allocationArr.length];
            for (int i2 = 0; i2 < allocationArr.length; i2++) {
                jArr[i2] = allocationArr[i2].a(this.t);
            }
            long a = allocation.a(this.t);
            int[] iArr = null;
            if (launchOptions != null) {
                iArr = new int[]{launchOptions.a, launchOptions.c, launchOptions.b, launchOptions.d, launchOptions.e, launchOptions.f};
            }
            this.t.a(a(this.t), i, jArr, a, iArr);
        } else {
            throw new RSIllegalArgumentException("aout is required to be non-null.");
        }
    }

    public void setVar(int i, float f) {
        this.t.a(a(this.t), i, f, this.a);
    }

    public void setVar(int i, double d) {
        this.t.a(a(this.t), i, d, this.a);
    }

    public void setVar(int i, int i2) {
        this.t.a(a(this.t), i, i2, this.a);
    }

    public void setVar(int i, long j) {
        this.t.a(a(this.t), i, j, this.a);
    }

    public void setVar(int i, boolean z) {
        this.t.a(a(this.t), i, z ? 1 : 0, this.a);
    }

    public void setVar(int i, BaseObj baseObj) {
        long j = 0;
        if (this.a) {
            this.t.b(a(this.t), i, baseObj == null ? 0L : a((Allocation) baseObj), this.a);
            return;
        }
        RenderScript renderScript = this.t;
        long a = a(this.t);
        if (baseObj != null) {
            j = baseObj.a(this.t);
        }
        renderScript.b(a, i, j, this.a);
    }

    public void setVar(int i, FieldPacker fieldPacker) {
        this.t.b(a(this.t), i, fieldPacker.getData(), this.a);
    }

    public void setVar(int i, FieldPacker fieldPacker, Element element, int[] iArr) {
        if (this.a) {
            this.t.a(a(this.t), i, fieldPacker.getData(), element.getDummyElement(this.t), iArr, this.a);
            return;
        }
        this.t.a(a(this.t), i, fieldPacker.getData(), element.a(this.t), iArr, this.a);
    }

    /* loaded from: classes.dex */
    public static class FieldBase {
        protected Allocation mAllocation;
        protected Element mElement;

        public void updateAllocation() {
        }

        protected void init(RenderScript renderScript, int i) {
            this.mAllocation = Allocation.createSized(renderScript, this.mElement, i, 1);
        }

        protected void init(RenderScript renderScript, int i, int i2) {
            this.mAllocation = Allocation.createSized(renderScript, this.mElement, i, i2 | 1);
        }

        protected FieldBase() {
        }

        public Element getElement() {
            return this.mElement;
        }

        public Type getType() {
            return this.mAllocation.getType();
        }

        public Allocation getAllocation() {
            return this.mAllocation;
        }
    }

    /* loaded from: classes.dex */
    public static final class LaunchOptions {
        private int a = 0;
        private int b = 0;
        private int c = 0;
        private int d = 0;
        private int e = 0;
        private int f = 0;

        public LaunchOptions setX(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new RSIllegalArgumentException("Invalid dimensions");
            }
            this.a = i;
            this.c = i2;
            return this;
        }

        public LaunchOptions setY(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new RSIllegalArgumentException("Invalid dimensions");
            }
            this.b = i;
            this.d = i2;
            return this;
        }

        public LaunchOptions setZ(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new RSIllegalArgumentException("Invalid dimensions");
            }
            this.e = i;
            this.f = i2;
            return this;
        }

        public int getXStart() {
            return this.a;
        }

        public int getXEnd() {
            return this.c;
        }

        public int getYStart() {
            return this.b;
        }

        public int getYEnd() {
            return this.d;
        }

        public int getZStart() {
            return this.e;
        }

        public int getZEnd() {
            return this.f;
        }
    }
}
