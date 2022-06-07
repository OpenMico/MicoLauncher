package org.fourthline.cling.model.state;

import java.lang.reflect.Method;
import org.seamless.util.Reflections;

/* loaded from: classes5.dex */
public class GetterStateVariableAccessor extends StateVariableAccessor {
    private Method getter;

    public GetterStateVariableAccessor(Method method) {
        this.getter = method;
    }

    public Method getGetter() {
        return this.getter;
    }

    @Override // org.fourthline.cling.model.state.StateVariableAccessor
    public Class<?> getReturnType() {
        return getGetter().getReturnType();
    }

    @Override // org.fourthline.cling.model.state.StateVariableAccessor
    public Object read(Object obj) throws Exception {
        return Reflections.invoke(getGetter(), obj, new Object[0]);
    }

    @Override // org.fourthline.cling.model.state.StateVariableAccessor
    public String toString() {
        return super.toString() + " Method: " + getGetter();
    }
}
