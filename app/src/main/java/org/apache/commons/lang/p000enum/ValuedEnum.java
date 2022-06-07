package org.apache.commons.lang.p000enum;

import org.apache.commons.lang.ClassUtils;

/* renamed from: org.apache.commons.lang.enum.ValuedEnum  reason: invalid package */
/* loaded from: classes5.dex */
public abstract class ValuedEnum extends Enum {
    private static final long serialVersionUID = -7129650521543789085L;
    private final int iValue;

    protected ValuedEnum(String str, int i) {
        super(str);
        this.iValue = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static Enum getEnum(Class cls, int i) {
        if (cls != null) {
            for (ValuedEnum valuedEnum : Enum.getEnumList(cls)) {
                if (valuedEnum.getValue() == i) {
                    return valuedEnum;
                }
            }
            return null;
        }
        throw new IllegalArgumentException("The Enum Class must not be null");
    }

    public final int getValue() {
        return this.iValue;
    }

    @Override // org.apache.commons.lang.p000enum.Enum, java.lang.Comparable
    public int compareTo(Object obj) {
        return this.iValue - ((ValuedEnum) obj).iValue;
    }

    @Override // org.apache.commons.lang.p000enum.Enum
    public String toString() {
        if (this.iToString == null) {
            String shortClassName = ClassUtils.getShortClassName(getEnumClass());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(shortClassName);
            stringBuffer.append("[");
            stringBuffer.append(getName());
            stringBuffer.append("=");
            stringBuffer.append(getValue());
            stringBuffer.append("]");
            this.iToString = stringBuffer.toString();
        }
        return this.iToString;
    }
}
