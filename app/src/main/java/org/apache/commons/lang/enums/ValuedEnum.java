package org.apache.commons.lang.enums;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang.ClassUtils;

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

    @Override // org.apache.commons.lang.enums.Enum, java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == this) {
            return 0;
        }
        if (obj.getClass() == getClass()) {
            return this.iValue - ((ValuedEnum) obj).iValue;
        }
        if (obj.getClass().getName().equals(getClass().getName())) {
            return this.iValue - a(obj);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Different enum class '");
        stringBuffer.append(ClassUtils.getShortClassName(obj.getClass()));
        stringBuffer.append(LrcRow.SINGLE_QUOTE);
        throw new ClassCastException(stringBuffer.toString());
    }

    private int a(Object obj) {
        try {
            return ((Integer) obj.getClass().getMethod("getValue", null).invoke(obj, null)).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            throw new IllegalStateException("This should not happen");
        }
    }

    @Override // org.apache.commons.lang.enums.Enum
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
