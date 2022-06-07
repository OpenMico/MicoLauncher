package org.fourthline.cling.model.state;

import java.lang.reflect.Field;
import org.seamless.util.Reflections;

/* loaded from: classes5.dex */
public class FieldStateVariableAccessor extends StateVariableAccessor {
    protected Field field;

    public FieldStateVariableAccessor(Field field) {
        this.field = field;
    }

    public Field getField() {
        return this.field;
    }

    @Override // org.fourthline.cling.model.state.StateVariableAccessor
    public Class<?> getReturnType() {
        return getField().getType();
    }

    @Override // org.fourthline.cling.model.state.StateVariableAccessor
    public Object read(Object obj) throws Exception {
        return Reflections.get(this.field, obj);
    }

    @Override // org.fourthline.cling.model.state.StateVariableAccessor
    public String toString() {
        return super.toString() + " Field: " + getField();
    }
}
