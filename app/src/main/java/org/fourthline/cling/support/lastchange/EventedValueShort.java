package org.fourthline.cling.support.lastchange;

import java.util.Map;
import org.fourthline.cling.model.types.Datatype;

/* loaded from: classes5.dex */
public class EventedValueShort extends EventedValue<Short> {
    public EventedValueShort(Short sh) {
        super(sh);
    }

    public EventedValueShort(Map.Entry<String, String>[] entryArr) {
        super(entryArr);
    }

    @Override // org.fourthline.cling.support.lastchange.EventedValue
    protected Datatype getDatatype() {
        return Datatype.Builtin.I2_SHORT.getDatatype();
    }
}
