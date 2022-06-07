package org.fourthline.cling.model.action;

import org.fourthline.cling.model.VariableValue;
import org.fourthline.cling.model.meta.ActionArgument;
import org.fourthline.cling.model.meta.Service;

/* loaded from: classes5.dex */
public class ActionArgumentValue<S extends Service> extends VariableValue {
    private final ActionArgument<S> argument;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ActionArgumentValue(org.fourthline.cling.model.meta.ActionArgument<S> r3, java.lang.Object r4) throws org.fourthline.cling.model.types.InvalidValueException {
        /*
            r2 = this;
            org.fourthline.cling.model.types.Datatype r0 = r3.getDatatype()
            if (r4 == 0) goto L_0x0014
            java.lang.Class r1 = r4.getClass()
            boolean r1 = r1.isEnum()
            if (r1 == 0) goto L_0x0014
            java.lang.String r4 = r4.toString()
        L_0x0014:
            r2.<init>(r0, r4)
            r2.argument = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.model.action.ActionArgumentValue.<init>(org.fourthline.cling.model.meta.ActionArgument, java.lang.Object):void");
    }

    public ActionArgument<S> getArgument() {
        return this.argument;
    }
}
