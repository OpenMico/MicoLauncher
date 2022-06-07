package org.fourthline.cling.support.lastchange;

import java.util.Map;
import org.fourthline.cling.model.types.Datatype;
import org.fourthline.cling.model.types.UnsignedIntegerTwoBytes;

/* loaded from: classes5.dex */
public class EventedValueUnsignedIntegerTwoBytes extends EventedValue<UnsignedIntegerTwoBytes> {
    public EventedValueUnsignedIntegerTwoBytes(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
        super(unsignedIntegerTwoBytes);
    }

    public EventedValueUnsignedIntegerTwoBytes(Map.Entry<String, String>[] entryArr) {
        super(entryArr);
    }

    @Override // org.fourthline.cling.support.lastchange.EventedValue
    protected Datatype getDatatype() {
        return Datatype.Builtin.UI2.getDatatype();
    }
}
