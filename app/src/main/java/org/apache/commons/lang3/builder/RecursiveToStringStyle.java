package org.apache.commons.lang3.builder;

import java.util.Collection;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes5.dex */
public class RecursiveToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = 1;

    protected boolean accept(Class<?> cls) {
        return true;
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, Object obj) {
        if (ClassUtils.isPrimitiveWrapper(obj.getClass()) || String.class.equals(obj.getClass()) || !accept(obj.getClass())) {
            super.appendDetail(stringBuffer, str, obj);
        } else {
            stringBuffer.append(ReflectionToStringBuilder.toString(obj, this));
        }
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    protected void appendDetail(StringBuffer stringBuffer, String str, Collection<?> collection) {
        appendClassName(stringBuffer, collection);
        appendIdentityHashCode(stringBuffer, collection);
        appendDetail(stringBuffer, str, collection.toArray());
    }
}
