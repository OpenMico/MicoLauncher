package org.apache.commons.lang3.builder;

import com.xiaomi.mipush.sdk.Constants;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

/* loaded from: classes5.dex */
public class MultilineRecursiveToStringStyle extends RecursiveToStringStyle {
    private static final long serialVersionUID = 1;
    private int indent = 2;
    private int spaces = 2;

    public MultilineRecursiveToStringStyle() {
        b();
    }

    private void b() {
        setArrayStart("{" + SystemUtils.LINE_SEPARATOR + ((Object) a(this.spaces)));
        setArraySeparator(Constants.ACCEPT_TIME_SEPARATOR_SP + SystemUtils.LINE_SEPARATOR + ((Object) a(this.spaces)));
        setArrayEnd(SystemUtils.LINE_SEPARATOR + ((Object) a(this.spaces - this.indent)) + "}");
        setContentStart("[" + SystemUtils.LINE_SEPARATOR + ((Object) a(this.spaces)));
        setFieldSeparator(Constants.ACCEPT_TIME_SEPARATOR_SP + SystemUtils.LINE_SEPARATOR + ((Object) a(this.spaces)));
        setContentEnd(SystemUtils.LINE_SEPARATOR + ((Object) a(this.spaces - this.indent)) + "]");
    }

    private StringBuilder a(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(StringUtils.SPACE);
        }
        return sb;
    }

    @Override // org.apache.commons.lang3.builder.RecursiveToStringStyle, org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, Object obj) {
        if (ClassUtils.isPrimitiveWrapper(obj.getClass()) || String.class.equals(obj.getClass()) || !accept(obj.getClass())) {
            super.appendDetail(stringBuffer, str, obj);
            return;
        }
        this.spaces += this.indent;
        b();
        stringBuffer.append(ReflectionToStringBuilder.toString(obj, this));
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, Object[] objArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, objArr);
        this.spaces -= this.indent;
        b();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void reflectionAppendArrayDetail(StringBuffer stringBuffer, String str, Object obj) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, obj);
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, long[] jArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, jArr);
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, int[] iArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, iArr);
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, short[] sArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, sArr);
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, byte[] bArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, bArr);
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, char[] cArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, cArr);
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, double[] dArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, dArr);
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, float[] fArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, fArr);
        this.spaces -= this.indent;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, boolean[] zArr) {
        this.spaces += this.indent;
        b();
        super.appendDetail(stringBuffer, str, zArr);
        this.spaces -= this.indent;
        b();
    }
}
